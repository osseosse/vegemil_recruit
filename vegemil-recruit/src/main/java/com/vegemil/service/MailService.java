package com.vegemil.service;

import lombok.AllArgsConstructor;

import java.util.UUID;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import com.vegemil.domain.MailDTO;
import com.vegemil.domain.MemberDTO;
import com.vegemil.util.RedisUtil;

import javassist.NotFoundException;

@Service
@AllArgsConstructor
public class MailService {
    private JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;
    private final RedisUtil redisUtil;
    private static final String FROM_ADDRESS = "recruit@vegemil.co.kr";

    public void mailSend(MailDTO mailDto) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mailDto.getAddress());
        message.setFrom(MailService.FROM_ADDRESS);
        message.setSubject(mailDto.getTitle());
        message.setText(mailDto.getMessage());

        mailSender.send(message);
    }
    
    public void sendActiveEmail(MemberDTO member) {
    	
        try {
        	
        	MimeMessage message = mailSender.createMimeMessage();
        	
			message.addRecipients(MimeMessage.RecipientType.TO, member.getEmailAddr());
			 message.setSubject("[정식품]신규입사자 등록 인증 메일");
			message.setText(setContext(member), "utf-8", "html"); 
	        mailSender.send(message);
	        
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
    }
    
    private String setContext(MemberDTO member) {
    	
    	Context context = new Context();
    	try {
			
    		UUID uuid = UUID.randomUUID();
            // redis에 링크 정보 저장
            redisUtil.setDataExpire(uuid.toString(),member.getEmailAddr(), 7);
            context.setVariable("member", member);
            context.setVariable("authToken", uuid.toString());
    		
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return templateEngine.process("member/confirmMail", context);
        
    }
    
    public String verifyEmail(String key) throws NotFoundException {
        String memberEmail = redisUtil.getData(key);
        if(memberEmail==null) throw new NotFoundException("유효하지 않은 링크입니다.");
        redisUtil.deleteData(key);
        
        return memberEmail;
    }
    
}