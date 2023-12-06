

window.onload = function() {
	scrollToBottom();
};



function sendSanctionForUser(sanction, reason) {
    var xhr = new XMLHttpRequest();

    xhr.open('POST', 'controller', true);
    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');

    var user_id = document.getElementById("user_id_for_sanction").value;
    xhr.send('command=Database&activity=add_sanction&sanction=' + sanction + '&user_id=' + user_id + '&reason=' + reason);
    console.log(reason);

    // Обрабатываем ответ сервера
    xhr.onreadystatechange = function () {
        if (xhr.readyState === XMLHttpRequest.DONE) {
        	var users = document.getElementsByClassName("user");
        	const filter = ", " + user_id  + ")";
        	for (var i = 0; i < users.length; i++) {
        		var userIdStr = users[i].getAttribute('onmouseenter');
        		if (userIdStr.indexOf(filter) > -1) {
        			if (sanction === 'Block') {
        				users[i].classList.add("banned_user");
        			}
        			else {
        				users[i].classList.remove("banned_user");
        			}
        		}
        	}
        }
    };
}

function showSanctionMenu(event, element, user_id) {
	document.getElementById("user_id_for_sanction").value = user_id;
	const menu = document.getElementById("user_sanction");
	const item_pos = element.getBoundingClientRect();
	menu.style.opacity = 1;
	menu.style.pointerEvents = "auto";
	menu.style.top = document.body.scrollTop + item_pos.top - menu.offsetHeight / 2 + element.offsetHeight / 2;
	menu.style.left = item_pos.left - menu.offsetWidth + 10;
}


function closeSanctionMenu(event, element) {
	const menu = document.getElementById("sanc_triangle");
	const item_pos = element.getBoundingClientRect();
	if (event.relatedTarget != menu) {
		document.getElementById("user_sanction").style.opacity = 0;
		document.getElementById("user_sanction").style.pointerEvents = "none";
	}
}


function closeSancMenu() {
	const menu = document.getElementById("user_sanction");
	menu.style.opacity = 0;
	menu.style.pointerEvents = "none";
}


function chatFilter() {
	var input = document.getElementById("chat_filter_input");
	var filter = input.value.toLowerCase();

	var teams = document.getElementsByClassName("team");
	
	for (var i = 0; i < teams.length; i++) {
		var teamName = teams[i].textContent.toLowerCase();
		if (teamName.indexOf(filter) > -1) {
			teams[i].style.display = "block";
		} else {
			teams[i].style.display = "none";
		}
	}
}


function showAll() {
	var messages = document.getElementsByClassName("message");
	for (var i = 0; i < messages.length; i++) {
		messages[i].style.display = "flex"; 
	}
	scrollToBottom();
}
	

function chatMessageFilter(element) {
	console.log(element.innerHTML);
	var filter = element.innerHTML.toLowerCase();
	
	var messages = document.getElementsByClassName("message");

	for (var i = 0; i < messages.length; i++) {
		var messageTeamName = messages[i].getElementsByClassName("message_team")[0].textContent.toLowerCase();
		if (messageTeamName.indexOf(filter) > -1) {
			messages[i].style.display = "flex"; 
		} else {
			messages[i].style.display = "none";
		}
	}
	scrollToBottom();
}






function openDialog(title) {
	const favDialog = document.getElementById('sanctionReasonDialog');
	favDialog.style.display = 'flex';
	favDialog.querySelector("#reasonDialogHeader").innerText = title;
	favDialog.showModal();
}

function sendSanctionRequest() {
	const sanction = document.getElementById('reasonDialogHeader').innerText;
	const reason = document.getElementById('reasonValue').value;
	sendSanctionForUser(sanction, reason);
	closeDialog();
}

function closeDialog() {
	const favDialog = document.getElementById('sanctionReasonDialog');
	favDialog.style.display = 'none';
	favDialog.close();
}


function inputKeyPressHandler(event) {
    if (event.key === 'Enter') {
    	sendSanctionRequest();
    }
    if (event.key === 'Escape') {
    	closeDialog();
    }
}