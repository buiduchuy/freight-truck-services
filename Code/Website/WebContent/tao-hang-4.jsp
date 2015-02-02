<%-- 
    Document   : tao-hang-4
    Created on : Jan 30, 2015, 6:54:48 PM
    Author     : KhuongNguyen-PC
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<title>Tạo hàng</title>
<jsp:include page="header.jsp"/>

<section class="container">
    <center>
        <div class="form-content" style="border: 1px solid #ccc;box-shadow: 1px 1px 2px 2px #CCC;margin-bottom: 50px;width: 700px;">
            <div class="form-content">
                <form action="Controller" method="get" accept-charset="utf-8">						
                    <div class="row">
                        <div class="large-12 columns">
                            <h2 class="page-title"><font color="orange" >Tạo hàng</font></h2>
                        </div>
                        <div class="large-12 columns">
                            <div class="row">
                                <div class="small-4 columns">
                                    <button class="expand">
                                        <i class="icon-ok"></i> Địa chỉ giao nhận hàng
                                    </button>                                  
                                </div>
                                <div class="small-3 columns">
                                    <button class="expand ">
                                        <i class="icon-ok"></i> Thông tin hàng hoá
                                    </button>
                                </div>
                                <div class="small-2 columns">
                                    <button class="expand ">
                                        <i class="icon-ok"></i> Chi phí
                                    </button>
                                </div>
                                <div class="small-3 columns">
                                    <button class="expand ">
                                        Xác nhận
                                    </button>
                                </div>
                            </div>
                            </br>
                            <div class="large-12 columns">
                                <div class="extra-title">
                                    <h3>Thông tin hàng</h3>
                                </div>
                                <div class="row">
                                    <div class="small-3 columns">
                                        <label for="right-label" class="right inline"><small class="validate">*</small> Loại hàng: </label>                                        
                                    </div>
                                    <div class="small-6 columns">
                                        <label for="right-label" class="left inline">May mặc</label>
                                    </div>
                                    <div class="small-3 columns">
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="small-3 columns">
                                        <label for="right-label" class="right inline"><small class="validate">*</small> Khối lượng: </label>                                        
                                    </div>
                                    <div class="small-6 columns">
                                        <label for="right-label" class="left inline">12 Tấn</label>
                                    </div>
                                    <div class="small-3 columns">
                                        <label for="right-label" class="left inline"></label>  
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="small-3 columns">
                                        <label for="right-label" class="right inline"><small class="validate">*</small> Chi phí vấn chuyển: </label>                                        
                                    </div>
                                    <div class="small-6 columns">
                                        <label for="right-label" class="left inline">20.000.00 Ngàn đồng</label>
                                    </div>
                                    <div class="small-3 columns">
                                        <label for="right-label" class="left inline"></label>            
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="small-3 columns">
                                        <label for="right-label" class="right inline">Ghi chú : </label>                                        
                                    </div>
                                    <div class="small-6 columns">
                                        <label for="right-label" class="left inline"></label>
                                    </div>
                                    <div class="small-3 columns">
                                    </div>
                                </div>
                            </div>
                            <div class="large-12 columns">

                                <div class="extra-title">
                                    <h3>Địa chỉ giao</h3>
                                </div>
                                <div class="row">
                                    <div class="large-7 columns">
                                        <div class="large-3 columns">
                                            <label for="right-label" class="right inline"><small class="validate">*</small> Địa chỉ: </label>                                        
                                        </div>
                                        <div class="large-9 columns">
                                            <label for="right-label" class="left inline">123 Nguyễn Kiệm, Gò Vấp, Tp.HCM</label>

                                        </div>
                                    </div>
                                    <div class="large-5 columns">
                                        <div class="large-4 columns">
                                            <label for="right-label" class="right inline"><small class="validate">*</small> Thời gian : </label>                                        
                                        </div>
                                        <div class="large-8 columns">
                                            <label for="right-label" class="left inline">16h 15/1/2015</label>

                                        </div>
                                    </div>
                                </div>
                                <div class="extra-title">
                                    <h3>Địa chỉ nhận</h3>
                                </div>
                                <div class="row">
                                    <div class="large-7 columns">
                                        <div class="large-3 columns">
                                            <label for="right-label" class="right inline"><small class="validate">*</small> Địa chỉ: </label>                                        
                                        </div>
                                        <div class="large-9 columns">
                                            <label for="right-label" class="left inline">321 Hàng Trống, Hà Nội</label>

                                        </div>
                                    </div>
                                    <div class="large-5 columns">
                                        <div class="large-4 columns">
                                            <label for="right-label" class="right inline"><small class="validate">*</small> Thời gian : </label>                                        
                                        </div>
                                        <div class="large-8 columns">
                                            <label for="right-label" class="left inline">16h 15/2/2015</label>

                                        </div>
                                    </div>
                                </div>
                            </div>
                       
                            

                        </div>
               


            </div>
            <div class="row">
                <div class="large-12 columns">
                    <div class="submit-area">
                        <div class="submit-area right">
                            <a href="tao-hang-3.jsp" class="button secondary">
                                <i class="icon-mail-reply"></i> Trở về
                            </a>
                            <button name="btnAction" value="Taohang"><i class="icon-ok"></i> Tạo hàng</button>
                        </div>
                    </div>	
                    </br>
                </div>
            </div>
 </form>
            </br>

    </center>

</section>

<!-- dialog box -->




<!-- script of dialog -->

<jsp:include page="footer.jsp"/>
