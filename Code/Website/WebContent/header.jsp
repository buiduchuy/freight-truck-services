<%-- 
    Document   : header
    Created on : Jan 30, 2015, 8:03:10 AM
    Author     : KhuongNguyen-PC
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="reqUrl" value="${pageContext.request.requestURI}"/>
<c:set var="namePage" value="${fn:substringAfter(reqUrl, 'FTS/')}"
    scope="session" />
<!DOCTYPE html>
<html class="no-js" xmlns="http://www.w3.org/1999/xhtml" lang="vi-VN"  >
    <meta http-equiv="content-type" content="text/html;charset=UTF-8" />
    <head>
        <meta http-equiv="Content-Type" content="text/html;charset=UTF-8"/>
        <link rel="stylesheet" href="css/foundations.css" />
        <link rel="shortcut icon" type="image/x-icon" href="css/img/favicon.png"/>
        <script type="text/javascript" src="js/jquery.js" charset="UTF-8"></script>
        <link type="text/css" rel="stylesheet" href="css/foundation.css" media="screen" />
        <link type="text/css" rel="stylesheet" href="css/font-awesome/font-awesome.css" media="screen" />
        <link type="text/css" rel="stylesheet" href="css/style.css" media="screen" />
        <script src="js/jquerytab.js" type="text/javascript"></script>
        <script src="js/custom.modernizr.js" type="text/javascript"></script>

        <link rel="stylesheet" href="css/alertify.core.css" />
        <link rel="stylesheet" href="css/alertify.default.css" id="toggleCSS" />



	<script src="js/jquery.js"></script>
	<script src="js/alertify.min.js"></script>



        <link href="css/jquery.dataTables.css" rel="stylesheet" type="text/css"/>
        <script type="text/javascript" language="javascript" src="js/jquery.dataTables.js"></script>
        <script type="text/javascript" language="javascript" class="init">


            $.extend($.fn.dataTable.defaults, {
                "searching": false,
                "ordering": false
            });


            $(document).ready(function () {
                $('#example').dataTable();
            });

            $(document).ready(function () {
                $('#example1').dataTable();
            });
        </script>
        <script src="https://maps.googleapis.com/maps/api/js?v=3.exp&signed_in=true&libraries=places"></script>
        







    </head>
    <body onload="auto()">
        <div class="site-wrapper">
            <div class="top-bar">
                <div class="row">
                    <div class="large-12 columns">
                        <div class="row">
                            <div class="small-5 large-6 columns">

                            </div>
                            <div class="small-7 large-6 columns">
                                <c:set var="account" value="${sessionScope.account}"/>
                                <c:choose>
                                    <c:when test="${not empty account}">
                                        <ul class="inline-list top-links right">
                                            <li>
                                                <a href="#"><i class="icon-user"></i> Xin chào, ${account}!</a>
                                                <ul class="sub-topbar">
                                                    <li><a href="ControllerManageGoods?btnAction=manageGoods"><i class="icon-desktop"></i>Quản lý hàng</a></li>
                                                    <li><a href="tai-khoan.jsp"><i class="icon-cog"></i>Cấu hình tài khoản</a></li>
                                                    <li><a href="lich-su-hang.jsp"><i class="icon-bar-chart"></i>Lịch sử giao dịch</a></li>
                                                    <li><a href="ControllerAccount?btnAction=offAccount"><i class="icon-off"></i>Đăng xuất</a></li>
                                                </ul>
                                            </li>
                                        </ul> 
                                    </c:when>
                                    <c:otherwise>
                                        <jsp:forward page="dang-nhap.jsp"/>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="main-nav">
                <div class="row">
                    <div class="large-2 columns top-logo">
                        <h1><a href="index.jsp">fts.vn</a></h1>
                    </div>
                    <div class="large-7 columns top-bar-section">
                        <nav class="">
                            <ul class="inline-list">
                                <li >
                                    <a href="tao-hang-1.jsp"><i class="icon-truck"></i> Tạo hàng&nbsp;&nbsp;</a>
                                </li>
                                <li><a href="ControllerManageGoods?btnAction=manageGoods"><i class="icon-desktop"></i> Quản lý hàng</a></li>
                                <li><a href="ControllerManageGoods?btnAction=manageOrder"><i class="icon-desktop"></i> Quản lý hoá đơn</a></li>
                                <li><a href="lich-su-hang.jsp"><i class="icon-bar-chart"></i> Lịch sử hàng</a></li>
                            </ul>
                        </nav>

                    </div>

                </div>
            </div>
            </br>
