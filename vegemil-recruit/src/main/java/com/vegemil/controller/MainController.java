package com.vegemil.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.vegemil.service.MailService;
import com.vegemil.service.RecruitService;
import com.vegemil.domain.MailDTO;
import com.vegemil.domain.MemberDTO;
import com.vegemil.domain.RecruitDTO;

@Controller
public class MainController {
	
	@Autowired
	MailService mailService;
	
	@Autowired
	private RecruitService RecruitService;

	@RequestMapping(value = "/")
	public String index(Model model, Authentication authentication) {
		//Authentication 객체를 통해 유저 정보를 가져올 수 있다.
		MemberDTO member = null;
		if(authentication != null)
        member = (MemberDTO) authentication.getPrincipal();  //userDetail 객체를 가져옴
		
        if(member != null) {
        	model.addAttribute("member",member);	//유저 정보
	        if(member.getActive() != 1) {
	        	return "member/joinConfirm";
	        }
        }
        
        RecruitDTO recruit = RecruitService.getRecruitLatest();
		int recruitCount = RecruitService.getRecruitCount();
		
		model.addAttribute("recruit", recruit);
		model.addAttribute("recruitCount", recruitCount);
        
        return "index";
	}
	
	@GetMapping(value = "/home")
    public String main() throws Exception {
		return "index";
    }
	
	@GetMapping(value = "/fragments/{viewName}")
    public String openFragments(@PathVariable(value = "viewName", required = false) String viewName) throws Exception {
		
		return "fragments/"+viewName;
    }
	
	
	@GetMapping(value = "/info/work")
    public String moveWork() throws Exception {
		
		return "info/work";
    }
	
	@GetMapping(value = "/info/benefit")
    public String moveBenefit() throws Exception {
		
		return "info/benefit";
    }
	
	@GetMapping("/mail")
    public String dispMail() {
        return "utils/mail";
    }
	
	@PostMapping("/sendAuthmail")
	public void sendAuthmail(MailDTO mailDto) {
        mailService.mailSend(mailDto);
    }
	
}
