package ru.rsreu.kuznecovsokolov12.datalayer;

import java.sql.SQLException;
import java.util.List;

import ru.rsreu.kuznecovsokolov12.datalayer.data.Message;
import ru.rsreu.kuznecovsokolov12.datalayer.data.Team;

public abstract class MessageDAO {

	public abstract List<Message> getUndeletedMessagesForTeam(Team team) throws SQLException;
	
	public abstract List<Message> getAllMessagesForTeam(Team team) throws SQLException;

	public abstract List<Message> getAllMessages() throws SQLException;
}
