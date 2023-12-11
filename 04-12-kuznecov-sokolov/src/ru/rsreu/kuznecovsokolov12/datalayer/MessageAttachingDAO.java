package ru.rsreu.kuznecovsokolov12.datalayer;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;

import ru.rsreu.kuznecovsokolov12.datalayer.data.MessageAttaching;

/**
 * This interface provides methods for attaching messages to specific commands.
 *
 * <p>This interface defines methods for retrieving different types of message attachments (undeleted,
 * deleted, and both) and for attaching messages to commands.
 */
public interface MessageAttachingDAO {

    /**
     * Retrieves all attachments of undeleted messages.
     *
     * @return A list of all attachments of undeleted messages.
     * @throws SQLException If a database error occurs.
     */
	List<MessageAttaching> getAllUndeletedMessageAttachs() throws SQLException;
	
    /**
     * Retrieves all attachments of deleted messages.
     *
     * @return A set of all attachments of deleted messages.
     * @throws SQLException If a database error occurs.
     */
	Set<MessageAttaching> getAllDeletedMessageAttachs() throws SQLException;
	
    /**
     * Retrieves all attachments of both deleted and undeleted messages.
     *
     * @return A list of all attachments of messages, both deleted and undeleted.
     * @throws SQLException If a database error occurs.
     */
	List<MessageAttaching> getAllMessageAttachs() throws SQLException;
	
    /**
     * Attaches a message to a command.
     *
     * @param messageAttach The message attachment to be added.
     * @throws SQLException If a database error occurs.
     */
	void addMessage(MessageAttaching messageAttach) throws SQLException;
	
}
