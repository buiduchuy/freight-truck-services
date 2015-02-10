<%-- 
    Document   : menu-tao-hang
    Created on : Feb 5, 2015, 12:46:19 AM
    Author     : KhuongNguyen-PC
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="router" value="${sessionScope.router}" />
<c:set var="good" value="${sessionScope.good}" />
<c:set var="price" value="${sessionScope.price}" />
<c:set var="namePage" value="${sessionScope.namePage}" />

<li><a href="tao-hang-1.jsp" class="button info expand"><i
		class="icon-truck"></i> Tạo hàng</a></li>
<nav class="">
	<ul class="">
		<c:choose>
			<c:when test="${not empty router}">
				<li><a href="ControllerCreateGoods?btnAction=viewCreate_1"
					class="button secondary expand left"> Địa chỉ
						giao nhận hàng</a></li>
			</c:when>
			<c:when test="${namePage=='tao-hang-1.jsp'}">
				<li><a href="#" class="button secondary expand ">Địa chỉ giao nhận
						hàng</a></li>
			</c:when>
			<c:otherwise>
				<li><a href="#" class="button secondary disabled expand ">Địa chỉ giao
						nhận hàng</a></li>
			</c:otherwise>
		</c:choose>
		<c:choose>
			<c:when test="${not empty good}">
				<li><a href="ControllerCreateGoods?btnAction=viewCreate_2"
					class="button secondary expand "> Thông tin
						hàng hoá</a></li>
			</c:when>
			<c:when test="${namePage=='tao-hang-2.jsp'}">
				<li><a href="#" class="button expand secondary">Thông tin hàng hoá</a></li>
			</c:when>
			<c:otherwise>
				<li><a href="#" class="button disabled expand secondary">Thông tin
						hàng hoá</a></li>
			</c:otherwise>
		</c:choose>
		<c:choose>
			<c:when test="${not empty price}">
				<li><a href="ControllerCreateGoods?btnAction=viewCreate_3"
					class="button secondary expand"> Chi phí</a></li>
			</c:when>
			<c:when test="${namePage=='tao-hang-3.jsp'}">
				<li><a href="#" class="button expand secondary">Chi phí</a></li>
			</c:when>
			<c:otherwise>
				<li><a href="#" class="button disabled expand secondary">Chi phí</a></li>
			</c:otherwise>
		</c:choose>
		<c:choose>
			<c:when test="${namePage=='tao-hang-4.jsp'}">
				<li><a href="#" class="button expand secondary">Xác nhận</a></li>
			</c:when>
			<c:otherwise>
				<li><a href="#" class="button disabled expand secondary">Xác nhận</a></li>
			</c:otherwise>
		</c:choose>
	</ul>
</nav>
<li><a href="ControllerManageGoods?btnAction=manageGoods"class="button info expand"><i
		class="icon-desktop"></i> Quản lý hàng</a></li>
		
<li><a href="ControllerManageGoods?btnAction=manageOrder"class="button info expand"><i
		class="icon-desktop"></i> Quản lý hoá đơn</a></li>
		




