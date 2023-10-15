<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<html lang="ru-RU">
<head>
	<title>Выбор команды</title>
	<link rel="stylesheet" href="/03-12-kuznecov-sokolov/css1/main_win.css">
	<link rel="stylesheet" href="/03-12-kuznecov-sokolov/css1/input_items.css">
</head>
<body>
		<div class="window"> 
			<div id="top_bar" class="container">
				<div class="top_bar_items">
					User name
				</div>
				<div id="logout_button" class="top_bar_items">
					<a id="logout_button_ref" href="controller?command=login">Logout</a><br>
				</div>
			</div>
			<div id="main_content_bar">
				<div id="left_bar" class="container">
					<ol>
						<li id="button_report" class="menu_button">
							<a href="controller?login=${userName}&role=${userRole}&command=Report"><div>Отчеты</div></a>
						</li>
						<li id="button_to_team" class="menu_button">
							<a href="team_select.jsp"><div>Вернуться в команду</div></a>
						</li>
					</ol>
				</div>
				<div id="center_bar" class="container">
					<div id="center_bar_top">
						<div class="center_bar_boxes" id="create_team"> 
							<span class="header_name">Создать новую команду</span><br/>
							<span>Введите название команды:</span> 
							<input id="text_box_team_name" class="text_box" type="form"/>
							<input type="button" value="Создать"/>
						</div>
						<div class="center_bar_boxes" id="teams_info">
							Сводная информация о командах
						</div>
					</div>
					<div class="center_bar_boxes" id="center_bar_bottom">
						<div id="team_list">
							Список команд
						</div>
					</div>		
				</div>
				<div id="right_bar" class="container">
					Список команд в которые вступил пользователь
				</div>
			</div>
		</div>
</body>
</html>