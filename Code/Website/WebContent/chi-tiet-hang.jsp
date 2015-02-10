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

<c:set var="detailGood1" value="${sessionScope.detailGood1 }" />
<c:if test="${not empty detailGood1 }">
	<section class="container">
		<center>
			<div class="form-content"
				style="border: 1px solid #ccc; box-shadow: 1px 1px 2px 2px #CCC; margin-bottom: 50px; width: 700px;">

				<div class="form-content">
					<form action="ControllerManageGoods" method="post" accept-charset="utf-8">
						<div class="row">
							<div class="large-12 columns">
								<h2 class="page-title">
									<font color="orange">Chi tiết hàng</font>
								</h2>
							<div class="row">
						<button class="right" name="btnAction" value="suggestFromSystem">Gợi ý từ hệ thống</button>
						<input type="hidden" name="txtIdGood" value="${detailGood1.goodsID }"/>
						</div>
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
													maxlength="5" value="${detailGood1.weight}" />
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
												<textarea maxlength="250" name="txtNotes">${detailGood1.notes }</textarea>
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
										<h3>Địa chỉ nhận hàng</h3>
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
										<h3>Chi phí</h3>
									</div>
									<div class="row">
										<div class="small-4 columns">
											<label class="right inline">Chi phí tài xế: </label>
										</div>
										<div class="small-4 columns left">
											<input type="text" id="right-label" name="txtPrice"
												value="${detailGood1.price}" />
										</div>
									</div>

								</div>
								<div class="row">
									<div class="large-12 columns">
										<div class="submit-area">
											<a href="ControllerManageGoods?btnAction=manageGoods"
												class="button secondary"> <i class="icon-mail-reply"></i>
												Trở về trước
											</a>
											<button class="button "
												onclick="return confirm('Bạn có muốn cập nhật hàng không?')"
												name="btnAction" value="updateGood">
												<i class="icon-wrench"></i> Cập nhật hàng
											</button>
											<button class="button  alert"
												onclick="return confirm('Bạn có mướn huỷ hàng không?')"
												name="btnAction" value="deleteGood">
												<i class="icon-remove"> Xoá hàng</i>
											</button>

										</div>
										</br>
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
<script> src = "https://maps.googleapis.com/maps/api/js?v=3.exp&signed_in=true&libraries=places" ></script>
<script>
            // Script autocomplete place for location pick up and location delivery
            // of the Google Places API to help users fill in the information.

            var placeSearch, place_start, place_end
    var options = {
        types: ['geocode'],
        componentRestrictions: {country: "vn"}
    };
    function auto() {
        place_start = new google.maps.places.Autocomplete(
                (document.getElementById('place_start')), options);
        place_end = new google.maps.places.Autocomplete(
                (document.getElementById('place_end')), options);
    }

    function geolocate() {
        if (navigator.geolocation) {
            navigator.geolocation.getCurrentPosition(function (position) {
                var geolocation = new google.maps.LatLng(
                        position.coords.latitude, position.coords.longitude);
                var circle = new google.maps.Circle({
                    center: geolocation,
                    radius: position.coords.accuracy
                });
                autocomplete.setBounds(circle.getBounds());
            });
        }
    }
</script>
<!-- end -->

<script>
            $(function () {
                window.prettyPrint && prettyPrint();
                $('#d-pick-up-date').fdatepicker({
                });
                $('#d-dilivery-date').fdatepicker({
                });
                var nowTemp = new Date();
                var now = new Date(nowTemp.getFullYear(), nowTemp.getMonth(), nowTemp.getDate(), 0, 0, 0, 0);
                var checkin = $('#pick-up-date').fdatepicker({
                    onRender: function (date) {
                        return date.valueOf() < now.valueOf() ? 'disabled' : '';
                    }
                }).on('changeDate', function (ev) {
                    if (ev.date.valueOf() > checkout.date.valueOf()) {
                        var newDate = new Date(ev.date)
                        newDate.setDate(newDate.getDate() + 1);
                        checkout.update(newDate);
                    }
                    checkin.hide();
                 
                }).data('datepicker');
                
                var checkout = $('#dilivery-date').fdatepicker({
                    onRender: function (date) {
                        return date.valueOf() <= checkin.date.valueOf() ? 'disabled' : '';
                    }
                }).on('changeDate', function (ev) {
                    checkout.hide();
                }).data('datepicker');
                
                var checkout1 = $('#dilivery-date').fdatepicker({
                    onRender: function (date) {
                        return date.valueOf() >= (checkin.date.valueOf()+3) ? 'disabled' : '';
                    }
                }).on('changeDate1', function (ev) {
                    checkout1.hide();
                }).data('datepicker');
            });
        </script>
<jsp:include page="footer.jsp" />
