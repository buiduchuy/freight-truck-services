<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="reqUrl" value="${pageContext.request.requestURI}"
	scope="session" />
<c:set var="namePage" value="${fn:substringAfter(reqUrl, 'FTS/')}"
	scope="session" />
<!DOCTYPE html>
<html class="no-js" xmlns="http://www.w3.org/1999/xhtml" lang="vi-VN">
<meta http-equiv="content-type" content="text/html;charset=UTF-8" />
<head>
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
<link rel="stylesheet" href="css/foundations.css" />
<link rel="shortcut icon" type="image/x-icon" href="css/img/favicon.png" />
<script type="text/javascript" src="js/jquery.js" charset="UTF-8"></script>
<link type="text/css" rel="stylesheet" href="css/foundation.css"
	media="screen" />
<link type="text/css" rel="stylesheet"
	href="css/font-awesome/font-awesome.css" media="screen" />

<link type="text/css" rel="stylesheet" href="css/style.css"
	media="screen" />


<script src="js/jquerytab.js" type="text/javascript"></script>
<script src="js/custom.modernizr.js" type="text/javascript"></script>

<link rel="stylesheet" href="css/alertify.core.css" />
<link rel="stylesheet" href="css/alertify.default.css" id="toggleCSS" />

<script src="js/jquery.js"></script>
<script src="js/alertify.min.js"></script>
<script src="js/notify.js"></script>

<link href="css/notification.css" rel="stylesheet" type="text/css" />
<link href="css/jquery.dataTables.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="js/jquery.dataTables.js"></script>
<script type="text/javascript" class="init">
	$.extend($.fn.dataTable.defaults, {
		"searching" : false,
		"ordering" : false
	});

	$(document).ready(function() {
		$('#example').dataTable();
	});

	$(document).ready(function() {
		$('#example1').dataTable();
	});

	$(document).ready(function() {
		$('#example2').dataTable();
	});
</script>
<script
	src="https://maps.googleapis.com/maps/api/js?v=3.exp&signed_in=true&libraries=places"></script>

</head>
<body onload="getLastID();getListNotification();">
	<div class="top-bar">
		<div class="row">
			<div class="large-12 columns">
				<div class="row">
					<div class="small-5 large-6 columns"></div>
					<div class="small-7 large-6 columns">
						<c:set var="account" value="${sessionScope.account}" />
						<c:choose>
							<c:when test="${not empty account}">
								<input type="hidden" id="email" value="${owner.email}" />
								<ul class="inline-list top-links right">
									<li style="width: auto;"><a href="#"><i class="icon-user"></i> Xin chào,
											${account}!</a>
										<ul class="sub-topbar">
											<li><a href="ProcessServlet?btnAction=manageGoods"><i
													class="icon-desktop"></i>Quản lý hàng</a></li>
											<li><a href="tai-khoan.jsp"><i class="icon-cog"></i>Cấu
													hình tài khoản</a></li>

											<li><a href="ProcessServlet?btnAction=logout"><i
													class="icon-off"></i>Đăng xuất</a></li>
										</ul></li>
									<li><a href="#"><b id="notificationSize"></b> <i class="icon-bell-alt"></i></a>
										<ul id="notificationList" class="sub-topbar"
											style="width: 400px;">
										</ul></li>
								</ul>
							</c:when>
							<c:otherwise>
								<jsp:forward page="ProcessServlet?btnAction=loginPage" />
							</c:otherwise>
						</c:choose>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="main-nav">
		<div class="row">
		<img src="css/img/logo.png" width="200px" style="padding-top: 10px;"/>
			</div>
			<!-- <div class="large-7 columns top-bar-section">
				<nav class="">
					<ul class="inline-list">
						<c:choose>
							<c:when
								test="${namePage=='tao-hang-1.jsp' or namePage=='tao-hang-2.jsp' or namePage=='tao-hang-3.jsp' or namePage=='tao-hang-4.jsp'}">
								<li class="active"><a
									href="ProcessServlet?btnAction=createGoods"><i
										class="icon-truck"></i> Tạo hàng&nbsp;&nbsp;</a></li>
							</c:when>
							<c:otherwise>
								<li><a href="ProcessServlet?btnAction=createGoods"><i
										class="icon-truck"></i> Tạo hàng&nbsp;&nbsp;</a></li>
							</c:otherwise>
						</c:choose>
						<c:choose>
							<c:when
								test="${namePage=='quan-ly-hang.jsp' or namePage=='chi-tiet-hang.jsp'or namePage=='goi-y-he-thong.jsp' or namePage=='chi-tiet-route.jsp' or namePage=='chi-tiet-de-nghi.jsp'or namePage=='danh-sach-de-nghi.jsp'or namePage=='cleardeliveryAddress.jsp' or namePage=='clearpickupAddress.jsp'}">
								<li class="active"><a
									href="ProcessServlet?btnAction=manageGoods"><i
										class="icon-truck"></i> Quản lý hàng</a></li>
							</c:when>
							<c:otherwise>
								<li><a href="ProcessServlet?btnAction=manageGoods"><i
										class="icon-truck"></i> Quản lý hàng</a></li>

							</c:otherwise>
						</c:choose>

						<c:choose>
							<c:when
								test="${namePage=='quan-ly-order.jsp' or namePage=='chi-tiet-order.jsp'}">
								<li class="active"><a
									href="OrderServlet?btnAction=manageOrder"><i
										class="icon-desktop"></i> Quản lý hoá đơn</a></li>
							</c:when>
							<c:otherwise>
								<li><a href="OrderServlet?btnAction=manageOrder"><i
										class="icon-desktop"></i> Quản lý hoá đơn</a></li>
							</c:otherwise>
						</c:choose>
					</ul>
				</nav>
			</div> -->
		</div>
	</div>