package ru.rsreu.kuznecovsokolov12.datalayer;

import java.sql.SQLException;
import java.util.List;

import ru.rsreu.kuznecovsokolov12.datalayer.data.TeamInteract;
import ru.rsreu.kuznecovsokolov12.datalayer.data.User;

public interface TeamInteractDAO {

	List<TeamInteract> getTeamInteractsForUser(User user) throws SQLException;

	List<TeamInteract> getAllTeamInteracts() throws SQLException;

	void addTeamInteract(TeamInteract teamInteract) throws SQLException;
}
