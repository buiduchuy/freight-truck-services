<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<title>Chi tiết tài xế</title>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="header.jsp"/>
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
    <script src="https://maps.googleapis.com/maps/api/js?v=3.exp&signed_in=true"></script>
    <script>
var directionsDisplay;
var directionsService = new google.maps.DirectionsService();
var map;

function initialize() {
  directionsDisplay = new google.maps.DirectionsRenderer();
  var chicago = new google.maps.LatLng(41.850033, -87.6500523);
  var mapOptions = {
    zoom:7,
    center: chicago
  };
  map = new google.maps.Map(document.getElementById('map-canvas'), mapOptions);
  directionsDisplay.setMap(map);
}

function calcRoute() {
  var start = 'ho chi minh';
  var end = 'ha noi';
  var request = {
      origin:start,
      destination:end,
      travelMode: google.maps.TravelMode.DRIVING
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
                <div class="form-content" style="border: 1px solid #ccc;box-shadow: 1px 1px 2px 2px #CCC;margin-bottom: 50px;width: 800px;">

                    <div class="form-content">
                        <form action="#" method="post" accept-charset="utf-8" enctype="multipart/form-data" data-abide="" novalidate="novalidate">						
                            <div class="row">
                                <div class="large-12 columns">
                                    <h2 class="page-title"><font color="orange" >Chi tiết tài xế</font></h2>
                                </div>

                                <div class="large-12 columns">
                                    <div data-alert="" class="alert-box radius secondary">
                                        <label class="left"><font color="white" size="+1">Thông tin tài xế</font></label>
                                        </br>
                                    </div>
                                    <div class="row">
                                         <div class="row">
                                        <div class="small-3 columns">
                                            <label class="right"></label>
                                        </div>
                                        <div class="small-4 columns">
                                            <label class="left">Địa điểm bắt đầu: Tp.HCM</label>
                                        </div>
                                        <div class="small-5 columns">
                                            <label class="left">Thời gian: 16h 7/1/2015</label>
                                        </div>
                                         </div>
                                      
                                        <div class="row">
                                        <div class="small-3 columns">
                                            <label class="right"></label>
                                        </div>
                                        <div class="small-4 columns">
                                            <label class="left">Địa điểm kết thúc: Hà nội</label>
                                        </div>
                                        <div class="small-5 columns">
                                            <label class="left">Thời gian: 16h 7/1/2015</label>
                                        </div>
                                         </div>
                                         
                                         <div class="row">
                                        <div class="small-3 columns">
                                            <label class="right"></label>
                                        </div>
                                        <div class="small-6 columns">
                                            <label class="left">Khối lượng có thể chở: 12 tấn</label>
                                        </div>
                                        <div class="small-3 columns">
                                            <label class="right"></label>
                                        </div>
                                         </div>
                                         <div class="row">
                                        <div class="small-3 columns">
                                            <label class="right"></label>
                                        </div>
                                        <div class="small-6 columns">
                                            <label class="left">Các hàng không chở: Cá</label>
                                        </div>
                                        <div class="small-3 columns">
                                            <label class="right"></label>
                                        </div>

                                         </div>


                                        </br>
                                    </div>
                                    <div class="row">
                                        <div class="submit-area">

                                            <button class="button"><i class="icon-ok"></i> Gửi để nghị</button>
                                            <a href="goi-y-he-thong.jsp" class="button secondary">
                                                        <i class="icon-mail-reply"></i> Trở về trước
                                                    </a>
                                        </div>

                                        </br>

                                    </div>

                                </div>
                                <div class="large-12 columns">
                                    <div data-alert="" class="alert-box radius secondary">
                                        <label class="left"><font color="white" size="+1">Lộ trình</font></label>
                                        </br>
                                    </div>
                                    <div id="map-canvas"></div>
                                    </br>
                                    </br>
                                </div>




                            </div>
                        </form>


                    </div>

                </div>
                </br>

            </center>

        </section>
<jsp:include page="footer.jsp"/>
