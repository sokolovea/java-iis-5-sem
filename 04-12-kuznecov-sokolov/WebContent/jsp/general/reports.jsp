<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="ru-RU">
<meta http-equiv="Cache-Control" content="no-cache">
<head>
	<title>Отчеты</title>
	<link rel="stylesheet" href="/04-12-kuznecov-sokolov/css/main_win.css">
	<link rel="stylesheet" href="/04-12-kuznecov-sokolov/css/input_items.css">
	<link rel="stylesheet" href="/04-12-kuznecov-sokolov/css/reports.css">
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
			
			localStorage.setItem('element_id', element.id);
			localStorage.setItem('report_id', report_id);
		}

		
		window.onload = function() {
			var element_id = localStorage.getItem("element_id");
			var report_id = localStorage.getItem("report_id");
			element = document.getElementById(element_id);
			if (element != null) {
				reportSelector(element, report_id);
			}
			console.log(element_id);
			console.log(report_id);
		};
		

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
				<div id="report_container">
				
					<c:if test = "${role.toString() == 'expert'}">
						<div id="report_consult_with_expert" class="report">
							<div class="report_caption">Вывод команд, консультирующихся у данного эксперта</div>
							<div class="report_table">
								<table>
									<thead>
										<tr>
											<th>Наименование команды</th>
										</tr>
										<tbody>
									        <c:forEach var="team" items="${expertReportFirst}">
									            <tr>
									                <td>${team.getName()}</td>
									            </tr>
									        </c:forEach>
							    		</tbody>
								    </thead>
								</table>
							</div>
						</div>
			            <div id="report_more_messages" class="report">
							<div class="report_caption">Вывод N команд, которым данный эксперт писал больше всего сообщений</div>
 							<form id="admin_report_second_form" action="controller" method="GET"> 
								<div class="report_inputs">
									<input type="hidden" name="command" value="Report"/>
									<span style="margin-right: 7px;">N =</span> 
									<input id="n_teams_text_box" class="text_box" type="number" name="countCommands" min="0" max="10000" required/><br/>
			            			<input id="n_teams_search" type="submit" name="countCommands_submit" value="Найти"/>
								</div>
							</form>
							<div class="report_table">
								<table>
									<thead>
										<tr>
											<th>Наименование команды</th>
										</tr>
										<tbody>
									        <c:forEach var="team" items="${expertReportSecond}">
									            <tr>
									                <td>${team.getKey().getName()}</td>
									            </tr>
									        </c:forEach>
							    		</tbody>
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
							    	<tbody>
								        <c:forEach var="team" items="${expertReportThird}">
								            <tr>
								                <td>${team.getName()}</td>
								            </tr>
								        </c:forEach>
						    		</tbody>
								</table>
							</div>
						</div>
			        </c:if>
			        
					<c:if test = "${role.toString() == 'user'}">
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
								  	<tbody>
								        <c:forEach var="message" items="${userReportFirst}">
								            <tr>
								                <td>${message.getData()}</td>
								                <td>${message.getTime()}</td>
								            </tr>
								        </c:forEach>
							    	</tbody>
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
									<tbody>
								        <c:forEach var="sanction" items="${userReportSecond}">
								            <tr>
								                <td>${sanction.getType().getName()}</td>
								                <td>${sanction.getSender().getLogin()}</td>
								                <td>${sanction.getReason()}</td>
								                <td>${sanction.getTime()}</td>
								            </tr>
								        </c:forEach>
							    	</tbody>
								</table>
							</div>
						</div>
						<div id="report_user_messages_stat" class="report">
							<div class="report_caption">Вывод количества оставленных и удаленных сообщений у данного пользователя</div>
							<div class="report_string">
								Всего сообщений: ${countSendedMessages}<br>
			            		Удалено сообщений: ${countDeletedMessages}<br>
							</div>
						</div>
		            </c:if>
		            
		            <c:if test = "${role.toString() == 'moderator'}">
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
								    <tbody>
								        <c:forEach var="user" items="${moderatorReportFirst}">
								            <tr>
								                <td>${user.getLogin()}</td>
								                <td>${user.getName()}</td>
								                <td>${user.getEmail()}</td>
								                <td>${user.isIsAuthorized()}</td>
								            </tr>
								        </c:forEach>
						    		</tbody>
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
								    <tbody>
								        <c:forEach var="sanction" items="${moderatorReportSecond}">
								            <tr>
								                <td>${sanction.getType().getName()}</td>
								                <td>${sanction.getReceiver().getLogin()}</td>
								                <td>${sanction.getReason()}</td>
								                <td>${sanction.getTime()}</td>
								            </tr>
								        </c:forEach>
						    		</tbody>
								</table>
							</div>
						</div>
			            <div id="report_moderator_blocked_n" class="report">
							<div class="report_caption">Вывод пользователей, заблокированных N или более раз</div>
							<form id="admin_report_second_form" action="controller" method="GET">
								<div class="report_inputs">
									<input type="hidden" name="command" value="Report"/>
									<span style="margin-right: 7px;">N =</span> 
									<input id="n_moderator_blocked_n_text_box" class="text_box" type="number" name="countBlocked" min="0" max="10000" required/>
			            			<input id="n_moderator_blocked_n_search" type="submit" name="countBlocked_submit" value="Найти"/>
								</div>
							</form>
							<div class="report_table">
								<table>
									<thead>
										<tr>
											<th>Пользователь</th>
										</tr>
								    </thead>
								    <tbody>
								        <c:forEach var="user" items="${moderatorReportThird}">
								            <tr>
								                <td>${user.getLogin()}</td>
								            </tr>
								        </c:forEach>
						    		</tbody>
								</table>
							</div>
						</div>
		            </c:if>
		            
		            <c:if test = "${role.toString() == 'admin'}">

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
								    <tbody>
								        <c:forEach var="user" items="${adminReportFirst}">
								            <tr>
								                <td>${user.getKey().getLogin()}</td>
								                <td>${user.getKey().getPassword()}</td>
								                <td>${user.getKey().getName()}</td>
								                <td>${user.getKey().getEmail()}</td>
								                <td>${user.getValue().getName()}</td>
								                <td>${user.getKey().isIsAuthorized()}</td>
								            </tr>
								        </c:forEach>
							    	</tbody>
								</table>
							</div>
						</div>
						<div id="report_admin_user_role_history" class="report">
							<div class="report_caption">Вывод информации о истории назначения на роль по конкретной учетной записи</div>
							<form id="admin_report_second_form" action="controller" method="GET">
								<div class="report_inputs">
									<input type="hidden" name="command" value="Report"/>
									<span style="margin-right: 7px;">login =</span> 
									<input id="user_login_text_box" class="text_box" type="text" name="adminUserRole" placeholder="Введите логин пользователя" maxlength="30"/>
			            			<input id="user_search" type="submit" name="adminUserRole_submit" value="Найти"/>
								</div>
							</form>
							<div class="report_table">
								<table>
									<thead>
										<tr>
											<th>Пользователь</th>
							    			<th>Роль</th>
							    			<th>Кто назначил</th>
										    <th>Время назначения</th>
										</tr>
								    </thead>
								  	<c:forEach var="roleAssigment" items="${adminReportSecond}">
							            <tr>
							                <td>${roleAssigment.getReceiver().getLogin()}</td>
							                <td>${roleAssigment.getRole().getName()}</td>
							                <td>${roleAssigment.getSender().getLogin()}</td>
							                <td>${roleAssigment.getTime()}</td>
							            </tr>
							        </c:forEach>
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
						<c:if test = "${role.toString() == 'expert'}">
							<div id="consulted_teams_button" class="report_button" onclick='reportSelector(this, "report_consult_with_expert")'>
								Консультирующиеся команды
							</div>
							<div id="usefull_cooperate_button" class="report_button" onclick='reportSelector(this, "report_more_messages")'>
								Полезное сотрудничество
							</div>
							<div id="teams_ejected_expert_button" class="report_button" onclick='reportSelector(this, "report_team_reject_consult")'>
								Вывод команд, отказавшихся от консультирования у данного эксперта
							</div>
						</c:if>
						<c:if test = "${role.toString() == 'user'}">
							<div id="deleted_message_button" class="report_button" onclick='reportSelector(this, "report_messages_deleted_by_no_user")'>
								Удаленные сообщения
							</div>
							<div id="sanction_history_button" class="report_button" onclick='reportSelector(this, "report_sanction_history")'>
								История санкций
							</div>
							<div id="message_stats_button" class="report_button" onclick='reportSelector(this, "report_user_messages_stat")'>
								Статистика сообщений
							</div>
						</c:if>
						<c:if test = "${role.toString() == 'moderator'}">
							<div id="user_list_button" class="report_button" onclick='reportSelector(this, "report_moderator_users")'>
								Вывод учетных записей, являющихся пользователями
							</div>
							<div id="block_stats_button" class="report_button" onclick='reportSelector(this, "report_moderator_sanction_history")'>
								Статистика блокировок и разблокировок пользователей данным модератором
							</div>
							<div id="users_blocked_more_N_times_button" class="report_button" onclick='reportSelector(this, "report_moderator_blocked_n")'>
								Вывод пользователей, заблокированных N или более раз
							</div>
						</c:if>
						<c:if test = "${role.toString() == 'admin'}">
							<div id="users_info_list_button" class="report_button" onclick='reportSelector(this, "report_admin_users")'>
								Вывод информации о всех учетных записях
							</div>
							<div id="role_assigment_history_button" class="report_button" onclick='reportSelector(this, "report_admin_user_role_history")'>
								Вывод информации о истории назначения на роль по конкретной учетной записи
							</div>
						</c:if>
					</div>
				</div>
			</div>
		</div>
	</div>
</body></html>