package ru.rsreu.kuznecovsokolov12.servlet;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import ru.rsreu.kuznecovsokolov12.datalayer.DAOFactory;
import ru.rsreu.kuznecovsokolov12.datalayer.DBType;
import ru.rsreu.kuznecovsokolov12.datalayer.MessageDAO;
import ru.rsreu.kuznecovsokolov12.datalayer.RoleAssigmentDAO;
import ru.rsreu.kuznecovsokolov12.datalayer.SanctionDAO;
import ru.rsreu.kuznecovsokolov12.datalayer.TeamDAO;
import ru.rsreu.kuznecovsokolov12.datalayer.UserDAO;
import ru.rsreu.kuznecovsokolov12.datalayer.data.Message;
import ru.rsreu.kuznecovsokolov12.datalayer.data.RoleAssigment;
import ru.rsreu.kuznecovsokolov12.datalayer.data.Sanction;
import ru.rsreu.kuznecovsokolov12.datalayer.data.Team;
import ru.rsreu.kuznecovsokolov12.datalayer.data.User;

public class ReportCommand implements ActionCommand {
	private static final String PARAM_NAME_LOGIN = "login";
	private static final String PARAM_NAME_PASSWORD = "password";

	@Override
	public String execute(HttpServletRequest request) {
		String login = request.getParameter(PARAM_NAME_LOGIN);
		request.getSession().setAttribute("userName", login);
		String password = request.getParameter(PARAM_NAME_PASSWORD);
		request.getSession().setAttribute("userPassword", password);
		EnumLogin loginResult = null;
		try {
			loginResult = LoginLogic.checkLogin(login, password);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		request.setAttribute(ReportCommand.getRequestAttribute(loginResult), login);
		DAOFactory factory = DAOFactory.getInstance(DBType.ORACLE);
		UserDAO userDAO = factory.getUserDAO();
		RoleAssigmentDAO roleAssigmentDAO = factory.getRoleAssigmentDAO();
		MessageDAO messageDAO = factory.getMessageDAO();
		SanctionDAO sanctionDAO = factory.getSanctionDAO();
		TeamDAO teamDAO = factory.getTeamDAO();
		if (loginResult == EnumLogin.ADMIN) {
			List<User> adminReportFirst = new ArrayList<User>();
			List<RoleAssigment> adminReportSecond = new ArrayList<RoleAssigment>();
			User userForSecondReport = new User();
			try {
				adminReportFirst = userDAO.getUsers();
				String tempLogin = request.getParameter("adminUserRole");
				userForSecondReport = userDAO.getUserByLogin(tempLogin);
				adminReportSecond = roleAssigmentDAO.getRoleAssigmentsForUser(userForSecondReport);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				// TODO Auto-generated catch block
				factory.returnConnectionToPool();
			}
			request.setAttribute("adminReportFirst", adminReportFirst);
			request.setAttribute("adminReportSecond", adminReportSecond);
			request.setAttribute("element", "role_assigment_history_button");
			request.setAttribute("report_id", "report_admin_user_role_history");
			factory.returnConnectionToPool();
		} else if (loginResult == EnumLogin.USER) {
			User user = new User();
			try {
				user = userDAO.getUserByLogin(login);
				List<Message> messageList = messageDAO.getMessagesDeletedByNoSelfUser(user);
				request.setAttribute("userReportFirst", messageList);
				List<Sanction> sanctionList = sanctionDAO.getUserSanctions(user);
				request.setAttribute("userReportSecond", sanctionList);
				int countSendedMessages = messageDAO.getCountMessagesSendedByUser(user);
				int countDeletedMessages = messageDAO.getCountDeletedMessagesSendedByUser(user);
				request.setAttribute("countSendedMessages", countSendedMessages);
				request.setAttribute("countDeletedMessages", countDeletedMessages);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			request.setAttribute("element", "deleted_message_button");
			request.setAttribute("report_id", "report_messages_deleted_by_no_user");
			factory.returnConnectionToPool();
		} else if (loginResult == EnumLogin.EXPERT) {
			User expert = new User();
			try {
				expert = userDAO.getUserByLogin(login);
				List<Team> firstReportList = teamDAO.getTeamsConsultedByExpert(expert);
				request.setAttribute("expertReportFirst", firstReportList);
				
				
				if (request.getParameter("countCommands") != null) {
					int n = Integer.parseInt(request.getParameter("countCommands"));
					Map<Team, Integer> secondReportList = teamDAO.getNTeamsBestCooperatedExpert(expert, n);
					request.setAttribute("expertReportSecond", secondReportList);
				}
				List<Team> thirdReportList = teamDAO.getTeamsEjectedExpert(expert);
				request.setAttribute("expertReportThird", thirdReportList);
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			request.setAttribute("element", "usefull_cooperate_button");
			request.setAttribute("report_id", "report_more_messages");
			factory.returnConnectionToPool();
		} else if (loginResult == EnumLogin.MODERATOR) {
			User moderator = new User();
			try {
				moderator = userDAO.getUserByLogin(login);
				List<User> firstReportList = userDAO.getUnprivilegedUsers();
				request.setAttribute("moderatorReportFirst", firstReportList);
				
				List<Sanction> secondReportList = sanctionDAO.getSanctionsByUser(moderator);
				request.setAttribute("moderatorReportSecond", secondReportList);
				
				
				if (request.getParameter("countBlocked") != null) {
					int n = Integer.parseInt(request.getParameter("countBlocked"));
					List<User> thirdReportList = userDAO.getBlockedUsersMoreNTimes(n);
					request.setAttribute("moderatorReportThird", thirdReportList);
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			request.setAttribute("element", "users_blocked_more_N_times_button");
			request.setAttribute("report_id", "report_moderator_blocked_n");
			factory.returnConnectionToPool();
		}
		return ReportCommand.getPage(loginResult);
	}

	private static String getRequestAttribute(EnumLogin loginResult) {
		if (loginResult != EnumLogin.NOUSER) {
			return loginResult.toString();
		}
		return "errorLoginPassMessage";
	}

	private static String getPage(EnumLogin loginResult) {
		if (loginResult != EnumLogin.NOUSER) {
			return ConfigurationManager.getProperty("path.page.reports");
		}
		return ConfigurationManager.getProperty("path.page.login");
	}

}
