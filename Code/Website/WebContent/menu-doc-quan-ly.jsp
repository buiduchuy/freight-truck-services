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
<nav class=""></nav>
<li><a href="ControllerManageGoods?btnAction=manageGoods"
	class="button info expand"><i class="icon-desktop"></i> Quản lý
		hàng</a></li>

<li><a href="ControllerManageOrder?btnAction=manageOrder"
	class="button info expand"><i class="icon-desktop"></i> Quản lý hoá
		đơn</a></li>





