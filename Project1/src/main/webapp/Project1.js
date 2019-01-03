/**
 * 
 */
window.onload = function() {
	loadLoginView();
}

function loadLoginView() {
	var xhr = new XMLHttpRequest();
	
	xhr.onreadystatechange = function() {
		if(xhr.readyState == 4 && xhr.status == 200) {
			//do things w/ response
			$('#view').html(xhr.responseText);
			$('#LogIn').on("click",login);
			$('#Register').on("click",loadRegisterView);
		}
	}
	xhr.open("GET", "login.view", true);
	xhr.send();
	
}

function login() {
	var username=$('#username').val();
	var password=$('#password').val();
	var xhr = new XMLHttpRequest();
	var obj = { 
		username: username,
		password: password
	}
    xhr.onreadystatechange = function(){
        if(xhr.readyState == 4 && xhr.status == 200){
        	var user = JSON.parse(xhr.responseText);
        	if (user.id == 0) {
     			loadErrorView();
       		}
       		else if (user.roleID == 1) {
       			loadEmpNav();
       			loadEmpView();
       		}
       		else {
       			loadManNav();
        		loadManView();
        	}
        }
    }
    var toSend = JSON.stringify(obj);
    xhr.open("POST","login",true);
    xhr.setRequestHeader("Content-type","application/json");
    xhr.send(toSend);
}

function loadErrorView() {
	var xhr = new XMLHttpRequest();
	
	xhr.onreadystatechange = function() {
		if(xhr.readyState == 4 && xhr.status == 200) {
			//do things w/ response
			$('#view').html(xhr.responseText);
			$('#Return').on("click",loadLoginView);
			$('#errRegister').on("click",loadRegisterView);
		}
	}
	xhr.open("GET", "error-login.view", true);
	xhr.send();
	
}

function logout() {
	removeNavbar();
	var xhr = new XMLHttpRequest();
	
	xhr.onreadystatechange = function() {
		if(xhr.readyState == 4 && xhr.status == 200) {
			loadLoginView();
		}
	}
	xhr.open("GET", "logout", true);
	xhr.send();
}

/*
 * ###########################################
 * LOAD NAVBAR
 * ###########################################
 */
function loadEmpNav() {
	var xhr = new XMLHttpRequest();
	
	xhr.onreadystatechange = function() {
		if(xhr.readyState == 4 && xhr.status == 200) {
			//do things w/ response
			$('#nav').html(xhr.responseText);
			$('#fileReimbNav').on('click',loadFileReimbView);
			$('#viewPastNav').on('click',loadPastView);
			$('#logOut').on('click',logout);
		}
	}
	xhr.open("GET", "empnav.view", true);
	xhr.send();
	
}

function loadManNav() {
	var xhr = new XMLHttpRequest();
	
	xhr.onreadystatechange = function() {
		if(xhr.readyState == 4 && xhr.status == 200) {
			//do things w/ response
			$('#nav').html(xhr.responseText);
			$('#pendingNav').on('click',loadPendingView);
			$('#oldNav').on('click',loadOldView)
			$('#logoutNav').on('click',logout);
		}
	}
	xhr.open("GET", "mannav.view", true);
	xhr.send();
}

function removeNavbar() {
	var xhr = new XMLHttpRequest();
	
	xhr.onreadystatechange = function() {
		if(xhr.readyState == 4 && xhr.status == 200) {
			//do things w/ response
			$('#nav').html(xhr.responseText);
		}
	}
	xhr.open("GET", "emptynav.view", true);
	xhr.send();
}

/*
 * ###########################################
 * REGISTER VIEW
 * ###########################################
 */

function loadRegisterView() {
	var xhr = new XMLHttpRequest();
	
	xhr.onreadystatechange = function() {
		if(xhr.readyState == 4 && xhr.status == 200) {
			//do things w/ response
			$('#view').html(xhr.responseText);
			$('#RegisterNew').on("click",register);
			$('#Return').on("click",loadLoginView);
		}
	}
	xhr.open("GET", "register.view", true);
	xhr.send();
	
}

function register () {
	var username=$('#newUsername').val();
	var email=$('#newEmail').val();
	var password=$('#newPassword').val();
	var firstName=$('#newFirst').val();
	var lastName=$('#newLast').val();
	var xhr = new XMLHttpRequest();
	var obj = { 
		username: username,
		email: email,
		password: password,
		firstName: firstName,
		lastName: lastName
	}
    xhr.onreadystatechange = function(){
        if(xhr.readyState == 4 && xhr.status == 200){
        	loadLoginView();
        }
    }
    var toSend = JSON.stringify(obj);
    xhr.open("POST","register");
    xhr.setRequestHeader("Content-type","application/json");
    xhr.send(toSend);
}

/*
 * ###########################################
 * MANAGER VIEWS
 * ###########################################
 */
function loadManView() {
	var xhr = new XMLHttpRequest();
	
	xhr.onreadystatechange = function() {
		if(xhr.readyState == 4 && xhr.status == 200) {
			//do things w/ response
			$('#view').html(xhr.responseText);
		}
	}
	xhr.open("GET", "manager.view", true);
	xhr.send();
}

function loadPendingView() {
	var xhr = new XMLHttpRequest();

	xhr.onreadystatechange = function() {
		if (xhr.readyState == 4 && xhr.status == 200) {
			// do things w/ response
			$('#view').html(xhr.responseText);
			loadPendingReimbs();
		}
	}
	xhr.open("GET", "pending.view", true);
	xhr.send();
		
}

function loadPendingReimbs(){
	var xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function() {
		if(xhr.readyState == 4 && xhr.status == 200) {
			let reimbs = JSON.parse(xhr.responseText);
			for (let r of reimbs) {
				let ts = new Date(r.submitted);
				let date = formatDate(ts);
				var th = $(`<tr class="table-light">
					<td>${r.authorln}</td>
					<td>${date}</td>
					<td>${r.description}</td>
					<td>$${r.amount}</td>
					<td>
					<button value ="${r.id}" class = "approve btn btn-success">Approve</button>
					<button value ="${r.id}" class = "deny btn btn-danger">Deny</button>
					</td>
					</tr>`);
				$('#pendReimbList tr:last').after(th);
			}
			$('.approve').on('click',approveReimb);
			$('.deny').on('click',denyReimb);
		}
	}
	xhr.open("GET","pending");
	xhr.send();
}

function approveReimb() {
	var xhr = new XMLHttpRequest();
	var obj = { 
		id: $(this).val(),
		status: 2
	} 
	
    xhr.onreadystatechange = function(){
        if(xhr.readyState == 4 && xhr.status == 200){
        	$('#view').html(xhr.responseText);
        	loadPendingView();
        }
    }
    var toSend = JSON.stringify(obj);
    xhr.open("POST","pending",true);
    xhr.setRequestHeader("Content-type","application/json");
    xhr.send(toSend);
}

function denyReimb() {
	var xhr = new XMLHttpRequest();
	var obj = { 
		id: $(this).val(),
		status: 3
	}
    xhr.onreadystatechange = function(){
        if(xhr.readyState == 4 && xhr.status == 200){
        	$('#view').html(xhr.responseText);
        	loadPendingView();
        }
    }
	var toSend = JSON.stringify(obj);
    xhr.open("POST","pending",true)
    xhr.setRequestHeader("Content-type","application/json")
    xhr.send(toSend);
}

function loadOldView() {
	var xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function() {
		if (xhr.readyState == 4 && xhr.status == 200) {
			// do things w/ response
			$('#view').html(xhr.responseText);
			loadOldReimbs();
		}
	}
	xhr.open("GET", "old.view", true);
	xhr.send();
		
}

function loadOldReimbs(){
	var xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function() {
		if(xhr.readyState == 4 && xhr.status == 200) {
			let reimbs = JSON.parse(xhr.responseText);
			for (let r of reimbs) {
				let ts = new Date(r.submitted);
				let date = formatDate(ts);
				let tr = new Date(r.resolved);
				let dater = formatDate(tr);
				if (r.status=="2") {
					var status = "Approved";
					var th = $(`<tr class="table-success">
							<td>${r.authorln}</td>
							<td>${date}</td>
							<td>${r.description}</td>
							<td>$${r.amount}</td>
							<td>${status}</td>
							<td>${dater}</td>
							</tr>`);
				} 
				else {
					var status = "Denied";
					var th = $(`<tr class="table-danger">
							<td>${r.authorln}</td>
							<td>${date}</td>
							<td>${r.description}</td>
							<td>$${r.amount}</td>
							<td>${status}</td>
							<td>${dater}</td>
							</tr>`);
				}
				$('#oldReimbList tr:last').after(th);
			}
		}
	}
	xhr.open("GET","old");
	xhr.send();
}


/*
 * ###########################################
 * EMPLOYEE VIEWS
 * ###########################################
 */
function loadEmpView() {
	var xhr = new XMLHttpRequest();
	
	xhr.onreadystatechange = function() {
		if(xhr.readyState == 4 && xhr.status == 200) {
			//do things w/ response
			$('#view').html(xhr.responseText);
		}
	}
	xhr.open("GET", "employee.view", true);
	xhr.send();
}

function loadFileReimbView() {
	var xhr = new XMLHttpRequest();

	xhr.onreadystatechange = function() {
		if (xhr.readyState == 4 && xhr.status == 200) {
			// do things w/ response
			$('#view').html(xhr.responseText);
			$('#submit').on('click',addReimb);
		}
	}
	xhr.open("GET", "reimb.view", true);
	xhr.send();
		
}

function addReimb() {
	var amount = $('#Amount').val();
	var type = $('#reimbType').val();
	var description = $('#Desc').val();
	if (type=="Lodging") {
		var type_id=1;
	}
	else if (type=="Travel") {
		var type_id=2;
	}
	else {
		var type_id=3;
	}
	
	var obj = {	
			amount: amount,
			type: type_id,
			description: description
	};
	var toSend = JSON.stringify(obj);
	
	var xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function() {
		if (xhr.readyState == 4) {
			loadFileReimbView();
		}
	}
	xhr.open("POST","reimb");
	xhr.setRequestHeader("Content-type","application/json");
	xhr.send(toSend);
	
}

function loadPastView() {
	var xhr = new XMLHttpRequest();

	xhr.onreadystatechange = function() {
		if (xhr.readyState == 4 && xhr.status == 200) {
			// do things w/ response
			$('#view').html(xhr.responseText);
			loadReimbs();
		}
	}
	xhr.open("GET", "past.view", true);
	xhr.send();
		
}

function loadReimbs(){
	var xhr = new XMLHttpRequest();
	
	xhr.onreadystatechange = function() {
		if(xhr.readyState == 4 && xhr.status == 200) {
			let reimbs = JSON.parse(xhr.responseText);
			for (let r of reimbs) {
				let ts = new Date(r.submitted);
				let date = formatDate(ts);
				if (r.status == "1") {
					var status = "Pending";
					var resolved = "N/A";
					var th = $(`<tr class="table-warning">
							<td>${date}</td>
							<td>${r.description}</td>
							<td>$${r.amount}</td>
							<td>${status}</td>
							<td>${resolved}</td>
							</tr>`);
				}
				else if (r.status =="2") {
					var status = "Approved";
					let tr = new Date(r.resolved);
					let dater = formatDate(tr);
					var th = $(`<tr class="table-success">
							<td>${date}</td>
							<td>${r.description}</td>
							<td>$${r.amount}</td>
							<td>${status}</td>
							<td>${dater}</td>
							</tr>`);
				}
				else {
					var status = "Denied";
					let tr = new Date(r.resolved);
					let dater = formatDate(tr);
					var th = $(`<tr class="table-danger">
							<td>${date}</td>
							<td>${r.description}</td>
							<td>$${r.amount}</td>
							<td>${status}</td>
							<td>${dater}</td>
							</tr>`);
				}
				$('#reimbList tr:last').after(th);
			}
		}
	}
	xhr.open("GET","past");
	xhr.send();
}

/*
 * DATE FORMAT FUNCTION
 * 
 */
var formatDate = function (time) {
    var t = new Date(time);
    return t.getMonth()+1 + "-" + t.getDate() + "-" + t.getFullYear();
 
}

