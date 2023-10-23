<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="ru-RU">
<head>
	<meta http-equiv="Cache-Control" content="no-cache">
	<title>Кабинет администратора</title>
	<link rel="stylesheet" href="/03-12-kuznecov-sokolov/css/main_win.css">
	<link rel="stylesheet" href="/03-12-kuznecov-sokolov/css/input_items.css">
	<link rel="stylesheet" href="/03-12-kuznecov-sokolov/css/admin.css">
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
					<li id="button_settings" class="menu_button">
						<a class="menu_button_ref" href="controller?login=${userName}&password=${userPassword}&destination=settings&command=Menu">Настройки СИС</a>
					</li>
				</ol>
			</div>
			<div id="center_bar" class="container">
				<div id="user_info_form">
					<div id="user_info_form_caption">Изменение данных пользователей</div>
					<div id="current_user_caption">Текущий пользователь: не выбран</div>
					<div class="user_form_item">
						<div class="user_form_item_caption">Логин</div>
						<input id="user_login" class="text_box" type="text" name="userLogin" value="user1"/>
					</div>
					<div class="user_form_item">
						<div class="user_form_item_caption">Пароль</div>
						<input id="user_password" class="text_box" type="text" name="userPassword" value="1234567890"/>
					</div>
					<div class="user_form_item">
						<div class="user_form_item_caption">Имя</div>
						<input id="user_name" class="text_box" type="text" name="userName" value="Userov User Userovich"/>
					</div>
					<div class="user_form_item">
						<div class="user_form_item_caption">Электронная почта</div>
						<input id="user_email" class="text_box" type="text" name="userEmail" value="123@yandex.ru"/>
					</div>
					<div class="user_form_item">
						<div class="user_form_item_caption">Роль</div>
						<select id="user_role" class="text_box" name="userRole">
							<option>Пользователь</option>
							<option>Эксперт</option>
							<option>Модератор</option>
							<option>Администратор</option>
						</select>
					</div>
					<div class="user_form_buttons">
						<input id="save_changes" type="button" name="saveChanges" value="Сохранить"/>
						<input id="create_user" type="button" name="creatUser" value="Создать пользователя"/>
						<input id="remove_user" type="button" name="removeUser" value="Удалить пользователя"/>
					</div>
		        </div>
			</div>
			<div id="right_bar" class="container">
				<div id="full_users_list_box">
					<div id="full_users_list_caption" class="right_bar_header">
						Список всех пользователей СИС
					</div>
					<div id="users_filter">
						<input id="user_filter_input" class="text_box" type="text" name="userFilter" placeholder="Фильтр"/>
					</div>
					<div id="full_users_list">
						<div class="user">User1</div>
						<div class="user">User2</div>
						<div class="user">User3</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body></html>