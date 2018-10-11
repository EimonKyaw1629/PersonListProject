package com.example.personlist.model;

import javax.validation.constraints.NotNull;


public class PersonInfo extends AddressInfo{
	private int PersonID;
	private String FullName;
	private String FirstName;
	private String LastName;
	private String ClassName;
	private String Grade;
//	public AddressInfo ainfo;
	//public String address1;
	//public String address2;
	
	
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
	
	public PersonInfo(String fullname,String firstname,String lastname,String classname,String grade)
	{
		super();
		this.FullName = fullname;
		this.FirstName = firstname;
		this.LastName = lastname;
		this.ClassName = classname;
		this.Grade = grade;
		
	}
	
	public PersonInfo(int personid,String fullname,String firstname,String lastname,String classname,String grade,String address1,String address2)
	{
		super(address1,address2);
		this.PersonID =personid;
		this.FullName = fullname;
		this.FirstName = firstname;
		this.LastName = lastname;
		this.ClassName = classname;
		this.Grade = grade;
		
	}
	
	public PersonInfo(String fullname,String firstname,String lastname,String classname,String grade,String address1,String address2)
	{
		super(address1,address2);
		this.FullName = fullname;
		this.FirstName = firstname;
		this.LastName = lastname;
		this.ClassName = classname;
		this.Grade = grade;
		
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
