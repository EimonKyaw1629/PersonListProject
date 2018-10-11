package com.example.personlist.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.example.personlist.model.AddressInfo;


public class AddressInfoMapper implements RowMapper<AddressInfo>
{
	public static final String AD_UPDATE_SQL ="Update Tb_Address set PersonID=?,Address=? where PersonID=?";
	public static final String AD_SELECT_SQL = "Select * from  Tb_Address";
	@Override
	public AddressInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
		int addressID = rs.getInt("AddressID");
		int personID=rs.getInt("PersonID");
		String address2= rs.getString("Address");
		return new AddressInfo(addressID,personID,address2);
	}
	
}