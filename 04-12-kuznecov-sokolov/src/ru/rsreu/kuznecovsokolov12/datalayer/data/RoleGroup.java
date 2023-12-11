package ru.rsreu.kuznecovsokolov12.datalayer.data;

/**
 * The {@code RoleGroup} class represents the grouping of roles.
 * It extends the {@link AbstractEntity} class and includes information about the group name.
 *
 * <p>This class provides constructors for creating instances with different sets of attributes.
 * It includes getter and setter methods for accessing and modifying the group name.
 */
public class RoleGroup extends AbstractEntity {
	
	private String name;
	
    /**
     * Constructs a RoleGroup with uninitialized values.
     */
	public RoleGroup() {
	}
	
    /**
     * Constructs a RoleGroup with the specified id and uninitialized attributes.
     *
     * @param id The id to set for the RoleGroup.
     */
	public RoleGroup(int id) {
		super(id);
	}
	
    /**
     * Constructs a RoleGroup with the specified id and group name.
     *
     * @param id   The id to set for the RoleGroup.
     * @param name The string representing the group name.
     */
	public RoleGroup(int id, String name) {
		this(id);
		this.name = name;
	}

    /**
     * Gets the name of the role group.
     *
     * @return The string representing the group name.
     */
	public String getName() {
		return name;
	}

    /**
     * Sets the name of the role group.
     *
     * @param name The string representing the group name.
     */
	public void setName(String name) {
		this.name = name;
	}
	
}
