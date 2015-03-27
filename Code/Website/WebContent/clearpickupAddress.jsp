<%-- 
    Document   : tao-hang-4
    Created on : Jan 30, 2015, 11:21:10 AM
    Author     : KhuongNguyen-PC
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<title>Tạo hàng</title>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="lat" value="${sessionScope.latpickupAddress }" />
<c:set var="log" value="${sessionScope.lngpickupAddress }" />
<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
<script type="text/javascript"
	src="http://maps.google.com/maps/api/js?sensor=false"></script>
<script type="text/javascript">
var geocoder = new google.maps.Geocoder();

function geocodePosition(pos) {
  geocoder.geocode({
    latLng: pos
  }, function(responses) {
    if (responses && responses.length > 0) {
      updateMarkerAddress(responses[0].formatted_address);
    } else {
      updateMarkerAddress('Cannot determine address at this location.');
    }
  });
}

function updateMarkerStatus(str) {
  document.getElementById('markerStatus').innerHTML = str;
}

function updateMarkerPosition(latLng) {
	
	document.getElementById('textlat').value= latLng.lat();
	document.getElementById('textlng').value= latLng.lng();
}
function updateMarkerAddress(str) {
  document.getElementById('address').innerHTML = str;
}


function myfunction() {
    return latLng.lat();
}
function initialize() {
  var latLng = new google.maps.LatLng(${lat}, ${log});
  var map = new google.maps.Map(document.getElementById('mapCanvas'), {
    zoom: 8,
    center: latLng,
    mapTypeId: google.maps.MapTypeId.ROADMAP
  });
  var marker = new google.maps.Marker({
    position: latLng,
    title: 'Point A',
    map: map,
    draggable: true
  });
  
  // Update current position info.
  updateMarkerPosition(latLng);
  geocodePosition(latLng);
  
  // Add dragging event listeners.
  google.maps.event.addListener(marker, 'dragstart', function() {
    updateMarkerAddress('Dragging...');
  });
  
  google.maps.event.addListener(marker, 'drag', function() {
    updateMarkerStatus('Dragging...');
    updateMarkerPosition(marker.getPosition());
  });
  
  google.maps.event.addListener(marker, 'dragend', function() {
    updateMarkerStatus('Drag ended');
    geocodePosition(marker.getPosition());
  });
}

// Onload handler to fire off the app.
google.maps.event.addDomListener(window, 'load', initialize);
</script>
<style>
#mapCanvas {
	width: 100%;
	height: 400px;
	float: left;
}

#infoPanel {
	float: left;
	margin-left: 10px;
}

#infoPanel div {
	margin-bottom: 5px;
}
</style>
<jsp:include page="header.jsp" />
<div class="large-12 columns">
	<div class="small-3 columns">
		<div class="form-content"
			style="border: 1px solid #ccc; box-shadow: 1px 1px 2px 2px #CCC; margin-bottom: 50px; width: 100%;">
			<jsp:include page="vertical-menu-create-good.jsp" />
			<div class="row"></div>
		</div>
		<div class="form-content "
			style="border: 1px solid #ccc; box-shadow: 1px 1px 2px 2px #CCC; margin-bottom: 50px; width: 100%;">
			<jsp:include page="vertical-pr.jsp" />

		</div>
	</div>
	<div class="small-9 columns">
		<div class="form-content"
			style="border: 1px solid #ccc; box-shadow: 1px 1px 2px 2px #CCC; margin-bottom: 50px; width: 100%;">
			<div class="form-content">
				<form action="GoodsServlet" method="post"
					accept-charset="utf-8">
					<div class="row">
						<div class="large-12 columns">
							<h2 class="page-title">
								<font color="orange">Tạo hàng</font>
							</h2>
						</div>
						<label>Địa chỉ giao : ${router.pickupAddress }</label>
						<div id="mapCanvas"></div>
						<div id="infoPanel">
							<div id="markerStatus" style="display: none;"></div>
							<input type="hidden" name="latpickAddress" id="textlat" readonly="readonly"> <input
								type="hidden" name="lngpickAddress" id="textlng" readonly="readonly">
						</div>






						<div class="large-12 columns">
							<div class="submit-area right">
								<a href="tao-hang-4.jsp" class="button secondary"><i
									class="icon-mail-reply"></i> Trở về</a>
								<button class="success" name="btnAction"
									value="finishClearPickupAddress">
									<i class="icon-ok"></i> Hoàn tất
								</button>
							</div>
						</div>
						<div class="row"></div>
						<div class="row"></div>
				</form>


			</div>
		</div>
	</div>

</div>



<jsp:include page="footer.jsp" />
