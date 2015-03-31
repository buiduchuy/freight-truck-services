<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<a class="button menu-button menu-primary-button expand center">Trình đơn</a>

<a href="ProcessServlet?btnAction=createGoods"
	class="button menu-button expand left"><i class="icon-shopping-cart"></i> Tạo hàng</a>
<a href="ProcessServlet?btnAction=manageGoods"
	class="button menu-button expand left"><i class="icon-list"></i> Quản lý hàng</a>
<a href="ProcessServlet?btnAction=manageDeal"
	class="button menu-button expand left"><i class="icon-exchange"></i> Quản lý đề nghị</a>
<a href="ProcessServlet?btnAction=manageOrder"
	class="button menu-button expand left"><i class="icon-list-alt"></i> Quản lý hoá đơn</a>



