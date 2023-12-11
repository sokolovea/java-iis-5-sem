package ru.rsreu.kuznecovsokolov12.commands;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import ru.rsreu.kuznecovsokolov12.exceptions.RedirectErrorPage;
import ru.rsreu.kuznecovsokolov12.servlet.EnumLogin;
import ru.rsreu.kuznecovsokolov12.utils.ConfigurationManager;

public class MenuCommand implements ActionCommand {

	@Override
	public String execute(HttpServletRequest request) {
		
		HttpSession session = request.getSession(false);
		
		if (session == null) {
			return ConfigurationManager.getProperty("path.page.index");
		}
		
		String destination = request.getParameter(MenuCommand.PARAM_DESTINATION);

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
			if (destination.equals(ActionCommand.DESTINATION_EXIT_TEAM)) {
				MenuLogic.userExitFromTeam(login);
				session.setAttribute(ActionCommand.PARAM_TEAM_ID, null);
				return MenuCommand.URL_MAIN_PAGE;
			}
		}
		
		if (loginResult == EnumLogin.USER || loginResult == EnumLogin.EXPERT || loginResult == EnumLogin.CAPTAIN) {
			if (destination.equals(ActionCommand.DESTINATION_TEAM)) {
				try {
					Object tempTeamId = request.getSession().getAttribute(MenuCommand.PARAM_TEAM_ID);
					if (tempTeamId == null) {
						return ConfigurationManager.getProperty("path.page.team_select");
					}
					int teamId = 0;
					if (loginResult == EnumLogin.EXPERT) {
						teamId = Integer.parseInt(request.getParameter(ActionCommand.PARAM_TEAM_ID));
					} else {
						teamId = (int) request.getSession().getAttribute(MenuCommand.PARAM_TEAM_ID);
					}
					MenuLogic.fillTeamPageForUser(request, login, teamId);
				}
				catch (RedirectErrorPage e) {
					MenuLogic.fillTeamSelectPageForUser(request, login);
					return e.getUrl();
				} 
				return ConfigurationManager.getProperty("path.page.team");
			}
			
			if (destination.equals(ActionCommand.DESTINATION_MAIN)) {
				MenuLogic.fillTeamSelectPageForUser(request, login);
				return ConfigurationManager.getProperty("path.page.team_select");
			}
		}
		
		if (loginResult == EnumLogin.MODERATOR) {
			if (destination.equals(ActionCommand.DESTINATION_MAIN)) {
				MenuLogic.fillMainPageForModerator(request);
				return ConfigurationManager.getProperty("path.page.moderator");
			}
		}
		
		if (loginResult == EnumLogin.ADMIN ) {
			if (destination.equals(ActionCommand.DESTINATION_SETTINGS)) {
				MenuLogic.fillSettingsPageForAdmin(request);
				return ConfigurationManager.getProperty("path.page.admin_settings");
			} 
			
			if (destination.equals(ActionCommand.DESTINATION_MAIN)) {
				MenuLogic.fillMainPageForAdmin(request);
				return ConfigurationManager.getProperty("path.page.admin");
			}
		}
		return ConfigurationManager.getProperty("path.page.login");
	}

}
