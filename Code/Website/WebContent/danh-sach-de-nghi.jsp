<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<title>Gợi ý hệ thống</title>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="header.jsp"/>
        <section class="container">
            <center>
                <div class="form-content" style="border: 1px solid #ccc;box-shadow: 1px 1px 2px 2px #CCC;margin-bottom: 50px;width: 800px;">

                    <div class="form-content">
                        <form action="#" method="post" accept-charset="utf-8" enctype="multipart/form-data" data-abide="" novalidate="novalidate">						
                            <div class="row">
                                <div class="large-12 columns">
                                    <h2 class="page-title"><font color="orange" >Danh sách đề nghị</font></h2>
                                </div>
                                <div class="large-12 columns">
                                    <ul class="tabs" data-tab>
                                        <li class="tab-title active"><a href="#panel1">Đề nghị đã gửi</a></li>
                                        <li class="tab-title"><a href="#panel2">Đề nghị đã nhận</a></li>
                                    </ul>
                                    <div class="tabs-content">
                                        <div class="content active" id="panel1">
                                            <table>
                                                <thead>
                                                    <tr>
                                                        <th ><h4 ><font color="orange" >#</font></h4></th>
                                                        <th width="150"><h3 ><font color="orange" >Địa điểm bắt đầu</font></h3></th>
                                                        <th width="150"><h3 ><font color="orange" >Địa điểm kết thúc</font></h3></th>
                                                        <th width="150"><h3 ><font color="orange" >Gía (ngàn đồng)</font></h3></th>
                                                        <th width="150"><h3 ><font color="orange" >Điểm uy tín</font></h3></th>
                                                        <th ><h4 ><font color="orange" ></font></h4></th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <tr>
                                                        <td>1</td>
                                                        <td>TP.HCM</td>
                                                        <td>Hà Nội</td>
                                                        <td>10.000.000</td>
                                                        <td>95 điểm</td>
                                                        <td><a class="button alert" id="delete" href="#"><i class="icon-remove"></i> Huỷ đề nghị</a></td>
                                                    </tr>
                                                    <tr>
                                                        <td>2</td>
                                                        <td>TP.HCM</td>
                                                        <td>Long Xuyên</td>
                                                        <td>11.000.000</td>
                                                        <td>98 điểm</td>
                                                        <td><a class="button alert" id="delete"href="#"><i class="icon-remove"></i> Huỷ đề nghị</a></td>
                                                    </tr>
                                                </tbody>
                                            </table>
                                        </div>
                                        <div class="content" id="panel2">
                                            <table>
                                                <thead>
                                                    <tr>
                                                        <th ><h4 ><font color="orange" >#</font></h4></th>
                                                        <th width="150"><h3 ><font color="orange" >Địa điểm bắt đầu</font></h3></th>
                                                        <th width="150"><h3 ><font color="orange" >Địa điểm kết thúc</font></h3></th>
                                                        <th width="150"><h3 ><font color="orange" >Gía (ngàn đồng)</font></h3></th>
                                                        <th width="150"><h3 ><font color="orange" >Điểm uy tín</font></h3></th>
                                                        <th ><h4 ><font ></font></h4></th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <tr>
                                                        <td>1</td>
                                                        <td>TP.HCM</td>
                                                        <td>Hải Phòng</td>
                                                        <td>10.000.000</td>
                                                        <td>95 điểm</td>
                                                        <td><a class="button" href="chi-tiet-de-nghi.jsp"><i class="icon-ok"></i> Xem chi tiết</a></td>
                                                    </tr>
                                                    <tr>
                                                        <td>2</td>
                                                        <td>TP.HCM</td>
                                                        <td>Huế</td>
                                                        <td>10.000.000</td>
                                                        <td>98 điểm</td>
                                                        <td><a class="button" href="chi-tiet-de-nghi.jsp"><i class="icon-ok"></i> Xem chi tiết</a></td>
                                                    </tr>
                                                </tbody>
                                            </table>
                                        </div>

                                    </div>

                                </div>


                            </div>
                        </form>


                    </div>

                </div>
                </br>

            </center>

        </section>
 <script>
		function reset () {
			$("#toggleCSS").attr("href", "css/alertify.default.css");
			alertify.set({
				labels : {
					ok     : "Đồng ý",
					cancel : "Không đồng ý"
				},
				delay : 5000,
				buttonReverse : false,
				buttonFocus   : "ok"
			});
		}

		// ==============================
		// Standard Dialogs
	

		$("#update").on( 'click', function () {
			reset();
			alertify.confirm("Bạn có muốn cập nhật hàng không?", function (e) {
				if (e) {
                                    document.location.href="Controller?btnAction=test";  
				} else {
				}
			});
			return false;
		});
		$("#delete").on( 'click', function () {
			reset();
			alertify.confirm("Bạn có muốn xoá đề nghị không?", function (e) {
				if (e) {
					document.location.href="Controller?btnAction=#";  
				} else {
				}
			});
			return false;
		});
	</script>
        <!-- end main content -->
        <jsp:include page="footer.jsp"/>