package com.example.personlist.model;

public class AddressInfo {
	private String AddressID;
	private String Address1;
	private String Address2;
	
	public AddressInfo(String AddressID, String address1, String address2)
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
	
	public String getAddressID() {
		return AddressID;
	}
	public void setAddressID(String addressID) {
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
