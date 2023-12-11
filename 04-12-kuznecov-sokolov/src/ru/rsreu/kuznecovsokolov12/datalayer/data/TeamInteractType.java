package ru.rsreu.kuznecovsokolov12.datalayer.data;

/**
 * The {@code TeamInteractType} class represents the type of interaction with a team.
 * It extends the {@link AbstractEntity} class and includes information about the interaction type name.
 *
 * <p>This class provides constructors for creating instances with different sets of attributes.
 * It includes getter and setter methods for accessing and modifying the interaction type name.
 */
public class TeamInteractType extends AbstractEntity {

	private String name;
	
    /**
     * Constructs a TeamInteractType with uninitialized values.
     */
	public TeamInteractType() {
	}
	
    /**
     * Constructs a TeamInteractType with the specified id and uninitialized attributes.
     *
     * @param id The id to set for the TeamInteractType.
     */
	public TeamInteractType(int id) {
		super(id);
	}
	
    /**
     * Constructs a TeamInteractType with the specified id and interaction type name.
     *
     * @param id   The id to set for the TeamInteractType.
     * @param name The String representing the interaction type name.
     */
	public TeamInteractType(int id, String name) {
		this(id);
		this.name = name;
	}

    /**
     * Gets the name of the interaction type.
     *
     * @return The String representing the interaction type name.
     */
	public String getName() {
		return name;
	}

    /**
     * Sets the name of the interaction type.
     *
     * @param name The String representing the interaction type name.
     */
	public void setName(String name) {
		this.name = name;
	}
	
}
