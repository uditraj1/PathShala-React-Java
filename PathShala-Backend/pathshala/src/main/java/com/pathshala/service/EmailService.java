package com.pathshala.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
@AllArgsConstructor
public class EmailService {

    private JavaMailSender mailSender;
    private final TemplateEngine templateEngine;



    public void sendEmail(String to, String name, String password) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");
        Context context = new Context();
        context.setVariable("name", name);
        context.setVariable("email", to);
        context.setVariable("password", password);
        try {
            helper.setTo(to);
            helper.setSubject("Welcome to Pathshala!!");
            String htmlContent = templateEngine.process("password-email.html", context);
            helper.setText(htmlContent, true);
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            // Handle exception
        }
    }
}
