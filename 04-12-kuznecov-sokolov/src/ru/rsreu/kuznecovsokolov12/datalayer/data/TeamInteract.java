package ru.rsreu.kuznecovsokolov12.datalayer.data;

import java.sql.Timestamp;

public class TeamInteract extends AbstractEntity {

	private int id;
	private int user;
	private int type;
	private int team;
	private Timestamp time;
	 
	public TeamInteract() {
	}

	public TeamInteract(int id, int user, int type, int team, Timestamp time) {
		this.setId(id);
		this.setUser(user);
		this.setType(type);
		this.setTeam(team);
		this.setTime(time);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUser() {
		return user;
	}

	public void setUser(int user) {
		this.user = user;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getTeam() {
		return team;
	}

	public void setTeam(int team) {
		this.team = team;
	}

	public Timestamp getTime() {
		return time;
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}

}
