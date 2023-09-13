package org.sp.springborder.exception;

//나만의 예외 객체 정의하기
public class FileException extends RuntimeException{
	
	//Throwable은 예외의 최상위 객체이다
	public FileException(String msg) {
		super(msg);
	}
	public FileException(String msg, Throwable e) {
		super(msg,e);
	}
}
