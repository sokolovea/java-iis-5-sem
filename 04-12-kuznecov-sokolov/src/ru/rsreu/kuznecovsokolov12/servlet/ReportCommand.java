package ru.rsreu.kuznecovsokolov12.servlet;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import ru.rsreu.kuznecovsokolov12.datalayer.DAOFactory;
import ru.rsreu.kuznecovsokolov12.datalayer.DBType;
import ru.rsreu.kuznecovsokolov12.datalayer.RoleAssigmentDAO;
import ru.rsreu.kuznecovsokolov12.datalayer.UserDAO;
import ru.rsreu.kuznecovsokolov12.datalayer.data.RoleAssigment;
import ru.rsreu.kuznecovsokolov12.datalayer.data.User;

public class ReportCommand implements ActionCommand {
	private static final String PARAM_NAME_LOGIN = "login";
	private static final String PARAM_NAME_PASSWORD = "password";
	
	@Override
	public String execute(HttpServletRequest request) {
		String login = request.getParameter(PARAM_NAME_LOGIN);
		request.getSession().setAttribute("userName", login);
		String password = request.getParameter(PARAM_NAME_PASSWORD);
		request.getSession().setAttribute("userPassword", password);
		EnumLogin loginResult = null;
		try {
			loginResult = LoginLogic.checkLogin(login, password);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		request.setAttribute(ReportCommand.getRequestAttribute(loginResult), login);
		DAOFactory factory = DAOFactory.getInstance(DBType.ORACLE);
		UserDAO userDAO = factory.getUserDAO();
		RoleAssigmentDAO roleAssigmentDAO = factory.getRoleAssigmentDAO();
		List<User> adminReportFirst = new ArrayList<User>();
		List<RoleAssigment> adminReportSecond = new ArrayList<RoleAssigment>();
		User userForSecondReport = new User();
		try {
			adminReportFirst = userDAO.getUsers();
			String tempLogin = request.getParameter("adminUserRole");
			userForSecondReport = userDAO.getUserByLogin(tempLogin);
			adminReportSecond = roleAssigmentDAO.getRoleAssigmentsForUser(userForSecondReport);
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			// TODO Auto-generated catch block
			factory.returnConnectionToPool();
		}
		if (loginResult == EnumLogin.ADMIN) {
			 request.setAttribute("adminReportFirst", adminReportFirst);
			 request.setAttribute("adminReportSecond", adminReportSecond);
			 request.setAttribute("element", "role_assigment_history_button");
			 request.setAttribute("report_id", "report_admin_user_role_history");
		}
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
