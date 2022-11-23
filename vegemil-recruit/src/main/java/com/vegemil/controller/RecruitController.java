package com.vegemil.controller;

import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.vegemil.adapter.GsonLocalDateTimeAdapter;
import com.vegemil.constant.Method;
import com.vegemil.domain.MemberDTO;
import com.vegemil.domain.PersonalInfoDTO;
import com.vegemil.domain.RecruitDTO;
import com.vegemil.service.ApplicationService;
import com.vegemil.service.RecruitService;
import com.vegemil.util.UiUtils;

@Controller
public class RecruitController extends UiUtils {

	@Autowired
	private RecruitService RecruitService;
	
	@Autowired
	private ApplicationService applicationService;
	
	@GetMapping(value = "/recruit/list")
	public String openRecruitList(@ModelAttribute("params") RecruitDTO params, Model model) {
		
		List<RecruitDTO> recruitList = RecruitService.getRecruitList();
		int recruitCount = RecruitService.getRecruitCount();
		
		model.addAttribute("recruitList", recruitList);
		model.addAttribute("recruitCount", recruitCount);

		return "recruit/list";
	}
	
	@GetMapping(value = "/recruit/majorList")
	public @ResponseBody JsonObject getMajorList(@RequestParam(value = "majorName", required = false) String majorName, Model model) {
		
		JsonObject jsonObj = new JsonObject();
		List<String> majorList = RecruitService.getMajorList(majorName);
		if (CollectionUtils.isEmpty(majorList) == false) {
			Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new GsonLocalDateTimeAdapter()).create();
			JsonArray jsonArr = gson.toJsonTree(majorList).getAsJsonArray();
			jsonObj.add("majorList", jsonArr);
		}

		return jsonObj;
	}
	
	@GetMapping(value = "/info/info")
    public String moveInfo(Model model, HttpServletResponse response) throws Exception {
		
		response.setContentType("text/html; charset=UTF-8");
		RecruitDTO recruit = null;
		recruit = RecruitService.getRecruitLatest();
		model.addAttribute("recruit", recruit);
		
		return "info/info";
    }
	
	@GetMapping(value = "/recruit/detail")
	public String openRecruitDetail(@ModelAttribute("params") RecruitDTO params, @RequestParam(value = "sTh", required = false) String sTh, Model model, HttpServletResponse response) throws Exception {
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
	public String openRecruitJoin(@ModelAttribute("params") RecruitDTO params, @RequestParam(value = "sTh", required = false) String sTh, Model model
			, HttpServletResponse response, Authentication authentication) throws Exception {
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		try {
		
			if (sTh == null) {
				out.println("<script>alert('올바르지 않은 접근입니다.'); history.back();</script>");
				out.flush();
				return showMessageWithRedirect("올바르지 않은 접근입니다.", "recruit/list", Method.GET, null, model);
			}
			
			RecruitDTO recruit = RecruitService.getRecruitDetail(sTh);
			if (recruit == null) {
				out.println("<script>alert('이미 종료된 채용입니다.'); history.back();</script>");
				out.flush();
				return showMessageWithRedirect("이미 종료된 채용입니다.", "recruit/list", Method.GET, null, model);
			}
			
			MemberDTO member = (MemberDTO) authentication.getPrincipal();  //userDetail 객체를 가져옴
			if(member != null) {
		        if(member.getActive() != 1) {
		        	return "member/joinConfirm";
		        }
			
		        PersonalInfoDTO application = new PersonalInfoDTO();
		        application.setSetupTh(sTh);
		        application.setSetupTitle(recruit.getSTitle());
		        application.setEmailAddr(member.getEmailAddr());
		        application.setMemName(member.getMemName());
		        application.setMemNo(member.getMemNo());
		        application.setPhoneNo(member.getPhoneNo());
		        
		        int idxCount = applicationService.getIdxCount(application);
		        if(idxCount != 0 ) {
		        	int idx = applicationService.getIdx(application);
		        	return showMessageWithRedirect("입사지원서 입력 페이지로 이동합니다.", "/application/personalInfo?idx="+idx, Method.GET, null, model);
		        } else {
		        	boolean isRegistered = applicationService.registerPersonalInfo(application);
		        	if (isRegistered == false) {
						out.println("<script>alert('저장에 실패하였습니다.'); history.go(-1);</script>");
						out.flush();
					}
		        }
				
				return showMessageWithRedirect("지원서 입력 페이지로 이동합니다.", "/application/personalInfo?idx="+application.getIdx(), Method.GET, null, model);
			}
			
			return showMessageWithRedirect("시스템에 문제가 발생하였습니다.", "/", Method.GET, null, model);
		
		} catch (DataAccessException e) {
			out.println("<script>alert('데이터베이스 처리 과정에 문제가 발생하였습니다.'); history.go(-1);</script>");
			out.flush();
			return showMessageWithRedirect("데이터베이스 처리 과정에 문제가 발생하였습니다.", "/", Method.GET, null, model);

		} catch (Exception e) {
			out.println("<script>alert('시스템에 문제가 발생하였습니다.'); history.go(-1);</script>");
			out.flush();
			return showMessageWithRedirect("시스템에 문제가 발생하였습니다.", "/", Method.GET, null, model);
		}
	}
	
	@PostMapping(value = "/recruit/register")
	public String registerRecruit(
			@ModelAttribute("member") final MemberDTO memberDTO, @RequestParam(value = "sTh", required = false) String sTh,
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
