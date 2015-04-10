<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<title>Quản lý đề nghị</title>
<jsp:include page="header.jsp" />
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="namePage" value="${sessionScope.namePage}" />
<c:set var="typeGoods" value="${sessionScope.typeGoods }" />
<div class="container">
	<div class="large-12 columns">
		<div class="large-3 columns">
			<div class="form-content"
				style="border: 1px solid #ccc; box-shadow: 1px 1px 2px 2px #CCC; margin-bottom: 50px; width: 100%;">
				<jsp:include page="vertical-menu-make-deal.jsp" />
				<div class="row"></div>
			</div>
		</div>
		<div class="large-9 columns">
			<div class="form-content"
				style="border: 1px solid #ccc; box-shadow: 1px 1px 2px 2px #CCC; margin-bottom: 50px; width: 100%;">
				<div class="form-content">
					<form action="#" method="post" accept-charset="utf-8"
						enctype="multipart/form-data" data-abide=""
						novalidate="novalidate">
						<div class="row">
							<div class="large-12 columns">
								<h2 class="page-title">
									<font color="orange">Quản lý đề nghị</font>
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
								<div class="filter-bar">
									<form action="ProcessServlet" accept-charset="utf-8"
										id="frm-list-lading" method="POST">

										<div class="row">
											<div class="large-3 columns">
												<select required
													data-errormessage-value-missing="Vui lòng chọn loại hàng !"
													name="ddlgoodsCategoryID">
													<option value="0" selected="selected">Tất cả</option>
													<c:forEach var="row" items="${typeGoods}">
														<c:choose>
															<c:when test="${row.goodsCategoryId==1 }">
																<option value="${row.goodsCategoryId}">${row.name }</option>
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
							</div>

							<div class="large-12 columns">
								<ul class="tabs" data-tab>
									<li class="tab-title active"><a href="#panel1">Đề nghị đang chờ</a></li>
									<li class="tab-title"><a href="#panel2">Đề nghị đã đồng ý</a></li>
									<li class="tab-title"><a href="#panel3">Tất cả đề nghị</a></li>
								</ul>
								<div class="tabs-content">
									<div class="content active" id="panel1">
										<table id="example" class="display" cellspacing="0"
											width="100%">
											<thead>
												<tr>
													<th><font color="orange">#</font></th>
													<th><font color="orange">LOẠI HÀNG</font></th>
													<th><font color="orange">THỜI GIAN</font></th>
													<th><font color="orange">TRẠNG THÁI</font></th>
													<th><h4>
															<font color="orange"></font>
														</h4></th>
												</tr>
											</thead>
											<tbody>
												<c:set var="count" value="0" />
												<c:set var="listDeal" value="${requestScope.listDeal }" />
												<c:if test="${not empty listDeal }">
													<c:forEach var="deal" items="${listDeal}">
													<c:if test="${deal.dealStatusID==1}">
														<c:set var="count" value="${count+1 }" />
														<tr>
															<td>${count}</td>
															<c:forEach var="row" items="${typeGoods }">
																<c:if
																	test="${deal.goods.goodsCategoryID==row.goodsCategoryId }">
																	<td>${row.name }</td>
																</c:if>
															</c:forEach>
															<td><c:set var="stringPickupTime"
																	value="${deal.goods.pickupTime}" /> <fmt:parseDate
																	value="${stringPickupTime}" var="datePickupTime"
																	pattern="yyyy-MM-dd HH:mm:ss.SSS" /> <fmt:formatDate
																	value="${datePickupTime}" pattern="dd-MM-yyyy"
																	var="pickUpTimeFormatted" /> Ngày giao: <c:out
																	value="${pickUpTimeFormatted}" /> </br> </br> <c:set
																	var="stringDeliveryTime"
																	value="${deal.goods.deliveryTime}" /> <fmt:parseDate
																	value="${stringDeliveryTime}" var="dateDeliveryTime"
																	pattern="yyyy-MM-dd HH:mm:ss.SSS" /> <fmt:formatDate
																	value="${dateDeliveryTime}" pattern="dd-MM-yyyy"
																	var="deliveryTimeFormatted" /> Ngày nhận: <c:out
																	value="${deliveryTimeFormatted}" /></td>
															<c:if test="${deal.dealStatusID==1 }">
																<td>Đang chờ</td>
															</c:if>
															<c:if test="${deal.dealStatusID==2 }">
																<td>Đã chấp nhận</td>
															</c:if>
															<c:if test="${deal.dealStatusID==3 }">
																<td>Đã từ chối</td>
															</c:if>
															<c:if test="${deal.dealStatusID==4 }">
																<td>Đã hủy</td>
															</c:if>
															<td><a class="button"
																href="ProcessServlet?btnAction=viewDetailDeal&dealID=${deal.dealID }">Chi tiết</a></td>
														</tr>
														</c:if>
													</c:forEach>
												</c:if>
											</tbody>
										</table>
									</div>
									<div class="content" id="panel2">
										<table id="example1" class="display" cellspacing="0"
											width="100%">
											<thead>
												<tr>
													<th><font color="orange">#</font></th>
													<th><font color="orange">LOẠI HÀNG</font></th>
													<th><font color="orange">THỜI GIAN</font></th>
													<th><font color="orange">TRẠNG THÁI</font></th>
													<th><h4>
															<font color="orange"></font>
														</h4></th>
												</tr>
											</thead>
											<tbody>
												<c:set var="count" value="0" />
												<c:set var="listDeal" value="${requestScope.listDeal }" />
												<c:if test="${not empty listDeal }">
													<c:forEach var="deal" items="${listDeal}">
													<c:if test="${deal.dealStatusID==2}">
														<c:set var="count" value="${count+1 }" />
														<tr>
															<td>${count}</td>
															<c:forEach var="row" items="${typeGoods }">
																<c:if
																	test="${deal.goods.goodsCategoryID==row.goodsCategoryId }">
																	<td>${row.name }</td>
																</c:if>
															</c:forEach>
															<td><c:set var="stringPickupTime"
																	value="${deal.goods.pickupTime}" /> <fmt:parseDate
																	value="${stringPickupTime}" var="datePickupTime"
																	pattern="yyyy-MM-dd HH:mm:ss.SSS" /> <fmt:formatDate
																	value="${datePickupTime}" pattern="dd-MM-yyyy"
																	var="pickUpTimeFormatted" /> Ngày giao: <c:out
																	value="${pickUpTimeFormatted}" /> </br> </br> <c:set
																	var="stringDeliveryTime"
																	value="${deal.goods.deliveryTime}" /> <fmt:parseDate
																	value="${stringDeliveryTime}" var="dateDeliveryTime"
																	pattern="yyyy-MM-dd HH:mm:ss.SSS" /> <fmt:formatDate
																	value="${dateDeliveryTime}" pattern="dd-MM-yyyy"
																	var="deliveryTimeFormatted" /> Ngày nhận: <c:out
																	value="${deliveryTimeFormatted}" /></td>
															<c:if test="${deal.dealStatusID==1 }">
																<td>Đang chờ</td>
															</c:if>
															<c:if test="${deal.dealStatusID==2 }">
																<td>Đã chấp nhận</td>
															</c:if>
															<c:if test="${deal.dealStatusID==3 }">
																<td>Đã từ chối</td>
															</c:if>
															<c:if test="${deal.dealStatusID==4 }">
																<td>Đã hủy</td>
															</c:if>
															<td><a class="button"
																href="ProcessServlet?btnAction=viewDetailDeal&dealID=${deal.dealID }">Chi tiết</a></td>
														</tr>
														</c:if>
													</c:forEach>
												</c:if>
											</tbody>
										</table>
									</div>
									<div class="content" id="panel3">
										<table id="example2" class="display" cellspacing="0"
											width="100%">
											<thead>
												<tr>
													<th><font color="orange">#</font></th>
													<th><font color="orange">LOẠI HÀNG</font></th>
													<th><font color="orange">THỜI GIAN</font></th>
													<th><font color="orange">TRẠNG THÁI</font></th>
													<th><h4>
															<font color="orange"></font>
														</h4></th>
												</tr>
											</thead>
											<tbody>
												<c:set var="count" value="0" />
												<c:set var="listDeal" value="${requestScope.listDeal }" />
												<c:if test="${not empty listDeal }">
													<c:forEach var="deal" items="${listDeal}">
														<c:set var="count" value="${count+1 }" />
														<tr>
															<td>${count}</td>
															<c:forEach var="row" items="${typeGoods }">
																<c:if
																	test="${deal.goods.goodsCategoryID==row.goodsCategoryId }">
																	<td>${row.name }</td>
																</c:if>
															</c:forEach>
															<td><c:set var="stringPickupTime"
																	value="${deal.goods.pickupTime}" /> <fmt:parseDate
																	value="${stringPickupTime}" var="datePickupTime"
																	pattern="yyyy-MM-dd HH:mm:ss.SSS" /> <fmt:formatDate
																	value="${datePickupTime}" pattern="dd-MM-yyyy"
																	var="pickUpTimeFormatted" /> Ngày giao: <c:out
																	value="${pickUpTimeFormatted}" /> </br> </br> <c:set
																	var="stringDeliveryTime"
																	value="${deal.goods.deliveryTime}" /> <fmt:parseDate
																	value="${stringDeliveryTime}" var="dateDeliveryTime"
																	pattern="yyyy-MM-dd HH:mm:ss.SSS" /> <fmt:formatDate
																	value="${dateDeliveryTime}" pattern="dd-MM-yyyy"
																	var="deliveryTimeFormatted" /> Ngày nhận: <c:out
																	value="${deliveryTimeFormatted}" /></td>
															<c:if test="${deal.dealStatusID==1 }">
																<td>Đang chờ</td>
															</c:if>
															<c:if test="${deal.dealStatusID==2 }">
																<td>Đã chấp nhận</td>
															</c:if>
															<c:if test="${deal.dealStatusID==3 }">
																<td>Đã từ chối</td>
															</c:if>
															<c:if test="${deal.dealStatusID==4 }">
																<td>Đã hủy</td>
															</c:if>
															<td><a class="button"
																href="ProcessServlet?btnAction=viewDetailDeal&dealID=${deal.dealID }">Chi tiết</a></td>
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
			</div>
		</div>
	</div>
</div>
<jsp:include page="footer.jsp" />