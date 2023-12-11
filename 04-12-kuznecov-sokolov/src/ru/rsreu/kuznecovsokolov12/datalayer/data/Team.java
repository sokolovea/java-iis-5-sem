package ru.rsreu.kuznecovsokolov12.datalayer.data;

/**
 * The {@code Team} class represents a team that users can join.
 * It extends the {@link AbstractEntity} class and includes information about the team name.
 *
 * <p>This class provides constructors for creating instances with different sets of attributes.
 * It includes getter and setter methods for accessing and modifying the team name.
 */
public class Team extends AbstractEntity {
	
	private String name;
	
    /**
     * Constructs a Team with uninitialized values.
     */
	public Team() {
	}

    /**
     * Constructs a Team with the specified id and uninitialized attributes.
     *
     * @param id The id to set for the Team.
     */
	public Team(int id) {
		super(id);
	}
	
    /**
     * Constructs a Team with the specified id and team name.
     *
     * @param id   The id to set for the Team.
     * @param name The String representing the team name.
     */
	public Team(int id, String name) {
		this(id);
		this.setName(name);
	}

    /**
     * Gets the name of the team.
     *
     * @return The String representing the team name.
     */
	public String getName() {
		return name;
	}

    /**
     * Sets the name of the team.
     *
     * @param name The String representing the team name.
     */
	public void setName(String name) {
		this.name = name;
	}

}
