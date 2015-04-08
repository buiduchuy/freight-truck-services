<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
									<th><font color="orange">GIÁ TIỀN</font></th>
									<th><font color="orange">KHỐI LƯỢNG</font></th>
									<th><font color="orange">TRẠNG THÁI</font></th>
									<!--  <th><h4>
											<font color="orange"></font>
										</h4></th>-->
								</tr>
							</thead>
							<tbody>
								<c:set var="count" value="0" />
								<c:set var="listOrder" value="${requestScope.listOrder }" />
								<c:if test="${not empty listOrder }">
									<c:forEach var="order" items="${listOrder}">
										<c:set var="count" value="${count+1 }" />
										<tr>
											<td>${count }</td>
											<td><font color="black" style="font-weight: 700;">OD${order.orderID }</font></td>
											<c:forEach var="row" items="${typeGoods }">
												<c:if
													test="${order.deal.goods.goodsCategoryID==row.goodsCategoryId }">
													<td>${row.name }</td>
												</c:if>
											</c:forEach>
											<td>Ngày giao: ${order.deal.goods.pickupTime}</br> </br>Ngày nhận:
												${order.deal.goods.deliveryTime}
											</td>
											<td>${order.deal.price }</td>
											<td>${order.deal.goods.weight }</td>
											<c:if test="${order.orderStatusID==1 }">
												<td>Đang vận chuyển</td>
											</c:if>
											<c:if test="${order.orderStatusID==2 }">
												<td>Tài xế xác nhận</td>
											</c:if>
											<c:if test="${order.orderStatusID==3 }">
												<td>Chủ hàng xác nhận</td>
											</c:if>
											<c:if test="${order.orderStatusID==4 }">
												<td>Nhân viên xác nhận</td>
											</c:if>
											<c:if test="${order.orderStatusID==5 }">
												<td>Mất hàng</td>
											</c:if>
											<!--  <td><a class="button"
												href="ProcessServlet?btnAction=viewDetailOrder&orderID=${order.orderID }">Xem
													chi tiết</a></td>-->
										</tr>
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
