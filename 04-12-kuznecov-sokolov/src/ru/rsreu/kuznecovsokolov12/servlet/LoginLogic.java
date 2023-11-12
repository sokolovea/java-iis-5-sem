package ru.rsreu.kuznecovsokolov12.servlet;

import java.sql.Connection;
import java.sql.SQLException;

import jdk.jshell.UnresolvedReferenceException;
import ru.rsreu.kuznecovsokolov12.datalayer.data.*;
import ru.rsreu.kuznecovsokolov12.datalayer.DAOFactory;
import ru.rsreu.kuznecovsokolov12.datalayer.DBType;
import ru.rsreu.kuznecovsokolov12.datalayer.RoleDAO;
import ru.rsreu.kuznecovsokolov12.datalayer.UserDAO;
import ru.rsreu.kuznecovsokolov12.datalayer.oracledb.OracleDataBaseDAOFactory;
import ru.rsreu.kuznecovsokolov12.datalayer.oracledb.OracleUserDAO;

public class LoginLogic {
	
//	private final static String ADMIN_LOGIN = "admin";
//	private final static String ADMIN_PASS = "1";
//	
//	private final static String MODERATOR_LOGIN = "moderator";
//	private final static String MODERATOR_PASS = "1";
//	
//	private final static String EXPERT_LOGIN = "expert";
//	private final static String EXPERT_PASS = "1";
//	
//	private final static String CAPTAIN_LOGIN = "captain";
//	private final static String CAPTAIN_PASS = "1";
//	
//	private final static String USER_LOGIN = "user";
//	private final static String USER_PASS = "1";

	public static EnumLogin checkLogin(String enterLogin, String enterPass) throws SQLException {
		
		DAOFactory factory = DAOFactory.getInstance(DBType.ORACLE);
		UserDAO userDAO = factory.getUserDAO();
		RoleDAO roleDAO = factory.getRoleDAO();
		
		User user = userDAO.getUserByLogin(enterLogin);
		if (user.getLogin() == null) {
			return EnumLogin.NOUSER;
		}
		boolean loginResult = user.checkPassword(enterPass);
		if (!loginResult) {
			factory.returnConnectionToPool();
			return EnumLogin.NOUSER;
		}
		Role role = roleDAO.getUserRole(user);
		String roleName = role.getName();
		factory.returnConnectionToPool();
		if (roleName.equals("Administrator")) {
			return EnumLogin.ADMIN;
		} else if (roleName.equals("Moderator")) {
			return EnumLogin.MODERATOR;
		} else if (roleName.equals("Expert")) {
			return EnumLogin.EXPERT;
		} else if (roleName.equals("Common user")) {
			return EnumLogin.USER;
		}
		return EnumLogin.NOUSER;
		
	}
}