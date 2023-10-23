<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="ru-RU">
<meta http-equiv="Cache-Control" content="no-cache">
<head>
	<title>Кабинет модератора</title>
	<link rel="stylesheet" href="/03-12-kuznecov-sokolov/css/main_win.css">
	<link rel="stylesheet" href="/03-12-kuznecov-sokolov/css/input_items.css">
	<link rel="stylesheet" href="/03-12-kuznecov-sokolov/css/admin.css">
</head>
<body>
	<div class="window">
		<div id="top_bar" class="container">
			<div class="top_bar_items">
				User name
			</div>
			<div id="logout_button" class="top_bar_items">
				<a id="logout_button_ref" href="controller?command=logout">Logout</a><br>
			</div>
		</div>
		<div id="main_content_bar">
			<div id="left_bar" class="container">
				<ol>
					<li id="button_report" class="menu_button">
						<a class="menu_button_ref" href="controller?login=${userName}&password=${userPassword}&command=Report">Отчеты</a>
					</li>
					<li id="button_main" class="menu_button">
						<a class="menu_button_ref" href="controller?login=${userName}&password=${userPassword}&destination=main&command=Menu">Главное меню</a>
					</li>
				</ol>
			</div>
			<div id="center_bar" class="container">
				<div>
					<input id="team_capacity" class="text_box" type="number" name="teamCapacity" value="10"/><br/>
					<input id="expert_capacity" class="text_box" type="number" name="expertCapacity" value="5"/><br/>
		        	<input id="save_changes" type="button" name="saveChanges" value="Сохранить"/>
		        </div>
			</div>
			<div id="right_bar" class="container">
				Список всех пользователей СИС
				<div>User1</div>
				<div>User2</div>
				<div>User3</div>
			</div>
		</div>
	</div>
</body></html>