<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
</head>
<body>
	<div class="top_bar_items">
		${login}
	</div>
	<div id="logout_button" class="top_bar_items">
		<a id="logout_button_ref" href="controller?command=logout">Logout</a><br>
	</div>
</body>
</html>