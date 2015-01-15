<%-- 
    Document   : tracking
    Created on : Jan 13, 2015, 1:25:38 PM
    Author     : KhuongNguyen-PC
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <jsp:include page="header.jsp"/>
    </head>
    <body class="page-homepage map-google" id="page-top" data-spy="scroll" data-target=".navigation" data-offset="90">
        <div class="wrapper">
            <jsp:include page="menu-ngang.jsp"/>
            <div class="container">
            </div><!-- /#Find My Location -->


            <div class="row">
                <div class="col-sm-8">
                    <div id="map-canvas"></div>
                </div>
                <div class="col-sm-4">
                    <h2>Tìm kiếm thông tin tài xế</h2>

                    <form action="#" method="">
                       
                        <h4><b>Theo dõi tuyến đường</b></h4>
                        <figure class="note">
                            Điền số điện thoại của tài xế bạn muốn theo dõi.</figure>
                        <div class="form-group">
                            <input type="email" class="form-control" placeholder="Điền số điện thoại" required/>
                        </div>
                        <hr/>
                        <div class="form-group clearfix">
                            <button type="submit" class="btn pull-left btn-default" id="account-submit">Hiện tuyến đường đang chạy</button>
                        </div>
                </div>
            </div>

            <style>
                #map-canvas {
                    width: 100%;
                    height: 500px;
                }
            </style>
            <script src="https://maps.googleapis.com/maps/api/js"></script>
            <script>
                function initialize() {
                    var mapCanvas = document.getElementById('map-canvas');
                    var mapOptions = {
                        center: new google.maps.LatLng(10.768451, 106.6943626),
                        zoom: 8,
                        mapTypeId: google.maps.MapTypeId.ROADMAP
                    }
                    var map = new google.maps.Map(mapCanvas, mapOptions)
                }
                google.maps.event.addDomListener(window, 'load', initialize);
            </script>
            <!-- /#Search -->

            <jsp:include page="footer.jsp"/>
        </div>
        <jsp:include page="footer-sub1.jsp"/>
    </body>
</html>
