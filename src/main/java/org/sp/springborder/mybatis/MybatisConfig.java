package org.sp.springborder.mybatis;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

//mybatis의 설정파일을 읽어들여, SqlSessionFactory를 얻기위한 객체 선언
//이 객체는 어플리케이션에서 한번만 인스턴스를 생성해야 하므로, 싱글턴으로 선언하자
public class MybatisConfig {
	private static MybatisConfig instance;
	private SqlSessionFactory sqlSessionFactory;
	
	private MybatisConfig() {
		String resource = "org/sp/springborder/mybatis/config.xml";
		InputStream inputStream=null;
		try {
			inputStream = Resources.getResourceAsStream(resource);
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
	}
	
	public static MybatisConfig getInstance() {
		if(instance==null) {
			instance=new MybatisConfig();
		}
		return instance;
	}
	//팩토리부터 SqlSession 반환
	public SqlSession getSqlSession() {
		return sqlSessionFactory.openSession();
	}
	//SqlSession 반납
	public void release(SqlSession sqlSession) {
		sqlSession.close();
	}
}
