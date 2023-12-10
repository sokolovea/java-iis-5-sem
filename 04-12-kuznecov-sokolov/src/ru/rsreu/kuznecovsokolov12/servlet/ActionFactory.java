package ru.rsreu.kuznecovsokolov12.servlet;

import javax.servlet.http.HttpServletRequest;

import ru.rsreu.kuznecovsokolov12.commands.ActionCommand;
import ru.rsreu.kuznecovsokolov12.commands.CommandEnum;
import ru.rsreu.kuznecovsokolov12.commands.EmptyCommand;
import ru.rsreu.kuznecovsokolov12.utils.MessageManager;

public class ActionFactory {
	public ActionCommand defineCommand(HttpServletRequest request) {
		ActionCommand current = new EmptyCommand();
		String action = request.getParameter("command");
		if (action == null || action.isEmpty()) {
			return current;
		}
		try {
			CommandEnum currentEnum = CommandEnum.valueOf(action.toUpperCase());
			current = currentEnum.getCurrentCommand();
		} catch (IllegalArgumentException e) {
			request.setAttribute("wrongAction", action + MessageManager.getProperty("message.wrongaction"));
		}
		return current;
	}
}