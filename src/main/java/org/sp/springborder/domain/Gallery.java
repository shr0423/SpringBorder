package org.sp.springborder.domain;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class Gallery {
	
	private int gallery_idx;
	private String title;
	private String writer;
	private String regdate;
	private int hit;
	private String content;
	
	//바이너리 파일을 받을 수 있는 자료형
	MultipartFile[] photo;
	
	List<GalleryImg> galleryImgList;
}
