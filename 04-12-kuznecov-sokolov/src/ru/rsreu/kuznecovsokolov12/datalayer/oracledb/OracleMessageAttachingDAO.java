package ru.rsreu.kuznecovsokolov12.datalayer.oracledb;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import ru.rsreu.kuznecovsokolov12.datalayer.MessageAttachingDAO;
import ru.rsreu.kuznecovsokolov12.datalayer.data.MessageAttaching;

public class OracleMessageAttachingDAO implements MessageAttachingDAO {

	private final static String COLUMN_MESSAGE_ATTACH_ID 	= "message_attach_id";
	private final static String[] ALL_MESSAGE_ATTACH_COLUMNS = {COLUMN_MESSAGE_ATTACH_ID};
	
	private Connection connection;

	public OracleMessageAttachingDAO(Connection connection) {
		this.connection = connection;
	}

	public Connection getConnection() {
		return this.connection;
	}
	
	@Override
	public List<MessageAttaching> getAllUndeletedMessageAttachs() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<MessageAttaching> getAllDeletedMessageAttachs() throws SQLException {
		// TODO Auto-generated method stub
		return null;
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
