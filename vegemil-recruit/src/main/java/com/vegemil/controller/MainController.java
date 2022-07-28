package com.vegemil.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

	@RequestMapping(value = "/")
	public String index() {
	       return "index";
	}
	
	@RequestMapping(value = "/home")
    public String main()throws Exception{
		return "index";
    }
	
	@RequestMapping(value = "/fragments/{viewName}")
    public String openFragments(@PathVariable(value = "viewName", required = false) String viewName)throws Exception{
		
		return "/fragments/"+viewName;
    }
	
	@RequestMapping(value = "/information/{viewName}")
    public String moveInformation(@PathVariable(value = "viewName", required = false) String viewName)throws Exception{
		
		return "/information/"+viewName;
    }
	
	@RequestMapping(value = "/account/{viewName}")
    public String moveAccount(@PathVariable(value = "viewName", required = false) String viewName)throws Exception{
		
		return "/account/"+viewName;
    }
	
	@RequestMapping(value = "/application/{viewName}")
    public String moveAapplication(@PathVariable(value = "viewName", required = false) String viewName)throws Exception{
		
		return "/application/"+viewName;
    }
	
	@GetMapping("/mail")
    public String dispMail() {
        return "/utils/mail";
    }
	
}
