<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html lang="en">

<head>

<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="order" value="${requestScope.order }" />
<title>Chi tiết hóa đơn</title>
<jsp:include page="header.jsp"></jsp:include>
</head>

<body>

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
							Chi tiết hóa đơn <small>#OD<c:out
									value="${order.orderID }" /></small>
						</h1>
						<ol class="breadcrumb">
							<li><i class="fa fa-dashboard"></i><a
								href="ProcessServlet?btnAction=aIndex"> Dashboard</a></li>
							<li class="active"><i class="fa fa-exchange"></i><a
								href="ProcessServlet?btnAction=employeeManageOrder"> Quản lý
									giao dịch</a></li>
							<li>Chi tiết hóa đơn</li>
						</ol>
					</div>
				</div>
				<!-- /.row -->
				<div class="col-lg-12">
					<div class="panel panel-primary">
						<!--  <div class="panel-heading">Bootstrap: Basic Form</div>-->
						<div class="panel-body">
							<div class="extra-title">
								<h3>
									<font color="blue">Thông tin hàng hoá</font>
								</h3>
							</div>
							
						</div>
					</div>
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
