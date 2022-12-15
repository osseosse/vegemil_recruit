package com.vegemil.controller;

import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.JsonObject;
import com.vegemil.constant.Method;
import com.vegemil.domain.MemberDTO;
import com.vegemil.domain.PersonalInfoDTO;
import com.vegemil.service.MemberService;
import com.vegemil.service.MailService;
import com.vegemil.util.UiUtils;

@Controller
public class EmailController extends UiUtils {

	@Autowired
	private MemberService memberService;
	
	@Autowired
	private MailService mailService;
	
	@RequestMapping(value = { "/email/sendAuthEmail" }, method = { RequestMethod.POST, RequestMethod.PATCH })
	public void execAuthmail(MemberDTO member, HttpServletResponse response) throws Exception {
		
		PrintWriter out = response.getWriter();
		
        try {
        	
        	if (member == null) {
    			out.println("<script>alert('올바르지 않은 접근입니다.'); history.go(-1);</script>");
    			out.flush();
    		}
        	//멤버를 세션에서 가져와야한다.
			mailService.sendActiveEmail(member);
			
		} catch (Exception e) {
			e.printStackTrace();
			out.println("<script>alert('시스템에 문제가 발생하였습니다.'); history.go(-1);</script>");
			out.flush();
		}
        
    }
	
	@RequestMapping(value = { "/email/sendAuthEmailJson" }, method = { RequestMethod.POST})
	public @ResponseBody JsonObject execAuthmailJson(MemberDTO member, HttpServletResponse response) throws Exception {
		
		JsonObject jsonObj = new JsonObject();
		
        try {
        	
        	if (member == null) {
        		jsonObj.addProperty("message", "시스템에 문제가 발생하였습니다.");
    		}
        	//멤버를 세션에서 가져와야한다.
        	if("ADMIN".equals(member.getAuth())) {
        		mailService.adminMailSend(member);
        	}else {
        		mailService.sendActiveEmail(member);
        	}
			
			
			jsonObj.addProperty("result", true);
			
        } catch (DataAccessException e) {
			jsonObj.addProperty("message", "데이터베이스 처리 과정에 문제가 발생하였습니다.");

		} catch (Exception e) {
			jsonObj.addProperty("message", "시스템에 문제가 발생하였습니다.");
		}
        
        return jsonObj;
        
    }
	
	//회원 활성화
	@GetMapping(value = "/email/active")
	public String changeActive(@ModelAttribute("authToken") final String authToken, Model model, HttpServletResponse response) throws Exception {
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		MemberDTO member = null;
		if (authToken == null) {
			out.println("<script>alert('올바르지 않은 접근입니다.'); history.go(-1);</script>");
			out.flush();
		}
		String emailAddr = mailService.verifyEmail(authToken);
		if (emailAddr == null) {
			out.println("<script>alert('올바르지 않은 접근입니다.'); history.go(-1);</script>");
			out.flush();
		}
		member = new MemberDTO();
		member.setEmailAddr(emailAddr);
		boolean isRegistered = memberService.activeMember(member);
		if (isRegistered == false) {
			out.println("<script>alert('이메일인증에 실패하였습니다.'); window.location='/';</script>");
			out.flush();
		}
		
		return showMessageWithRedirect("이메일 인증이 완료되었습니다.", "/member/login", Method.GET, null, model);
	}
	
	@RequestMapping(value = { "/email/sendPwResetEmail" }, method = { RequestMethod.POST })
	public @ResponseBody JsonObject sendPwResetEmail(MemberDTO member,
														HttpServletResponse response, HttpServletRequest request) {

		JsonObject jsonObj = new JsonObject();

		try {
			
			if(member != null) {
		        
		        int mamberCount = memberService.getMemberCountByEmail(member);
		        if(mamberCount == 1) {
		        	//비밀번호 재설정 메일 발송 
		        	mailService.sendPwResetEmail(member);
		        	jsonObj.addProperty("result", true);
		        } else {
		        	jsonObj.addProperty("result", false);
		        }
				
			}
			
		} catch (DataAccessException e) {
			jsonObj.addProperty("message", "데이터베이스 처리 과정에 문제가 발생하였습니다.");

		} catch (Exception e) {
			jsonObj.addProperty("message", "시스템에 문제가 발생하였습니다.");
		}

		return jsonObj;
	}
	
	@GetMapping(value = "/email/pwReset")
	public String pwReset(@ModelAttribute("authToken") final String authToken, Model model, HttpServletResponse response) throws Exception {
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		MemberDTO member = null;
		if (authToken == null) {
			out.println("<script>alert('올바르지 않은 접근입니다.'); history.go(-1);</script>");
			out.flush();
		}
		String emailAddr = mailService.verifyEmail(authToken);
		if (emailAddr == null) {
			out.println("<script>alert('올바르지 않은 접근입니다.'); history.go(-1);</script>");
			out.flush();
		}
		member = new MemberDTO();
		member.setEmailAddr(emailAddr);
		model.addAttribute("member", member);
		
		return "member/passwordReset";
	}
	
}
