
var latestId = 0;
var timeoutHide = 10000;


setInterval(function() {
	getNewestNotify();
}, 15000);

function getNewestNotify() {
	var _email = document.getElementById("email").value;
		$.ajax({
			url : "/FTS/api/Notification/getNotificationByEmail",
			type : "POST",
			crossDomain : true,
			data : {
				email : _email,
				latestId: latestId
			},
			dataType : "json",
			cache : false,
			success : function(result) {

				var json = JSON.stringify(result);
				//alert(json);
				json = JSON.parse(json); 
				
				var size = json.notification.length;
				if (size >1 ) {
					$.each(json.notification, function(i, e) {
						//var el = $('<div class="notify-item"><a href="ProcessServlet?action'+e.url+'"'+e.message+'</a></div>');
						var el;
						if (e.type == "deal") {
							el = $('<div class="notify-item-deal"><a style="color:#3C763D" href="ProcessServlet?btnAction=viewDetailDeal&dealID='+e.idOfType+'">'+e.message+'</a></div>');
						}
						if (e.type == "order") {
							el = $('<div class="notify-item-order"><a style="color:#31708F" href="ProcessServlet?btnAction=viewDetailOrder&orderID='+e.idOfType+'">'+e.message+'</a></div>');
						}
						$('#notify').append(el);
						setTimeout(function() {
							$(el).fadeOut('slow');
						}, timeoutHide);
					});
					
					latestId = json.notification[json.notification.length-1].notificationID;
				} else {
					var type = json.notification.type;
					var idOfType = json.notification.idOfType;
					var message = json.notification.message;
					var el;
					if (type == "deal") {
						el = $('<div class="notify-item-deal"><a style="color:#3C763D" href="ProcessServlet?btnAction=viewDetailDeal&dealID='+idOfType+'">'+message+'</a></div>');
					}
					if (type == "order") {
						el = $('<div class="notify-item-order"><a style="color:#31708F" href="ProcessServlet?btnAction=viewDetailOrder&orderID='+idOfType+'">'+message+'</a></div>');
					}
					$('#notify').append(el);
					setTimeout(function() {
						$(el).fadeOut('slow');
					}, timeoutHide);
				}
			},
			error : function(xhr, status, error) {
				alert(status);
			}
		});
}
