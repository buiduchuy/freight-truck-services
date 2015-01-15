<%-- 
    Document   : sign-in
    Created on : Jan 13, 2015, 12:33:13 AM
    Author     : KhuongNguyen-PC
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <jsp:include page="header.jsp"/>
    </head>
    <body class="page-homepage map-google" id="page-top" data-spy="scroll" data-target=".navigation" data-offset="90">
        <div class="wrapper">
            <jsp:include page="menu-ngang.jsp"/>
            <div class="container">
            </div><%--find my location--%>

            <div id="page-content">
                <!-- Breadcrumb -->
                <div class="container">
                    <ol class="breadcrumb">
                        <li><a href="#">Trang chủ</a></li>
                        <li class="active">Đăng nhập tài khoản</li>
                    </ol>
                </div>
                <!-- end Breadcrumb -->

                <div class="container">
                    <header><h1>Đăng nhập</h1></header>
                    <div class="row">
                        <div class="col-md-4 col-sm-6 col-md-offset-4 col-sm-offset-3">
                            <form role="form" id="form-create-account" method="post" action="#">
                                <div class="form-group">
                                    <label for="form-create-account-email">Email:</label>
                                    <input type="email" class="form-control" id="form-create-account-email" required>
                                </div><!-- /.form-group -->
                                <div class="form-group">
                                    <label for="form-create-account-password">Mật khẩu:</label>
                                    <input type="password" class="form-control" id="form-create-account-password" required>
                                </div><!-- /.form-group -->
                                <div class="form-group clearfix">
                                    <button type="submit" class="btn pull-right btn-default" id="account-submit">Đăng nhập</button>
                                </div><!-- /.form-group -->
                            </form>
                            <hr>
                            <div class="center"><a href="#">Quên mật khẩu</a></div>
                        </div>
                    </div><!-- /.row -->
                </div><!-- /.container -->
            </div>
            <jsp:include page="footer.jsp"/>
        </div>
        <jsp:include page="footer-sub1.jsp"/>
    </body>
</html>
