<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="ru-RU">
<meta http-equiv="Cache-Control" content="no-cache">
<head>
	<title>Кабинет модератора</title>
	<link rel="stylesheet" href="/04-12-kuznecov-sokolov/css/main_win.css">
	<link rel="stylesheet" href="/04-12-kuznecov-sokolov/css/input_items.css">
	<link rel="stylesheet" href="/04-12-kuznecov-sokolov/css/moderator.css">
	
	<script src="/04-12-kuznecov-sokolov/js/chat_utils.js"></script>
	<script src="/04-12-kuznecov-sokolov/js/moderator.js"></script>
</head>
<body>
	<div id="user_sanction" onmouseleave='closeSancMenu()'>
		<div id="sanction_list">
			<!-- <div class="sanction" onclick='sendSanctionForUser("Block")'>Block</div>
			<div class="sanction" onclick='sendSanctionForUser("Unblock")'>Unblock</div> -->
			<div class="sanction" onclick='openDialog("Block")'>Block</div>
			<input id="user_id_for_sanction" type="hidden" value="" >
			<div class="sanction" onclick='openDialog("Unblock")'>Unblock</div>
		</div>
		<div id="sanc_triangle"></div>
	</div>
	 
	<dialog id="sanctionReasonDialog" onkeydown="inputKeyPressHandler(event)">
		<div id="reasonDialogHeader" class="right_bar_header">Блокировка</div>
		<input type="text" class="text_box" id="reasonValue" placeholder="Причина" />
	    <div id="dialogButtons">
	        <input type="button" onclick="sendSanctionRequest()" id="ok" value="Ок"/>
	    	<input type="button" onclick="closeDialog()" id="cancel" value="Отмена"/>
	    </div>
	</dialog>
	
	<div class="window">
		<div id="top_bar" class="container">
			<div class="top_bar_items">
				${login}
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
						<div class="message<c:if test="${messageIsDeleted}"> deleted_message</c:if>">
							<div class="message_header">
								<div class="user_box">
									<div class="message_user" onmouseenter='showSanctionMenu(event, this, ${message.getMessage().getAuthor().getId()})' onmouseleave='closeSanctionMenu(event, this)'>${message.getMessage().getAuthor().getLogin()}</div>
								</div>
								<div class="time_and_team_box">
									<div class="message_team">${message.getTeam().getName()} -&nbsp</div>
									<div class="message_time">[${message.getMessage().getTime()}]</div>
								</div>
							</div>
							<div class="message_content">
								<div class="message_data">${message.getMessage().getData()}</div>
								<div class="message_buttons">
									<div class="restore_message_button" onclick='restoreMessage(${message.getMessage().getId()}, this)'>&#8635</div>
									<div class="delete_message_button" onclick='deleteMessage(${message.getMessage().getId()}, this)'>✖</div>
								</div>
							</div>
						</div>
					</c:forEach>
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
							<div id="general_chat" class="team" onclick="showAll()">Общий чат</div>
							<c:forEach var="teamMap" items="${fullTeamMap}">
								<div class="team" onclick="chatMessageFilter(this)">${teamMap.getKey().getName()}</div>
							</c:forEach>
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