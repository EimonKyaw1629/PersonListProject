package com.example.personlist.model;

import javax.validation.constraints.NotNull;

public class PersonInfo {
	private int PersonID;
	private String FullName;
	private String FirstName;
	private String LastName;
	private String ClassName;
	private String Grade;
	private String Address1;
	private String Address2;
	
	
	@Override
	public String toString() {
		return "PersonInfo [PersonID=" + PersonID + ", FullName=" + FullName + ", FirstName=" + FirstName
				+ ", LastName=" + LastName + ", ClassName=" + ClassName + ", Grade=" + Grade + "]";
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
	
	public PersonInfo(String fullname,String firstname,String lastname,String classname,String grade, String address1, String address2)
	{
		super();
		this.FullName = fullname;
		this.FirstName = firstname;
		this.LastName = lastname;
		this.ClassName = classname;
		this.Grade = grade;
		this.Address1 = address1;
		this.Address2 = address2;
	}
	
	
	public String getAddress1() {
		return Address1;
	}


	public void setAddress1(String address1) {
		Address1 = address1;
	}


	public String getAddress2() {
		return Address2;
	}


	public void setAddress2(String address2) {
		Address2 = address2;
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
}
