package com.weble.linkedhouse.customer.service;

import com.weble.linkedhouse.customer.dtos.request.SignupRequest;
import com.weble.linkedhouse.customer.dtos.response.SignupResponse;
import com.weble.linkedhouse.customer.entity.Customer;
import com.weble.linkedhouse.customer.entity.CustomerProfile;
import com.weble.linkedhouse.customer.entity.constant.AuthState;
import com.weble.linkedhouse.customer.repository.CustomerRepository;
import com.weble.linkedhouse.customer.repository.ProfileRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CustomerService {

    private final JavaMailSender javaMailsender;
    private final CustomerRepository customerRepository;
    private final ProfileRepository profileRepository;

    @Value("${spring.mail.username}")
    private String emailSender;

    @Transactional
    public SignupResponse saveUser(SignupRequest signupRequest) {

         if(customerRepository.findByCustomerEmail(signupRequest.getCustomerEmail()).isPresent()){
             //TODO: Exception 클래스들 만들고 수정 예정
             throw new IllegalArgumentException();
         }
        Customer customer = customerRepository.save(signupRequest.convertCustomer());
        CustomerProfile profile = profileRepository.save(signupRequest.convertProfile(customer));

        SendCheckMail(customer);

        return new SignupResponse(customer.getCustomerEmail());
    }

    @Transactional
    public void activateAccount(Long customerId) {
        Customer customer = customerRepository.findById(customerId)
                //TODO: Exception 클래스들 만들고 수정 예정
                .orElseThrow(NullPointerException::new);

        customer.ApproveAuth(AuthState.AUTH);
    }


    public void withdrawl() {
        // 동적 SQL로 테이블 생성 후 해야됨.
    }


    private void SendCheckMail(Customer customer) {

        MimeMessage message = javaMailsender.createMimeMessage();

        try{
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

}
