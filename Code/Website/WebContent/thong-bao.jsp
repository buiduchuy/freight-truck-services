<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Hello jQuery</title>
<script src="js/jquery.js"></script>
<script type="text/javascript">
	$(document)
			.ready(
					function() {
						$("#driver")
								.click(
										function(event) {
											$
													.ajax({
														url : "/FTS/api/Notification/getNotificationByEmail",
														type : "POST",
														crossDomain : true,
														data : {
															email : "jay"
														},
														dataType : "json",
														cache : false,
														success : function(
																result) {
															if (result != null) {
																var json = JSON
																		.stringify(result);

																json = JSON
																		.parse(json); // after receiving make a object from it
																var size = json.notification.length;

																for (i = 0; i < size; i++) {
																	var noti = json.notification[i];
																	var mess = noti.message;
																	var notiID = noti.notificationID;
																	var createTime = noti.createTime;
																	var type = noti.type;
																	var content;

																	if (type == "deal") {
																		content += '<br/><font size="3" color="red"> <a href="/FTS/GoodsServlet?btnAction=viewDetailGood1&idGood=110">'
																				+ mess
																				+ '</a> loại thông báo '
																				+ type
																				+ '</font>';
																	} else if (type == "order") {
																		content += '<br/><font size="3" color="blue"> <a href="/FTS/GoodsServlet?btnAction=viewDetailGood1&idGood=110">'
																				+ mess
																				+ '</a> loại thông báo '
																				+ type
																				+ '</font>';
																	}

																}

																document
																		.getElementById("stage").innerHTML = content;
															}

														},
														error : function(xhr,
																status, error) {
															alert(status);
														}
													});
										});
					});
</script>
</head>
<body>
	<p>Click on the button to load result.html file:</p>
	<div id="stage">STAGE</div>
	<input type="button" id="driver" value="Load Data" />
</body>
</html>