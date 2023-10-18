<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ru-RU">
<head>
	<title>Выбор команды</title>
	<link rel="stylesheet" href="/03-12-kuznecov-sokolov/css/main_win.css">
	<link rel="stylesheet" href="/03-12-kuznecov-sokolov/css/input_items.css">
	<link rel="stylesheet" href="/03-12-kuznecov-sokolov/css/team_select.css">
</head>
<body>
		<div class="window"> 
			<div id="top_bar" class="container">
				<div class="top_bar_items">
					User name
				</div>
				<div id="logout_button" class="top_bar_items">
					<a id="logout_button_ref" href="controller?command=logout">Logout</a>
				</div>
			</div>
			<div id="main_content_bar">
				<div id="left_bar" class="container">
					<ol>
						<li id="button_report" class="menu_button">
							<a class="menu_button_ref" href="controller?login=${userName}&password=${userPassword}&command=Report">Отчеты</a>
						</li>
						<li id="button_to_team" class="menu_button">
							<a class="menu_button_ref" href="controller?login=${userName}&password=${userPassword}&destination=team&command=Menu">Вернуться в команду</a>
						</li>
					</ol>
				</div>
				<div id="center_bar" class="container">
					<div id="center_bar_top">
						<div id="create_team" class="center_bar_boxes"> 
							<div id="create_team_caption" class="header_name">
								Создание команды
							</div>
							<div id="create_team_input">
								<div id="create_team_text_box">
									<input class="text_box" type="text" placeholder="Название команды"/>
								</div>
								<div id="create_team_send">
									<input type="button" value="Создать"/>
								</div>
							</div>
						</div>
						<div class="center_bar_boxes" id="teams_info">
							<div>
								Сводная информация о командах
							</div>
						</div>
					</div>
					<div class="center_bar_boxes" id="center_bar_bottom">
						<div id="team_list">
							<div id="team_list_header" class="header_name">
								Список команд
							</div>
							<div id="teams">
								<div class="team_box">
									Команда 1
								</div>
								<div class="team_box">
									Команда 2
								</div>
								<div class="team_box">
									Команда 3
								</div>
								<div class="team_box">
									Команда 4
								</div>
								<div class="team_box">
									Команда 5
								</div>
								<div class="team_box">
									Команда 6
								</div>
								<div class="team_box">
									Команда 7
								</div>
								<div class="team_box">
									Команда 1
								</div>
								<div class="team_box">
									Команда 2
								</div>
								<div class="team_box">
									Команда 3
								</div>
								<div class="team_box">
									Команда 4
								</div>
								<div class="team_box">
									Команда 5
								</div>
								<div class="team_box">
									Команда 6
								</div>
								<div class="team_box">
									Команда 7
								</div>
							</div>
						</div>
					</div>		
				</div>
				<jsp:useBean id="myLogic" class="ru.rsreu.kuznecovsokolov12.servlet.LoginLogic" scope="page"></jsp:useBean>
				<div id="right_bar" class="container">
					<c:if test = "${myLogic.checkLogin(userName, userPassword).toString() != 'expert'}">
						Текущая команда пользователя:
					</c:if>
					<c:if test = "${myLogic.checkLogin(userName, userPassword).toString() == 'expert'}">
						Список команд, которые консультируются у эксперта:
					</c:if>
				</div>
			</div>
		</div>
</body>
</html>