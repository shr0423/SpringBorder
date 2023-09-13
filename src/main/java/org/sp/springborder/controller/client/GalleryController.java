package org.sp.springborder.controller.client;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.sp.springborder.domain.Gallery;
import org.sp.springborder.domain.GalleryImg;
import org.sp.springborder.exception.FileException;
import org.sp.springborder.exception.GalleryException;
import org.sp.springborder.exception.GalleryImgException;
import org.sp.springborder.model.gallery.GalleryService;
import org.sp.springborder.util.FileManager;
import org.sp.springborder.util.Pager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

//갤러리와 관련된 요청을 처리하는 하위 컨트롤러
@Controller
public class GalleryController {

	// 컨트롤러가 직접 DAO를 다루게 되면, 트랜잭션 처리까지 부담한다거나,
	// 모델 part업무를 너무 전문적으로 처리하게된다.
	// 컨트롤러와 모델의 업무 경계가 모호해지므로, 즉 코드의 분리가 안되므로 추후 비슷한 업무시
	// 코드를 분리해놓지 않았기 때문에 코드의 재사용성이 떨어진다.
	@Autowired
	private GalleryService galleryService;

	@Autowired
	private FileManager fileManager;

	@Autowired
	private Pager pager;

	// 게시판 목록요청 처리
	@RequestMapping(value = "/gallery/list", method = RequestMethod.GET)
	public ModelAndView getList(HttpServletRequest request) {
		// 3단계 :일시키기
		List galleryList = galleryService.selectAll();

		// 4단계: 목록저장

		pager.init(galleryList, request);

		ModelAndView mav = new ModelAndView("gallery/list");
		mav.addObject("galleryList", galleryList);// 요청객체에 galleryList저장
		mav.addObject("pager", pager);// 요청객체에 pager저장햇다는 것은 포워딩이 필요하다는 것임

		return mav;
	}

	// 글쓰기 폼 요청
	@RequestMapping(value = "/gallery/registform", method = RequestMethod.GET)
	public String getForm() {

		return "gallery/regist";

	}

	// 글쓰기요청을 처리
	@RequestMapping(value = "/gallery/regist", method = RequestMethod.POST)
	public String regist(Gallery gallery, HttpServletRequest request) {
		// 3단계:오라클에 글등록 + 파일업로드

		MultipartFile[] photo = gallery.getPhoto();
		System.out.println("넘겨받은 파일의 수는 " + photo.length);

		// jsp의 application 내장객체는 서블릿 api에서 ServletContext 이단
		// 따라서 이 객체를 얻기위해 HttpSession을 얻어야한다
		ServletContext context = request.getSession().getServletContext();
		String path = context.getRealPath("/resources/data/");
		System.out.println("파일이 저장될 풀경로는 " + path);

		List<GalleryImg> imgList = new ArrayList<GalleryImg>();// 새롭게 생성한 파일명이 누적될곳

		for (int i = 0; i < photo.length; i++) {
			if (photo[i].isEmpty() == false) {// 비어있지 않다면 즉 업로드가 된경우만..
				String filename = photo[i].getOriginalFilename();

				String name = fileManager.save(path, filename, photo[i]);

				GalleryImg galleryImg = new GalleryImg();// empty
				galleryImg.setGallery(gallery);// 이 시점의 gallery DTO에는 아직 gallery_idx는 0인 상태
				galleryImg.setFilename(name);// 새롭게 바뀐 이름으로 대체

				imgList.add(galleryImg);
			}

		}

		// Gallery DTO에 GalleryImg 들을 생성하여 List로 넣어두기
		gallery.setGalleryImgList(imgList);

		// 서비스에서 예외가 발생했을땐, 스프링의 컨트롤러는 예외를 감지하는 이벤트가 발생함
		// 이때 이 이벤트를 처리할 수 있는 메서드를 정의해놓고 개발자가 알맞는 에러 페이지 및 메시지를 구성
		galleryService.regist(gallery);// 글 등록 요청

		return "redirect:/gallery/list";// 형님인 DispatcherServlet이 ViewResolver를 이용하여
		// 이 몸뚱아리를 해석
	}

	// 상세보기 요청처리
	@RequestMapping(value = "/gallery/content", method = RequestMethod.GET)
	public ModelAndView getContent(int gallery_idx) {
		// 3단계:데이터 가져오기
		Gallery gallery = galleryService.select(gallery_idx);

		// 4단계:가져온 레코드 저장(jsp로 가져가려고)
		ModelAndView mav = new ModelAndView();
		mav.addObject("gallery", gallery);
		mav.setViewName("gallery/content");

		return mav;
	}

	@RequestMapping(value = "/gallery/update", method = RequestMethod.POST)
	public String update(Gallery gallery) {
		
		
		galleryService.update(gallery);

		
		return "redirect:/gallery/list";
	}

	// 삭제요청 처리
	@RequestMapping(value = "/gallery/delete", method = RequestMethod.POST)
	public String del(int gallery_idx, String[] filename, HttpServletRequest request) {
		// 3단계:삭제

		ServletContext context = request.getSession().getServletContext();

		for (String str : filename) {
			System.out.println("파일명은 " + str);

			// 삭제될 파일의 풀 경로 얻기
			String path = context.getRealPath("/resources/data/" + str);
			fileManager.remove(path);

		}

		galleryService.delete(gallery_idx);// db삭제

		// 4단계:리스트를 재요청 들어오게 할것이므로 jsp로 가져갈것이 없다
		return "redirect:/gallery/list";

	}

	// 어떠한 예외가 발생했을때 어떤 처리를 할지 아래의 메서드에서 로직 작성
	@ExceptionHandler(FileException.class)
	public ModelAndView handle(FileException e) {
		// jsp에서 에러 메시지를 보여줘야하므로 요청은 유지가 되어야한다.
		// 즉 저장할것ㄷ이 있고, 가져갈 것이 있따.

		ModelAndView mav = new ModelAndView();
		mav.addObject("e", e);// 에러객체 저장
		mav.setViewName("error/result");

		return mav;

	}

	// 어떠한 예외가 발생했을때 어떤 처리를 할지 아래의 메서드에서 로직 작성
	@ExceptionHandler(GalleryException.class)
	public ModelAndView handle(GalleryException e) {
		// jsp에서 에러 메시지를 보여줘야하므로 요청은 유지가 되어야한다.
		// 즉 저장할것ㄷ이 있고, 가져갈 것이 있따.

		ModelAndView mav = new ModelAndView();
		mav.addObject("e", e);// 에러객체 저장
		mav.setViewName("error/result");

		return mav;

	}

	@ExceptionHandler(GalleryImgException.class)
	public ModelAndView handle(GalleryImgException e) {
		// jsp에서 에러 메시지를 보여줘야하므로 요청은 유지가 되어야한다.
		// 즉 저장할것ㄷ이 있고, 가져갈 것이 있따.

		ModelAndView mav = new ModelAndView();
		mav.addObject("e", e);// 에러객체 저장
		mav.setViewName("error/result");

		return mav;

	}
}
