package com.example.personlist.model;

public class AddressInfo {
	private int AddressID;
	public int PersonID;
	public String Address;
	
	public AddressInfo()
	{
		
	}
	public AddressInfo(int addressID, int personid, String address2)
	{
		super();
		this.AddressID = addressID;
		this.PersonID = personid;
		this.Address = address2;
	}
	
	public AddressInfo(int personid, String address2)
	{
		super();
		this.PersonID = personid;
		this.Address = address2;
	}
	
	public int getAddressID() {
		return AddressID;
	}
	public void setAddressID(int addressID) {
		AddressID = addressID;
	}
	
	public String getAddress() {
		return Address;
	}
	public void setAddress(String address2) {
		Address = address2;
	}
	
	
}
