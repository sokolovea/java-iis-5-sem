package ru.rsreu.kuznecovsokolov12.servlet;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class SessionListener implements HttpSessionListener, HttpSessionBindingListener {

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        System.out.println("Сессия создана. ID сессии: " + se.getSession().getId());
//		String login = (String) se.getSession().getAttribute(DatabaseCommand.PARAM_USER_LOGIN);
//		String password = (String) se.getSession().getAttribute(DatabaseCommand.PARAM_USER_PASSWORD);
//		System.out.println("DEBUG: login = " + login);
        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
//        scheduler.schedule(() -> {
//        	// Ваш код для обработки уничтожения сессии
//        	String login = (String) se.getSession().getAttribute(DatabaseCommand.PARAM_USER_LOGIN);
//        	System.out.println("DEBUG: Sheduler login = " + login);
//        	}, 1, TimeUnit.SECONDS);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
    	String login = (String) se.getSession().getAttribute(DatabaseCommand.PARAM_USER_LOGIN);
		System.out.println("DEBUG DESTROY!!!: login = " + login);
        System.out.println("Сессия уничтожена. ID сессии: " + se.getSession().getId());
    }

    @Override
    public void valueUnbound(HttpSessionBindingEvent event) {
        System.out.println("Объект удален из сессии. ПРИВЯЗКА ПИШЕТ. Истек срок жизни сессии. ID сессии: " + event.getSession().getId());
    }

    @Override
    public void valueBound(HttpSessionBindingEvent event) {
    	System.out.println("Сессия создана. ПРИВЯЗКА ПИШЕТ. ID сессии: " + event.getSession().getId());
    		
    }
}