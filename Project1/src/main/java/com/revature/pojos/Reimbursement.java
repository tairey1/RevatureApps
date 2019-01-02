package com.revature.pojos;

import java.sql.Timestamp;

public class Reimbursement {

	private int id;
	private double amount;
	private Timestamp submitted;
	private Timestamp resolved;
	private String description;
	private int Author;
	private int resolver;
	private int status;
	private int type;
	private String authorln;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public Timestamp getSubmitted() {
		return submitted;
	}
	public void setSubmitted(Timestamp submitted) {
		this.submitted = submitted;
	}
	public Timestamp getResolved() {
		return resolved;
	}
	public void setResolved(Timestamp resolved) {
		this.resolved = resolved;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getAuthor() {
		return Author;
	}
	public void setAuthor(int author) {
		Author = author;
	}
	public int getResolver() {
		return resolver;
	}
	public void setResolver(Integer resolver) {
		this.resolver = resolver;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getAuthorln() {
		return authorln;
	}
	public void setAuthorln(String authorln) {
		this.authorln = authorln;
	}
	@Override
	public String toString() {
		return "Reimbursement [id=" + id + ", amount=" + amount + ", submitted=" + submitted + ", resolved=" + resolved
				+ ", description=" + description + ", Author=" + Author + ", resolver=" + resolver + ", status="
				+ status + ", type=" + type + "]";
	}
	
	
}
