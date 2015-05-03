<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html lang="en">

<head>

<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">

<title>Danh sách thông báo</title>

<jsp:include page="header.jsp"></jsp:include>
</head>

<body onload="getLastID();getListNotification();">

	<div id="wrapper">

		<!-- Navigation -->
		<jsp:include page="navigation-bar.jsp"></jsp:include>
		<!-- End Navigation -->

		<div id="page-wrapper">

			<div class="container-fluid">

				<!-- Page Heading -->
				<div class="row">
					<div class="col-lg-12">
						<h1 class="page-header">
							Thông báo 
							<!--  <small>Subheading</small>-->
						</h1>
						<ol class="breadcrumb">
							<li><i class="fa fa-dashboard"></i> <a href="ProcessServlet?btnAction=aIndex">Dashboard</a>
							</li>
							<li class="active"><i class="fa fa-newspaper-o"></i> Danh sách thông báo</li>
						</ol>
					</div>
				</div>
				<!-- /.row -->

			</div>
			<!-- /.container-fluid -->

		</div>
		<!-- /#page-wrapper -->

	</div>
	<!-- /#wrapper -->

	<!-- jQuery -->
	<script src="admin/js/jquery.js"></script>

	<!-- Bootstrap Core JavaScript -->
	<script src="admin/js/bootstrap.min.js"></script>

</body>

</html>
