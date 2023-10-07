package ru.rsreu.packagename.projectname;

import javax.servlet.http.HttpServletRequest;

public class LogoutCommand implements ActionCommand {

	@Override
	public String execute(HttpServletRequest request) {
		String page = ConfigurationManager.getProperty("path.page.index");
		 request.getSession().invalidate();
		 return page;
	}

}
