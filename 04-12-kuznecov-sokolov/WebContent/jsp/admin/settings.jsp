<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="ru-RU">
<meta http-equiv="Cache-Control" content="no-cache">
<head>
	<title>Кабинет администратора</title>
	<link rel="stylesheet" href="/04-12-kuznecov-sokolov/css/main_win.css">
	<link rel="stylesheet" href="/04-12-kuznecov-sokolov/css/input_items.css">
	<link rel="stylesheet" href="/04-12-kuznecov-sokolov/css/settings.css">
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
				<c:import url="/jsp/general/left_bar_menu.jsp"/>
			</div>
			<div id="center_bar" class="container">
				<div id="settings_div">
					<div id="settings_box">
						<div class="setting">
							<div class="setting_caption">Максимум человек в команде</div>
							<input id="team_capacity" class="text_box" type="number" name="teamCapacity" value="${setting_list[0].getValue()}"/><br/>
						</div>
						<div class="setting">
							<div class="setting_caption">Максимум команд у эксперта</div>
							<input id="expert_capacity" class="text_box" type="number" name="expertCapacity" value="${setting_list[1].getValue()}"/><br/>
			        	</div>
					</div>
					<div id="settings_box_button">
						<input id="save_changes" type="button" name="saveChanges" value="Сохранить"/>
					</div>
				</div>
			</div>
			<div id="right_bar" class="container">
			</div>
		</div>
	</div>
</body></html>