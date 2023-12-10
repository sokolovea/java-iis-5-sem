package ru.rsreu.kuznecovsokolov12.exceptions;

public class RedirectErrorPage extends Exception {

	private static final long serialVersionUID = 1L;
	
	private String message;
	private String url;
	
	public RedirectErrorPage(String url, String message) {
		this.setUrl(url);
		this.setMessage(message);
	}

	public RedirectErrorPage(String url) {
		this.setUrl(url);
	}
	
	public RedirectErrorPage() {
		
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
