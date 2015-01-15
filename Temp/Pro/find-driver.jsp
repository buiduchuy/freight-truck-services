<%-- 
    Document   : find-driver
    Created on : Jan 13, 2015, 11:30:27 AM
    Author     : KhuongNguyen-PC
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <jsp:include page="header.jsp"/>
    </head>
    <body>
        <div class="wrapper">
            <jsp:include page="menu-ngang.jsp"/>
            <div class="container">
                <div class="row">
                    <div class="col-sm-7">
                        <h2>Thông tin tài xế</h2>
                        <table class="table table-condensed">
                            <tr>
                                <th>#</th>
                                <th>Tên đầy đủ</th>
                                <th>Khởi hành</th>
                                <th>Kết thúc</th>
                                <th>Tình trạng</th>
                                <th><button type="button" class="btn btn-primary btn-lg btn-success" data-toggle="modal" data-target="#myModal">
                                        Deal
                                    </button></th>
                            </tr>
                            <tr>
                                <td>1.</td>
                                <td>Nguyễn Văn A</td>
                                <td>Vị trí A</td>
                                <td>Vị trí B</td>
                                <td>Can deal </td>
                                <td><input type="checkbox"></td>
                            </tr>
                            <tr>
                                <td>2.</td>
                                <td>Nguyễn Văn B</td>
                                <td>Vị trí A</td>
                                <td>Vị trí B</td>
                                <td>Can't deal </td>
                                <td><input type="checkbox" disabled=""></td>
                            </tr>
                            <tr>
                                <td>3.</td>
                                <td>Nguyễn Văn C</td>
                                <td>Vị trí A</td>
                                <td>Vị trí B</td>
                                <td>Can deal </td>
                                <td><input type="checkbox"></td>
                            </tr>
                            <!-- Modal -->
                            <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                                <div class="modal-dialog">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                                            <h4 class="modal-title" id="myModalLabel">Chọn hàng cần deal</h4>
                                        </div>

                                        <div class="modal-body">
                                            <div class="row">

                                                <div class="col-md-6">
                                                    <select>
                                                        <option value="" selected="">Chọn hàng cần deal</option>
                                                        <option value="1">Cần chuyển hàng cá</option>
                                                        <option value="2">Cần chuyển hàng rau</option>
                                                    </select>
                                                </div>
                                                <div class="col-md-6">
                                                    <a href="add-deals.jsp"><button class="btn btn-warnminh">Đăng hàng mới</button></a>  
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="collapse" id="a">
                                                    <div class="well">
                                                        Thông tin hàng <br/>
                                                        Khối lượng:<br/>
                                                        Loại hàng:<br/>
                                                        <a  data-toggle="collapse" href="#b" aria-expanded="false" aria-controls="collapseExample">
                                                            Bắt đầu deal với tài xế
                                                        </a>
                                                        <div class="collapse" id="b">
                                                            <div class="well">
                                                                <div class="row">
                                                                    <div class="col-sm-3">
                                                                        Deal lần 1:
                                                                    </div>
                                                                    <div class="col-md-9">
                                                                        <div class="form-group">
                                                                            <label class="sr-only" for="exampleInputAmount">Amount (in dollars)</label>
                                                                            <div class="input-group">
                                                                                <div class="input-group-addon">$</div>
                                                                                <input type="text" class="form-control" id="exampleInputAmount" placeholder="Amount">
                                                                                <div class="input-group-addon">.00</div>
                                                                            </div>

                                                                            <a class="btn btn-primary" data-toggle="collapse" href="#c" aria-expanded="false" aria-controls="collapseExample">
                                                                                Ra giá
                                                                            </a>
                                                                        </div>

                                                                    </div>
                                                                    <div class="collapse" id="c">
                                                                        <div class="well">

                                                                            Nguyễn Văn A:  xxxxx<button type="button" class="btn btn-default" data-dismiss="modal">Đóng</button>
                                                                            <button type="button" class="btn btn-primary">Đồng ý deal</button><br/>
                                                                            <br/>Nguyễn Văn C:  xxxxx<button type="button" class="btn btn-default" data-dismiss="modal">Đóng</button>
                                                                            <button type="button" class="btn btn-primary">Đồng ý deal</button>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>

                                                        </div>
                                                    </div>

                                                </div>
                                            </div>
                                        </div>
                                        <div class="modal-footer">
                                            <a  data-toggle="collapse" href="#a" aria-expanded="false" aria-controls="collapseExample">
                                                Cần chuyển hàng cá
                                            </a>

                                            <button type="button" class="btn btn-default" data-dismiss="modal">Đóng</button>

                                        </div>
                                    </div>
                                </div>
                            </div>









                        </table>
                    </div>
                    <div class="col-sm-5">
                        <h2>Tìm kiếm thông tin tài xế</h2>

                        <form action="#" method="">




                            <div class="form-group">
                                <input type="text" class="form-control" placeholder="Tên tài xế" required/>
                            </div>
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
                                <button type="submit" class="btn pull-left btn-default" id="account-submit">Tìm tài xế</button>
                            </div>
                    </div>
                </div>
                <jsp:include page="footer.jsp"/>
            </div>
        </div>
    </body>
</html>
