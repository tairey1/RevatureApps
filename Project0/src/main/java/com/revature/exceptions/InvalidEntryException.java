package com.revature.exceptions;

public class InvalidEntryException extends Exception {

	private static final long serialVersionUID = 1L;

	public InvalidEntryException () {
		super("Invalid Entry. Try Again");
	}
	
}