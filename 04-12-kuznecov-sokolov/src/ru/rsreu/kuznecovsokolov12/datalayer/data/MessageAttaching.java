package ru.rsreu.kuznecovsokolov12.datalayer.data;

/***
 * Attaching message to command
 * 
 * @author Cuznecov
 *
 */
public class MessageAttaching extends AbstractEntity {

	private Team team;
	private Message message;
	
	public MessageAttaching() {
	}

	public MessageAttaching(int id) {
		super(id);
	}
	
	public MessageAttaching(int id, Team team, Message message) {
		this(id);
		this.setTeam(team);
		this.setMessage(message);
	}

	public Team getTeam() {
		return team;
	}
	
	public void setTeam(Team team) {
		this.team = team;
	}
	
	public Message getMessage() {
		return message;
	}
	
	public void setMessage(Message message) {
		this.message = message;
	}

}
