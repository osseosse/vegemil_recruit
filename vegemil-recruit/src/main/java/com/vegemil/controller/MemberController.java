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
import org.springframework.web.bind.annotation.RequestParam;

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
	
	@GetMapping(value = "/member/login")
	public String moveLogin(Model model, Authentication authentication) {
		
		if(authentication != null)
    	return showMessageWithRedirect("이미 로그인 상태입니다.", "/", Method.GET, null, model);
		
		return "member/login";
	}
	
	@GetMapping(value = "/member/detail")
	public String openMemberDetail(@ModelAttribute("params") MemberDTO params, @RequestParam(value = "sTh", required = false) String emailAddr, Model model, HttpServletResponse response) throws Exception {
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		if (emailAddr == null) {
			out.println("<script>alert('올바르지 않은 접근입니다.'); history.go(-1);</script>");
			out.flush();
			return showMessageWithRedirect("올바르지 않은 접근입니다.", "member/memberList", Method.GET, null, model);
		}
		MemberDTO member = memberService.loadUserByUsername(emailAddr);
		if (member == null) {
			out.println("<script>alert('이미 종료된 채용입니다.'); history.go(-1);</script>");
			out.flush();
			return showMessageWithRedirect("이미 종료된 채용입니다.", "member/memberList", Method.GET, null, model);
		}
		model.addAttribute("member", member);

		return "member/detail";
	}
	
	@PostMapping(value = "/member/infoUpdate")
	public String updateMemberInfo(@ModelAttribute("params") final MemberDTO params, Model model, HttpServletResponse response, HttpServletRequest request) throws Exception {
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		try {
			boolean isRegistered = memberService.registerMember(params);
			if (isRegistered == false) {
				out.println("<script>alert('게시글 수정에 실패하였습니다.'); history.go(-1);</script>");
				out.flush();
			}
		} catch (DataAccessException e) {
			out.println("<script>alert('데이터베이스 처리 과정에 문제가 발생하였습니다.'); history.go(-1);</script>");
			out.flush();
			return showMessageWithRedirect("데이터베이스 처리 과정에 문제가 발생하였습니다.", "/admin/baby/babyInfoList", Method.GET, null, model);

		} catch (Exception e) {
			out.println("<script>alert('시스템에 문제가 발생하였습니다.'); history.go(-1);</script>");
			out.flush();
			return showMessageWithRedirect("시스템에 문제가 발생하였습니다.", "/admin/baby/babyInfoList", Method.GET, null, model);
		}
		
		out.println("<script>alert('게시글 수정이 완료되었습니다.'); window.location='/admin/baby/babyInfoList';</script>");
		out.flush();

		return showMessageWithRedirect("게시글 수정이 완료되었습니다.", "/admin/baby/babyInfoList", Method.GET, null, model);
	}
	
	@GetMapping(value = "/member/infoCheck")
	public void changeCheck(@RequestParam(value = "memId", required = false) String emailAddr, @RequestParam(value = "active", required = false) int active, HttpServletResponse response) throws Exception {
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		if (emailAddr == null) {
			out.println("<script>alert('올바르지 않은 접근입니다.'); history.go(-1);</script>");
			out.flush();
		}
		MemberDTO member = memberService.loadUserByUsername(emailAddr);
		member.setEmailAddr(emailAddr);
		member.setActive(active);
		boolean isRegistered = memberService.registerMember(member);
		if (isRegistered == false) {
			out.println("<script>alert('메인화면 진열 변경에 실패하였습니다.'); window.location='/admin/baby/babyInfoList';</script>");
			out.flush();
		}
	}
	
}
