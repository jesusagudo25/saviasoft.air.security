package ec.com.saviasoft.air.security.util;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class EmailUtil {

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendForgotPasswordEmail(String email, String token) throws MessagingException {

        //Thymeleaf template, see https://www.thymeleaf.org/doc/tutorials/3.0/usingthymeleaf.html#template-layout

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
        mimeMessageHelper.setTo(email);
        mimeMessageHelper.setSubject("Reset Password");
        mimeMessageHelper.setText("""
            <div>
              <a href="http://localhost:3030/reset-password/%s" target="_blank">click link to reset password</a>
            </div>
            """.formatted(token), true);

        javaMailSender.send(mimeMessage);
    }
}
