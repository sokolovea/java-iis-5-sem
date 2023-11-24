package test;

public class RedirectErrorPage extends Exception {

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
