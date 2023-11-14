<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="ru-RU">
<meta http-equiv="Cache-Control" content="no-cache">
<head>
<head>
	<%
		String loginValue = request.getParameter("login");
		String passwordValue = request.getParameter("password");
		String teamId = request.getParameter("team_id");
	%>
	<title>Кабинет пользователя/капитана/эксперта</title>
	<link rel="stylesheet" href="/04-12-kuznecov-sokolov/css/main_win.css">
	<link rel="stylesheet" href="/04-12-kuznecov-sokolov/css/input_items.css">
	<link rel="stylesheet" href="/04-12-kuznecov-sokolov/css/user.css">
	
<script>
    function restoreMessage(messageId, element) {
        // Создаем объект XMLHttpRequest
        var xhr = new XMLHttpRequest();

        // Настраиваем запрос
        xhr.open('POST', 'controller', true);
        xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');

        // Отправляем запрос с идентификатором сообщения
        xhr.send('command=Database&activity=restore_message&login=<%= loginValue %>&password=<%= passwordValue %>&team_id=<%= teamId %>&messageId=' + messageId);

        // Обрабатываем ответ сервера (если нужно)
        xhr.onreadystatechange = function () {
            if (xhr.readyState === XMLHttpRequest.DONE) {
            	element.parentNode.parentNode.parentNode.classList.remove("deleted_message");
            }
        };
    }
    
    function deleteMessage(messageId, element) {
        // Создаем объект XMLHttpRequest
        var xhr = new XMLHttpRequest();

        // Настраиваем запрос
        xhr.open('POST', 'controller', true);
        xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');

        // Отправляем запрос с идентификатором сообщения
	    xhr.send('command=Database&activity=delete_message&login=<%= loginValue %>&password=<%= passwordValue %>&team_id=<%= teamId %>&messageId=' + messageId);

        // Обрабатываем ответ сервера (если нужно)
        xhr.onreadystatechange = function () {
            if (xhr.readyState === XMLHttpRequest.DONE) {
            	element.parentNode.parentNode.parentNode.classList.add("deleted_message");
            }
        };
    }
</script>

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
					<c:forEach var="message" items="${messageList}">
						<c:set var="messageIsDeleted" value="${deletedMessageSet.containsKey(message)}"></c:set>
						<c:if test="${!messageIsDeleted || messageIsDeleted && message.getAuthor().getLogin().equals(userName)}">  
							<div class="message<c:if test="${messageIsDeleted}"> deleted_message</c:if>">
								<div class="message_header">
									<div class="user_box">
										<div class="message_user">${message.getAuthor().getLogin()}</div>
									</div>
									<div class="message_time">[${message.getTime()}]</div>
								</div>
								<div class="message_content">
									<div class="message_data">${message.getData()}</div>
										<div class="message_buttons">
											<c:if test="${!messageIsDeleted && message.getAuthor().getLogin().equals(userName) || messageIsDeleted && message.getAuthor().getId().equals(deletedMessageSet.get(message))}"> 
												<div class="restore_message_button" onclick='restoreMessage(${message.getId()}, this)'>&#8635</div>
												<div class="delete_message_button" onclick='deleteMessage(${message.getId()}, this)'>✖</div>
											</c:if>
										</div>

								</div>
							</div>
						</c:if>
					</c:forEach>
				</div>
				<form class="display_contents_form" id="message_chat_form" action="controller" method="POST">
					<input type="hidden" name="team_id" value="<%= teamId %>"/>
					<input type="hidden" name="command" value="Database"/>
					<input type="hidden" name="activity" value="send_message"/>
					<input type="hidden" name="login" value="<%= loginValue %>"/>
				    <input type="hidden" name="password" value="<%= passwordValue %>"/>
					<div id="message_input_box" class="center_bar_boxes">
						<div id="message_input_box_text_box">	
							<input id="text_box_message" class="text_box" type="text" name="message" placeholder="Введите сообщение"></input>
						</div>
						<div id="message_input_box_send">
							<input type="submit" value="Отправить"/>
						</div>
					</div>
				</form>
			</div>
			<div id="right_bar" class="container">
				<div class='team_list'>
					<div id="team_name" class="right_bar_header">${team.getName()}</div>
					<div id="team_list_caption" class="right_bar_header">Члены команды</div>
					<div id="member_list">
						<div id="team_capitan" class="team_member">
							${teamMembers.get(0).getLogin()}
						</div>
						<c:forEach var="member" begin="1" items="${teamMembers}">
							<div class="team_member">
								${member.getLogin()}
							</div>
						</c:forEach>
					</div>
				</div>
				<jsp:useBean id="myLogic" class="ru.rsreu.kuznecovsokolov12.servlet.LoginLogic" scope="page"></jsp:useBean>
				<div id="expert_block">
					<c:if test = "${myLogic.checkLogin(userName, userPassword).toString() != 'expert'}">
						<div id="expert_block_caption">
							Эксперт: ${teamExpert.getLogin()}
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