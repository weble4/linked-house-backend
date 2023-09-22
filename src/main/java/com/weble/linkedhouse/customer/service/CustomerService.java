package com.weble.linkedhouse.customer.service;

import com.weble.linkedhouse.bookmark.repository.BookMarkRepository;
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
import com.weble.linkedhouse.security.jwt.JwtReturn;
import com.weble.linkedhouse.security.jwt.JwtTokenProvider;
import com.weble.linkedhouse.security.jwt.token.RedisTokenRepository;
import com.weble.linkedhouse.security.jwt.token.RefreshToken;
import com.weble.linkedhouse.security.jwt.token.RefreshTokenRepository;
import com.weble.linkedhouse.security.jwt.token.TokenDto;
import com.weble.linkedhouse.util.storage.ObjectStorage;
import io.jsonwebtoken.JwtException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

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
    private final RedisTokenRepository redisTokenRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final DynamicRepository dynamicRepository;
    private final BookMarkRepository bookMarkRepository;
    private final ObjectStorage objectStorage;

    @Value("${spring.mail.username}")
    private String emailSender;

    public ProfileDto getCustomer(Long customerId) {
        return customerRepository.findByIdWithProfile(customerId)
                .map(Customer::getCustomerProfile)
                .map(ProfileDto::from)
                .orElseThrow(NotExistCustomer::new);
    }

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
    public void certifiedEmail(Long customerId) {
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

        RefreshToken refreshToken = RefreshToken.create(request.getCustomerEmail(), tokenDto.getRefreshToken());
        redisTokenRepository.save(refreshToken.getKey(), refreshToken.getValue());
        refreshTokenRepository.save(refreshToken);

        return LoginResponse.of(customer, customer.getCustomerProfile(), tokenDto);
    }

    @Transactional
    public void logout(UserDetailsImpl userDetails) {
        String email = userDetails.getUsername();
        redisTokenRepository.deleteToken(email);
    }

    @Transactional
    public void applyHost(UserDetailsImpl userDetails) {
        Customer customer = customerRepository.findByIdWithProfile(userDetails.getUserId())
                .orElseThrow(NotExistCustomer::new);

        if (customer.getRole().contains(Role.ROLE_HOST)) {
            throw new AlreadyHasRole();
        }
        customer.addRole(Role.ROLE_HOST);
    }

    public boolean checkEmail(String email) {
        return customerRepository.findByCustomerEmail(email).isPresent();
    }

    public ProfileDto getCustomerProfile(UserDetailsImpl userDetails) {
        Customer customer = customerRepository.findByIdWithProfile(userDetails.getUserId())
                .orElseThrow(NotExistCustomer::new);
        return ProfileDto.from(customer.getCustomerProfile());
    }

    @Transactional
    public ProfileDto updateProfile(UserDetailsImpl userDetails, UpdateRequest updateRequest, MultipartFile file) {

        Customer customer = customerRepository.findByIdWithProfile(userDetails.getUserId())
                .orElseThrow(NotExistCustomer::new);

        String imagePath = customer.getCustomerProfile().getImagePath();

        if (fileValid(file)) {
            if(isPathExistence(customer)) {
                boolean isSuccess = objectStorage.deleteFile(customer.getCustomerProfile().getImagePath());
                log.info("Delete isSuccess : {}", isSuccess);
            }
            imagePath = objectStorage.uploadFile(file);
        }

        customer.getCustomerProfile().updateProfile(
                updateRequest.getNickname(),
                updateRequest.getPhoneNum(),
                updateRequest.getPublicAt(),
                imagePath);

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

        String bookmarkTableName = "bookmark_" + getTableName(userDetails);
        if (bookMarkRepository.isTableExists(bookmarkTableName)) {
            bookMarkRepository.deleteTable(bookmarkTableName);
        }
    }

    @Transactional
    public TokenDto reissue(String refreshToken) {

        String email = jwtTokenProvider.getCustomerEmail(refreshToken);
        String value = redisTokenRepository.find(email);

        if (jwtTokenProvider.validToken(value) != JwtReturn.SUCCESS) {
            throw new JwtException("JWT RefreshToken 만료");
        }

        TokenDto tokenDto = jwtTokenProvider.generateToken(email);
        tokenDto.setRefreshToken(refreshToken);
        return tokenDto;
    }

    @Transactional
    public void addRoleAdmin(UserDetailsImpl userDetails) {
        Customer customer = customerRepository.findByIdWithProfile(userDetails.getUserId())
                .orElseThrow(NotExistCustomer::new);

        if (customer.getRole().contains(Role.ROLE_ADMIN)) {
            throw new AlreadyHasRole();
        }
        customer.addRole(Role.ROLE_ADMIN);
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
//                            + "<a href='http://110.165.18.244/certified?customerId=" + customer.getCustomerId() + "'"
                            + "<a href='http://localhost:3000/certified?customerId=" + customer.getCustomerId() + "'"
                            + "target='blenk'>이메일 인증 확인</a><br/>" +
                            "감사합니다.",
                    true);
            javaMailsender.send(message);
        } catch (MessagingException e) {
            log.error("메시지 발송 오류", e);
        }
    }

    private String getTableName(UserDetailsImpl userDetails) {
        //email 뒤를 때버리고 + 유저번호
        int idx = userDetails.getUsername().indexOf("@");
        String origin = userDetails.getUsername().substring(0, idx);
        Long userId = userDetails.getUserId();
        return origin + "_" + userId;
    }

    public Customer findByEmail(String email) {
        return customerRepository.findByCustomerEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Unexpected User"));
    }

    private boolean isPathExistence(Customer customer) {
        return customer.getCustomerProfile().getImagePath() != null;
    }

    private boolean fileValid(MultipartFile file) {
        return file != null;
    }
}
