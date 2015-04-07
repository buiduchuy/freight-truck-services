<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<title>Gợi ý hệ thống</title>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<c:set var="listDriver" value="${requestScope.listDriver}" />
<c:set var="detailGoods" value="${requestScope.detailGoods}" />
<jsp:include page="header.jsp" />
<div class="container">
	<div class="large-12 columns">
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
					<form action="DealServlet" method="get" accept-charset="utf-8">
						<div class="row">
							<div class="row">
								<div class="large-12 columns">
									<h2 class="page-title">
										<font color="orange">Danh sách tuyến đường phù hợp</font>
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
												<th><font color="orange">MÃ</font></th>
												<th><font color="orange">ĐIỂM BẮT ĐẦU</font></th>
												<th><font color="orange">ĐIỂM KẾT THÚC</font></th>
												<!--  <th><font color="orange">ĐIỂM UY TÍN</font></th>-->
												<th></th>
											</tr>
										</thead>
										<tbody>
											<c:set var="route" value="${requestScope.listRouter}" />
											<c:if test="${not empty rou}">
												<c:set var="count" value="0" />
												<c:forEach var="rows" items="${route}">
													<tr>
														<c:set var="count" value="${count+1 }" />
														<td>${count}</td>
														<td>${fn:substringBefore(fn:replace(rows.createTime, '-', ''),' ')}${rows.routeID}</td>
														<td>${rows.startingAddress}</td>
														<td>${rows.destinationAddress}</td>
														<!--<c:if test="${not empty listDriver }">
														<c:forEach var="driver" items="${listDriver}">
															<c:if test="${driver.driverID==rows.driverID}">
																<td>${driver.point }</td>
															</c:if>
														</c:forEach>
													</c:if>-->
														<td><a class="button"
															href="DealServlet?btnAction=routeDetail&routeID=${rows.routeID}"">
																Xem chi tiết</a></td>
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