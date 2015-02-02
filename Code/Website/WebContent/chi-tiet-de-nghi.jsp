<%-- 
    Document   : chi-tiet-de-nghi
    Created on : Jan 31, 2015, 12:01:24 AM
    Author     : KhuongNguyen-PC
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<title>Chi tiết đề nghị</title>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="header.jsp"/>
<section class="container">
    <center>
        <div class="form-content" style="border: 1px solid #ccc;box-shadow: 1px 1px 2px 2px #CCC;margin-bottom: 50px;width: 700px;">

            <div class="form-content">
                <form action="Controller" method="get" accept-charset="utf-8" >						
                    <div class="row">
                        <div class="large-12 columns">
                            <h2 class="page-title"><font color="orange" >Chi tiết đề nghị</font></h2>



                        </div>



                        <div class="large-12 columns">
                            <div data-alert="" class="alert-box radius secondary">
                                <label class="left"><font color="white" size="+1">Lịch sử đề nghị</font></label>
                                </br>
                            </div>
                            <table>
                                <thead>
                                    <tr>
                                        <th ><h3 ><font color="orange" >THỜI GIAN</font></h3></th>
                                <th ><h3 ><font color="orange" >NGƯỜI GỬI</font></h3></th>
                                <th ><h3 ><font color="orange" >GIÁ (NGÀN ĐỒNG)</font></h3></th>
                                <th ><h3 ><font color="orange" >GHI CHÚ</font></h3></th>
                                </tr>
                                </thead>
                                <tbody>
                                    <tr>
                                        <td>14h:03 13/1/2015</td>
                                        <td>KHUONGNGUYEN</td>
                                        <td>10.000.000</td>
                                        <td></td>
                                    </tr>
                                    <tr>
                                        <td>14h:10 13/1/2015</td>
                                        <td>TÀI XẾ</td>
                                        <td>10.500.000</td>
                                        <td>Thêm tiền</td>
                                    </tr>
                                </tbody>

                            </table>
                            </br>
                            <div class="submit-area">
                                <div class="submit-area right">
                                    <a href="#" class="button success" id="update">
                                        <i class="icon-ok"></i> Chấp nhận
                                    </a>
                                    <a href="#" class="button alert" id="delete">
                                        <i class="icon-remove"></i> Từ chối
                                    </a>

                                </div>
                            </div>
                        </div>
                        </br>
                        <div class="large-12 columns">
                            </br>
                            <div data-alert="" class="alert-box radius secondary">
                                <label class="left"><font color="white" size="+1">Phản hồi</font></label>
                                </br>
                            </div>
                            <div class="row">
                                <div class="small-3 columns">
                                    <label for="right-label" class="right inline"><small class="validate">*</small> Giá thương lượng: </label>                                        
                                </div>
                                <div class="small-6 columns">
                                    <input onkeypress="return keyPhone(event);"type="text" id="right-label" placeholder="" required=""data-errormessage-value-missing="Vui lòng nhập giá thương lượng !"/>
                                </div>
                                <div class="small-3 columns">
                                    <label for="right-label" class="left inline">Ngàn đồng</label>            
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
                                <div class="submit-area">
                                    <div class="submit-area right">

                                        <button>
                                            <i class="icon-mail-forward"></i> Gửi
                                        </button>
                                    </div>
                                </div>
                                </br>
                            </div>

                            </br>


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
			alertify.confirm("Bạn có chấp nhận thương lượng không?", function (e) {
				if (e) {
                                    document.location.href="Controller?btnAction=test";  
				} else {
				}
			});
			return false;
		});
		$("#delete").on( 'click', function () {
			reset();
			alertify.confirm("Bạn có muốn từ chối thương lượng này không?", function (e) {
				if (e) {
					document.location.href="Controller?btnAction=#";  
				} else {
				}
			});
			return false;
		});
	</script>
<jsp:include page="footer.jsp"/>