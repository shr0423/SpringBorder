package org.sp.springborder.util;

import java.io.File;
import java.io.IOException;

import org.sp.springborder.exception.FileException;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

//scan에 의해 자동 인스턴스 생성
@Component
public class FileManager {

	
	 //확장자 구하기 
	public static String getExt(String path) {
		int index=path.lastIndexOf(".");
		return path.substring(index+1, path.length());//exclusive
		
		
	}
	//최종적인 파일명 만들기
	public static String createFilename(String filename) {
		long time=System.currentTimeMillis();
		
		return time+"."+getExt(filename);
	}
	
	//파일 저장
	public String save(String path, String filename, MultipartFile mf) throws FileException{
		
		System.out.println(filename);
		
		//파일명 만들기
		String newName=FileManager.createFilename(filename);
		
		
		File file=new File(path+newName);
		
		try {
			mf.transferTo(file);
			
		} catch (IllegalStateException e) {
			throw new FileException("파일저장 실패",e);
			
		} catch (IOException e) {
			throw new FileException("파일저장 실패",e);
		
		}
		
		
		return newName;
	}
	//파일 삭제
	public void remove(String path) throws FileException{
		File file=new File(path);
		boolean result=file.delete();//파일삭제
		if(result==false) {
			throw new FileException("파일삭제 실패입니다");
		}
	}
}
