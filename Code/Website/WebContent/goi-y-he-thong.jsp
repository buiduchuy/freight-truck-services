<%-- 
    Document   : tao-hang-1
    Created on : Jan 30, 2015, 11:21:10 AM
    Author     : KhuongNguyen-PC
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<title>Gợi ý hệ thống</title>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="message" value="${sessionScope.messageCreateGood }" />
<c:set var="dri" value="${sessionScope.listDriver}" />
<jsp:include page="header.jsp" />
<section class="container">
	<center>
		<div class="form-content"
			style="border: 1px solid #ccc; box-shadow: 1px 1px 2px 2px #CCC; margin-bottom: 50px; width: 900px;">

			<div class="form-content">
				<form action="Controller" method="get" accept-charset="utf-8">
					<div class="row">
						<div class="large-12 columns">
							<h2 class="page-title">
								<font color="orange">Gợi ý từ hệ thống</font>
							</h2>
						</div>
						<c:if test="${not empty message}">
							<font color="green">${message}</font>
						</c:if>
						<%
							request.getSession().removeAttribute("messageCreateGood");
						%>
						<div class="large-12 columns">
							<div data-alert="" class="alert-box radius secondary">
								<label class="left"><font color="white" size="+1">Danh
										sách tài xế phù hợp</font></label> </br>
							</div>
							<table id="example" class="display" cellspacing="0" width="100%">
								<thead>
									<tr>
										<th width="50"><h4>
												<font color="orange">#</font>
											</h4></th>
										<th width="150"><font color="orange">Địa điểm bắt
												đầu</font></th>
										<th width="150"><font color="orange">Địa điểm kết
												thúc</font></th>
										<th width="150"><font color="orange">Gía (ngàn
												đồng)</font></th>
										<th width="150"><font color="orange">Điểm uy tín</font></th>
										<th width="150"><a class="button success expand"
											href="Controller?btnAction=viewSuggest"> Các đề nghị </a></th>
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
												<td>1</td>
												<c:if test="${not empty dri }">
													<c:forEach var="driver" items="${dri }">
														<c:if test="${driver.driverID==rows.driverID }">
															<td>${driver.point }</td>
														</c:if>
													</c:forEach>
												</c:if>






												<td><a class="button"
													href="Controller?btnAction=viewDetailRouter&idRouter=${rows.routeID }"">
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
		</br>

	</center>

</section>


<jsp:include page="footer.jsp" />