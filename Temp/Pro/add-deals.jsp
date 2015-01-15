<%-- 
    Document   : add-deals
    Created on : Jan 13, 2015, 1:07:22 PM
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
                <div class="row">
                    <div class="col-md-8 col-sm-6 col-md-offset-2 col-sm-offset-3">
                        <div class="row">
                            <div class="col-sm-4">
                                <h2>Nhắc nhở</h2>
                                » Miêu tả chi tiết sản phẩm<br/>
                                » Tin chỉ quảng cáo cho cửa hàng sẽ không được đăng.<br/>
                                » Đăng tin bằng tiếng Việt có dấu.<br/>
                            </div>
                            <div class="col-sm-8">
                                <h2>Thông tin bài đăng</h2>
                                <h4><b>Tên tựa đề</b></h4>
                                <figure class="note">
                                    Vui lòng nhập tên đầy đủ tên tựa đề</figure>
                                <input type="text" class="form-control" placeholder="Điền tên tựa đề bài đăng" required />
                                <h4><b>Thông tin hàng</b></h4>
                                <div class="row">
                                    <div class="col-sm-6">
                                        <div class="form-group">

                                            <input type="text" class="form-control" placeholder="Khối lượng (Tấn)" required />
                                        </div>
                                    </div>
                                    <div class="col-sm-6">
                                        <div class="form-group">

                                            <select class="form-control" >
                                                <option selected="">Loại hàng</option>
                                                <option >Thịt cá</option>
                                                <option >Rau quả</option>
                                            </select>
                                        </div>
                                    </div>

                                </div>
                                <h4><b>Loại xe</b></h4>
                                <div class="row">
                                    <div class="col-sm-6">
                                        <div class="form-group">
                                            <select name="payload-truck">
                                                <option selected="">Chọn Trọng Tải</option>
                                                <option><16 Tấn</option>
                                                <option><24 Tấn</option>
                                                <option><30 Tấn</option>
                                                <option><34 Tấn</option>
                                                <option><44 Tấn</option>
                                            </select>
                                        </div><!-- /.Payload-->   
                                    </div>
                                    <div class="col-sm-6">
                                        <div class="form-group ">
                                            <select>
                                                <option selected="">Thùng đông lạnh</option>
                                                <option>Không thùng đông lạnh</option>
                                            </select>
                                        </div>
                                    </div>
                                </div>
                                <h4><b>Lộ trình</b></h4>
                                <div class="row">
                                    <div class="col-sm-6">
                                        <div class="form-group">
                                            <input type="text" class="form-control" placeholder="Khởi hành" required/>
                                        </div>
                                    </div>
                                    <div class="col-sm-6">
                                        <div class="form-group">
                                            <input type="text" class="form-control" placeholder="Kết thúc" required/>
                                        </div>
                                    </div>
                                </div>
                                <h4><b>Thời gian</b></h4>

                                <div class="row">
                                    <div class='col-md-6'>
                                        <div class="form-group">
                                            <div class='input-group date' id='datetimepicker9'>
                                                <input type='text' class="form-control" placeholder="Thời gian từ"/>
                                                <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span>
                                                </span>
                                            </div>
                                        </div>
                                    </div>
                                    <div class='col-md-6'>
                                        <div class="form-group">
                                            <div class='input-group date' id='datetimepicker10'>
                                                <input type='text' class="form-control" placeholder="Đến thời gian"/>
                                                <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span>
                                                </span>
                                            </div>
                                        </div>
                                    </div>

                                    <script type="text/javascript">
                                        $(function () {
                                            $('#datetimepicker9').datetimepicker();
                                            $('#datetimepicker10').datetimepicker();
                                            $("#datetimepicker9").on("dp.change", function (e) {
                                                $('#datetimepicker10').data("DateTimePicker").setMinDate(e.date);
                                            });
                                            $("#datetimepicker10").on("dp.change", function (e) {
                                                $('#datetimepicker9').data("DateTimePicker").setMaxDate(e.date);
                                            });
                                        });
                                    </script>
                                </div>
                                <hr/>
                                <div class="form-group clearfix">
                                    <a href="find-driver.jsp"><button type="submit" class="btn pull-left btn-default" id="account-submit">Đăng hàng</button></a> 
                                </div>


                            </div>
                        </div>

                    </div>
                </div>
                <jsp:include page="footer.jsp"/>
            </div>
    </body>
</html>
