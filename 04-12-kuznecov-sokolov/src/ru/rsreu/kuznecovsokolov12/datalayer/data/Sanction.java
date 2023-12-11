package ru.rsreu.kuznecovsokolov12.datalayer.data;

import java.sql.Timestamp;

/**
 * The {@code Sanction} class represents the delivery of a sanction to a user (receiver) by a sender.
 * It extends the {@link AbstractEntity} class and includes information about the sanction type, sender, receiver,
 * reason for the sanction, and the timestamp of delivery.
 *
 * <p>This class provides constructors for creating instances with different sets of attributes.
 * It includes getter and setter methods for accessing and modifying the sanction type, sender, receiver, reason, and time attributes.
 *
 * @version 1.0
 * @since 2023-01-01
 */
public class Sanction extends AbstractEntity {

	private SanctionType type;
	private User sender;
	private User receiver;
	private String reason;
	private Timestamp time;
	
    /**
     * Constructs a Sanction with uninitialized values.
     */
	public Sanction() {
	}

    /**
     * Constructs a Sanction with the specified id and uninitialized attributes.
     *
     * @param id The id to set for the Sanction.
     */
	public Sanction(int id) {
		super(id);
	}
	
    /**
     * Constructs a Sanction with the specified id, sanction type, sender, receiver, reason, and time.
     *
     * @param id      The id to set for the Sanction.
     * @param type    The SanctionType representing the type of sanction.
     * @param sender  The User representing the sender of the sanction.
     * @param receiver The User representing the receiver of the sanction.
     * @param reason  The String representing the reason for the sanction.
     * @param time    The Timestamp representing the time of delivery.
     */
	public Sanction(int id, SanctionType type, User sender, User receiver, String reason, Timestamp time) {
		this(id);
		this.setType(type);
		this.setSender(sender);
		this.setReceiver(receiver);
		this.setReason(reason);
		this.setTime(time);
	}
	
    /**
     * Gets the type of the sanction.
     *
     * @return The SanctionType representing the type of sanction.
     */
	public SanctionType getType() {
		return type;
	}
	
    /**
     * Sets the type of the sanction.
     *
     * @param type The SanctionType representing the type of sanction.
     */	
	public void setType(SanctionType type) {
		this.type = type;
	}
	
    /**
     * Gets the sender of the sanction.
     *
     * @return The User representing the sender of the sanction.
     */
	public User getSender() {
		return sender;
	}
	
    /**
     * Sets the sender of the sanction.
     *
     * @param sender The User representing the sender of the sanction.
     */
	public void setSender(User sender) {
		this.sender = sender;
	}
	
    /**
     * Gets the receiver of the sanction.
     *
     * @return The User representing the receiver of the sanction.
     */
	public User getReceiver() {
		return receiver;
	}
	
    /**
     * Sets the receiver of the sanction.
     *
     * @param receiver The User representing the receiver of the sanction.
     */
	public void setReceiver(User receiver) {
		this.receiver = receiver;
	}
	
    /**
     * Gets the reason for the sanction.
     *
     * @return The String representing the reason for the sanction.
     */
	public String getReason() {
		return reason;
	}
	
    /**
     * Sets the reason for the sanction.
     *
     * @param reason The String representing the reason for the sanction.
     */
	public void setReason(String reason) {
		this.reason = reason;
	}
	
    /**
     * Gets the timestamp of delivery.
     *
     * @return The Timestamp representing the time of delivery.
     */
	public Timestamp getTime() {
		return time;
	}
	
    /**
     * Sets the timestamp of delivery.
     *
     * @param time The Timestamp representing the time of delivery.
     */
	public void setTime(Timestamp time) {
		this.time = time;
	}
	
}
