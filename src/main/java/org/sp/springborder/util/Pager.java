package org.sp.springborder.util;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class Pager {
	private int totalRecord;//총 레코드 수
	private int pageSize=10;//페이지당 보여질 레코드수
	private int totalPage;//총 페이지 수
	private int blockSize=10;//블럭당 보여질 페이지 수
	private int currentPage=1;//현재 페이지
	private int firstPage;//블럭당 for문의 시작페이지
	private int lastPage;//블럭당 for문의 마지막 페이지
	private int curPos;//페이징 List의 시작 index
	private int num;//페이지 당 시작 번호
	
	public void init(List list, HttpServletRequest request) {
		
		totalRecord=list.size();
		totalPage=(int)Math.ceil((float)totalRecord/pageSize);
		if(request.getParameter("currentPage")!=null) {
			currentPage=Integer.parseInt(request.getParameter("currentPage"));
		}
		firstPage=currentPage-((currentPage-1)%blockSize);
		lastPage=firstPage+(blockSize-1);
		curPos=(currentPage-1)*pageSize;
		num=totalRecord-curPos;
	}
}
