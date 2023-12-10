package ru.rsreu.kuznecovsokolov12.datalayer.data;

import java.sql.Timestamp;

/***
 * Delivery sanction to the user(receiver) by sender
 * 
 * @author Cuznecov
 *
 */
public class Sanction extends AbstractEntity {

	private SanctionType type;
	private User sender;
	private User receiver;
	private String reason;
	private Timestamp time;
	
	public Sanction() {
	}

	public Sanction(int id) {
		super(id);
	}
	
	public Sanction(int id, SanctionType type, User sender, User receiver, String reason, Timestamp time) {
		this(id);
		this.setType(type);
		this.setSender(sender);
		this.setReceiver(receiver);
		this.setReason(reason);
		this.setTime(time);
	}
	
	public SanctionType getType() {
		return type;
	}
	public void setType(SanctionType type) {
		this.type = type;
	}
	public User getSender() {
		return sender;
	}
	public void setSender(User sender) {
		this.sender = sender;
	}
	public User getReceiver() {
		return receiver;
	}
	public void setReceiver(User receiver) {
		this.receiver = receiver;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public Timestamp getTime() {
		return time;
	}
	public void setTime(Timestamp time) {
		this.time = time;
	}
	
}
