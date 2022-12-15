package com.vegemil.service;

import lombok.AllArgsConstructor;

import java.util.UUID;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import com.vegemil.domain.AdminDTO;
import com.vegemil.domain.MailDTO;
import com.vegemil.domain.MemberDTO;
import com.vegemil.mapper.AdminMapper;
import com.vegemil.util.RedisUtil;

import javassist.NotFoundException;

@Service
@AllArgsConstructor
public class MailService {
    private JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;
    private final RedisUtil redisUtil;
    private static final String FROM_ADDRESS = "DCF 채용담당<dcfrecruit@vegemil.co.kr>";
    
    @Autowired
	private AdminMapper adminMapper;

    public void mailSend(MailDTO mailDto) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mailDto.getAddress());
        message.setFrom(MailService.FROM_ADDRESS);
        message.setSubject(mailDto.getTitle());
        message.setText(mailDto.getMessage());

        mailSender.send(message);
    }
    
    /**
     * 관리자 회원인증
     * @param adminDto
     */
    public void mailSend(AdminDTO adminDto) {
    	
    	try {
	    	MimeMessage message = mailSender.createMimeMessage();
	    	message.setFrom(MailService.FROM_ADDRESS);
			message.addRecipients(MimeMessage.RecipientType.TO, adminDto.getAId());
	        message.setSubject("관리자 회원인증");
	        message.setText(setAuthContext(adminDto), "utf-8", "html");
	        mailSender.send(message);
    	}catch(MessagingException e) {
    		e.printStackTrace();
    	}

    }
    
    public void sendActiveEmail(MemberDTO member) {
    	
        try {
        	
        	MimeMessage message = mailSender.createMimeMessage();
        	message.setFrom(MailService.FROM_ADDRESS);
			message.addRecipients(MimeMessage.RecipientType.TO, member.getEmailAddr());
			message.setSubject("[DCF]신규입사자 등록 인증 메일");
			message.setText(setAuthContext(member), "utf-8", "html"); 
	        mailSender.send(message);
	        
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
    }
    
    public void sendPwResetEmail(MemberDTO member) {
    	
        try {
        	
        	MimeMessage message = mailSender.createMimeMessage();
        	message.setFrom(MailService.FROM_ADDRESS);
			message.addRecipients(MimeMessage.RecipientType.TO, member.getEmailAddr());
			message.setSubject("[DCF]비밀번호 재설정 메일");
			message.setText(setPwContext(member), "utf-8", "html"); 
	        mailSender.send(message);
	        
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
    }
    
    private String setAuthContext(MemberDTO member) {
    	
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
    	return templateEngine.process("email/confirmMail", context);
        
    }
    
    public String setAuthContext(AdminDTO adminDto) {
    	Context context = new Context();
    	try {
			
            context.setVariable("member", adminDto);
    		
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return templateEngine.process("admin/email/confirmMail", context);
    }
    
    private String setPwContext(MemberDTO member) {
    	
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
    	return templateEngine.process("email/pwResetMail", context);
        
    }
    
    public String verifyEmail(String key) throws NotFoundException {
    	
        String memberEmail = redisUtil.getData(key);
        if(memberEmail==null) { 
        	throw new NotFoundException("유효하지 않은 링크입니다.");
        } 
        redisUtil.deleteData(key);
        
        return memberEmail;
    }
    
    /**
     * 관리자 로그인
     * @param adminDto
     * @return
     * @throws NotFoundException
     */
    public boolean verifyEmail(AdminDTO adminDto) throws NotFoundException {
    	int queryResult = adminMapper.selectAdminCount(adminDto);
    		
		if (queryResult != 0) {
			queryResult = adminMapper.activeAdmin(adminDto);
		}

		return (queryResult == 1) ? true : false;
        
    }
    
}