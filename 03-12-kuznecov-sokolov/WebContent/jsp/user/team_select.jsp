<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
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
					<a id="logout_button_ref" href="controller?command=logout">Logout</a><br>
				</div>
			</div>
			<div id="main_content_bar">
				<div id="left_bar" class="container">
					<ol>
						<li id="button_report" class="menu_button">
							<a href="controller?login=${userName}&password=${userPassword}&command=Report"><div>Отчеты</div></a>
						</li>
						<li id="button_to_team" class="menu_button">
							<a href="controller?login=${userName}&password=${userPassword}&destination=team&command=Menu"><div>Вернуться в команду</div></a>
						</li>
					</ol>
				</div>
				<div id="center_bar" class="container">
					<div id="center_bar_top">
						<div class="center_bar_boxes" id="create_team"> 
							<div>
								<span class="header_name">Создать новую команду</span>
							</div>
							<div>
								<span>Введите название команды:</span> 
								<input id="text_box_team_name" class="text_box" type="form"/>
								<input type="button" value="Создать"/>
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