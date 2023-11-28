package ru.rsreu.kuznecovsokolov12.servlet;

import java.io.IOException;
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

import ru.rsreu.kuznecovsokolov12.datalayer.DAOFactory;
import ru.rsreu.kuznecovsokolov12.datalayer.DBType;
import ru.rsreu.kuznecovsokolov12.datalayer.TeamDAO;
import ru.rsreu.kuznecovsokolov12.datalayer.TeamInteractDAO;
import ru.rsreu.kuznecovsokolov12.datalayer.UserDAO;
import ru.rsreu.kuznecovsokolov12.datalayer.data.User;

public class ServletAuthorizeFilter implements Filter {
	private String indexPath;

	public void init(FilterConfig fConfig) throws ServletException {
		indexPath = fConfig.getInitParameter("INDEX_PATH");
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		if (true) {
			chain.doFilter(request, response);
			return;
		}
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		
		String pathInfo = httpRequest.getPathInfo();
		System.out.print("PATHINFO = " + pathInfo);
        if (pathInfo == null || pathInfo.equals("/login")) {
            chain.doFilter(request, response);
            return;
        }
        
		HttpSession session = httpRequest.getSession(false);
		if (session == null) {
			System.out.println("SESSION == NULL!!!");
			RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/index.jsp");
			dispatcher.forward(request, response);
			return;
		}
		String login = (String) session.getAttribute(ActionCommand.PARAM_USER_LOGIN);
		String loginRequest = request.getParameter(LoginCommand.PARAM_USER_LOGIN);
		if (login == null && loginRequest == null) {
			System.out.println("LOGIN == NULL!");
			httpResponse.sendRedirect(httpRequest.getContextPath() + "/login");
			return;
		}
		DAOFactory factory = DAOFactory.getInstance(DBType.ORACLE);
		UserDAO userDAO = factory.getUserDAO();
		User user = null;
		try {
			user = userDAO.getUserByLogin(login);
			if (!(user.isIsAuthorized())) {
//				RequestDispatcher dispatcher = request.getRequestDispatcher("/login");

				// Forward к целевому ресурсу
//				dispatcher.forward(request, response);
				httpResponse.sendRedirect(httpRequest.getContextPath() + "/login");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		httpResponse.sendRedirect(httpRequest.getContextPath());
		chain.doFilter(request, response);
	}

	public void destroy() {
	}
}