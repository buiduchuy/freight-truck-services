<%-- 
    Document   : chi-tiet-hang-chua-giao-dich
    Created on : Jan 31, 2015, 12:18:21 AM
    Author     : KhuongNguyen-PC
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<title>Chi tiết hàng</title>
<jsp:include page="header.jsp"/>

<section class="container">
    <center>
        <div class="form-content" style="border: 1px solid #ccc;box-shadow: 1px 1px 2px 2px #CCC;margin-bottom: 50px;width: 700px;">

            <div class="form-content">
                <form action="#" method="get" accept-charset="utf-8" >						
                    <div class="row">
                        <div class="large-12 columns">
                            <h2 class="page-title"><font color="orange" >Chi tiết hàng</font></h2>
                            <div class="row">
                                <div class="small-3 columns">
                                    <label for="right-label" class="right inline"> </label>                                        
                                </div>
                                <div class="small-6 columns">
                                    <label for="right-label" class="right inline"> </label> 
                                </div>
                                <div class="small-3 columns">
                                    <a class="button success" href="goi-y-he-thong.jsp"><i class="icon-ok"></i> Gợi ý hệ thống</a>
                                    </br>
                                    </br>
                                    </br>
                                </div>
                            </div>


                        </div>




                        <div class="large-12 columns">
                            <div data-alert="" class="alert-box radius secondary">
                                <label class="left"><font color="white" size="+1">Thông tin hàng hoá</font></label>
                                </br>
                            </div>


                            <div class="large-12 columns">
                                <div class="row">
                                    <div class="small-3 columns">
                                        <label for="right-label" class="right inline"><small class="validate">*</small> Loại hàng: </label>                                        
                                    </div>
                                    <div class="small-6 columns">
                                        <select required data-errormessage-value-missing="Vui lòng chọn loại hàng !">
                                            <option selected disabled value="">Chọn loại hàng</option>
                                            <option >May mặc</option>
                                            <option>Điện gia dụng</option>
                                            <option>Thực phẩm</option>
                                            <option>Hi-tech</option>
                                        </select>
                                    </div>
                                    <div class="small-3 columns">
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="small-3 columns">
                                        <label for="right-label" class="right inline"><small class="validate">*</small> Khối lượng: </label>                                        
                                    </div>
                                    <div class="small-6 columns">
                                        <input type="text" id="right-label" onkeypress="return keyPhone(event);"placeholder="Nhập khối lượng hàng"required=""data-errormessage-value-missing="Vui lòng điền khối lượng của hàng !"/>
                                    </div>
                                    <div class="small-3 columns">
                                        <label for="right-label" class="left inline">Tấn</label>  
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="small-3 columns">
                                        <label for="right-label" class="right inline">Ghi chú : </label>                                        
                                    </div>
                                    <div class="small-6 columns">
                                        <textarea  maxlength="250" ></textarea>
                                    </div>
                                    <div class="small-3 columns">
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="large-12 columns">
                            <div data-alert="" class="alert-box radius secondary">
                                <label class="left"><font color="white" size="+1">Thông tin bên giao nhận hàng</font></label>
                                </br>
                            </div>


                            <div class="large-12 columns">


                                <div class="extra-title">
                                    <h3>Địa chỉ giao</h3>
                                </div>
                                <div class="row">
                                    <div class="small-12 columns">
                                        <div class="small-2 columns">
                                            <label for="right-label" class="right inline"><small class="validate">*</small> Địa chỉ: </label>                                        
                                        </div>
                                        <div class="small-10 columns">
                                            <input type="text"  onFocus="geolocate()" id="place_start" pattern=".{1,50}" placeholder="Nhập địa điểm giao hàng" required=""data-errormessage-value-missing="Vui lòng chọn địa điểm giao hàng !" data-errormessage-pattern-mismatch="Bạn phải nhập địa chỉ [1-50] kí tự !"/>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="small-12 columns">
                                        <div class="small-2 columns">
                                            <label for="right-label" class="right inline"><small class="validate">*</small> Giờ: </label>                                        
                                        </div>
                                        <div class="small-3 columns">
                                            <input type="time" id="right-label"  required=""data-errormessage-value-missing="Vui lòng chọn giờ giao hàng !"/>
                                        </div>
                                        <div class="small-2 columns">
                                            <label for="right-label" class="right inline"><small class="validate">*</small> Ngày: </label>                                        
                                        </div>
                                        <div class="small-5 columns">
                                            <input type="date" id="right-label" required=""data-errormessage-value-missing="Vui lòng chọn ngày giao hàng !" />
                                        </div>
                                    </div>
                                </div>


                                <div class="extra-title">
                                    <h3>Địa chỉ nhận</h3>
                                </div>
                                <div class="row">
                                    <div class="small-12 columns">
                                        <div class="small-2 columns">
                                            <label for="right-label" class="right inline"><small class="validate">*</small> Địa chỉ: </label>                                        
                                        </div>
                                        <div class="small-10 columns">
                                            <input type="text" onFocus="geolocate()" id="place_end" pattern=".{1,50}" placeholder="Nhập địa điểm nhận hàng" required=""data-errormessage-value-missing="Vui lòng chọn địa điểm nhận hàng !" data-errormessage-pattern-mismatch="Bạn phải nhập địa chỉ [1-50] kí tự !"/>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="small-12 columns">
                                        <div class="small-2 columns">
                                            <label for="right-label" class="right inline"><small class="validate">*</small> Giờ: </label>                                        
                                        </div>
                                        <div class="small-3 columns">
                                            <input type="time" id="right-label"  required=""data-errormessage-value-missing="Vui lòng chọn giờ nhận hàng !"/>
                                        </div>
                                        <div class="small-2 columns">
                                            <label for="right-label" class="right inline"><small class="validate">*</small> Ngày: </label>                                        
                                        </div>
                                        <div class="small-5 columns">
                                            <input type="date" id="right-label" required=""data-errormessage-value-missing="Vui lòng chọn ngày nhận hàng !" />
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="large-12 columns">
                                <div class="submit-area">
                                    <button class="button " ><i class="icon-ok"></i> Cập nhật hàng</button>
                                    <button class="button  alert" id="delete"><i class="icon-remove"> Xoá hàng</i></button>

                                </div>	
                                </br>
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
                                        function keyPhone(e)
                                        {
                                            var keyword = null;
                                            if (window.event)
                                            {
                                                keyword = window.event.keyCode;
                                            } else
                                            {
                                                keyword = e.which; //NON IE;
                                            }

                                            if (keyword < 48 || keyword > 57)
                                            {
                                                if (keyword == 48 || keyword == 127)
                                                {
                                                    return;
                                                }
                                                return false;
                                            }
                                        }

                                    </script>
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
