<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html lang="ru-RU"><head><title>Авторизация</title></head>
<meta http-equiv="Cache-Control" content="no-cache">
<body>
	<h1 style="font-size:32px;" align="center">Эксперты и команды</h1>
	<p style="font-size:20px;" align="center">Введите ваши учетные данные для входа</p>
	<form name="loginForm" method="POST" action="controller">
		<input type="hidden" name="command" value="login" />
		<p style="font-size:20px;" align="center">Логин:
		<input type="text" name="login" value=""/></p>
		<p style="font-size:20px;" align="center">Пароль:
		<input type="password" name="password" value=""/><br/>
			 <br/>
			${errorLoginPassMessage}
			 <!-- <br/> -->
			${wrongAction}
			 <!-- <br/> -->
			${nullPage}
			 <br/>
		<input type="submit" style="font-size:20px;" value="Войти"/>
	</form><hr/>
</body></html>