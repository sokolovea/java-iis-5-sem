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

	if (document.getElementById("user_login").value == ""){
		alert("Введите логин пользователя");
		return;
	}
	
	if (document.getElementById("user_password").value == "" && commandType != "remove_user"){
		alert("Введите пароль пользователя");
		return;
	}
	
	var xhr = new XMLHttpRequest();
	
    var data = {
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
    
    xhr.open('POST', 'controller', true);
    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');

    var formData = [];
    for (var key in data) {
      if (data.hasOwnProperty(key)) {
        formData.push(encodeURIComponent(key) + "=" + encodeURIComponent(data[key]));
      }
    }
    var encodedData = formData.join("&");
    
    xhr.send(encodedData);

    xhr.onreadystatechange = function () {
        if (xhr.readyState === XMLHttpRequest.DONE) {
        	location.reload();
        }
    };
}

function findUserData(commandType) {

	if (document.getElementById("user_login").value == ""){
		alert("Введите логин пользователя");
		return;
	}
	
	var xhr = new XMLHttpRequest();
	
    var data = {
		command: "Database",
		activity: "update_user",
		command_type: commandType, 
		form_login: document.getElementById("user_login").value
    };
    
    var formData = [];
    for (var key in data) {
      if (data.hasOwnProperty(key)) {
        formData.push(encodeURIComponent(key) + "=" + encodeURIComponent(data[key]));
      }
    }
    var encodedData = formData.join("&");
    
    xhr.open('GET', 'controller?' + encodedData, true);
    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');

    xhr.send();

    xhr.onreadystatechange = function () {
        if (xhr.readyState === XMLHttpRequest.DONE) {
        	var redirectPage = xhr.responseText;
    		
    		document.open();
    		document.write(redirectPage);
    		document.close();
        }
    };
}