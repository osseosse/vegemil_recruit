package com.vegemil.controller;

import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.vegemil.constant.Method;
import com.vegemil.domain.MemberDTO;
import com.vegemil.domain.RecruitDTO;
import com.vegemil.service.RecruitService;
import com.vegemil.util.UiUtils;

@Controller
public class RecruitController extends UiUtils {

	@Autowired
	private RecruitService RecruitService;
	
	@GetMapping(value = "/recruit/list")
	public String openRecruitList(@ModelAttribute("params") RecruitDTO params, Model model) {
		
		List<RecruitDTO> recruitList = RecruitService.getRecruitList();
		int recruitCount = RecruitService.getRecruitCount();
		
		model.addAttribute("recruitList", recruitList);
		model.addAttribute("recruitCount", recruitCount);

		return "recruit/list";
	}
	
	
	@GetMapping(value = "/recruit/detail")
	public String openRecruitDetail(@ModelAttribute("params") RecruitDTO params, @RequestParam(value = "sTh", required = false) Long sTh, Model model, HttpServletResponse response) throws Exception {
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		if (sTh == null) {
			out.println("<script>alert('올바르지 않은 접근입니다.'); history.go(-1);</script>");
			out.flush();
			return showMessageWithRedirect("올바르지 않은 접근입니다.", "application/recruitList", Method.GET, null, model);
		}
		RecruitDTO recruit = RecruitService.getRecruitDetail(sTh);
		if (recruit == null) {
			out.println("<script>alert('이미 종료된 채용입니다.'); history.go(-1);</script>");
			out.flush();
			return showMessageWithRedirect("이미 종료된 채용입니다.", "application/recruitList", Method.GET, null, model);
		}
		recruit.setSTh(sTh);
		model.addAttribute("recruit", recruit);

		return "recruit/detail";
	}
	
	@GetMapping(value = "/recruit/join")
	public String openRecruitJoin(@ModelAttribute("params") RecruitDTO params, @RequestParam(value = "sTh", required = false) Long sTh, Model model, HttpServletResponse response) throws Exception {
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		if (sTh == null) {
			out.println("<script>alert('올바르지 않은 접근입니다.'); history.back();</script>");
			out.flush();
			return showMessageWithRedirect("올바르지 않은 접근입니다.", "application/recruitList", Method.GET, null, model);
		}
		RecruitDTO recruit = RecruitService.getRecruitDetail(sTh);
		if (recruit == null) {
			out.println("<script>alert('이미 종료된 채용입니다.'); history.back();</script>");
			out.flush();
			return showMessageWithRedirect("이미 종료된 채용입니다.", "application/recruitList", Method.GET, null, model);
		}
		//recruit.setSTh(sTh);
		model.addAttribute("recruit", recruit);
		model.addAttribute("sTh", sTh);

		return "recruit/join";
	}
	
	@PostMapping(value = "/recruit/register")
	public String registerRecruit(
			@ModelAttribute("member") final MemberDTO memberDTO, @RequestParam(value = "sTh", required = false) Long sTh,
			Model model, HttpServletResponse response, HttpServletRequest request) throws Exception {
		
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		try {
			
			if (sTh == null) {
				out.println("<script>alert('올바르지 않은 접근입니다.'); history.go(-1);</script>");
				out.flush();
				return showMessageWithRedirect("올바르지 않은 접근입니다.", "application/recruitList", Method.GET, null, model);
			}
			RecruitDTO recruit = RecruitService.getRecruitDetail(sTh);
			if (recruit == null) {
				out.println("<script>alert('이미 종료된 채용입니다.'); history.go(-1);</script>");
				out.flush();
				return showMessageWithRedirect("이미 종료된 채용입니다.", "application/recruitList", Method.GET, null, model);
			}
			model.addAttribute("recruit", recruit);
			
		} catch (DataAccessException e) {
			out.println("<script>alert('데이터베이스 처리 과정에 문제가 발생하였습니다.'); history.back();</script>");
			out.flush();
			return showMessageWithRedirect("데이터베이스 처리 과정에 문제가 발생하였습니다.", "/application/list", Method.GET, null, model);

		} catch (Exception e) {
			out.println("<script>alert('시스템에 문제가 발생하였습니다.'); history.go(-1);</script>");
			out.flush();
			return showMessageWithRedirect("시스템에 문제가 발생하였습니다.", "/application/list", Method.GET, null, model);
		}

		return showMessageWithRedirect("시스템에 문제가 발생하였습니다.", "application/registerPersonalInfo", Method.GET, null, model);
	}
	
}
