package com.weble.linkedhouse.customer.service;

import com.weble.linkedhouse.customer.config.TestConfig;
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
import com.weble.linkedhouse.customer.entity.constant.PublicAt;
import com.weble.linkedhouse.customer.entity.constant.Role;
import com.weble.linkedhouse.customer.repository.CustomerRepository;
import com.weble.linkedhouse.customer.repository.ProfileRepository;
import com.weble.linkedhouse.exception.NotExistCustomer;
import com.weble.linkedhouse.exception.Unauthorized;
import com.weble.linkedhouse.security.UserDetailsImpl;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


@SpringBootTest
@Import(TestConfig.class)
@TestPropertySource(properties = {
        "spring.mail.username=dummy",
        "spring.mail.password=dummypassword",
        "jwt.secret_key=testKeyNotRealKeyJustTestKeyAndKeyIsNeedToLength",
})
@Transactional
class CustomerServiceTest {

    @Autowired
    CustomerService customerService;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    ProfileRepository profileRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    // JavaMailSender 빈을 모킹합니다.
    @MockBean
    JavaMailSender javaMailSender;

    @BeforeEach
    void setUp() {
        MimeMessage mimeMessage = Mockito.mock(MimeMessage.class);
        Mockito.when(javaMailSender.createMimeMessage()).thenReturn(mimeMessage);
    }

    @Test
    @DisplayName("Customer 회원 가입 로직 테스트")
    void saveCustomerTest() {
        //given
        SignupRequest user = createSignup();
        //when
        SignupResponse saveCustomer = customerService.saveUser(user);
        Customer customer = customerRepository.findByCustomerEmail(saveCustomer.getCustomerEmail())
                .orElseThrow(NotExistCustomer::new);

        //then
        assertThat(passwordEncoder.matches("abc123", customer.getCustomerPw()))
                .isTrue();
        assertThat(customer.getCustomerEmail()).isEqualTo(user.getCustomerEmail());
        assertThat(customer.getCustomerProfile().getBirthDay()).isEqualTo(user.getBirthDay());
        assertThat(customer.getCustomerProfile().getGender()).isEqualTo(user.getGender());
    }

    @Test
    @DisplayName("Email인증 로직 테스트")
    void emailAuthenticated() {
        //given
        Customer save = customerRepository.save(createUser());
        CustomerProfile profile = profileRepository.save(createProfile(save));

        //when
        Customer customer = customerRepository.findByCustomerEmail("sample@mail.com")
                .orElseThrow(NotExistCustomer::new);

        //after then
        customerService.certifiedEmail(customer.getCustomerId());
        assertThat(customer.getAuthState()).isEqualTo(AuthState.AUTH);
    }

    @Test
    @DisplayName("로그인 기능 로직 체크 - 이메일 미인증으로 인한 로그인 실패")
    void loginFailTest() {
        //given
        Customer customer = customerRepository.save(createUser());
        LoginRequest loginRequest = new LoginRequest(
                customer.getCustomerEmail(),
                "abc123");

        //expected
        assertThatThrownBy(() -> customerService.login(loginRequest))
                .isInstanceOf(Unauthorized.class);
    }

    @Test
    @DisplayName("로그인 기능 로직 체크 - 로그인 성공")
    void loginSuccessTest() {
        //given
        Customer customer = customerRepository.save(createUser());
        CustomerProfile profile = profileRepository.save(createProfile(customer));
        customer.setCustomerProfile(profile);

        customerService.certifiedEmail(customer.getCustomerId());
        LoginRequest loginRequest = new LoginRequest(
                customer.getCustomerEmail(),
                "abc123");

        //when
        LoginResponse loginResponse = customerService.login(loginRequest);

        //then
        assertThat(loginResponse).isNotNull()
                .hasFieldOrPropertyWithValue("customerId", customer.getCustomerId())
                .hasFieldOrPropertyWithValue("customerEmail", "sample@mail.com")
                .hasFieldOrPropertyWithValue("nickname", "nickname")
                .hasFieldOrProperty("token");
    }

    @Test
    @DisplayName("호스트 요청 테스트")
    void hasRoleHost() {
        //given
        Customer customer = customerRepository.save(createUser());
        CustomerProfile profile = profileRepository.save(createProfile(customer));

        UserDetailsImpl userDetails = new UserDetailsImpl(ProfileDto.from(profile));

        //when
        customerService.applyHost(userDetails);

        //then
        Customer updatedCustomer = customerRepository.findById(customer.getCustomerId())
                .orElseThrow(NotExistCustomer::new);
        assertThat(updatedCustomer.getRole()).contains(Role.ROLE_HOST);
    }

    @Test
    @DisplayName("비밀번호 변경 테스트")
    void changePasswordTest () {
        //given
        Customer customer = customerRepository.save(createUser());
        String password = "changePassword";

        PasswordFindRequest passwordFindRequest = new PasswordFindRequest(customer.getCustomerEmail(), password);

        //when
        customerService.findPassword(passwordFindRequest);
        Customer updateCustomer = customerRepository.findByCustomerEmail(customer.getCustomerEmail()).get();

        //then
        assertThat(passwordEncoder.matches(password, updateCustomer.getCustomerPw()))
                .isTrue();
    }

    @Test
    @DisplayName("가입 이메일 중복 체크 - 중복")
    void checkEmailDuplicatedTest() {
        Customer save = customerRepository.save(createUser());

        boolean checkEmail = customerService.checkEmail("sample@mail.com");
        //true - email 중복
        assertThat(checkEmail).isTrue();
    }

    @Test
    @DisplayName("가입 이메일 중복 체크 - 중복 x")
    void checkEmailNotDuplicatedTest() {
        //given
        String email = "sameple@mail.com";

        //expected
        boolean checkEmail = customerService.checkEmail(email);
        assertThat(checkEmail).isFalse();
    }

    @Test
    @DisplayName("유저 회원 탈퇴 업데이트 테스트")
    void withdrawalTest() {
        //given
        Customer customer = customerRepository.save(createUser());
        CustomerProfile profile = profileRepository.save(createProfile(customer));

        //when
        customer.deleteAccountRequest();

        //then
        assertThat(customer.getDeleteRequest())
                .isEqualTo(DeleteRequest.DELETE);
    }

    @Test
    @DisplayName("프로필 받아오기")
    void getProfileTest() {
        Customer customer = customerRepository.save(createUser());
        CustomerProfile profile = profileRepository.save(createProfile(customer));
        customer.setCustomerProfile(profile);
        UserDetailsImpl userDetails = new UserDetailsImpl(ProfileDto.from(profile));

        ProfileDto customerProfile = customerService.getCustomerProfile(userDetails);

        assertThat(customerProfile)
                .hasFieldOrProperty("customerDto")
                .hasFieldOrPropertyWithValue("nickname", profile.getNickname())
                .hasFieldOrPropertyWithValue("gender", profile.getGender())
                .hasFieldOrPropertyWithValue("birthDay", profile.getBirthDay())
                .hasFieldOrPropertyWithValue("phoneNum", profile.getPhoneNum());
    }

    @Test
    @DisplayName("프로필 업데이트")
    void updateProfileTest() {
        Customer customer = customerRepository.save(createUser());
        CustomerProfile profile = profileRepository.save(createProfile(customer));
        customer.setCustomerProfile(profile);
        UserDetailsImpl userDetails = new UserDetailsImpl(ProfileDto.from(profile));

        String name = "file";
        String originalFileName = "test.txt";
        String contentType = "text/plain";
        byte[] content = "File content".getBytes();
        MultipartFile image = new MockMultipartFile(name, originalFileName,contentType,content);

        UpdateRequest updateRequest = UpdateRequest.builder()
                .nickname("update")
                .phoneNum("010-1111-1234")
                .publicAt(PublicAt.PUBLIC)
                .build();

        ProfileDto update = customerService.updateProfile(userDetails, updateRequest, image);

        Customer updateCustomer = customerRepository
                .findByIdWithProfile(userDetails.getUserId()).get();

        assertThat(update.getNickname())
                .isEqualTo(updateCustomer.getCustomerProfile().getNickname());
        assertThat(update.getImagePath())
                .isEqualTo(updateCustomer.getCustomerProfile().getImagePath());
    }

// 유저들 생성
    CustomerProfile createProfile(Customer customer) {
        return CustomerProfile.of(
                customer,
                "nickname",
                "man",
                "921223",
                "010-1111-1234",
                null
        );
    }

    Customer createUser() {
        return Customer.of(
                "sample@mail.com",
                passwordEncoder.encode("abc123"),
                Set.of(Role.ROLE_CUSTOMER)
        );
    }

    SignupRequest createSignup() {
        return SignupRequest.of(
                "sample@mail.com",
                "abc123",
                Set.of(Role.ROLE_CUSTOMER),
                "nickname",
                "man",
                "010-1111-1234",
                "921223"
        );
    }
}