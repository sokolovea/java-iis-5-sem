package ru.rsreu.kuznecovsokolov12.commands;

import javax.servlet.http.HttpServletRequest;

public interface ActionCommand {
	
	public static final String PARAM_USER_LOGIN 							= "login";
	public static final String PARAM_USER_PASSWORD 							= "password";
	public static final String PARAM_USER_ROLE 								= "role";
	public static final String PARAM_DESTINATION							= "destination";
	public static final String PARAM_ACTIVITY 								= "activity";
	public static final String PARAM_TEAM_ID 								= "team_id";
	public static final String PARAM_MESSAGE 								= "message";
	public static final String PARAM_MESSAGE_ID 							= "messageId";
	public static final String PARAM_USER_ID 								= "user_id";
	public static final String PARAM_SANCTION 								= "sanction";
	public static final String PARAM_REASON 								= "reason";
	
	public static final String URL_LOGIN_PAGE 								= "/login";
	public static final String URL_MAIN_PAGE 								= "/controller?command=menu&destination=main";
	public static final String URL_TEAM_PAGE 								= "/controller?command=menu&destination=team";
	public static final String URL_TEAM_PAGE_AND_TEAM_ID 					= "/controller?command=menu&destination=team&team_id=%s";
	public static final String URL_SETTINGS_PAGE		 					= "/controller?command=menu&destination=settings";
	
	public static final String TEAM_INTERACT_TYPE_JOIN		 				= "Join";
	public static final String TEAM_INTERACT_TYPE_EXIT		 				= "Exit";
	public static final String TEAM_INTERACT_TYPE_EXPERT_EJECTED		 	= "Expert_ejected";
	
	public static final String DESTINATION_MAIN		 						= "main";
	public static final String DESTINATION_TEAM							 	= "team";
	public static final String DESTINATION_EXIT_TEAM					 	= "exit_team";
	public static final String DESTINATION_SETTINGS					 		= "settings";
	
	public static final String ACTIVITY_SEND_MESSAGE				 		= "send_message";
	public static final String ACTIVITY_DELETE_MESSAGE				 		= "delete_message";
	public static final String ACTIVITY_RESTORE_MESSAGE				 		= "restore_message";
	public static final String ACTIVITY_CREATE_TEAM				 			= "create_team";
	public static final String ACTIVITY_JOIN_TEAM				 			= "join_team";
	public static final String ACTIVITY_POP_EXPERT				 			= "captain_pop_expert";
	public static final String ACTIVITY_EXPERT_EXIT_TEAM					= "expert_exit_team";
	public static final String ACTIVITY_ADD_SANCTION				 		= "add_sanction";
	public static final String ACTIVITY_UPDATE_SETTING				 		= "update_setting";
	public static final String ACTIVITY_UPDATE_USER				 			= "update_user";
	public static final String ACTIVITY_SAVE_USER				 			= "save_user";
	public static final String ACTIVITY_CREATE_USER				 			= "create_user";
	public static final String ACTIVITY_REMOVE_USER				 			= "remove_user";
	public static final String ACTIVITY_FIND_USER				 			= "find_user";
	
	public static final String SETTING_MAX_TEAM_COUNT				 		= "max_team_capacity";
	public static final String SETTING_MAX_TEAMS_FOR_EXPERT				 	= "max_team_consulted_expert";
	
	public static final String ROLE_USER				 					= "Common user";
	public static final String ROLE_EXPERT				 					= "Expert";
	public static final String ROLE_MODER				 					= "Moderator";
	public static final String ROLE_ADMIN				 					= "Administrator";
	
	public static final String SANCTION_TYPE_BLOCK		 					= "Block";
	public static final String SANCTION_TYPE_UNBLOCK		 				= "Unblock";
	
	
	String execute(HttpServletRequest request);
}
