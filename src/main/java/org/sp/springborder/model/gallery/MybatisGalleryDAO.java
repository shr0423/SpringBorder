package org.sp.springborder.model.gallery;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.sp.springborder.domain.Gallery;
import org.sp.springborder.exception.GalleryException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MybatisGalleryDAO implements GalleryDAO{

	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	
	
	@Override
	public void insert(Gallery gallery) throws GalleryException{
		
		
		int result=sqlSessionTemplate.insert("Gallery.insert",gallery);
		
		
		//result=0;//일부러 에러테스트
		
		if(result==0) {
			//개발자가 일부러 관련있는 에러를 일으키자
			
			throw new GalleryException("갤러리등록을 실패했어요");
		}
	}

	@Override
	public List selectAll() {
	
		List list=sqlSessionTemplate.selectList("Gallery.selectAll");
		
		return list;
	}

	@Override
	public Gallery select(int gallery_idx) {
		
		Gallery gallery=sqlSessionTemplate.selectOne("Gallery.select",gallery_idx);
	
		return gallery;
	}

	@Override
	public void update(Gallery gallery) {
		int result=sqlSessionTemplate.update("Gallery.update",gallery);
		
		
		if(result<1) {//수정실패
			throw new GalleryException("수정실패");
		}
	}

	@Override
	public void delete(int gallery_idx) throws GalleryException{
		
		int result=sqlSessionTemplate.delete("Gallery.delete",gallery_idx);
		
		
		if(result<1) {//삭제실패
			throw new GalleryException("삭제실패");
		}
		
	}

}
