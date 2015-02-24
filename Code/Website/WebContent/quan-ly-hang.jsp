<%-- 
    Document   : quan-ly-hang.jsp
    Created on : Jan 30, 2015, 11:21:10 AM
    Author     : KhuongNguyen-PC
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<title>Quản lý hàng</title>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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
		<div class="form-content "
			style="border: 1px solid #ccc; box-shadow: 1px 1px 2px 2px #CCC; margin-bottom: 50px; width: 100%;">
			<jsp:include page="vertical-pr.jsp" />
			<div class="row"></div>
		</div>
	</div>
	<div class="small-9 columns">
		<div class="form-content"
			style="border: 1px solid #ccc; box-shadow: 1px 1px 2px 2px #CCC; margin-bottom: 50px; width: 100%;">
			<div class="form-content">
				<form action="ControllerManageGoods" method="post" accept-charset="utf-8">
					<div class="row">
						<div class="large-12 columns">
							<h2 class="page-title">
								<font color="orange">Quản lý hàng</font>
							</h2>
						</div>




						<div class="large-12 columns">
							<div class="filter-bar">

								<div class="row">
									<div class="large-5 columns">
										<div class="large-4 columns ">
											<label class="right inline">Loại hàng: </label>
										</div>
										<div class="large-8 columns">
											<select required
												data-errormessage-value-missing="Vui lòng chọn loại hàng !"
												name="ddlgoodsCategoryID">
												<option value="0" selected="selected">Tất cả</option>
												<c:forEach var="row" items="${typeGoods }">


													<option value="${row.goodsCategoryId }">${row.name }</option>

												</c:forEach>
											</select>
										</div>
									</div>

									<div class="large-5 columns">
										<div class="large-4 columns ">
											<label class="right inline">Ngày giao: </label>
										</div>
										<div class="large-8 columns">
											<input type="text" name="txtstartdate" value="" id="dp1"
												readonly="readonly">
										</div>
										</br> </br> </br>
										<div class="large-4 columns ">
											<label class="right inline">Ngày nhận: </label>
										</div>
										<div class="large-8 columns">
											<input type="text" name="txtenddate" value="" id="dp2"
												readonly="readonly">
										</div>

									</div>



									<div class="large-2 columns">
										<button name="btnAction" value="filter">
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
							<th width="80"><font color="orange">LOẠI HÀNG</font></th>
							<th width="200"><font color="orange">THỜI GIAN GIAO
									NHẬN HÀNG</font></th>

							<th><h4>
									<font color="orange"></font>
								</h4></th>
						</tr>
					</thead>
					<tbody>
						<c:set var="count" value="0" />
						<c:set var="list1" value="${sessionScope.listGood1 }" />
						<c:if test="${not empty list1 }">
							<c:forEach var="good1" items="${list1 }">
								<c:set var="count" value="${count+1 }" />
								<tr>
									<td>${count }</td>
									<c:forEach var="row" items="${typeGoods }">
										<c:if test="${good1.goodsCategoryID==row.goodsCategoryId }">
											<td>${row.name }</td>
										</c:if>
									</c:forEach>
									<td>Ngày giao: ${good1.pickupTime } </br>Ngày
										nhận:${good1.deliveryTime }
									</td>
									<td><a class="button"
										href="ControllerManageGoods?btnAction=viewDetailGood1&idGood=${good1.goodsID }">Xem
											chi tiết</a></td>
								</tr>
							</c:forEach>

						</c:if>
					</tbody>

				</table>
			</div>



			<div class="row"></div>
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
