package playfit.se.members.services.impl;

import jakarta.mail.internet.InternetAddress;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import playfit.se.members.services.EmailService;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    @Value("${SMTP_USER}")
    private String fromEmail;
    private final JavaMailSender javaMailSender;

    @Override
    public String sendSimpleEmail(String to, String subject, String content) {
        try {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setFrom(String.valueOf(new InternetAddress(fromEmail, "Play Fit")));
            simpleMailMessage.setTo(to);
            simpleMailMessage.setSubject(subject);
            simpleMailMessage.setText(content);
            javaMailSender.send(simpleMailMessage);
            return "The email has been sent successfully";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String sendMultipleSimpleEmail(List<String> toList, String subject, String content) {
        try {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setFrom(String.valueOf(new InternetAddress(fromEmail, "Play Fit")));
            for (String to : toList) {
                simpleMailMessage.setTo(to);
                simpleMailMessage.setSubject(subject);
                simpleMailMessage.setText(content);
                javaMailSender.send(simpleMailMessage);
            }
            return "The emails have been sent successfully";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String sendActivationEmail(String to, String activationLink) {
        return "";
    }

    @Override
    public String sendEmailWithAttachment(String to, String subject, String content, byte[] attachment) {
        return "";
    }
}
