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
import ru.rsreu.kuznecovsokolov12.datalayer.data.User;

public class OracleRoleDAO extends RoleDAO {

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
		Statement statement = this.getConnection().createStatement();
		Resourcer resourcer = ProjectResourcer.getInstance();
		ResultSet resultSet = statement
				.executeQuery("select \"ROLE\".* from (\"USER\" join \"ROLE_ASSIGMENT\" on \"USER\".\"id\" = \"ROLE_ASSIGMENT\".\"receiver\") join \"ROLE\" on \"ROLE_ASSIGMENT\".\"role\" = \"ROLE\".\"id\" where \"USER\".\"id\" = " + user.getId());
		if (resultSet.next()) {
			role.setId(resultSet.getInt("ID"));
			role.setName(resultSet.getString("NAME"));
		}
		return role;
	}
	
}
