package com.example.personlist.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.example.personlist.model.PersonInfo;

public class PersonInfoMapper implements RowMapper<PersonInfo>{

	public static final String  BASE_SQL ="Select PersonID,FullName,FirstName,LastName,ClassName,Grade from Tb_Person";
	@Override
	public PersonInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		
		int personID = rs.getInt("PersonID");
		String fullName=rs.getString("FullName");
		String firstName= rs.getString("FirstName");
		String lastName = rs.getString("LastName");
		String className = rs.getString("ClassName");
		String grade = rs.getString("Grade");
		return new PersonInfo(personID, fullName, firstName, lastName, className, grade);
	}

}
