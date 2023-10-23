<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="ru-RU">
<meta http-equiv="Cache-Control" content="no-cache">
<head>
	<title>Кабинет модератора</title>
	<link rel="stylesheet" href="/03-12-kuznecov-sokolov/css/main_win.css">
	<link rel="stylesheet" href="/03-12-kuznecov-sokolov/css/input_items.css">
	<link rel="stylesheet" href="/03-12-kuznecov-sokolov/css/moderator.css">
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
				</ol>
			</div>
			<div id="center_bar" class="container">
				<div id="chat" class="center_bar_boxes">
					<div class="message"> 
						<div class="message_header">
							<div class="message_user">user1</div>
							<div class="message_time">[12:18:30]</div>
						</div>
						<div class="message_data">Всем привет!</div> 
					</div>
					<div class="message"> 
						<div class="message_header">
							<div class="message_user">user2</div>
							<div class="message_time">[12:18:53]</div>
						</div>
						<div class="message_data">Здорова всем!!!)))</div> 
					</div>
					<div class="message"> 
						<div class="message_header">
							<div class="message_user">captain</div>
							<div class="message_time">[12:19:24]</div>
						</div>
						<div class="message_data">Коллеги, не отвлекаемся! Через минуту начинается игра</div> 
					</div>
					<div class="message"> 
						<div class="message_header">
							<div class="message_user">captain</div>
							<div class="message_time">[12:22:02]</div>
						</div>
						<div class="message_data">@expert, сколько лет было Пушкину на момент дуэли с Дантесом?</div> 
					</div>
				</div>
			</div>
			<div id="right_bar" class="container">
				<div id="chat_type">
					<div class="right_bar_header">Чат</div>
					<div>Общий чат</div>
					<div>Команда 1</div>
					<div>Команда 2</div>
					<div>Команда 3</div>
				</div>
				<div id="user_list">
					<div class="right_bar_header">Пользователи</div>
					<div>User1</div>
					<div>User2</div>
					<div>User3</div>
				</div>
			</div>
		</div>
	</div>
</body></html>