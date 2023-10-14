<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="ru-RU"><head><title>Кабинет пользователя/капитана/эксперта</title></head>
<body>
        <div class='reports'>
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
        </div> -->
</body></html>