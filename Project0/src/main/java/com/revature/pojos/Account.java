package com.revature.pojos;

public class Account {
	
	private int id;
	private int type;
	private int userID;
	private double balance;
	
	public Account () {}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	
	public int getUserID() {
		return userID;
	}
	public void setUserID(int userID) {
		this.userID = userID;
	}
	
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}

	@Override
	public String toString() {
		return "Account [id=" + id + ", type=" + type + ", userID=" + userID + ", balance=" + balance + "]";
	}
	

}
