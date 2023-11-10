package ru.rsreu.kuznecovsokolov12.datalayer.data;

import java.sql.Timestamp;

public class TeamInteract extends AbstractEntity {

	private User user;
	private TeamInteractType type;
	private Team team;
	private Timestamp time;
	 
	public TeamInteract() {
	}

	public TeamInteract(int id) {
		super(id);
	}
	
	public TeamInteract(int id, User user, TeamInteractType type, Team team, Timestamp time) {
		this(id);
		this.setUser(user);
		this.setType(type);
		this.setTeam(team);
		this.setTime(time);
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public TeamInteractType getType() {
		return type;
	}

	public void setType(TeamInteractType type) {
		this.type = type;
	}

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

	public Timestamp getTime() {
		return time;
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}

}
