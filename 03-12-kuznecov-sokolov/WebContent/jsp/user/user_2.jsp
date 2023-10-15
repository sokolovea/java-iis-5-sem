<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html lang="ru-RU">
<head>
	<title>Кабинет пользователя/капитана/эксперта</title>
	<link rel="stylesheet" href="/03-12-kuznecov-sokolov/css1/main_win.css">
	<link rel="stylesheet" href="/03-12-kuznecov-sokolov/css1/input_items.css">
	<link rel="stylesheet" href="/03-12-kuznecov-sokolov/css1/user_2.css">
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
						<a href="controller?login=${userName}&password=${userPassword}&command=Report"><div>Отчеты</div></a>
					</li>
					<li id="button_to_team" class="menu_button">
						<a href="controller?login=${userName}&password=${userPassword}&destination=main&command=Menu"><div>Выйти из команды</div></a>
					</li>
				</ol>
			</div>
			<div id="center_bar" class="container">
				<div id="chat" class="center_bar_boxes">
					<div>user1: Всем привет! [12:18:30]</div>
					<div>user2: Здорова всем!!!))) [12:18:53]</div>
					<div>captain: Коллеги, не отвлекаемся! Через минуту начинается игра [12:19:24]</div>
					<div>captain: @expert, сколько лет было Пушкину на момент дуэли с Дантесом? [12:22:02]</div>
				</div>
				<div id="message_input_box" class="center_bar_boxes">
					<span>Ваше сообщение:</span>
						<input id="text_box_message" class="text_box" type="form" name="message" size="100"></input>
					<input type="button" value="Отправить"/>
				</div>
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
</body></html>