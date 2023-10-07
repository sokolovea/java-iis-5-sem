package ru.rsreu.packagename.projectname;

import javax.servlet.http.HttpServletRequest;

public class LogoutCommand implements ActionCommand {

	@Override
	public String execute(HttpServletRequest request) {
		// TODO Auto-generated method stub
		String page = "/jsp/index.jsp";
		 // уничтожение сессии
		 request.getSession().invalidate();
		 return page;
	}

}
