package org.sp.springborder.model.gallery;

import java.util.List;

import org.sp.springborder.domain.Gallery;
import org.sp.springborder.domain.GalleryImg;
import org.sp.springborder.exception.GalleryException;
import org.sp.springborder.exception.GalleryImgException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
//서비스 인터페이스를 구현한 하위 서비스 객체
public class GalleryServiceImpl implements GalleryService{

	//DAO는 서비스에서 보유해야한다.model영역의 업무이므로
	//따라서 두개이상의 테이블에 대한 트랜잭션 DML상황에 있어 트랜잭션 처리또한 서비스 객체의 몫이다.
	@Autowired
	private GalleryDAO galleryDAO;
	
	@Autowired
	private GalleryImgDAO galleryImgDAO;
	
	//DAO로부터 전달받은 예외객체는 서비스가 방치하지말고 Controller까지 전달을 해줘야
	//웹브라우저인 클라이언트에게 적절한 에러 화면을 제공할 수 있다.
	
	@Transactional(propagation = Propagation.REQUIRED)
	public void regist(Gallery gallery) throws GalleryException, GalleryImgException{
		//두개의 DAO를 이용하여 글 등록 업무처리
		galleryDAO.insert(gallery);//mybatis에 의해 gallery_idx
		
		List<GalleryImg> imgList=gallery.getGalleryImgList();
		
		for(int i=0;i<imgList.size();i++) {
			GalleryImg galleryImg=imgList.get(i);
			galleryImgDAO.insert(galleryImg);//이미지 테이블에 insert
		}
		
	}

	@Override
	public List selectAll() {
		return galleryDAO.selectAll();
		
	}

	@Override
	public Gallery select(int gallery_idx) {
		
		return galleryDAO.select(gallery_idx);
	}

	@Override
	public void update(Gallery gallery) {
		galleryDAO.update(gallery);
	}

	@Override
	public void delete(int gallery_idx) throws GalleryException, GalleryImgException{
		
		//이미지레코드 삭제
		galleryImgDAO.deleteByGalleryIdx(gallery_idx);
		//갤러리 삭제
		galleryDAO.delete(gallery_idx);
	}

	
}
