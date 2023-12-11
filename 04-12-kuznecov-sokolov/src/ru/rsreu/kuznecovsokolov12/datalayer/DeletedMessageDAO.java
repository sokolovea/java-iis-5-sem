package ru.rsreu.kuznecovsokolov12.datalayer;
import java.sql.SQLException;

import ru.rsreu.kuznecovsokolov12.datalayer.data.*;

/**
 * This interface provides methods for interacting with a database table
 * storing information about deleted messages.
 *
 * <p>Implementations of this interface should handle the storage and retrieval of deleted messages.
 *
 */
public interface DeletedMessageDAO {

    /**
     * Adds a deleted message to the table storing deleted messages in the database.
     *
     * @param deletedMessage The deleted message to be added.
     * @throws SQLException If a database exception error occurs.
     */
	void addDeletedMessage(DeletedMessage deletedMessage) throws SQLException;
	
    /**
     * Restores a deleted message and removes it from the deleted messages table.
     *
     * @param deletedMessage The deleted message to be restored.
     * @throws SQLException If a database access error occurs.
     */
	void removeFromDeletedMessage(DeletedMessage deletedMessage) throws SQLException;
	
    /**
     * Gets a deleted message.
     *
     * @param message The original deleted message to be gets.
     * @return The deleted message corresponding to the original message, or {@code null} if not found.
     * @throws SQLException If a database access error occurs.
     */
	DeletedMessage getDeletedMessage(Message message) throws SQLException;
	
}
