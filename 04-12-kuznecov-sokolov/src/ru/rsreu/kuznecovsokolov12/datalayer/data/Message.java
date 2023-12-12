package ru.rsreu.kuznecovsokolov12.datalayer.data;

import java.sql.Timestamp;

/**
 * The {@code Message} class represents a message written by a User.
 * It extends the {@link AbstractEntity} class and includes information about the message data,
 * the author (User), and the timestamp of creation.
 *
 * <p>This class provides constructors for creating instances with different sets of attributes.
 * It includes getter and setter methods for accessing and modifying the data, author, and time attributes.
 */
public class Message extends AbstractEntity {

	private String data;
	private User author;
	private Timestamp time;
	
	/**
	 * Constructs a Message with uninitialized values.
	 */
	public Message() {
	}
	
	/**
	 * Constructs a Message with the specified id and default (uninitialized) attributes.
	 *
	 * @param id The id to set for the Message.
	 */
	public Message(int id) {
		super(id);
	}

	/**
	 * Constructs a Message with the specified id, data, author, and time.
	 *
	 * @param id	 The id to set for the Message.
	 * @param data   The String representing the message data.
	 * @param author The User representing the author of the message.
	 * @param time   The Timestamp representing the time of creation.
	 */
	public Message(int id, String data, User author, Timestamp time) {
		this(id);
		this.setData(data);
		this.setAuthor(author);
		this.setTime(time);
	}

	/**
	 * Gets the data of the message.
	 *
	 * @return The String representing the message data.
	 */
	public String getData() {
		return data;
	}

	/**
	 * Sets the data of the message.
	 *
	 * @param data The String representing the message data.
	 */
	public void setData(String data) {
		this.data = data;
	}

	/**
	 * Gets the author of the message.
	 *
	 * @return The User representing the author of the message.
	 */
	public User getAuthor() {
		return author;
	}

	/**
	 * Sets the author of the message.
	 *
	 * @param author The User representing the author of the message.
	 */
	public void setAuthor(User author) {
		this.author = author;
	}

	/**
	 * Gets the timestamp of creation.
	 *
	 * @return The Timestamp representing the time of creation.
	 */
	public Timestamp getTime() {
		return time;
	}

	/**
	 * Sets the timestamp of creation.
	 *
	 * @param time The Timestamp representing the time of creation.
	 */
	public void setTime(Timestamp time) {
		this.time = time;
	}

}
