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
			<a href="controller?command=login">Logout</a><br>
		</div>
		<div id="main_content_bar">
			<div id="left_bar" class="container">
				<ol>
					<li id="button_report" class="menu_button">
						<a href="controller?login=${userName}&role=${userRole}&command=Report"><div>Отчеты</div></a>
					</li>
					<li id="button_to_team" class="menu_button">
						<a href="team_select.jsp"><div>Вернуться в команду</div></a>
					</li>
					<li id="button_select_expert" class="menu_button">
						<a href="user_2.jsp"><div>Меню выбора эксперта капитаном (команды экспертом)</div></a>
					</li>
				</ol>
			</div>
			<div id="center_bar" class="container">
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
        </div> --> -->
</body></html>