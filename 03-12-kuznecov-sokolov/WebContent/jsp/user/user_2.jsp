<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html lang="ru-RU">
<head>
	<title>Кабинет пользователя/капитана/эксперта</title>
	<link rel="stylesheet" href="/03-12-kuznecov-sokolov/css/main_win.css">
	<link rel="stylesheet" href="/03-12-kuznecov-sokolov/css/input_items.css">
</head>
<body>
	<div class="window">
		<div id="top_bar" class="container">
			<div id="logout_button">
				<a href="controller?command=login">Logout</a><br>
			</div>
		</div>
		<div id="main_content_bar">
			<div id="left_bar" class="container">
				<ol>
					<li id="button_report" class="menu_button">
						<a href="user_2.jsp"><div>Отчеты</div></a>
					</li>
					<li id="button_to_team" class="menu_button">
						<a href="team_select.jsp"><div>Выйти из команды</div></a>
					</li>
					<li id="button_to_team" class="menu_button">
						<a href="user_2.jsp"><div>Меню выбора эксперта капитаном</div></a>
					</li>
					<li id="button_to_team" class="menu_button">
						<a href="user_2.jsp"><div>Отказаться от консультирования (капитан)</div></a>
					</li>
				</ol>
			</div>
			<div id="center_bar" class="container">
				<div class='chat'>
					<div>user1: Всем привет! [12:18:30]</div>
					<div>user2: Здорова всем!!!))) [12:18:53]</div>
					<div>captain: Коллеги, не отвлекаемся! Через минуту начинается игра [12:19:24]</div>
					<div>captain: @expert, сколько лет было Пушкину на момент дуэли с Дантесом? [12:22:02]</div>
					
				</div>
				<p>Ваше сообщение:
					<input id="text_box_message" class="text_box" type="form" name="message" size="100"></input>
				</p>
				<input type="button" value="Отправить"/>
			</div>
			<div id="right_bar" class="container">
				<div class='user_list'>
					<div id="team_list">Список пользователей команды (возможно, лучше сделать в таблице)</div>
				</div>
				<div class='expert_block'>
					Ваш эксперт: ...
					<br>
					
				</div>	
			</div>
		</div>
	</div>
	
	<style>
  
	.chat {
		border-bottom: 2px solid black;
		height: 600px;
	}
  
	.user_list {
		height: 300px;
		border-bottom: 2px solid black;
		margin-bottom: 40px;
	}
	</style>
</body></html>