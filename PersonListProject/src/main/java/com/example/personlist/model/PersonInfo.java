package com.example.personlist.model;

import java.util.List;

import javax.validation.constraints.Size;


public class PersonInfo extends AddressInfo{
	
	public int PersonID;
	
	@Size(min=1, message="FullNameを入力してぐださい。")
	private String FullName;
	
	private String FirstName;
	private String LastName;
	private String ClassName;
	private String Grade;
	private String Address;
	
	public List<AddressInfo> alist;
	public List<MyUploadForm> fileString;
	public List<String> fpathList;
	public List<MongoInfo> mongoList;
	
	@Override
	public String toString() {
		return "PersonInfo [PersonID=" + PersonID + ", FullName=" + FullName + ", FirstName=" + FirstName
				+ ", LastName=" + LastName + ", ClassName=" + ClassName + ", Grade=" + Grade + "]";
	}
	public PersonInfo()
	{
		
	}
	public PersonInfo(int personid,String fullname,String firstname,String lastname,String classname,String grade)
	{
		super();
		this.PersonID =personid;
		this.FullName = fullname;
		this.FirstName = firstname;
		this.LastName = lastname;
		this.ClassName = classname;
		this.Grade = grade;
		
	}
	
	public PersonInfo(String fullname,String firstname,String lastname,String classname,String grade)
	{
		super();
		this.FullName = fullname;
		this.FirstName = firstname;
		this.LastName = lastname;
		this.ClassName = classname;
		this.Grade = grade;
		
	}
	
	public PersonInfo(int personid,String fullname,String firstname,String lastname,String classname,String grade,List<AddressInfo> info)
	{
		super();
		this.PersonID =personid;
		this.FullName = fullname;
		this.FirstName = firstname;
		this.LastName = lastname;
		this.ClassName = classname;
		this.Grade = grade;
		this.alist = info;
	}
	/*
	public PersonInfo(int personid,String fullname,String firstname,String lastname,String classname,String grade,List<AddressInfo> info,List<String> fl)
	{
		super();
		this.PersonID =personid;
		this.FullName = fullname;
		this.FirstName = firstname;
		this.LastName = lastname;
		this.ClassName = classname;
		this.Grade = grade;
		this.alist = info;
		this.fpathList = fl;
	}
	*/
	public PersonInfo(int personid,String fullname,String firstname,String lastname,String classname,String grade,List<AddressInfo> info,List<MyUploadForm> fl, List<MongoInfo> mongo)
	{
		super();
		this.PersonID =personid;
		this.FullName = fullname;
		this.FirstName = firstname;
		this.LastName = lastname;
		this.ClassName = classname;
		this.Grade = grade;
		this.alist = info;
		this.fileString = fl;
		this.mongoList = mongo;
		
	}
	public PersonInfo(int personid,String fullname,String firstname,String lastname,String classname,String grade,String fl)
	{
		super();
		this.PersonID =personid;
		this.FullName = fullname;
		this.FirstName = firstname;
		this.LastName = lastname;
		this.ClassName = classname;
		this.Grade = grade;
		
		this.Address = fl;
	}

	public int getPersonID()
	{
		return PersonID;
	}
	
	public void setPersonID(int pid)
	{
		this.PersonID =pid;
	}
	
	public String getFullName()
	{
		return FullName;
	}
	
	public void setFullName(String fn)
	{
		this.FullName = fn;
	}
	
    public String getFirstName()
    {
    	return FirstName;
    }
    
    public void setFirstName(String fn)
    {
    	this.FirstName = fn;
    }
    
    public String getLastName()
    {
    	return LastName;
    }
    
    public void setLastName(String ln)
    {
    	this.LastName= ln;
    }
    
    public String getClassName()
    {
    	return ClassName;
    }
    
    public void setClassName(String cs)
    {
    	this.ClassName = cs;
    }
    
    public String getGrade()
    {
    	return Grade;
    }
    
    public void setGrade(String g)
    {
    	this.Grade =g;
    }
    
    public List<AddressInfo> getAddressList()
    {
    	return this.alist;
    }
    
    public List<MongoInfo> getMongoList()
    {
    	return this.mongoList;
    }
    
    public List<MyUploadForm> getFileString()
    {
    	return this.fileString;
    }
    
    public List<String> getfpathList()
    {
    	return fpathList;
    }
    
    public String getAddress()
    {
    	return this.Address;
    }
  
}
