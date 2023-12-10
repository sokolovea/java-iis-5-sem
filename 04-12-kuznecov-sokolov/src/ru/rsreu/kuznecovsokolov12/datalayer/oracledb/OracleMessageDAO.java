package ru.rsreu.kuznecovsokolov12.datalayer.oracledb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ru.rsreu.kuznecovsokolov12.datalayer.MessageDAO;
import ru.rsreu.kuznecovsokolov12.datalayer.data.Message;
import ru.rsreu.kuznecovsokolov12.datalayer.data.Team;
import ru.rsreu.kuznecovsokolov12.datalayer.data.User;
import ru.rsreu.kuznecovsokolov12.utils.ResourcerHolder;

public class OracleMessageDAO implements MessageDAO, ResourcerHolder {

	private final static String SQL_ALL_MESSAGES_SELECT = resourser.getString("sql.message.select_all_message");
	private final static String SQL_SELECT_UNDELETED_MESSAGES_FOR_TEAM = resourser.getString("sql.message.select_undeleted_for_team");
	private final static String SQL_SELECT_DELETED_MESSAGES_FOR_TEAM = resourser.getString("sql.message.select_deleted_for_team");
	private final static String SQL_SELECT_ALL_MESSAGES_FOR_TEAM = resourser.getString("sql.message.select_all_for_team");
	private final static String SQL_SELECT_MESSAGES_DEL_BY_NO_SELF_USER = resourser.getString("sql.message.select_del_by_no_self_user");
	private final static String SQL_SELECT_COUNT_MESSAGES_BY_USER = resourser.getString("sql.message.select_count_messages_by_user");
	private final static String SQL_SELECT_COUNT_DEL_MESSAGES_BY_USER = resourser.getString("sql.message.select_count_del_messages_by_user");
	
	
	public final static String COLUMN_MESSAGE_ID 	  = resourser.getString("sql.message.column.id");
	public final static String COLUMN_MESSAGE_DATA 	  = resourser.getString("sql.message.column.data");
	public final static String COLUMN_MESSAGE_TIME 	  = resourser.getString("sql.message.column.time");
	public final static String[] ALL_MESSAGE_COLUMNS = {COLUMN_MESSAGE_ID, COLUMN_MESSAGE_DATA, COLUMN_MESSAGE_TIME};
	
	
	private Connection connection;

	public OracleMessageDAO(Connection connection) {
		this.connection = connection;
	}

	public Connection getConnection() {
		return this.connection;
	}

	@Override
	public List<Message> getAllMessages() throws SQLException {
		PreparedStatement ps;
		List<Message> result = new ArrayList<Message>();
		ps = this.connection.prepareStatement(SQL_ALL_MESSAGES_SELECT);
		ResultSet resultSet = ps.executeQuery();
		while (resultSet.next()) {
			Message message = getMessageData(resultSet, ALL_MESSAGE_COLUMNS);
			message.setAuthor(OracleUserDAO.getUserData(resultSet, OracleUserDAO.ALL_USER_COLUMNS));
			result.add(message);
		}
		return result;
	}
	
	@Override
	public List<Message> getUndeletedMessagesForTeam(Team team) throws SQLException {
		PreparedStatement ps;
		List<Message> result = new ArrayList<Message>();
		ps = this.connection.prepareStatement(SQL_SELECT_UNDELETED_MESSAGES_FOR_TEAM);
		ps.setInt(1, team.getId());
		ResultSet resultSet = ps.executeQuery();
		while (resultSet.next()) {
			Message message = getMessageData(resultSet, ALL_MESSAGE_COLUMNS);
			message.setAuthor(OracleUserDAO.getUserData(resultSet, OracleUserDAO.ALL_USER_COLUMNS));
			result.add(message);
		}
		return result;
	}

	@Override
	public Map<Message, Integer> getDeletedMessagesForTeam(Team team) throws SQLException {
		PreparedStatement ps;
		Map<Message, Integer> result = new HashMap<>();
		ps = this.connection.prepareStatement(SQL_SELECT_DELETED_MESSAGES_FOR_TEAM);
		ps.setInt(1, team.getId());
		ResultSet resultSet = ps.executeQuery();
		while (resultSet.next()) {
			Message message = getMessageData(resultSet, ALL_MESSAGE_COLUMNS);
			message.setAuthor(OracleUserDAO.getUserData(resultSet, OracleUserDAO.ALL_USER_COLUMNS));
			int sender_id = resultSet.getInt("sender");
			result.put(message, sender_id);
		}
		return result;
	}
	
	@Override
	public List<Message> getAllMessagesForTeam(Team team) throws SQLException {
		PreparedStatement ps;
		List<Message> result = new ArrayList<Message>();
		ps = this.connection.prepareStatement(SQL_SELECT_ALL_MESSAGES_FOR_TEAM);
		ps.setInt(1, team.getId());
		ResultSet resultSet = ps.executeQuery();
		while (resultSet.next()) {
			Message message = getMessageData(resultSet, ALL_MESSAGE_COLUMNS);
			message.setAuthor(OracleUserDAO.getUserData(resultSet, OracleUserDAO.ALL_USER_COLUMNS));
			result.add(message);
		}
		return result;
	}

	@Override
	public List<Message> getMessagesDeletedByNoSelfUser(User user) throws SQLException {
		PreparedStatement ps;
		List<Message> result = new ArrayList<Message>();
		ps = this.connection.prepareStatement(SQL_SELECT_MESSAGES_DEL_BY_NO_SELF_USER);
		ps.setInt(1, user.getId());
		ResultSet resultSet = ps.executeQuery();
		while (resultSet.next()) {
			Message message = getMessageData(resultSet, ALL_MESSAGE_COLUMNS);
			result.add(message);
		}
		return result;
	}

	@Override
	public int getCountMessagesSendedByUser(User user) throws SQLException {
		PreparedStatement ps;
		int result = 0;
		ps = this.connection.prepareStatement(SQL_SELECT_COUNT_MESSAGES_BY_USER);
		ps.setInt(1, user.getId());
		ResultSet resultSet = ps.executeQuery();
		if (resultSet.next()) {
			result = resultSet.getInt(1);
		}
		return result;
	}

	@Override
	public int getCountDeletedMessagesSendedByUser(User user) throws SQLException {
		PreparedStatement ps;
		int result = 0;
		ps = this.connection.prepareStatement(SQL_SELECT_COUNT_DEL_MESSAGES_BY_USER);
		ps.setInt(1, user.getId());
		ResultSet resultSet = ps.executeQuery();
		if (resultSet.next()) {
			result = resultSet.getInt(1);
		}
		return result;
	}

	public static Message getMessageData(ResultSet resultSet, String... columns) throws SQLException {
		Message message = new Message();
		for (String column : columns) {
			if (column.equals(COLUMN_MESSAGE_ID)) {
				message.setId(resultSet.getInt(column));
				continue;
			}
			
			if (column.equals(COLUMN_MESSAGE_DATA)) {
				message.setData(resultSet.getString(column));
				continue;
			}
			
			if (column.equals(COLUMN_MESSAGE_TIME)) {
				message.setTime(resultSet.getTimestamp(column));
				continue;
			}
		}
		return message;
	}



}
