package org.sp.springborder.exception;

//나만의 예외 객체 정의하기
public class AdminException extends RuntimeException{
	
	//Throwable은 예외의 최상위 객체이다
	public AdminException(String msg) {
		super(msg);
	}
	public AdminException(String msg, Throwable e) {
		super(msg,e);
	}
}
