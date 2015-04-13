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
							<div class="row">
								<div class="large-12 columns">
									<div class="row">
										<div class="small-2 columns">
											<label for="right-label" class="right inline"><small
												class="validate">*</small> Loại hàng: </label>
										</div>

										<div class="small-4 columns">
											<c:set var="typeGoods" value="${sessionScope.typeGoods }" />
											<c:forEach var="row" items="${typeGoods }">
												<c:if
													test="${row.goodsCategoryId==order.deal.goods.goodsCategoryID}">
													<input type="text" value="${row.name }" readonly="readonly" />
												</c:if>
											</c:forEach>
										</div>
										<div class="small-2 columns">
											<label for="right-label" class="right inline"><small
												class="validate">*</small> Khối lượng: </label>
										</div>
										<div class="small-2 columns">
											<input type="text" id="right-label" name="txtWeight"
												onkeypress="return keyPhone(event);"
												placeholder="Nhập khối lượng hàng" required=""
												data-errormessage-value-missing="Vui lòng nhập khối lượng của hàng !"
												maxlength="5" value="${order.deal.goods.weight}"
												readonly="readonly" />
										</div>
										<div class="small-2 columns">
											<label for="right-label" class="left inline">kg</label>
										</div>
									</div>
									<div class="row">
										<div class="small-2 columns">
											<label for="right-label" class="right inline">Ghi chú
												: </label>
										</div>
										<div class="small-8 columns left inline">
											<textarea maxlength="250" name="txtNotes" readonly="readonly">${order.deal.goods.notes }</textarea>
										</div>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="extra-title">
									<h3>
										<font color="blue">Địa chỉ giao hàng</font>
									</h3>
								</div>
								<div class="row">
									<div class="small-8 columns">
										<div class="small-3 columns">
											<label class="right inline"><small class="validate">*</small>
												Địa chỉ: </label>
										</div>
										<div class="small-9 columns">
											<input class="left inline"
												value="${order.deal.goods.pickupAddress}"
												readonly="readonly" name="txtpickupAddress" type="text"
												onFocus="geolocate()" id="place_start" pattern=".{1,100}"
												placeholder="Nhập địa điểm giao hàng" required=""
												data-errormessage-value-missing="Vui lòng chọn địa điểm giao hàng !"
												data-errormessage-pattern-mismatch="Bạn phải nhập địa chỉ [1-100] kí tự !" />
										</div>
									</div>
									<div class="small-4 columns">
										<div class="small-5 columns">
											<label for="right-label" class="right inline"><small
												class="validate">*</small> Ngày: </label>
										</div>
										<div class="small-7 columns">
											<c:set var="stringPickupTime"
												value="${order.deal.goods.pickupTime}" />
											<fmt:parseDate value="${stringPickupTime}"
												var="datePickupTime" pattern="yyyy-MM-dd HH:mm:ss.SSS" />
											<fmt:formatDate value="${datePickupTime}"
												pattern="dd-MM-yyyy" var="pickUpTimeFormatted" />
											<input type="text" name="txtpickupTime"
												value="${pickUpTimeFormatted}" id="pick-up-date"
												data-date-format="dd-mm-yyyy" readonly>
										</div>
									</div>
								</div>

								<div class="extra-title">
									<h3>
										<font color="blue">Địa chỉ nhận hàng</font>
									</h3>
								</div>
								<div class="row">
									<div class="small-8 columns">
										<div class="small-3 columns">
											<label for="right-label" class="right inline"><small
												class="validate">*</small> Địa chỉ: </label>
										</div>
										<div class="small-9 columns">
											<input type="text" readonly="readonly" onFocus="geolocate()"
												value="${order.deal.goods.deliveryAddress}"
												name="txtdeliveryAddress" id="place_end" pattern=".{1,100}"
												placeholder="Nhập địa điểm nhận hàng" required=""
												data-errormessage-value-missing="Vui lòng chọn địa điểm nhận hàng !"
												data-errormessage-pattern-mismatch="Bạn phải nhập địa chỉ [1-100] kí tự !" />
										</div>
									</div>
									<div class="small-4 columns">
										<div class="small-5 columns">
											<label for="right-label" class="right inline"><small
												class="validate">*</small> Ngày: </label>
										</div>
										<div class="small-7 columns">

											<c:set var="stringDeliveryTime"
												value="${order.deal.goods.deliveryTime}" />
											<fmt:parseDate value="${stringDeliveryTime}"
												var="dateDeliveryTime" pattern="yyyy-MM-dd HH:mm:ss.SSS" />
											<fmt:formatDate value="${dateDeliveryTime}"
												pattern="dd-MM-yyyy" var="deliveryTimeFormatted" />
											<input type="text" name="txtdeliveryTime"
												value="${deliveryTimeFormatted}" id="dilivery-date"
												data-date-format="dd-mm-yyyy" readonly>
										</div>
									</div>
								</div>

							</div>
							<div class="row">
								<div class="extra-title">
									<h3>
										<font color="blue">Chi phí</font>
									</h3>
								</div>
								<div class="row">
									<div class="small-2 columns">
										<label class="right inline">Chi phí: </label>
									</div>
									<div class="small-4 columns left">
										<input type="text" id="right-label"
											value="<fmt:formatNumber type="number"
															groupingUsed="false" value="${order.price}" />"
											readonly="readonly" />
									</div>
								</div>
							</div>

							<div class="row">
								<div class="extra-title">
									<h3>
										<font color="blue">Thông tin tuyến đường</font>
									</h3>
								</div>
								<div class="row">
									<div class="small-4 columns ">
										<label class="right inline">Địa điểm bắt đầu:</label>
									</div>
									<div class="small-6 columns left">
										<input type="text" id="startAddress" class="left inline"
											value="${order.deal.route.startingAddress }"
											readonly="readonly">
									</div>
								</div>
								<div class="row">
									<div class="small-4 columns ">
										<label class="right inline">Thời gian bắt đầu:</label>
									</div>
									<div class="small-6 columns left">
										<c:set var="stringStartTime"
											value="${order.deal.route.startTime}" />
										<fmt:parseDate value="${stringStartTime}" var="dateStartTime"
											pattern="yyyy-MM-dd HH:mm:ss.SSS" />
										<fmt:formatDate value="${dateStartTime}" pattern="dd-MM-yyyy"
											var="startTimeFormatted" />
										<input type="text" class="left inline"
											value="<c:out value="${startTimeFormatted}" />"
											readonly="readonly">
									</div>
								</div>
								<div class="row">
									<div class="small-4 columns ">
										<label class="right inline">Địa điểm kết thúc:</label>
									</div>
									<div class="small-6 columns left">
										<input type="text" id="endAddress" class="left inline"
											value="${order.deal.route.destinationAddress }"
											readonly="readonly">
									</div>
								</div>
								<div class="row">
									<div class="small-4 columns ">
										<label class="right inline">Thời gian kết thúc:</label>
									</div>
									<div class="small-6 columns left">
										<c:set var="stringFinishTime"
											value="${order.deal.route.finishTime }" />
										<fmt:parseDate value="${stringFinishTime}"
											var="dateFinishTime" pattern="yyyy-MM-dd HH:mm:ss.SSS" />
										<fmt:formatDate value="${dateFinishTime}" pattern="dd-MM-yyyy"
											var="finishTimeFormatted" />
										<input type="text" class="left inline"
											value="<c:out value="${finishTimeFormatted}"/>"
											readonly="readonly">
									</div>
								</div>
								<div class="row">
									<div class="small-4 columns ">
										<label class="right inline">Khối lượng có thể chở:</label>
									</div>
									<div class="small-6 columns left">
										<input type="text" class="left inline"
											value="${order.deal.route.weight } kg" readonly="readonly">
									</div>
								</div>
								<div class="row">
									<div class="small-4 columns ">
										<label class="right inline">Ghi chú:</label>
									</div>
									<div class="small-6 columns left">
										<input type="text" class="left inline"
											value="${order.deal.route.notes }" readonly="readonly">
									</div>
								</div>

							</div>
							<div class="row">
								<div class="extra-title">
									<h3>
										<font color="blue">Trạng thái hoá đơn</font>
									</h3>
								</div>
								<div class="row">
									<div class="small-4 columns">
										<label class="right inline">Trạng thái </label>
									</div>
									<div class="small-4 columns left">
										<c:if test="${order.orderStatusID ==1}">
											<input type="text" id="right-label" value="Đang vận chuyển"
												readonly="readonly" />
										</c:if>

										<c:if test="${order.orderStatusID ==2}">
											<input type="text" id="right-label" value="Đã giao hàng"
												readonly="readonly" />
										</c:if>

										<c:if test="${order.orderStatusID ==3}">
											<input type="text" id="right-label" value="Báo mất hàng"
												readonly="readonly" />
										</c:if>
									</div>
								</div>
							</div>
							<div class="row">

								<div class="large-12 columns">
									<div class="submit-area">
										<c:if test="${order.orderStatusID==2}">
											<a class="button alert"
												href="OrderServlet?btnAction=lostGoods&orderID=${order.orderID}"
												onclick="return confirm('Bạn có muốn báo mất hàng không?')">
												Báo mất hàng </a>
										</c:if>
										<a class="button success" href="#"> In hóa đơn </a>
									</div>
								</div>
							</div>
							<div class="row"></div>
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
