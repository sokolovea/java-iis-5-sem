package ru.rsreu.kuznecovsokolov12.datalayer;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ru.rsreu.kuznecovsokolov12.datalayer.data.*;

/**
 * The {@code UserDAO} interface provides methods for working with tables storing user data.
 *
 * <p>This interface includes methods for retrieving users by ID or login, getting the expert for a team,
 * retrieving a list of all users, getting a list of unprivileged users, retrieving a list of blocked users
 * more than N times, getting the user list for a specific team, retrieving a map of users with their roles,
 * getting the team captain for a team, adding a new user, updating user information, deleting a user, and
 * getting a set of blocked users.
 */
public interface UserDAO {
	
	/**
	 * Retrieves a user by their ID.
	 *
	 * @param id The ID of the user to retrieve.
	 * @return The user with the specified ID.
	 * @throws SQLException If a database error occurs.
	 */
	User getUserByID(int id) throws SQLException;
	
	/**
	 * Retrieves a user by their login.
	 *
	 * @param login The login of the user to retrieve.
	 * @return The user with the specified login.
	 * @throws SQLException If a database error occurs.
	 */
	User getUserByLogin(String login) throws SQLException;
	
	/**
	 * Retrieves the expert for a specific team.
	 *
	 * @param team The team for which to retrieve the expert.
	 * @return The expert for the specified team.
	 * @throws SQLException If a database error occurs.
	 */
	User getExpertForTeam(Team team) throws SQLException;
	
	/**
	 * Retrieves a list of all users.
	 *
	 * @return A list of all users.
	 * @throws SQLException If a database error occurs.
	 */
	List<User> getUsers() throws SQLException;
	
	/**
	 * Retrieves a list of unprivileged users.
	 *
	 * @return A list of unprivileged users.
	 * @throws SQLException If a database error occurs.
	 */
	List<User> getUnprivilegedUsers() throws SQLException;
	
	/**
	 * Retrieves a list of users who have been blocked more than N times.
	 *
	 * @param N The number of times a user must be blocked to be included in the list.
	 * @return A list of users blocked more than N times.
	 * @throws SQLException If a database error occurs.
	 */
	List<User> getBlockedUsersMoreNTimes(int N) throws SQLException;
	
	/**
	 * Retrieves the user list for a specific team.
	 *
	 * @param team The team for which to retrieve the user list.
	 * @return A list of users associated with the specified team.
	 * @throws SQLException If a database error occurs.
	 */
	List<User> getTeamUserList(Team team) throws SQLException;
	
	/**
	 * Retrieves a map of users with their roles.
	 *
	 * @return A map where keys are users, and values are their roles.
	 * @throws SQLException If a database error occurs.
	 */
	Map<User, Role> getUsersWithRole() throws SQLException;
	
	/**
	 * Retrieves the team captain for a specific team.
	 *
	 * @param team The team for which to retrieve the team captain.
	 * @return The team captain for the specified team.
	 * @throws SQLException If a database error occurs.
	 */
	User getTeamCapitan(Team team) throws SQLException;
	
	/**
	 * Adds a new user.
	 *
	 * @param user The user to be added.
	 * @throws SQLException If a database error occurs.
	 */
	void addUser(User user) throws SQLException;
	
	/**
	 * Updates user information.
	 *
	 * @param user The user with updated information.
	 * @throws SQLException If a database error occurs.
	 */
	void updateUser(User user) throws SQLException;
	
	/**
	 * Deletes a user.
	 *
	 * @param user The user to be deleted.
	 * @throws SQLException If a database error occurs.
	 */
	void deleteUser(User user) throws SQLException;
	
	/**
	 * Retrieves a set of blocked users.
	 *
	 * @return A set of blocked users.
	 * @throws SQLException If a database error occurs.
	 */
	public Set<User> getBlockedUsers() throws SQLException;
}
