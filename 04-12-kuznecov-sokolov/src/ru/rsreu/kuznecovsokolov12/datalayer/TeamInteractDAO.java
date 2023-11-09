package ru.rsreu.kuznecovsokolov12.datalayer;

import java.sql.SQLException;
import java.util.List;

import ru.rsreu.kuznecovsokolov12.datalayer.data.TeamInteract;
import ru.rsreu.kuznecovsokolov12.datalayer.data.User;

public abstract class TeamInteractDAO {

	public abstract List<TeamInteract> getTeamInteractsForUser(User user) throws SQLException;

	public abstract List<TeamInteract> getAllTeamInteracts() throws SQLException;

}
