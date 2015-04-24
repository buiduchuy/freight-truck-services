<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<fmt:setLocale value="vi_VN"/>
<title>Chi tiết hoá đơn</title>
<jsp:include page="header.jsp" />

<script type="text/javascript">
var Msg ='<%=request.getAttribute("message")%>';
	function message() {
		if (Msg == "success") {
			alert("Thanh toán thành công!");
		} else if (Msg == "fail") {
			alert("Thanh toán thất bại!");
		}
	}
</script>

<div class="large-12 columns">
	<div class="row">
		<div class="large-12 columns">
			<nav class="breadcrumbs left" id="login-form">
				<a href="ProcessServlet">Trang chủ</a> <a
					href="ProcessServlet?btnAction=manageOrder">Quản lý hóa đơn</a> <a
					class="current" href="#">Chi tiết hóa đơn</a>
			</nav>
		</div>
	</div>
	<br />
	<div class="large-3 columns">
		<div class="form-content"
			style="border: 1px solid #ccc; box-shadow: 1px 1px 2px 2px #CCC; margin-bottom: 50px; width: 100%;">
			<jsp:include page="vertical-menu-manage-order.jsp" />
			<div class="row"></div>
		</div>
	</div>
	<c:set var="order" value="${requestScope.order}" />
	<div class="large-9 columns">
		<div class="form-content"
			style="border: 1px solid #ccc; box-shadow: 1px 1px 2px 2px #CCC; margin-bottom: 50px; width: 100%;">
			<div class="form-content">
				<form action="ProcessServlet" method="post" accept-charset="utf-8">
					<div class="row">
						<div class="large-12 columns">
							<h2 class="page-title">
								<font color="orange">Chi tiết hoá đơn</font>
							</h2>
							<c:set var="messageSuccess"
								value="${requestScope.messageSuccess }" />
							<c:set var="messageError" value="${requestScope.messageError }" />
							<c:if test="${not empty messageSuccess}">
								<div class="row">
									<div data-alert class="alert-box success radius inline">
										${messageSuccess} <a href="#" class="close">&times;</a>
									</div>
								</div>
							</c:if>
							<c:if test="${not empty messageError}">
								<div class="row">
									<div data-alert class="alert-box alert radius inline">
										${messageError} <a href="#" class="close">&times;</a>
									</div>

								</div>
							</c:if>
						</div>
						<div class="large-12 columns">
							<div class="extra-title">
								<h3>
									<font color="blue">Thông tin hàng hoá</font>
								</h3>
							</div>
							<div class="row">
								<div class="large-12 columns">
										<div class="large-2 columns">
											<label for="right-label" class="right inline">Mã hóa đơn: </label>
										</div>
										<div class="large-10 columns">
											<label for="right-label" class="left inline"><b style="font-weight:700">OD${order.orderID}</b></label>
										</div>
										</div>
										<div class="large-12 columns">
										<div class="large-2 columns">
											<label for="right-label" class="right inline">Loại hàng: </label>
										</div>
										<div class="large-10 columns">
											<c:set var="typeGoods" value="${sessionScope.typeGoods }" />
											<c:forEach var="row" items="${typeGoods }">
												<c:if
													test="${row.goodsCategoryId==order.deal.goods.goodsCategoryID}">
													<label for="right-label" class="left inline">${row.name}</label>
												</c:if>
											</c:forEach>
										</div>
										<div class="large-12 columns">
										<div class="large-2 columns">
											<label for="right-label" class="right inline">Khối lượng: </label>
										</div>
										<div class="large-10 columns">
											<label for="right-label" class="left inline">${order.deal.goods.weight} kg</label>
										</div>
										</div>
								</div>
							</div>
							<div class="row">
								<div class="extra-title">
									<h3>
										<font color="blue">Địa chỉ</font>
									</h3>
								</div>
								<div class="row">
									<div class="small-8 columns">
										<div class="small-3 columns">
											<label class="right inline">
												Địa chỉ giao: </label>
										</div>
										<div class="small-9 columns">
											<label for="right-label" class="left inline">${order.deal.goods.pickupAddress}</label>
										</div>
									</div>
									<div class="small-4 columns">
										<div class="small-5 columns">
											<label for="right-label" class="right inline"> Ngày: </label>
										</div>
										<div class="small-7 columns">
											<c:set var="stringPickupTime"
												value="${order.deal.goods.pickupTime}" />
											<fmt:parseDate value="${stringPickupTime}"
												var="datePickupTime" pattern="yyyy-MM-dd HH:mm:ss.SSS" />
											<fmt:formatDate value="${datePickupTime}"
												pattern="dd-MM-yyyy" var="pickUpTimeFormatted" />
											<label for="right-label" class="left inline">${pickUpTimeFormatted}</label>
										</div>
									</div>
								</div>
								<div class="row">
									<div class="small-8 columns">
										<div class="small-3 columns">
											<label for="right-label" class="right inline"> Địa chỉ nhận: </label>
										</div>
										<div class="small-9 columns">
											<label for="right-label" class="left inline">${order.deal.goods.deliveryAddress}</label>
										</div>
									</div>
									<div class="small-4 columns">
										<div class="small-5 columns">
											<label for="right-label" class="right inline">Ngày: </label>
										</div>
										<div class="small-7 columns">

											<c:set var="stringDeliveryTime"
												value="${order.deal.goods.deliveryTime}" />
											<fmt:parseDate value="${stringDeliveryTime}"
												var="dateDeliveryTime" pattern="yyyy-MM-dd HH:mm:ss.SSS" />
											<fmt:formatDate value="${dateDeliveryTime}"
												pattern="dd-MM-yyyy" var="deliveryTimeFormatted" />
											<label for="right-label" class="left inline">${deliveryTimeFormatted}</label>
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
										<label for="right-label" class="left inline">
										<fmt:formatNumber type="currency" pattern="###,###,###,###,###"
															 value="${order.price}" />
										 nghìn đồng</label>
									</div>
								</div>
							</div>

							<!--  <div class="row">
								<div class="extra-title">
									<h3>
										<font color="blue">Thông tin tuyến đường</font>
									</h3>
								</div>
								<div class="row">
									<div class="small-3 columns ">
										<label class="right inline">Địa điểm bắt đầu:</label>
									</div>
									<div class="small-4 columns left">
										<label for="right-label" class="left inline">${order.deal.route.startingAddress }</label>
									</div>
									<div class="small-3 columns ">
										<label class="right inline">Thời gian bắt đầu:</label>
									</div>
									<div class="small-2 columns left">
										<c:set var="stringStartTime"
											value="${order.deal.route.startTime}" />
										<fmt:parseDate value="${stringStartTime}" var="dateStartTime"
											pattern="yyyy-MM-dd HH:mm:ss.SSS" />
										<fmt:formatDate value="${dateStartTime}" pattern="dd-MM-yyyy"
											var="startTimeFormatted" />
										<label for="right-label" class="left inline">${startTimeFormatted}</label>
									</div>
								</div>
								<div class="row">
									<div class="small-3 columns ">
										<label class="right inline">Địa điểm kết thúc:</label>
									</div>
									<div class="small-4 columns left">
										<label for="right-label" class="left inline">${order.deal.route.destinationAddress }</label>
									</div>
									<div class="small-3 columns ">
										<label class="right inline">Thời gian kết thúc:</label>
									</div>
									<div class="small-2 columns left">
										<c:set var="stringFinishTime"
											value="${order.deal.route.finishTime }" />
										<fmt:parseDate value="${stringFinishTime}"
											var="dateFinishTime" pattern="yyyy-MM-dd HH:mm:ss.SSS" />
										<fmt:formatDate value="${dateFinishTime}" pattern="dd-MM-yyyy"
											var="finishTimeFormatted" />
										<label for="right-label" class="left inline">${finishTimeFormatted}</label>
									</div>
								</div>
								<div class="row">
									<div class="small-3 columns ">
										<label class="right inline">Khối lượng có thể chở:</label>
									</div>
									<div class="small-2 columns left">
										<label for="right-label" class="left inline">${order.deal.route.weight } kg</label>
									</div>
								</div> -->
								<!--  <div class="row">
									<div class="small-3 columns ">
										<label class="right inline">Ghi chú:</label>
									</div>
									<div class="small-9 columns left">
										<input type="text" class="left inline"
											value="${order.deal.route.notes }" readonly="readonly">
									</div>
								</div>-->

							</div>
							<div class="row">
								<div class="extra-title">
									<h3>
										<font color="blue">Trạng thái hoá đơn</font>
									</h3>
								</div>
								<div class="row">
									<div class="small-4 columns">
										<label class="right inline">Trạng thái: </label>
									</div>
									<div class="small-4 columns left">
										<c:if test="${order.orderStatusID ==1}">
											<label for="right-label" class="left inline">Chưa thanh toán</label>
										</c:if>

										<c:if test="${order.orderStatusID ==2}">
										<label for="right-label" class="left inline">Đã thanh toán</label>
											
										</c:if>

										<c:if test="${order.orderStatusID ==3}">
											<label for="right-label" class="left inline">Đang vận chuyển</label>
										
										</c:if>
										
										<c:if test="${order.orderStatusID ==4}">
											<label for="right-label" class="left inline">Đã giao hàng</label>
										</c:if>
										
										<c:if test="${order.orderStatusID ==5}">
											<label for="right-label" class="left inline">Đã hủy</label>
										</c:if>
										
										<c:if test="${order.orderStatusID ==6}">
											<label for="right-label" class="left inline">Hoàn tiền</label>
										</c:if>
										
										<c:if test="${order.orderStatusID ==7}">
											<label for="right-label" class="left inline">Kết thúc</label>
										</c:if>
										
									</div>
								</div>
							</div>
							<div class="row">

								<div class="large-12 columns">
									<div class="submit-area">
									<c:if test="${order.orderStatusID==1 or order.orderStatusID==2}">
									<c:set var="messageCancel" value="" />
									
									<c:if test="${order.orderStatusID==1}">
									<c:set var="messageCancel" value="Bạn đã chắc chắn việc hủy hóa đơn này không?" />
									</c:if>
									
									<c:if test="${order.orderStatusID==2}">
									<c:set var="messageCancel" value="Hệ thống đã đặt tài xế đến vận chuyển hàng của bạn, nếu bạn hủy sẽ bị trừ khoản tiền nhất định. Bạn đã chắc chắn việc hủy hóa đơn này không?" />
									</c:if>
										<a class="button alert"
												href="OrderServlet?btnAction=cancelOrder&orderID=${order.orderID}"
												onclick="return confirm(${messageCancel})">Hủy hóa đơn </a>
									</c:if>
										<c:if test="${order.orderStatusID==1}">
										<a class="button success"
											href="PaypalServlet?btnAction=pay&orderID=${order.orderID}&amount=${order.price}">
											<i class="icon-ok"></i> Thanh toán
										</a> 
										</c:if>
										<a class="button success"
											href="ExportServlet?btnAction=exportOrder&orderID=${order.orderID}">
											<i class="icon-print"></i> In hóa đơn
										</a>
										
										<c:if test="${order.orderStatusID==4}">
										<a class="button alert"
											href="OrderServlet?btnAction=reportOrder&orderID=${order.orderID}">
											Báo mất/ hỏng hàng
										</a>
										</c:if>
									</div>
								</div>
							</div>
							<div class="row"></div>
						</div>
				</form>
			</div>
		</div>
	</div>
</div>
<!--<jsp:include page="footer.jsp" />-->
<script type="text/javascript">
	window.onload = message;
</script>
