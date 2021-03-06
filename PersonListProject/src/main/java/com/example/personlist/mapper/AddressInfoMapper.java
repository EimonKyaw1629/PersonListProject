package com.example.personlist.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.example.personlist.model.AddressInfo;


public class AddressInfoMapper implements RowMapper<AddressInfo>
{
	public static final String AD_UPDATE_SQL = "Update Tb_Address set PersonID=?,Address=? where PersonID=?";
	public static final String AD_SELECT_SQL = "Select * from  Tb_Address";
	public static final String AD_INSERT_SQL = "insert into Tb_Address(personID,address) values(?,?)";
	public static final String AD_DELETE_SQL = "DELETE FROM Tb_Address WHERE PersonID = ?";
	public static final String AD_ADDELETE_SQL = "DELETE FROM Tb_Address WHERE AddressID = ?";
	public static final String XML_SELECT = "Select PersonID,FullName,ClassName" + 
			",SUBSTRING((SELECT ',' + Address as 'data()'  FROM Tb_Address where Tb_Address.PersonID = Tb_Person.PersonID FOR XML PATH('')), 2, 999999)" + 
			" from Tb_Person";
	
	@Override
	public AddressInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
		int addressID = rs.getInt("AddressID");
		int personID=rs.getInt("PersonID");
		String address2= rs.getString("Address");
		return new AddressInfo(addressID,personID,address2);
	}
	
}