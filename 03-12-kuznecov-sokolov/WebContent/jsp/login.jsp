<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html lang="ru-RU">
<head>
	<title>Авторизация</title>
	<link rel="stylesheet" href="/03-12-kuznecov-sokolov/css/main_win.css">
	<link rel="stylesheet" href="/03-12-kuznecov-sokolov/css/input_items.css">
	<link rel="stylesheet" href="/03-12-kuznecov-sokolov/css/login.css">
</head>
<meta http-equiv="Cache-Control" content="no-cache">
<body>
	<div id="login_main_box">
		<div id="app_name">Эксперты и команды</div>
		<form id="login" name="loginForm" method="POST" action="controller">
			<input type="hidden" name="command" value="login" />
			<div id="login_box">
				<div id="login_box_caption">Логин</div>
				<input class="text_box" type="text" name="login" value=""/>
			</div>
			<div id="password_box">
				<div id="password_box_caption">Пароль</div>
				<input class="text_box" type="password" name="password" value=""/>
			</div>
			<div id="error_box">
				${errorLoginPassMessage}
				${wrongAction}
				${nullPage}
			</div>
			<input id="button_get_in" type="submit" value="Войти"/>
		</form>
	</div>
</body></html>