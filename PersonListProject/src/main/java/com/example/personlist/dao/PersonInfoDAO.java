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
		String sql = PersonInfoMapper.BASE_SQL +"where PersonID=?";
		
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
}
