<%-- 
    Document   : chi-tiet-hang-chua-giao-dich
    Created on : Jan 31, 2015, 12:18:21 AM
    Author     : KhuongNguyen-PC
--%>

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
		<div class="form-content "
			style="border: 1px solid #ccc; box-shadow: 1px 1px 2px 2px #CCC; margin-bottom: 50px; width: 100%;">
			<jsp:include page="vertical-pr.jsp" />
			<div class="row"></div>
		</div>
	</div>
	<div class="large-9 columns">
		<div class="form-content"
			style="border: 1px solid #ccc; box-shadow: 1px 1px 2px 2px #CCC; margin-bottom: 50px; width: 100%;">
			<c:set var="detailGood1" value="${sessionScope.detailOrder }" />
			<c:if test="${not empty detailGood1 }">
				<div class="form-content">
					<form action="Controller" method="post" accept-charset="utf-8">
						<div class="row">
							<div class="large-12 columns">
								<h2 class="page-title">
									<font color="orange">Chi tiết hoá đơn</font>
								</h2>

							</div>
							<c:set var="message" value="${sessionScope.messageUpdateGood }" />
							<c:if test="${not empty message}">
								<font color="green">${message}</font>
							</c:if>
							<%
								request.getSession().removeAttribute("messageUpdateGood");
							%>
							<div class="large-12 columns">
								<div class="extra-title">
								<h3><font color="blue">Thông tin hàng hoá</font>
								</h3>
								</div>


								<div class="row">
									<div class="large-12 columns">
										<div class="row">
											<div class="small-3 columns">
												<label for="right-label" class="right inline"><small
													class="validate">*</small> Loại hàng: </label>
											</div>
											<c:set var="typeGoods" value="${sessionScope.typeGoods }" />
											<div class="small-6 columns">

												<c:forEach var="row" items="${typeGoods }">

													<c:if
														test="${row.goodsCategoryId==detailGood1.goodsCategoryID}">
														<input type="text" value="${row.name }"
															readonly="readonly" />
													</c:if>

												</c:forEach>

											</div>
											<div class="small-3 columns"></div>
										</div>
										<div class="row">
											<div class="small-3 columns">
												<label for="right-label" class="right inline"><small
													class="validate">*</small> Khối lượng: </label>
											</div>
											<div class="small-6 columns">
												<input type="text" id="right-label" name="txtWeight"
													onkeypress="return keyPhone(event);"
													placeholder="Nhập khối lượng hàng" required=""
													data-errormessage-value-missing="Vui lòng nhập khối lượng của hàng !"
													maxlength="5" value="${detailGood1.weight}"
													readonly="readonly" />
											</div>
											<div class="small-3 columns">
												<label for="right-label" class="left inline">Kg</label>
											</div>
										</div>
										<div class="row">
											<div class="small-3 columns">
												<label for="right-label" class="right inline">Ghi
													chú : </label>
											</div>
											<div class="small-6 columns">
												<textarea maxlength="250" name="txtNotes"
													readonly="readonly">${detailGood1.notes }</textarea>
											</div>
											<div class="small-3 columns"></div>
										</div>

									</div>
								</div>
								<div class="row">
									<div class="extra-title">
									<h3><font color="blue">Địa chỉ giao hàng</font></h3>
									</div>
									<div class="row">
										<div class="small-8 columns">
											<div class="small-3 columns">
												<label class="right inline"><small class="validate">*</small>
													Địa chỉ: </label>
											</div>
											<div class="small-9 columns">
												<input class="left inline"
													value="${detailGood1.pickupAddress}" readonly="readonly"
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
													value="${detailGood1.pickupTime}" id="pick-up-date"
													data-date-format="dd-mm-yyyy" readonly>
											</div>
										</div>
									</div>

									<div class="extra-title">
									<h3><font color="blue">Địa chỉ nhận hàng</font></h3>
									</div>
									<div class="row">
										<div class="small-8 columns">
											<div class="small-3 columns">
												<label for="right-label" class="right inline"><small
													class="validate">*</small> Địa chỉ: </label>
											</div>
											<div class="small-9 columns">
												<input type="text" readonly="readonly" onFocus="geolocate()"
													value="${detailGood1.deliveryAddress}"
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
													value="${detailGood1.deliveryTime}" id="dilivery-date"
													data-date-format="dd-mm-yyyy" readonly>
											</div>
										</div>
									</div>

								</div>
								<div class="row">
									<div class="extra-title">
										<h3><font color="blue">Chi phí</font></h3>
									</div>
									<div class="row">
										<div class="small-4 columns">
											<label class="right inline">Chi phí tài xế: </label>
										</div>
										<div class="small-4 columns left">
											<input type="text" id="right-label" name="txtPrice"
												value="${detailGood1.price}" readonly="readonly" />
										</div>
									</div>

								</div>
								<div class="row">
									<div class="extra-title">
										<h3><font color="blue">Tracking hoá đơn</font></h3>
									</div>
									<div class="row">
										<div class="small-4 columns">
											<label class="right inline">Trạng thái </label>
										</div>
										<div class="small-4 columns left">
											<c:set var="orderStatus" value="${sessionScope.orderStatus }" />
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
										
											<fmt:parseDate value="${detailGood1.deliveryTime }"
												var="deliveryTime" pattern="dd-MM-yyyy" />
											<fmt:formatDate pattern="yyyy-MM-dd" value="${deliveryTime}"
												var="checkDay" />
											<jsp:useBean id="today" class="java.util.Date" />
											<fmt:formatDate pattern="yyyy-MM-dd" value="${today}"
												var="current" />
											<c:if test="${checkDay lt current}">
												<c:if test="${orderStatus.orderStatusID!=5 && orderStatus.orderStatusID!=4}">
												<a class="button alert"
													href="ControllerManageOrder?btnAction=lostGood&idGood=${detailGood1.goodsID }"
													onclick="return confirm('Bạn có muốn báo mất hàng không?')">
													<i class="icon-ok"></i> Báo mất hàng
												</a>
												</c:if>
											</c:if>

											<c:if test="${orderStatus.orderStatusID==3}">
												<a class="button success"
													href="ControllerManageOrder?btnAction=confirmOrder&idGood=${detailGood1.goodsID }"
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
