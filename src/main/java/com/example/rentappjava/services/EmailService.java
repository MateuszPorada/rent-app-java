package com.example.rentappjava.services;

import com.example.rentappjava.entities.User;
import lombok.AllArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
class EmailService {

    private final JavaMailSender mailSender;

    @Async
    void sendEmail(String recipient, String subject, String body) {
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom("rentapp@email.com");
            messageHelper.setTo(recipient);
            messageHelper.setSubject(subject);
            messageHelper.setText(body);
        };
        mailSender.send(messagePreparator);
    }

    @Async
    void sendRegistrationEmail(String path, User user, String token) {
        sendEmail(user.getEmail(), "Registration in rent app",
                "Thank you for signing up in rent app, please verify your email: " + path + "/auth/verify/" + token);
    }
}