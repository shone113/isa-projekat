package rs.ac.uns.ftn.informatika.rest.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class MailConfig {

    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        // Podešavanja za SMTP server
        mailSender.setHost("smtp.gmail.com");  // Ovde staviš SMTP server koji koristiš
        mailSender.setPort(587);

        mailSender.setUsername("your-email@gmail.com");  // Tvoj email
        mailSender.setPassword("your-email-password");  // Tvoja lozinka (ili specifičan app password ako koristiš Gmail)

        // Dodatne opcije za SMTP
        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");

        return mailSender;
    }
}

