package com.example.personlist.model;

public class ConbineModel {

	private  int PersonID;
	private String FullName;
	private String FirstName;
	private String LastName;
	private String ClassName;
	private String Grade;
	private String Address;
	private String Gender;
	
	public ConbineModel(int personid,String fullname,String firstname,String lastname,String classname,String grade,String fl,String gender)
	{
		super();
		this.PersonID =personid;
		this.FullName = fullname;
		this.FirstName = firstname;
		this.LastName = lastname;
		this.ClassName = classname;
		this.Grade = grade;
		this.Gender = gender;
		this.Address = fl;
	}
	
	public String getGender()
	{
		return this.Gender;
	}
	
	public void setGender(String g)
	{
		this.Gender = g;
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
    
    public String getAddress()
    {
    	return Address;
    }
    
    public void setAddress(String g)
    {
    	this.Address =g;
    }
}
