<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<fmt:setLocale value="vi_VN" />
<html lang="en">

<head>

<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">

<title>Quản lý tài khoản</title>

<jsp:include page="header.jsp"></jsp:include>

</head>

<body onload="getLastID();getListNotification();">

	<div id="wrapper">

		<!-- Navigation -->
		<jsp:include page="navigation-bar.jsp"></jsp:include>
		<!-- End Navigation -->

		<div id="page-wrapper">

			<div class="container-fluid">

				<!-- Page Heading -->
				<div class="row">
					<div class="col-lg-12">
						<h1 class="page-header">
							Danh sách chờ kích hoạt
							<!-- <small>Subheading</small> -->
						</h1>
						<ol class="breadcrumb">
							<li><i class="fa fa-dashboard"></i> <a
								href="ProcessServlet?btnAction=aIndex">Dashboard</a></li>
							<li class="active"><i class="fa fa-users"></i> Kích hoạt tài
								khoản</li>
						</ol>
					</div>
					<div class="col-lg-12">
						<form action="AccountServlet" method="POST" accept-charset="UTF-8">
							Tìm kiếm: <input type="text" name="txtSearch" /></input>
							<button type="submit" value="searchByEmail" name="btnAction">Tìm
								kiếm</button>
						</form>
						<br />
						<table class="table table-bordered table-hover table-striped">
							<thead>
								<tr>
									<th><font color="orange">Tài khoản</font></th>
									<th><font color="orange">Địa chỉ</font></th>
									<th><font color="orange">Thông tin liên lạc</font></th>
									<th><font color="orange">Ngày đăng kí</font></th>
									<th></th>
								</tr>
							</thead>
							<tbody>
								<c:set var="listAccount" value="${requestScope.listAccount }" />
								<c:if test="${not empty listAccount }">
									<c:forEach var="account" items="${listAccount}">
										<form action="AccountServlet" method="POST"
											accept-charset="UTF-8">
											<tr>
												<td>${account.email}<input type="hidden" name="email"
													value="${account.email}" /><input type="hidden" name="type"
													value="${account.type}" /></td>
												<td>${account.address}</td>
												<td>${account.contact}</td>
												<td><c:set var="createTime"
														value="${account.createTime}" /> <fmt:parseDate
														value="${createTime}" var="dateCreateTime"
														pattern="yyyy-MM-dd HH:mm:ss.SSS" /> <fmt:formatDate
														value="${dateCreateTime}" pattern="hh:mm dd-MM-yyyy"
														var="createTimeFormatted" /> <c:out
														value="${createTimeFormatted}" /></td>
												<td><button class="btn btn-sm btn-success"
														type="submit" value="employeeActivateAccount"
														name="btnAction">Xác nhận</button></td>
											</tr>
										</form>
									</c:forEach>
								</c:if>
							</tbody>
						</table>
					</div>
				</div>
				<!-- /.row -->

			</div>
			<!-- /.container-fluid -->

		</div>
		<!-- /#page-wrapper -->

	</div>
	<!-- /#wrapper -->

	<!-- jQuery -->
	<script src="admin/js/jquery.js"></script>

	<!-- Bootstrap Core JavaScript -->
	<script src="admin/js/bootstrap.min.js"></script>

</body>

</html>
