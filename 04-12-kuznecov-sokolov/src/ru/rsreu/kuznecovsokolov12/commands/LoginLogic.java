package ru.rsreu.kuznecovsokolov12.commands;

import java.sql.SQLException;

import ru.rsreu.kuznecovsokolov12.datalayer.data.*;
import ru.rsreu.kuznecovsokolov12.datalayer.DAOFactory;
import ru.rsreu.kuznecovsokolov12.datalayer.DBType;
import ru.rsreu.kuznecovsokolov12.datalayer.RoleDAO;
import ru.rsreu.kuznecovsokolov12.datalayer.SanctionDAO;
import ru.rsreu.kuznecovsokolov12.datalayer.UserDAO;
import ru.rsreu.kuznecovsokolov12.servlet.EnumLogin;

public class LoginLogic {

	public static EnumLogin checkLogin(String enterLogin, String enterPass) throws SQLException {
		
		DAOFactory factory = DAOFactory.getInstance(DBType.ORACLE);
		UserDAO userDAO = factory.getUserDAO();
		RoleDAO roleDAO = factory.getRoleDAO();
		SanctionDAO sanctionDAO = factory.getSanctionDAO();
		
		User user = userDAO.getUserByLogin(enterLogin);
		if (user.getLogin() == null) {
			factory.returnConnectionToPool();
			return EnumLogin.NOUSER;
		}
		
		Sanction lastSanction = sanctionDAO.getLastUserSanction(user);
		if (lastSanction.getReceiver() != null) {
			if (lastSanction.getType().getName().toString().equals(ActionCommand.SANCTION_TYPE_BLOCK)) {
				factory.returnConnectionToPool();
				return EnumLogin.NOUSER;
			}
		}
		boolean loginResult = user.checkPassword(enterPass);
		if (!loginResult) {
			factory.returnConnectionToPool();
			return EnumLogin.NOUSER;
		}
		Role role = roleDAO.getUserRole(user);
		String roleName = role.getName();
		factory.returnConnectionToPool();
		if (roleName == null) {
			return EnumLogin.NOUSER;
		}
		if (roleName.equals(ActionCommand.ROLE_ADMIN)) {
			return EnumLogin.ADMIN;
		} else if (roleName.equals(ActionCommand.ROLE_MODER)) {
			return EnumLogin.MODERATOR;
		} else if (roleName.equals(ActionCommand.ROLE_EXPERT)) {
			return EnumLogin.EXPERT;
		} else if (roleName.equals(ActionCommand.ROLE_USER)) {
			return EnumLogin.USER;
		}
		return EnumLogin.NOUSER;
	}
	
	public static boolean isCapitan(String userLogin, int team_id) {
		DAOFactory factory = DAOFactory.getInstance(DBType.ORACLE);
		UserDAO userDAO = factory.getUserDAO();
		User capitan = null;
		try {
			capitan = userDAO.getTeamCapitan(new Team(team_id));
			factory.returnConnectionToPool();
			if (capitan.getLogin() == null) {
				return false;
			}
			return capitan.getLogin().equals(userLogin);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		factory.returnConnectionToPool();
		return false;
	}
	
	public static void setUserAuth(String login, boolean auth) throws SQLException {
		DAOFactory factory = DAOFactory.getInstance(DBType.ORACLE);
		UserDAO userDAO = factory.getUserDAO();
		User user = userDAO.getUserByLogin(login);
		user.setIsAuthorized(auth);
		userDAO.updateUser(user);
		factory.returnConnectionToPool();
	}
}