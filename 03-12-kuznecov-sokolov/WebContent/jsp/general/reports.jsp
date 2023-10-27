<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="ru-RU">
<meta http-equiv="Cache-Control" content="no-cache">
<head>
	<title>Отчеты</title>
	<link rel="stylesheet" href="/03-12-kuznecov-sokolov/css/main_win.css">
	<link rel="stylesheet" href="/03-12-kuznecov-sokolov/css/input_items.css">
	<link rel="stylesheet" href="/03-12-kuznecov-sokolov/css/reports.css">
	<script type="text/javascript">
		function reportSelector(element, report_id) {
			const reports = document.getElementsByClassName("report");
			for (let i = 0; i < reports.length; i++) {
				reports[i].style.opacity = 0;
				reports[i].style.zIndex = 0;
				reports[i].style.pointerEvents = "none";
			}
			document.getElementById(report_id).style.opacity = 1;
			document.getElementById(report_id).style.zIndex = 1;
			document.getElementById(report_id).style.pointerEvents = "auto";
			
			const reports_button = document.getElementsByClassName("report_button");
			for (let i = 0; i < reports_button.length; i++) {
				reports_button[i].style.textDecoration = "auto";
			}
			element.style.textDecoration = "underline black";
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
			<jsp:useBean id="myLogic" class="ru.rsreu.kuznecovsokolov12.servlet.LoginLogic" scope="page"></jsp:useBean>
			<div id="center_bar" class="container">
				<div id="report_container">
					<c:if test = "${myLogic.checkLogin(userName, userPassword).toString() == 'expert'}">
						<div id="report_consult_with_expert" class="report">
							<div class="report_caption">Вывод команд, консультирующихся у данного эксперта</div>
							<div class="report_table">
								<table>
									<thead>
										<tr>
											<th>Наименование команды</th>
										</tr>
								    </thead>
								</table>
							</div>
						</div>
			            <div id="report_more_messages" class="report">
							<div class="report_caption">Вывод N команд, которым данный эксперт писал больше всего сообщений</div>
							<div class="report_inputs">
								<span style="margin-right: 7px;">N =</span> 
								<input id="n_teams_text_box" class="text_box" type="number" name="countCommands" value="100"/><br/>
		            			<input id="n_teams_search" type="button" name="countCommands_submit" value="Найти"/>
							</div>
							<div class="report_table">
								<table>
									<thead>
										<tr>
											<th>Наименование команды</th>
										</tr>
								    </thead>
								</table>
							</div>
						</div>
			            <div id="report_team_reject_consult" class="report">
							<div class="report_caption">Вывод команд, отказавшихся от консультирования у данного эксперта</div>
							<div class="report_table">
								<table>
									<thead>
										<tr>
											<th>Наименование команды</th>
										</tr>
								    </thead>
								</table>
							</div>
						</div>
			        </c:if>
					<c:if test = "${myLogic.checkLogin(userName, userPassword).toString() == 'user' || myLogic.checkLogin(userName, userPassword).toString() == 'captain'}">
			            <div id="report_messages_deleted_by_no_user" class="report">
							<div class="report_caption">Вывод сообщений, которые удалил не сам пользователь</div>
							<div class="report_table">
								<table>
									<thead>
										<tr>
											<th>Сообщение</th>
							      			<th>Время</th>
										</tr>
								    </thead>
								</table>
							</div>
						</div>
						<div  id="report_sanction_history" class="report">
							<div class="report_caption">История санкций, наложенных на данного пользователя</div>
							<div class="report_table">
								<table>
									<thead>
										<tr>
											<th>Тип санкции</th>
							    			<th>Отправитель</th>
										    <th>Причина</th>
										    <th>Время</th>
										</tr>
								    </thead>
								</table>
							</div>
						</div>
						<div id="report_user_messages_stat" class="report">
							<div class="report_caption">Вывод количества оставленных и удаленных сообщений у данного пользователя</div>
							<div class="report_string">
								Всего сообщений:<br>
			            		Удалено сообщений:<br>
							</div>
						</div>
		            </c:if>
		            <c:if test = "${myLogic.checkLogin(userName, userPassword).toString() == 'moderator'}">
			            <div id="report_moderator_users" class="report">
							<div class="report_caption">Вывод учетных записей, являющихся пользователями</div>
							<div class="report_table">
								<table>
									<thead>
										<tr>
											<th>Логин</th>
							      			<th>ФИО</th>
							      			<th>email</th>
							      			<th>Авторизован</th>
										</tr>
								    </thead>
								</table>
							</div>
						</div>
						<div  id="report_moderator_sanction_history" class="report">
							<div class="report_caption">Статистика блокировок и разблокировок пользователей данным модератором</div>
							<div class="report_table">
								<table>
									<thead>
										<tr>
											<th>Тип санкции</th>
							    			<th>Пользователь</th>
										    <th>Причина</th>
										    <th>Время</th>
										</tr>
								    </thead>
								</table>
							</div>
						</div>
			            <div id="report_moderator_blocked_n" class="report">
							<div class="report_caption">Вывод пользователей, заблокированных N или более раз</div>
							<div class="report_inputs">
								<span style="margin-right: 7px;">N =</span> 
								<input id="n_moderator_blocked_n_text_box" class="text_box" type="number" name="countBlocked" value="100"/><br/>
		            			<input id="n_moderator_blocked_n_search" type="button" name="countBlocked_submit" value="Найти"/>
							</div>
							<div class="report_table">
								<table>
									<thead>
										<tr>
											<th>Пользователь</th>
										</tr>
								    </thead>
								</table>
							</div>
						</div>
		            </c:if>
		            
		            
		            <c:if test = "${myLogic.checkLogin(userName, userPassword).toString() == 'admin'}">
			            <div id="report_admin_users" class="report">
							<div class="report_caption">Вывод информации о всех учетных записях</div>
							<div class="report_table">
								<table>
									<thead>
										<tr>
											<th>Логин</th>
											<th>Пароль</th>
							      			<th>ФИО</th>
							      			<th>email</th>
							      			<th>Роль</th> <!-- решил добавить -->
							      			<th>Авторизован</th>
										</tr>
								    </thead>
								</table>
							</div>
						</div>
						<div id="report_admin_user_role_history" class="report">
							<div class="report_caption">Вывод информации о истории назначения на роль по конкретной учетной записи</div>
							<div class="report_table">
								<table>
									<thead>
										<tr>
											<th>Пользователь</th>
							    			<th>Роль</th>
										    <th>Время назначения</th>
										</tr>
								    </thead>
								</table>
							</div>
						</div>
		            </c:if>
		            
		            
	             </div>
			</div>
			<div id="right_bar" class="container">
				<div id="avaliable_reports">
					<div id="reports_header" class="right_bar_header">
						Доступные отчеты
					</div>
					<div id="reports_buttons">
						<c:if test = "${myLogic.checkLogin(userName, userPassword).toString() == 'expert'}">
							<div class="report_button" onclick='reportSelector(this, "report_consult_with_expert")'>
								Консультирующиеся команды
							</div>
							<div class="report_button" onclick='reportSelector(this, "report_more_messages")'>
								Полезное сотрудничество
							</div>
							<div class="report_button" onclick='reportSelector(this, "report_team_reject_consult")'>
								Вывод команд, отказавшихся от консультирования у данного эксперта
							</div>
						</c:if>
						<c:if test = "${myLogic.checkLogin(userName, userPassword).toString() == 'user' || myLogic.checkLogin(userName, userPassword).toString() == 'captain'}">
							<div class="report_button" onclick='reportSelector(this, "report_messages_deleted_by_no_user")'>
								Удаленные сообщения
							</div>
							<div class="report_button" onclick='reportSelector(this, "report_sanction_history")'>
								История санкций
							</div>
							<div class="report_button" onclick='reportSelector(this, "report_user_messages_stat")'>
								Статистика сообщений
							</div>
						</c:if>
						<c:if test = "${myLogic.checkLogin(userName, userPassword).toString() == 'moderator'}">
							<div class="report_button" onclick='reportSelector(this, "report_moderator_users")'>
								Вывод учетных записей, являющихся пользователями
							</div>
							<div class="report_button" onclick='reportSelector(this, "report_moderator_sanction_history")'>
								Статистика блокировок и разблокировок пользователей данным модератором
							</div>
							<div class="report_button" onclick='reportSelector(this, "report_moderator_blocked_n")'>
								Вывод пользователей, заблокированных N или более раз
							</div>
						</c:if>
						<c:if test = "${myLogic.checkLogin(userName, userPassword).toString() == 'admin'}">
							<div class="report_button" onclick='reportSelector(this, "report_admin_users")'>
								Вывод информации о всех учетных записях
							</div>
							<div class="report_button" onclick='reportSelector(this, "report_admin_user_role_history")'>
								Вывод информации о истории назначения на роль по конкретной учетной записи
							</div>
						</c:if>
					</div>
				</div>
			</div>
		</div>
	</div>
</body></html>