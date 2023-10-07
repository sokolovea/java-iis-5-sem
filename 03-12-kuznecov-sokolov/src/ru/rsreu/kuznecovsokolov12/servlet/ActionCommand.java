package ru.rsreu.kuznecovsokolov12.servlet;

import javax.servlet.http.HttpServletRequest;

public interface ActionCommand {
	String execute(HttpServletRequest request);
}
