<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<html lang="ru-RU"><head><title>Выбор команды</title></head>
<body>
	<div class="window">
		<form name="loginForm" method="POST" action="controller">
			<div id="top_bar" class="container">
				<a href="controller?command=login">Logout</a><br>
			</div>
			<div id="main_content_bar">
				<div id="left_bar" class="container">
					<input type="button" value="Отчеты"/><br>
					<a href="controller?login=${userName}&role=${userRole}&command=Report">-Отчеты-</a>
					    <input type="submit" value="Вернуться в команду" />
					</form>
				</div>
				<div id="center_bar" class="container">
					<div>Создать команду<br/>
						Введите название команды: <input type="form"/><br/>
						<input type="button" value="Создать"/>
					</div>
					Список команд
				</div>
				<div id="right_bar" class="container">
					Список команд в которые вступил пользователь
				</div>
			</div>
		</form>
	</div>
	
	
	
	<style>
  
	input[type=button] {
		width:200px;
	}
  
	body {
		display: flex;
		justify-content: center;
	}
  
	.window {
		--main_height: 900px;
		--top_bar_height: 70px;
		--min_left_bar_w: 150px;
		--min_right_bar_w: 150px;
		--min_center_bar_w: 400px;
		
		width: 95%;
		min-width: 700px;
		height: var(--main_height);
		border: 4px solid black;
		padding: 10px;
	}
	
	.container {
		background: pink;
		height: 100%;
		border: 1px solid blue;
	}
	
	#main_content_bar {
		width: 100%;
		display: flex;
		height: calc(var(--main_height) - var(--top_bar_height));
		flex-flow: row nowrap;
		border: 2px solid red;
	}
	
	
	#top_bar {
		flow-grow: 0;
		width: 100%;
		height: 70px;
		border: 2px solid blue;
	}
	
	#left_bar {
		width: 15%;
	}
	
	#right_bar {
		width: 15%;
	}
	
	#center_bar {
		width: 70%;
		flex-shrink: 10;
	}
	</style>
	
	
</body>
</html>