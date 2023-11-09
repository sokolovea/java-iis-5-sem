package ru.rsreu.kuznecovsokolov12.servlet;

import java.sql.Connection;
import java.sql.SQLException;

import jdk.jshell.UnresolvedReferenceException;
import ru.rsreu.kuznecovsokolov12.datalayer.data.*;
import ru.rsreu.kuznecovsokolov12.datalayer.DAOFactory;
import ru.rsreu.kuznecovsokolov12.datalayer.DBType;
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
		
		User user = userDAO.getUserByLogin(enterLogin);
		boolean loginResult = user.checkPassword(enterPass);
		factory.returnConnectionToPool();
		if (!loginResult) {
			return EnumLogin.NOUSER;
		}
//		if (ADMIN_LOGIN.equals(enterLogin) && ADMIN_PASS.equals(enterPass)) {
//			return EnumLogin.ADMIN;
//		} else if (MODERATOR_LOGIN.equals(enterLogin) && MODERATOR_PASS.equals(enterPass)) {
//			return EnumLogin.MODERATOR;
//		} else if (EXPERT_LOGIN.equals(enterLogin) && EXPERT_PASS.equals(enterPass)) {
//			return EnumLogin.EXPERT;
//		} else if (CAPTAIN_LOGIN.equals(enterLogin) && CAPTAIN_PASS.equals(enterPass)) {
//			return EnumLogin.CAPTAIN;
//		} else if (USER_LOGIN.equals(enterLogin) && USER_PASS.equals(enterPass)) {
//			return EnumLogin.USER;
//		}
		return EnumLogin.NOUSER;
		
	}
}