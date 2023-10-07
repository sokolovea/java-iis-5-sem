package ru.rsreu.packagename.projectname;

import javax.servlet.http.HttpServletRequest;

public class LogoutCommand implements ActionCommand {

	@Override
	public String execute(HttpServletRequest request) {
		// TODO Auto-generated method stub
		String page = ConfigurationManager.getProperty("path.page.index");
		 // уничтожение сессии
		 request.getSession().invalidate();
		 return page;
	}

}
