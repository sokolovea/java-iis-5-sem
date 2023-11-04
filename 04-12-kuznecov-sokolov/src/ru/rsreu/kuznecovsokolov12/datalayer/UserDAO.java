package ru.rsreu.kuznecovsokolov12.datalayer;

import java.sql.SQLException;
import java.util.List;

import ru.rsreu.kuznecovsokolov12.datalayer.data.*;

public abstract class UserDAO {
	
	public abstract User getUserByID(int id) throws SQLException;
	
	public abstract User getUserByLogin(String login) throws SQLException;
	
	public abstract List<User> getUsers() throws SQLException;
	
	public abstract void addUser(User user) throws SQLException;
	
	public abstract void updateUser(User user) throws SQLException;
}
