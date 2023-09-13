package org.sp.springborder.controller.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.sp.springborder.domain.Admin;
import org.sp.springborder.exception.AdminException;
import org.sp.springborder.model.admin.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AdminController {
	
	@Autowired
	private AdminService adminService;
	
	//로그인 폼 요청처리
	@GetMapping("/loginform")
	public ModelAndView loginform() {
		ModelAndView mav=new ModelAndView("admin/loginform");
		
		return mav;
		
	}
	//회원가입 양식요청
	@GetMapping("/signupform")
	public ModelAndView signupform() {
		ModelAndView mav=new ModelAndView("admin/signupform");
		
		return mav;
	}
	
	//관리자 메인 요청 처리
	@GetMapping("/main")
	public ModelAndView getMain() {
		
		ModelAndView mav=new ModelAndView("admin/index");
		
		return mav;
	}
	
	//로그인 요청 처리
	@PostMapping("/login")
	public ModelAndView login(Admin admin, HttpServletRequest request) {
		//3단계:일치하는 관리자가 있는지 조회
		Admin dto=adminService.login(admin);
		
		//4단계:일치하는 관리자가 있다면 세션에 DTO를 담아서 서비스해준다
		HttpSession session=request.getSession();
		session.setAttribute("admin", dto);
		
		ModelAndView mav=new ModelAndView("redirect:/admin/main");
		
		return mav;
	}
	@ExceptionHandler(AdminException.class)
	public ModelAndView handle(AdminException e) {
		ModelAndView mav=new ModelAndView("error/result");
		mav.addObject("e",e);
		return mav;
	}
	
}
