package ru.rsreu.kuznecovsokolov12.datalayer;

import java.sql.SQLException;
import java.util.List;

import ru.rsreu.kuznecovsokolov12.datalayer.data.Message;
import ru.rsreu.kuznecovsokolov12.datalayer.data.Team;

public interface MessageDAO {

	List<Message> getUndeletedMessagesForTeam(Team team) throws SQLException;
	
	List<Message> getAllMessagesForTeam(Team team) throws SQLException;

	List<Message> getAllMessages() throws SQLException;
}
