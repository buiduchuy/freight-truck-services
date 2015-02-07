<%-- 
    Document   : quan-ly-hang
    Created on : Jan 30, 2015, 11:21:10 AM
    Author     : KhuongNguyen-PC
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<title>Quản lý hàng</title>
<jsp:include page="header.jsp" />
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="router" value="${sessionScope.router}" />
<c:set var="good" value="${sessionScope.good}" />
<c:set var="price" value="${sessionScope.price}" />
<c:set var="namePage" value="${sessionScope.namePage}" />
<c:set var="typeGoods" value="${sessionScope.typeGoods }" />
<section class="container">
	<center>
		<div class="form-content"
			style="border: 1px solid #ccc; box-shadow: 1px 1px 2px 2px #CCC; margin-bottom: 50px; width: 850px;">

			<div class="form-content">
				<form action="#" method="post" accept-charset="utf-8"
					enctype="multipart/form-data" data-abide="" novalidate="novalidate">
					<div class="row">
						<div class="large-12 columns">
							<h2 class="page-title">
								<font color="orange">Quản lý hàng</font>
							</h2>
						</div>
						<div class="large-12 columns">
							<div class="filter-bar">
								<form action="#" accept-charset="utf-8" id="frm-list-lading"
									method="GET">
									<div class="row">
										<div class="large-3 columns">
											<select required
												data-errormessage-value-missing="Vui lòng chọn loại hàng !"
												name="ddlgoodsCategoryID">
												<c:forEach var="row" items="${typeGoods }">
													<c:choose>
														<c:when test="${row.goodsCategoryId==1 }">
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
							<div class="large-12 columns">
								<ul class="tabs" data-tab>
									<li class="tab-title active"><a href="#panel1">Hàng
											chưa giao dịch</a></li>
									<li class="tab-title"><a href="#panel2">Hàng đang giao
											dich</a></li>
								</ul>
								<div class="tabs-content">
									<div class="content active" id="panel1">
										<table id="example" class="display" cellspacing="0"
											width="100%">
											<thead>
												<tr>
													<th><input type="checkbox" class="toggle-all"
														value="0"></th>
													<th width="125"><font color="orange">LOẠI HÀNG</font></th>
													<th width="225"><font color="orange">THỜI GIAN
															GIAO HÀNG</font></th>
													<th width="250"><font color="orange">THỜI GIAN
															NHẬN HÀNG</font></th>

													<th width="200"><h4>
															<font color="orange"></font>
														</h4></th>
												</tr>
											</thead>
											<tbody>
												<c:set var="list1" value="${sessionScope.listGood1 }" />
												<c:if test="${not empty list1 }">
													<c:forEach var="good1" items="${list1 }">
														<tr>
															<td><input type="checkbox" class="toggle-all"
																value="0"></td>
																<c:forEach var="row" items="${typeGoods }">
																<c:if test="${good1.goodsCategoryID==row.goodsCategoryId }">
																<td>${row.name }</td>
																</c:if>
																</c:forEach>
															<td>${good1.pickupTime }</td>
															<td>${good1.deliveryTime }</td>
															<td><a class="button" href="Controller?btnAction=viewDetailGood1&idGood=${good1.goodsID }">Xem chi tiết</a></td>
														</tr>
													</c:forEach>

												</c:if>
											</tbody>

										</table>
										</br>
										<div class="pull-left">
											<select name="smart-action"
												style="width: 180px; margin-right: 10px;">
												<option value="0">-- Chức năng --</option>

												<option value="cancel" selected="">Xoá nhanh hàng</option>
											</select> <a class="button " id="delete">Thực hiện</a>
										</div>
									</div>
									<div class="content" id="panel2">
										<table id="example1" class="display" cellspacing="0"
											width="100%">
											<thead>
												<tr>
													<th width="125"><font color="orange">STT</font></th>
													<th width="125"><font color="orange">LOẠI HÀNG</font></th>
													<th width="225"><font color="orange">THỜI GIAN
															GIAO HÀNG</font></th>
													<th width="250"><font color="orange">THỜI GIAN
															NHẬN HÀNG</font></th>

													<th width="200"><h4>
															<font color="orange"></font>
														</h4></th>
												</tr>
											</thead>
											<tbody>
												<c:set var="list2" value="${sessionScope.listGood2 }" />
												<c:if test="${not empty list2 }">
													<c:forEach var="good2" items="${list2 }">
														<tr>
															<td><input type="checkbox" class="toggle-all"
																value="0"></td>
																<c:forEach var="row" items="${typeGoods }">
																<c:if test="${good2.goodsCategoryID==row.goodsCategoryId }">
																<td>${row.name }</td>
																</c:if>
																</c:forEach>
															<td>${good2.pickupTime }</td>
															<td>${good2.deliveryTime }</td>
															<td><a class="button" href="Controller?btnAction=viewDetailGood2&idGood=${good1.goodsID }">Xem chi tiết</a></td>
														</tr>
													</c:forEach>

												</c:if>
											</tbody>

										</table>
									</div>

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
<script>
	function reset() {
		$("#toggleCSS").attr("href", "css/alertify.default.css");
		alertify.set({
			labels : {
				ok : "Đồng ý",
				cancel : "Không đồng ý"
			},
			delay : 5000,
			buttonReverse : false,
			buttonFocus : "ok"
		});
	}

	// ==============================
	// Standard Dialogs

	$("#update").on('click', function() {
		reset();
		alertify.confirm("Bạn có muốn cập nhật hàng không?", function(e) {
			if (e) {
				document.location.href = "Controller?btnAction=test";
			} else {
			}
		});
		return false;
	});
	$("#delete").on('click', function() {
		reset();
		alertify.confirm("Bạn có muốn xoá hàng không?", function(e) {
			if (e) {
				document.location.href = "Controller?btnAction=#";
			} else {
			}
		});
		return false;
	});
</script>
<jsp:include page="footer.jsp" />