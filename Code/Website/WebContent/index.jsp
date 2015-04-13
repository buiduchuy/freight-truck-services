<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
    <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

        <title>FTS | Trang chủ</title>
        <link rel="shortcut icon" type="image/x-icon" href="css/img/favicon.png"/>
        <link href="css/font-awesome/font-awesome.css" rel="stylesheet" type="text/css"/>
        <link href="css/style-index.css" rel="stylesheet" type="text/css"/>
        <link href="css/foundation.css" rel="stylesheet" type="text/css"/>
    </head>
    <body>
        <nav class="navbar navbar-default navbar-static-top" role="navigation">
            <div class="container">
                <div class="navbar-header">
                    <a class="navbar-brand" href="ProcessServlet"><img src="css/img/logo.png"></a>
                </div>
                <div class="navbar-collapse collapse">
                    <c:set var="account" value="${sessionScope.account}"/>
                    <c:choose>
                        <c:when test="${not empty account}">
                            <ul id="nav" class="nav navbar-nav navbar-right">
                                <li>
                                    <a href="#"><i class="icon-user"></i> Xin chào, ${account }!</a>
                                  
                                </li>                            
                            </ul> 
                            </c:when>
                            <c:otherwise>
                            <ul id="nav" class="nav navbar-nav navbar-right">
                                <li class="menu-dang-nhap"><a title="Đăng nhập" target="_blank" href="ProcessServlet?btnAction=loginPage"><font color="gray" size="+1">ĐĂNG NHẬP</font></a></li>
                                <li class="menu-dang-ky-dich-vu"><a target="_blank" href="ProcessServlet?btnAction=registerPage"><font color="gray" size="+1">ĐĂNG KÝ</font></a></li>
                            </ul> 
                        </c:otherwise>
                    </c:choose>

                </div>
            </div>
        </nav>
        <section class="content-area bg2 animated" id="chapter644" data-fx="fadeInLeft">
            <div class="container  ">
                <div class="row">
                    <h3 class="text-center">Vận chuyển hàng hoá FTS</h3>
                    <p class="larger text-center">Cắt giảm chi phí vận chuyển.
                        </br>
                        Giúp bạn tiết kiệm thời gian, nhân sự đi giao hàng.
                        </br>
                        Vận chuyển hàng hoá toàn quốc, gia tăng cơ hội.
                        </br>
                    </p>
                    <hr>
                </div>
                <div class="row ">
                    <div class="col-md-4">
                        <div class="iconBox type3">
                            <div class="media text-center ">
                                <div class="media-body"><h4 class="  media-heading"><a href=""><font size="+3"><i class="icon-shopping-cart"></i></font> Tạo hàng nhanh</a>
                                    </h4><p>Tạo hàng nhanh và dễ dàng với hướng dẫn chi tiết cho người sử dụng</p>
                                    <br/>
                                    <a class="button" href="ProcessServlet?btnAction=createGoods"><i class="icon-ok"></i> Tạo hàng</a>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-4">
                        <div class="iconBox type3">
                            <div class="media text-center">
                                <div class="media-body">
                                    <h4 class="  media-heading"><a href=""><font size="+3"><i class="icon-list-ul"></i></font> Quản lý hàng</a></h4>
                                    <p>Giúp người sử dụng quản lý các hàng đã đăng cũng như tìm tài xế cho hàng</p>
                                    <br/>
                                    <a class="button" href="ProcessServlet?btnAction=manageGoods"><i class="icon-ok"></i> Quản lý hàng</a>
                                </div>
                            </div>
                        </div>
                        <div data-height="40" class="spacer " style="height: 40px;"></div>
                    </div>
                    <div class="col-md-4">
                        <div class="iconBox type3">
                            <div class="media text-center">
                                <div class="media-body"><h4 class="  media-heading">
                                        <a href=""> <font size="+3"><i class="icon-list-alt"></i></font> Quản lý hóa đơn</a>
                                    </h4><p>Quản lý hóa đơn giao hàng chuyên nghiệp hơn, kiểm soát giao nhận hàng.</p>
									<br/>
                                    <a class="button" href="ProcessServlet?btnAction=manageOrder"><i class="icon-ok"></i> Quản lý hóa đơn</a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </section>
        <footer>
            <section class="footer-copyright">
                <div class="row">
                    <div class="small-6 large-6 columns left">
                        Bản quyền © 2015
                    </div> 
                    <div class="small-6 large-6 columns">
                        <nav id="footer-nav" class="text-right">
                            <ul id="bot-nav" class="inline-list right">
                                <li>
                                    <a href="index.html" target="_blank"><font color="gray" size="">Trang chủ</font></a>
                                </li>
                                <li>
                                    <a href="#" target="_blank"><font color="gray" size="">Giới thiệu</font></a>
                                </li>
                            </ul>
                        </nav>
                    </div>
                </div>
            </section>
        </footer>
    </body>
</html>
