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

import ru.rsreu.kuznecovsokolov12.commands.ActionCommand;

public class ServletAuthorizeFilter implements Filter {

	public void init(FilterConfig fConfig) throws ServletException {

	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		
		System.out.println(req.getServletPath() + " ; " + req.getRequestURI());
	    if (req.getServletPath().equals("/login")) {
	        chain.doFilter(request, response);
	        return;
	    }
	    
		HttpSession session = req.getSession();
		EnumLogin type = (EnumLogin) session.getAttribute(ActionCommand.PARAM_USER_ROLE);
		System.out.println("Сейчас мы в фильтре на NOUSER");
		if (type == null) {
			System.out.println("Фильтр на NOUSER сработал");
			type = EnumLogin.NOUSER;
			session.setAttribute(ActionCommand.PARAM_USER_ROLE, type);
			RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/login");
			dispatcher.forward(req, resp);
			return;
		}
		chain.doFilter(request, response);
	}
}