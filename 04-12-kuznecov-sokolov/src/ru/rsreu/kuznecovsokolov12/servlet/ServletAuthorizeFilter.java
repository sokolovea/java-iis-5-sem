package ru.rsreu.kuznecovsokolov12.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ru.rsreu.kuznecovsokolov12.commands.ActionCommand;
import ru.rsreu.kuznecovsokolov12.commands.LoginLogic;

public class ServletAuthorizeFilter implements Filter {

	public void init(FilterConfig fConfig) throws ServletException {

	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		
		if (req.getServletPath().equals(ActionCommand.URL_LOGIN_PAGE)) {
			chain.doFilter(request, response);
			return;
		}
		
		HttpSession session = req.getSession();
		EnumLogin sessionRoleType = (EnumLogin) session.getAttribute(ActionCommand.PARAM_USER_ROLE);
		String login = (String) session.getAttribute(ActionCommand.PARAM_USER_LOGIN);
		String password = (String) session.getAttribute(ActionCommand.PARAM_USER_PASSWORD);
		EnumLogin checkLoginResult = EnumLogin.NOUSER;
		try {
			checkLoginResult = LoginLogic.checkLogin(login, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (sessionRoleType == null || checkLoginResult == EnumLogin.NOUSER || checkLoginResult != sessionRoleType) {
			sessionRoleType = EnumLogin.NOUSER;
			session.setAttribute(ActionCommand.PARAM_USER_ROLE, sessionRoleType);
			RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher(ActionCommand.URL_LOGIN_PAGE);
			dispatcher.forward(req, resp);
			return;
		}		
		chain.doFilter(request, response);
	}
}