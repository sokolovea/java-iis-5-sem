package ru.rsreu.kuznecovsokolov12.datalayer.data;

import java.sql.Timestamp;

/**
 * The {@code DeletedMessage} class represents a deleted message by the sender.
 * It extends the {@link AbstractEntity} class and includes information about the sender,
 * the deleted message, and the timestamp of deletion.
 *
 * <p>This class provides constructors for creating instances with different sets of attributes.
 * It includes getter and setter methods for accessing and modifying the sender, message, and time attributes.
 */
public class DeletedMessage extends AbstractEntity {

	private User sender;
	private Message message;
	private Timestamp time;
	
	/**
	 * Constructs a DeletedMessage with uninitialized values.
	 */
	public DeletedMessage() {
	}
	
	/**
	 * Constructs a DeletedMessage with the specified id and uninitialized attributes.
	 *
	 * @param id The id to set for the DeletedMessage.
	 */
	public DeletedMessage(int id) {
		super(id);
	}
	
	/**
	 * Constructs a DeletedMessage with the specified id, sender, message, and time.
	 *
	 * @param id	  The id to set for the DeletedMessage.
	 * @param sender  The User representing the sender of the deleted message.
	 * @param message The Message representing the deleted message.
	 * @param time	The Timestamp representing the time of deletion.
	 */
	public DeletedMessage(int id, User sender, Message message, Timestamp time) {
		this(id);
		this.setSender(sender);
		this.setMessage(message);
		this.setTime(time);
	}
	
	/**
	 * Gets the sender of the deleted message.
	 *
	 * @return The User representing the sender of the deleted message.
	 */
	public User getSender() {
		return sender;
	}

	/**
	 * Sets the sender of the deleted message.
	 *
	 * @param sender The User representing the sender of the deleted message.
	 */
	public void setSender(User sender) {
		this.sender = sender;
	}

	/**
	 * Gets the deleted message.
	 *
	 * @return The Message representing the deleted message.
	 */
	public Message getMessage() {
		return message;
	}

	/**
	 * Sets the deleted message.
	 *
	 * @param message The Message representing the deleted message.
	 */
	public void setMessage(Message message) {
		this.message = message;
	}

	/**
	 * Gets the timestamp of deletion.
	 *
	 * @return The Timestamp representing the time of deletion.
	 */
	public Timestamp getTime() {
		return time;
	}

	/**
	 * Sets the timestamp of deletion.
	 *
	 * @param time The Timestamp representing the time of deletion.
	 */
	public void setTime(Timestamp time) {
		this.time = time;
	}

}
