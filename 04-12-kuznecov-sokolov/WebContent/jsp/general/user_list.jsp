<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<link rel="stylesheet" href="/04-12-kuznecov-sokolov/css/user_list.css">
</head>
<body>
	
	<script>
		function userFilter() {
			  // Получите значение введенного текста из поля фильтра
			  var input = document.getElementById("user_filter_input");
			  var filter = input.value.toLowerCase();
	
			  // Получите все элементы с классом "user" из списка пользователей
			  var users = document.getElementsByClassName("user");
	
			  // Переберите все элементы и скройте тех, которые не соответствуют фильтру
			  for (var i = 0; i < users.length; i++) {
			    var userName = users[i].textContent.toLowerCase();
			    if (userName.indexOf(filter) > -1) {
			      users[i].style.display = "block"; // Отобразите элемент, если он соответствует фильтру
			    } else {
			      users[i].style.display = "none"; // Спрячьте элемент, если он не соответствует фильтру
			    }
			}
		}
		
	</script>
	<div id="full_users_list_caption" class="right_bar_header">
		Список всех пользователей СИС
	</div>
	<div id="users_filter">
		<input id="user_filter_input" class="text_box" type="text" name="userFilter" placeholder="Фильтр" onkeyup="userFilter()"/>
	</div>
	<div id="full_users_list">
		<c:forEach var="user" items="${user_list}">
			<c:if test = "${role.toString() == 'admin'}">
				<div class="user <c:if test="${blockedUsers.contains(user)}">banned_user</c:if>" onclick="insertLoginToTextBox(this)">${user.getLogin()}</div>
			</c:if>
			<c:if test = "${role.toString() == 'moderator'}">
				<div class="user <c:if test="${blockedUsers.contains(user)}">banned_user</c:if>" onmouseenter='showSanctionMenu(event, this, ${user.getId()})' onmouseleave='closeSanctionMenu(event, this)'>${user.getLogin()}</div>
			</c:if>
        </c:forEach>	
	</div>
</body>
</html>