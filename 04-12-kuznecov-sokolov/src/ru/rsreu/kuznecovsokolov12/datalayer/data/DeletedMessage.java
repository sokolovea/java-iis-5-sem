package ru.rsreu.kuznecovsokolov12.datalayer.data;

import java.sql.Timestamp;

public class DeletedMessage extends AbstractEntity {

	private int id;
	private int sender;
	private int message;
	private Timestamp time;
	
	public DeletedMessage() {
	}

	public DeletedMessage(int id, int sender, int message, Timestamp time) {
		this.setId(id);
		this.setSender(sender);
		this.setMessage(message);
		this.setTime(time);
	}
	
	public int getSender() {
		return sender;
	}

	public void setSender(int sender) {
		this.sender = sender;
	}

	public int getMessage() {
		return message;
	}

	public void setMessage(int message) {
		this.message = message;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Timestamp getTime() {
		return time;
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}

}
