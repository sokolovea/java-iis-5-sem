package ru.rsreu.kuznecovsokolov12.datalayer.data;

public class User extends AbstractEntity {

	private int id;
	private String login;
	private String password;
	private String name;
	private String email;
	private boolean is_authorized = false;
	
	public User(String login, String password) {
		this.setLogin(login);
		this.setPassword(password);
	}

	public User(String login, String password, String name, String email) {
		this(login, password);
		this.setName(name);
		this.setEmail(email);
	}
	
	public User(int id, String login, String password, String name, String email) {
		this(login, password, name, email);
		this.setId(id);
	}
	
	public User(int id, String login, String password, String name, String email, boolean is_authorized) {
		this(id, login, password, name, email);
		this.setIsAuthorized(is_authorized);;
	}
	
	public User() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isIsAuthorized() {
		return is_authorized;
	}

	public void setIsAuthorized(boolean is_authorized) {
		this.is_authorized = is_authorized;
	}

	public boolean checkPassword(String enterPass) {
		return this.getPassword().equals(enterPass);
	}

	
	
}
