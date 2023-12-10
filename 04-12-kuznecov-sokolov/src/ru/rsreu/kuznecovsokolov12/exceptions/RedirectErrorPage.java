package ru.rsreu.kuznecovsokolov12.exceptions;

public class RedirectErrorPage extends Exception {

	private static final long serialVersionUID = 1L;
	
	private String message;
	
	public RedirectErrorPage(String message) {
		this.setMessage(message);
	}

	public RedirectErrorPage() {
		
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
