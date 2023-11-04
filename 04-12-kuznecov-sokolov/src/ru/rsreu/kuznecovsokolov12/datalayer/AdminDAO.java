package ru.rsreu.kuznecovsokolov12.datalayer;

import java.sql.SQLException;
import java.util.List;

import ru.rsreu.kuznecovsokolov12.datalayer.data.TableRow;

public abstract class AdminDAO {
	
	public abstract List<String> getNetworkInformationSystemSettings() throws SQLException;
	
	public abstract void setNetworkInformationSystemSettings(String settings) throws SQLException;
	
	public abstract List<String> getUserInfo() throws SQLException;
	
	public abstract void setUserInfo(String userInfo) throws SQLException;
	
	public abstract List<String> getUserRoleHistory() throws SQLException;
}
