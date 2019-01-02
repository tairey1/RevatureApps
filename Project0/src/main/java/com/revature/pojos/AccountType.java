package com.revature.pojos;

public class AccountType {
	
	private int id;
	private String name;
	
	public AccountType() {}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "AccountType [id=" + id + ", name=" + name + "]";
	}

}
