package com.example.personlist.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.example.personlist.model.ConbineModel;

public class ConbineModelMapper {
	public ConbineModel mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub

		int personID = rs.getInt("PersonID");
		String fullName = rs.getString("FullName");
		String firstName = rs.getString("FirstName");
		String lastName = rs.getString("LastName");
		String className = rs.getString("ClassName");
		String grade = rs.getString("Grade");
		String address = rs.getString("Address");		
		String gender = rs.getString("Gender");
		return new ConbineModel(personID, fullName, firstName, lastName, className, grade,address,gender);
	}
}
