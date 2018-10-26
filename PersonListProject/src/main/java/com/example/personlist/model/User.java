package com.example.personlist.model;

import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="LoginUser")
public class User {
	
	@Id
	private String id;
	private String email;
	private String password;
	
	@DBRef
	private Set<Role> roles;
	
	@Override
	public String toString()
	{
		return "User{"+"id=" + id + ", Email=" +email + ", Password=" + password + '}';
	}
	
	public User()
	{
		
	}
	public User(String email,String psw)
	{
		super();
		this.email = email;
		this.password = psw;
		
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
		return this.email;
	}
	
	public void setEmail(String em)
	{
		this.email = em;
	}
	
	public String getPassword()
	{
		return this.password;
	}
	
	public void setPassword(String ps)
	{
		this.password = ps;
	}
	
	
	
	public Set<Role> getRoles() {
	    return roles;
	}

	public void setRoles(Set<Role> roles) {
	    this.roles = roles;
	}

}
