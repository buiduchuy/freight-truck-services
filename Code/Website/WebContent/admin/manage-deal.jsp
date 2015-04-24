<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<fmt:setLocale value="vi_VN" />
<html lang="en">
<c:set var="typeGoods" value="${sessionScope.typeGoods }" />
<head>

<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">

<title>Thông tin thương lượng</title>

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
							Quản lý thương lượng
							<!--  <small>Subheading</small>-->
						</h1>
						<ol class="breadcrumb">
							<li><i class="fa fa-dashboard"></i> <a
								href="ProcessServlet?btnAction=aIndex">Tổng quan</a></li>
							<li class="active"><i class="fa fa-exchange"></i> Quản lý
								thương lượng</li>
						</ol>
					</div>
					<div class="col-lg-12">
						<form action="DealServlet" method="POST" accept-charset="UTF-8">
							Tìm kiếm thương lượng theo chủ hàng: <input type="text"
								name="txtSearch" /></input>
							<button type="submit" value="searchDealByOwnerEmail" name="btnAction">Tìm
								kiếm</button>
						</form>
						<br/>
						<table class="table table-bordered table-hover table-striped">
							<thead>
								<tr>
									<th><font color="orange">#</font></th>
									<th><font color="orange">THỜI GIAN</font></th>
									<th><font color="orange">NGƯỜI GỬI</font></th>
									<th><font color="orange">NGƯỜI NHẬN</font></th>
									<th><font color="orange">GIÁ<br />(NGHÌN)
									</font></th>
									<th><font color="orange">GHI CHÚ </font></th>
									<th><font color="orange">TRẠNG THÁI</font></th>
								</tr>
							</thead>
							<tbody>

								<c:set var="count" value="0" />

								<c:set var="listDeal" value="${requestScope.listDeal }" />
								<c:if test="${not empty listDeal }">
									<c:forEach var="deal" items="${listDeal}">

										<c:set var="count" value="${count+1 }" />
										<tr>
											<td>${count }</td>
											<td><c:set var="createTime" value="${deal.createTime}" />
												<fmt:parseDate value="${createTime}" var="dateCreateTime"
													pattern="yyyy-MM-dd HH:mm:ss.SSS" /> <fmt:formatDate
													value="${dateCreateTime}" pattern="hh:mm dd-MM-yyyy"
													var="createTimeFormatted" /> <c:out
													value="${createTimeFormatted}" /></td>
											<c:if test="${deal.createBy eq 'owner'}">
												<td>Chủ hàng: ${deal.goods.owner.firstName}</td>
												<td>Tài xế: ${deal.route.driver.firstName}</td>
											</c:if>
											<c:if test="${deal.createBy eq 'driver'}">
												<td>Tài xế: ${deal.route.driver.firstName}</td>
												<td>Chủ hàng: ${deal.goods.owner.firstName}</td>
											</c:if>
											<td><fmt:formatNumber type="currency"
													pattern="###,###,###,###,###" value="${deal.price }" /></td>
											<td>${deal.notes }</td>
											<c:if test="${deal.dealStatusID==1 }">
												<td>Đang chờ</td>
											</c:if>
											<c:if test="${deal.dealStatusID==2 }">
												<td>Đã chấp nhận</td>
											</c:if>
											<c:if test="${deal.dealStatusID==3 }">
												<td>Đã từ chối</td>
											</c:if>
											<c:if test="${deal.dealStatusID==4 }">
												<td>Đã hủy</td>
											</c:if>
											<!--<td>
												
												<c:if test="${order.orderStatusID==4 }">
														<button class="btn btn-sm btn-warning" type="submit"
															value="employeeRefund" name="btnAction">Hoàn
															tiền</button>
														<button class="btn btn-sm btn-warning" type="submit"
															value="employeePayForDriver" name="btnAction">Thanh
															toán</button>
													</c:if></td>-->

											<!--<td>
													<button class="btn btn-sm btn-warning" type="submit"
														value="employeeViewDetailOrder" name="btnAction">Chi
														tiết</button>
												</td>-->
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

</body>

</html>
