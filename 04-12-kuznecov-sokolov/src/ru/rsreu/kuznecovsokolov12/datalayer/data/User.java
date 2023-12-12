package ru.rsreu.kuznecovsokolov12.datalayer.data;

/**
 * The {@code User} class represents an entity that uses the information system.
 * It extends the {@link AbstractEntity} class and includes information about the user's login, password,
 * name, email, and authorization status.
 *
 * <p>This class provides constructors for creating instances with different sets of attributes.
 * It includes getter and setter methods for accessing and modifying the login, password, name, email,
 * and authorization status attributes. Additionally, it provides a method for checking the password.
 */
public class User extends AbstractEntity {

	private String login;
	private String password;
	private String name;
	private String email;
	private boolean is_authorized = false;
	
	/**
	 * Constructs a User with uninitialized values.
	 */
	public User() {
	}
	
	/**
	 * Constructs a User with the specified id and uninitialized attributes.
	 *
	 * @param id The id to set for the User.
	 */
	public User(int id) {
		super(id);
	}
	
	/**
	 * Constructs a User with the specified login and password, and uninitialized id.
	 *
	 * @param login	The String representing the user's login.
	 * @param password The String representing the user's password.
	 */
	public User(String login, String password) {
		this.setLogin(login);
		this.setPassword(password);
	}

	/**
	 * Constructs a User with the specified login, password, name, and email.
	 *
	 * @param login	The String representing the user's login.
	 * @param password The String representing the user's password.
	 * @param name	 The String representing the user's name.
	 * @param email	The String representing the user's email.
	 */
	public User(String login, String password, String name, String email) {
		this(login, password);
		this.setName(name);
		this.setEmail(email);
	}
	
	/**
	 * Constructs a User with the specified id, login, password, name, and email.
	 *
	 * @param id	   The id to set for the User.
	 * @param login	The String representing the user's login.
	 * @param password The String representing the user's password.
	 * @param name	 The String representing the user's name.
	 * @param email	The String representing the user's email.
	 */
	public User(int id, String login, String password, String name, String email) {
		this(login, password, name, email);
		this.setId(id);
	}
	
	/**
	 * Constructs a User with the specified id, login, password, name, email, and authorization status.
	 *
	 * @param id		   The id to set for the User.
	 * @param login		The String representing the user's login.
	 * @param password	 The String representing the user's password.
	 * @param name		 The String representing the user's name.
	 * @param email		The String representing the user's email.
	 * @param is_authorized The boolean representing the authorization status of the user.
	 */
	public User(int id, String login, String password, String name, String email, boolean is_authorized) {
		this(id, login, password, name, email);
		this.setIsAuthorized(is_authorized);;
	}

	/**
	 * Gets the user's login.
	 *
	 * @return The String representing the user's login.
	 */
	public String getLogin() {
		return login;
	}

	/**
	 * Sets the user's login.
	 *
	 * @param login The String representing the user's login.
	 */
	public void setLogin(String login) {
		this.login = login;
	}

	/**
	 * Gets the user's password.
	 *
	 * @return The String representing the user's password.
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets the user's password.
	 *
	 * @param password The String representing the user's password.
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Gets the user's name.
	 *
	 * @return The String representing the user's name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the user's name.
	 *
	 * @param name The String representing the user's name.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the user's email.
	 *
	 * @return The String representing the user's email.
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Sets the user's email.
	 *
	 * @param email The String representing the user's email.
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Checks if the user is authorized.
	 *
	 * @return The boolean representing the authorization status of the user.
	 */
	public boolean isIsAuthorized() {
		return is_authorized;
	}

	/**
	 * Sets the authorization status of the user.
	 *
	 * @param is_authorized The boolean representing the authorization status of the user.
	 */
	public void setIsAuthorized(boolean is_authorized) {
		this.is_authorized = is_authorized;
	}

	/**
	 * Checks if the entered password matches the user's password.
	 *
	 * @param enterPass The String representing the entered password.
	 * @return True if the entered password matches the user's password; otherwise, false.
	 */
	public boolean checkPassword(String enterPass) {
		return this.getPassword().equals(enterPass);
	}
	
}
