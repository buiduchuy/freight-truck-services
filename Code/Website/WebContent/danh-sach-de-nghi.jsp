<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<title>Gợi ý hệ thống</title>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="header.jsp" />
<section class="container">
<center>
	<div class="form-content"
		style="border: 1px solid #ccc; box-shadow: 1px 1px 2px 2px #CCC; margin-bottom: 50px; width: 800px;">

		<div class="form-content">
			<form action="#" method="post" accept-charset="utf-8"
				enctype="multipart/form-data" data-abide="" novalidate="novalidate">
				<div class="row">
					<div class="large-12 columns">
						<h2 class="page-title">
							<font color="orange">Danh sách đề nghị</font>
						</h2>
					</div>
					<div class="large-12 columns">
						<ul class="tabs" data-tab>
							<li class="tab-title active"><a href="#panel1">Đề nghị
									đã gửi</a></li>
							<li class="tab-title"><a href="#panel2">Đề nghị đã nhận</a></li>
						</ul>
						<div class="tabs-content">
							<div class="content active" id="panel1">
								<table id="example" class="display" cellspacing="0"
											width="100%">
									<thead>
										<tr>
											<th><h4>
													<font color="orange">#</font>
												</h4></th>
											<th width="200"><h3>
													<font color="orange">Địa điểm bắt đầu</font>
												</h3></th>
											<th width="200"><h3>
													<font color="orange">Địa điểm kết thúc</font>
												</h3></th>

											<th width="100"><h3>
													<font color="orange">Điểm uy tín</font>
												</h3></th>
											<th><h4>
													<font color="orange"></font>
												</h4></th>
										</tr>
									</thead>
									<tbody>
										<c:set var="dealByOwner" value="${sessionScope.dealByOwner}" />
										<c:set var="listRouteSuggest"
											value="${sessionScope.listRouteSuggest}" />
										<c:if
											test="${not empty dealByOwner && not empty listRouteSuggest}">
											<c:set var="countOwner" value="0" />
											<c:forEach var="dealOwner" items="${ dealByOwner}">
												<c:forEach var="listRoute" items="${ listRouteSuggest}">
													<c:if test="${listRoute.routeID==dealOwner.routeID }">
														<c:set var="countOwner" value="${countOwner+1 }" />
														<tr>
															<td>${countOwner}</td>
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
															<td><a class="button alert"
																onclick="return confirm('Bạn có chắc khi huỷ đề nghị không?')"
																href="Controller?btnAction=deleteDealSent&idDeal=${dealOwner.dealID }"><i
																	class="icon-remove"></i> Huỷ đề nghị</a></td>
														</tr>
													</c:if>
												</c:forEach>
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
											<th><h4>
													<font color="orange">#</font>
												</h4></th>
											<th width="200"><h3>
													<font color="orange">Địa điểm bắt đầu</font>
												</h3></th>
											<th width="200"><h3>
													<font color="orange">Địa điểm kết thúc</font>
												</h3></th>

											<th width="100"><h3>
													<font color="orange">Điểm uy tín</font>
												</h3></th>
											<th><h4>
													<font color="orange"></font>
												</h4></th>
										</tr>
									</thead>
									<tbody>
										<c:set var="dealByDriver" value="${sessionScope.dealByDriver}" />
										<c:set var="listRouteSuggest"
											value="${sessionScope.listRouteSuggest}" />
										<c:if
											test="${not empty dealByDriver && not empty listRouteSuggest}">
											<c:set var="countDriver" value="0" />
											<c:forEach var="dealDriver" items="${ dealByDriver}">
												<c:forEach var="listRoute" items="${ listRouteSuggest}">
													<c:if test="${listRoute.routeID==dealDriver.routeID }">
														<c:set var="countDriver" value="${countDriver+1 }" />
														<tr>
															<td>${countDriver}</td>
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
																
																href="Controller?btnAction=deleteDealSent&idDeal=${dealOwner.dealID }"><i
																	class="icon-remove"></i> Xem Chi Tiết</a></td>
														</tr>
													</c:if>
												</c:forEach>
											</c:forEach>
										</c:if>
									</tbody>
								</table>
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

<jsp:include page="footer.jsp" />