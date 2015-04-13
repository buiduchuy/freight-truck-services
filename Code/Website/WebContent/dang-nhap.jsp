<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<title>Đăng nhập</title>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html class="no-js" xmlns="http://www.w3.org/1999/xhtml" lang="vi-VN">
<meta http-equiv="content-type" content="text/html;charset=UTF-8" />
<head>
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
<link rel="shortcut icon" type="image/x-icon" href="css/img/favicon.png" />
<link type="text/css" rel="stylesheet" href="css/foundation.css"
	media="screen" />
<link type="text/css" rel="stylesheet"
	href="css/font-awesome/font-awesome.css" media="screen" />
<link type="text/css" rel="stylesheet" href="css/style.css"
	media="screen" />

</head>
<body>
	<div class="top-bar">
		<div class="row">
			<div class="large-12 columns">
				<div class="row">
					<div class="small-5 large-6 columns"></div>
					<div class="small-7 large-6 columns">
						<ul class="inline-list top-links right">
							<li><a href="dang-nhap.jsp"><i class="icon-signin"></i>
									Đăng nhập</a></li>
							<li><a href="dang-ky.jsp"><i class="icon-user"></i> Đăng
									ký</a></li>
						</ul>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="page-header">
		<div class="row">
			<div class="large-12 columns"></div>
		</div>
	</div>
	<section class="container">
		<center>
			<div class="form-content"
				style="border: 1px solid #ccc; box-shadow: 1px 1px 2px 2px #CCC; margin-bottom: 50px; width: 550px;">
				<form action="AccountServlet" method="post" accept-charset="utf-8"
					id="frm-login">
					<h2>Đăng nhập</h2>
					<input type="hidden" name="ReturnUrl" value='${requestScope["javax.servlet.forward.request_uri"]}?${requestScope["javax.servlet.forward.query_string"]}' />
					</br>
					<c:set var="error" value="${sessionScope.errorLogin}" />
					<c:if test="${not empty error}">
						<font color="red">${error}</font>
					</c:if>
					<div class="row text-left">
						<label><i class="icon-user"></i> Tài khoản</label> <input
							type="text" placeholder="Xin hãy nhập email đăng nhập vào đây..."
							required=""
							data-errormessage-value-missing="Vui lòng nhập Email!"
							name="txtEmail" />
					</div>
					<!--  type="email" data-errormessage-type-mismatch="Bạn nhập email không đúng dạng. Email phải có dạng: abc@abc.com"-->
					<div class="row text-left">
						<label><i class="icon-key"></i> Mật khẩu</label> <input
							type="password" placeholder="Nhập mật khẩu đăng nhập"
							name="txtPassword" required=""
							data-errormessage-value-missing="Vui lòng nhập mật khẩu !" />
					</div>
					<div class="row remember-me text-right">
						<span class="forgot-password"> <i
							class="icon-exclamation-sign"></i> <a href="quen-mat-khau.html"
							target="_blank">Tôi quên mật khẩu ?</a>
						</span>
					</div>
					<div class="row">
						<button class="large" style="width: 100%;" name="btnAction"
							value="login">Đăng nhập</button>
					</div>
				</form>
			</div>
		</center>
	</section>
	<jsp:include page="footer.jsp" />