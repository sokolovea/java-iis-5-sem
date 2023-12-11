package ru.rsreu.kuznecovsokolov12.datalayer;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import ru.rsreu.kuznecovsokolov12.datalayer.data.Team;
import ru.rsreu.kuznecovsokolov12.datalayer.data.User;

/**
 * The interface provides methods for working with teams.
 *
 * <p>This interface includes methods for retrieving information about teams,
 * such as getting all teams, getting a team by name or ID, retrieving teams consulted
 * by an expert, getting the N best-cooperated teams with an expert, retrieving teams
 * ejected by an expert, getting teams associated with a specific user, retrieving
 * the count of team members, adding a new team, and deleting a team.
 */
public interface TeamDAO {
	
    /**
     * Retrieves information about all teams.
     *
     * @return A map where keys are teams, and values are additional information
     *         (the name of the command and count of members).
     * @throws SQLException If a database error occurs.
     */
	Map<Team, Map<String, Integer>> getAllTeam() throws SQLException;

    /**
     * Retrieves a team by its name.
     *
     * @param name The name of the team to retrieve.
     * @return The team with the specified name.
     * @throws SQLException If a database error occurs.
     */
	Team getTeamByName(String name) throws SQLException;
	
    /**
     * Retrieves a team by its id.
     *
     * @param id The ID of the team to retrieve.
     * @return The team with the specified id.
     * @throws SQLException If a database error occurs.
     */
	Team getTeamById(int id) throws SQLException;
	
    /**
     * Retrieves teams consulted by a specific expert.
     *
     * @param expert The expert for whom to retrieve teams.
     * @return A list of teams consulted by the specified expert.
     * @throws SQLException If a database error occurs.
     */
	List<Team> getTeamsConsultedByExpert(User expert) throws SQLException;
	
	 /**
     * Retrieves the N best-cooperated teams with a specific expert.
     *
     * @param expert The expert for whom to retrieve teams.
     * @param N The number of teams to retrieve.
     * @return A map where keys are teams, and values are cooperation scores or counts.
     * @throws SQLException If a database error occurs.
     */
	Map<Team, Integer> getNTeamsBestCooperatedExpert(User expert, int N) throws SQLException;
	
    /**
     * Retrieves teams ejected by a specific expert.
     *
     * @param expert The expert for whom to retrieve teams.
     * @return A list of teams ejected by the specified expert.
     * @throws SQLException If a database error occurs.
     */
	List<Team> getTeamsEjectedExpert(User expert) throws SQLException;
	
    /**
     * Retrieves teams associated with a specific user.
     *
     * @param user The user for whom to retrieve teams.
     * @return A list of teams associated with the specified user.
     * @throws SQLException If a database error occurs.
     */
	List<Team> getTeamsForUser(User user) throws SQLException;
	
    /**
     * Retrieves the count of members in a specific team.
     *
     * @param team The team for which to retrieve the member count.
     * @return The count of members in the specified team.
     * @throws SQLException If a database error occurs.
     */
	int getCountTeamMembers(Team team) throws SQLException;
	
    /**
     * Adds a new team.
     *
     * @param team The team to be added.
     * @throws SQLException If a database error occurs.
     */
	void addTeam(Team team) throws SQLException;
	
    /**
     * Deletes a team.
     *
     * @param team The team to be deleted.
     * @throws SQLException If a database error occurs.
     */
	void deleteTeam(Team team) throws SQLException;
}
