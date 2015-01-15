<%-- 
    Document   : Menu
    Created on : Jan 12, 2015, 10:04:36 PM
    Author     : KhuongNguyen-PC
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<div class="navigation">
    <div class="secondary-navigation">
        <div class="container">
            <div class="contact">
                <figure><strong>Phone:</strong>+84 (xxx) xxx-xxxx</figure>
                <figure><strong>Email:</strong>FTS@fpt.com</figure>
            </div>
            <div class="user-area">
                <div class="actions">
                    <a href="create-account.jsp" class="promoted"><strong>Đăng ký tài khoản</strong></a>


                    <a  data-toggle="collapse" href="#login" aria-expanded="false" aria-controls="collapseExample">
                        Đăng nhập tài khoản
                    </a>
                    <div class="collapse" id="login">
                        <div class="well">
                            <input type="email" class="form-control" id="form-create-account-email" required size="35" placeholder="Email">
                            <input type="password" class="form-control" id="form-create-account-password" required size="35" placeholder="Password">
                            <input type="submit" class="btn btn-default"value="Đăng nhập"><a href="#">Quên mật khẩu ?</a>
                        </div>
      
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="container">
        <header class="navbar" id="top" role="banner">
            <div class="navbar-header">
                <button class="navbar-toggle" type="button" data-toggle="collapse" data-target=".bs-navbar-collapse">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <div class="navbar-brand nav" id="brand">
                    <a href="index.jsp"><img src="assets/img/logo_1.png" alt="brand"></a>
                </div>
            </div>
            <nav class="collapse navbar-collapse bs-navbar-collapse navbar-right" role="navigation">
                <ul class="nav navbar-nav">
                    <li class="active has-child"><a href="index.jsp">Trang chủ</a>
                        <ul class="child-navigation">
                            <li><a href="#">Trang chủ</a></li>
                            <li><a href="#">Trang chủ</a></li>

                        </ul>
                    </li>
                    <li class="has-child"><a href="#">Tìm kiếm</a>
                        <ul class="child-navigation">
                            <li><a href="find-driver.jsp">Tìm kiếm tài xế</a></li>
                            <li><a href="find-road.jsp">Tìm kiếm đường</a></li>
                            <li><a href="tracking.jsp">Theo dõi</a></li>
                        </ul>
                    </li>
                    <li class="has-child"><a href="#">Hàng hoá</a>
                        <ul class="child-navigation">
                            <li><a href="add-deals.jsp">Đăng hàng</a></li>
                            <li><a href="manage-deals.jsp">Quản lý deal</a></li>
                        </ul>
                    </li>
                    <li><a href="#">Liên Hệ</a></li>
                </ul>
            </nav><!-- /.navbar collapse-->
            <div class="add-your-property">
                <a href="add-deals.jsp" class="btn btn-default"><i class="fa fa-plus"></i><span class="text">Đăng hàng</span></a>
            </div>
        </header><!-- /.navbar -->
    </div><!-- /.container -->
</div>
