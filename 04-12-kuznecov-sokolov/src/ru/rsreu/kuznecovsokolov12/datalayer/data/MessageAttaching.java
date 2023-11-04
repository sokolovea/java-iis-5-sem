package ru.rsreu.kuznecovsokolov12.datalayer.data;

import java.sql.Timestamp;

public class MessageAttaching extends AbstractEntity {

	private int id;
	private int team;
	private int message;
	
	public MessageAttaching() {
	}

	public MessageAttaching(int id, int team, int message) {
		this.setId(id);
		this.setTeam(team);
		this.setMessage(message);
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getTeam() {
		return team;
	}
	public void setTeam(int team) {
		this.team = team;
	}
	public int getMessage() {
		return message;
	}
	public void setMessage(int message) {
		this.message = message;
	}

}
