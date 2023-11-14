package ru.rsreu.kuznecovsokolov12.datalayer;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import ru.rsreu.kuznecovsokolov12.datalayer.data.*;

public interface UserDAO {
	
	User getUserByID(int id) throws SQLException;
	
	User getUserByLogin(String login) throws SQLException;
	
	User getExpertForTeam(Team team) throws SQLException;
	
	List<User> getUsers() throws SQLException;
	
	List<User> getUnprivilegedUsers() throws SQLException;
	
	List<User> getBlockedUsersMoreNTimes(int N) throws SQLException;
	
	List<User> getTeamUserList(Team team) throws SQLException;
	
	Map<User, Role> getUsersWithRole() throws SQLException;
	
	User getTeamCapitan(Team team) throws SQLException;
	
	void addUser(User user) throws SQLException;
	
	void updateUser(User user) throws SQLException;
	
	void deleteUser(User user) throws SQLException;
}
