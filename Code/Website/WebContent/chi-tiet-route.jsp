<%-- 
    Document   : chi-tiet-route
    Created on : Jan 30, 2015, 10:58:01 PM
    Author     : KhuongNguyen-PC
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<title>Chi tiết tài xế</title>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="detailRoute" value="${sessionScope.viewDetailRoute }" />
<jsp:include page="header.jsp" />
<style>
html, body, #map-canvas {
	height: 100%;
	margin: 0px;
	padding: 0px
}

#panel {
	position: absolute;
	top: 5px;
	left: 50%;
	margin-left: -180px;
	z-index: 5;
	background-color: #fff;
	padding: 5px;
	border: 1px solid #999;
}
</style>
<script
	src="https://maps.googleapis.com/maps/api/js?v=3.exp&signed_in=true"></script>
<script>
	var directionsDisplay;
	var directionsService = new google.maps.DirectionsService();
	var map;

	function initialize() {
		directionsDisplay = new google.maps.DirectionsRenderer();
		var chicago = new google.maps.LatLng(41.850033, -87.6500523);
		var mapOptions = {
			zoom : 7,
			center : chicago
		};
		map = new google.maps.Map(document.getElementById('map-canvas'),
				mapOptions);
		directionsDisplay.setMap(map);
	}

	function calcRoute() {
		var start = '533 Huỳnh Văn Bánh, 14, Phú Nhuận';
		var end = 'Hanoi, Vietnam';
		var request = {
			origin : start,
			destination : end,
			travelMode : google.maps.TravelMode.DRIVING
		};
		directionsService.route(request, function(response, status) {
			if (status == google.maps.DirectionsStatus.OK) {
				directionsDisplay.setDirections(response);
			}
		});
	}

	google.maps.event.addDomListener(window, 'load', initialize);
	google.maps.event.addDomListener(window, 'load', calcRoute);
</script>
<style>
#map-canvas {
	width: 100%;
	height: 300px;
}
</style>
<section class="container">
	<center>
		<div class="form-content"
			style="border: 1px solid #ccc; box-shadow: 1px 1px 2px 2px #CCC; margin-bottom: 50px; width: 800px;">

			<div class="form-content">
				<c:choose>
					<c:when test="${not empty detailRoute }">
						<form action="#" method="post" accept-charset="utf-8"
							enctype="multipart/form-data" data-abide=""
							novalidate="novalidate">
							<div class="row">
								<div class="large-12 columns">
									<h2 class="page-title">
										<font color="orange">Chi tiết lộ trình</font>
									</h2>
								</div>
								<c:set var="error" value="${sessionScope.errorSendSuggest}" />
								<c:if test="${not empty error}">
									<font color="red">${error}</font>
								</c:if>
								<div class="large-12 columns">
									<div data-alert="" class="alert-box radius secondary">
										<label class="left"><font color="white" size="+1">Thông
												tin lộ trình</font></label> </br>
									</div>
									<div class="row">
										<div class="small-4 columns ">
											<label class="right inline">Địa điểm bắt đầu:</label>
										</div>
										<div class="small-6 columns left">
											<input type="text" id="startAddress" class="left inline"
												value="${detailRoute.startingAddress }" readonly="readonly">
										</div>
									</div>
									<div class="row">
										<div class="small-4 columns ">
											<label class="right inline">Thời gian bắt đầu:</label>
										</div>
										<div class="small-6 columns left">
											<input type="text" class="left inline"
												value="${detailRoute.startTime }" readonly="readonly">
										</div>
									</div>
									<div class="row">
										<div class="small-4 columns ">
											<label class="right inline">Địa điểm kết thúc:</label>
										</div>
										<div class="small-6 columns left">
											<input type="text" id="endAddress" class="left inline"
												value="${detailRoute.destinationAddress }"
												readonly="readonly">
										</div>
									</div>
									<div class="row">
										<div class="small-4 columns ">
											<label class="right inline">Thời gian kết thúc:</label>
										</div>
										<div class="small-6 columns left">
											<input type="text" class="left inline"
												value="${detailRoute.finishTime }" readonly="readonly">
										</div>
									</div>
									<div class="row">
										<div class="small-4 columns ">
											<label class="right inline">Khối lượng có thể chở:</label>
										</div>
										<div class="small-6 columns left">
											<input type="text" class="left inline"
												value="${detailRoute.weight } Kg" readonly="readonly">
										</div>
									</div>
									<div class="row">
										<div class="small-4 columns ">
											<label class="right inline">Ghi chú:</label>
										</div>
										<div class="small-6 columns left">
											<input type="text" class="left inline"
												value="${detailRoute.notes }" readonly="readonly">
										</div>
									</div>




									<div class="row">
										<div class="submit-area">
											<a href="goi-y-he-thong.jsp" class="button secondary"> <i
												class="icon-mail-reply"></i> Trở về trước
											</a> <a class="button success"
												href="ControllerMakeDeal?btnAction=sendSuggest&routeID=${detailRoute.routeID }">
												<i class="icon-envelope"></i> Gửi để nghị
											</a>

										</div>

										</br>

									</div>

								</div>
								<div class="large-12 columns">
									<div data-alert="" class="alert-box radius secondary">
										<label class="left"><font color="white" size="+1">Lộ
												trình</font></label> </br>
									</div>
									<div id="map-canvas"></div>
									</br> </br>
								</div>




							</div>
						</form>
					</c:when>
					<c:otherwise>
						<jsp:forward page="goi-y-he-thong.jsp" />
					</c:otherwise>
				</c:choose>


			</div>

		</div>
		</br>

	</center>

</section>
<jsp:include page="footer.jsp" />
