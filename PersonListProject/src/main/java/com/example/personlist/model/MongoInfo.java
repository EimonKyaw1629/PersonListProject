package com.example.personlist.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "mongoInfo")
public class MongoInfo {
	
	@Id
	private int id;
	private String gender;
	private int age;

	
	public MongoInfo() {
		
	}
	
	public MongoInfo(int id, String gender, int age) {
		super();
		this.id = id;
		this.gender = gender;
		this.age = age;
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
		return "MongoInfo [id=" + id + ", gender=" + gender + ", age=" + age + "]";
	}
	
}
