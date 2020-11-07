package com.alican.service.impl;

import com.alican.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * @author ahatun
 */
@Service
public class EmailServiceImpl implements EmailService {


    @Value("${email.to}")
    private String to;

    @Value("${email.from}")
    private String from;

    @Value("${email.content}")
    private String content;

    @Value("${email.subject}")
    private String subject;

    private final JavaMailSender emailSender;

    public EmailServiceImpl(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    @Override
    public void sendSimpleMail() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject(subject);
        message.setText(content);
        message.setTo(to);
        message.setFrom(from);
        emailSender.send(message);
    }
}
