package ru.rsreu.kuznecovsokolov12.commands;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import ru.rsreu.kuznecovsokolov12.servlet.EnumLogin;
import ru.rsreu.kuznecovsokolov12.utils.ConfigurationManager;

public class ReportCommand implements ActionCommand {

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
