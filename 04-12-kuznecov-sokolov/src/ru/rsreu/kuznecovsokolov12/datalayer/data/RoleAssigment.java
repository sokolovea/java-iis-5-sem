package ru.rsreu.kuznecovsokolov12.datalayer.data;

import java.sql.Timestamp;

/***
 * Assignment role for user (receiver) by sender
 * 
 * @author cuzne
 *
 */
public class RoleAssigment extends AbstractEntity {

	private Role role;
	private User sender;
	private User receiver;
	private Timestamp time;
	
	public RoleAssigment() {
	}
	
	public RoleAssigment(int id) {
		super(id);
	}

	public RoleAssigment(int id, Role role, User sender, User receiver, Timestamp time) {
		this(id);
		this.setRole(role);;
		this.setSender(sender);;
		this.setReceiver(receiver);;
		this.setTime(time);;
	}
	
	public Role getRole() {
		return role;
	}
	
	public void setRole(Role role) {
		this.role = role;
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
	
	public Timestamp getTime() {
		return time;
	}
	
	public void setTime(Timestamp time) {
		this.time = time;
	}
	
}
