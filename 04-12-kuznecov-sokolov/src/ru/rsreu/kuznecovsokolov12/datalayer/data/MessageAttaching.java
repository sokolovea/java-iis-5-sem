package ru.rsreu.kuznecovsokolov12.datalayer.data;

/**
 * The {@code MessageAttaching} class represents the attachment of a message to a team.
 * It extends the {@link AbstractEntity} class and includes information about the associated team
 * and the attached message.
 *
 * <p>This class provides constructors for creating instances with different sets of attributes.
 * It includes getter and setter methods for accessing and modifying the team and message attributes.
 */
public class MessageAttaching extends AbstractEntity {

	private Team team;
	private Message message;
	
    /**
     * Constructs a MessageAttaching with uninitialized values.
     */
	public MessageAttaching() {
	}

    /**
     * Constructs a MessageAttaching with the specified id and uninitialized attributes.
     *
     * @param id The id to set for the MessageAttaching.
     */
	public MessageAttaching(int id) {
		super(id);
	}
	
    /**
     * Constructs a MessageAttaching with the specified id, team, and message.
     *
     * @param id      The id to set for the MessageAttaching.
     * @param team    The Team representing the associated team.
     * @param message The Message representing the attached message.
     */
	public MessageAttaching(int id, Team team, Message message) {
		this(id);
		this.setTeam(team);
		this.setMessage(message);
	}

    /**
     * Gets the associated team for the message attachment.
     *
     * @return The Team representing the associated team.
     */
	public Team getTeam() {
		return team;
	}
	
    /**
     * Sets the associated team for the message attachment.
     *
     * @param team The Team representing the associated team.
     */
	public void setTeam(Team team) {
		this.team = team;
	}
	
    /**
     * Gets the attached message.
     *
     * @return The Message representing the attached message.
     */
	public Message getMessage() {
		return message;
	}
	
    /**
     * Sets the attached message.
     *
     * @param message The Message representing the attached message.
     */
	public void setMessage(Message message) {
		this.message = message;
	}

}
