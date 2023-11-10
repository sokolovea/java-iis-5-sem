package ru.rsreu.kuznecovsokolov12.datalayer.data;

import java.sql.Timestamp;

public class DeletedMessage extends AbstractEntity {

	private User sender;
	private Message message;
	private Timestamp time;
	
	public DeletedMessage() {
	}
	
	public DeletedMessage(int id) {
		super(id);
	}
	
	public DeletedMessage(int id, User sender, Message message, Timestamp time) {
		this(id);
		this.setSender(sender);
		this.setMessage(message);
		this.setTime(time);
	}
	
	public User getSender() {
		return sender;
	}

	public void setSender(User sender) {
		this.sender = sender;
	}

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	public Timestamp getTime() {
		return time;
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}

}
