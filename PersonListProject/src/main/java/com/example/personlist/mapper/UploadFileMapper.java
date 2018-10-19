package com.example.personlist.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.example.personlist.model.AddressInfo;
import com.example.personlist.model.MyUploadForm;

public class UploadFileMapper implements RowMapper<MyUploadForm>{

	public static final String file_UPDATE_SQL = "Update Tb_File set uploadRootPath=?,name=?,serverFile=? where PersonID=?";
	public static final String file_Delete_Sql = "Delete from Tb_File where PersonID=?";
	public static final String file_XMLSelect_Sql = "SELECT  FileID,PersonID,uploadRootPath,name,serverFile  FROM Tb_Address FOR XML AUTO";
	public static final String file_INSERT_SQL = "insert into Tb_File(personID,uploadRootPath,name,serverFile) values(?,?,?,?)";
	public static final String file_Select_Sql = "SELECT  FileID,PersonID,uploadRootPath,name,serverFile  FROM Tb_File ";
	@Override
	public MyUploadForm mapRow(ResultSet rs, int rowNum) throws SQLException {
		int fileID = rs.getInt("FileID");
		int personID=rs.getInt("PersonID");
		
		String uploadPath = rs.getString("uploadRootPath");
		String name = rs.getString("name");
		String serverFile = rs.getString("serverFile");
		return new MyUploadForm(fileID,personID,uploadPath,name,serverFile);
	}
}
