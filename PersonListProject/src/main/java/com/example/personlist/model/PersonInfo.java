package com.example.personlist.model;

public class PersonInfo {
//PersonID,FullName,FirstName,LastName,ClassName,Grade
	private int PersonID;
	private String FullName;
	private String FirstName;
	private String LastName;
	private String ClassName;
	private String Grade;
	
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
