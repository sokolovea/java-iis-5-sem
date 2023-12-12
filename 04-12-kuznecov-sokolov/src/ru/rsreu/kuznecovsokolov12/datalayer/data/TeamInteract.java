package ru.rsreu.kuznecovsokolov12.datalayer.data;

import java.sql.Timestamp;

/**
 * The {@code TeamInteract} class represents the interaction of a user with a team.
 * It extends the {@link AbstractEntity} class and includes information about the user, interaction type, team,
 * and the timestamp of the interaction.
 *
 * <p>This class provides constructors for creating instances with different sets of attributes.
 * It includes getter and setter methods for accessing and modifying the user, interaction type, team, and time attributes.
 */
public class TeamInteract extends AbstractEntity {

	private User user;
	private TeamInteractType type;
	private Team team;
	private Timestamp time;
	
	/**
	 * Constructs a TeamInteract with uninitialized values.
	 */
	public TeamInteract() {
	}

	/**
	 * Constructs a TeamInteract with the specified id and uninitialized attributes.
	 *
	 * @param id The id to set for the TeamInteract.
	 */
	public TeamInteract(int id) {
		super(id);
	}
	
	/**
	 * Constructs a TeamInteract with the specified id, user, interaction type, team, and time.
	 *
	 * @param id	The id to set for the TeamInteract.
	 * @param user  The User representing the user involved in the interaction.
	 * @param type  The TeamInteractType representing the type of interaction.
	 * @param team  The Team representing the team involved in the interaction.
	 * @param time  The Timestamp representing the time of the interaction.
	 */
	public TeamInteract(int id, User user, TeamInteractType type, Team team, Timestamp time) {
		this(id);
		this.setUser(user);
		this.setType(type);
		this.setTeam(team);
		this.setTime(time);
	}

	/**
	 * Gets the user involved in the interaction.
	 *
	 * @return The User representing the user involved in the interaction.
	 */
	public User getUser() {
		return user;
	}

	/**
	 * Sets the user involved in the interaction.
	 *
	 * @param user The User representing the user involved in the interaction.
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * Gets the type of interaction.
	 *
	 * @return The TeamInteractType representing the type of interaction.
	 */
	public TeamInteractType getType() {
		return type;
	}

	/**
	 * Sets the type of interaction.
	 *
	 * @param type The TeamInteractType representing the type of interaction.
	 */
	public void setType(TeamInteractType type) {
		this.type = type;
	}

	/**
	 * Gets the team involved in the interaction.
	 *
	 * @return The Team representing the team involved in the interaction.
	 */
	public Team getTeam() {
		return team;
	}

	/**
	 * Sets the team involved in the interaction.
	 *
	 * @param team The Team representing the team involved in the interaction.
	 */
	public void setTeam(Team team) {
		this.team = team;
	}

	 /**
	 * Gets the timestamp of the interaction.
	 *
	 * @return The Timestamp representing the time of the interaction.
	 */
	public Timestamp getTime() {
		return time;
	}

	/**
	 * Sets the timestamp of the interaction.
	 *
	 * @param time The Timestamp representing the time of the interaction.
	 */
	public void setTime(Timestamp time) {
		this.time = time;
	}

}
