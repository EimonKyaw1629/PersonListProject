package com.example.personlist.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="LoginUser")
public class User {
	
	@Id
	private String id;
	private String Email;
	private String Password;
	private boolean enabled;
	
	
	@Override
	public String toString()
	{
		return "User{"+"id=" + id + ", Email=" +Email + ", Password=" + Password +  ", enabled=" +enabled + '}';
	}
	
	public User(String email,String psw,boolean e)
	{
		super();
		this.Email = email;
		this.Password = psw;
		this.enabled = e;
	}
	
	public String getID()
	{
		return this.getID();
	}
	
	public void setID(String id)
	{
		this.id= id;
	}
	
	public String getEmail()
	{
		return this.Email;
	}
	
	public void setEmail(String em)
	{
		this.Email = em;
	}
	
	public String getPassword()
	{
		return this.Password;
	}
	
	public void setPassword(String ps)
	{
		this.Password = ps;
	}
	
	public boolean isEnabled()
	{
		return this.enabled;
	}
	
	public void setEnabled(boolean f)
	{
		this.enabled = f;
	}

}
