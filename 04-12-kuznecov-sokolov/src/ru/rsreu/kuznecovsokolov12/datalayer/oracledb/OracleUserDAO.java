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

import ru.rsreu.kuznecovsokolov12.datalayer.UserDAO;
import ru.rsreu.kuznecovsokolov12.datalayer.data.User;

public class OracleUserDAO extends UserDAO {

	private Connection connection;

	public OracleUserDAO(Connection connection) {
		this.connection = connection;
	}

	public Connection getConnection() {
		return this.connection;
	}

	@Override
	public User getUserByID(int id) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User getUserByLogin(String login) throws SQLException {
		User user = new User();
		Statement statement = this.getConnection().createStatement();
		Resourcer resourcer = ProjectResourcer.getInstance();
		ResultSet resultSet = statement
				.executeQuery("SELECT * FROM \"USER\" WHERE \"login\" = " + "'" + login + "'");
		if (resultSet.next()) {
			user.setId(resultSet.getInt("ID"));
			user.setLogin(resultSet.getString("LOGIN"));
			user.setPassword(resultSet.getString("PASSWORD"));
			user.setName(resultSet.getString("NAME"));
			user.setEmail(resultSet.getString("EMAIL"));
			user.setIsAuthorized(resultSet.getBoolean("IS_AUTHORIZED"));
		}
		return user;
	}

	@Override
	public List<User> getUsers() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addUser(User user) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateUser(User user) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	
}
