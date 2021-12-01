package com.fibabankproject.banking.domain;

public class Account {

	private Integer accountId;
	private Integer userId;
	private String title;
	private String description ;
	private Double balance ;
	private String currency ;

	public Account(Integer accountId, Integer userId, String title, String description, Double balance, String currency) {
        this.accountId = accountId;
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.balance = balance;
        this.currency = currency;
    }
	
	public Integer getAccountId() {
		return accountId;
	}
	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}
	public Integer getUsrId() {
		return userId;
	}
	public void setUsrId(Integer userId) {
		this.userId = userId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Double getBalance() {
		return balance;
	}
	public void setBalance(Double balance) {
		this.balance = balance;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
}
