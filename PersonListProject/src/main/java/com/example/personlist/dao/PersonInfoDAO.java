package com.example.personlist.dao;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.personlist.mapper.PersonInfoMapper;
import com.example.personlist.model.PersonInfo;


@Repository
@Transactional
public class PersonInfoDAO extends JdbcDaoSupport{

	@Autowired
	public PersonInfoDAO(DataSource ds)
	{
		this.setDataSource(ds);
	}
	
	public  List<PersonInfo> getPersonInfo()
	{
		String sql = PersonInfoMapper.BASE_SQL;
		Object[] params = new Object[] {};
		PersonInfoMapper mapper = new PersonInfoMapper();
		List<PersonInfo> list = this.getJdbcTemplate().query(sql, params,mapper);
		return list;
	}
	
	public PersonInfo findPersonInfo(int pid)
	{
		String sql = PersonInfoMapper.BASE_SQL +" where PersonID=?";
		
		Object[] params = new Object[] {pid};
		PersonInfoMapper mapper = new PersonInfoMapper();
		try
		{
			PersonInfo info = this.getJdbcTemplate().queryForObject(sql, params,mapper);
			return info;
		}
		catch(EmptyResultDataAccessException ex)
		{
			return null;
		}
	}
	
	public List<PersonInfo> getSearchPersonInfo(String fullname,String classname)
	{
		String sql=null;
		Object[] params= null;
		PersonInfoMapper mapper = new PersonInfoMapper();
		if(fullname !="" && classname!="")
		{
			sql=PersonInfoMapper.BASE_SQL +" where FullName=? and ClassName=?";
			params= new Object[] {fullname,classname};
		}
		else if (fullname !="" && classname=="")
		{
			sql=PersonInfoMapper.BASE_SQL +" where FullName=? ";
			params= new Object[] {fullname};
		}
		else if(fullname =="" && classname!="")
		{
			sql=PersonInfoMapper.BASE_SQL +" where  ClassName=?";
			params= new Object[] {classname};
		}
		try
		{
			List<PersonInfo> info = this.getJdbcTemplate().query(sql, params,mapper);
			return info;
		}
		catch(EmptyResultDataAccessException ex)
		{
			return null;
		}
	}
	
	public void editPersonInfo(int pid,String fullname,String firstname,String lastname,String classname,String grade)//PersonInfo info)//
	{
		String sql = "Update Tb_Person set FullName=?,FirstName=?,LastName=?,ClassName=?,Grade=? where PersonID=?";
		Object[] params = new Object[]{fullname,firstname,lastname,classname,grade,pid};// {info.getFullName(),info.getFirstName(),info.getLastName(),info.getClassName(),info.getGrade(),pid}; //
		PersonInfoMapper mapper = new PersonInfoMapper();
		//this.getJdbcTemplate().update(sql,params,mapper);
		getJdbcTemplate().update(sql, params);
	}
	
	public void deleteInfo(int pid) {
		String sql = PersonInfoMapper.DELETE_SQL;
		
		Object[] params = new Object[] {pid};
		
		getJdbcTemplate().update(sql, params);
	
	}
	
	
	public void insertInfo(PersonInfo info) {
		String sql = PersonInfoMapper.INSERT_SQL;
		Object[] params = new Object[] {info.getFullName(),info.getFirstName(),info.getLastName(),info.getClassName(),info.getGrade()};
		getJdbcTemplate().update(sql, params);
	
	}
	
	

	/*public void insertInfo(InsertPersonInfo info) {
	String sql = PersonInfoMapper.INSERT_SQL;
	Object[] params = new Object[] {info};
	getJdbcTemplate().update(sql, params);
	
	}*/
}
