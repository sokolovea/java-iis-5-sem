<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<link rel="stylesheet" href="/03-12-kuznecov-sokolov/css/user_list.css">
</head>
<body>
	<jsp:useBean id="myLogic" class="ru.rsreu.kuznecovsokolov12.servlet.LoginLogic" scope="page"></jsp:useBean>
	<c:set var="role" value="${myLogic.checkLogin(userName, userPassword).toString()}"></c:set>
	<ol>
		<c:if test="${role == 'user' or role == 'captain'}">
			<li id="button_to_team" class="menu_button">
				<a class="menu_button_ref" href="controller?login=${userName}&password=${userPassword}&destination=team&command=Menu">Моя команда</a>
			</li>
			<li id="button_to_team" class="menu_button">
				<a class="menu_button_ref" href="controller?login=${userName}&password=${userPassword}&destination=main&command=Menu">Команды</a>
			</li>
			<li id="button_exit_team" class="menu_button">
				<a class="menu_button_ref" href="controller?login=${userName}&password=${userPassword}&destination=main&command=Menu">Покинуть команду</a>
			</li>
			<li id="button_report" class="menu_button">
				<a class="menu_button_ref" href="controller?login=${userName}&password=${userPassword}&command=Report">Отчеты</a>
			</li>
		</c:if>
		<c:if test="${role == 'expert'}">
			<li id="button_to_team" class="menu_button">
				<a class="menu_button_ref" href="controller?login=${userName}&password=${userPassword}&destination=main&command=Menu">Команды</a>
			</li>
			<li id="button_report" class="menu_button">
				<a class="menu_button_ref" href="controller?login=${userName}&password=${userPassword}&command=Report">Отчеты</a>
			</li>
		</c:if>
		<c:if test="${role == 'moderator'}">
			<li id="button_main" class="menu_button">
				<a class="menu_button_ref" href="controller?login=${userName}&password=${userPassword}&destination=main&command=Menu">Главное меню[TODO]</a>
			</li>
			<li id="button_report" class="menu_button">
				<a class="menu_button_ref" href="controller?login=${userName}&password=${userPassword}&command=Report">Отчеты</a>
			</li>
		</c:if>
		<c:if test="${role == 'admin'}">
			<li id="button_report" class="menu_button">
				<a class="menu_button_ref" href="controller?login=${userName}&password=${userPassword}&command=Report">Отчеты</a>
			</li>
			<li id="button_main" class="menu_button">
				<a class="menu_button_ref" href="controller?login=${userName}&password=${userPassword}&destination=main&command=Menu">Главное меню</a>
			</li>
			<li id="button_settings" class="menu_button">
				<a class="menu_button_ref" href="controller?login=${userName}&password=${userPassword}&destination=settings&command=Menu">Настройки СИС</a>
			</li>
		</c:if>
	</ol>
</body>
</html>