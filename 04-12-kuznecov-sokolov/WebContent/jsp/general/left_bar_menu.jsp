<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<link rel="stylesheet" href="/04-12-kuznecov-sokolov/css/user_list.css">
</head>
<body>
	<jsp:useBean id="capitanChecker" class="ru.rsreu.kuznecovsokolov12.servlet.LoginLogic" scope="page"></jsp:useBean>
	<ol>
		<c:if test="${role.toString() == 'user'}">
			<c:if test="${team_id != null}">
				<li id="button_to_team" class="menu_button">
					<a class="menu_button_ref" href="controller?command=Menu&destination=team">Моя команда</a>
				</li>
			</c:if>
			<li id="button_to_team" class="menu_button">
				<a class="menu_button_ref" href="controller?destination=main&command=Menu">Команды</a>
			</li>
			<c:if test="${!capitanChecker.isCapitan(login, team_id)}">
				<li id="button_exit_team" class="menu_button">
					<a class="menu_button_ref" href="controller?destination=exit_team&command=Menu">Покинуть команду</a>
				</li>
			</c:if>
			<li id="button_report" class="menu_button">
				<a class="menu_button_ref" href="controller?command=Report">Отчеты</a>
			</li>
		</c:if>
		<c:if test="${role.toString() == 'expert'}">
			<li id="button_to_team" class="menu_button">
				<a class="menu_button_ref" href="controller?destination=main&command=Menu">Команды</a>
			</li>
			<li id="button_report" class="menu_button">
				<a class="menu_button_ref" href="controller?command=Report">Отчеты</a>
			</li>
		</c:if>
		<c:if test="${role.toString() == 'moderator'}">
			<li id="button_main" class="menu_button">
				<a class="menu_button_ref" href="controller?destination=main&command=Menu">Главное меню</a>
			</li>
			<li id="button_report" class="menu_button">
				<a class="menu_button_ref" href="controller?command=Report">Отчеты</a>
			</li>
		</c:if>
		<c:if test="${role.toString() == 'admin'}">
			<li id="button_main" class="menu_button">
				<a class="menu_button_ref" href="controller?destination=main&command=Menu">Главное меню</a>
			</li>
			<li id="button_settings" class="menu_button">
				<a class="menu_button_ref" href="controller?destination=settings&command=Menu">Настройки СИС</a>
			</li>
			<li id="button_report" class="menu_button">
				<a class="menu_button_ref" href="controller?command=Report">Отчеты</a>
			</li>
		</c:if>
	</ol>
</body>
</html>