package com.vegemil.controller;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.vegemil.constant.Method;
import com.vegemil.domain.MemberDTO;
import com.vegemil.service.MemberService;
import com.vegemil.service.MailService;
import com.vegemil.util.UiUtils;

@Controller
public class EmailController extends UiUtils {

	@Autowired
	private MemberService memberService;
	
	@Autowired
	private MailService mailService;
	
	@PostMapping("/email/sendAuthEmail")
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
	
}
