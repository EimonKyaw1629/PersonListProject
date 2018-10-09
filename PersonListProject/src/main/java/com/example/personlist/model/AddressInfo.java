package com.example.personlist.model;

public class AddressInfo {
	private int AddressID;
	public String Address1;
	public String Address2;
	
	public AddressInfo(int AddressID, String address1, String address2)
	{
		super();
		this.AddressID = AddressID;
		this.Address1 = address1;
		this.Address2 = address2;
	}
	
	public AddressInfo(String address1, String address2)
	{
		super();
		this.Address1 = address1;
		this.Address2 = address2;
	}
	
	public int getAddressID() {
		return AddressID;
	}
	public void setAddressID(int addressID) {
		AddressID = addressID;
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
	
	
}
