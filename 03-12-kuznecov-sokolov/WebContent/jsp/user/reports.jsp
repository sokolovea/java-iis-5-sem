<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="ru-RU">
<head>
	<title>Кабинет пользователя/капитана/эксперта</title>
	<link rel="stylesheet" href="/03-12-kuznecov-sokolov/css/main_win.css">
	<link rel="stylesheet" href="/03-12-kuznecov-sokolov/css/input_items.css">
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
						<a class="menu_button_ref" href="controller?login=${userName}&password=${userPassword}&command=Report"><div>Отчеты</div></a>
					</li>
					<li id="button_to_team" class="menu_button">
						<a class="menu_button_ref" href="controller?login=${userName}&password=${userPassword}&destination=team&command=Menu"><div>Вернуться в команду</div></a>
					</li>
				</ol>
			</div>
			<jsp:useBean id="myLogic" class="ru.rsreu.kuznecovsokolov12.servlet.LoginLogic" scope="page"></jsp:useBean>
			<div id="center_bar" class="container">
				<c:if test = "${myLogic.checkLogin(userName, userPassword).toString() == 'expert'}">
		            <div>Вывод команд, консультирующихся у данного эксперта<br>
		            	<table>
						  <thead>
						    <tr>
						      <th>Наименование команды</th>
						    </tr>
						  </thead>
						</table>
		            </div>
		            <div>Вывод N команд, которым данный эксперт писал больше всего сообщений<br>
		            	<input class="text_box" type="text" name="countCommands" value="100"/><br/>
	            		<input type="button" name="countCommands_submit" value="Найти"/>
		            	<table>
						  <thead>
						    <tr>
						      <th>Наименование команды</th>
						    </tr>
						  </thead>
						</table>
		            </div>
		            <div>Вывод команд, отказавшихся от консультирования у данного эксперта<br>
			           	<table>
						  <thead>
						    <tr>
						      <th>Наименование команды</th>
						    </tr>
						  </thead>
						</table>
		            </div>
		        </c:if>
				<c:if test = "${myLogic.checkLogin(userName, userPassword).toString() != 'expert'}">
		            <div>Вывод сообщений, которые удалил не сам пользователь<br>
			            <table>
						  <thead>
						    <tr>
						      <th>Сообщение</th>
						      <th>Время</th>
						    </tr>
						  </thead>
						</table>
					</div>
		            <div>История санкций, наложенных на данного пользователя<br>
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
		            <div>Вывод количества оставленных и удаленных сообщений у данного пользователя<br>
		            	Всего сообщений:<br>
		            	Удалено сообщений:<br>
		            </div>
	             </c:if>
			</div>
			<div id="right_bar" class="container">
			</div>
		</div>
	</div>
    <style>
    	th {
			border: 2px solid;
		}	
    </style>
</body></html>