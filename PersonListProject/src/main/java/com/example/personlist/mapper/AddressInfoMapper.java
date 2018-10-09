package com.example.personlist.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.example.personlist.model.AddressInfo;


public class AddressInfoMapper implements RowMapper<AddressInfo>
{
	public static final String AD_UPDATE_SQL ="Update Tb_Address set Address1=?,Address2=? where AddressID=?";
	
	public static final String AD_SELECT_SQL = "Select * from Tb_Address";
	@Override
	public AddressInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		int addressID = rs.getInt("AddressID");
		String address1=rs.getString("Address1");
		String address2= rs.getString("Address2");
		return new AddressInfo(addressID,address1,address2);
	}
	
}