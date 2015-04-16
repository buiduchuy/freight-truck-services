<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<title>Gợi ý hệ thống</title>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="listDriver" value="${requestScope.listDriver}" />
<c:set var="detailGoods" value="${requestScope.detailGoods}" />
<jsp:include page="header.jsp" />
<div class="container">
	<div class="large-12 columns">
	<div class="row">
	<div class="large-12 columns">
		<nav class="breadcrumbs left" id="login-form">
			<a href="ProcessServlet">Trang chủ</a> <a href="ProcessServlet?btnAction=manageGoods">Quản lý hàng</a> <a href="ProcessServlet?btnAction=viewDetailGoods&goodsID=${detailGoods.goodsID}">Chi tiết hàng</a> <a class="current" href="#">Gợi ý từ hệ thống</a>
		</nav>
	</div>
</div>
<br/>
		<div class="large-3 columns">
			<div class="form-content"
				style="border: 1px solid #ccc; box-shadow: 1px 1px 2px 2px #CCC; margin-bottom: 50px; width: 100%;">
				<jsp:include page="vertical-menu-manage-suggestion.jsp" />
				<div class="row"></div>
			</div>
		</div>
		<div class="large-9 columns">
			<div class="form-content"
				style="border: 1px solid #ccc; box-shadow: 1px 1px 2px 2px #CCC; margin-bottom: 50px; width: 100%;">
				<div class="form-content">
					<form action="DealServlet" method="POST" accept-charset="utf-8">
							<div class="row">
								<div class="large-12 columns">
									<h2 class="page-title">
										<font color="orange">Các tuyến đường phù hợp</font>
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
									<table id="example" class="display" cellspacing="0"
										width="100%">
										<thead>
											<tr>
												<th><font color="orange">#</font></th>
												<th style="width: 100px;"><font color="orange">ĐIỂM
														BẮT ĐẦU</font></th>
												<th style="width: 100px;"><font color="orange">ĐIỂM
														KẾT THÚC</font></th>
												<th><font color="orange">THỜI GIAN</font></th>
												<th><font color="orange">THAO TÁC</font></th>
											</tr>
										</thead>
										<tbody>
											<c:set var="route" value="${requestScope.listRoute}" />
											<c:if test="${not empty route}">
												<c:set var="count" value="0" />
												<c:forEach var="rows" items="${route}">
													<tr>
														<c:set var="count" value="${count+1 }" />
														<td>${count}</td>
														<td>${rows.startingAddress}</td>
														<td>${rows.destinationAddress}</td>
														<td>
														<c:set var="stringStartTime"
																	value="${rows.startTime}" /> <fmt:parseDate
																	value="${stringStartTime}" var="dateStartTime"
																	pattern="yyyy-MM-dd HH:mm:ss.SSS" /> <fmt:formatDate
																	value="${dateStartTime}" pattern="dd-MM-yyyy"
																	var="startTimeTimeFormatted" /> Ngày đi: <c:out
																	value="${startTimeTimeFormatted}" /> 
														<br /> <br />
															<c:set var="stringFinishTime"
																	value="${rows.finishTime}" /> <fmt:parseDate
																	value="${stringFinishTime}" var="dateFinishTime"
																	pattern="yyyy-MM-dd HH:mm:ss.SSS" /> <fmt:formatDate
																	value="${dateFinishTime}" pattern="dd-MM-yyyy"
																	var="finishTimeFormatted" /> Ngày về: <c:out
																	value="${finishTimeFormatted}" /> 
														</td>
														<!--<c:if test="${not empty listDriver }">
														<c:forEach var="driver" items="${listDriver}">
															<c:if test="${driver.driverID==rows.driverID}">
																<td>${driver.point }</td>
															</c:if>
														</c:forEach>
													</c:if>-->
														<td>
														<!--  <a class="button"
															href="DealServlet?btnAction=routeDetail&routeID=${rows.routeID}&goodsID=${detailGoods.goodsID}">
																Chi tiết</a>-->
																<a class="button success"
												href="DealServlet?btnAction=createDeal&routeID=${rows.routeID }&goodsID=${detailGoods.goodsID}">
												<i class="icon-envelope"></i> Gửi để nghị</a>
													   </td>
													</tr>
												</c:forEach>
											</c:if>
										</tbody>
									</table>
									</br> </br>
								</div>
							</div>
					</form>
				</div>
			</div>
		</div>
	</div>
</div>

<jsp:include page="footer.jsp" />