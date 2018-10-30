package com.example.personlist.model;


public class MongoInfo {
	
	private int id;
	private String gender;
	private int age;
	private String job;

	
	
	

	public MongoInfo() {
		
	}
	
	public MongoInfo(int id, String gender, int age, String job) {
		super();
		this.id = id;
		this.gender = gender;
		this.age = age;
		this.job = job;
	}
	public String getJob() {
		return job;
	}
	public void setJob(String job) {
		this.job = job;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	@Override
	public String toString() {
		return "{'_id' : " +id + ", 'gender' : '"+ gender +"', 'age' : " + age + ", 'job' : '"+job+"'}";
	}


	
}
