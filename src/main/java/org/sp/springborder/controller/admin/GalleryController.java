package org.sp.springborder.controller.admin;

import java.util.List;

import org.sp.springborder.model.gallery.GalleryService;
import org.sp.springborder.util.Pager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

//관리자모드에서 갤러리와 관련된 요청을 처리하는 컨트롤러
@Controller
public class GalleryController {
	
	@Autowired
	private GalleryService galleryService;
	
	@Autowired
	private Pager pager;
	
	//갤러리 목록 요청 처리
	@GetMapping("/gallery/list")
	public ModelAndView getList() {
		//3단계:모든 레코드 가져오기
		List galleryList=galleryService.selectAll();
		
		//4단계:저장
		ModelAndView mav=new ModelAndView();
		mav.addObject("galleryList",galleryList);
		mav.addObject("pager",pager);
		mav.setViewName("admin/gallery/list");
		
		return mav;
	}
}
