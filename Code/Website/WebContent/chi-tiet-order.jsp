<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<title>Chi tiết hoá đơn</title>
<jsp:include page="header.jsp" />
<div class="large-12 columns">
	<div class="large-3 columns">
		<div class="form-content"
			style="border: 1px solid #ccc; box-shadow: 1px 1px 2px 2px #CCC; margin-bottom: 50px; width: 100%;">
			<jsp:include page="vertical-menu-manage-good.jsp" />
			<div class="row"></div>
		</div>
	</div>
	<div class="large-9 columns">
		<div class="form-content"
			style="border: 1px solid #ccc; box-shadow: 1px 1px 2px 2px #CCC; margin-bottom: 50px; width: 100%;">
			<c:set var="detailOrder" value="${requestScope.detailOrder }" />
			<c:if test="${not empty detailOrder }">
				<div class="form-content">
					<form action="Controller" method="post" accept-charset="utf-8">
						<div class="row">
							<div class="large-12 columns">
								<h2 class="page-title">
									<font color="orange">Chi tiết hoá đơn</font>
								</h2>

							</div>
							<div class="large-12 columns">
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
											<c:set var="typeGoods" value="${sessionScope.typeGoods }" />
											<div class="small-4 columns">
												<c:forEach var="row" items="${typeGoods }">
													<c:if
														test="${row.goodsCategoryId==detailOrder.goodsCategoryID}">
														<input type="text" value="${row.name }"
															readonly="readonly" />
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
													maxlength="5" value="${detailOrder.weight}"
													readonly="readonly" />
											</div>
											<div class="small-2 columns">
												<label for="right-label" class="left inline">kg</label>
											</div>
										</div>
										<div class="row">
											<div class="small-2 columns">
												<label for="right-label" class="right inline">Ghi
													chú : </label>
											</div>
											<div class="small-8 columns left inline">
												<textarea maxlength="250" name="txtNotes"
													readonly="readonly">${detailOrder.notes }</textarea>
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
													value="${detailOrder.pickupAddress}" readonly="readonly"
													name="txtpickupAddress" type="text" onFocus="geolocate()"
													id="place_start" pattern=".{1,100}"
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
												<input type="text" name="txtpickupTime"
													value="${detailOrder.pickupTime}" id="pick-up-date"
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
													value="${detailOrder.deliveryAddress}"
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
												<input type="text" name="txtdeliveryTime"
													value="${detailOrder.deliveryTime}" id="dilivery-date"
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
										<div class="small-4 columns">
											<label class="right inline">Chi phí tài xế: </label>
										</div>
										<div class="small-4 columns left">
											<c:set var="priceDriver"
												value="${requestScope.priceForDriver }" />
											<input type="text" id="right-label" value="${priceDriver}"
												readonly="readonly" />
										</div>
									</div>
									<div class="row">
										<div class="small-4 columns">
											<label class="right inline">Chi phí tạo hàng: </label>
										</div>
										<div class="small-4 columns left">
											<c:set var="priceCreate" value="${requestScope.priceCreate }" />
											<input type="text" id="right-label" value="${priceCreate}"
												readonly="readonly" />
										</div>
									</div>
									<div class="row">
										<div class="small-4 columns">
											<label class="right inline">Chi phí tổng cộng: </label>
										</div>
										<div class="small-4 columns left">
											<c:set var="priceTotal" value="${requestScope.priceTotal }" />
											<input type="text" id="right-label" value="${priceTotal}"
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
									<c:set var="detailRoute" value="${requestScope.routeOrder }" />
									<div class="row">
										<div class="small-4 columns ">
											<label class="right inline">Địa điểm bắt đầu:</label>
										</div>
										<div class="small-6 columns left">
											<input type="text" id="startAddress" class="left inline"
												value="${detailRoute.startingAddress }" readonly="readonly">
										</div>
									</div>
									<div class="row">
										<div class="small-4 columns ">
											<label class="right inline">Thời gian bắt đầu:</label>
										</div>
										<div class="small-6 columns left">
											<input type="text" class="left inline"
												value="${detailRoute.startTime }" readonly="readonly">
										</div>
									</div>
									<div class="row">
										<div class="small-4 columns ">
											<label class="right inline">Địa điểm kết thúc:</label>
										</div>
										<div class="small-6 columns left">
											<input type="text" id="endAddress" class="left inline"
												value="${detailRoute.destinationAddress }"
												readonly="readonly">
										</div>
									</div>
									<div class="row">
										<div class="small-4 columns ">
											<label class="right inline">Thời gian kết thúc:</label>
										</div>
										<div class="small-6 columns left">
											<input type="text" class="left inline"
												value="${detailRoute.finishTime }" readonly="readonly">
										</div>
									</div>
									<div class="row">
										<div class="small-4 columns ">
											<label class="right inline">Khối lượng có thể chở:</label>
										</div>
										<div class="small-6 columns left">
											<input type="text" class="left inline"
												value="${detailRoute.weight } Kg" readonly="readonly">
										</div>
									</div>
									<div class="row">
										<div class="small-4 columns ">
											<label class="right inline">Ghi chú:</label>
										</div>
										<div class="small-6 columns left">
											<input type="text" class="left inline"
												value="${detailRoute.notes }" readonly="readonly">
										</div>
									</div>

								</div>
								<div class="row">
									<div class="extra-title">
										<h3>
											<font color="blue">Tracking hoá đơn</font>
										</h3>
									</div>
									<div class="row">
										<div class="small-4 columns">
											<label class="right inline">Trạng thái </label>
										</div>
										<div class="small-4 columns left">
											<c:set var="orderStatus" value="${requestScope.orderStatus }" />
											<c:if test="${not empty orderStatus }">
												<input type="text" id="right-label"
													value="${orderStatus.orderStatusName}" readonly="readonly" />
											</c:if>

										</div>
									</div>

								</div>
								<div class="row">
									<div class="large-12 columns">
										<div class="submit-area">

											<fmt:parseDate value="${detailOrder.deliveryTime }"
												var="deliveryTime" pattern="dd-MM-yyyy" />
											<fmt:formatDate pattern="yyyy-MM-dd" value="${deliveryTime}"
												var="checkDay" />
											<jsp:useBean id="today" class="java.util.Date" />
											<fmt:formatDate pattern="yyyy-MM-dd" value="${today}"
												var="current" />
											<c:if test="${checkDay lt current}">
												<c:if
													test="${orderStatus.orderStatusID!=5 && orderStatus.orderStatusID!=4}">
													<a class="button alert"
														href="OrderServlet?btnAction=lostGood&idGood=${detailOrder.goodsID }"
														onclick="return confirm('Bạn có muốn báo mất hàng không?')">
														<i class="icon-ok"></i> Báo mất hàng
													</a>
												</c:if>
											</c:if>

											<c:if test="${orderStatus.orderStatusID==3}">
												<a class="button success"
													href="OrderServlet?btnAction=confirmOrder&idGood=${detailOrder.goodsID }"
													onclick="return confirm('Bạn có muốn xác nhận hoá đơn không?')">
													<i class="icon-ok"></i> Xác nhận hoá đơn
												</a>
											</c:if>

										</div>
										</br>
									</div>
								</div>
								<div class="row"></div>
							</div>
					</form>


				</div>
			</c:if>
		</div>
	</div>

</div>












<jsp:include page="footer.jsp" />
