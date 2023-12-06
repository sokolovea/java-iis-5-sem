package ru.rsreu.kuznecovsokolov12.servlet;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.security.auth.message.callback.PrivateKeyCallback.Request;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.catalina.Session;


import ru.rsreu.kuznecovsokolov12.datalayer.DAOFactory;
import ru.rsreu.kuznecovsokolov12.datalayer.DBType;
import ru.rsreu.kuznecovsokolov12.datalayer.DeletedMessageDAO;
import ru.rsreu.kuznecovsokolov12.datalayer.MessageAttachingDAO;
import ru.rsreu.kuznecovsokolov12.datalayer.MessageDAO;
import ru.rsreu.kuznecovsokolov12.datalayer.RoleDAO;
import ru.rsreu.kuznecovsokolov12.datalayer.SettingDAO;
import ru.rsreu.kuznecovsokolov12.datalayer.TeamDAO;
import ru.rsreu.kuznecovsokolov12.datalayer.TeamInteractDAO;
import ru.rsreu.kuznecovsokolov12.datalayer.UserDAO;
import ru.rsreu.kuznecovsokolov12.datalayer.data.DeletedMessage;
import ru.rsreu.kuznecovsokolov12.datalayer.data.Message;
import ru.rsreu.kuznecovsokolov12.datalayer.data.MessageAttaching;
import ru.rsreu.kuznecovsokolov12.datalayer.data.Role;
import ru.rsreu.kuznecovsokolov12.datalayer.data.Setting;
import ru.rsreu.kuznecovsokolov12.datalayer.data.Team;
import ru.rsreu.kuznecovsokolov12.datalayer.data.TeamInteract;
import ru.rsreu.kuznecovsokolov12.datalayer.data.User;
import ru.rsreu.kuznecovsokolov12.exceptions.RedirectErrorPage;

public class MenuCommand implements ActionCommand {

	@Override
	public String execute(HttpServletRequest request) {
		
		HttpSession session = request.getSession(false);
		
		if (session == null) {
			return ConfigurationManager.getProperty("path.page.index");
		}
		
		String login = (String) session.getAttribute(PARAM_USER_LOGIN);
		String password = (String) session.getAttribute(PARAM_USER_PASSWORD);
		String destination = request.getParameter(MenuCommand.PARAM_DESTINATION);
		EnumLogin loginResult = null;
		
		try {
			loginResult = LoginLogic.checkLogin(login, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		MenuLogic.initDAOitems();
		
		String page = MenuCommand.getPage(destination, request);
		
		MenuLogic.closeFactory();
		
		return page;
	}
	
	public static String getPage(String destination, HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		
		if (session == null) {
			return ConfigurationManager.getProperty("path.page.index");
		}
		
		EnumLogin loginResult = (EnumLogin) session.getAttribute(MenuCommand.PARAM_USER_ROLE);
		String login = (String) session.getAttribute(MenuCommand.PARAM_USER_LOGIN);
		if (loginResult == EnumLogin.USER) {
			if (destination.equals("exit_team")) {
				MenuLogic.userExitFromTeam(login);
				return MenuCommand.URL_MAIN_PAGE;
			}
		}
		
		if (loginResult == EnumLogin.USER || loginResult == EnumLogin.EXPERT || loginResult == EnumLogin.CAPTAIN) {
			if (destination.equals("team")) {
				try {
					Object tempTeamId = request.getSession().getAttribute(MenuCommand.PARAM_TEAM_ID);
					if (tempTeamId == null) {
						return ConfigurationManager.getProperty("path.page.team_select");
					}
					int teamId = 0;
					if (loginResult == EnumLogin.EXPERT) {
						teamId = Integer.parseInt(request.getParameter("team_id"));
					} else {
						teamId = (int) request.getSession().getAttribute(MenuCommand.PARAM_TEAM_ID);
					}
					MenuLogic.fillTeamPageForUser(request, login, teamId);
				}
				catch (RedirectErrorPage e) {
					return ConfigurationManager.getProperty("path.page.error");
				} 
				return ConfigurationManager.getProperty("path.page.team");
			}
			
			if (destination.equals("main")) {
				MenuLogic.fillTeamSelectPageForUser(request, login);
				return ConfigurationManager.getProperty("path.page.team_select");
			}
		}
		
		if (loginResult == EnumLogin.MODERATOR) {
			if (destination.equals("main")) {
				MenuLogic.fillMainPageForModerator(request);
				return ConfigurationManager.getProperty("path.page.moderator");
			}
		}
		
		if (loginResult == EnumLogin.ADMIN ) {
			if (destination.equals("settings")) {
				MenuLogic.fillSettingsPageForAdmin(request);
				return ConfigurationManager.getProperty("path.page.admin_settings");
			} 
			
			if (destination.equals("main")) {
				MenuLogic.fillMainPageForAdmin(request);
				return ConfigurationManager.getProperty("path.page.admin");
			}
		}
		return ConfigurationManager.getProperty("path.page.login");
	}

}
