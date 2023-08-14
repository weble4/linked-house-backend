package com.weble.linkedhouse.customer.service;

import com.weble.linkedhouse.customer.dtos.ProfileDto;
import com.weble.linkedhouse.customer.dtos.request.LoginRequest;
import com.weble.linkedhouse.customer.dtos.request.PasswordFindRequest;
import com.weble.linkedhouse.customer.dtos.request.SignupRequest;
import com.weble.linkedhouse.customer.dtos.request.UpdateRequest;
import com.weble.linkedhouse.customer.dtos.response.LoginResponse;
import com.weble.linkedhouse.customer.dtos.response.SignupResponse;
import com.weble.linkedhouse.customer.entity.Customer;
import com.weble.linkedhouse.customer.entity.CustomerProfile;
import com.weble.linkedhouse.customer.entity.constant.AuthState;
import com.weble.linkedhouse.customer.entity.constant.DeleteRequest;
import com.weble.linkedhouse.customer.entity.constant.Role;
import com.weble.linkedhouse.customer.repository.CustomerRepository;
import com.weble.linkedhouse.customer.repository.DynamicRepository;
import com.weble.linkedhouse.customer.repository.ProfileRepository;
import com.weble.linkedhouse.exception.AlreadyAuthentication;
import com.weble.linkedhouse.exception.AlreadyExistEmailException;
import com.weble.linkedhouse.exception.AlreadyHasRole;
import com.weble.linkedhouse.exception.DeleteCustomerException;
import com.weble.linkedhouse.exception.InvalidSignInInformation;
import com.weble.linkedhouse.exception.NotExistCustomer;
import com.weble.linkedhouse.exception.Unauthorized;
import com.weble.linkedhouse.security.UserDetailsImpl;
import com.weble.linkedhouse.security.jwt.JwtTokenProvider;
import com.weble.linkedhouse.security.jwt.token.RefreshToken;
import com.weble.linkedhouse.security.jwt.token.RefreshTokenRepository;
import com.weble.linkedhouse.security.jwt.token.TokenDto;
import com.weble.linkedhouse.util.CreateFile;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.concurrent.TimeUnit;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CustomerService {

    private final PasswordEncoder passwordEncoder;
    private final JavaMailSender javaMailsender;
    private final CustomerRepository customerRepository;
    private final ProfileRepository profileRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final RedisTemplate<String, Object> redisTemplate;
    private final JwtTokenProvider jwtTokenProvider;
    private final DynamicRepository dynamicRepository;

    @Value("${spring.mail.username}")
    private String emailSender;

    @Transactional
    public SignupResponse saveUser(SignupRequest signupRequest) {
        log.info("saveUser service");

        if (customerRepository.findByCustomerEmail(signupRequest.getCustomerEmail()).isPresent()) {
            throw new AlreadyExistEmailException();
        }
        // 비밀번호 인코딩후 저장
        String pw = passwordEncoder.encode(signupRequest.getCustomerPw());
        signupRequest.pwEncoding(pw);

        Customer customer = customerRepository.save(signupRequest.convertCustomer());
        CustomerProfile profile = profileRepository.save(signupRequest.convertProfile(customer));

        customer.setCustomerProfile(profile);

        SendCheckMail(customer);

        return new SignupResponse(customer.getCustomerEmail());
    }

    @Transactional
    public void activateAccount(Long customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(NotExistCustomer::new);
        if (customer.getAuthState() == AuthState.AUTH) {
            throw new AlreadyAuthentication();
        }
        customer.approveAuth(AuthState.AUTH);
    }

    @Transactional
    public LoginResponse login(LoginRequest request) {
        Customer customer = customerRepository.findByCustomerEmail(request.getCustomerEmail())
                .orElseThrow(InvalidSignInInformation::new);

        if (customer.getAuthState() == AuthState.NONAUTH) {
            throw new Unauthorized();
        }
        if (customer.getDeleteRequest() == DeleteRequest.DELETE) {
            throw new DeleteCustomerException();
        }
        if (!passwordEncoder.matches(request.getCustomerPw(), customer.getCustomerPw())) {
            throw new InvalidSignInInformation();
        }

        TokenDto tokenDto = jwtTokenProvider.generateToken(customer.getCustomerEmail());

        saveRefreshToken(request, tokenDto);

        return LoginResponse.of(customer, customer.getCustomerProfile(), tokenDto);
    }

    @Transactional
    public void applyHost(UserDetailsImpl userDetails) {
        Customer customer = customerRepository.findByCustomerEmailWithCustomerProfile(userDetails.getUsername())
                .orElseThrow(NotExistCustomer::new);

        if (customer.getRole().contains(Role.HOST)) {
            throw new AlreadyHasRole();
        }
        customer.addRole(Role.HOST);
    }

    public ProfileDto getCustomerProfile(UserDetailsImpl userDetails) {
        Customer customer = customerRepository.findByCustomerEmailWithCustomerProfile(userDetails.getUsername())
                .orElseThrow(NotExistCustomer::new);
        return ProfileDto.from(customer.getCustomerProfile());
    }

    @Transactional
    public ProfileDto updateProfile(UserDetailsImpl userDetails, UpdateRequest updateRequest, MultipartFile image) {
        CreateFile createFile = new CreateFile();

        Customer customer = customerRepository.findByCustomerEmailWithCustomerProfile(userDetails.getUsername())
                .orElseThrow(NotExistCustomer::new);

        String imagePath;

        if (image.isEmpty()) {
            if (customer.getCustomerProfile().getImagePath().isEmpty()) {
                imagePath = null;
            } else {
                imagePath = customer.getCustomerProfile().getImagePath();
            }
        } else {
            imagePath = createFile.saveImage(image, customer.getCustomerId());
        }

        customer.getCustomerProfile().updateProfile(updateRequest, imagePath);

        return ProfileDto.from(customer.getCustomerProfile());
    }


    @Transactional
    public void findPassword(PasswordFindRequest passwordFindRequest) {
        Customer customer = customerRepository.findByCustomerEmail(passwordFindRequest.getCustomerEmail())
                .orElseThrow(NotExistCustomer::new);
        String encodePw = passwordEncoder.encode(passwordFindRequest.getCustomerPw());
        customer.changePassword(encodePw);
    }

    @Transactional
    public void withdrawal(UserDetailsImpl userDetails) {
        Customer customer = customerRepository.findById(userDetails.getUserId())
                .orElseThrow(NotExistCustomer::new);
        if (customer.getDeleteRequest() == DeleteRequest.DELETE) {
            throw new DeleteCustomerException();
        }
        customer.deleteAccountRequest();

        String tableName = getTableName(userDetails);
        dynamicRepository.deleteAccount(tableName, userDetails.getUserId());
    }



    private void SendCheckMail(Customer customer) {

        MimeMessage message = javaMailsender.createMimeMessage();

        try {
            MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");
            messageHelper.setTo(customer.getCustomerEmail());
            messageHelper.setFrom(emailSender);
            messageHelper.setSubject("이메일 인증메일 입니다");
            messageHelper.setText(" <h1>메일인증</h1></br>"
                            + customer.getCustomerEmail() + "님<br/>"
                            + "[이메일 인증 확인]을 눌러주세요."
                            + "<a href='http://localhost:8080/customer/activate-state?customerId=" + customer.getCustomerId() + "'"
                            + "target='blenk'>이메일 인증 확인</a><br/>" +
                            "감사합니다.",
                    true);
            //TODO : 여기 들어갈 주소는 나중에 프론트쪽으로 바꾸어 줘야됨.
            // 유저 인증메일 체크 -> 프론트단에서 백으로 인증 업데이트 날림 -> 백에서 인증 업데이트 -> 프론트에서 다시 화면 띄어줌
            javaMailsender.send(message);
        } catch (MessagingException e) {
            log.error("메시지 발송 오류", e);
        }
    }

    private void saveRefreshToken(LoginRequest request, TokenDto tokenDto) {
        RefreshToken refreshToken = RefreshToken.create(request.getCustomerEmail(),
                tokenDto.getRefreshToken());
        authRedisSave(refreshToken.getKey(), refreshToken.getValue());
    }

    private void authRedisSave(String customerEmail, String refreshToken) {
        final ValueOperations<String, Object> stringStringValueOperations = redisTemplate.opsForValue();
        stringStringValueOperations.set(customerEmail, refreshToken);
        redisTemplate.expire(customerEmail, 1209600, TimeUnit.SECONDS);//2주
    }

    private String getTableName(UserDetailsImpl userDetails) {
        int idx = userDetails.getUsername().indexOf("@");
        String origin = userDetails.getUsername().substring(0, idx);
        Long userId = userDetails.getUserId();
        return origin + "_" + String.valueOf(userId);
    }

}
