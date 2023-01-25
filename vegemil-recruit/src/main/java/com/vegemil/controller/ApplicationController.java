package com.vegemil.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.JsonObject;
import com.vegemil.constant.Method;
import com.vegemil.domain.AcademyInfoDTO;
import com.vegemil.domain.ApplicationDTO;
import com.vegemil.domain.CareerDTO;
import com.vegemil.domain.IntroduceDTO;
import com.vegemil.domain.MemberDTO;
import com.vegemil.domain.PersonalInfoDTO;
import com.vegemil.domain.QualificationDTO;
import com.vegemil.domain.ResumeDTO;
import com.vegemil.service.ApplicationService;
import com.vegemil.service.RecruitService;
import com.vegemil.util.UiUtils;

@Controller
public class ApplicationController extends UiUtils {

	@Autowired
	private ApplicationService applicationService;
	
	@Autowired
	private RecruitService recruitService;
	
	@Value("${spring.servlet.multipart.location}")
    private String uploadPath;
	
	@GetMapping("/display")
	public ResponseEntity<Resource> display(@RequestParam("filename") String filename) {
		Resource resource = new FileSystemResource(uploadPath + "/img/" + filename);
		if(!resource.exists()) 
			return new ResponseEntity<Resource>(HttpStatus.NOT_FOUND);
		HttpHeaders header = new HttpHeaders();
		Path filePath = null;
		try {
			filePath = Paths.get(uploadPath + "/img/" + filename);
			header.add("Content-type", Files.probeContentType(filePath));
		} catch(IOException e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Resource>(resource, header, HttpStatus.OK);
	}
	
	@GetMapping(value = "/application/personalInfo")
	public String openPersonalInfo(@RequestParam(value = "idx", required = false) Long idx, Model model, HttpServletResponse response, Authentication authentication) throws Exception {
		
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		PersonalInfoDTO application = new PersonalInfoDTO();
		
		if (idx == null) {
			out.println("<script>alert('올바르지 않은 접근입니다.'); history.go(-1);</script>");
			out.flush();
			return showMessageWithRedirect("올바르지 않은 접근입니다.", "/", Method.GET, null, model);
		}
		
		//Authentication 객체를 통해 유저 정보를 가져올 수 있다.
        MemberDTO member = (MemberDTO) authentication.getPrincipal();  //userDetail 객체를 가져옴
        if(member != null) {
	        if(member.getActive() != 1) {
	        	return "member/joinConfirm";
	        }
	        
	        application = applicationService.selectPersonalInfo(idx, member.getMemNo());
			if (application == null) {
				out.println("<script>alert('없는 지원서이거나 이미 삭제된 지원서입니다.'); history.go(-1);</script>");
				out.flush();
				return showMessageWithRedirect("없는 지원서이거나 이미 삭제된 지원서입니다.", "/", Method.GET, null, model);
			}
			
			int recruitEndCheck = recruitService.checkRecruitEnd(application.getSetupTh());
			if(recruitEndCheck == 0) {
				out.println("<script>alert('종료된 채용입니다.'); history.go(-1);</script>");
				out.flush();
				return showMessageWithRedirect("종료된 채용입니다.", "/", Method.GET, null, model);
			}
			
			application.setIdx(idx);
			application.setEmailAddr(member.getEmailAddr());
	        application.setMemName(member.getMemName());
	        application.setPhoneNo(member.getPhoneNo());
	        
    		model.addAttribute("member", member);
			model.addAttribute("app", application);
	        
        }
		
		return "application/personalInfo";
	}
	
	@RequestMapping(value = { "/application/updatePersonalInfo" }, method = { RequestMethod.POST, RequestMethod.PATCH }, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public @ResponseBody JsonObject updatePersonalInfo(PersonalInfoDTO params,
														HttpServletResponse response, HttpServletRequest request, Authentication authentication) {

		JsonObject jsonObj = new JsonObject();

		try {
			
			if(params.getFileName() != null) {
				String originalName = params.getFileName().getOriginalFilename();
				if(!"".equals(originalName)) {
					String file = originalName.substring(originalName.lastIndexOf("\\") + 1);
					String uuid = UUID.randomUUID().toString();
					String savefileName = uuid + "_" + file;
					Path savePath = Paths.get(uploadPath + "/img/" + savefileName);
					System.err.println(uploadPath + "/img/" + savefileName);
					
					params.getFileName().transferTo(savePath);
					params.setPhoto(savefileName);
				}
			}
			
			MemberDTO member = (MemberDTO) authentication.getPrincipal();
			if(member != null) {
			
				params.setEmailAddr(member.getEmailAddr());
				params.setMemName(member.getMemName());
				params.setMemNo(member.getMemNo());
				params.setPhoneNo(member.getPhoneNo());
		        
		        boolean isRegistered = applicationService.registerPersonalInfo(params);
				jsonObj.addProperty("result", isRegistered);
				
			}
			
		} catch (DataAccessException e) {
			jsonObj.addProperty("message", "데이터베이스 처리 과정에 문제가 발생하였습니다.");

		} catch (Exception e) {
			jsonObj.addProperty("message", "시스템에 문제가 발생하였습니다.");
		}

		return jsonObj;
	}
	
	@PostMapping(value = "/application/registerPersonalInfo")
	public String registerPersonalInfo(@ModelAttribute("app") final PersonalInfoDTO application, 
			BindingResult bindingResult, @RequestParam("fileName") MultipartFile fileName, Model model, 
			HttpServletResponse response, HttpServletRequest request, Authentication authentication) throws Exception {
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		try {
			
			String originalName = fileName.getOriginalFilename();
			if(!"".equals(originalName)) {
				String file = originalName.substring(originalName.lastIndexOf("\\") + 1);
				String uuid = UUID.randomUUID().toString();
				String savefileName = uuid + "_" + file;
				Path savePath = Paths.get(uploadPath + "/img/" + savefileName);
				
				fileName.transferTo(savePath);
				application.setPhoto(savefileName);
			}
			
			MemberDTO member = (MemberDTO) authentication.getPrincipal();  //userDetail 객체를 가져옴
			if(member != null) {
		        if(member.getActive() != 1) {
		        	return "member/joinConfirm";
		        }
			
		        application.setEmailAddr(member.getEmailAddr());
		        application.setMemName(member.getMemName());
		        application.setMemNo(member.getMemNo());
		        application.setPhoneNo(member.getPhoneNo());
		        
				boolean isRegistered = applicationService.registerPersonalInfo(application);
				if (isRegistered == false) {
					out.println("<script>alert('저장에 실패하였습니다.'); history.go(-1);</script>");
					out.flush();
				}
				
			}
		} catch (DataAccessException e) {
			out.println("<script>alert('데이터베이스 처리 과정에 문제가 발생하였습니다.'); history.go(-1);</script>");
			out.flush();
			return showMessageWithRedirect("데이터베이스 처리 과정에 문제가 발생하였습니다.", "/", Method.GET, null, model);

		} catch (Exception e) {
			out.println("<script>alert('시스템에 문제가 발생하였습니다.'); history.go(-1);</script>");
			out.flush();
			return showMessageWithRedirect("시스템에 문제가 발생하였습니다.", "/", Method.GET, null, model);
		}

		return showMessageWithRedirect("지원서 저장이 완료되었습니다.", "/application/academy?idx="+application.getIdx(), Method.GET, null, model);
	}
	
	
	@GetMapping(value = "/application/academy")
	public String openAcademy(@RequestParam(value = "idx", required = false) Long idx, Model model, HttpServletResponse response, Authentication authentication) throws Exception {
		
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		AcademyInfoDTO application = new AcademyInfoDTO();
		
		if (idx == null) {
			out.println("<script>alert('올바르지 않은 접근입니다.'); history.go(-1);</script>");
			out.flush();
			return showMessageWithRedirect("올바르지 않은 접근입니다.", "/", Method.GET, null, model);
		}
		
		//Authentication 객체를 통해 유저 정보를 가져올 수 있다.
        MemberDTO member = (MemberDTO) authentication.getPrincipal();  //userDetail 객체를 가져옴
        if(member != null) {
	        if(member.getActive() != 1) {
	        	return "member/joinConfirm";
	        }
	        
	        application = applicationService.selectAcademy(idx, member.getMemNo());
			if (application == null) {
				out.println("<script>alert('없는 지원서이거나 이미 삭제된 지원서입니다.'); history.go(-1);</script>");
				out.flush();
				return showMessageWithRedirect("없는 지원서이거나 이미 삭제된 지원서입니다.", "/", Method.GET, null, model);
			}
			
			int recruitEndCheck = recruitService.checkRecruitEnd(application.getSetupTh());
			if(recruitEndCheck == 0) {
				out.println("<script>alert('종료된 채용입니다.'); history.go(-1);</script>");
				out.flush();
				return showMessageWithRedirect("종료된 채용입니다.", "/", Method.GET, null, model);
			}
			
			application.setIdx(idx);
			application.setEmailAddr(member.getEmailAddr());
	        application.setMemName(member.getMemName());
	        application.setPhoneNo(member.getPhoneNo());
    		
    		model.addAttribute("member", member);
			model.addAttribute("app", application);
	        
        }
		
		return "application/academy";
	}
	
	@PostMapping(value = "/application/registerAcademyInfo")
	public String registerAcademyInfo(@ModelAttribute("app") final AcademyInfoDTO application, 
			BindingResult bindingResult, Model model, 
			HttpServletResponse response, HttpServletRequest request, Authentication authentication) throws Exception {
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		try {
			
			MemberDTO member = (MemberDTO) authentication.getPrincipal();  //userDetail 객체를 가져옴
			if(member != null) {
		        if(member.getActive() != 1) {
		        	return "member/joinConfirm";
		        }
			
		        application.setEmailAddr(member.getEmailAddr());
		        application.setMemName(member.getMemName());
		        application.setMemNo(member.getMemNo());
		        application.setPhoneNo(member.getPhoneNo());
		        
				boolean isRegistered = applicationService.registerAcademyInfo(application);
				if (isRegistered == false) {
					out.println("<script>alert('저장에 실패하였습니다.'); history.go(-1);</script>");
					out.flush();
				}
				
			}
		} catch (DataAccessException e) {
			out.println("<script>alert('데이터베이스 처리 과정에 문제가 발생하였습니다.'); history.go(-1);</script>");
			out.flush();
			return showMessageWithRedirect("데이터베이스 처리 과정에 문제가 발생하였습니다.", "/", Method.GET, null, model);

		} catch (Exception e) {
			out.println("<script>alert('시스템에 문제가 발생하였습니다.'); history.go(-1);</script>");
			out.flush();
			return showMessageWithRedirect("시스템에 문제가 발생하였습니다.", "/", Method.GET, null, model);
		}

		return showMessageWithRedirect("지원서 저장이 완료되었습니다.", "/application/qualification?idx="+application.getIdx(), Method.GET, null, model);
	}
	
	@RequestMapping(value = { "/app/updateAcademy" }, method = { RequestMethod.POST, RequestMethod.PATCH }, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public JsonObject updateAcademy(@RequestBody Map<String, Object> academyInfoMap, Authentication authentication) {

		JsonObject jsonObj = new JsonObject();

		try {
			
			MemberDTO member = (MemberDTO) authentication.getPrincipal();
			if(member != null) {
				
				academyInfoMap.put("memNo", member.getMemNo());
				
		        boolean isRegistered = applicationService.updateAcademyInfo(academyInfoMap);
				jsonObj.addProperty("result", isRegistered);
				
			}
			
		} catch (DataAccessException e) {
			jsonObj.addProperty("message", "데이터베이스 처리 과정에 문제가 발생하였습니다.");

		} catch (Exception e) {
			jsonObj.addProperty("message", "시스템에 문제가 발생하였습니다.");
		}

		return jsonObj;
	}
	
	@GetMapping(value = "/application/qualification")
	public String openQualification(@RequestParam(value = "idx", required = false) Long idx, Model model, HttpServletResponse response, Authentication authentication) throws Exception {
		
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		QualificationDTO application = new QualificationDTO();
		
		if (idx == null) {
			out.println("<script>alert('올바르지 않은 접근입니다.'); history.go(-1);</script>");
			out.flush();
			return showMessageWithRedirect("올바르지 않은 접근입니다.", "/", Method.GET, null, model);
		}
		
		//Authentication 객체를 통해 유저 정보를 가져올 수 있다.
        MemberDTO member = (MemberDTO) authentication.getPrincipal();  //userDetail 객체를 가져옴
        if(member != null) {
	        if(member.getActive() != 1) {
	        	return "member/joinConfirm";
	        }
	        
	        application = applicationService.selectQualification(idx, member.getMemNo());
			if (application == null) {
				out.println("<script>alert('없는 지원서이거나 이미 삭제된 지원서입니다.'); history.go(-1);</script>");
				out.flush();
				return showMessageWithRedirect("없는 지원서이거나 이미 삭제된 지원서입니다.", "/", Method.GET, null, model);
			}
			
			int recruitEndCheck = recruitService.checkRecruitEnd(application.getSetupTh());
			if(recruitEndCheck == 0) {
				out.println("<script>alert('종료된 채용입니다.'); history.go(-1);</script>");
				out.flush();
				return showMessageWithRedirect("종료된 채용입니다.", "/", Method.GET, null, model);
			}
			
			application.setIdx(idx);
			application.setEmailAddr(member.getEmailAddr());
	        application.setMemName(member.getMemName());
	        application.setPhoneNo(member.getPhoneNo());
	        
    		model.addAttribute("member", member);
			model.addAttribute("app", application);
	        
        }
		
		return "application/qualification";
	}
	
	@RequestMapping(value = { "/application/updateQualification" }, method = { RequestMethod.POST, RequestMethod.PATCH }, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public @ResponseBody JsonObject updateQualification(QualificationDTO params,
														HttpServletResponse response, HttpServletRequest request, Authentication authentication) {

		JsonObject jsonObj = new JsonObject();

		try {
			
			MemberDTO member = (MemberDTO) authentication.getPrincipal();
			if(member != null) {
			
				params.setEmailAddr(member.getEmailAddr());
				params.setMemName(member.getMemName());
				params.setMemNo(member.getMemNo());
				params.setPhoneNo(member.getPhoneNo());
		        
		        boolean isRegistered = applicationService.registerQualification(params);
				jsonObj.addProperty("result", isRegistered);
				
			}
			
		} catch (DataAccessException e) {
			jsonObj.addProperty("message", "데이터베이스 처리 과정에 문제가 발생하였습니다.");

		} catch (Exception e) {
			jsonObj.addProperty("message", "시스템에 문제가 발생하였습니다.");
		}

		return jsonObj;
	}
	
	@PostMapping(value = "/application/registerQualification")
	public String registerQualification(@ModelAttribute("app") final QualificationDTO application, 
			BindingResult bindingResult, Model model, 
			HttpServletResponse response, HttpServletRequest request, Authentication authentication) throws Exception {
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		try {
			
			MemberDTO member = (MemberDTO) authentication.getPrincipal();  //userDetail 객체를 가져옴
			if(member != null) {
		        if(member.getActive() != 1) {
		        	return "member/joinConfirm";
		        }
			
		        application.setEmailAddr(member.getEmailAddr());
		        application.setMemName(member.getMemName());
		        application.setMemNo(member.getMemNo());
		        application.setPhoneNo(member.getPhoneNo());
		        
				boolean isRegistered = applicationService.registerQualification(application);
				if (isRegistered == false) {
					out.println("<script>alert('저장에 실패하였습니다.'); history.go(-1);</script>");
					out.flush();
				}
				
			}
		} catch (DataAccessException e) {
			out.println("<script>alert('데이터베이스 처리 과정에 문제가 발생하였습니다.'); history.go(-1);</script>");
			out.flush();
			return showMessageWithRedirect("데이터베이스 처리 과정에 문제가 발생하였습니다.", "/", Method.GET, null, model);

		} catch (Exception e) {
			out.println("<script>alert('시스템에 문제가 발생하였습니다.'); history.go(-1);</script>");
			out.flush();
			return showMessageWithRedirect("시스템에 문제가 발생하였습니다.", "/", Method.GET, null, model);
		}

		return showMessageWithRedirect("지원서 저장이 완료되었습니다.", "/application/career?idx="+application.getIdx(), Method.GET, null, model);
	}
	
	@GetMapping(value = "/application/career")
	public String openCareer(@RequestParam(value = "idx", required = false) Long idx, Model model, HttpServletResponse response, Authentication authentication) throws Exception {
		
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		CareerDTO application = new CareerDTO();
		
		if (idx == null) {
			out.println("<script>alert('올바르지 않은 접근입니다.'); history.go(-1);</script>");
			out.flush();
			return showMessageWithRedirect("올바르지 않은 접근입니다.", "/", Method.GET, null, model);
		}
		
		//Authentication 객체를 통해 유저 정보를 가져올 수 있다.
        MemberDTO member = (MemberDTO) authentication.getPrincipal();  //userDetail 객체를 가져옴
        if(member != null) {
	        if(member.getActive() != 1) {
	        	return "member/joinConfirm";
	        }
	        
	        application = applicationService.selectCareer(idx, member.getMemNo());
			if (application == null) {
				out.println("<script>alert('없는 지원서이거나 이미 삭제된 지원서입니다.'); history.go(-1);</script>");
				out.flush();
				return showMessageWithRedirect("없는 지원서이거나 이미 삭제된 지원서입니다.", "/", Method.GET, null, model);
			}
			
			int recruitEndCheck = recruitService.checkRecruitEnd(application.getSetupTh());
			if(recruitEndCheck == 0) {
				out.println("<script>alert('종료된 채용입니다.'); history.go(-1);</script>");
				out.flush();
				return showMessageWithRedirect("종료된 채용입니다.", "/", Method.GET, null, model);
			}
			
			application.setIdx(idx);
			application.setEmailAddr(member.getEmailAddr());
	        application.setMemName(member.getMemName());
	        application.setPhoneNo(member.getPhoneNo());
    		
    		model.addAttribute("member", member);
			model.addAttribute("app", application);
	        
        }
		
		return "application/career";
	}
	
	@RequestMapping(value = { "/application/updateCareer" }, method = { RequestMethod.POST, RequestMethod.PATCH }, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public @ResponseBody JsonObject updateCareer(CareerDTO params,
												HttpServletResponse response, HttpServletRequest request, Authentication authentication) {

		JsonObject jsonObj = new JsonObject();

		try {
			
			if(params.getFileName() != null) {
				String originalName = params.getFileName().getOriginalFilename();
				if(!"".equals(originalName)) {
					String file = originalName.substring(originalName.lastIndexOf("\\") + 1);
					String uuid = UUID.randomUUID().toString();
					String savefileName = uuid + "_" + file;
					Path savePath = Paths.get(uploadPath + "/port/" + savefileName);
					
					params.getFileName().transferTo(savePath);
					params.setPortFile(savefileName);
				}
			}
			
			MemberDTO member = (MemberDTO) authentication.getPrincipal();
			if(member != null) {
			
				params.setEmailAddr(member.getEmailAddr());
				params.setMemName(member.getMemName());
				params.setMemNo(member.getMemNo());
				params.setPhoneNo(member.getPhoneNo());
		        
		        boolean isRegistered = applicationService.registerCareer(params);
				jsonObj.addProperty("result", isRegistered);
				
			}
			
		} catch (DataAccessException e) {
			jsonObj.addProperty("message", "데이터베이스 처리 과정에 문제가 발생하였습니다.");

		} catch (Exception e) {
			jsonObj.addProperty("message", "시스템에 문제가 발생하였습니다.");
		}

		return jsonObj;
	}
	
	@PostMapping(value = "/application/registerCareer")
	public String registerCareer(@ModelAttribute("app") final CareerDTO application, 
			BindingResult bindingResult, @RequestParam("fileName") MultipartFile fileName, Model model, 
			HttpServletResponse response, HttpServletRequest request, Authentication authentication) throws Exception {
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		try {
			
			String originalName = fileName.getOriginalFilename();
			if(!"".equals(originalName)) {
				String file = originalName.substring(originalName.lastIndexOf("\\") + 1);
				String uuid = UUID.randomUUID().toString();
				String savefileName = uuid + "_" + file;
				//테스트경로
				Path savePath = Paths.get(uploadPath + "/port/" + savefileName);
				
				//저장
				fileName.transferTo(savePath);
				//포트폴리오
				application.setPortFile(file);
			}
			
			MemberDTO member = (MemberDTO) authentication.getPrincipal();  //userDetail 객체를 가져옴
			if(member != null) {
		        if(member.getActive() != 1) {
		        	return "member/joinConfirm";
		        }
			
		        application.setEmailAddr(member.getEmailAddr());
		        application.setMemName(member.getMemName());
		        application.setMemNo(member.getMemNo());
		        application.setPhoneNo(member.getPhoneNo());
		        
				boolean isRegistered = applicationService.registerCareer(application);
				if (isRegistered == false) {
					out.println("<script>alert('저장에 실패하였습니다.'); history.go(-1);</script>");
					out.flush();
				}
				
			}
		} catch (DataAccessException e) {
			out.println("<script>alert('데이터베이스 처리 과정에 문제가 발생하였습니다.'); history.go(-1);</script>");
			out.flush();
			return showMessageWithRedirect("데이터베이스 처리 과정에 문제가 발생하였습니다.", "/", Method.GET, null, model);

		} catch (Exception e) {
			out.println("<script>alert('시스템에 문제가 발생하였습니다.'); history.go(-1);</script>");
			out.flush();
			return showMessageWithRedirect("시스템에 문제가 발생하였습니다.", "/", Method.GET, null, model);
		}

		return showMessageWithRedirect("지원서 저장이 완료되었습니다.", "/application/introduce?idx="+application.getIdx(), Method.GET, null, model);
	}
	
	@GetMapping(value = "/application/introduce")
	public String openIntroduce(@RequestParam(value = "idx", required = false) Long idx, Model model, HttpServletResponse response, Authentication authentication) throws Exception {
		
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		IntroduceDTO application = new IntroduceDTO();
		
		if (idx == null) {
			out.println("<script>alert('올바르지 않은 접근입니다.'); history.go(-1);</script>");
			out.flush();
			return showMessageWithRedirect("올바르지 않은 접근입니다.", "/", Method.GET, null, model);
		}
		
		//Authentication 객체를 통해 유저 정보를 가져올 수 있다.
        MemberDTO member = (MemberDTO) authentication.getPrincipal();  //userDetail 객체를 가져옴
        if(member != null) {
	        if(member.getActive() != 1) {
	        	return "member/joinConfirm";
	        }
	        
	        application = applicationService.selectIntroduce(idx, member.getMemNo());
			if (application == null) {
				out.println("<script>alert('없는 지원서이거나 이미 삭제된 지원서입니다.'); history.go(-1);</script>");
				out.flush();
				return showMessageWithRedirect("없는 지원서이거나 이미 삭제된 지원서입니다.", "/", Method.GET, null, model);
			}
			
			int recruitEndCheck = recruitService.checkRecruitEnd(application.getSetupTh());
			if(recruitEndCheck == 0) {
				out.println("<script>alert('종료된 채용입니다.'); history.go(-1);</script>");
				out.flush();
				return showMessageWithRedirect("종료된 채용입니다.", "/", Method.GET, null, model);
			}
			
			application.setIdx(idx);
			application.setEmailAddr(member.getEmailAddr());
	        application.setMemName(member.getMemName());
	        application.setPhoneNo(member.getPhoneNo());
	        
    		model.addAttribute("member", member);
			model.addAttribute("app", application);
	        
        }
		
		return "application/introduce";
	}
	
	@RequestMapping(value = { "/application/updateIntroduce" }, method = { RequestMethod.POST, RequestMethod.PATCH }, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public @ResponseBody JsonObject updateIntroduce(IntroduceDTO params,
												HttpServletResponse response, HttpServletRequest request, Authentication authentication) {

		JsonObject jsonObj = new JsonObject();

		try {
			
			MemberDTO member = (MemberDTO) authentication.getPrincipal();
			if(member != null) {
			
				params.setEmailAddr(member.getEmailAddr());
				params.setMemName(member.getMemName());
				params.setMemNo(member.getMemNo());
				params.setPhoneNo(member.getPhoneNo());
				params.setSubmitOk("1");
		        
		        boolean isRegistered = applicationService.registerIntroduce(params);
				jsonObj.addProperty("result", isRegistered);
				
			}
			
		} catch (DataAccessException e) {
			jsonObj.addProperty("message", "데이터베이스 처리 과정에 문제가 발생하였습니다.");

		} catch (Exception e) {
			jsonObj.addProperty("message", "시스템에 문제가 발생하였습니다.");
		}

		return jsonObj;
	}
	
	@PostMapping(value = "/application/registerIntroduce")
	public String registerIntroduce(@ModelAttribute("app") final IntroduceDTO application, 
			BindingResult bindingResult, Model model, 
			HttpServletResponse response, HttpServletRequest request, Authentication authentication) throws Exception {
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		try {
			
			MemberDTO member = (MemberDTO) authentication.getPrincipal();  //userDetail 객체를 가져옴
			if(member != null) {
		        if(member.getActive() != 1) {
		        	return "member/joinConfirm";
		        }
			
		        application.setEmailAddr(member.getEmailAddr());
		        application.setMemName(member.getMemName());
		        application.setMemNo(member.getMemNo());
		        application.setPhoneNo(member.getPhoneNo());
		        
		        //제출완료 submitOk=1
		        application.setSubmitOk("1");
		        
				boolean isRegistered = applicationService.registerIntroduce(application);
				if (isRegistered == false) {
					out.println("<script>alert('저장에 실패하였습니다.'); history.go(-1);</script>");
					out.flush();
				}
				
			}
		} catch (DataAccessException e) {
			out.println("<script>alert('데이터베이스 처리 과정에 문제가 발생하였습니다.'); history.go(-1);</script>");
			out.flush();
			return showMessageWithRedirect("데이터베이스 처리 과정에 문제가 발생하였습니다.", "/", Method.GET, null, model);

		} catch (Exception e) {
			out.println("<script>alert('시스템에 문제가 발생하였습니다.'); history.go(-1);</script>");
			out.flush();
			return showMessageWithRedirect("시스템에 문제가 발생하였습니다.", "/", Method.GET, null, model);
		}

		return showMessageWithRedirect("지원서 저장이 완료되었습니다.", "/application/finish?idx="+application.getIdx(), Method.GET, null, model);
	}
	
	@GetMapping(value = "/application/finish")
	public String openFinish(@RequestParam(value = "idx", required = false) Long idx, Model model, HttpServletResponse response, Authentication authentication) throws Exception {
		
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		ApplicationDTO application = new ApplicationDTO();
		
		if (idx == null) {
			out.println("<script>alert('올바르지 않은 접근입니다.'); history.go(-1);</script>");
			out.flush();
			return showMessageWithRedirect("올바르지 않은 접근입니다.", "/", Method.GET, null, model);
		}
		
		//Authentication 객체를 통해 유저 정보를 가져올 수 있다.
        MemberDTO member = (MemberDTO) authentication.getPrincipal();  //userDetail 객체를 가져옴
        if(member != null) {
	        if(member.getActive() != 1) {
	        	return "member/joinConfirm";
	        }
	        
	        application = applicationService.getApplication(idx, member.getMemNo());
			if (application == null) {
				out.println("<script>alert('없는 지원서이거나 이미 삭제된 지원서입니다.'); history.go(-1);</script>");
				out.flush();
				return showMessageWithRedirect("없는 지원서이거나 이미 삭제된 지원서입니다.", "/", Method.GET, null, model);
			}
	        
			//지원서 미리보기
        	ResumeDTO resume = applicationService.getResume(idx,member.getMemNo());
    		
    		model.addAttribute("member", member);
    		model.addAttribute("resume", resume);
			
			model.addAttribute("idx", idx);
	        model.addAttribute("setupTitle", application.getSetupTitle());
	        model.addAttribute("memName", member.getMemName());
			model.addAttribute("submitDate", application.getSubmitDate());
	        
        }
		
		return "application/finish";
	}
	
	@RequestMapping(value = { "/application/result/{idx}" }, method = { RequestMethod.POST, RequestMethod.GET })
	public String openResult(@PathVariable("idx") Long idx, Model model, HttpServletResponse response, Authentication authentication) throws Exception {
		
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		ApplicationDTO application = new ApplicationDTO();
		String viewPage = "";
		
		try {
			if (idx == null) {
				out.println("<script>alert('올바르지 않은 접근입니다.'); history.go(-1);</script>");
				out.flush();
				return showMessageWithRedirect("올바르지 않은 접근입니다.", "/", Method.GET, null, model);
			}
			
			//Authentication 객체를 통해 유저 정보를 가져올 수 있다.
	        MemberDTO member = (MemberDTO) authentication.getPrincipal();  //userDetail 객체를 가져옴
	        if(member != null) {
		        if(member.getActive() != 1) {
		        	return "member/joinConfirm";
		        }
		        
		        application = applicationService.getResume(idx, member.getMemNo());
				if (application == null) {
					out.println("<script>alert('없는 지원서이거나 이미 삭제된 지원서입니다.'); history.go(-1);</script>");
					out.flush();
					return showMessageWithRedirect("없는 지원서이거나 이미 삭제된 지원서입니다.", "/", Method.GET, null, model);
				}
				
				model.addAttribute("member", member);
				if(application.getJoinOk().equals("0") || application.getJoinOk().equals("1") || application.getJoinOk().equals("2")) {
					viewPage = "application/resultUnpass";
				} else if( application.getJoinOk().equals("3")) {
					model.addAttribute("joinOk", application.getJoinOk());
					viewPage = "application/resultPass";
				} else {
					viewPage = "application/result";
				}
				
	        }
		} catch (Exception e) {
			out.println("<script>alert('시스템에 문제가 발생하였습니다.'); history.go(-1);</script>");
			out.flush();
			return showMessageWithRedirect("시스템에 문제가 발생하였습니다.", "/", Method.GET, null, model);
		}
		
        return viewPage;
		
	}
	
	@GetMapping(value = "/application/delete")
	public String deleteResume( @RequestParam(value = "idx", required = false) Long idx, Model model) {
		if (idx == null) {
			return showMessageWithRedirect("올바르지 않은 접근입니다.", "/mypage/list", Method.GET, null, model);
		}

		try {
			boolean isDeleted = applicationService.deleteResume(idx);
			if (isDeleted == false) {
				return showMessageWithRedirect("지원서 삭제에 실패하였습니다.", "/mypage/list", Method.GET, null, model);
			}
		} catch (DataAccessException e) {
			return showMessageWithRedirect("데이터베이스 처리 과정에 문제가 발생하였습니다.", "/mypage/list", Method.GET, null, model);

		} catch (Exception e) {
			return showMessageWithRedirect("시스템에 문제가 발생하였습니다.", "/mypage/list", Method.GET, null, model);
		}

		return showMessageWithRedirect("지원서 삭제가 완료되었습니다.", "/mypage/list", Method.GET, null, model);
	}
	
}
