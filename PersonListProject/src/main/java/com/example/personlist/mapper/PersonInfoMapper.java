package com.example.personlist.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.example.personlist.model.PersonInfo;

public class PersonInfoMapper implements RowMapper<PersonInfo> {

	public static final String BASE_SQL = "Select PersonID,FullName,FirstName,LastName,ClassName,Grade from Tb_Person";
	public static final String INSERT_SQL = "insert into Tb_Person(FullName,FirstName,LastName,ClassName,Grade) values(?,?,?,?,?)";
	public static final String DELETE_SQL = "DELETE FROM Tb_Person WHERE PersonID = ?";
	public static final String UPDATE_SQL = "Update Tb_Person set FullName=?,FirstName=?,LastName=?,ClassName=?,Grade=? where PersonID=?";

	public static final String file_INSERT_SQL = "insert into Tb_File(personID,uploadRootPath,name,serverFile) values(?,?,?,?)";
	public static final String file_DELETE_SQL = "DELETE FROM Tb_File WHERE PersonID = ?";
	
	@Override
	public PersonInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub

		int personID = rs.getInt("PersonID");
		String fullName = rs.getString("FullName");
		String firstName = rs.getString("FirstName");
		String lastName = rs.getString("LastName");
		String className = rs.getString("ClassName");
		String grade = rs.getString("Grade");
		
		return new PersonInfo(personID, fullName, firstName, lastName, className, grade);
	}

}
