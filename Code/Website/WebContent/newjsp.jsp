
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<html>
<c:set var="lat" value="${sessionScope.listDeal }" />

<head>


</head>
<body>
	<c:choose>
		<c:when test="${not empty lat }">
				<c:forEach var="listRoute" items="${ lat}">
				123
				</c:forEach>
		</c:when>
		<c:otherwise>
		AB C
		</c:otherwise>
	</c:choose>

	aaaaaaa
</body>
</html>
