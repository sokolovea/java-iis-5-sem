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
		
		
	    function modifyUser(commandType) {
	        // Создаем объект XMLHttpRequest
	        var xhr = new XMLHttpRequest();
			
	        var data = {
				login: "<%= loginValue %>",
				password: "<%= passwordValue %>",
				command: "Database",
				activity: "update_user",
				command_type: commandType, 
				form_login: document.getElementById("user_login").value,
				form_password: document.getElementById("user_password").value,
				form_name: document.getElementById("user_name").value,
				form_email: document.getElementById("user_email").value,
				form_role: document.getElementById("user_role").value
	  	    };
	        console.log(data);
	        
	        // Настраиваем запрос
	        xhr.open('POST', 'controller', true);
	        xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');

	        var formData = [];
	        for (var key in data) {
	          if (data.hasOwnProperty(key)) {
	            formData.push(encodeURIComponent(key) + "=" + encodeURIComponent(data[key]));
	          }
	        }
	        var encodedData = formData.join("&");
	        
	        // Отправляем запрос с идентификатором сообщения
	        xhr.send(encodedData);

	        // Обрабатываем ответ сервера (если нужно)
	        xhr.onreadystatechange = function () {
	            if (xhr.readyState === XMLHttpRequest.DONE) {
	            	location.reload();
	            }
	        };
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
						<input id="save_changes" type="button" name="saveChanges" value="Сохранить" onclick='modifyUser("save_user")'/>
						<input id="create_user" type="button" name="createUser" value="Создать пользователя" onclick='modifyUser("create_user")'/>
						<input id="remove_user" type="button" name="removeUser" value="Удалить пользователя" onclick='modifyUser("remove_user")'/>
						<input id="find_user" type="button" name="findUser" value="Поиск пользователя по логину" onclick='modifyUser("find_user")'/>												
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