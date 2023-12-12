package ru.rsreu.kuznecovsokolov12.servlet;

import java.sql.SQLException;
import javax.servlet.http.HttpSessionBindingListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import ru.rsreu.kuznecovsokolov12.commands.DatabaseCommand;
import ru.rsreu.kuznecovsokolov12.commands.LoginLogic;

public class SessionListener implements HttpSessionListener, HttpSessionBindingListener {

	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		String login = (String) se.getSession().getAttribute(DatabaseCommand.PARAM_USER_LOGIN);
		if (login != null) {
			try {
				LoginLogic.setUserAuth(login, false);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}