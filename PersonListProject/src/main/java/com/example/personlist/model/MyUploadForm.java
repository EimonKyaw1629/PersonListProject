package com.example.personlist.model;

import org.springframework.web.multipart.MultipartFile;

public class MyUploadForm {

	private String description;
	private int FileID;
	private int PersonID;
	private String uploadRootPath;
	private String name;
	private String serverFile;

	// Upload files.
	private MultipartFile[] fileDatas;

	public MyUploadForm() {

	}

	public MyUploadForm(int fid, int pid, String des, String upath, String na, String sf) {
		super();
		this.FileID = fid;
		this.PersonID = pid;
		this.description = des;
		this.uploadRootPath = upath;
		this.name = na;
		this.serverFile = sf;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public MultipartFile[] getFileDatas() {
		return fileDatas;
	}

	public void setFileDatas(MultipartFile[] fileDatas) {
		this.fileDatas = fileDatas;
	}

	public int getFileID() {
		return this.FileID;
	}

	public void setFileID(int fid) {
		this.FileID = fid;
	}

	public int getPersonID() {
		return this.PersonID;
	}

	public void setPersonID(int pid) {
		this.PersonID = pid;
	}

	public String getUploadRootPath() {
		return this.uploadRootPath;
	}

	public void setUploadRootPath(String upf) {
		this.uploadRootPath = upf;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String na) {
		this.name = na;
	}

	public String getServerFile() {
		return this.serverFile;
	}

	public void setServerFile(String sf) {
		this.serverFile = sf;
	}
}