<%-- 
    Document   : tao-hang-1
    Created on : Jan 30, 2015, 11:21:10 AM
    Author     : KhuongNguyen-PC
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<title>Gợi ý hệ thống</title>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="header.jsp"/>
<section class="container">
    <center>
        <div class="form-content" style="border: 1px solid #ccc;box-shadow: 1px 1px 2px 2px #CCC;margin-bottom: 50px;width: 900px;">

            <div class="form-content">
                <form action="Controller" method="get" accept-charset="utf-8">						
                    <div class="row">
                        <div class="large-12 columns">
                            <h2 class="page-title"><font color="orange" >Gợi ý từ hệ thống</font></h2>
                        </div>
                        <div class="large-12 columns">
                            <div data-alert="" class="alert-box radius secondary">
                                <label class="left"><font color="white" size="+1">Danh sách tài xế phù hợp</font></label>
                                </br>
                            </div>
                            <table id="example" class="display" cellspacing="0" width="100%">
                                <thead>
                                    <tr>
                                        <th width="50"><h4 ><font color="orange" >#</font></h4></th>
                                <th width="150"><font color="orange" >Địa điểm bắt đầu</font></th>
                                <th width="150"><font color="orange" >Địa điểm kết thúc</font></th>
                                <th width="150"><font color="orange" >Gía (ngàn đồng)</font></th>
                                <th width="150"><font color="orange" >Điểm uy tín</font></th>
                                <th width="150"><a class="button success expand" href="danh-sach-de-nghi.jsp"> Các đề nghị </a></th>
                                </tr>

                                </thead>



                                <tbody>
                                    <c:set var="taxe" value="${sessionScope.TaiXe}"/>
                                    <c:if test="${not empty taxe}">
                                        <c:forEach var="rows" items="${taxe}">
                                            <tr>
                                                <td>${rows.id}</td>
                                                <td>${rows.ddBatdau}</td>
                                                <td>${rows.ddKetthuc}</td>
                                                <td>${rows.gia}</td>
                                                <td>${rows.diem}</td>

                                                <td>
                                                    <input type="hidden"value="${rows.id}" name="txtID"/>
                                                    <button expand="" name="btnAction" value="xemCT"><i class="icon-ok"></i> Xem chi tiết</button>
                                                </td>
                                            </tr>
                                        </c:forEach>

                                    </c:if>
                                </tbody>
                            </table>
                            </br></br>
                        </div>


                    </div>
                </form>


            </div>

        </div>
        </br>

    </center>

</section>


<jsp:include page="footer.jsp"/>