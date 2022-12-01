package com.vegemil.controller;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.Authentication;
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
public class MemberController extends UiUtils {

	@Autowired
	private MemberService memberService;
	
	@Autowired
	private MailService mailService;
	
	@GetMapping(value = "/member/recruitList")
	public String openMemberList(@ModelAttribute("params") MemberDTO params, Model model) {
		
		List<MemberDTO> memberList = memberService.getMemberList(params);
		int memberCount = memberService.getMemberCount(params);
		
		model.addAttribute("memberList", memberList);
		model.addAttribute("memberCount", memberCount);

		return "member/list";
	}
	
	@GetMapping(value = "/member/join")
	public String moveJoin(Model model) {
		
		model.addAttribute("member", new MemberDTO());

		return "member/join";
	}
	
	@GetMapping(value = "/member/emailSearch")
	public String moveEmailSearch(Model model) {
		
		model.addAttribute("member", new MemberDTO());

		return "member/emailSearch";
	}
	
	@GetMapping(value = "/member/passwordSearch")
	public String movePasswordSearch(Model model) {
		
		model.addAttribute("member", new MemberDTO());

		return "member/passwordSearch";
	}
	
	@PostMapping(value = "/member/register")
	public String registerMember(
			@ModelAttribute("member") final @Valid MemberDTO member,
			Model model, HttpServletResponse response, HttpServletRequest request) throws Exception {
		
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		try {
			
			boolean isRegistered = memberService.registerMember(member);
			if (isRegistered == false) {
				out.println("<script>alert('이미 등록된 이메일입니다.'); history.go(-1);</script>");
				out.flush();
			}
			mailService.sendActiveEmail(member);
			model.addAttribute("member", member);
			
		} catch (DataAccessException e) {
			out.println("<script>alert('데이터베이스 처리 과정에 문제가 발생하였습니다.'); history.back();</script>");
			out.flush();
			return showMessageWithRedirect("데이터베이스 처리 과정에 문제가 발생하였습니다.", "/member/list", Method.GET, null, model);

		} catch (Exception e) {
			out.println("<script>alert('시스템에 문제가 발생하였습니다.'); history.go(-1);</script>");
			out.flush();
			return showMessageWithRedirect("시스템에 문제가 발생하였습니다.", "/member/list", Method.GET, null, model);
		}

		return "member/joinConfirm";
	}
	
	@PostMapping(value = "/member/resetPassword")
	public String resetPassword(
			@ModelAttribute("member") MemberDTO member,
			Model model, HttpServletResponse response, HttpServletRequest request) throws Exception {
		
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		int memCnt;
		String mEmail = "";
		
		try {
			
			memCnt = memberService.isMemberCount(member);
			//해당 회원 없음
			if(memCnt == 0) {
				out.println("<script>alert('비밀번호 재설정에 실패하였습니다.'); history.go(-1);</script>");
				out.flush();
			} else {
				boolean isRegistered = memberService.resetPassword(member);
				if (isRegistered == false) {
					out.println("<script>alert('비밀번호 재설정에 실패하였습니다.'); history.go(-1);</script>");
					out.flush();
				}
			}
			
		} catch (DataAccessException e) {
			out.println("<script>alert('데이터베이스 처리 과정에 문제가 발생하였습니다.'); history.back();</script>");
			out.flush();
			return showMessageWithRedirect("데이터베이스 처리 과정에 문제가 발생하였습니다.", "/member/list", Method.GET, null, model);

		} catch (Exception e) {
			out.println("<script>alert('시스템에 문제가 발생하였습니다.'); history.go(-1);</script>");
			out.flush();
			return showMessageWithRedirect("시스템에 문제가 발생하였습니다.", "/member/list", Method.GET, null, model);
		}

		return "member/pwResetResult";
	}
	
	@GetMapping(value = "/member/login")
	public String moveLogin(Model model, Authentication authentication) {
		
		if(authentication != null)
    	return showMessageWithRedirect("이미 로그인 상태입니다.", "/", Method.GET, null, model);
		
		return "member/login";
	}
	
	
	@PostMapping(value = "/member/searchEmail")
	public String searchEmail(
			@ModelAttribute("member") MemberDTO member,
			Model model, HttpServletResponse response, HttpServletRequest request) throws Exception {
		
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		String emailAddr = "";
		
		try {
			
			emailAddr = memberService.searchEmail(member);
			model.addAttribute("emailAddr", emailAddr);
			
		} catch (Exception e) {
			out.println("<script>alert('시스템에 문제가 발생하였습니다.'); history.go(-1);</script>");
			out.flush();
			return showMessageWithRedirect("시스템에 문제가 발생하였습니다.", "/home", Method.GET, null, model);
		}

		return "member/idSearchResult";
	}
	
	
}
