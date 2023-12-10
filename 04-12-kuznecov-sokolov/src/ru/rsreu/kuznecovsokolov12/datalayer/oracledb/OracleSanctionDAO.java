package ru.rsreu.kuznecovsokolov12.datalayer.oracledb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ru.rsreu.kuznecovsokolov12.datalayer.SanctionDAO;
import ru.rsreu.kuznecovsokolov12.datalayer.data.Sanction;
import ru.rsreu.kuznecovsokolov12.datalayer.data.SanctionType;
import ru.rsreu.kuznecovsokolov12.datalayer.data.User;
import ru.rsreu.kuznecovsokolov12.utils.ResourcerHolder;

public class OracleSanctionDAO implements SanctionDAO, ResourcerHolder {
	
	private static final String SQL_SELECT_USER_SANCTIONS = resourser.getString("sql.sanction.select_all_user_sanctions");
	private static final String SQL_SELECT_SANCTIONS_BY_USER = resourser.getString("sql.sanction.select_sanctions_by_user");
	private static final String SQL_SELECT_LAST_USER_SANCTION = resourser.getString("sql.sanction.select_last_user_sanction");
	private static final String SQL_SELECT_SANCTION_TYPE_BY_NAME = resourser.getString("sql.sanction.select_sanction_type_by_name");
	private static final String SQL_SANCTION_CREATE = resourser.getString("sql.sanction.create");

	public final static String COLUMN_SANCTION_ID 		= resourser.getString("sql.sanction.column.id");
	public final static String COLUMN_SANCTION_REASON 	= resourser.getString("sql.sanction.column.reason");
	public final static String COLUMN_SANCTION_TIME 	= resourser.getString("sql.sanction.column.time");
	public final static String[] ALL_SANCTION_COLUMNS = {COLUMN_SANCTION_ID, COLUMN_SANCTION_REASON, COLUMN_SANCTION_TIME};
	
	public final static String COLUMN_SANCTION_TYPE_ID 	= resourser.getString("sql.sanction_type.column.id");
	public final static String COLUMN_SANCTION_TYPE_NAME 	= resourser.getString("sql.sanction_type.column.name");
	public final static String[] ALL_SANCTION_TYPE_COLUMNS = {COLUMN_SANCTION_TYPE_ID, COLUMN_SANCTION_TYPE_NAME};
	
	
	private Connection connection;

	public OracleSanctionDAO(Connection connection) {
		this.connection = connection;
	}

	public Connection getConnection() {
		return this.connection;
	}

	@Override
	public List<Sanction> getUserSanctions(User receiver) throws SQLException {
		PreparedStatement ps;
		List<Sanction> result = new ArrayList<>();
		ps = this.connection.prepareStatement(SQL_SELECT_USER_SANCTIONS);
		ps.setInt(1, receiver.getId());
		ResultSet resultSet = ps.executeQuery();
		while (resultSet.next()) {
			Sanction sanction = getSanctionData(resultSet, ALL_SANCTION_COLUMNS);
			sanction.setType(getSanctionTypeData(resultSet, ALL_SANCTION_TYPE_COLUMNS));
			sanction.setSender(OracleUserDAO.getUserData(resultSet, OracleUserDAO.ALL_USER_COLUMNS));
			sanction.setReceiver(OracleUserDAO.getUserData(resultSet, 12, 13, 14, 15, 16, 17));
			result.add(sanction);
		}
		return result;
	}

	@Override
	public List<Sanction> getSanctionsByUser(User sender) throws SQLException {
		PreparedStatement ps;
		List<Sanction> result = new ArrayList<>();
		ps = this.connection.prepareStatement(SQL_SELECT_SANCTIONS_BY_USER);
		ps.setInt(1, sender.getId());
		ResultSet resultSet = ps.executeQuery();
		while (resultSet.next()) {
			Sanction sanction = getSanctionData(resultSet, ALL_SANCTION_COLUMNS);
			sanction.setType(getSanctionTypeData(resultSet, ALL_SANCTION_TYPE_COLUMNS));
			sanction.setSender(OracleUserDAO.getUserData(resultSet, OracleUserDAO.ALL_USER_COLUMNS));
			sanction.setReceiver(OracleUserDAO.getUserData(resultSet, 12, 13, 14, 15, 16, 17));
			result.add(sanction);
		}
		return result;
	}

	@Override
	public Sanction getLastUserSanction(User user) throws SQLException {
		PreparedStatement ps;
		Sanction sanction = new Sanction();
		ps = this.connection.prepareStatement(SQL_SELECT_LAST_USER_SANCTION);
		ps.setInt(1, user.getId());
		ResultSet resultSet = ps.executeQuery();
		while (resultSet.next()) {
			sanction = getSanctionData(resultSet, ALL_SANCTION_COLUMNS);
			sanction.setType(getSanctionTypeData(resultSet, ALL_SANCTION_TYPE_COLUMNS));
			sanction.setSender(OracleUserDAO.getUserData(resultSet, OracleUserDAO.ALL_USER_COLUMNS));
			sanction.setReceiver(OracleUserDAO.getUserData(resultSet, 12, 13, 14, 15, 16, 17));
		}
		return sanction;
	}
	
	@Override
	public void addSanction(Sanction sanction) throws SQLException {
		PreparedStatement ps;
		ps = this.connection.prepareStatement(SQL_SANCTION_CREATE);
		ps.setInt(1, sanction.getType().getId());
		ps.setInt(2, sanction.getSender().getId());
		ps.setInt(3, sanction.getReceiver().getId());
		ps.setString(4, sanction.getReason());
		ps.executeUpdate();
	}
	
	@Override
	public SanctionType getSanctionTypeByName(String name) throws SQLException {
		PreparedStatement ps;
		SanctionType sanctionType = new SanctionType();
		ps = this.connection.prepareStatement(SQL_SELECT_SANCTION_TYPE_BY_NAME);
		ps.setString(1, name);
		ResultSet resultSet = ps.executeQuery();
		while (resultSet.next()) {
			sanctionType = getSanctionTypeData(resultSet, ALL_SANCTION_TYPE_COLUMNS);
		}
		return sanctionType;
	}
	
	public static Sanction getSanctionData(ResultSet resultSet, String... columns) throws SQLException {
		Sanction sanction = new Sanction();
		for (String column : columns) {
			if (column.equals(COLUMN_SANCTION_ID)) {
				sanction.setId(resultSet.getInt(column));
				continue;
			}
			
			if (column.equals(COLUMN_SANCTION_REASON)) {
				sanction.setReason(resultSet.getString(column));
				continue;
			}
			
			if (column.equals(COLUMN_SANCTION_TIME)) {
				sanction.setTime(resultSet.getTimestamp(column));
				continue;
			}
		}
		return sanction ;
	}
	
	public static SanctionType getSanctionTypeData(ResultSet resultSet, String... columns) throws SQLException {
		SanctionType sanctionType = new SanctionType();
		for (String column : columns) {
			if (column.equals(COLUMN_SANCTION_TYPE_ID)) {
				sanctionType.setId(resultSet.getInt(column));
				continue;
			}
			
			if (column.equals(COLUMN_SANCTION_TYPE_NAME)) {
				sanctionType.setName(resultSet.getString(column));
				continue;
			}
		}
		return sanctionType;
	}

}
