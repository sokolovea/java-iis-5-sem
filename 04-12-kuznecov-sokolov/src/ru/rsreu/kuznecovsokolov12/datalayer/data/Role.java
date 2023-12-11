package ru.rsreu.kuznecovsokolov12.datalayer.data;

/**
 * The {@code Role} class represents a role for separating user powers.
 * It extends the {@link AbstractEntity} class and includes information about the role name
 * and the role group to which it belongs.
 *
 * <p>This class provides constructors for creating instances with different sets of attributes.
 * It includes getter and setter methods for accessing and modifying the name and group attributes.
 */
public class Role extends AbstractEntity {

	private String name;
	private RoleGroup group;
	
    /**
     * Constructs a Role with default (uninitialized) values.
     */
	public Role() {
	}

    /**
     * Constructs a Role with the specified id and default (uninitialized) attributes.
     *
     * @param id The id to set for the Role.
     */
	public Role(int id) {
		super(id);
	}
	
    /**
     * Constructs a Role with the specified id, name, and role group.
     *
     * @param id    The id to set for the Role.
     * @param name  The String representing the role name.
     * @param group The RoleGroup representing the role group.
     */
	public Role(int id, String name, RoleGroup group) {
		this(id);
		this.setName(name);
		this.setGroup(group);
	}

    /**
     * Gets the name of the role.
     *
     * @return The String representing the role name.
     */
	public String getName() {
		return name;
	}

    /**
     * Sets the name of the role.
     *
     * @param name The String representing the role name.
     */
	public void setName(String name) {
		this.name = name;
	}

    /**
     * Gets the role group to which the role belongs.
     *
     * @return The RoleGroup representing the role group.
     */
	public RoleGroup getGroup() {
		return group;
	}

    /**
     * Sets the role group to which the role belongs.
     *
     * @param group The RoleGroup representing the role group.
     */
	public void setGroup(RoleGroup group) {
		this.group = group;
	}
	
}
