<%-- 
    Document   : tao-hang-3
    Created on : Jan 30, 2015, 6:43:36 PM
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
                                    <button class="expand " disabled="">
                                        <i class="icon-ok"></i> Thông tin hàng hoá
                                    </button>
                                </div>
                                <div class="small-2 columns">
                                    <button class="expand " disabled="">
                                        Chi phí
                                    </button>
                                </div>
                                <div class="small-3 columns">
                                    <button class="expand secondary" disabled="">
                                        Xác nhận
                                    </button>
                                </div>
                            </div>
                            </br>
                            <div class="large-12 columns">



                                <div class="row">
                                    <div class="small-4 columns">
                                        <label for="right-label" class="right inline">Chi phí hệ thống đề nghị: </label>                                        
                                    </div>
                                    <div class="small-6 columns">
                                        <input type="text" id="right-label" value="20.000.000" disabled=""/>
                                    </div>
                                    <div class="small-2 columns">
                                        <label for="right-label" class="left inline">(Ngàn đồng)</label>            
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="small-4 columns">
                                        <label for="right-label" class="right inline">Chi phí người dùng đưa ra: </label>                                        
                                    </div>
                                    <div class="small-6 columns">
                                        <input type="text" id="right-label" />
                                    </div>
                                    <div class="small-2 columns">
                                        <label for="right-label" class="left inline">(Ngàn đồng)</label>            
                                    </div>
                                </div>

                            </div>

                            <div class="row">
                                <div class="large-12 columns">
                                    </br>
                                    <label> <font style="color: orange">Nếu bạn để trống ô "Chi phí người dùng" thì hệ thống sẽ lấy chi phí đề nghị làm chi phí hàng hoá.
                                        </font> </label>
                                    <div class="submit-area right">

                                        <button class="secondary" name="btnAction" value="pTaohang3"><i class="icon-mail-reply"></i> Trở về</button>
                                        <button class=""name="btnAction" value="nTaohang3"><i class="icon-mail-forward"></i> Tiếp theo</button>

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
<jsp:include page="footer.jsp"/>
