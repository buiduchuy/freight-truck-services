<%@page contentType="text/html" pageEncoding="UTF-8"%>
<title>Quản lý hàng</title>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<c:set var="namePage" value="${sessionScope.namePage}" />
<c:set var="typeGoods" value="${sessionScope.typeGoods }" />
<jsp:include page="header.jsp" />
<script src="js/foundation-datepicker.js"></script>
<link rel="stylesheet" href="css/foundation-datepicker.css">

<div class="container">
	<div class="large-12 columns">
		<div class="small-3 columns">
			<div class="form-content"
				style="border: 1px solid #ccc; box-shadow: 1px 1px 2px 2px #CCC; margin-bottom: 50px; width: 100%;">
				<jsp:include page="vertical-menu-manage-good.jsp" />
				<div class="row"></div>
			</div>
		</div>
		<div class="small-9 columns">
			<div class="form-content"
				style="border: 1px solid #ccc; box-shadow: 1px 1px 2px 2px #CCC; margin-bottom: 50px; width: 100%;">
				<div class="form-content">
					<div class="row">
						<div class="large-12 columns">
							<h2 class="page-title">
								<font color="orange">Quản lý hàng</font>
							</h2>
						</div>
						<div class="large-12 columns">
							<div class="filter-bar">
								<div class="row">
									<div class="large-3 columns">
										<select required
											data-errormessage-value-missing="Vui lòng chọn loại hàng !"
											name="ddlgoodsCategoryID">
											<option value="0" selected="selected">Tất cả</option>
											<c:forEach var="row" items="${typeGoods}">
												<c:choose>
													<c:when test="${row.goodsCategoryId==1 }">
														<option value="${row.goodsCategoryId}">${row.name}</option>
													</c:when>
													<c:otherwise>
														<option value="${row.goodsCategoryId}">${row.name }</option>
													</c:otherwise>
												</c:choose>
											</c:forEach>
										</select>
									</div>

									<div class="large-3 columns">
										<div class="date-picker-wrap">
											<input type="text" value="Từ ngày" name="from_date"
												placeholder="Từ ngày" class="date-picker" /> <i
												class="icon-calendar"></i>
										</div>
									</div>

									<div class="large-3 columns date-picker-wrap">
										<div class="date-picker-wrap">
											<input type="text" value="Đến ngày" name="to_date"
												placeholder="Đến ngày" class="date-picker" /> <i
												class="icon-calendar"></i>
										</div>
									</div>

									<div class="large-3 columns">
										<button class="js_search_lading">
											<i class="icon-filter"></i> Lọc
										</button>
									</div>
								</div>
							</div>
						</div>
						<div class="large-12 columns">
							<c:set var="list" value="${sessionScope.listGoods}" />
							<table id="example" class="display" cellspacing="0" width="100%">
								<thead>
									<tr>
										<th><font color="orange">#</font></th>
										<th><font color="orange">LOẠI HÀNG</font></th>
										<th><font color="orange">THỜI GIAN</font></th>
										<th><font color="orange">KHỐI LƯỢNG</font></th>
										<th><font color="orange">GIÁ</font></th>
										<th><h4>
												<font color="orange"></font>
											</h4></th>
									</tr>
								</thead>
								<tbody>
									<c:if test="${not empty list}">

										<c:set var="count" value="0" />
										<c:forEach var="goods" items="${list}">
											<c:set var="count" value="${count+1}" />
											<form action="ProcessServlet" method="POST"
												accept-charset="utf-8">
												<tr>
													<td>${count }</td>
													<td><c:forEach var="row" items="${typeGoods}">
															<c:if
																test="${goods.goodsCategoryID==row.goodsCategoryId}">
														${row.name}
													</c:if>
														</c:forEach></td>
													<td><c:set var="stringPickupTime"
																	value="${goods.pickupTime}" /> <fmt:parseDate
																	value="${stringPickupTime}" var="datePickupTime"
																	pattern="yyyy-MM-dd HH:mm:ss.SSS" /> <fmt:formatDate
																	value="${datePickupTime}" pattern="dd-MM-yyyy"
																	var="pickUpTimeFormatted" /> Ngày giao: <c:out
																	value="${pickUpTimeFormatted}" /> </br> </br> <c:set
																	var="stringDeliveryTime"
																	value="${goods.deliveryTime}" /> <fmt:parseDate
																	value="${stringDeliveryTime}" var="dateDeliveryTime"
																	pattern="yyyy-MM-dd HH:mm:ss.SSS" /> <fmt:formatDate
																	value="${dateDeliveryTime}" pattern="dd-MM-yyyy"
																	var="deliveryTimeFormatted" /> Ngày nhận: <c:out
																	value="${deliveryTimeFormatted}" />
													</td>
													<td>${goods.weight }<input type="hidden"
														name="goodsID" value="${goods.goodsID}" />
													</td>
													<td><fmt:formatNumber type="number"
															groupingUsed="false" value="${goods.price }" /></td>
													<td><a class="button"
														href="ProcessServlet?btnAction=viewDetailGoods&goodsID=${goods.goodsID}">Chi tiết</a> <!--  <button type="submit" class="button" name="btnAction"
														value="viewDetailGoods">Xem chi tiết</button>
													<button type="submit" class="success" name="btnAction"
														value="getSuggestionRoute">Nhận gợi ý tuyến đường</button>-->
													</td>
												</tr>
											</form>
										</c:forEach>
									</c:if>
								</tbody>
							</table>
							</br>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<jsp:include page="footer.jsp" />
<script>
	$(function() {
		window.prettyPrint && prettyPrint();
		$('#dp1').fdatepicker({
			format : 'dd-mm-yyyy'
		});
		$('#dp2').fdatepicker({
			format : 'dd-mm-yyyy'
		});
	});
</script>


