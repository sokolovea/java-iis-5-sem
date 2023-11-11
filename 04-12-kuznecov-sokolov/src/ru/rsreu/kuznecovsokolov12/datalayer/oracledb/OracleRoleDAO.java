package ru.rsreu.kuznecovsokolov12.datalayer.oracledb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.prutzkow.resourcer.ProjectResourcer;
import com.prutzkow.resourcer.Resourcer;

import ru.rsreu.kuznecovsokolov12.datalayer.RoleDAO;
import ru.rsreu.kuznecovsokolov12.datalayer.UserDAO;
import ru.rsreu.kuznecovsokolov12.datalayer.data.Role;
import ru.rsreu.kuznecovsokolov12.datalayer.data.RoleGroup;
import ru.rsreu.kuznecovsokolov12.datalayer.data.Sanction;
import ru.rsreu.kuznecovsokolov12.datalayer.data.SanctionType;
import ru.rsreu.kuznecovsokolov12.datalayer.data.User;

public class OracleRoleDAO implements RoleDAO {

	private final static String SQL_SELECT_ROLE_BY_USER = "select \"ROLE\".* from (\"USER\" join \"ROLE_ASSIGMENT\" on \"USER\".\"user_id\" = \"ROLE_ASSIGMENT\".\"receiver\") join \"ROLE\" on \"ROLE_ASSIGMENT\".\"role\" = \"ROLE\".\"role_id\" where \"USER\".\"user_id\" = ?";
	
	private final static String COLUMN_ROLE_ID 		= "role_id";
	private final static String COLUMN_ROLE_NAME 	= "role_name";
	private final static String[] ALL_ROLE_COLUMNS = {COLUMN_ROLE_ID, COLUMN_ROLE_NAME};
	
	private final static String COLUMN_ROLE_GROUP_ID 	= "role_group_id";
	private final static String COLUMN_ROLE_GROUP_NAME 	= "role_group_name";
	private final static String[] ALL_ROLE_GROUP_COLUMNS = {COLUMN_ROLE_GROUP_ID, COLUMN_ROLE_GROUP_NAME};
	
	private Connection connection;

	public OracleRoleDAO(Connection connection) {
		this.connection = connection;
	}

	public Connection getConnection() {
		return this.connection;
	}

	@Override
	public Role getUserRole(User user) throws SQLException {
		Role role = new Role();
		PreparedStatement ps;
		ps = this.connection.prepareStatement(SQL_SELECT_ROLE_BY_USER);
		ps.setInt(1, user.getId());
		ResultSet resultSet = ps.executeQuery();
		if (resultSet.next()) {
			role = getRoleData(resultSet, ALL_ROLE_COLUMNS);
		}
		return role;
	}
	
	public static Role getRoleData(ResultSet resultSet, String... columns) throws SQLException {
		Role role = new Role();
		for (String column : columns) {
			if (column.equals(COLUMN_ROLE_ID)) {
				role.setId(resultSet.getInt(column));
				continue;
			}
			
			if (column.equals(COLUMN_ROLE_NAME)) {
				role.setName(resultSet.getString(column));
				continue;
			}
		}
		return role ;
	}
	
	public static RoleGroup getRoleGroupData(ResultSet resultSet, String... columns) throws SQLException {
		RoleGroup roleGroup = new RoleGroup();
		for (String column : columns) {
			if (column.equals(COLUMN_ROLE_GROUP_ID)) {
				roleGroup.setId(resultSet.getInt(column));
				continue;
			}
			
			if (column.equals(COLUMN_ROLE_GROUP_NAME)) {
				roleGroup.setName(resultSet.getString(column));
				continue;
			}
		}
		return roleGroup;
	}

	
}
