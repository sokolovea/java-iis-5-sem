<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="ru-RU">
<head>
	<title>Кабинет пользователя/капитана/эксперта</title>
	<link rel="stylesheet" href="/03-12-kuznecov-sokolov/css1/main_win.css">
	<link rel="stylesheet" href="/03-12-kuznecov-sokolov/css1/input_items.css">
</head>
<body>

	<div class="window"> 
		<div id="top_bar" class="container">
			<div class="top_bar_items">
				User name
			</div>
			<div id="logout_button" class="top_bar_items">
				<a id="logout_button_ref" href="controller?command=login">Logout</a><br>
			</div>
		</div>
		<div id="main_content_bar">
			<div id="left_bar" class="container">
				<ol>
					<li id="button_report" class="menu_button">
						<a href="controller?login=${userName}&password=${userPassword}&command=Report"><div>Отчеты</div></a>
					</li>
					<li id="button_to_team" class="menu_button">
						<a href="controller?login=${userName}&password=${userPassword}&destination=team&command=Menu"><div>Вернуться в команду</div></a>
					</li>
				</ol>
			</div>
			<jsp:useBean id="myLogic" class="ru.rsreu.kuznecovsokolov12.servlet.LoginLogic" scope="page"></jsp:useBean>
			<div id="center_bar" class="container">
				<c:if test = "${myLogic.checkLogin(userName, userPassword).toString() == 'expert'}">
		            <p>Раздел эксперта</p>
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
				<c:if test = "${myLogic.checkLogin(userName, userPassword).toString() == 'user'}">
		            <p>Раздел пользователя</p>
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
     <!-- <div class='reports'>
        	<c:if test = "${userRole != 'USER'}">
	            <p>Раздел эксперта</p>
	            <div>Вывод команд, консультирующихся у данного эксперта</div>
	            <div>Вывод N команд, которым данный эксперт писал больше всего сообщений</div>
	            <div>Вывод команд, отказавшихся от консультирования у данного эксперта</div>
	        </c:if>
	        <c:if test = "${userRole == 'USER'}">
	            <p>Раздел пользователя</p>
	            <div>Вывод сообщений, которые удалил не сам пользователь</div>
	            <div>История санкций, наложенных на данного пользователя</div>
	            <div>Вывод количества оставленных и удаленных сообщений у данного пользователя</div>
             </c:if>
        </div>

        <div class='additional_buttons'>
            <input type="submit" value="???Вернуться куда-то назад"/><br>
            <input type="submit" value="???Отчеты"/><br>
            <input type="submit" value="???Меню выбора эксперта капитаном (команды экспертом)"/><br>
        </div>


        <!-- <div class='user_list'>
            <textarea name="message" rows="30" cols="15">Список пользователей команды (возможно, лучше сделать в таблице)</textarea>
            <br>
            <input type="submit" value="Исключить выделенного пользователя (только капитан)"/>
        </div>

        <div class='expert_block'>
            Ваш эксперт: ...
            <br>
            <input type="submit" value="Отказаться от консультирования (капитан)"/>
        </div> -->
</body></html>