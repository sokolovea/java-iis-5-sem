package ru.rsreu.kuznecovsokolov12.datalayer;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import ru.rsreu.kuznecovsokolov12.datalayer.data.Message;
import ru.rsreu.kuznecovsokolov12.datalayer.data.Team;
import ru.rsreu.kuznecovsokolov12.datalayer.data.User;

/**
 * The interface provides methods for interacting with a database table
 * storing information about messages.
 *
 * <p>This interface defines methods for retrieving different types of messages, including
 * undeleted, deleted, and all messages for a team. It also includes methods to retrieve
 * messages sent or deleted by a specific user.
 */
public interface MessageDAO {

    /**
     * Retrieves undeleted messages for a specific team.
     *
     * @param team The team for which to retrieve undeleted messages.
     * @return A list of undeleted messages for the specified team.
     * @throws SQLException If a database error occurs.
     */
	List<Message> getUndeletedMessagesForTeam(Team team) throws SQLException;
	
    /**
     * Retrieves deleted messages for a specific team along with their deletion counts.
     *
     * @param team The team for which to retrieve deleted messages.
     * @return A map where keys are deleted messages and values are their deletion counts.
     * @throws SQLException If a database error occurs.
     */
	Map<Message, Integer> getDeletedMessagesForTeam(Team team) throws SQLException;
	
    /**
     * Retrieves all messages for a specific team, both deleted and undeleted.
     *
     * @param team The team for which to retrieve all messages.
     * @return A list of all messages for the specified team.
     * @throws SQLException If a database error occurs.
     */
	List<Message> getAllMessagesForTeam(Team team) throws SQLException;

    /**
     * Retrieves all messages, both deleted and undeleted.
     *
     * @return A list of all messages.
     * @throws SQLException If a database error occurs.
     */
	List<Message> getAllMessages() throws SQLException;
	
    /**
     * Retrieves messages deleted by a specific user, excluding self-deleted messages.
     *
     * @param user The user for whom to retrieve deleted messages.
     * @return A list of messages deleted by the specified user.
     * @throws SQLException If a database error occurs.
     */
	List<Message> getMessagesDeletedByNoSelfUser(User user) throws SQLException;
	
    /**
     * Retrieves the count of messages sent by a specific user.
     *
     * @param user The user for whom to retrieve the message count.
     * @return The count of messages sent by the specified user.
     * @throws SQLException If a database error occurs.
     */
	int getCountMessagesSendedByUser(User user) throws SQLException;
	
    /**
     * Retrieves the count of deleted messages sent by a specific user.
     *
     * @param user The user for whom to retrieve the deleted message count.
     * @return The count of deleted messages sent by the specified user.
     * @throws SQLException If a database error occurs.
     */
	int getCountDeletedMessagesSendedByUser(User user) throws SQLException;
}
