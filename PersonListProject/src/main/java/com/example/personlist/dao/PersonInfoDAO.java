package com.example.personlist.dao;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import com.example.personlist.mapper.AddressInfoMapper;
import com.example.personlist.mapper.PersonInfoMapper;
import com.example.personlist.mapper.UploadFileMapper;
import com.example.personlist.model.AddressInfo;
import com.example.personlist.model.MongoInfo;
import com.example.personlist.model.MyUploadForm;
import com.example.personlist.model.PersonInfo;

@Component
@Repository
@Transactional
public class PersonInfoDAO extends JdbcDaoSupport {

	@Autowired
	public PersonInfoDAO(DataSource ds) {
		this.setDataSource(ds);
	}

	/**
	 *  for Person Info
	 */
	public List<PersonInfo> getPersonInfo() {
		String sql = PersonInfoMapper.BASE_SQL;
		Object[] params = new Object[] {};
		PersonInfoMapper mapper = new PersonInfoMapper();
		List<PersonInfo> list = this.getJdbcTemplate().query(sql, params, mapper);
		return list;
	}

	public List<Map<String, Object>> getPersonInfoList(List<MongoInfo> i) {
		String sql = AddressInfoMapper.XML_SELECT;

		List<Map<String, Object>> list = this.getJdbcTemplate().queryForList(sql);

		for (Map<String, Object> k : list) {
			for (MongoInfo j : i) {

				if (j.getId() == Integer.valueOf(k.get("PersonID").toString())) {
					k.put("gender", j.getGender());
					k.put("job", j.getJob());
				}
			}
		}
		System.out.println(list);
		return list;
	}


	public PersonInfo findPersonInfo(int pid) {
		String sql = PersonInfoMapper.BASE_SQL + " where PersonID=?";

		Object[] params = new Object[] { pid };
		PersonInfoMapper mapper = new PersonInfoMapper();
		try {
			PersonInfo info = this.getJdbcTemplate().queryForObject(sql, params, mapper);
			return info;
		} catch (EmptyResultDataAccessException ex) {
			return null;
		}

	}
	
	public int insertPersonInfo(PersonInfo personinfo, List<String> addrlist) {
		String sql = PersonInfoMapper.INSERT_SQL;
		Object[] params = new Object[] { personinfo.getFullName(), personinfo.getFirstName(), personinfo.getLastName(),
				personinfo.getClassName(), personinfo.getGrade() };
		getJdbcTemplate().update(sql, params);

		List<PersonInfo> list = getPersonInfo();

		for (int i = 0, size = addrlist.size(); i < size; i++) {
			String AddrSql = AddressInfoMapper.AD_INSERT_SQL;
			Object[] paramsAddr = new Object[] { list.get(list.size() - 1).getPersonID(), addrlist.get(i) };
			getJdbcTemplate().update(AddrSql, paramsAddr);
		}

		return list.get(list.size() - 1).getPersonID();
	}
	
	
	public void deletePersonInfo(int pid) {
		String sql = PersonInfoMapper.DELETE_SQL;
		Object[] params = new Object[] { pid };
		getJdbcTemplate().update(sql, params);

		String AddrSql = AddressInfoMapper.AD_DELETE_SQL;
		Object[] paramsAddr = new Object[] { pid };
		getJdbcTemplate().update(AddrSql, paramsAddr);

		deleteFileByPersonID(pid);
	}
	
	public List<Map<String, Object>> getSearchGenderInfo(List<MongoInfo> pid) {

		String sql = null;
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> r = new ArrayList<Map<String, Object>>();
		for (MongoInfo i : pid) {
			sql = AddressInfoMapper.XML_SELECT + " where (PersonID =IIF(" + i.getId() + " IS NULL, PersonID, "
					+ i.getId() + "))";

			try {
				list = this.getJdbcTemplate().queryForList(sql);
				for (Map<String, Object> k : list) {
					k.put("gender", i.getGender());
					k.put("job", i.getJob());
					r.add(k);
				}
			} catch (EmptyResultDataAccessException ex) {
				return null;
			}
		}
		return r;
	}

	public List<Map<String, Object>> getSearchPersonInfo(String fullname, String classname) {
		String sql = null;

		if (!StringUtils.isEmpty(fullname) || !StringUtils.isEmpty(classname)) {
			sql = AddressInfoMapper.XML_SELECT + " where ('" + fullname + "' is null or  FullName='" + fullname
					+ "') or ('" + classname + "' is null or ClassName='" + classname + "') ";
			
		}
		else
		{
			sql = AddressInfoMapper.XML_SELECT;
		}

		try {
			List<Map<String, Object>> list = this.getJdbcTemplate().queryForList(sql);
			return list;

		} catch (EmptyResultDataAccessException ex) {
			return null;
		}
	}
	
	public void editPersonInfo(PersonInfo info, List<AddressInfo> ainfo, List<MyUploadForm> upfrm) {
		PersonInfo P = this.findPersonInfo(info.getPersonID());
		List<AddressInfo> a = this.findAddressInfoByPersonID(info.getPersonID());
		if (P != null) {
			String sql = PersonInfoMapper.UPDATE_SQL;

			Object[] params = new Object[] { info.getFullName(), info.getFirstName(), info.getLastName(),
					info.getClassName(), info.getGrade(), info.getPersonID() };

			getJdbcTemplate().update(sql, params);
		}
		if (a != null && !ainfo.isEmpty()) {
			String dsql = AddressInfoMapper.AD_DELETE_SQL;
			Object[] params = new Object[] { info.getPersonID() };

			getJdbcTemplate().update(dsql, params);

			for (AddressInfo addressInfo : ainfo) {
				String isql = AddressInfoMapper.AD_INSERT_SQL;
				Object[] param = new Object[] { info.getPersonID(), addressInfo.getAddress() };

				getJdbcTemplate().update(isql, param);
			}

		}
		if (ainfo.isEmpty()) {
			String sql = AddressInfoMapper.AD_DELETE_SQL;
			Object[] params = new Object[] { info.getPersonID() };
			getJdbcTemplate().update(sql, params);
		}
		if (!upfrm.isEmpty()) {
			String dsql = UploadFileMapper.file_Delete_Sql;
			Object[] params = new Object[] { info.getPersonID() };

			getJdbcTemplate().update(dsql, params);

			for (MyUploadForm frm : upfrm) {
				String isql = UploadFileMapper.file_INSERT_SQL;
				Object[] param = new Object[] { info.getPersonID(), frm.getUploadRootPath(), frm.getName(),
						frm.getServerFile() };

				getJdbcTemplate().update(isql, param);
			}

		} else {
			String dsql = UploadFileMapper.file_Delete_Sql;
			Object[] params = new Object[] { info.getPersonID() };

			getJdbcTemplate().update(dsql, params);
		}
	}
	
	/**
	 * for Address Info
	 */
	public String getAddressInfo() {
		String sql = AddressInfoMapper.AD_SELECT_SQL;
		Object[] params = new Object[] {};
		try {
			String name = (String) getJdbcTemplate().queryForObject(sql, params, String.class);
			return name;
		} catch (EmptyResultDataAccessException ex) {
			return null;
		}
	}


	public List<AddressInfo> findAddressInfoByPersonID(int aid) {
		String asql = AddressInfoMapper.AD_SELECT_SQL + " where PersonID=?";

		Object[] params = new Object[] { aid };
		AddressInfoMapper mapper = new AddressInfoMapper();
		try {
			List<AddressInfo> list = this.getJdbcTemplate().query(asql, params, mapper);
			return list;
		} catch (EmptyResultDataAccessException ex) {
			return null;
		}
	}

	public AddressInfo findAddressInfoByAddressID(int aid) {
		String asql = AddressInfoMapper.AD_SELECT_SQL + " where AddressID=?";

		Object[] params = new Object[] { aid };
		AddressInfoMapper mapper = new AddressInfoMapper();
		try {

			AddressInfo list = this.getJdbcTemplate().queryForObject(asql, params, mapper);
			;

			return list;
		} catch (EmptyResultDataAccessException ex) {
			return null;
		}
	}

	
	/**
	 * for File Info
	 */
	public String findFileByPersonID(int pid) {
		String sql = UploadFileMapper.file_Select_Sql;
		Object[] params = new Object[] { pid };
		try {
			String name = (String) getJdbcTemplate().queryForObject(sql, params, String.class);
			return name;
		} catch (EmptyResultDataAccessException ex) {
			return null;
		}

	}

	public List<MyUploadForm> findFileListByPersonID(int pid) {
		String sql = UploadFileMapper.file_Select_Sql + " where PersonID=?";
		Object[] params = new Object[] { pid };
		UploadFileMapper mapper = new UploadFileMapper();
		try {
			List<MyUploadForm> name = this.getJdbcTemplate().query(sql, params, mapper);
			return name;
		} catch (EmptyResultDataAccessException ex) {
			return null;
		}

	}
	
	public void deleteFileByPersonID(int pid) {
		List<MyUploadForm> list = findFileListByPersonID(pid);

		for (int j = 0; j < list.size(); j++) {
			File deleteFolder = new File(list.get(j).getServerFile());
			deleteFolder.delete();
		}

		String FileSql = PersonInfoMapper.file_DELETE_SQL;
		Object[] paramsFile = new Object[] { pid };
		getJdbcTemplate().update(FileSql, paramsFile);
	}

	public void insertUploadFile(String uploadRootPath, String name, File serverFile, int pid) {
		String sql = PersonInfoMapper.file_INSERT_SQL;
		String serverfile = "" + serverFile;
		Object[] params = new Object[] { pid, uploadRootPath, name, serverfile };
		getJdbcTemplate().update(sql, params);

	}

	
	

	
}
