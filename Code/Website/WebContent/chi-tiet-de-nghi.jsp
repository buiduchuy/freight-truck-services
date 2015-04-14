<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<title>Chi tiết đề nghị</title>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<fmt:setLocale value="vi_VN" />
<jsp:include page="header.jsp" />
<c:set var="historyDeal" value="${requestScope.listDealDetail}" />
<c:set var="sizeHistory" value="${requestScope.sizeHistory}" />
<div class="container">
	<div class="large-12 columns">
		<div class="row">
			<div class="large-12 columns">
				<nav class="breadcrumbs left" id="login-form">
					<a href="ProcessServlet">Trang chủ</a> <a
						href="ProcessServlet?btnAction=manageDeal">Quản lý đề nghị</a> <a
						class="current" href="#">Chi tiết đề nghị</a>
				</nav>
			</div>
		</div>
		<br />
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
					<form action="DealServlet" method="post" accept-charset="utf-8">
						<div class="row">
							<div class="large-12 columns">
								<h2 class="page-title">
									<font color="orange">Chi tiết đề nghị</font>
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
								<div class="extra-title">

									<h3>
										<font color="blue">Lịch sử đề nghị</font>
									</h3>
								</div>
								<table id="example" class="display" cellspacing="0" width="100%">
									<thead>
										<tr>
											<th><font color="orange">#</font></th>
											<th><font color="orange">THỜI GIAN</font></th>
											<th><font color="orange">NGƯỜI GỬI</font></th>
											<th><font color="orange">NGƯỜI NHẬN</font></th>
											<th><font color="orange">GIÁ</font></th>
											<th><font color="orange">GHI CHÚ</font></th>
											<th><font color="orange">TRẠNG THÁI</font></th>
											<th></th>
										</tr>
									</thead>
									<tbody>
										<c:set var="isPending" value="0" />
										<c:set var="count" value="0" />
										<c:if test="${not empty historyDeal }">
											<c:forEach var="history" items="${historyDeal }">
												<c:set var="count" value="${count+1 }" />
												<tr>
													<td>${count }</td>
													<td><c:set var="createTime"
															value="${history.createTime}" /> <fmt:parseDate
															value="${createTime}" var="dateCreateTime"
															pattern="yyyy-MM-dd HH:mm:ss.SSS" /> <fmt:formatDate
															value="${dateCreateTime}" pattern="hh:mm dd-MM-yyyy"
															var="createTimeFormatted" /> <c:out
															value="${createTimeFormatted}" /></td>
													<c:if test="${history.createBy eq 'owner'}">
													<td>Chủ hàng: ${history.goods.owner.email}</td>
													<td>Tài xế:  ${history.route.driver.email}</td>
													</c:if>
													<c:if test="${history.createBy eq 'driver'}">
													<td>Tài xế: ${history.route.driver.email}</td>
													<td>Chủ hàng: ${history.goods.owner.email}</td>
													</c:if>
													<td><fmt:formatNumber type="currency"
															pattern="###,###,###,###,###" value="${history.price }" />
													</td>
													<td>${history.notes }</td>
													<c:if test="${history.dealStatusID==1 }">
														<td>Đang chờ</td>
													</c:if>
													<c:if test="${history.dealStatusID==2 }">
														<td>Đã chấp nhận</td>
													</c:if>
													<c:if test="${history.dealStatusID==3 }">
														<td>Đã từ chối</td>
													</c:if>
													<c:if test="${history.dealStatusID==4 }">
														<td>Đã hủy</td>
													</c:if>
													<c:choose>
														<c:when test="${count==sizeHistory}">
															<c:choose>
																<c:when test="${history.dealStatusID==1}">
																	<c:choose>
																		<c:when test="${history.createBy== 'driver'}">
																			<td><a class="button alert"
																				href="DealServlet?btnAction=declineDeal&dealID=${history.dealID }"
																				onclick="return confirm('Bạn có muốn từ chối đề nghị này không?')">Từ
																					chối</a> <a class="button success"
																				href="DealServlet?btnAction=confirmDeal&dealID=${history.dealID }"
																				onclick="return confirm('Bạn có chấp nhận đề nghị này không?')">Chấp
																					nhận</a></td>

																		</c:when>
																		<c:when test="${history.createBy== 'owner'}">
																			<td><a class="button alert"
																				href="DealServlet?btnAction=cancelDeal&dealID=${history.dealID }"
																				onclick="return confirm('Bạn có muốn huỷ đề nghị này không?')">Huỷ</a>
																			</td>
																		</c:when>
																	</c:choose>
																</c:when>
																<c:otherwise>
																	<td></td>
																</c:otherwise>
															</c:choose>
														</c:when>
														<c:otherwise>
															<td></td>
														</c:otherwise>
													</c:choose>
												</tr>
											</c:forEach>
										</c:if>
									</tbody>
								</table>
								</br>
							</div>
							</br>
					</form>
					<form action="DealServlet" method="post" accept-charset="utf-8">
						<div class="large-12 columns">
							</br>
							<div class="extra-title">
								<h3>
									<font color="blue">Phản hồi</font>
								</h3>
							</div>
							<div class="row">
								<div class="small-3 columns">
									<label for="right-label" class="right inline"><small
										class="validate">*</small> Giá thương lượng: </label>
								</div>
								<div class="small-6 columns">
									<input onkeypress="return keyPhone(event);" type="text"
										id="right-label" placeholder="" required=""
										data-errormessage-value-missing="Vui lòng nhập giá thương lượng !"
										name="txtPrice" />
								</div>
								<div class="small-3 columns">
									<label for="right-label" class="left inline">nghìn đồng</label>
								</div>
							</div>
							<div class="row">
								<div class="small-3 columns">
									<label for="right-label" class="right inline">Ghi chú:
									</label>
								</div>
								<div class="small-6 columns">
									<textarea maxlength="250" name="txtNotes"></textarea>
								</div>
								<div class="small-3 columns"></div>
								<div class="submit-area">
									<div class="submit-area right">
										<button name="btnAction" value="sendDeal"
											onclick="return confirm('Bạn có muốn gửi đề nghị này không?')">
											<i class="icon-mail-forward"></i> Gửi
										</button>
									</div>
								</div>
								</br>
							</div>
							</br>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
</div>

<script>
    function keyPhone(e)
    {
        var keyword = null;
        if (window.event)
        {
            keyword = window.event.keyCode;
        } else
        {
            keyword = e.which; //NON IE;
        }

        if (keyword < 48 || keyword > 57)
        {
            if (keyword == 48 || keyword == 127)
            {
                return;
            }
            return false;
        }
    }

</script>
<script>
		function reset () {
			$("#toggleCSS").attr("href", "css/alertify.default.css");
			alertify.set({
				labels : {
					ok     : "Đồng ý",
					cancel : "Không đồng ý"
				},
				delay : 5000,
				buttonReverse : false,
				buttonFocus   : "ok"
			});
		}

		// ==============================
		// Standard Dialogs
	

		$("#update").on( 'click', function () {
			reset();
			alertify.confirm("Bạn có chấp nhận thương lượng không?", function (e) {
				if (e) {
                                    document.location.href="Controller?btnAction=test";  
				} else {
				}
			});
			return false;
		});
		$("#delete").on( 'click', function () {
			reset();
			alertify.confirm("Bạn có muốn từ chối thương lượng này không?", function (e) {
				if (e) {
					document.location.href="Controller?btnAction=#";  
				} else {
				}
			});
			return false;
		});
	</script>
<jsp:include page="footer.jsp" />