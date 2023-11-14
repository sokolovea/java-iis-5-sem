<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ru-RU">
<meta http-equiv="Cache-Control" content="no-cache">
<head>
	<title>Выбор команды</title>
	<link rel="stylesheet" href="/04-12-kuznecov-sokolov/css/main_win.css">
	<link rel="stylesheet" href="/04-12-kuznecov-sokolov/css/input_items.css">
	<link rel="stylesheet" href="/04-12-kuznecov-sokolov/css/team_select.css">
	<%
		String loginValue = request.getParameter("login");
		String passwordValue = request.getParameter("password");
		String teamId = request.getParameter("team_id");
	%>
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
				<div id="center_bar_top">
					<div id="create_team" class="center_bar_boxes"> 
						<div id="create_team_caption" class="header_name">
							Создание команды
						</div>
							<form class="display_contents_form" id="create_form_team" action="controller" method="POST">
							<div id="create_team_input">
								<div id="create_team_text_box">
									<input type="hidden" name="team_id" value="<%= teamId %>"/>
									<input type="hidden" name="command" value="Database"/>
									<input type="hidden" name="activity" value="create_team"/>
									<input type="hidden" name="login" value="<%= loginValue %>"/>
								    <input type="hidden" name="password" value="<%= passwordValue %>"/>
									<input class="text_box" type="text" name="teamFormName" placeholder="Название команды"/>
								</div>
								<div id="create_team_send">
									<input type="submit" value="Создать"/>
								</div>
							</div>
						</form>
					</div>
					<div class="center_bar_boxes" id="teams_info">
						<div id="team_count">
							Всего команд: ${fullTeamMap.size()}
						</div>
					</div>
				</div>
				<div class="center_bar_boxes" id="center_bar_bottom">
					<div id="team_list">
						<div id="team_list_header" class="header_name">
							Список команд
						</div>
						<div id="teams">
							<c:forEach var="team" items="${fullTeamMap}">
								<div class="team_box">
									<div class="team_box_top">
										<div class="team_name">
											${team.getKey().getName()}
										</div>
										<div class="team_fullness">
										${team.getValue().values().iterator().next()}
											/10
										</div>
									</div>
									<div class="team_box_bottom">
										<div class="team_captain">
											${team.getValue().keySet().iterator().next()}
										</div>
										<div class="team_join_button">
											<input type="button" value="Вступить"/>
										</div>
									</div>
								</div>
							</c:forEach>
						</div>
					</div>
				</div>		
			</div>
			<jsp:useBean id="myLogic" class="ru.rsreu.kuznecovsokolov12.servlet.LoginLogic" scope="page"></jsp:useBean>
			<div id="right_bar" class="container">
				<div id="list_of_teams">
					<div id="list_of_teams_caption" class="right_bar_header">
						<c:if test = "${myLogic.checkLogin(userName, userPassword).toString() != 'expert'}">
							Текущая команда пользователя:
						</c:if>
						<c:if test = "${myLogic.checkLogin(userName, userPassword).toString() == 'expert'}">
							Список команд, которые консультируются у эксперта:
						</c:if>
					</div>
					<div id="user_teams">
						<c:forEach var="team" items="${teamList}">
							<div class="rb_user_team_name">
								<a class="menu_button_ref" href="controller?login=${userName}&password=${userPassword}&command=Menu&destination=team&team_id=${team.getId()}">${team.getName()}</a>
							</div>
        				</c:forEach>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>