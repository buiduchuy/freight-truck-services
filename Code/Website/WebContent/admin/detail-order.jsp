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
				<div class="panel panel-default">
					<!--  <div class="panel-heading">Bootstrap: Basic Form</div>-->
					<div class="panel-body">
						<form action="PaypalServlet" method="POST" accept-charset="UTF-8">
							<div class="col-sm-12">
								<div class="col-sm-12">
									<label class="control-label col-sm-3">Mã hóa đơn: </label> <label
										class="control-label col-sm-8">${order.orderID}<input
										type="hidden" name="txtOrderID" value="${order.orderID }" /></label>
								</div>
								<div class="col-sm-12">
									<label class="control-label col-sm-3">Loại hàng: </label>

									<div class="col-sm-8">
										<c:set var="typeGoods" value="${sessionScope.typeGoods }" />
										<c:forEach var="row" items="${typeGoods }">
											<c:if
												test="${row.goodsCategoryId==order.deal.goods.goodsCategoryID}">
												<label for="right-label" class="left inline">${row.name}</label>
											</c:if>
										</c:forEach>
									</div>
								</div>
								<div class="col-sm-12">
									<label class="control-label col-sm-3">Khối lượng: </label>
									<div class="col-sm-8">
										<label for="left-label" class="left inline">${order.deal.goods.weight}
											kg</label>
									</div>
								</div>
								<div class="col-sm-12">
									<label class="control-label col-sm-3"> Địa chỉ giao: </label>
									<div class="col-sm-5">
										<label for="right-label" class="left inline">${order.deal.goods.pickupAddress}</label>
									</div>
									<label class="control-label col-sm-1"> Ngày: </label>
									<div class="col-sm-2">
										<c:set var="stringPickupTime"
											value="${order.deal.goods.pickupTime}" />
										<fmt:parseDate value="${stringPickupTime}"
											var="datePickupTime" pattern="yyyy-MM-dd HH:mm:ss.SSS" />
										<fmt:formatDate value="${datePickupTime}" pattern="dd-MM-yyyy"
											var="pickUpTimeFormatted" />
										<label for="right-label" class="left inline">${pickUpTimeFormatted}</label>
									</div>
								</div>
								<div class="col-sm-12">
									<label class="control-label col-sm-3"> Địa chỉ nhận: </label>
									<div class="col-sm-5">
										<label for="right-label" class="left inline">${order.deal.goods.deliveryAddress}</label>
									</div>
									<label class="control-label col-sm-1">Ngày: </label>
									<div class="col-sm-2">
										<c:set var="stringDeliveryTime"
											value="${order.deal.goods.deliveryTime}" />
										<fmt:parseDate value="${stringDeliveryTime}"
											var="dateDeliveryTime" pattern="yyyy-MM-dd HH:mm:ss.SSS" />
										<fmt:formatDate value="${dateDeliveryTime}"
											pattern="dd-MM-yyyy" var="deliveryTimeFormatted" />
										<label for="right-label" class="left inline">${deliveryTimeFormatted}</label>
									</div>
								</div>
								<div class="col-sm-12">
									<label class="control-label col-sm-3">Chi phí: </label>
									<div class="col-sm-3">
									<input
										type="hidden" name="txtPrice" value="${order.price }" />
										<label for="right-label" class="left inline"> <fmt:formatNumber
												type="currency" pattern="###,###,###,###,###"
												value="${order.price}" /> nghìn đồng
										</label>
									</div>
								</div>
								<div class="col-sm-12">
									<label class="control-label col-sm-3">Trạng thái: </label>
									<div class="col-sm-4">
										<label for="right-label" class="left inline"> <c:if
												test="${order.orderStatusID==1 }">
											Chưa thanh toán
										</c:if> <c:if test="${order.orderStatusID==2 }">
											Đã thanh toán
										</c:if> <c:if test="${order.orderStatusID==3 }">
											Đang vận chuyển
										</c:if> <c:if test="${order.orderStatusID==4 }">
											Đã giao hàng
										</c:if> <c:if test="${order.orderStatusID==5 }">
											Hủy
										</c:if> <c:if test="${order.orderStatusID==6 }">
											Hoàn trả tiền
										</c:if> <c:if test="${order.orderStatusID==7 }">
											Hoàn tất
										</c:if>
										</label>
									</div>
								</div>
								<div class="col-sm-12">
									<br />
									<c:if test="${order.orderStatusID==5}">
										<button class="btn btn-success" type="submit"
											value="employeePay" name="btnAction">Thanh toán</button>
										<button class="btn btn-warning" type="submit"
											value="employeeRefund" name="btnAction">Hoàn trả
											tiền</button>
									</c:if>
								</div>
							</div>
						</form>
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
