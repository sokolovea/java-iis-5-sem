<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html lang="ru-RU"><head><title>Кабинет пользователя/капитана/эксперта</title></head>
<body>
	<div class="window">
		<form name="loginForm" method="POST" action="controller">
			<div id="top_bar" class="container">
				<a href="controller?command=logout">Logout</a><br>
			</div>
			<div id="main_content_bar">
				<div id="left_bar" class="container">
					<div class='additional_buttons'>
						<input type="button" value="Выйти из &#13;&#10; команды"/><br>
						<input type="button" value="Отчеты"/><br>
						<input type="button" value="Меню выбора &#13;&#10; эксперта капитаном"/><br>
						<input type="button" value="Отказаться от консультирования (капитан)"/>
					</div>
				</div>
				<div id="center_bar" class="container">
					<div class='chat'>
						<div>user1: Всем привет! [12:18:30]</div>
						<div>user2: Здорова всем!!!))) [12:18:53]</div>
						<div>captain: Коллеги, не отвлекаемся! Через минуту начинается игра [12:19:24]</div>
						<div>captain: @expert, сколько лет было Пушкину на момент дуэли с Дантесом? [12:22:02]</div>
						
					</div>
					<p>Ваше сообщение:
						<input type="form" name="message" size="10"></input>
					</p>
					<input type="button" value="Отправить"/>
					<input type="button" value="Удалить выделенное сообщение"/>
				</div>
				<div id="right_bar" class="container">
					<div class='user_list'>
						<div id="team_list">Список пользователей команды (возможно, лучше сделать в таблице)</div>
					</div>
					<div class='expert_block'>
						Ваш эксперт: ...
						<br>
						
					</div>	
				</div>
			</div>
		</form>
	</div>
	
	<style>
  
	.chat {
		border-bottom: 2px solid black;
		height: 600px;
	}
  
	.user_list {
		height: 300px;
		border-bottom: 2px solid black;
		margin-bottom: 40px;
	}
  
	input[type=button] {
		width:200px;
	}
  
	body {
		display: flex;
		justify-content: center;
	}
  
	.window {
		--main_height: 95%;
		--top_bar_height: 70px;
		--min_left_bar_w: 150px;
		--min_right_bar_w: 150px;
		--min_center_bar_w: 400px;
		
		width: 95%;
		min-width: calc(var(--min_left_bar_w) + var(--min_right_bar_w) + var(--min_center_bar_w));
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
		height: calc(100% - var(--top_bar_height));
		flex-flow: row nowrap;
		border: 2px solid red;
		justify-content: stretch;
	}
	
	
	#top_bar {
		flow-grow: 0;
		width: 100%;
		height: var(--top_bar_height);
		border: 2px solid blue;
	}
	
	#left_bar {
		width: 15%;
		min-width: var(--min_left_bar_w);
	}
	
	#right_bar {
		width: 15%;
		min-width: var(--min_right_bar_w);
	}
	
	#center_bar {
		width: 70%;
		flex-shrink: 10;
		min-width: var(--min_center_bar_w);
	}
	</style>
</body></html>