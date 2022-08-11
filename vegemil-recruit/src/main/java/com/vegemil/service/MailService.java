package com.vegemil.service;

import lombok.AllArgsConstructor;

import javax.mail.internet.MimeMessage;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import com.vegemil.domain.MailDTO;
import com.vegemil.domain.MemberDTO;

@Service
@AllArgsConstructor
public class MailService {
    private JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;
    private static final String FROM_ADDRESS = "recruit@vegemil.co.kr";

    public void mailSend(MailDTO mailDto) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mailDto.getAddress());
        message.setFrom(MailService.FROM_ADDRESS);
        message.setSubject(mailDto.getTitle());
        message.setText(mailDto.getMessage());

        mailSender.send(message);
    }
    
    public void sendActiveEmail(MemberDTO member) throws Exception {
        MimeMessage message = mailSender.createMimeMessage();
        message.addRecipients(MimeMessage.RecipientType.TO, member.getEmailAddr());
        message.setText(setContext(member), "utf-8", "html"); 
        mailSender.send(message);
    }
    
    private String setContext(MemberDTO member) {
        Context context = new Context();
        context.setVariable("member", member);
        return templateEngine.process("/member/confirmMail", context);
    }
    
}