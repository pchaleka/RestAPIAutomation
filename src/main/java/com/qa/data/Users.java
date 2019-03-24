package com.qa.data;

public class Users {


	String id;
	String email;
	String phone;
	String username;
	String website;
	String address;
	
	
	public Users(){
		
	}
	
	
	public Users(String id, String email,String phone, String username, String website, String address) {
		this.id = id;
		this.email = email;
		this.phone = phone;
		this.username = username;
		this.website = website;
		this.address = address;

	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getPhone() {
		return phone;
	}


	public void setPhone(String phone) {
		this.phone = phone;
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getWebsite() {
		return website;
	}


	public void setWebsite(String website) {
		this.website = website;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	
}
