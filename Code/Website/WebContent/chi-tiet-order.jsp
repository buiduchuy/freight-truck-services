<%-- 
    Document   : chi-tiet-hang-chua-giao-dich
    Created on : Jan 31, 2015, 12:18:21 AM
    Author     : KhuongNguyen-PC
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<title>Chi tiết hàng</title>
<jsp:include page="header.jsp" />

<c:set var="detailGood1" value="${sessionScope.detailGood2 }" />
<c:if test="${not empty detailGood1 }">
	<section class="container">
		<center>
			<div class="form-content"
				style="border: 1px solid #ccc; box-shadow: 1px 1px 2px 2px #CCC; margin-bottom: 50px; width: 700px;">

				<div class="form-content">
					<form action="Controller" method="post" accept-charset="utf-8">
						<div class="row">
							<div class="large-12 columns">
								<h2 class="page-title">
									<font color="orange">Chi tiết hàng</font>
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
									<h3>Thông tin hàng hoá</h3>
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

													<c:if test="${row.goodsCategoryId==detailGood1.goodsCategoryID}">
													<input type="text" value="${row.name }" readonly="readonly" />
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
										<h3>Địa chỉ giao hàng</h3>
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
										<h3>Địa chỉ nhận hàng</h3>
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
										<h3>Chi phí</h3>
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

							</div>
					</form>


				</div>

			</div>
			</br>

		</center>

	</section>


</c:if>

<jsp:include page="footer.jsp" />
