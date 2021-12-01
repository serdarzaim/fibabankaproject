package com.fibabankproject.banking.domain;

public class User {
	private Integer userId;
	private String tckn;
	private String firstName;
	private String lastName ;
	private String email ;
	private String password ;
	
	public User(Integer userId, String tckn, String firstName, String lastName, String email, String password) {
		this.userId = userId;
		this.tckn = tckn;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
	}
	
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}	
	public String getTckn() {
		return tckn;
	}
	public void setTckn(String tckn) {
		this.tckn = tckn;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
}


