package ec.com.saviasoft.air.security.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class EmailConfig {

    @Value("${saviasoft.email.host}")
    private String mailHost;

    @Value("${saviasoft.email.port}")
    private String mailPort;

    @Value("${saviasoft.email.username}")
    private String mailUsername;

    @Value("${saviasoft.email.password}")
    private String mailPassword;

    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost(mailHost);
        javaMailSender.setPort(Integer.parseInt(mailPort));
        javaMailSender.setUsername(mailUsername);
        javaMailSender.setPassword(mailPassword);

        Properties props = javaMailSender.getJavaMailProperties();
        props.put("mail.smtp.starttls.enable", "true");
        return javaMailSender;
    }
}
