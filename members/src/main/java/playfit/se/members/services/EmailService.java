package playfit.se.members.services;

import java.util.List;

public interface EmailService {

    String sendSimpleEmail(String to, String subject, String content);

    String sendMultipleSimpleEmail(List<String> toList, String subject, String content);

    String sendActivationEmail(String to, String activationLink);

    String sendEmailWithAttachment(String to, String subject, String content, byte[] attachment);

}

