package ru.rsreu.kuznecovsokolov12.servlet;

import java.sql.SQLException;
import java.util.List;

import javax.security.auth.message.callback.PrivateKeyCallback.Request;
import javax.servlet.http.HttpServletRequest;

import ru.rsreu.kuznecovsokolov12.datalayer.DAOFactory;
import ru.rsreu.kuznecovsokolov12.datalayer.DBType;
import ru.rsreu.kuznecovsokolov12.datalayer.SettingDAO;
import ru.rsreu.kuznecovsokolov12.datalayer.UserDAO;
import ru.rsreu.kuznecovsokolov12.datalayer.data.Setting;

public class MenuCommand implements ActionCommand {
	private static final String PARAM_NAME_LOGIN = "login";
	private static final String PARAM_NAME_PASSWORD = "password";
	private static final String PARAM_USER_NAME = "userName";
	private static final String PARAM_USER_PASSWORD = "userPassword";
	private static final String PARAM_DESTINATION = "destination";
	@Override
	public String execute(HttpServletRequest request) {
		String login = request.getParameter(PARAM_NAME_LOGIN);
		request.getSession().setAttribute(MenuCommand.PARAM_USER_NAME, login);
		String password = request.getParameter(PARAM_NAME_PASSWORD);
		request.getSession().setAttribute(MenuCommand.PARAM_USER_PASSWORD, password);
		String destination = request.getParameter(MenuCommand.PARAM_DESTINATION);
		EnumLogin loginResult = null;
		try {
			loginResult = LoginLogic.checkLogin(login, password);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return MenuCommand.getPage(loginResult, destination, request);
	}
	
//	private static String getRequestAttribute(EnumLogin loginResult) {
//		if (loginResult != EnumLogin.NOUSER) {
//			return loginResult.toString();
//		}
//		return "errorLoginPassMessage";
//	}
	
	private static String getPage(EnumLogin loginResult, String destination, HttpServletRequest request) {
		if (loginResult == EnumLogin.USER || loginResult == EnumLogin.EXPERT || loginResult == EnumLogin.CAPTAIN) {
			if (destination.equals("team")) {
				return ConfigurationManager.getProperty("path.page.team");
			} else if (destination.equals("main")) {
				return ConfigurationManager.getProperty("path.page.team_select");
			}
		}
		if (loginResult == EnumLogin.MODERATOR) {
			if (destination.equals("main")) {
				return ConfigurationManager.getProperty("path.page.moderator");
			}
		}
		if (loginResult == EnumLogin.ADMIN ) {
			if (destination.equals("settings")) {
				DAOFactory factory = DAOFactory.getInstance(DBType.ORACLE);
				SettingDAO settingDAO = factory.getSettingDAO();
				try {
					List<Setting> settingList = settingDAO.getSetting();
					request.setAttribute("setting_list", settingList);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				return ConfigurationManager.getProperty("path.page.admin_settings");
			} else if (destination.equals("main")) {
				return ConfigurationManager.getProperty("path.page.admin");
			}
//			} else if (destination.equals("settings_modify")) {
//				// Запись в базу данных новых настроек через DAO
//				щлщлщл
//				return ConfigurationManager.getProperty("path.page.admin_settings"); //возврат обратно
//			}
		}
		return ConfigurationManager.getProperty("path.page.login");
	}

}
