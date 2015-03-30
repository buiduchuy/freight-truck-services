<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<a class="button alert expand center">MENU</a>

<a href="tao-hang-1.jsp" class="button info expand center">Tạo
			hàng</a>
<a href="GoodsServlet?btnAction=manageGoods"
	class="button info expand left">Quản lý hàng</a>
	<ul class="">

	<li><a href="GoodsServlet?btnAction=suggestFromSystem&txtIdGood=${detailGood1.goodsID }" class="button expand secondary">Gợi ý lộ trình
			phù hợp</a></li>
	<li><a href="DealServlet?btnAction=viewSuggest&txtIdGood=${detailGood1.goodsID }" class="button expand secondary">Danh sách các đề nghị</a></li>
</ul>
<a href="OrderServlet?btnAction=manageOrder"
	class="button info expand left">Quản lý hoá đơn</a>

