<%-- 
    Document   : tao-hang-1
    Created on : Jan 30, 2015, 11:21:10 AM
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
                                        Địa chỉ giao nhận hàng
                                    </button>                                  
                                </div>
                                <div class="small-3 columns">
                                    <button class="expand secondary" disabled="">
                                        Thông tin hàng hoá
                                    </button>
                                </div>
                                <div class="small-2 columns">
                                    <button class="expand secondary" disabled="">
                                        Chi phí
                                    </button>
                                </div>
                                <div class="small-3 columns">
                                    <button class="expand secondary" disabled="">
                                        Xác nhận
                                    </button>
                                </div>
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
                                            <input type="text"onFocus="geolocate()" id="place_start" pattern=".{1,50}" placeholder="Nhập địa điểm giao hàng" required=""data-errormessage-value-missing="Vui lòng chọn địa điểm giao hàng !" data-errormessage-pattern-mismatch="Bạn phải nhập địa chỉ [1-50] kí tự !"/>
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
                            <div class="row">
                                <div class="large-12 columns">
                                    <div class="submit-area right">
                                        <button class=""name="btnAction" value="nTaohang1"><i class="icon-mail-forward"></i> Tiếp theo</button>

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

<!-- end main content -->
<jsp:include page="footer.jsp"/>
