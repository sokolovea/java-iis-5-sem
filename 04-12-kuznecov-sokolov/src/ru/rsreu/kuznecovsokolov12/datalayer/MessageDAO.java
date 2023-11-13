package ru.rsreu.kuznecovsokolov12.datalayer;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ru.rsreu.kuznecovsokolov12.datalayer.data.Message;
import ru.rsreu.kuznecovsokolov12.datalayer.data.Team;
import ru.rsreu.kuznecovsokolov12.datalayer.data.User;

public interface MessageDAO {

	List<Message> getUndeletedMessagesForTeam(Team team) throws SQLException;
	
	Map<Message, Integer> getDeletedMessagesForTeam(Team team) throws SQLException;
	
	List<Message> getAllMessagesForTeam(Team team) throws SQLException;

	List<Message> getAllMessages() throws SQLException;
	
	List<Message> getMessagesDeletedByNoSelfUser(User user) throws SQLException;
	
	int getCountMessagesSendedByUser(User user) throws SQLException;
	
	int getCountDeletedMessagesSendedByUser(User user) throws SQLException;
	
	void addMessage(Message message) throws SQLException;
}
