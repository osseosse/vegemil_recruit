package com.vegemil.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.vegemil.constant.Method;
import com.vegemil.domain.AdminDTO;
import com.vegemil.domain.MemberDTO;
import com.vegemil.service.AdminService;
import com.vegemil.service.MailService;
import com.vegemil.util.UiUtils;

@Controller
public class AdminController extends UiUtils {
	@Autowired
	private AdminService adminService;
	
	@Autowired
	private MailService mailService;

	@RequestMapping(value = "/admin/index")
	public String adminIndex(HttpServletRequest req, Model model) {
		
		// View attribute
	    return "admin/index1";
	}
	
	@RequestMapping(value = "/admin/auth/{viewName}")
    public String adminAuth(@PathVariable(value = "viewName", required = false) String viewName, HttpServletRequest req
    										, Authentication authentication, Model model)throws Exception{
		
//		if(authentication == null) {
//			return showMessageWithRedirect("????????? ????????????  ?????? ?????????.", "/admin/auth/login", Method.GET, null, model);
//		}
		
		return "admin/auth/"+viewName;
    }
	
	@RequestMapping(value = "/admin/account/{viewName}")
    public String adminAccount(@PathVariable(value = "viewName", required = false) String viewName, HttpServletRequest req, Model model)throws Exception{
		
		HttpSession session = req.getSession();
//		if(viewName.equals("settings")) {
//			AdminDTO admin = (AdminDTO) session.getAttribute("admin");
//			admin = adminService.getAdmin(admin.getAId());
//			if(admin != null) {
//				model.addAttribute("admin", admin);
//			}
//		}
		
		return "admin/account/"+viewName;
    }
	
	@GetMapping("/admin/auth/login")
	public String adminLogin(@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "exception", required = false) String exception,
			Model model) {
		model.addAttribute("error", error);
		model.addAttribute("exception", exception);
		return "admin/auth/login";
    }
	
//	@GetMapping("/admin/auth/loginProc")
//    public String userAccess(Model model, Authentication authentication) {
//        //Authentication ????????? ?????? ?????? ????????? ????????? ??? ??????.
//        AdminDTO adminDto = (AdminDTO) authentication.getPrincipal();  //userDetail ????????? ?????????
//        model.addAttribute("info", adminDto.getAId() +"??? "+ adminDto.getAName()+ "???");      //?????? ?????????
//        return "admin/recruit/volunteerList";
//    }
	
    // ???????????? ??????
    @PostMapping("/admin/auth/signUp")
    public String adminSignup(MemberDTO memberDto, Model model) {
    	
    	try {
	    	
	    	int result = adminService.joinAdmin(memberDto);
			if (result == 0) {
				return showMessageWithRedirect("?????? ????????? ??????????????????.", "/admin/auth/register", Method.GET, null, model);
			}else if(result == 2) {
				return showMessageWithRedirect("?????? ?????? ?????????????????????.", "/admin/auth/register", Method.GET, null, model);
			}
			
			memberDto.setAuth("ADMIN");
//			mailService.adminMailSend(memberDto); ????????? 
			model.addAttribute("member", memberDto);
			
    	} catch (DataAccessException e) {
			return showMessageWithRedirect("?????????????????? ?????? ????????? ????????? ?????????????????????.", "/admin/auth/register", Method.GET, null, model);

		} catch (Exception e) {
			return showMessageWithRedirect("???????????? ????????? ?????????????????????.", "/admin/auth/register", Method.GET, null, model);
		}
    	
    	return "admin/auth/joinConfirm";

    }
    
    @PostMapping("/admin/auth/searchPwdProc")
    public String searchPwdProc(MemberDTO memberDto, Model model, Authentication authentication) {
    	
    	boolean result;
    	
    	try {
    		result = adminService.initPwd(memberDto);
    		if(!result) {
    			return showMessageWithRedirect("?????? ??????????????????.", "admin/auth/resetPassword", Method.GET, null, model);
    		}
    	}catch(Exception e) {
    		return showMessageWithRedirect("???????????? ????????? ?????????????????????.", "/admin/auth/register", Method.GET, null, model);
    	}
        return showMessageWithRedirect("????????????????????????. - abcd1234 \n" , "/admin/auth/login", Method.GET, null, model);
    }

    // ???????????? ?????? ?????????
    @GetMapping("/admin/logout")
    public String adminLogout(HttpServletRequest req, HttpServletResponse response, Model model) {
    	
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(req, response, auth);
        }
        return "redirect:/admin/recruit/volunteerList";
    	
    }
    
//    @GetMapping("/admin/auth/login")
//    public String moveAdminLogin(HttpServletRequest req, Model model) {
//    	
//    	if(req.getParameter("msg") != null) {
//	    	if(req.getParameter("msg").equals("session")) {
//	    		return showMessageWithRedirect("????????????????????? \n????????? ???????????? ?????? ?????????.", "/admin/auth/login", Method.GET, null, model);
//	    	}
//    	}
//        return "/admin/auth/login";
//    }

    @PostMapping("/admin/passChange" )
	public String adminPassChange(MemberDTO memberDto, Model model , HttpServletRequest req, RedirectAttributes rttr 
			,Authentication authentication) {
		
		try {
			MemberDTO loginUser = (MemberDTO) authentication.getPrincipal();  //userDetail ????????? ?????????
			memberDto.setEmailAddr(loginUser.getEmailAddr());
			
			boolean isUpdated = adminService.changePwd(memberDto);
			
			if(!isUpdated) {
				return showMessageWithRedirect("??????????????? ???????????? ????????????.", "/admin/auth/resetPassword", Method.GET, null, model);
			}
			
		} catch (DataAccessException e) {
			return showMessageWithRedirect("?????????????????? ?????? ????????? ????????? ?????????????????????.", "/admin/auth/login", Method.GET, null, model);

		} catch (Exception e) {
			return showMessageWithRedirect("???????????? ????????? ?????????????????????.", "/admin/auth/login", Method.GET, null, model);
		}
		
		return showMessageWithRedirect("?????????????????????.", "/admin/event/calendarModel", Method.GET, null, model);
    }
    
    //?????? ?????????
  	@GetMapping(value = "/admin/email/active")
  	public void changeActive(@RequestParam(value="emailAddr", required=true) String eamilAddr, 
  			@RequestParam(value="memName", required=true) String memName, Model model, HttpServletResponse response) throws Exception {
  		response.setContentType("text/html; charset=UTF-8");
  		PrintWriter out = response.getWriter();
  		MemberDTO memberDto = new MemberDTO();
  		if (eamilAddr == null || memName == null) {
  			out.println("<script>alert('???????????? ?????? ???????????????.'); history.go(-1);</script>");
  			out.flush();
  		}
  		memberDto.setEmailAddr(eamilAddr);
  		memberDto.setMemName(memName);
  		
  		boolean isActived = mailService.verifyAdminEmail(memberDto);
  		if (!isActived) {
  			out.println("<script>alert('???????????? ?????? ???????????????.'); history.go(-1);</script>");
  			out.flush();
  		}
  		boolean isRegistered = adminService.activeMember(memberDto);
  		if (isRegistered == false) {
  			out.println("<script>alert('?????????????????? ?????????????????????.'); window.location='/';</script>");
  			out.flush();
  		}
  		
  		out.println("<script>alert('????????? ????????? ?????????????????????.'); window.close();</script>");
		out.flush();
  	}
  	

}
