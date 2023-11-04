package ru.rsreu.kuznecovsokolov12.datalayer;

import java.sql.SQLException;
import java.util.List;

import ru.rsreu.kuznecovsokolov12.datalayer.data.TableRow;

public abstract class UserDAO {
	
	public abstract String getUserByLogin(String login) throws SQLException;
	
	public abstract List<String> getUsersListForAdmin() throws SQLException;
	
	public abstract List<String> getUsersListForModerator() throws SQLException;
	
	public abstract List<String> getUsersListForOtherUser() throws SQLException;
	
	public abstract List<String> getUserInfoForAdmin() throws SQLException;
	
	public abstract List<String> getUserInfoForAdminReport() throws SQLException;
	
	public abstract void setUserInfo(String userInfo) throws SQLException;
	
	public abstract List<String> getUserRoleHistory() throws SQLException;
}
