package ru.rsreu.kuznecovsokolov12.datalayer.oracledb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ru.rsreu.kuznecovsokolov12.datalayer.MessageAttachingDAO;
import ru.rsreu.kuznecovsokolov12.datalayer.data.Message;
import ru.rsreu.kuznecovsokolov12.datalayer.data.MessageAttaching;

public class OracleMessageAttachingDAO implements MessageAttachingDAO {

	private static final String SQL_SELECT_ALL_DELETED_MESSAGES_ATTACH = "select \"MESSAGE_ATTACHING\".*, \"TEAM\".*, \"MESSAGE\".*, \"USER\".* FROM \"MESSAGE\" join \"USER\" on \"user_id\" = \"author\" join \"MESSAGE_ATTACHING\" on \"message_id\" = \"message\" join \"DELETED_MESSAGE\" on \"message_id\" = \"DELETED_MESSAGE\".\"message\" join \"TEAM\" on \"team_id\" = \"team\"";
	private static final String SQL_SELECT_ALL_UNDELETED_MESSAGES_ATTACH = "select \"MESSAGE_ATTACHING\".*, \"TEAM\".*, \"MESSAGE\".*, \"USER\".* FROM \"MESSAGE\" join \"USER\" on \"user_id\" = \"author\" join \"MESSAGE_ATTACHING\" on \"message_id\" = \"message\" left join \"DELETED_MESSAGE\" on \"message_id\" = \"DELETED_MESSAGE\".\"message\" join \"TEAM\" on \"team_id\" = \"team\" where \"del_message_id\" is null";
	
	public final static String COLUMN_MESSAGE_ATTACH_ID 	= "message_attach_id";
	public final static String[] ALL_MESSAGE_ATTACH_COLUMNS = {COLUMN_MESSAGE_ATTACH_ID};
	
	private Connection connection;

	public OracleMessageAttachingDAO(Connection connection) {
		this.connection = connection;
	}

	public Connection getConnection() {
		return this.connection;
	}
	
	@Override
	public List<MessageAttaching> getAllUndeletedMessageAttachs() throws SQLException {
		PreparedStatement ps;
		List<MessageAttaching> result = new ArrayList<>();
		ps = this.connection.prepareStatement(SQL_SELECT_ALL_UNDELETED_MESSAGES_ATTACH);
		ResultSet resultSet = ps.executeQuery();
		while (resultSet.next()) {
			MessageAttaching mesAttaching = getMessageData(resultSet, ALL_MESSAGE_ATTACH_COLUMNS);
			mesAttaching.setTeam(OracleTeamDAO.getTeamData(resultSet, OracleTeamDAO.ALL_TEAM_COLUMNS));
			
			Message mes = OracleMessageDAO.getMessageData(resultSet, OracleMessageDAO.ALL_MESSAGE_COLUMNS);
			mes.setAuthor(OracleUserDAO.getUserData(resultSet, OracleUserDAO.ALL_USER_COLUMNS));
			mesAttaching.setMessage(mes);
			
			result.add(mesAttaching);
		}
		return result;
	}

	@Override
	public List<MessageAttaching> getAllDeletedMessageAttachs() throws SQLException {
		PreparedStatement ps;
		List<MessageAttaching> result = new ArrayList<>();
		ps = this.connection.prepareStatement(SQL_SELECT_ALL_DELETED_MESSAGES_ATTACH);
		ResultSet resultSet = ps.executeQuery();
		while (resultSet.next()) {
			MessageAttaching mesAttaching = getMessageData(resultSet, ALL_MESSAGE_ATTACH_COLUMNS);
			mesAttaching.setTeam(OracleTeamDAO.getTeamData(resultSet, OracleTeamDAO.ALL_TEAM_COLUMNS));
			Message mes = OracleMessageDAO.getMessageData(resultSet, OracleMessageDAO.ALL_MESSAGE_COLUMNS);
			mes.setAuthor(OracleUserDAO.getUserData(resultSet, OracleUserDAO.ALL_USER_COLUMNS));
			mesAttaching.setMessage(mes);
			result.add(mesAttaching);
		}
		return result;
	}
	
	public static MessageAttaching getMessageData(ResultSet resultSet, String... columns) throws SQLException {
		MessageAttaching messageAttach = new MessageAttaching();
		for (String column : columns) {
			if (column.equals(COLUMN_MESSAGE_ATTACH_ID)) {
				messageAttach.setId(resultSet.getInt(column));
				continue;
			}
		}
		return messageAttach;
	}

}
