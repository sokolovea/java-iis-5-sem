package ru.rsreu.kuznecovsokolov12.datalayer.data;

import java.sql.Timestamp;

/**
 * The {@code RoleAssignment} class represents the assignment of a role to a user (receiver) by a sender.
 * It extends the {@link AbstractEntity} class and includes information about the assigned role, sender, receiver,
 * and the timestamp of assignment.
 *
 * <p>This class provides constructors for creating instances with different sets of attributes.
 * It includes getter and setter methods for accessing and modifying the assigned role, sender, receiver, and time attributes.
 */
public class RoleAssigment extends AbstractEntity {

	private Role role;
	private User sender;
	private User receiver;
	private Timestamp time;
	
    /**
     * Constructs a RoleAssignment with default (uninitialized) values.
     */
	public RoleAssigment() {
	}
	
    /**
     * Constructs a RoleAssignment with the specified id and default (uninitialized) attributes.
     *
     * @param id The id to set for the RoleAssignment.
     */
	public RoleAssigment(int id) {
		super(id);
	}

    /**
     * Constructs a RoleAssignment with the specified id, assigned role, sender, receiver, and time.
     *
     * @param id       The id to set for the RoleAssignment.
     * @param role     The Role representing the assigned role.
     * @param sender   The User representing the sender of the assignment.
     * @param receiver The User representing the receiver of the assignment.
     * @param time     The Timestamp representing the time of assignment.
     */
	public RoleAssigment(int id, Role role, User sender, User receiver, Timestamp time) {
		this(id);
		this.setRole(role);;
		this.setSender(sender);;
		this.setReceiver(receiver);;
		this.setTime(time);;
	}
	
    /**
     * Gets the assigned role.
     *
     * @return The Role representing the assigned role.
     */
	public Role getRole() {
		return role;
	}
	
    /**
     * Sets the assigned role.
     *
     * @param role The Role representing the assigned role.
     */
	public void setRole(Role role) {
		this.role = role;
	}
	
    /**
     * Gets the sender of the assignment.
     *
     * @return The User representing the sender of the assignment.
     */
	public User getSender() {
		return sender;
	}
	
    /**
     * Sets the sender of the assignment.
     *
     * @param sender The User representing the sender of the assignment.
     */
	public void setSender(User sender) {
		this.sender = sender;
	}
	
    /**
     * Gets the receiver of the assignment.
     *
     * @return The User representing the receiver of the assignment.
     */
	public User getReceiver() {
		return receiver;
	}
	
    /**
     * Sets the receiver of the assignment.
     *
     * @param receiver The User representing the receiver of the assignment.
     */
	public void setReceiver(User receiver) {
		this.receiver = receiver;
	}
	

    /**
     * Gets the timestamp of assignment.
     *
     * @return The Timestamp representing the time of assignment.
     */
	public Timestamp getTime() {
		return time;
	}
	
    /**
     * Sets the timestamp of assignment.
     *
     * @param time The Timestamp representing the time of assignment.
     */
	public void setTime(Timestamp time) {
		this.time = time;
	}
	
}
