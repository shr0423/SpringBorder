package org.sp.springborder.model.admin;

import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.sp.springborder.domain.Admin;
import org.sp.springborder.mybatis.MybatisConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MybatisAdminDAO implements AdminDAO{
	
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	
	public Admin login(Admin admin) {
		
		Admin dto=sqlSessionTemplate.selectOne("Admin.login",admin);
		
		
		return dto;
	}

}
