<%-- 
    Document   : tao-hang-1
    Created on : Jan 30, 2015, 11:21:10 AM
    Author     : KhuongNguyen-PC
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<title>Gợi ý hệ thống</title>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="dri" value="${sessionScope.listDriver}" />
<c:set var="detailGood1" value="${sessionScope.detailGood1}" />
<jsp:include page="header.jsp" />
<div class="large-12 columns">
	<div class="large-2 columns">
		<div class="form-content"
			style="border: 1px solid #ccc; box-shadow: 1px 1px 2px 2px #CCC; margin-bottom: 50px; width: 100%;">
		<jsp:include page="menu-doc-quan-ly-hang.jsp"/>
		</div>
	</div>
	<div class="large-8 columns">
		<div class="form-content"
			style="border: 1px solid #ccc; box-shadow: 1px 1px 2px 2px #CCC; margin-bottom: 50px; width: 100%;">
		

			<div class="form-content">
				<form action="ControllerMakeDeal" method="get"
					accept-charset="utf-8">
					<div class="row">
						<div class="large-12 columns">
							<h2 class="page-title">
								<font color="orange">Danh sách các lộ trình thích hợp</font>
							</h2>
							<c:set var="messageSuccess"
									value="${sessionScope.messageSuccess }" />
								<c:set var="messageError"
									value="${sessionScope.messageError }" />
								<c:if test="${not empty messageSuccess}">
									<div class="row">
										<div data-alert class="alert-box success radius inline">
											${messageSuccess} <a href="#" class="close">&times;</a>
										</div>
									</div>
									<%
										request.getSession().removeAttribute(
														"messageSuccess");
									%>
								</c:if>
								<c:if test="${not empty messageError}">
									<div class="row">
										<div data-alert class="alert-box alert radius inline">
											${messageError} <a href="#" class="close">&times;</a>
										</div>

									</div>
									<%
										request.getSession().removeAttribute(
														"messageError");
									%>
								</c:if>
						</div>
					
						<div class="large-12 columns">
						
							<table id="example" class="display" cellspacing="0" width="100%">
								<thead>
									<tr>
										<th width="50"><h4>
												<font color="orange">#</font>
											</h4></th>
										<th ><font color="orange">Địa điểm bắt
												đầu</font></th>
										<th width="150"><font color="orange">Địa điểm kết
												thúc</font></th>
											<th width="150"><font color="orange">Điểm uy tín</font></th>
										<th width="150"></th>
									</tr>

								</thead>



								<tbody>
									<c:set var="rou" value="${sessionScope.listRouter}" />
									<c:if test="${not empty rou}">
										<c:set var="count" value="0" />
										<c:forEach var="rows" items="${rou}">
											<tr>
												<c:set var="count" value="${count+1 }" />
												<td>${count}</td>
												<td>${rows.startingAddress}</td>
												<td>${rows.destinationAddress}</td>
												<c:if test="${not empty dri }">
													<c:forEach var="driver" items="${dri }">
														<c:if test="${driver.driverID==rows.driverID }">
															<td>${driver.point }</td>
														</c:if>
													</c:forEach>
												</c:if>






												<td><a class="button"
													href="ControllerMakeDeal?btnAction=viewDetailRouter&idRouter=${rows.routeID }"">
														<i class="icon-ok"></i> Xem chi tiết
												</a></td>
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
	<div class="large-2 columns">
		<div class="form-content"
			style="border: 1px solid #ccc; box-shadow: 1px 1px 2px 2px #CCC; margin-bottom: 50px; width: 100%;">
		</div>
	</div>
</div>










<jsp:include page="footer.jsp" />