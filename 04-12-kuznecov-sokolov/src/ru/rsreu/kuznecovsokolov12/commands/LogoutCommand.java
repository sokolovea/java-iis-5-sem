package ru.rsreu.kuznecovsokolov12.commands;

import javax.servlet.http.HttpServletRequest;

import ru.rsreu.kuznecovsokolov12.utils.ConfigurationManager;

public class LogoutCommand implements ActionCommand {

	@Override
	public String execute(HttpServletRequest request) {
		String page = ConfigurationManager.getProperty("path.page.index");
		request.getSession().invalidate();
		return page;
	}

}
