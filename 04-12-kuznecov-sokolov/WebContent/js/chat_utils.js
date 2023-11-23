function restoreMessage(messageId, element) {
    // Создаем объект XMLHttpRequest
    var xhr = new XMLHttpRequest();

    // Настраиваем запрос
    xhr.open('POST', 'controller', true);
    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');

    // Отправляем запрос с идентификатором сообщения
    xhr.send('command=Database&activity=restore_message&messageId=' + messageId);

    // Обрабатываем ответ сервера (если нужно)
    xhr.onreadystatechange = function () {
        if (xhr.readyState === XMLHttpRequest.DONE) {
        	element.parentNode.parentNode.parentNode.classList.remove("deleted_message");
        }
    };
}

function deleteMessage(messageId, element) {
    // Создаем объект XMLHttpRequest
    var xhr = new XMLHttpRequest();

    // Настраиваем запрос
    xhr.open('POST', 'controller', true);
    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');

    // Отправляем запрос с идентификатором сообщения
    xhr.send('command=Database&activity=delete_message&messageId=' + messageId);

    // Обрабатываем ответ сервера (если нужно)
    xhr.onreadystatechange = function () {
        if (xhr.readyState === XMLHttpRequest.DONE) {
        	element.parentNode.parentNode.parentNode.classList.add("deleted_message");
        }
    };
}