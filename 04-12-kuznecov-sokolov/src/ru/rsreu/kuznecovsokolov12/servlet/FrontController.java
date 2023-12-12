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

/**
 * The {@code FrontController} class serves as the controller for handling requests and responses.
 * It accepts requests from associated JSP pages, invokes the corresponding business logic for processing,
 * and, depending on the result, determines which JSP page to associate with the result.
 *
 * <p>This servlet class implements the doGet and doPost methods to handle HTTP GET and POST requests,
 * respectively. The doGet method forwards the request to the appropriate JSP page using a RequestDispatcher,
 * while the doPost method redirects to the JSP page based on the result of request processing.
 *
 * <p>The class includes methods for processing requests, defining commands, and redirecting to the index page.
 */
public class FrontController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * Handles GET requests, processes it, gets the page to be redirected, forwards
	 * the request to the appropriate JSP page.
	 * {@inheritDoc}
	 * 
	 * @param request HttpServletRequest object.
	 * @param response HttpServletResponse object.
	 * @throws ServletException If errors with servlet occurs.
	 * @throws IOException If an I/O error occurs.
	 */
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

    /**
     * Handles POST requests, processes it, gets the page to be redirected.
     * {@inheritDoc}
     * 
     * @param request HttpServletRequest object.
     * @param response HttpServletResponse object.
     * @throws ServletException If errors with servlet occurs.
     * @throws IOException If an I/O error occurs.
     */
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
	 * 
	 * @param request HttpServletRequest object
	 * @param response HttpServletResponse object
	 * @return the page to be redirected 
	 * @throws ServletException
	 * @throws IOException if the request for the could not be handled
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
	 * 
	 * @param request HttpServletRequest object
	 * @param response HttpServletResponse object
	 * @throws IOException if an input or output error isdetected when the servlet handlesthe request
	 */
	private void redirectToIndexPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String page = ConfigurationManager.getProperty("path.page.index");
		request.getSession().setAttribute("nullPage", MessageManager.getProperty("message.nullpage"));
		response.sendRedirect(request.getContextPath() + page);
	}
}
