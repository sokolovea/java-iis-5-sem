package ru.rsreu.kuznecovsokolov12.datalayer;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;

import ru.rsreu.kuznecovsokolov12.datalayer.data.Message;
import ru.rsreu.kuznecovsokolov12.datalayer.data.MessageAttaching;
import ru.rsreu.kuznecovsokolov12.datalayer.data.Team;

public interface MessageAttachingDAO {

	List<MessageAttaching> getAllUndeletedMessageAttachs() throws SQLException;
	
	Set<MessageAttaching> getAllDeletedMessageAttachs() throws SQLException;
	
	List<MessageAttaching> getAllMessageAttachs() throws SQLException;
	
	void addMessage(MessageAttaching messageAttach) throws SQLException;
	
}
