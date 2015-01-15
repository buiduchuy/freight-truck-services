<%-- 
    Document   : menu
    Created on : Jan 15, 2015, 3:54:29 AM
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
                    <a data-toggle="modal" href="#form_owner_register">Đăng ký tài khoản</a>
                    <a data-toggle="modal" href="#form_login" >Đăng nhập tài khoản</a>
                </div>
            </div>

        </div>
    </div>
    <div class="container">
        <header class="navbar" id="top" role="banner">
            <div class="navbar-header">
                <div class="navbar-brand nav" id="brand">
                    <a href="index.jsp"><img src="assets/img/logo_1.png" alt="brand"></a>
                </div>
            </div>
            <nav class="collapse navbar-collapse bs-navbar-collapse navbar-right" role="navigation">
                <ul class="nav navbar-nav">
                    <li class="active has-child"><a href="index.jsp">Trang chủ</a>
                        <ul class="child-navigation">
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
                            <li><a href="#form_create_deal" data-toggle="modal">Đăng hàng</a></li>
                            <li><a href="manage-deals.jsp">Quản lý deal</a></li>
                        </ul>
                    </li>
                    <li><a href="#">Liên Hệ</a></li>
                </ul>
            </nav>
            <div class="add-your-property">
                <a href="#form_create_deal" data-toggle="modal" class="btn btn-default"><i class="fa fa-plus"></i><span class="text">Đăng hàng</span></a>
            </div>
        </header>
    </div>
</div>
<jsp:include page="form_login.jsp"/>
<jsp:include page="form_owner_register.jsp"/>
<jsp:include page="form_create_deal_owner.jsp"/>



