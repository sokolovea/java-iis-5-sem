package ru.rsreu.kuznecovsokolov12.datalayer;

public abstract class DAOFactory {
	public static DAOFactory getInstance(DBType dbType) {
		DAOFactory result = dbType.getDAOFactory();
		return result;
	}
	
	public abstract UserDAO getUserDAO();
	
	public abstract RoleDAO getRoleDAO();
	
	public abstract SettingDAO getSettingDAO();
	
	public abstract void returnConnectionToPool();
	
}
