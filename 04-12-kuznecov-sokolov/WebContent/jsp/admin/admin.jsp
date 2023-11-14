<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="ru-RU">
<head>
	<%
		String loginValue = request.getParameter("login");
		String passwordValue = request.getParameter("password");
	%>
	<meta http-equiv="Cache-Control" content="no-cache">
	<title>Кабинет администратора</title>
	<link rel="stylesheet" href="/04-12-kuznecov-sokolov/css/main_win.css">
	<link rel="stylesheet" href="/04-12-kuznecov-sokolov/css/input_items.css">
	<link rel="stylesheet" href="/04-12-kuznecov-sokolov/css/admin.css">
	<script type="text/javascript">
		function clearFormData() {
			document.getElementById("user_login").value = "";
			document.getElementById("user_password").value = "";
			document.getElementById("user_name").value = "";
			document.getElementById("user_email").value = "";
			document.getElementById("user_role").options[0].selected = "selected";
		}
		
		function insertLoginToTextBox(element) {
			document.getElementById("user_login").value = element.textContent;
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
			<div id="center_bar" class="container">
				<div id="user_info_form">
					<div id="user_info_form_caption">Изменение данных пользователей</div>
					<div id="current_user_caption">Текущий пользователь: не выбран</div>
					<div class="user_form_item">
						<div class="user_form_item_caption">Логин</div>
						<input id="user_login" class="text_box" type="text" name="userLogin" placeholder="user1"/>
					</div>
					<div class="user_form_item">
						<div class="user_form_item_caption">Пароль</div>
						<input id="user_password" class="text_box" type="text" name="userPassword" placeholder="1234567890"/>
					</div>
					<div class="user_form_item">
						<div class="user_form_item_caption">Имя</div>
						<input id="user_name" class="text_box" type="text" name="userName" placeholder="Userov User Userovich"/>
					</div>
					<div class="user_form_item">
						<div class="user_form_item_caption">Электронная почта</div>
						<input id="user_email" class="text_box" type="text" name="userEmail" placeholder="123@yandex.ru"/>
					</div>
					<div class="user_form_item">
						<div class="user_form_item_caption">Роль</div>
						<select id="user_role" class="text_box" name="userRole">
							<c:forEach var="role" items="${roleList}">
								<option>${role.getName()}</option>
							</c:forEach>
						</select>
					</div>
					<div class="user_form_buttons">
						<form class="display_contents_form" id="unknown" action="controller" method="POST">
							<input type="hidden" name="command" value="Database"/>
							<input type="hidden" name="activity" value="update_user"/>
							<input type="hidden" name="login" value="<%= loginValue %>"/>
						    <input type="hidden" name="password" value="<%= passwordValue %>"/>
						    <input type="hidden" name="command_type" value="save_changes"/>
						    <input type="hidden" name="command_type" value="unknown"/>
						    <input id="save_changes" type="submit" name="saveChanges" value="Сохранить"/>
						</form>
						<form class="display_contents_form" id="unknown" action="controller" method="POST">
							<input type="hidden" name="command" value="Database"/>
							<input type="hidden" name="activity" value="update_user"/>
							<input type="hidden" name="login" value="<%= loginValue %>"/>
						    <input type="hidden" name="password" value="<%= passwordValue %>"/>
						    <input type="hidden" name="command_type" value="create_user"/>
						    <input id="create_user" type="submit" name="creatUser" value="Создать пользователя"/>
						</form>
						<form class="display_contents_form" id="unknown" action="controller" method="POST">
							<input type="hidden" name="command" value="Database"/>
							<input type="hidden" name="activity" value="update_user"/>
							<input type="hidden" name="login" value="<%= loginValue %>"/>
						    <input type="hidden" name="password" value="<%= passwordValue %>"/>
						    <input type="hidden" name="command_type" value="remove_user"/>
						    <input id="remove_user" type="submit" name="removeUser" value="Удалить пользователя"/>
						</form>
						<form class="display_contents_form" id="unknown" action="controller" method="POST">
							<input type="hidden" name="command" value="Database"/>
							<input type="hidden" name="activity" value="update_user"/>
							<input type="hidden" name="login" value="<%= loginValue %>"/>
						    <input type="hidden" name="password" value="<%= passwordValue %>"/>
						    <input type="hidden" name="command_type" value="find_user"/>
						    <input id="find_user" type="submit" name="findUser" value="Поиск пользователя по логину"/>												
						</form>		
						<input id="clear_form" type="button" name="clearForm" value="Очистить форму" onclick='clearFormData()'/>
					</div>
		        </div>
			</div>
			<div id="right_bar" class="container">
				<div id="full_users_list_box">
					<c:import url="/jsp/general/user_list.jsp"/>
				</div>
			</div>
		</div>
	</div>
</body></html>