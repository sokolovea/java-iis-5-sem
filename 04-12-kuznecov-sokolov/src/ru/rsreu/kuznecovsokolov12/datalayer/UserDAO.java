package ru.rsreu.kuznecovsokolov12.datalayer;

import java.sql.SQLException;
import java.util.List;

import ru.rsreu.kuznecovsokolov12.datalayer.data.*;

public interface UserDAO {
	
	User getUserByID(int id) throws SQLException;
	
	User getUserByLogin(String login) throws SQLException;
	
	List<User> getUsers() throws SQLException;
	
	void addUser(User user) throws SQLException;
	
	void updateUser(User user) throws SQLException;
}
