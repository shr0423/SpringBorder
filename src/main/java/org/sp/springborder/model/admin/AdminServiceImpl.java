package org.sp.springborder.model.admin;

import org.sp.springborder.domain.Admin;
import org.sp.springborder.exception.AdminException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService{

	@Autowired
	private AdminDAO adminDAO;
	
	
	public Admin login(Admin admin) throws AdminException{
		
		Admin dto=adminDAO.login(admin);
		
		if(dto==null) {
			throw new AdminException("로그인정보가 올바르지않습니다.");
		}
		return dto;
	}
}
