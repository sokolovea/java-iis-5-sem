<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="ru-RU">
<meta http-equiv="Cache-Control" content="no-cache">
<head>
	<title>Кабинет пользователя/капитана/эксперта</title>
	<link rel="stylesheet" href="/04-12-kuznecov-sokolov/css/main_win.css">
	<link rel="stylesheet" href="/04-12-kuznecov-sokolov/css/input_items.css">
	<link rel="stylesheet" href="/04-12-kuznecov-sokolov/css/user.css">
</head>
<body>
	<div class="window">
		<div id="top_bar" class="container">
			<c:import url="/jsp/general/top_bar_content.jsp"/>
		</div>
		<div id="main_content_bar">
			<div id="left_bar" class="container">
				<c:import url="/jsp/general/left_bar_menu.jsp"/>
			</div>
			<div id="center_bar" class="container">
				<div id="chat" class="center_bar_boxes">
									<div class="message"> 
						<div class="message_header">
							<div class="user_box">
								<div class="message_user" onmouseenter='showSanctionMenu(event, this)' onmouseleave='closeSanctionMenu(event, this)'>user1</div>
							</div>
							<!-- <div class="time_and_team_box">
								<div class="message_team">Команда 1 -&nbsp</div> -->
								<div class="message_time">[12:18:30]</div>
							<!-- </div> -->
						</div>
						<div class="message_content">
							<div class="message_data">Всем привет!</div> 
							<div class="message_buttons">
								<div class="restore_message_button">&#8635</div>
								<div class="delete_message_button">✖</div>
							</div>
						</div>
					</div>
					<div class="message deleted_message"> 
						<div class="message_header">
							<div class="user_box">
								<div class="message_user" onmouseenter='showSanctionMenu(event, this)' onmouseleave='closeSanctionMenu(event, this)'>user2</div>
							</div>
							<!-- <div class="time_and_team_box">
								<div class="message_team">Команда 2 -&nbsp</div> -->
								<div class="message_time">[12:18:53]</div>
							<!-- </div> -->
						</div>
						<div class="message_content">
							<div class="message_data">Здорова всем!!!)))</div> 
							<div class="message_buttons">
								<div class="restore_message_button">&#8635</div>
								<div class="delete_message_button">✖</div>
							</div>
						</div>
					</div>
					<div class="message"> 
						<div class="message_header">
							<div class="user_box">
								<div class="message_user banned_user" onmouseenter='showSanctionMenu(event, this)' onmouseleave='closeSanctionMenu(event, this)'>captain</div>
							</div>
							<!-- <div class="time_and_team_box">
								<div class="message_team">Команда 1 -&nbsp</div> -->
								<div class="message_time">[12:19:24]</div>
							<!-- </div> -->
						</div>
						<div class="message_content">
							<div class="message_data">Коллеги, не отвлекаемся! Через минуту начинается игра</div> 
							<div class="message_buttons">
								<div class="restore_message_button">&#8635</div>
								<div class="delete_message_button">✖</div>
							</div>
						</div>
					</div>
					<div class="message"> 
						<div class="message_header">
							<div class="user_box">
								<div class="message_user" onmouseenter='showSanctionMenu(event, this)' onmouseleave='closeSanctionMenu(event, this)'>captain</div>
							</div>
							<!-- <div class="time_and_team_box">
								<div class="message_team">Команда 1 -&nbsp</div> -->
								<div class="message_time">[12:22:02]</div>
							<!-- </div> -->
						</div>
						<div class="message_content">
							<div class="message_data">@expert, сколько лет было Пушкину на момент дуэли с Дантесом?</div>
							<div class="message_buttons">
								<div class="restore_message_button">&#8635</div>
								<div class="delete_message_button">✖</div>
							</div>
						</div>
					</div>
					<div class="message"> 
						<div class="message_header">
							<div class="user_box">
								<div class="message_user" onmouseenter='showSanctionMenu(event, this)' onmouseleave='closeSanctionMenu(event, this)'>user2</div>
							</div>
							<!-- <div class="time_and_team_box">
								<div class="message_team">Команда 1 -&nbsp</div> -->
								<div class="message_time">[12:22:56]</div>
							<!-- </div> -->
						</div>
						<div class="message_content">
							<div class="message_data">Очевидно, что 37 лет, точная информация.</div>
							<div class="message_buttons">
								<div class="restore_message_button">&#8635</div>
								<div class="delete_message_button">✖</div>
							</div>
						</div>
					</div>
					<div class="message"> 
						<div class="message_header">
							<div class="user_box">
								<div class="message_user" onmouseenter='showSanctionMenu(event, this)' onmouseleave='closeSanctionMenu(event, this)'>user1</div>
							</div>
							<!-- <div class="time_and_team_box">
								<div class="message_team">Команда 1 -&nbsp</div> -->
								<div class="message_time">[12:23:09]</div>
							<!-- </div> -->
						</div>
						<div class="message_content">
							<div class="message_data">Подтверждаю, вспомнил.</div>
							<div class="message_buttons">
								<div class="restore_message_button">&#8635</div>
								<div class="delete_message_button">✖</div>
							</div>
						</div>
					</div>
					<div class="message"> 
						<div class="message_header">
							<div class="user_box">
								<div class="message_user" onmouseenter='showSanctionMenu(event, this)' onmouseleave='closeSanctionMenu(event, this)'>user2</div>
							</div>
							<!-- <div class="time_and_team_box">
								<div class="message_team">Команда 1 -&nbsp</div> -->
								<div class="message_time">[12:23:34]</div>
							<!-- </div> -->
						</div>
						<div class="message_content">
							<div class="message_data">Ну вот, да, я прав, зачем ещё эксперта отвлекать на такие простые вопросы.</div>
							<div class="message_buttons">
								<div class="restore_message_button">&#8635</div>
								<div class="delete_message_button">✖</div>
							</div>
						</div>
					</div>
				</div>
				<div id="message_input_box" class="center_bar_boxes">
					<div id="message_input_box_text_box">	
						<input id="text_box_message" class="text_box" type="text" name="message" placeholder="Введите сообщение"></input>
					</div>
					<div id="message_input_box_send">
						<input type="button" value="Отправить"/>
					</div>
				</div>
			</div>
			<div id="right_bar" class="container">
				<div class='team_list'>
					<div id="team_name" class="right_bar_header">Команда 1</div>
					<div id="team_list_caption" class="right_bar_header">Члены команды</div>
					<div id="member_list">
						<div id="team_capitan" class="team_member">
							captain
						</div>
						<div class="team_member">
							user1
						</div>
						<div class="team_member">
							user2
						</div>
					</div>
				</div>
				<jsp:useBean id="myLogic" class="ru.rsreu.kuznecovsokolov12.servlet.LoginLogic" scope="page"></jsp:useBean>
				<div id="expert_block">
					<c:if test = "${myLogic.checkLogin(userName, userPassword).toString() != 'expert'}">
						<div id="expert_block_caption">
							Эксперт: expert12
						</div>
					</c:if>
					<div id="expert_block_buttons">
						<c:if test = "${myLogic.checkLogin(userName, userPassword).toString() == 'captain'}">
							<input type="button" value="Отказаться от эксперта"/>
						</c:if>
						<c:if test = "${myLogic.checkLogin(userName, userPassword).toString() == 'expert'}">
							<input type="button" value="Отказаться от команды" href="controller?login=${userName}&password=${userPassword}&destination=team&command=Menu"/>
						</c:if>
					</div>	
				</div>
			</div>
		</div>
	</div>
</body></html>