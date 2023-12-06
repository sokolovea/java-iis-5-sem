package ru.rsreu.kuznecovsokolov12.servlet;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import ru.rsreu.kuznecovsokolov12.datalayer.DAOAcces;
import ru.rsreu.kuznecovsokolov12.datalayer.data.Message;
import ru.rsreu.kuznecovsokolov12.datalayer.data.Role;
import ru.rsreu.kuznecovsokolov12.datalayer.data.RoleAssigment;
import ru.rsreu.kuznecovsokolov12.datalayer.data.Sanction;
import ru.rsreu.kuznecovsokolov12.datalayer.data.Team;
import ru.rsreu.kuznecovsokolov12.datalayer.data.User;

public class ReportLogic extends DAOAcces {

	public static void addUserReports(HttpServletRequest request, String login) {
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
			int commandNumber = -1;
			List<Team> teamList = teamDAO.getTeamsForUser(user);
			if (teamList.size() != 0) {
				commandNumber = teamList.get(0).getId();
				request.setAttribute("teamList", teamList);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void addExpertReports(HttpServletRequest request, String login) {
		User expert = new User();
		try {
			expert = userDAO.getUserByLogin(login);
			List<Team> firstReportList = teamDAO.getTeamsConsultedByExpert(expert);
			request.setAttribute("expertReportFirst", firstReportList);
			
			if (request.getParameter("countCommands") != null && !request.getParameter("countCommands").isEmpty()) {
				int n = Integer.parseInt(request.getParameter("countCommands"));
				Map<Team, Integer> secondReportList = teamDAO.getNTeamsBestCooperatedExpert(expert, n);
				request.setAttribute("expertReportSecond", secondReportList);
			}

			List<Team> thirdReportList = teamDAO.getTeamsEjectedExpert(expert);
			request.setAttribute("expertReportThird", thirdReportList);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void addModeratorReports(HttpServletRequest request, String login) {
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
	}
	
	public static void addAdminReports(HttpServletRequest request) {
		Map<User, Role> adminReportFirst = new HashMap<>();
		List<RoleAssigment> adminReportSecond = new ArrayList<RoleAssigment>();
		User userForSecondReport = new User();
		try {
			adminReportFirst = userDAO.getUsersWithRole();
			String tempLogin = request.getParameter("adminUserRole");
			userForSecondReport = userDAO.getUserByLogin(tempLogin);
			adminReportSecond = roleAssigmentDAO.getRoleAssigmentsForUser(userForSecondReport);
		} catch (Exception e) {
			e.printStackTrace();
		}
		request.setAttribute("adminReportFirst", adminReportFirst);
		request.setAttribute("adminReportSecond", adminReportSecond);
	}

}
