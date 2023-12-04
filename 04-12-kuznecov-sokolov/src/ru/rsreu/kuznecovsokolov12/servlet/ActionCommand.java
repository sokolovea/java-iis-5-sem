package ru.rsreu.kuznecovsokolov12.servlet;

import javax.servlet.http.HttpServletRequest;

public interface ActionCommand {
	
	public static final String PARAM_USER_LOGIN 		= "login";
	public static final String PARAM_USER_PASSWORD 		= "password";
	public static final String PARAM_USER_ROLE 			= "role";
	public static final String PARAM_DESTINATION		= "destination";
	public static final String PARAM_ACTIVITY 			= "activity";
	public static final String PARAM_TEAM_ID 			= "team_id";
	public static final String PARAM_MESSAGE 			= "message";
	public static final String PARAM_MESSAGE_ID 		= "messageId";
	
	public static final String URL_LOGIN_PAGE 				= "/login";
	public static final String URL_MAIN_PAGE 				= "/controller?command=menu&destination=main";
	public static final String URL_TEAM_PAGE 				= "/controller?command=menu&destination=team";
	public static final String URL_TEAM_PAGE_AND_TEAM_ID 	= "/controller?command=menu&destination=team&team_id=%s";
	public static final String URL_SETTINGS_PAGE		 	= "/controller?command=menu&destination=settings";
	
	
	String execute(HttpServletRequest request);
}
