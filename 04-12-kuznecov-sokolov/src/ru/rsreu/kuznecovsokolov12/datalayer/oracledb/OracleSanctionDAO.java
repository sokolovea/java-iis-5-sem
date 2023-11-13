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

public class OracleSanctionDAO implements SanctionDAO {
	
	private static final String SQL_SELECT_USER_SANCTIONS = "select \"sanction_id\", \"reason\", \"time\", \"SANCTION_TYPE\".*, \"USER\".*, \"USER_receiver\".* from \"USER\" join \"SANCTION\" on \"USER\".\"user_id\" = \"sender\" join \"USER\" \"USER_receiver\" on \"USER_receiver\".\"user_id\" = \"receiver\" join \"SANCTION_TYPE\" on \"sanction_t_id\" = \"type\" where \"receiver\" = ?";
	private static final String SQL_SELECT_SANCTIONS_BY_USER = "select \"sanction_id\", \"reason\", \"time\", \"SANCTION_TYPE\".*, \"USER\".*, \"USER_receiver\".* from \"USER\" join \"SANCTION\" on \"USER\".\"user_id\" = \"sender\" join \"USER\" \"USER_receiver\" on \"USER_receiver\".\"user_id\" = \"receiver\" join \"SANCTION_TYPE\" on \"sanction_t_id\" = \"type\" where \"sender\" = ?";
	private static final String SQL_SELECT_LAST_USER_SANCTION = "select \"sanction_id\", \"reason\", \"time\", \"SANCTION_TYPE\".*, \"USER\".*, \"USER_receiver\".* from \"USER\" join \"SANCTION\" on \"USER\".\"user_id\" = \"sender\" join \"USER\" \"USER_receiver\" on \"USER_receiver\".\"user_id\" = \"receiver\" join \"SANCTION_TYPE\" on \"sanction_t_id\" = \"type\" where \"receiver\" = ? order by \"time\" desc FETCH NEXT 1 ROWS ONLY";
	private static final String SQL_SANCTION_CREATE = "INSERT INTO \"SANCTION\" (\"type\", \"sender\", \"receiver\", \"reason\", \"time\") VALUES (?, ?, ?, ?, (select sysdate from dual))";

	public final static String COLUMN_SANCTION_ID 		= "sanction_id";
	public final static String COLUMN_SANCTION_REASON 	= "reason";
	public final static String COLUMN_SANCTION_TIME 	= "time";
	public final static String[] ALL_SANCTION_COLUMNS = {COLUMN_SANCTION_ID, COLUMN_SANCTION_REASON, COLUMN_SANCTION_TIME};
	
	public final static String COLUMN_SANCTION_TYPE_ID 	= "sanction_t_id";
	public final static String COLUMN_SANCTION_TYPE_NAME 	= "sanction_t_name";
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
