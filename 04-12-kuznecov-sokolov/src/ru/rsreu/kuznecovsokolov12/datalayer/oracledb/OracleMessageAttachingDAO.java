package ru.rsreu.kuznecovsokolov12.datalayer.oracledb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ru.rsreu.kuznecovsokolov12.datalayer.MessageAttachingDAO;
import ru.rsreu.kuznecovsokolov12.datalayer.data.Message;
import ru.rsreu.kuznecovsokolov12.datalayer.data.MessageAttaching;
import ru.rsreu.kuznecovsokolov12.utils.ResourcerHolder;

public class OracleMessageAttachingDAO implements MessageAttachingDAO, ResourcerHolder {

	private static final String SQL_SELECT_ALL_DELETED_MESSAGES_ATTACH = resourser.getString("sql.message_attach.select_all_deleted_message_attach");
	private static final String SQL_SELECT_ALL_UNDELETED_MESSAGES_ATTACH = resourser.getString("sql.message_attach.select_all_undeleted_message_attach");
	private static final String SQL_SELECT_ALL_MESSAGES_ATTACH = resourser.getString("sql.message_attach.select_all_message_attach");
	private static final String SQL_LAST_MESSAGE_ID_SELECT = resourser.getString("sql.message_attach.select_last_message_id");
	
	private static final String SQL_MESSAGE_CREATE = resourser.getString("sql.message_attach.create_message");
	private static final String SQL_MESSAGE_ATTACHING_CREATE = resourser.getString("sql.message_attach.create");
	
	
	
	public final static String COLUMN_MESSAGE_ATTACH_ID 	= resourser.getString("sql.message_attach.column.id");
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
	public List<MessageAttaching> getAllMessageAttachs() throws SQLException {
		PreparedStatement ps;
		List<MessageAttaching> result = new ArrayList<>();
		ps = this.connection.prepareStatement(SQL_SELECT_ALL_MESSAGES_ATTACH);
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
	public Set<MessageAttaching> getAllDeletedMessageAttachs() throws SQLException {
		PreparedStatement ps;
		Set<MessageAttaching> result = new HashSet<>();
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

	@Override
	public void addMessage(MessageAttaching messageAttach) throws SQLException {
		PreparedStatement ps;
		ps = this.connection.prepareStatement(SQL_MESSAGE_CREATE, PreparedStatement.RETURN_GENERATED_KEYS);
		ps.setString(1, messageAttach.getMessage().getData());
		ps.setInt(2, messageAttach.getMessage().getAuthor().getId());
		ps.executeUpdate();
		ResultSet resultSet = ps.executeQuery(SQL_LAST_MESSAGE_ID_SELECT);
		if (resultSet.next()) {
			int messageId = resultSet.getInt(1);
			ps = this.connection.prepareStatement(SQL_MESSAGE_ATTACHING_CREATE);
			ps.setInt(1, messageAttach.getTeam().getId());
			ps.setInt(2, messageId);
			ps.executeUpdate();
		}
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
