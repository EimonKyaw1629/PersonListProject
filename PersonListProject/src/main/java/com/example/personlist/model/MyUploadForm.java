package com.example.personlist.model;

import java.io.File;

import org.springframework.web.multipart.MultipartFile;

public class MyUploadForm {
 
    private String description;
 
    private File fileData;
    
    private MultipartFile[] fileDatas;
 
    public File getFileData() {
		return fileData;
	}

	public void setFileData(File fileData) {
		this.fileData = fileData;
	}

	// Upload files.
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
    
    
}
