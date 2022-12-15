package com.vegemil.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.vegemil.adapter.GsonLocalDateTimeAdapter;
import com.vegemil.constant.Method;
import com.vegemil.domain.AdminDTO;
import com.vegemil.domain.MemberDTO;
import com.vegemil.domain.QnaDTO;
import com.vegemil.domain.RecruitDTO;
import com.vegemil.service.AdminRecruitService;
import com.vegemil.util.UiUtils;

import lombok.extern.log4j.Log4j2;

@Controller
public class AdminRecruitController extends UiUtils {
	 private final Logger LOGGER = LoggerFactory.getLogger(AdminRecruitController.class.getName());
	
	@Autowired
	private AdminRecruitService adminRecruitService;
	
	@GetMapping(value = "/admin/recruit/{viewName}")
    public String adminRecruit(@PathVariable(value = "viewName", required = false) String viewName, HttpServletRequest req, Model model)throws Exception{
		return "admin/recruit/"+viewName;
    }
	
	@RequestMapping(value = "/admin/recruit/recruitIndex")
    public String adminRecruitIndex(@RequestParam(value = "sTh", required = false) String sTh, HttpServletRequest req, Model model)throws Exception{
		List<RecruitDTO> recruitList = adminRecruitService.getRecruitList();
		
		if(sTh == null) sTh = recruitList.get(0).getSTh();
		
		model.addAttribute("sTh", sTh);
		model.addAttribute("recruitList", recruitList);
		model.addAttribute("volunteerdata", adminRecruitService.getVolunteerData(sTh));
		model.addAttribute("dateapplydata", adminRecruitService.getDateApplyData(sTh));
		model.addAttribute("fielddata", adminRecruitService.getFieldData(sTh));
		model.addAttribute("sTitle", adminRecruitService.getRecruit(sTh).getSTitle());
		
		return "admin/recruit/recruitIndex";
    }
	/*
	@GetMapping(value = "/admin/recruit/noticeList")
	public String adminRecruitAdd(@RequestParam(value = "sTh", required = false) String sTh, HttpServletRequest req, Model model) {
		List<RecruitDTO> recruitList = adminRecruitService.getRecruitList();
		
		model.addAttribute("recruitList", recruitList);
		return "admin/recruit/noticeList";
	}
	*/
	
	@GetMapping(value = "/admin/recruit/noticeAdd")
	public String adminRecruitAdd(@RequestParam(value = "sTh", required = false) String sTh, HttpServletResponse res, HttpServletRequest req, Model model) {
		RecruitDTO recruit = new RecruitDTO();
		List<RecruitDTO> recruitSubList = null;
		
		recruit.setSTh("");
		recruit.setSTitle("");
		recruit.setSCompany("");
		recruit.setSType("");
		recruit.setSStartdate("");
		recruit.setSEnddate("");
		recruit.setS1("");
		recruit.setS2("");
		recruit.setS3("");
		recruit.setSClose("");
		recruit.setSCategory("");
		recruit.setSCategory2("");
		recruit.setSCollect("");
		
		if(sTh != null) recruit = adminRecruitService.getRecruit(sTh);
		if(sTh != null) recruitSubList = adminRecruitService.getRecruitDt(sTh);
		
		model.addAttribute("recruit", recruit);
		model.addAttribute("recruitSubList", recruitSubList);
		return "admin/recruit/noticeAdd";
	}
	
	@RequestMapping(value = "/admin/recruit/noticeList/table", method = {RequestMethod.GET, RequestMethod.POST})
	 public @ResponseBody JsonObject getNoticeList(@ModelAttribute("params") RecruitDTO params, Model model, @RequestParam Map<String, Object> commandMap) {
		
		LOGGER.info("commandMap============="+commandMap.toString());

		JsonObject jsonObj = new JsonObject();
		List<RecruitDTO> noticeList = adminRecruitService.getRecruitList2(commandMap);
		if (CollectionUtils.isEmpty(noticeList) == false) {
			Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new GsonLocalDateTimeAdapter()).create();
			JsonArray jsonArr = gson.toJsonTree(noticeList).getAsJsonArray();
			jsonObj.add("data", jsonArr);
		}

		return jsonObj;
	 }
	
	
	@PostMapping("/admin/recruit/registerNotice")
    public String registerNotice(@ModelAttribute("params") RecruitDTO params, Model model, HttpServletResponse response, HttpServletRequest request) {
    	try {
     		Map<String, Object> paramMap = new HashMap<String, Object>();
     		List<RecruitDTO> list = new ArrayList<RecruitDTO>();
     		RecruitDTO recruitDto = null;
     		
    		int cnt = Integer.parseInt(request.getParameter("cnt"));
    		
    		
    		if("".equals(request.getParameter("sTh"))) {
     			params.setNewSTh(params.getSYyyy()+params.getSMm());
    		}
    		
    		for(int i=0; i<cnt; i++) {
    			recruitDto = new RecruitDTO();
    			recruitDto.setSTh("".equals(request.getParameter("sTh"))?params.getNewSTh():request.getParameter("sTh"));
    			recruitDto.setSCompany(request.getParameter("company"+i));
    			recruitDto.setSField(request.getParameter("field"+i));
    			recruitDto.setSArea(request.getParameter("area"+i));
    			list.add(recruitDto);
    			LOGGER.info("company========="+request.getParameter("company"+i));
    		}
    		
    		paramMap.put("list", list);
    		
    		
	    	boolean isRegistered = adminRecruitService.registerRecruitNotice(params, list);
			if (isRegistered == false) {
				return showMessageWithRedirect("채용공고 등록에 실패하였습니다.", "/admin/recruit/noticeAdd", Method.GET, null, model);
			}
    	} catch (DataAccessException e) {
			return showMessageWithRedirect("데이터베이스 처리 과정에 문제가 발생하였습니다.", "/admin/recruit/noticeAdd", Method.GET, null, model);
		} catch (Exception e) {
			return showMessageWithRedirect("시스템에 문제가 발생하였습니다.", "/admin/recruit/noticeAdd", Method.GET, null, model);
		}
    	return showMessageWithRedirect("채용공고 등록이 완료되었습니다.\n 채용공고 리스트로 이동 합니다.", "/admin/recruit/noticeList", Method.GET, null, model);
    }

	@RequestMapping("/admin/recruit/copyNotice")
    public @ResponseBody String copyNotice(@ModelAttribute("params") RecruitDTO params, Model model, HttpServletResponse response, HttpServletRequest request) {
    	try {
	    	boolean isRegistered = adminRecruitService.registerRecruitCopy(params);
			if (isRegistered == false) {
				return "false";
			}
    	} catch (DataAccessException e) {
    		return "false";
		} catch (Exception e) {
			return "false";
		}
    	return "success";
    }
	
	@RequestMapping(value = "/admin/recruit/volunteerList")
    public String adminRecruitVolunteer(@RequestParam(value = "sTh", required = false) String sTh, HttpServletRequest req, 
    		Model model, Authentication authentication)throws Exception{
		List<RecruitDTO> recruitList = adminRecruitService.getRecruitList();
		
		MemberDTO member = (MemberDTO) authentication.getPrincipal();  //userDetail 객체를 가져옴
        if(member != null) {
        	if(!"ADMIN".equals(member.getAuth())) {
        		return "/";
        	}
        }
		
		if(sTh == null) sTh = recruitList.get(0).getSTh();
		
		model.addAttribute("sTh", sTh);
		model.addAttribute("recruitList", recruitList);
		
		return "admin/recruit/volunteerList";
    }
	
	@RequestMapping(value = "/admin/recruit/volunteerList/table", method = {RequestMethod.GET, RequestMethod.POST})
	 public @ResponseBody JsonObject getVolunteerList(@ModelAttribute("params") RecruitDTO params, @RequestParam Map<String, Object> commandMap) {

		JsonObject jsonObj = new JsonObject();
		List<RecruitDTO> volunteerList = adminRecruitService.getVolunteerList(commandMap);
		if (CollectionUtils.isEmpty(volunteerList) == false) {
			Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new GsonLocalDateTimeAdapter()).create();
			JsonArray jsonArr = gson.toJsonTree(volunteerList).getAsJsonArray();
			jsonObj.add("data", jsonArr);
			
		}

		return jsonObj;
	 }
	
	@RequestMapping("/admin/recruit/updateVolunteerList")
    public @ResponseBody String updateVolunteerList(@ModelAttribute("params") RecruitDTO params, Model model, HttpServletResponse response, HttpServletRequest request) {
    	try {
	    	boolean isRegistered = adminRecruitService.updateVolunteerList(params);
			if (isRegistered == false) {
				return "false";
			}
    	} catch (DataAccessException e) {
    		return "false";
		} catch (Exception e) {
			return "false";
		}
    	return "success";
    }
	
	@RequestMapping(value = "/admin/recruit/recruitDetail")
    public String recruitDetail(@ModelAttribute("params") RecruitDTO params, HttpServletRequest req, Model model)throws Exception{
		RecruitDTO recruitDto = adminRecruitService.getVolunteerAllData(params);
		
		model.addAttribute("recruitList", recruitDto);
		
		return "admin/recruit/recruitDetail";
    }
	
	@RequestMapping("/admin/recruit/deleteVolunteerList")
    public @ResponseBody boolean deleteVolunteerList(@ModelAttribute("params") RecruitDTO params, Model model, HttpServletResponse response, HttpServletRequest request) {
    	try {
    		String checkList[] = request.getParameterValues("checkList");
    		String setupTh = request.getParameter("setupTh");
    		LOGGER.info("check==========="+checkList);
    		LOGGER.info("setupTh==========="+setupTh);
    		ArrayList<String> list = new ArrayList<>();
     		for(int i=0; i<checkList.length; i++) {
    			list.add(checkList[i]);
    		}
    		
    		Map<String, Object> paramMap = new HashMap<String, Object>();
    		paramMap.put("list", list);
    		paramMap.put("setupTh", setupTh);
    		
    		boolean isDeleted = adminRecruitService.deleteVolunteer(paramMap);
    		if (!isDeleted) {
				return false;
			}
    		
    		
    	} catch (DataAccessException e) {
    		return false;
		} catch (Exception e) {
			return false;
		}
    	return true;
    }
	
	@RequestMapping("/admin/recruit/deleteNoticeList")
    public @ResponseBody boolean deleteNoticeList(@ModelAttribute("params") RecruitDTO params, Model model, HttpServletResponse response, HttpServletRequest request) {
    	try {
    		String checkList[] = request.getParameterValues("checkList");
    		LOGGER.info("check==========="+checkList);
    		ArrayList<String> list = new ArrayList<>();
     		for(int i=0; i<checkList.length; i++) {
    			list.add(checkList[i]);
    		}
    		
    		Map<String, Object> paramMap = new HashMap<String, Object>();
    		paramMap.put("list", list);
    		
    		boolean isDelete = adminRecruitService.deleteNotice(paramMap);
    		if (!isDelete) {
				return false;
			}
    		
    		
    	} catch (DataAccessException e) {
    		return false;
		} catch (Exception e) {
			return false;
		}
    	return true;
    }
	
	@GetMapping(value = "/admin/recruit/qnaList")
    public String qnaList(@ModelAttribute("params") QnaDTO params, HttpServletRequest req, Model model)throws Exception{
		List<RecruitDTO> recruitList = adminRecruitService.getRecruitList();
		
		model.addAttribute("recruitList", recruitList);
		
		return "admin/recruit/qnaList";
    }
	
	@RequestMapping(value = "/admin/recruit/qnaList/table", method = {RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody JsonObject getQnaList(@ModelAttribute("params") RecruitDTO params, Model model, @RequestParam Map<String, Object> commandMap) {
		
		LOGGER.info("commandMap============="+commandMap.toString());

		JsonObject jsonObj = new JsonObject();
		List<QnaDTO> noticeList = adminRecruitService.getQnaList(commandMap);
		if (CollectionUtils.isEmpty(noticeList) == false) {
			Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new GsonLocalDateTimeAdapter()).create();
			JsonArray jsonArr = gson.toJsonTree(noticeList).getAsJsonArray();
			jsonObj.add("data", jsonArr);
		}

		return jsonObj;
	}
	
	@RequestMapping("/admin/recruit/deleteQnaList")
    public @ResponseBody boolean adminQnaListDelete(@ModelAttribute("params") RecruitDTO params, Model model, HttpServletResponse response, HttpServletRequest request) {
    	try {
    		String checkList[] = request.getParameterValues("checkList");
    		LOGGER.info("check==========="+checkList);
    		ArrayList<String> list = new ArrayList<>();
     		for(int i=0; i<checkList.length; i++) {
    			list.add(checkList[i]);
    		}
    		
    		Map<String, Object> paramMap = new HashMap<String, Object>();
    		paramMap.put("list", list);
    		
    		boolean isDelete = adminRecruitService.deleteQnaList(paramMap);
    		if (!isDelete) {
				return false;
			}
    		
    		
    	} catch (DataAccessException e) {
    		return false;
		} catch (Exception e) {
			return false;
		}
    	return true;
    }
	
	@GetMapping(value = "/admin/recruit/qnaDetail")
    public String qnaAnswer(@ModelAttribute("params") QnaDTO params, HttpServletRequest req, Model model)throws Exception{
		QnaDTO qnaDto = adminRecruitService.getQna(params);
		
		model.addAttribute("qna", qnaDto);
		
		return "admin/recruit/qnaDetail";
    }
	
	@PostMapping(value = "/admin/recruit/registerQnaDetail")
	public String registerFaq(@ModelAttribute("params") final QnaDTO params, Model model) {
		Map<String, Object> pagingParams = getPagingParams(params);
		try {
			boolean isRegistered = adminRecruitService.registerQnaDetail(params);
			if (isRegistered == false) {
				return showMessageWithRedirect("게시글 등록에 실패하였습니다.", "/admin/recruit/qnaList", Method.GET, pagingParams, model);
			}
		} catch (DataAccessException e) {
			return showMessageWithRedirect("데이터베이스 처리 과정에 문제가 발생하였습니다.", "/admin/recruit/qnaList", Method.GET, pagingParams, model);

		} catch (Exception e) {
			return showMessageWithRedirect("시스템에 문제가 발생하였습니다.", "/admin/recruit/qnaList", Method.GET, pagingParams, model);
		}

		return showMessageWithRedirect("게시글 등록이 완료되었습니다.", "/admin/recruit/qnaList", Method.GET, null, model);
	}
}
