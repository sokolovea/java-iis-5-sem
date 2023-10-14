<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<html lang="ru-RU">
<head>
	<title>Выбор команды</title>
	<link rel="stylesheet" href="/03-12-kuznecov-sokolov/css/main_win.css">
	<link rel="stylesheet" href="/03-12-kuznecov-sokolov/css/input_items.css">
</head>
<body>
		<div class="window"> 
			<div id="top_bar" class="container">
				<a href="controller?command=logout">Logout</a><br>
			</div>
			<div id="main_content_bar">
				<div id="left_bar" class="container">
					<ol>
						<li id="button_report" class="menu_button">
							<a href="team_select.jsp"><div>Отчеты</div></a>
						</li>
						<li id="button_to_team" class="menu_button">
							<a href="user_2.jsp"><div>Вернуться в команду</div></a>
						</li>
					</ol>
				</div>
				<div id="center_bar" class="container">
					<span>Создать новую команду</span><br/>
						<div id="create_team"> 
							<span>Введите название команды:</span> 
							<input id="text_box_team_name" class="text_box" type="form"/>
							<input type="button" value="Создать"/>
						</div>
						<div style="display:inline-block">
							Сводная информация
						</div>
						<div>
							Список команд
						</div>
				</div>
				<div id="right_bar" class="container">
					Список команд в которые вступил пользователь
				</div>
			</div>
		</div>
</body>
</html>