<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<title>Quản lý hàng</title>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:set var="namePage" value="${sessionScope.namePage}" />
<c:set var="typeGoods" value="${sessionScope.typeGoods }" />
<jsp:include page="header.jsp" />
<script src="js/foundation-datepicker.js"></script>
<link rel="stylesheet" href="css/foundation-datepicker.css">


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
				<form action="GoodsServlet" method="post"
					accept-charset="utf-8">
					<div class="row">
						<div class="large-12 columns">
							<h2 class="page-title">
								<font color="orange">Quản lý hàng</font>
							</h2>
						</div>
						<div class="large-12 columns">
							<div class="filter-bar">

								<form action="OrderServlet" accept-charset="utf-8" id="frm-list-lading"	method="GET">									<div class="row">
										<div class="large-3 columns">
											<select required
												data-errormessage-value-missing="Vui lòng chọn loại hàng !"
												name="ddlgoodsCategoryID">
												<option value="0"
																selected="selected">Tất cả</option>
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
				</form>

			</div>

			<div class="row">
				<table id="example" class="display" cellspacing="0" width="100%">
					<thead>
						<tr>
							<th><font color="orange">#</font></th>
							<th><font color="orange">MÃ HÀNG</font></th>
							<th><font color="orange">LOẠI HÀNG</font></th>
							<th><font color="orange">THỜI GIAN</font></th>

							<th><h4>
									<font color="orange"></font>
								</h4></th>
						</tr>
					</thead>
					<tbody>
						<c:set var="count" value="0" />
						<c:set var="list1" value="${sessionScope.listGoods}" />
						<c:if test="${not empty list1 }">
							<c:forEach var="good1" items="${list1 }">
								<c:set var="count" value="${count+1 }" />
								<tr>
									<td>${count }</td>
									<td> ${fn:substringBefore(fn:replace(good1.createTime, '-', ''),' ')}${good1.goodsID }</td>
									<c:forEach var="row" items="${typeGoods }">
										<c:if test="${good1.goodsCategoryID==row.goodsCategoryId }">
											<td>${row.name }</td>
										</c:if>
									</c:forEach>
									<td>Ngày giao: ${good1.pickupTime }</br></br>Ngày
										nhận: ${good1.deliveryTime }
									</td>
									<td><a class="button"
										href="GoodsServlet?btnAction=viewDetailGood1&idGood=${good1.goodsID }">Xem
											chi tiết</a></td>
								</tr>
							</c:forEach>
						</c:if>
					</tbody>

				</table>
			</div>
			<div class="row"></div>
		</div>
	</div>
</div>
</div>
</div>
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


<jsp:include page="footer.jsp" />
