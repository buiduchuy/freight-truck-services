var _lastedID = 0;
setInterval(function() {
	getNewestNotify();
	getListNotification();
}, 20000);

function getLastID() {
	var _email = document.getElementById("email").value;
	$.ajax({
		url : "/FTS/api/Notification/getNotificationByEmail",
		type : "POST",
		crossDomain : true,
		data : {
			email : _email
		},
		dataType : "json",
		cache : false,
		success : function(result) {
			if (result != null) {
				var json = JSON.stringify(result);
				// alert(json)
				json = JSON.parse(json);
				var size = json.notification.length;
				if (size > 1) {
					_lastedID = json.notification[0].notificationID;
					// alert(_lastedID)
				} else {
					_lastedID = json.notification.notificationID;
				}
			}
		}
	// ,
	// error : function(xhr, status, error) {
	// alert(status);
	// }
	});
}

function deactiveNotification(_notificationID) {
	$.ajax({
		url : "/FTS/api/Notification/deactivateNotification",
		type : "POST",
		crossDomain : true,
		data : {
			notificationID : _notificationID
		},
		dataType : "json",
		cache : false,
		success : function(result) {

		}
//		error : function(xhr, status, error) {
//			alert(status);
//		}
	});
}

function getListNotification() {
	var _email = document.getElementById("email").value;
	$.ajax({
		url : "/FTS/api/Notification/getNotificationByEmail",
		type : "POST",
		crossDomain : true,
		data : {
			email : _email
		},
		dataType : "json",
		cache : false,
		success : function(result) {
			if (result != null) {

				var json = JSON.stringify(result);
				json = JSON.parse(json);
				var size = json.notification.length;
				var notificationList = new Array();
				$
				.each(
						json.notification,
						function(i, e) {
							// var el = $('<div
							// class="notify-item"><a
							// href="ProcessServlet?action'+e.url+'"'+e.message+'</a></div>');
							var el;
							if (e.type == "deal") {
								el = $('<li><a onclick="deactiveNotification('
										+ e.notificationID
										+ ')" href="ProcessServlet?btnAction=viewDetailDeal&dealID='
										+ e.idOfType
										+ '">'
										+ e.message
										+ '</a></li>');
							}
							if (e.type == "order") {
								el = $('<li><a onclick="deactiveNotification('
										+ e.notificationID
										+ ')" style="color:red;" href="ProcessServlet?btnAction=viewDetailOrder&orderID='
										+ e.idOfType
										+ '">'
										+ e.message
										+ '</a></li>');
							}
							notificationList.push(el);
						});
				$('#notificationList').html(notificationList);
			}
		}
//		error : function(xhr, status, error) {
//			alert(status);
//		}
	});
}

function getNewestNotify() {
	var _email = document.getElementById("email").value;
	var timeoutHide = 15000;

	$
			.ajax({
				url : "/FTS/api/Notification/getNewestNotificationByEmail",
				type : "POST",
				crossDomain : true,
				data : {
					email : _email,
					lastedID : _lastedID
				},
				dataType : "json",
				cache : false,
				success : function(result) {

					if (result != null) {

						var json = JSON.stringify(result);
						json = JSON.parse(json);
						var size = json.notification.length;
						if (size > 1) {
							$
									.each(
											json.notification,
											function(i, e) {
												// var el = $('<div
												// class="notify-item"><a
												// href="ProcessServlet?action'+e.url+'"'+e.message+'</a></div>');
												var el;
												if (e.type == "deal") {
													el = $('<div class="notify-item-deal"><a onclick="deactiveNotification('
															+ e.notificationID
															+ ')" style="color:#3C763D" href="ProcessServlet?btnAction=viewDetailDeal&dealID='
															+ e.idOfType
															+ '">'
															+ e.message
															+ '</a></div>');
												}
												if (e.type == "order") {
													el = $('<div class="notify-item-order"><a onclick="deactiveNotification('
															+ e.notificationID
															+ ')" style="color:#31708F" href="ProcessServlet?btnAction=viewDetailOrder&orderID='
															+ e.idOfType
															+ '">'
															+ e.message
															+ '</a></div>');
												}
												$('#notify').append(el);
												setTimeout(function() {
													$(el).fadeIn('slow')
															.fadeOut('slow');
												}, timeoutHide);
											});
							_lastedID = json.notification[json.notification.length - 1].notificationID;
						} else {
							var notificationID = json.notification.notificationID;
							var type = json.notification.type;
							var idOfType = json.notification.idOfType;
							var message = json.notification.message;
							var el;
							if (type == "deal") {
								el = $('<div class="notify-item-deal"><a onclick="deactiveNotification('
										+ notificationID
										+ ')" style="color:#3C763D" href="ProcessServlet?btnAction=viewDetailDeal&dealID='
										+ idOfType
										+ '">'
										+ message
										+ '</a></div>');
							}
							if (type == "order") {
								el = $('<div class="notify-item-order"><a onclick="deactiveNotification('
										+ notificationID
										+ ')" style="color:#31708F" href="ProcessServlet?btnAction=viewDetailOrder&orderID='
										+ idOfType
										+ '">'
										+ message
										+ '</a></div>');
							}
							$('#notify').append(el);
							setTimeout(function() {
								$(el).fadeOut('slow');
							}, timeoutHide);
							_lastedID = json.notification.notificationID;
						}
					}
				}
//				error : function(xhr, status, error) {
//					alert(status);
//				}
			});
}

$(document).ready(function() {
	$("#notificationLink").click(function() {
		$("#notificationContainer").fadeToggle(300);
		$("#notification_count").fadeOut("slow");
		return false;
	});

	// Document Click
	$(document).click(function() {
		$("#notificationContainer").hide();
	});
	// Popup Click
	$("#notificationContainer").click(function() {
		return false
	});

});
