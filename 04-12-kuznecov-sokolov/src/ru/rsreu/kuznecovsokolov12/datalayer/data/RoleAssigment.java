package ru.rsreu.kuznecovsokolov12.datalayer.data;

import java.sql.Timestamp;

public class RoleAssigment extends AbstractEntity {

	private int id;
	private int role;
	private int sender;
	private int receiver;
	private Timestamp time;
	
	public RoleAssigment() {
	}

	public RoleAssigment(int id, int role, int sender, int receiver, Timestamp time) {
		this.id = id;
		this.role = role;
		this.sender = sender;
		this.receiver = receiver;
		this.time = time;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getRole() {
		return role;
	}
	public void setRole(int role) {
		this.role = role;
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
	public Timestamp getTime() {
		return time;
	}
	public void setTime(Timestamp time) {
		this.time = time;
	}
	
}
