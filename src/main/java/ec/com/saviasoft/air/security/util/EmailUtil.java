package ec.com.saviasoft.air.security.util;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

@Component
public class EmailUtil {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private SpringTemplateEngine templateEngine;

    public void sendForgotPasswordEmail(String email, String token, String frontEndUrl) throws MessagingException {

        //Thymeleaf template, see https://www.thymeleaf.org/doc/tutorials/3.0/usingthymeleaf.html#template-layout

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);

        Context context = new Context();
        context.setVariable("token", token);
        context.setVariable("email", email);
        context.setVariable("frontEndUrl", frontEndUrl);

        String html = templateEngine.process("user/resetPassword", context);

        mimeMessageHelper.setTo(email);
        mimeMessageHelper.setSubject("Recuperar contraseña");
        mimeMessageHelper.setText(html, true);

        javaMailSender.send(mimeMessage);
    }

    public void sendUserCredentials(String email, String password, String frontEndUrl) throws MessagingException {

        //Thymeleaf template, see https://www.thymeleaf.org/doc/tutorials/3.0/usingthymeleaf.html#template-layout

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);

        Context context = new Context();
        context.setVariable("password", password);
        context.setVariable("email", email);
        context.setVariable("frontEndUrl", frontEndUrl);

        String html = templateEngine.process("user/welcome", context);

        mimeMessageHelper.setTo(email);
        mimeMessageHelper.setSubject("Bienvenido a AirQualityApp");
        mimeMessageHelper.setText(html, true);

        javaMailSender.send(mimeMessage);
    }
}
