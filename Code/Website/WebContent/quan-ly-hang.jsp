<%-- 
    Document   : quan-ly-hang
    Created on : Jan 30, 2015, 11:21:10 AM
    Author     : KhuongNguyen-PC
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<title>Quản lý hàng</title>
<jsp:include page="header.jsp"/>
      <section class="container">
            <center>
                <div class="form-content" style="border: 1px solid #ccc;box-shadow: 1px 1px 2px 2px #CCC;margin-bottom: 50px;width: 850px;">

                    <div class="form-content">
                        <form action="#" method="post" accept-charset="utf-8" enctype="multipart/form-data" data-abide="" novalidate="novalidate">						
                            <div class="row">
                                <div class="large-12 columns">
                                    <h2 class="page-title"><font color="orange" >Quản lý hàng</font></h2>
                                </div>
                                <div class="large-12 columns">
                                    <div class="filter-bar">
                                        <form action="#" accept-charset="utf-8" id="frm-list-lading" method="GET">
                                            <div class="row">
                                                <div class="large-3 columns">
                                                    <select class="left">
                                                        <option selected="">Chọn loại hàng</option>
                                                        <option>Điện gia dụng</option>
                                                        <option>Điện gia dụng</option>
                                                        <option>Thực phẩm</option>
                                                        <option>Hi-tech</option>
                                                    </select>
                                                </div>

                                                <div class="large-3 columns">
                                                    <div class="date-picker-wrap">
                                                        <input type="text" value="Từ ngày" name="from_date" placeholder="Từ ngày" class="date-picker"/>
                                                        <i class="icon-calendar"></i>
                                                    </div>
                                                </div>

                                                <div class="large-3 columns date-picker-wrap">
                                                    <div class="date-picker-wrap">
                                                        <input type="text" value="Đến ngày" name="to_date" placeholder="Đến ngày" class="date-picker"/>
                                                        <i class="icon-calendar"></i>
                                                    </div>
                                                </div>

                                                <div class="large-3 columns">
                                                    <button class="js_search_lading"><i class="icon-filter"></i> Lọc</button>
                                                </div>
                                            </div>
                                        </form>

                                    </div>
                                    <div class="large-12 columns">
                                        <ul class="tabs" data-tab>
                                            <li class="tab-title active"><a href="#panel1">Hàng chưa giao dịch</a></li>
                                            <li class="tab-title"><a href="#panel2">Hàng đang giao dich</a></li>
                                        </ul>
                                        <div class="tabs-content">
                                            <div class="content active" id="panel1">
                                                <table  id="example" class="display" cellspacing="0" width="100%">
                                                    <thead>
                                                        <tr>
                                                            <th ><input type="checkbox" class="toggle-all" value="0"></th>
                                                            <th width="125"><font color="orange" >LOẠI HÀNG</font></th>
                                                            <th width="225"><font color="orange" >THỜI GIAN GIAO HÀNG</font></th>
                                                            <th width="250"><font color="orange" >THỜI GIAN NHẬN HÀNG</font></th>
                                                            
                                                            <th width="200" ><h4 ><font color="orange" ></font></h4></th>
                                                        </tr>
                                                    </thead>
                                                    <tbody>
                                                        <tr>
                                                            <td><input type="checkbox" class="toggle-all" value="0"></td>
                                                            <td>May mặc</td>
                                                            <td>16h 15/1/2015</td>
                                                            <td>16h 15/2/2015</td>
                                                            
                                                            <td><a class="button" href="chi-tiet-hang-chua-giao-dich.jsp"><i class="icon-ok"></i> Xem chi tiết</a></td>
                                                        </tr>
                                                        <tr>
                                                            <td><input type="checkbox" class="toggle-all" value="0"></td>
                                                            <td>Điện gia dụng</td>
                                                            <td>16h 15/1/2015</td>
                                                            <td>16h 15/2/2015</td>
                                                            
                                                            <td><a class="button" href="chi-tiet-hang-chua-giao-dich.jsp"><i class="icon-ok"></i> Xem chi tiết</a></td>
                                                        </tr>
                                                    </tbody>
                                                    
                                                </table>
                                            </br>
                                                <div class="pull-left">
                                                    <select name="smart-action" style="width: 180px;margin-right: 10px;">
                                                        <option value="0" >-- Chức năng --</option>

                                                        <option value="cancel" selected="">Xoá nhanh hàng</option>
                                                    </select>
                                                    <a class="button " id="delete">Thực hiện</a>
                                                </div>
                                            </div>
                                            <div class="content" id="panel2">
                                                <table id="example1" class="display" cellspacing="0" width="100%">
                                                    <thead>
                                                       <tr>
                                                            <th width="125"><font color="orange" >STT</font></th>
                                                            <th width="125"><font color="orange" >LOẠI HÀNG</font></th>
                                                            <th width="225"><font color="orange" >THỜI GIAN GIAO HÀNG</font></th>
                                                            <th width="250"><font color="orange" >THỜI GIAN NHẬN HÀNG</font></th>
                                                            
                                                            <th width="200" ><h4 ><font color="orange" ></font></h4></th>
                                                        </tr>
                                                    </thead>
                                                    <tbody>
                                                        <tr>
                                                            <td>1</td>
                                                            <td>May mặc</td>
                                                            <td>16h 15/1/2015</td>
                                                            <td>16h 15/2/2015</td>
                                                            
                                                            <td><a class="button" href="chi-tiet-hang-dang-giao-dich.jsp"><i class="icon-ok"></i> Xem chi tiết</a></td>
                                                        </tr>
                                                        <tr>
                                                            <td>2</td>
                                                            <td>Điện gia dụng</td>
                                                            <td>16h 15/1/2015</td>
                                                            <td>16h 15/2/2015</td>
                                                            
                                                            <td><a class="button" href="chi-tiet-hang-dang-giao-dich.jsp"><i class="icon-ok"></i> Xem chi tiết</a></td>
                                                        </tr>
                                                    </tbody>
                                                    
                                                </table>
                                            </div>

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
			alertify.confirm("Bạn có muốn xoá hàng không?", function (e) {
				if (e) {
					document.location.href="Controller?btnAction=#";  
				} else {
				}
			});
			return false;
		});
	</script>
<jsp:include page="footer.jsp"/>