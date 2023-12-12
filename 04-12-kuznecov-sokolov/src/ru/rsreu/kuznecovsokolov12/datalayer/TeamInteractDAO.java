package ru.rsreu.kuznecovsokolov12.datalayer;

import java.sql.SQLException;
import java.util.List;

import ru.rsreu.kuznecovsokolov12.datalayer.data.TeamInteract;
import ru.rsreu.kuznecovsokolov12.datalayer.data.TeamInteractType;
import ru.rsreu.kuznecovsokolov12.datalayer.data.User;

/**
 * The interface manages the interaction between a user and a team.
 *
 * <p>This interface includes methods for retrieving team interactions for a specific user,
 * getting all team interactions, retrieving a team interact type by its name, adding new team interactions,
 * and deleting team interactions for a user.
 */
public interface TeamInteractDAO {

	/**
	 * Retrieves team interactions for a specific user.
	 *
	 * @param user The user for whom to retrieve team interactions.
	 * @return A list of team interactions for the specified user.
	 * @throws SQLException If a database error occurs.
	 */
	List<TeamInteract> getTeamInteractsForUser(User user) throws SQLException;

	/**
	 * Retrieves all team interactions.
	 *
	 * @return A list of all team interactions.
	 * @throws SQLException If a database error occurs.
	 */
	List<TeamInteract> getAllTeamInteracts() throws SQLException;
	
	/**
	 * Retrieves a team interact type by its name.
	 *
	 * @param name The name of the team interact type to retrieve.
	 * @return The team interact type with the specified name.
	 * @throws SQLException If a database error occurs.
	 */
	TeamInteractType getTeamInteractTypeByName(String name) throws SQLException;

	/**
	 * Adds a new team interact.
	 *
	 * @param teamInteract The team interact to be added.
	 * @throws SQLException If a database error occurs.
	 */
	void addTeamInteract(TeamInteract teamInteract) throws SQLException;
	
	/**
	 * Deletes team interactions for a specific user.
	 *
	 * @param user The user for whom to delete team interactions.
	 * @throws SQLException If a database error occurs.
	 */
	void deleteTeamInteractsForUser(User user) throws SQLException;
}
