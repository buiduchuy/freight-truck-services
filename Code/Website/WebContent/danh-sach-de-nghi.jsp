<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<title>Gợi ý hệ thống</title>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<jsp:include page="header.jsp" />
<c:set var="detailGood1" value="${sessionScope.detailGood1 }" />
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
					enctype="multipart/form-data" data-abide="" novalidate="novalidate">
					<div class="row">
						<div class="row">
							<div class="large-12 columns">
								<h2 class="page-title">
									<font color="orange">Danh sách các đề nghị</font>
								</h2>
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

								<div class="content active" id="panel1">
									<table id="example" class="display" cellspacing="0"
										width="100%">
										<thead>
											<tr>
												<th><font color="orange">#</font></th>
												<th><font color="orange">Mã tuyến đường</font></th>
												<th><font color="orange">Địa điểm bắt đầu</font></th>
												<th><font color="orange">Địa điểm kết thúc</font></th>
												<th><font color="orange">Điểm uy tín</font></th>
												<th><font color="orange"></font></th>
											</tr>
										</thead>
										<tbody>
											<c:set var="listDeal" value="${sessionScope.listDeal}" />
											<c:set var="listRoute" value="${sessionScope.listRoute}" />
											<c:set var="count" value="0" />
											<c:forEach var="listDeal" items="${ listDeal}">
												<c:forEach var="listRoute" items="${ listRoute}">
													<c:if test="${listRoute.routeID==listDeal.routeID }">
														<c:set var="count" value="${count+1 }" />
														<tr>
															<td>${count}</td>
															<td>
																${fn:substringBefore(fn:replace(listRoute.createTime, '-', ''),' ')}${listRoute.routeID }</td>
															<td>${listRoute.startingAddress }</td>
															<td>${listRoute.destinationAddress }</td>
															<c:set var="dri" value="${sessionScope.listDriver}" />
															<c:if test="${not empty dri }">
																<c:forEach var="driver" items="${dri }">
																	<c:if test="${driver.driverID==listRoute.driverID }">
																		<td>${driver.point }</td>
																	</c:if>
																</c:forEach>
															</c:if>
															<td><a class="button "
																href="DealServlet?btnAction=viewDetailDeal&dealID=${listDeal.dealID }"><i
																	class="icon-remove"></i>Xem chi tiết</a></td>

														</tr>

													</c:if>
												</c:forEach>
											</c:forEach>


										</tbody>
									</table>
								</div>




							</div>


						</div>
				</form>


			</div>
		</div>
	</div>

</div>



















<jsp:include page="footer.jsp" />