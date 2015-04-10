<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html lang="en">
<c:set var="typeGoods" value="${sessionScope.typeGoods }" />
<head>

<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">

<title>Quản lý giao dịch</title>

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
							Quản lý giao dịch
							<!--  <small>Subheading</small>-->
						</h1>
						<ol class="breadcrumb">
							<li><i class="fa fa-dashboard"></i> <a
								href="ProcessServlet?btnAction=aIndex">Dashboard</a></li>
							<li class="active"><i class="fa fa-exchange"></i> Quản lý
								giao dịch</li>
						</ol>
					</div>
					<div class="col-lg-12">
						<table class="table table-bordered table-hover table-striped">
							<thead>
								<tr>
									<th><font color="orange">#</font></th>
									<th><font color="orange">MÃ HĐ</font></th>
									<th><font color="orange">LOẠI HÀNG</font></th>
									<th><font color="orange">THỜI GIAN</font></th>
									<th><font color="orange">GIÁ TIỀN<br/>(NGHÌN VNĐ)</font></th>
									<th><font color="orange">KHỐI LƯỢNG<br/>(KG)</font></th>
									<th><font color="orange">TRẠNG THÁI</font></th>
									<th><h4>
											<font color="orange"></font>
										</h4></th>
								</tr>
							</thead>
							<tbody>
								<c:set var="count" value="0" />
								<c:set var="listOrder" value="${requestScope.listOrder }" />
								<c:set var="listOrderStatus" value="${requestScope.listOrderStatus }" />
								<c:if test="${not empty listOrder }">
									<c:forEach var="order" items="${listOrder}">
									<form action="OrderServlet" method="POST" accept-charset="UTF-8">
										<c:set var="count" value="${count+1 }" />
										<tr>
											<td>${count }<input type="hidden" name="txtOrderID"
										value="${order.orderID }" /> </td>
											<td><font color="black" style="font-weight: 700;">OD${order.orderID }</font></td>
											<c:forEach var="row" items="${typeGoods }">
												<c:if
													test="${order.deal.goods.goodsCategoryID==row.goodsCategoryId }">
													<td>${row.name }</td>
												</c:if>
											</c:forEach>
											<td>
											<c:set var="stringPickupTime" value="${order.deal.goods.pickupTime}" /> 
															<fmt:parseDate value="${stringPickupTime}" var="datePickupTime"	pattern="yyyy-MM-dd HH:mm:ss.SSS" /> 
															<fmt:formatDate	value="${datePickupTime}" pattern="dd-MM-yyyy" var="pickUpTimeFormatted" />
															Ngày giao: <c:out value="${pickUpTimeFormatted}" />
											
											 </br>
											<c:set var="stringDeliveryTime" value="${order.deal.goods.deliveryTime}" /> 
															<fmt:parseDate value="${stringDeliveryTime}" var="dateDeliveryTime"	pattern="yyyy-MM-dd HH:mm:ss.SSS" /> 
															<fmt:formatDate	value="${dateDeliveryTime}" pattern="dd-MM-yyyy" var="deliveryTimeFormatted" />
															Ngày nhận: <c:out value="${deliveryTimeFormatted}" /></td>
											<td><fmt:formatNumber type="number"
															groupingUsed="false" value="${order.price}" /></td>
											<td>${order.deal.goods.weight }</td>
											<td>
											<select class="form-control">
											<c:forEach var="rowOrderStatus" items="${listOrderStatus }">
											<option value="${rowOrderStatus.orderStatusID }">
											<c:if test="${rowOrderStatus.orderStatusID==1 }">
												Đang vận chuyển
											</c:if>
											<c:if test="${rowOrderStatus.orderStatusID==2 }">
												Đã chấp nhận
											</c:if>
											<c:if test="${rowOrderStatus.orderStatusID==3 }">
												Mất hàng
											</c:if>
											</option>
											</c:forEach>
											</select>
											
											</td>
											
											<td>
											<button class="btn btn-sm btn-success" type="submit" value="employeeUpdateOrderStatus" name="btnAction">Cập nhật</button>
											<button class="btn btn-sm btn-info" type="submit" value="employeeViewDetailOrder" name="btnAction">Chi tiết</button>
											</td>
											<!--  <td><a class="button"
												href="ProcessServlet?btnAction=viewDetailOrder&orderID=${order.orderID }">Xem
													chi tiết</a></td>-->
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

	<!-- Morris Charts JavaScript -->
	<script src="admin/js/plugins/morris/raphael.min.js"></script>
	<script src="admin/js/plugins/morris/morris.min.js"></script>
	<script src="admin/js/plugins/morris/morris-data.js"></script>

</body>

</html>
