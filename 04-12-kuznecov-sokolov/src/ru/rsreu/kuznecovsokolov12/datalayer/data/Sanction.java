package ru.rsreu.kuznecovsokolov12.datalayer.data;

import java.sql.Timestamp;

public class Sanction extends AbstractEntity {

	private int id;
	private int type;
	private int sender;
	private int receiver;
	private String reason;
	private Timestamp time;
	
	public Sanction() {
	}

	public Sanction(int id, int type, int sender, int receiver, String reason, Timestamp time) {
		this.setId(id);
		this.setType(type);
		this.setSender(sender);
		this.setReceiver(receiver);
		this.setReason(reason);
		this.setTime(time);
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getSender() {
		return sender;
	}
	public void setSender(int sender) {
		this.sender = sender;
	}
	public int getReceiver() {
		return receiver;
	}
	public void setReceiver(int receiver) {
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
