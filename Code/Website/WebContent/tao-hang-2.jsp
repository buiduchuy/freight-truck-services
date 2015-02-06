<%-- 
    Document   : tao-hang-2
    Created on : Jan 30, 2015, 6:24:04 PM
    Author     : KhuongNguyen-PC
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<title>Tạo hàng</title>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="header.jsp"/>
<section class="container">
    <center>
        <div class="form-content" style="border: 1px solid #ccc;box-shadow: 1px 1px 2px 2px #CCC;margin-bottom: 50px;width: 800px;">
            <div class="row"> </div>
            <div class="large-12 columns">
                <h1 class="page-title"><font color="orange" >Tạo hàng</font></h1>
                <ul class="button-group even-4">
                    <li><a href="tao-hang-1.jsp" class="button success"><i class="icon-ok"></i>Địa chỉ giao nhận hàng</a></li>
                    <li><a href="#"class="button">Thông tin hàng hoá</a></li>
                    <li><a class="button disabled">Chi phí</a></li>
                    <li><a class="button disabled">Xác nhận</a></li>
                </ul>
            </div>

            <form action="Controller" method="post" accept-charset="utf-8" >
                <div class="row">
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
                                <label for="right-label" class="left inline">Kg</label>  
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
                        <div class="row">
                            <div class="large-12 columns">
                                <div class="submit-area right">
                                    <a href="tao-hang-1.jsp" class="button secondary"><i class="icon-mail-reply"></i> Trở về</a>
                                    <button class="success"name="btnAction" value="next2"><i class="icon-mail-forward"></i> Tiếp theo</button>

                                </div>	
                                </br>
                            </div>
                        </div>
                             <div class="row"> </div>
                    </div>
                </div>
            </form>
        </div>



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
<jsp:include page="footer.jsp"/>
