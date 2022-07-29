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
	
	@GetMapping(value = "/application/recruitList")
	public String openBabyInfoList(@ModelAttribute("params") RecruitDTO params, Model model) {
		
		List<RecruitDTO> recruitList = RecruitService.getRecruitList(params);
		int recruitCount = RecruitService.getRecruitCount(params);
		
		model.addAttribute("recruitList", recruitList);
		model.addAttribute("recruitCount", recruitCount);

		return "application/list";
	}
	
	
	@GetMapping(value = "/application/detail")
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

		return "application/detail";
	}
	
	@GetMapping(value = "/application/join")
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

		return "application/join";
	}
	
	@PostMapping(value = "/application/register")
	public String registerBabyInfo(
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

		return "application/write01";
	}
	
	/*
	@PostMapping(value = "/admin/baby/babyInfoUpdate")
	public String updateBabyInfo(@ModelAttribute("params") final RecruitDTO params, @RequestParam("fileName") MultipartFile fileName, Model model, HttpServletResponse response, HttpServletRequest request) throws Exception {
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		try {
				if(!fileName.getOriginalFilename().equals("")) {
					String originalName = fileName.getOriginalFilename();
					String file = originalName.substring(originalName.lastIndexOf("\\") + 1);
					String uuid = UUID.randomUUID().toString();
					String savefileName = uuid + "_" + file;
					Path savePath = Paths.get(request.getSession().getServletContext().getRealPath("/..") + "/WEB-INF/classes/static/upload/admin/babyInfo/" + savefileName);
					
					fileName.transferTo(savePath);
					params.setMbsImage(savefileName);
				}
			boolean isRegistered = ApplicationService.registerBabyInfo(params);
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
	
	
	@GetMapping(value = "/admin/baby/babyInfoActive")
	public void changeActive(@RequestParam(value = "mbsIdx", required = false) Long mbsIdx, @RequestParam(value = "display", required = false) int display, HttpServletResponse response) throws Exception {
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		if (mbsIdx == null) {
			out.println("<script>alert('올바르지 않은 접근입니다.'); history.go(-1);</script>");
			out.flush();
		}
		RecruitDTO babyInfo = ApplicationService.getBabyInfoDetail(mbsIdx);
		babyInfo.setMbsIdx(mbsIdx);
		babyInfo.setMbsActive(display);
		boolean isRegistered = ApplicationService.registerBabyInfo(babyInfo);
		if (isRegistered == false) {
			out.println("<script>alert('육아정보 진열 변경에 실패하였습니다.'); window.location='/admin/baby/babyInfoList';</script>");
			out.flush();
		}
	}
	
	@GetMapping(value = "/admin/baby/babyInfoCheck")
	public void changeCheck(@RequestParam(value = "mbsIdx", required = false) Long mbsIdx, @RequestParam(value = "display", required = false) int display, HttpServletResponse response) throws Exception {
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		if (mbsIdx == null) {
			out.println("<script>alert('올바르지 않은 접근입니다.'); history.go(-1);</script>");
			out.flush();
		}
		RecruitDTO babyInfo = ApplicationService.getBabyInfoDetail(mbsIdx);
		babyInfo.setMbsIdx(mbsIdx);
		babyInfo.setMbsCheck(display);
		boolean isRegistered = ApplicationService.registerBabyInfo(babyInfo);
		if (isRegistered == false) {
			out.println("<script>alert('메인화면 진열 변경에 실패하였습니다.'); window.location='/admin/baby/babyInfoList';</script>");
			out.flush();
		}
	}
	*/
	
}
