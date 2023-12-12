package ru.rsreu.kuznecovsokolov12.datalayer.data;

/**
 * The {@code Setting} class represents the settings of an information system.
 * It extends the {@link AbstractEntity} class and includes information about the setting name and its value.
 *
 * <p>This class provides constructors for creating instances with different sets of attributes.
 * It includes getter and setter methods for accessing and modifying the setting name and value.
 */
public class Setting extends AbstractEntity {

	private String name;
	private int value;
	
	/**
	 * Constructs a Setting with uninitialized attributes.
	 */
	public Setting() {
	}
	
	/**
	 * Constructs a Setting with the specified id and uninitialized attributes.
	 *
	 * @param id The id to set for the Setting.
	 */
	public Setting(int id) {
		super(id);
	}
	
	/**
	 * Constructs a Setting with the specified name and value, and uninitialized id.
	 *
	 * @param name  The String representing the setting name.
	 * @param value The int representing the setting value.
	 */
	public Setting(String name, int value) {
		this.setName(name);
		this.setValue(value);
	}
	
	/**
	 * Constructs a Setting with the specified id, name, and value.
	 *
	 * @param id	The id to set for the Setting.
	 * @param name  The String representing the setting name.
	 * @param value The int representing the setting value.
	 */
	public Setting(int id, String name, int value) {
		this(id);
		this.setName(name);
		this.setValue(value);
	}
	
	/**
	 * Gets the name of the setting.
	 *
	 * @return The String representing the setting name.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Sets the name of the setting.
	 *
	 * @param name The String representing the setting name.
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Gets the value of the setting.
	 *
	 * @return The int representing the setting value.
	 */
	public int getValue() {
		return value;
	}
	
	/**
	 * Sets the value of the setting.
	 *
	 * @param value The int representing the setting value.
	 */
	public void setValue(int value) {
		this.value = value;
	}
	
}
