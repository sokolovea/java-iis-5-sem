package ru.rsreu.kuznecovsokolov12.servlet;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ServletSecurityFilter implements Filter {
	private String indexPath;

	public void init(FilterConfig fConfig) throws ServletException {
		indexPath = fConfig.getInitParameter("INDEX_PATH");
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		HttpSession session = httpRequest.getSession(false);
		if (session == null) {
			System.out.println("SESSION = NULL!!!");
			httpResponse.sendRedirect(httpRequest.getContextPath() + indexPath);
			return;
		}
		EnumLogin role = (EnumLogin) session.getAttribute(ActionCommand.PARAM_USER_ROLE);
		if ((role != null) && (role != EnumLogin.NOUSER)) {
			System.out.println("SESSION EXISTS!");
			httpResponse.sendRedirect(httpRequest.getContextPath() + indexPath + "?command=Menu&destination=main");
			return;
		}
		System.out.println("ROLE == NULL OR ROLE == NOUSER");
		httpResponse.sendRedirect(httpRequest.getContextPath() + indexPath);
		chain.doFilter(request, response);
	}

	public void destroy() {
	}
}