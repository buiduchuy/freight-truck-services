<%-- 
    Document   : chi-tiet-hang-dang-giao-dich
    Created on : Feb 1, 2015, 12:02:40 AM
    Author     : KhuongNguyen-PC
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<title>Chi tiết hàng</title>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="header.jsp"/>
<section class="container">
            <center>
                <div class="form-content" style="border: 1px solid #ccc;box-shadow: 1px 1px 2px 2px #CCC;margin-bottom: 50px;width: 700px;">

                    <div class="form-content">
                        <form action="#" method="post" accept-charset="utf-8" enctype="multipart/form-data" data-abide="" novalidate="novalidate">						
                            <div class="row">
                                <div class="large-12 columns">
                                    <h2 class="page-title"><font color="orange" >Chi tiết hàng</font></h2>



                                </div>



                                <div class="large-12 columns">
                                    <div data-alert="" class="alert-box radius secondary">
                                        <label class="left"><font color="white" size="+1">Thông tin hàng hoá</font></label>
                                        </br>
                                    </div>
                                    <div class="row">
                                        <div class="small-3 columns">
                                            <label for="right-label" class="right inline"><small class="validate">*</small> Loại hàng: </label>                                        
                                        </div>
                                        <div class="small-6 columns">
                                            <select class="left" disabled="">
                                                <option selected="">May mặc</option>
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
                                            <input type="text" id="right-label" value="12" disabled=""/>
                                        </div>
                                        <div class="small-3 columns">
                                            <label for="right-label" class="left inline">Tấn</label>  
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="small-3 columns">
                                            <label for="right-label" class="right inline"><small class="validate">*</small> Chi phí vấn chuyển: </label>                                        
                                        </div>
                                        <div class="small-6 columns">
                                            <input type="text" id="right-label" value="20.000.000" disabled=""/>
                                        </div>
                                        <div class="small-3 columns">
                                            <label for="right-label" class="left inline">Ngàn đồng</label>            
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="small-3 columns">
                                            <label for="right-label" class="right inline" disabled="">Ghi chú : </label>                                        
                                        </div>
                                        <div class="small-6 columns">
                                            <textarea  maxlength="250" ></textarea>
                                        </div>
                                        <div class="small-3 columns">
                                        </div>
                                    </div>
                                </div>
                                <div class="large-12 columns">
                                    <div data-alert="" class="alert-box radius secondary">
                                        <label class="left"><font color="white" size="+1">Thông tin bên giao / bên nhận hàng</font></label>
                                        </br>
                                    </div>

                                    <div class="extra-title">
                                        <h3>Bên giao</h3>
                                    </div>
                                    <div class="row">
                                        <div class="large-7 columns">
                                            <div class="large-3 columns">
                                                <label for="right-label" class="right inline"><small class="validate">*</small> Địa chỉ: </label>                                        
                                            </div>
                                            <div class="large-9 columns">
                                                <input type="text" id="right-label" value="123 Nguyễn Kiệm, Gò Vấp, Tp.HCM"disabled=""/>
                                            </div>
                                        </div>
                                        <div class="large-5 columns">
                                            <div class="large-4 columns">
                                                <label for="right-label" class="right inline"><small class="validate">*</small> Thời gian : </label>                                        
                                            </div>
                                            <div class="large-8 columns">
                                                <div class="date-picker-wrap">
                                                    <input type="text" value="16h 15/1/2015" class="date-picker"disabled=""/>
                                                    <i class="icon-calendar"></i>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="extra-title">
                                        <h3>Bên nhận</h3>
                                    </div>
                                    <div class="row">
                                        <div class="large-7 columns">
                                            <div class="large-3 columns">
                                                <label for="right-label" class="right inline"><small class="validate">*</small> Địa chỉ: </label>                                        
                                            </div>
                                            <div class="large-9 columns">
                                                <input type="text" id="right-label" value="321 Hàng Trống, Hà Nội" disabled=""/>
                                            </div>
                                        </div>
                                        <div class="large-5 columns">
                                            <div class="large-4 columns">
                                                <label for="right-label" class="right inline"><small class="validate">*</small> Thời gian : </label>                                        
                                            </div>
                                            <div class="large-8 columns">
                                                <div class="date-picker-wrap">
                                                    <input type="text" value="16h 15/2/2015"class="date-picker" disabled=""/>
                                                    <i class="icon-calendar"></i>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="large-12 columns">
                                    <div data-alert="" class="alert-box radius secondary">
                                        <label class="left"><font color="white" size="+1">Trạng thái hàng</font></label>
                                        </br>
                                    </div>
                                    <table>
                                        <thead>
                                            <tr>
                                                <th ><h3 ><font color="orange" >THỜI GIAN</font></h3></th>
                                                <th ><h3 ><font color="orange" >TRẠNG THÁI</font></h3></th>

                                            </tr>
                                        </thead>
                                        <tbody>
                                            <tr>
                                                <td>14h 13/1/2015</td>
                                                <td>Tạo hàng</td>

                                            </tr>
                                            <tr>
                                                <td>16h 15/1/2015</td>
                                                <td>Đã nhận hàng</td>
                                            </tr>
                                        </tbody>

                                    </table>
                                </br>
                                </div>
                            </div>
                        </form>


                    </div>

                </div>
                </br>

            </center>

        </section>
<jsp:include page="footer.jsp"/>
