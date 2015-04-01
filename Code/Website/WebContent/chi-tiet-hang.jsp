<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<title>Chi Tiết Hàng</title>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<jsp:include page="header.jsp" />
<script src="js/foundation-datepicker.js"></script>
<link rel="stylesheet" href="css/foundation-datepicker.css">
<c:set var="detailGood1" value="${sessionScope.detailGood1 }" />
<c:if test="${not empty detailGood1 }">
	<div class="large-12 columns">
		<div class="small-3 columns">
			<div class="form-content"
				style="border: 1px solid #ccc; box-shadow: 1px 1px 2px 2px #CCC; margin-bottom: 50px; width: 100%;">
				<a class="button menu-button menu-primary-button expand center">Trình
					đơn</a> <a href="ProcessServlet?btnAction=createGoods"
					class="button menu-button expand left"
					style="text-align: left; padding-left: 10px"><i
					class="icon-shopping-cart"></i> Tạo hàng</a> <a
					href="ProcessServlet?btnAction=manageGoods"
					class="button menu-button expand left"
					style="text-align: left; padding-left: 10px"><i
					class="icon-list"></i> Quản lý hàng</a> <a
					href="ProcessServlet?btnAction=manageDeal"
					class="button menu-button expand left"
					style="text-align: left; padding-left: 10px"><i
					class="icon-exchange"></i> Quản lý đề nghị</a> <a
					href="ProcessServlet?btnAction=manageOrder"
					class="button menu-button expand left"
					style="text-align: left; padding-left: 10px"><i
					class="icon-list-alt"></i> Quản lý hoá đơn</a>

				<div class="row"></div>
			</div>
		</div>
		<div class="small-9 columns">
			<div class="form-content"
				style="border: 1px solid #ccc; box-shadow: 1px 1px 2px 2px #CCC; margin-bottom: 50px; width: 100%;">
				<div class="form-content">
					<form action="GoodsServlet" method="post" accept-charset="utf-8">
						<div class="row">
							<div class="large-12 columns">
								<h2 class="page-title">
									<font color="orange">Chi tiết hàng</font>

								</h2>
								<input type="hidden" name="txtIdGood"
									value="${detailGood1.goodsID }" />
								<c:set var="messageSuccess"
									value="${sessionScope.messageSuccess }" />
								<c:set var="messageError" value="${sessionScope.messageError }" />
								<c:if test="${not empty messageSuccess}">
									<div class="row">
										<div data-alert class="alert-box success radius inline">
											${messageSuccess} <a href="#" class="close">&times;</a>
										</div>
									</div>
									<%
										request.getSession().removeAttribute("messageSuccess");
									%>
								</c:if>
								<c:if test="${not empty messageError}">
									<div class="row">
										<div data-alert class="alert-box alert radius inline">
											${messageError} <a href="#" class="close">&times;</a>
										</div>

									</div>
									<%
										request.getSession().removeAttribute("messageError");
									%>
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
										<div class="row">
											<div class="small-2 columns">
												<label for="right-label" class="right inline"><small
													class="validate">*</small> Loại hàng: </label>
											</div>
											<c:set var="typeGoods" value="${sessionScope.typeGoods }" />
											<div class="small-4 columns">
												<select required
													data-errormessage-value-missing="Vui lòng chọn loại hàng !"
													name="ddlgoodsCategoryID">
													<c:forEach var="row" items="${typeGoods }">
														<c:choose>
															<c:when
																test="${row.goodsCategoryId==detailGood1.goodsCategoryID}">
																<option value="${row.goodsCategoryId }"
																	selected="selected">${row.name }</option>
															</c:when>
															<c:otherwise>
																<option value="${row.goodsCategoryId }">${row.name }</option>
															</c:otherwise>
														</c:choose>
													</c:forEach>
												</select>
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
													maxlength="5" value="${detailGood1.weight}" />
											</div>
											<div class="small-2 columns">
												<label for="right-label" class="left inline" style="text-align:left">kg</label>
											</div>
										</div>
										<div class="row">
											<div class="small-2 columns">
												<label for="right-label" class="right inline">Ghi
													chú : </label>
											</div>
											<div class="small-8 columns left inline">
												<textarea maxlength="250" name="txtNotes">${detailGood1.notes }</textarea>
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
													value="${detailGood1.pickupAddress}"
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
												<input type="text" onFocus="geolocate()"
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
										<h3>
											<font color="blue">Chi phí</font>
										</h3>
									</div>
									<div class="row">
										<div class="small-4 columns">
											<label class="right inline"><small class="validate">*</small>Chi
												phí tài xế: </label>
										</div>
										<div class="small-4 columns left">
											<input type="text" id="right-label" name="txtPrice"
												value="${detailGood1.price}" required=""
												data-errormessage-value-missing="Vui lòng điền đầy đủ chi phí!" />
										</div>
										<div class="small-4 columns left">
											<label class="left inline">nghìn đồng</label>
										</div>
									</div>
									<c:set var="priceCreateGood"
										value="${sessionScope.priceCreateGood }" />
									<div class="row">
										<div class="small-4 columns">
											<label class="right inline">Chi phí tạo hàng: </label>
										</div>
										<div class="small-4 columns left">
											<input type="text" id="right-label"
												value="${priceCreateGood}" readonly="readonly" />
										</div>
										<div class="small-4 columns left">
											<label class="left inline">nghìn đồng</label>
										</div>
									</div>
									<c:set var="priceTotal" value="${sessionScope.priceTotal }" />
									<div class="row">
										<div class="small-4 columns">
											<label class="right inline">Tổng Cộng: </label>
										</div>
										<div class="small-4 columns left">
											<input type="text" id="right-label" value="${priceTotal}"
												readonly="readonly" />
										</div>
										<div class="small-4 columns left">
											<label class="left inline">nghìn đồng</label>
										</div>
									</div>
								</div>
								<div class="row">
									<div class="large-12 columns">
										<div class="submit-area right">
											<button class="success" name="btnAction"
												value="suggestFromSystem">
												<i class="icon-mail-forward"></i> Nhận gợi ý tuyến đường
											</button>

											<button class="button "
												onclick="return confirm('Bạn có muốn cập nhật hàng không?')"
												name="btnAction" value="updateGood">
												<i class="icon-wrench"></i> Cập nhật hàng
											</button>

											<button class="alert"
												onclick="return confirm('Bạn có muốn xoá hàng không?')"
												name="btnAction" value="deleteGood">
												<i class="icon-remove"> Xoá hàng</i>

											</button>

										</div>
										</br>
									</div>
								</div>
								<div class="row"></div>
							</div>
					</form>
				</div>
			</div>
		</div>

	</div>

</c:if>

<!-- autocomplete place google API -->
<script
	src="https://maps.googleapis.com/maps/api/js?v=3.exp&signed_in=true&libraries=places"></script>
<script>
	// Script autocomplete place for location pick up and location delivery
	// of the Google Places API to help users fill in the information.

	var placeSearch, place_start, place_end
	var options = {
		types : [ 'geocode' ],
		componentRestrictions : {
			country : "vn"
		}
	};
	function auto() {
		place_start = new google.maps.places.Autocomplete((document
				.getElementById('place_start')), options);
		place_end = new google.maps.places.Autocomplete((document
				.getElementById('place_end')), options);
	}

	function geolocate() {
		if (navigator.geolocation) {
			navigator.geolocation.getCurrentPosition(function(position) {
				var geolocation = new google.maps.LatLng(
						position.coords.latitude, position.coords.longitude);
				var circle = new google.maps.Circle({
					center : geolocation,
					radius : position.coords.accuracy
				});
				autocomplete.setBounds(circle.getBounds());
			});
		}
	}
</script>
<!-- end -->

<script>
	$(function() {
		window.prettyPrint && prettyPrint();
		$('#d-pick-up-date').fdatepicker({});
		$('#d-dilivery-date').fdatepicker({});

		var nowTemp = new Date();
		var now = new Date(nowTemp.getFullYear(), nowTemp.getMonth(), nowTemp
				.getDate(), 0, 0, 0, 0);
		var checkin = $('#pick-up-date').fdatepicker({
			onRender : function(date) {
				return date.valueOf() < now.valueOf() ? 'disabled' : '';
			}
		}).on('changeDate', function(ev) {
			if (ev.date.valueOf() > checkout.date.valueOf()) {
				var newDate = new Date(ev.date)
				newDate.setDate(newDate.getDate() + 1);
				checkout.update(newDate);
			}
			checkin.hide();

		}).data('datepicker');

		var checkout = $('#dilivery-date')
				.fdatepicker(
						{
							onRender : function(date) {
								return date.valueOf() <= checkin.date.valueOf() ? 'disabled'
										: '';
							}
						}).on('changeDate', function(ev) {
					checkout.hide();
				}).data('datepicker');

		var checkout1 = $('#dilivery-date')
				.fdatepicker(
						{
							onRender : function(date) {
								return date.valueOf() >= (checkin.date
										.valueOf() + 3) ? 'disabled' : '';
							}
						}).on('changeDate1', function(ev) {
					checkout1.hide();
				}).data('datepicker');
	});
</script>

<jsp:include page="footer.jsp" />
