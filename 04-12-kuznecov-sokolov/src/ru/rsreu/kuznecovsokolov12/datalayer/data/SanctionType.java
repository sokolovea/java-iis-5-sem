package ru.rsreu.kuznecovsokolov12.datalayer.data;

/**
 * The {@code SanctionType} class represents the type of a sanction.
 * It extends the {@link AbstractEntity} class and includes information about the type name.
 *
 * <p>This class provides constructors for creating instances with different sets of attributes.
 * It includes getter and setter methods for accessing and modifying the type name.
 */
public class SanctionType extends AbstractEntity {
	
	private String name;
	
	/**
	 * Constructs a SanctionType with uninitialized values.
	 */
	public SanctionType() {
	}
	
	/**
	 * Constructs a SanctionType with the specified id and uninitialized attributes.
	 *
	 * @param id The id to set for the SanctionType.
	 */
	public SanctionType(int id) {
		super(id);
	}
	
	/**
	 * Constructs a SanctionType with the specified id and type name.
	 *
	 * @param id   The id to set for the SanctionType.
	 * @param name The String representing the type name.
	 */
	public SanctionType(int id, String name) {
		this(id);
		this.name = name;
	}

	/**
	 * Gets the name of the sanction type.
	 *
	 * @return The String representing the type name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name of the sanction type.
	 *
	 * @param name The String representing the type name.
	 */
	public void setName(String name) {
		this.name = name;
	}
	
}
