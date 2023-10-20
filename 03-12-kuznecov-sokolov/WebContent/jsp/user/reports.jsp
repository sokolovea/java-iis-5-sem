<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="ru-RU">
<head>
	<title>Кабинет пользователя/капитана/эксперта</title>
	<link rel="stylesheet" href="/03-12-kuznecov-sokolov/css/main_win.css">
	<link rel="stylesheet" href="/03-12-kuznecov-sokolov/css/input_items.css">
	<link rel="stylesheet" href="/03-12-kuznecov-sokolov/css/reports.css">
	<script type="text/javascript">
		function reportSelector(element, report_id) {
			const reports = document.getElementsByClassName("report");
			for (let i = 0; i < reports.length; i++) {
				reports[i].style.opacity = 0;
			}
			document.getElementById(report_id).style.opacity = 1;
			
			const reports_button = document.getElementsByClassName("report_button");
			for (let i = 0; i < reports_button.length; i++) {
				reports_button[i].style.textDecoration = "none";
			}
			element.style.textDecoration = "underline black";
		}
	</script>
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
					<li id="button_to_team" class="menu_button">
						<a class="menu_button_ref" href="controller?login=${userName}&password=${userPassword}&destination=team&command=Menu">Вернуться в команду</a>
					</li>
				</ol>
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
								<input class="text_box" type="text" name="countCommands" value="100"/><br/>
		            			<input type="button" name="countCommands_submit" value="Найти"/>
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
					<c:if test = "${myLogic.checkLogin(userName, userPassword).toString() != 'expert'}">
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
	             </div>
			</div>
			<div id="right_bar" class="container">
				<div id="avaliable_reports">
					<div id="reports_header">
						Доступные отчеты
					</div>
					<div id="reports_buttons">
						<div class="report_button" onclick='reportSelector(this, "report_messages_deleted_by_no_user")'>
							Отчет 1
						</div>
						<div class="report_button" onclick='reportSelector(this, "report_sanction_history")'>
							Отчет 2
						</div>
						<div class="report_button" onclick='reportSelector(this, "report_user_messages_stat")'>
							Отчет 3
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body></html>