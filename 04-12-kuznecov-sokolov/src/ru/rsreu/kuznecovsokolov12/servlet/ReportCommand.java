package ru.rsreu.kuznecovsokolov12.servlet;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import ru.rsreu.kuznecovsokolov12.datalayer.DAOFactory;
import ru.rsreu.kuznecovsokolov12.datalayer.DBType;
import ru.rsreu.kuznecovsokolov12.datalayer.MessageDAO;
import ru.rsreu.kuznecovsokolov12.datalayer.RoleAssigmentDAO;
import ru.rsreu.kuznecovsokolov12.datalayer.SanctionDAO;
import ru.rsreu.kuznecovsokolov12.datalayer.TeamDAO;
import ru.rsreu.kuznecovsokolov12.datalayer.UserDAO;
import ru.rsreu.kuznecovsokolov12.datalayer.data.Message;
import ru.rsreu.kuznecovsokolov12.datalayer.data.Role;
import ru.rsreu.kuznecovsokolov12.datalayer.data.RoleAssigment;
import ru.rsreu.kuznecovsokolov12.datalayer.data.Sanction;
import ru.rsreu.kuznecovsokolov12.datalayer.data.Team;
import ru.rsreu.kuznecovsokolov12.datalayer.data.User;

public class ReportCommand implements ActionCommand {
	private static final String PARAM_NAME_LOGIN = "login";
	private static final String PARAM_NAME_PASSWORD = "password";

	@Override
	public String execute(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		
		if (session == null) {
			return ConfigurationManager.getProperty("path.page.index");
		}
		String login = (String) session.getAttribute(DatabaseCommand.PARAM_USER_LOGIN);
		String password = (String) session.getAttribute(DatabaseCommand.PARAM_USER_PASSWORD);
		EnumLogin loginResult = null;
		
		ReportLogic.initDAOitems();
		
		try {
			loginResult = LoginLogic.checkLogin(login, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		request.setAttribute(ReportCommand.getRequestAttribute(loginResult), login);
		
		if (loginResult == EnumLogin.ADMIN) {
			ReportLogic.addAdminReports(request);
		} else if (loginResult == EnumLogin.USER) {
			ReportLogic.addUserReports(request, login);
		} else if (loginResult == EnumLogin.EXPERT) {
			ReportLogic.addExpertReports(request, login);
		} else if (loginResult == EnumLogin.MODERATOR) {
			ReportLogic.addModeratorReports(request, login);
		}
		
		ReportLogic.closeFactory();
		
		return ReportCommand.getPage(loginResult);
	}

	private static String getRequestAttribute(EnumLogin loginResult) {
		if (loginResult != EnumLogin.NOUSER) {
			return loginResult.toString();
		}
		return "errorLoginPassMessage";
	}

	private static String getPage(EnumLogin loginResult) {
		if (loginResult != EnumLogin.NOUSER) {
			return ConfigurationManager.getProperty("path.page.reports");
		}
		return ConfigurationManager.getProperty("path.page.login");
	}

}
