package ru.rsreu.kuznecovsokolov12.servlet;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ru.rsreu.kuznecovsokolov12.commands.ActionCommand;
import ru.rsreu.kuznecovsokolov12.utils.ConfigurationManager;
import ru.rsreu.kuznecovsokolov12.utils.MessageManager;

public class FrontController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String page = processRequest(request, response);
		if (page != null) {
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(page);
			dispatcher.forward(request, response);
		} else {
			redirectToIndexPage(request, response);
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String page = processRequest(request, response);
		if (page != null) {
			response.sendRedirect(request.getContextPath() + page);
		} else {
			redirectToIndexPage(request, response);
		}
	}

	/***
	 * Processes Command and returns the page to be redirected 
	 * @param request HttpServletRequest object
	 * @param response HttpServletResponse object
	 * @return the page to be redirected 
	 * @throws ServletException
	 * @throws IOException
	 */
	private String processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String page = null;
		ActionFactory client = new ActionFactory();
		ActionCommand command = client.defineCommand(request);
		page = command.execute(request);
		return page;
	}
	
	/***
	 * Processes redirect to index page
	 * @param request HttpServletRequest object
	 * @param response HttpServletResponse object
	 * @throws IOException
	 */
	private void redirectToIndexPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String page = ConfigurationManager.getProperty("path.page.index");
		request.getSession().setAttribute("nullPage", MessageManager.getProperty("message.nullpage"));
		response.sendRedirect(request.getContextPath() + page);
	}
}
