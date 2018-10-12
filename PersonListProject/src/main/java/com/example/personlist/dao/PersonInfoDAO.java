package com.example.personlist.dao;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.tomcat.jni.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.example.personlist.mapper.AddressInfoMapper;
import com.example.personlist.mapper.PersonInfoMapper;
import com.example.personlist.model.AddressInfo;
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
	
	public List<AddressInfo> getAddressInfo()
	{
		String sql = AddressInfoMapper.AD_SELECT_SQL;
		Object[] params = new Object[] {};
		AddressInfoMapper mapper = new AddressInfoMapper();
		List<AddressInfo> list = this.getJdbcTemplate().query(sql, params,mapper);
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
	
	public List<AddressInfo> findAddressInfo(int aid)
	{
		String asql = AddressInfoMapper.AD_SELECT_SQL +" where PersonID=?";
		
		Object[] params = new Object[] {aid};
		AddressInfoMapper mapper = new AddressInfoMapper();
		try
		{
			
			List<AddressInfo> list = this.getJdbcTemplate().query(asql, params,mapper);;
			
			return list;
		}
		catch(EmptyResultDataAccessException ex)
		{
			return null;
		}
	}
	
	public void deleteInfo(int pid) {
		String sql = PersonInfoMapper.DELETE_SQL;
		
		Object[] params = new Object[] {pid};
		
		getJdbcTemplate().update(sql, params);
	
	}
	
	
	public void insertInfo(PersonInfo personinfo) {
		String sql = PersonInfoMapper.INSERT_SQL;
		Object[] params = new Object[] {personinfo.getFullName(),personinfo.getFirstName(),personinfo.getLastName(),personinfo.getClassName(),personinfo.getGrade()};
		getJdbcTemplate().update(sql, params);
		
		/*List<PersonInfo> list = getPersonInfo();
		String AddrSql = AddressInfoMapper.AD_INSERT_SQL;
		Object[] paramsAddr = new Object[] {list.get(list.size()-1).getPersonID(),personinfo.getAddress()};
		getJdbcTemplate().update(AddrSql, paramsAddr);*/
		
	}
	public List<PersonInfo> getSearchPersonInfo(String fullname,String classname)
	{
		String sql=null;
		Object[] params= null;
		PersonInfoMapper mapper = new PersonInfoMapper();
		//sql = PersonInfoMapper.BASE_SQL +" where (@a is null or  FullName=@a) and (ISNUlLL(@b) or ClassName=@b)" ;
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
		else
		{
			sql=PersonInfoMapper.BASE_SQL ;
			params= new Object[] {};
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
	
	public void editPersonInfo(PersonInfo info)
	{
		PersonInfo P = this.findPersonInfo(info.getPersonID());
		List<AddressInfo> a = this.findAddressInfo(info.getPersonID());
		if(P!=null)
		{
			String sql = PersonInfoMapper.UPDATE_SQL;
			
			Object[] params = new Object[] {info.getFullName(),info.getFirstName(),info.getLastName(),info.getClassName(),info.getGrade(),info.getPersonID()};
			
			getJdbcTemplate().update(sql, params);
		}
		if(a!=null)
		{
				String sql = AddressInfoMapper.AD_UPDATE_SQL;
			
			Object[] params = new Object[] {info.getPersonID(),info.getAddress(),info.getPersonID()};
			
			getJdbcTemplate().update(sql, params);
		}
		else
		{
			this.insertInfo(info);
		}
		
	}
	
	
}
