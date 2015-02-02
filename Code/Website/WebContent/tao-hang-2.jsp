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
        <div class="form-content" style="border: 1px solid #ccc;box-shadow: 1px 1px 2px 2px #CCC;margin-bottom: 50px;width: 700px;">

            <div class="form-content">
                <form action="Controller" method="get" accept-charset="utf-8" >						
                    <div class="row">
                        <div class="large-12 columns">
                            <h2 class="page-title"><font color="orange" >Tạo hàng</font></h2>
                        </div>
                        <div class="large-12 columns">
                            <div class="row">
                                <div class="small-4 columns">
                                    <button class="expand" disabled="">
                                        <i class="icon-ok"></i> Địa chỉ giao nhận hàng
                                    </button>                                  
                                </div>
                                <div class="small-3 columns">
                                    <button class="expand "disabled="">
                                        Thông tin hàng hoá
                                    </button>
                                </div>
                                <div class="small-2 columns">
                                    <button class="expand secondary"disabled="">
                                        Chi phí
                                    </button>
                                </div>
                                <div class="small-3 columns">
                                    <button class="expand secondary"disabled="">
                                        Xác nhận
                                    </button>
                                </div>
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
                            <div class="row">
                                <div class="large-12 columns">
                                    <div class="submit-area right">
                                        <button class="secondary" name="btnAction" value="pTaohang2"><i class="icon-mail-reply"></i> Trở về</button>
                                        <button class=""name="btnAction" value="nTaohang2"><i class="icon-mail-forward"></i> Tiếp theo</button>

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
<jsp:include page="footer.jsp"/>
