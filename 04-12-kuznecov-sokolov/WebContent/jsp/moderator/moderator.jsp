<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="ru-RU">
<meta http-equiv="Cache-Control" content="no-cache">
<head>
	<title>Кабинет модератора</title>
	<link rel="stylesheet" href="/04-12-kuznecov-sokolov/css/main_win.css">
	<link rel="stylesheet" href="/04-12-kuznecov-sokolov/css/input_items.css">
	<link rel="stylesheet" href="/04-12-kuznecov-sokolov/css/moderator.css">
	<script>
		function showSanctionMenu(event, element, qweqwe) {
			const menu = document.getElementById("user_sanction");
			const item_pos = element.getBoundingClientRect();
			menu.style.opacity = 1;
			menu.style.pointerEvents = "auto";
			menu.style.top = document.body.scrollTop + item_pos.top - menu.offsetHeight / 2 + element.offsetHeight / 2;
			menu.style.left = item_pos.left - menu.offsetWidth + 10;
		}
		
		function closeSanctionMenu(event, element) {
			const menu = document.getElementById("sanc_triangle");
			const item_pos = element.getBoundingClientRect();
			if (event.relatedTarget != menu) {
				document.getElementById("user_sanction").style.opacity = 0;
				document.getElementById("user_sanction").style.pointerEvents = "none";
			}
		}
		
		function closeSancMenu() {
			const menu = document.getElementById("user_sanction");
			menu.style.opacity = 0;
			menu.style.pointerEvents = "none";
		}
		
		function chatFilter() {
			  // Получите значение введенного текста из поля фильтра
			  var input = document.getElementById("chat_filter_input");
			  var filter = input.value.toLowerCase();

			  // Получите все элементы с классом "team" из списка чатов
			  var teams = document.getElementsByClassName("team");

			  // Переберите все элементы и скройте те, которые не соответствуют фильтру
			  for (var i = 0; i < teams.length; i++) {
			    var teamName = teams[i].textContent.toLowerCase();
			    if (teamName.indexOf(filter) > -1) {
			      teams[i].style.display = "block"; // Отобразите элемент, если он соответствует фильтру
			    } else {
			      teams[i].style.display = "none"; // Спрячьте элемент, если он не соответствует фильтру
			    }
			  }
			}
	</script>
</head>
<body>
	<div id="user_sanction" onmouseleave='closeSancMenu()'>
		<div id="sanction_list">
			<div class="sanction">Block</div>
			<div class="sanction">Unblock</div>
		</div>
		<div id="sanc_triangle"></div>
	</div>
	
	<div class="window">
		<div id="top_bar" class="container">
			<div class="top_bar_items">
				${userName}
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
				<div id="chat" class="center_bar_boxes">
				
				
					<c:forEach var="message" items="${messageList}">
						<c:set var="messageIsDeleted" value="${deletedMessageSet.contains(message)}"></c:set>
						<c:if test="${!messageIsDeleted || messageIsDeleted && message.getMessage().getAuthor().getLogin().equals(userName)}">  
							<div class="message<c:if test="${messageIsDeleted}"> deleted_message</c:if>">
								<div class="message_header">
									<div class="user_box">
										<div class="message_user" onmouseenter='showSanctionMenu(event, this)' onmouseleave='closeSanctionMenu(event, this)'>${message.getMessage().getAuthor().getLogin()}</div>
									</div>
									<div class="time_and_team_box">
										<div class="message_team">${message.getTeam().getName()} -&nbsp</div>
										<div class="message_time">[${message.getMessage().getTime()}]</div>
									</div>
								</div>
								<div class="message_content">
									<div class="message_data">${message.getMessage().getData()}</div>
									<div class="message_buttons">
										<div class="restore_message_button">&#8635</div>
										<div class="delete_message_button">✖</div>
									</div>
								</div>
							</div>
						</c:if>
					</c:forEach>
					
	<!--				
					<div class="message"> 
						<div class="message_header">
							<div class="user_box">
								<div class="message_user" onmouseenter='showSanctionMenu(event, this)' onmouseleave='closeSanctionMenu(event, this)'>user1</div>
							</div>
							<div class="time_and_team_box">
								<div class="message_team">Команда 1 -&nbsp</div>
								<div class="message_time">[12:18:30]</div>
							</div>
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
							<div class="time_and_team_box">
								<div class="message_team">Команда 2 -&nbsp</div>
								<div class="message_time">[12:18:53]</div>
							</div>
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
							<div class="time_and_team_box">
								<div class="message_team">Команда 1 -&nbsp</div>
								<div class="message_time">[12:19:24]</div>
							</div>
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
							<div class="time_and_team_box">
								<div class="message_team">Команда 1 -&nbsp</div>
								<div class="message_time">[12:22:02]</div>
							</div>
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
							<div class="time_and_team_box">
								<div class="message_team">Команда 1 -&nbsp</div>
								<div class="message_time">[12:22:56]</div>
							</div>
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
							<div class="time_and_team_box">
								<div class="message_team">Команда 1 -&nbsp</div>
								<div class="message_time">[12:23:09]</div>
							</div>
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
							<div class="time_and_team_box">
								<div class="message_team">Команда 1 -&nbsp</div>
								<div class="message_time">[12:23:34]</div>
							</div>
						</div>
						<div class="message_content">
							<div class="message_data">Ну вот, да, я прав, зачем ещё эксперта отвлекать на такие простые вопросы.</div>
							<div class="message_buttons">
								<div class="restore_message_button">&#8635</div>
								<div class="delete_message_button">✖</div>
							</div>
						</div>
					</div>
					-->
				</div>
			</div>
			<div id="right_bar" class="container">
				<div id="right_bar_div">
					<div id="chat_type">
						<div class="right_bar_header">Чат</div>
						<div id="chats_filter">
							<input id="chat_filter_input" class="text_box" type="text" name="chatFilter" placeholder="Фильтр"  onkeyup="chatFilter()"/>
						</div>
						<div id="full_chats_list">
							<div id="general_chat" class="team">Общий чат</div>
							<div class="team">Команда 1</div>
							<div class="team">Команда 2</div>
							<div class="team">Команда 3</div>
							<div class="team">Команда 1</div>
							<div class="team">Команда 2</div>
							<div class="team">Команда 3</div>
							<div class="team">Команда 1</div>
							<div class="team">Команда 2</div>
							<div class="team">Команда 3</div>
						</div>
					</div>
					<div id="user_list">
						<c:import url="/jsp/general/user_list.jsp"/>
					</div>
				</div>
			</div>
		</div>
	</div>
</body></html>