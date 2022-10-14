package com.vegemil.controller;

import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
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
import com.vegemil.service.ApplicationService;
import com.vegemil.util.UiUtils;

@Controller
public class ApplicationController extends UiUtils {

	@Autowired
	private ApplicationService applicationService;
	
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
			application.setIdx(idx);
			application.setEmailAddr(member.getEmailAddr());
	        application.setMemName(member.getMemName());
	        application.setPhoneNo(member.getPhoneNo());
	        
			model.addAttribute("app", application);
	        
        }
		
		return "application/personalInfo";
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
				Path savePath = Paths.get(request.getServletContext().getRealPath("/") + "/WEB-INF/classes/static/recruit/img/" + savefileName);
				
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
			application.setIdx(idx);
			application.setEmailAddr(member.getEmailAddr());
	        application.setMemName(member.getMemName());
	        application.setPhoneNo(member.getPhoneNo());
	        
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
	
	@RequestMapping(value = { "/application/updateAcademy" }, method = { RequestMethod.POST, RequestMethod.PATCH })
	public @ResponseBody JsonObject updateAcademy(@PathVariable(value = "idx", required = false) Long idx, @RequestBody final AcademyInfoDTO params, Authentication authentication) {

		JsonObject jsonObj = new JsonObject();

		try {
			
			MemberDTO member = (MemberDTO) authentication.getPrincipal();
			if(member != null) {
			
				params.setEmailAddr(member.getEmailAddr());
				params.setMemName(member.getMemName());
				params.setMemNo(member.getMemNo());
				params.setPhoneNo(member.getPhoneNo());
		        
		        boolean isRegistered = applicationService.registerAcademyInfo(params);
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
			application.setIdx(idx);
			application.setEmailAddr(member.getEmailAddr());
	        application.setMemName(member.getMemName());
	        application.setPhoneNo(member.getPhoneNo());
	        
			model.addAttribute("app", application);
	        
        }
		
		return "application/qualification";
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
			application.setIdx(idx);
			application.setEmailAddr(member.getEmailAddr());
	        application.setMemName(member.getMemName());
	        application.setPhoneNo(member.getPhoneNo());
	        
			model.addAttribute("app", application);
	        
        }
		
		return "application/career";
	}
	
	@PostMapping(value = "/application/registerCareer")
	public String registerCareer(@ModelAttribute("app") final CareerDTO application, 
			BindingResult bindingResult, @RequestParam("portFile") MultipartFile fileName, Model model, 
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
				Path savePath = Paths.get("C:/Users/kid4290/git/vegemil_recruit/vegemil-recruit/src/main/resources/static/recruit/port/" + savefileName);
				
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
			application.setIdx(idx);
			application.setEmailAddr(member.getEmailAddr());
	        application.setMemName(member.getMemName());
	        application.setPhoneNo(member.getPhoneNo());
	        
			model.addAttribute("app", application);
	        
        }
		
		return "application/introduce";
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
	        
			model.addAttribute("idx", idx);
	        model.addAttribute("setupTitle", application.getSetupTitle());
	        model.addAttribute("memName", member.getMemName());
			model.addAttribute("submitDate", application.getSubmitDate());
	        
        }
		
		return "application/finish";
	}
	
	/*
	@GetMapping(value = "/application/active")
	public void changeActive(@RequestParam(value = "idx", required = false) Long idx, @RequestParam(value = "display", required = false) int display, HttpServletResponse response) throws Exception {
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		if (idx == null) {
			out.println("<script>alert('올바르지 않은 접근입니다.'); history.go(-1);</script>");
			out.flush();
		}
		ApplicationDTO application = applicationService.getApplicationDetail(idx);
		application.setIdx(idx);
		boolean isRegistered = applicationService.registerApplication(application);
		if (isRegistered == false) {
			out.println("<script>alert('육아정보 진열 변경에 실패하였습니다.'); window.location='/application/applicationList';</script>");
			out.flush();
		}
	}
	
	@GetMapping(value = "/application/check")
	public void changeCheck(@RequestParam(value = "idx", required = false) Long idx, @RequestParam(value = "display", required = false) int display, HttpServletResponse response) throws Exception {
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		if (idx == null) {
			out.println("<script>alert('올바르지 않은 접근입니다.'); history.go(-1);</script>");
			out.flush();
		}
		ApplicationDTO application = applicationService.getApplicationDetail(idx);
		application.setIdx(idx);
		boolean isRegistered = applicationService.registerApplication(application);
		if (isRegistered == false) {
			out.println("<script>alert('메인화면 진열 변경에 실패하였습니다.'); window.location='/application/applicationList';</script>");
			out.flush();
		}
	}
	
	@RequestMapping(value = "/application/{viewName}")
    public String adminMain(@PathVariable(value = "viewName", required = false) String viewName)throws Exception{
		// View attribute
		return "application/"+viewName;
    }

	@GetMapping(value = "/board/write.do")
	public String openBoardWrite(@ModelAttribute("params") BoardDTO params, @RequestParam(value = "idx", required = false) Long idx, Model model) {
		if (idx == null) {
			model.addAttribute("board", new BoardDTO());
		} else {
			BoardDTO board = boardService.getBoardDetail(idx);
			if (board == null || "Y".equals(board.getDeleteYn())) {
				return showMessageWithRedirect("없는 지원서이거나 이미 삭제된 지원서입니다.", "/adminBoard.do", Method.GET, null, model);
			}
			model.addAttribute("board", board);
		}

		return "board/write";
	}

	@PostMapping(value = "/board/register.do")
	public String registerBoard(@ModelAttribute("params") final BoardDTO params, Model model) {
		Map<String, Object> pagingParams = getPagingParams(params);
		try {
			boolean isRegistered = boardService.registerBoard(params);
			if (isRegistered == false) {
				return showMessageWithRedirect("지원서 등록에 실패하였습니다.", "/adminBoard.do", Method.GET, pagingParams, model);
			}
		} catch (DataAccessException e) {
			return showMessageWithRedirect("데이터베이스 처리 과정에 문제가 발생하였습니다.", "/adminBoard.do", Method.GET, pagingParams, model);

		} catch (Exception e) {
			return showMessageWithRedirect("시스템에 문제가 발생하였습니다.", "/adminBoard.do", Method.GET, pagingParams, model);
		}

		return showMessageWithRedirect("지원서 등록이 완료되었습니다.", "/adminBoard.do", Method.GET, pagingParams, model);
	}

	@GetMapping(value = "/board.do")
	public String openBoardList(@ModelAttribute("params") BoardDTO params, Model model) {
		List<BoardDTO> boardList = boardService.getBoardList(params);
		model.addAttribute("boardList", boardList);

		return "board";
	}
	
	@GetMapping(value = "/adminBoard.do")
	public String adminBoardList(@ModelAttribute("params") BoardDTO params, Model model) {
		List<BoardDTO> boardList = boardService.getBoardList(params);
		model.addAttribute("boardList", boardList);

		return "adminBoard";
	}

	@GetMapping(value = "/board/view.do")
	public String openBoardDetail(@ModelAttribute("params") BoardDTO params, @RequestParam(value = "idx", required = false) Long idx, Model model) {
		if (idx == null) {
			return showMessageWithRedirect("올바르지 않은 접근입니다.", "board", Method.GET, null, model);
		}

		BoardDTO board = boardService.getBoardDetail(idx);
		if (board == null || "Y".equals(board.getDeleteYn())) {
			return showMessageWithRedirect("없는 지원서이거나 이미 삭제된 지원서입니다.", "board", Method.GET, null, model);
		}
		model.addAttribute("board", board);

		return "board/view";
	}

	@PostMapping(value = "/board/delete.do")
	public String deleteBoard(@ModelAttribute("params") BoardDTO params, @RequestParam(value = "idx", required = false) Long idx, Model model) {
		if (idx == null) {
			return showMessageWithRedirect("올바르지 않은 접근입니다.", "/adminBoard.do", Method.GET, null, model);
		}

		Map<String, Object> pagingParams = getPagingParams(params);
		try {
			boolean isDeleted = boardService.deleteBoard(idx);
			if (isDeleted == false) {
				return showMessageWithRedirect("지원서 삭제에 실패하였습니다.", "/adminBoard.do", Method.GET, pagingParams, model);
			}
		} catch (DataAccessException e) {
			return showMessageWithRedirect("데이터베이스 처리 과정에 문제가 발생하였습니다.", "/adminBoard.do", Method.GET, pagingParams, model);

		} catch (Exception e) {
			return showMessageWithRedirect("시스템에 문제가 발생하였습니다.", "/adminBoard.do", Method.GET, pagingParams, model);
		}

		return showMessageWithRedirect("지원서 삭제가 완료되었습니다.", "/adminBoard.do", Method.GET, pagingParams, model);
	}
*/
	/*
	@GetMapping(value = "/application/babyQnaList")
	public String openBabyQnaList(@ModelAttribute("params") ApplicationDTO params, Model model) {
		List<ApplicationDTO> babyQnaList = applicationService.getBabyQnaList(params);
		model.addAttribute("babyQnaList", babyQnaList);

		return "application/babyQnaList";
	}
	
	@PostMapping(value = "/application/babyQnaRegist")
	public String registerBabyQna(@ModelAttribute("params") final ApplicationDTO params, @RequestParam("fileName") MultipartFile fileName, Model model, HttpServletResponse response, HttpServletRequest request) throws Exception {
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		try {
			String originalName = fileName.getOriginalFilename();
			String file = originalName.substring(originalName.lastIndexOf("\\") + 1);
			String uuid = UUID.randomUUID().toString();
			String savefileName = uuid + "_" + file;
			Path savePath = Paths.get(request.getSession().getServletContext().getRealPath("/") + "/WEB-INF/classes/static/upload/applicationQna/" + savefileName);
			
			fileName.transferTo(savePath);
			params.setMbsImage(savefileName);
			boolean isRegistered = applicationService.registerBabyQna(params);
			if (isRegistered == false) {
				out.println("<script>alert('지원서 등록에 실패하였습니다.'); history.go(-1);</script>");
				out.flush();
			}
		} catch (DataAccessException e) {
			out.println("<script>alert('데이터베이스 처리 과정에 문제가 발생하였습니다.'); history.go(-1);</script>");
			out.flush();
			return showMessageWithRedirect("데이터베이스 처리 과정에 문제가 발생하였습니다.", "/application/babyQnaList", Method.GET, null, model);

		} catch (Exception e) {
			out.println("<script>alert('시스템에 문제가 발생하였습니다.'); history.go(-1);</script>");
			out.flush();
			return showMessageWithRedirect("시스템에 문제가 발생하였습니다.", "/application/babyQnaList", Method.GET, null, model);
		}
		
		out.println("<script>alert('지원서 등록이 완료되었습니다.'); window.location='/application/babyQnaList';</script>");
		out.flush();

		return showMessageWithRedirect("지원서 등록이 완료되었습니다.", "/application/babyQnaList", Method.GET, null, model);
	}
	
	@GetMapping(value = "/application/babyQnaDetail")
	public String openbabyQnaDetail(@ModelAttribute("params") ApplicationDTO params, @RequestParam(value = "mbsIdx", required = false) Long mbsIdx, Model model, HttpServletResponse response) throws Exception {
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		if (mbsIdx == null) {
			out.println("<script>alert('올바르지 않은 접근입니다.'); history.go(-1);</script>");
			out.flush();
			return showMessageWithRedirect("올바르지 않은 접근입니다.", "application/babyQnaList", Method.GET, null, model);
		}
		ApplicationDTO babyQna = applicationService.getBabyQnaDetail(mbsIdx);
		if (babyQna == null) {
			out.println("<script>alert('없는 지원서이거나 이미 삭제된 지원서입니다.'); history.go(-1);</script>");
			out.flush();
			return showMessageWithRedirect("없는 지원서이거나 이미 삭제된 지원서입니다.", "application/babyQnaList", Method.GET, null, model);
		}
		babyQna.setMbsIdx(mbsIdx);
		model.addAttribute("babyQna", babyQna);

		return "application/babyQnaDetail";
	}
	
	@PostMapping(value = "/application/babyQnaUpdate")
	public String updateBabyQna(@ModelAttribute("params") final ApplicationDTO params, @RequestParam("fileName") MultipartFile fileName, Model model, HttpServletResponse response, HttpServletRequest request) throws Exception {
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		try {
				if(!fileName.getOriginalFilename().equals("")) {
					String originalName = fileName.getOriginalFilename();
					String file = originalName.substring(originalName.lastIndexOf("\\") + 1);
					String uuid = UUID.randomUUID().toString();
					String savefileName = uuid + "_" + file;
					Path savePath = Paths.get(request.getSession().getServletContext().getRealPath("/") + "/WEB-INF/classes/static/upload/applicationQna/" + savefileName);
					
					fileName.transferTo(savePath);
					params.setMbsImage(savefileName);
				}
			boolean isRegistered = applicationService.registerBabyQna(params);
			if (isRegistered == false) {
				out.println("<script>alert('지원서 수정에 실패하였습니다.'); history.go(-1);</script>");
				out.flush();
			}
		} catch (DataAccessException e) {
			out.println("<script>alert('데이터베이스 처리 과정에 문제가 발생하였습니다.'); history.go(-1);</script>");
			out.flush();
			return showMessageWithRedirect("데이터베이스 처리 과정에 문제가 발생하였습니다.", "/application/babyQnaList", Method.GET, null, model);

		} catch (Exception e) {
			out.println("<script>alert('시스템에 문제가 발생하였습니다.'); history.go(-1);</script>");
			out.flush();
			return showMessageWithRedirect("시스템에 문제가 발생하였습니다.", "/application/babyQnaList", Method.GET, null, model);
		}
		
		out.println("<script>alert('지원서 수정이 완료되었습니다.'); window.location='/application/babyQnaList';</script>");
		out.flush();

		return showMessageWithRedirect("지원서 수정이 완료되었습니다.", "/application/babyQnaList", Method.GET, null, model);
	}
	
	@GetMapping(value = "/application/babyQnaActive")
	public void changeQnaActive(@RequestParam(value = "mbsIdx", required = false) Long mbsIdx, @RequestParam(value = "display", required = false) int display, HttpServletResponse response) throws Exception {
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		if (mbsIdx == null) {
			out.println("<script>alert('올바르지 않은 접근입니다.'); history.go(-1);</script>");
			out.flush();
		}
		ApplicationDTO babyQna = applicationService.getBabyQnaDetail(mbsIdx);
		babyQna.setMbsIdx(mbsIdx);
		babyQna.setMbsActive(display);
		boolean isRegistered = applicationService.registerBabyQna(babyQna);
		if (isRegistered == false) {
			out.println("<script>alert('육아정보 진열 변경에 실패하였습니다.'); window.location='/application/babyQnaList';</script>");
			out.flush();
		}
	}
	
	@GetMapping(value = "/application/babyQnaCheck")
	public void changeQnaCheck(@RequestParam(value = "mbsIdx", required = false) Long mbsIdx, @RequestParam(value = "display", required = false) int display, HttpServletResponse response) throws Exception {
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		if (mbsIdx == null) {
			out.println("<script>alert('올바르지 않은 접근입니다.'); history.go(-1);</script>");
			out.flush();
		}
		ApplicationDTO babyQna = applicationService.getBabyQnaDetail(mbsIdx);
		babyQna.setMbsIdx(mbsIdx);
		babyQna.setMbsCheck(display);
		boolean isRegistered = applicationService.registerBabyQna(babyQna);
		if (isRegistered == false) {
			out.println("<script>alert('메인화면 진열 변경에 실패하였습니다.'); window.location='/application/babyQnaList';</script>");
			out.flush();
		}
	}
	*/
}
