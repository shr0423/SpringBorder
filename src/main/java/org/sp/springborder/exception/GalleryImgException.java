package org.sp.springborder.exception;

//나만의 예외 객체 정의하기
public class GalleryImgException extends RuntimeException{
	
	//Throwable은 예외의 최상위 객체이다
	public GalleryImgException(String msg) {
		super(msg);
	}
	public GalleryImgException(String msg, Throwable e) {
		super(msg,e);
	}
}
