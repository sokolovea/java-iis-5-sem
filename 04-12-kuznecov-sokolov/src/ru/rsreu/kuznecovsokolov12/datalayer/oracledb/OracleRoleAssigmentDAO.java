package ru.rsreu.kuznecovsokolov12.datalayer.oracledb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ru.rsreu.kuznecovsokolov12.datalayer.RoleAssigmentDAO;
import ru.rsreu.kuznecovsokolov12.datalayer.data.RoleAssigment;
import ru.rsreu.kuznecovsokolov12.datalayer.data.User;

public class OracleRoleAssigmentDAO implements RoleAssigmentDAO {

	private static final String SQL_ALL_ROLE_ASSIGMENT_SELECT = "select \"role_assigment_id\", \"time\", \"role_id\", \"role_name\", \"USER\".*, \"USER_receiver\".* from \"USER\" join \"ROLE_ASSIGMENT\" on \"user_id\" = \"sender\" join \"ROLE\" on \"role_id\" = \"role\" join \"USER\" \"USER_receiver\" on \"USER_receiver\".\"user_id\" = \"receiver\"";
	private static final String SQL_SELECT_ROLE_ASSIGMENT_FOR_USER = "select \"role_assigment_id\", \"time\", \"role_id\", \"role_name\", \"USER\".*, \"USER_receiver\".* from \"USER\" join \"ROLE_ASSIGMENT\" on \"user_id\" = \"sender\" join \"ROLE\" on \"role_id\" = \"role\" join \"USER\" \"USER_receiver\" on \"USER_receiver\".\"user_id\" = \"receiver\" where \"receiver\" = ?";
	
	public final static String COLUMN_ROLE_ASSIGMENT_ID 	= "role_assigment_id";
	public final static String COLUMN_ROLE_ASSIGMENT_TIME 	= "time";
	public final static String[] ALL_ROLE_ASSIGMENT_COLUMNS = {COLUMN_ROLE_ASSIGMENT_ID, COLUMN_ROLE_ASSIGMENT_TIME};
	
	private Connection connection;

	public OracleRoleAssigmentDAO(Connection connection) {
		this.connection = connection;
	}

	public Connection getConnection() {
		return this.connection;
	}

	@Override
	public List<RoleAssigment> getAllRoleAssigments() throws SQLException {
		PreparedStatement ps;
		List<RoleAssigment> result = new ArrayList<>();
		ps = this.connection.prepareStatement(SQL_ALL_ROLE_ASSIGMENT_SELECT);
		ResultSet resultSet = ps.executeQuery();
		while (resultSet.next()) {
			RoleAssigment roleAssig = getRoleAssigmentData(resultSet, ALL_ROLE_ASSIGMENT_COLUMNS);
			roleAssig.setRole(OracleRoleDAO.getRoleData(resultSet, OracleRoleDAO.ALL_ROLE_COLUMNS));
			roleAssig.setSender(OracleUserDAO.getUserData(resultSet, OracleUserDAO.ALL_USER_COLUMNS));
			roleAssig.setReceiver(OracleUserDAO.getUserData(resultSet, 11, 12, 13, 14, 15, 16));
			result.add(roleAssig);
		}
		return result;
	}
	
	@Override
	public List<RoleAssigment> getRoleAssigmentsForUser(User user) throws SQLException {
		PreparedStatement ps;
		List<RoleAssigment> result = new ArrayList<>();
		ps = this.connection.prepareStatement(SQL_SELECT_ROLE_ASSIGMENT_FOR_USER);
		ps.setInt(1, user.getId());
		ResultSet resultSet = ps.executeQuery();
		while (resultSet.next()) {
			RoleAssigment roleAssig = getRoleAssigmentData(resultSet, ALL_ROLE_ASSIGMENT_COLUMNS);
			roleAssig.setRole(OracleRoleDAO.getRoleData(resultSet, OracleRoleDAO.ALL_ROLE_COLUMNS));
			roleAssig.setSender(OracleUserDAO.getUserData(resultSet, OracleUserDAO.ALL_USER_COLUMNS));
			roleAssig.setReceiver(OracleUserDAO.getUserData(resultSet, 11, 12, 13, 14, 15, 16));
			result.add(roleAssig);
		}
		return result;
	}

	public static RoleAssigment getRoleAssigmentData(ResultSet resultSet, String... columns) throws SQLException {
		RoleAssigment roleAssigment = new RoleAssigment();
		for (String column : columns) {
			if (column.equals(COLUMN_ROLE_ASSIGMENT_ID)) {
				roleAssigment.setId(resultSet.getInt(column));
				continue;
			}
			
			if (column.equals(COLUMN_ROLE_ASSIGMENT_TIME)) {
				roleAssigment.setTime(resultSet.getTimestamp(column));
				continue;
			}
		}
		return roleAssigment ;
	}

}
