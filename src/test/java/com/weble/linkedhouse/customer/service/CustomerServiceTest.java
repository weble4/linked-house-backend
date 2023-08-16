package com.weble.linkedhouse.customer.service;

import com.weble.linkedhouse.customer.config.TestConfig;
import com.weble.linkedhouse.customer.dtos.ProfileDto;
import com.weble.linkedhouse.customer.dtos.request.LoginRequest;
import com.weble.linkedhouse.customer.dtos.request.PasswordFindRequest;
import com.weble.linkedhouse.customer.dtos.request.SignupRequest;
import com.weble.linkedhouse.customer.dtos.response.LoginResponse;
import com.weble.linkedhouse.customer.dtos.response.SignupResponse;
import com.weble.linkedhouse.customer.entity.Customer;
import com.weble.linkedhouse.customer.entity.CustomerProfile;
import com.weble.linkedhouse.customer.entity.constant.AuthState;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Transactional
@SpringBootTest
@Import(TestConfig.class)
@TestPropertySource(properties = {
        "spring.mail.username=dummy",
        "srping.mail.password=dummypassword",
        "jwt.secret_key=testKeyNotRealKeyJustTestKeyAndKeyIsNeedToLength",
})
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
        Customer customer = customerRepository.findByCustomerEmail("sameple@mail.com")
                .orElseThrow(NotExistCustomer::new);

        //after then
        customerService.certifiedEmail(customer.getCustomerId());
        assertThat(customer.getAuthState()).isEqualTo(AuthState.AUTH);
    }

    @Test
    @DisplayName("로그인 기능 로직 체크 - 이메일 미인증으로 인한 로그인 실패")
    void loginFailTest() {
        //given
        SignupResponse signupResponse = customerService.saveUser(createSignup());
        LoginRequest loginRequest = new LoginRequest(signupResponse.getCustomerEmail(), "abc123");

        //expected
        assertThatThrownBy(() -> customerService.login(loginRequest))
                .isInstanceOf(Unauthorized.class);
    }

    @Test
    @DisplayName("로그인 기능 로직 체크 - 로그인 성공")
    void loginSuccessTest() {
        //given
        SignupResponse signupResponse = customerService.saveUser(createSignup());
        Customer customer = customerRepository.findByCustomerEmail(signupResponse.getCustomerEmail()).get();
        customerService.certifiedEmail(customer.getCustomerId());
        LoginRequest loginRequest = new LoginRequest(signupResponse.getCustomerEmail(), "abc123");

        //when
        LoginResponse login = customerService.login(loginRequest);

        //then
        assertThat(login).isNotNull()
                .hasFieldOrPropertyWithValue("customerId", customer.getCustomerId())
                .hasFieldOrPropertyWithValue("customerEmail", "sample@mail.com")
                .hasFieldOrPropertyWithValue("nickname", "nickname")
                .hasFieldOrProperty("token");
    }

    @Test
    @DisplayName("호스트 요청 테스트")
    void hasRoleHost() {
        //given
        SignupResponse signupResponse = customerService.saveUser(createSignup());
        CustomerProfile profile = profileRepository.findByCustomerCustomerEmail(signupResponse.getCustomerEmail())
                .orElseThrow(NotExistCustomer::new);
        UserDetailsImpl userDetails = new UserDetailsImpl(ProfileDto.from(profile)); // other necessary details

        //when
        customerService.applyHost(userDetails);

        //then
        Customer updatedCustomer = customerRepository.findByCustomerEmail(signupResponse.getCustomerEmail())
                .orElseThrow(NotExistCustomer::new);
        assertThat(updatedCustomer.getRole()).contains(Role.HOST);
    }

    @Test
    @DisplayName("비밀번호 변경 테스트")
    void changePasswordTest () {
        //given
        SignupResponse signupResponse = customerService.saveUser(createSignup());
        Customer customer = customerRepository.findByCustomerEmail(signupResponse.getCustomerEmail())
                .orElseThrow(NotExistCustomer::new);
        PasswordFindRequest passwordFindRequest = new PasswordFindRequest(customer.getCustomerEmail(), "changePassword");

        //when
        customerService.findPassword(passwordFindRequest);
        Customer updateCustomer = customerRepository.findByCustomerEmail(customer.getCustomerEmail()).get();

        //then
        assertThat(passwordEncoder.matches("changePassword", updateCustomer.getCustomerPw()))
                .isTrue();

    }

// 유저들 생성
    CustomerProfile createProfile(Customer customer) {
        return CustomerProfile.of(
                customer,
                "nick",
                "man",
                "921223",
                "010-1111-1234",
                null
        );
    }

    Customer createUser() {
        return Customer.of(
                "sameple@mail.com",
                passwordEncoder.encode("abc123"),
                Set.of(Role.CUSTOMER)
        );
    }

    SignupRequest createSignup() {
        return SignupRequest.of(
                "sample@mail.com",
                "abc123",
                Set.of(Role.CUSTOMER),
                "nickname",
                "man",
                "010-1111-1234",
                "921223"
        );
    }
}