<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
	<!-- Brand and toggle get grouped for better mobile display -->
	<div class="navbar-header">
		<button type="button" class="navbar-toggle" data-toggle="collapse"
			data-target=".navbar-ex1-collapse">
			<span class="sr-only">Toggle navigation</span> <span class="icon-bar"></span>
			<span class="icon-bar"></span> <span class="icon-bar"></span>
		</button>
		<div class="navbar-header">
			<a class="navbar-brand" href="ProcessServlet?btnAction=aIndex"><img
				src="admin/img/logo.png" width="150px" height="30px"></a>
		</div>
	</div>
	<!-- Top Menu Items -->
	<ul class="nav navbar-right top-nav">
		<!-- <li class="dropdown"><a href="#" class="dropdown-toggle"
			data-toggle="dropdown"><i class="fa fa-envelope"></i> <b
				class="caret"></b></a>
			<ul class="dropdown-menu message-dropdown">
				<li class="message-preview"><a href="#">
						<div class="media">
							<span class="pull-left"> <img class="media-object"
								src="http://placehold.it/50x50" alt="">
							</span>
							<div class="media-body">
								<c:set var="employee" value="${sessionScope.employee}" />
								<c:choose>
									<c:when test="${not empty account}">
										<h5 class="media-heading">
											<strong>${employee.email}</strong>
										</h5>
										<p class="small text-muted">
											<i class="fa fa-clock-o"></i> Yesterday at 4:32 PM
										</p>
										<p>Lorem ipsum dolor sit amet, consectetur...</p>
									</c:when>
									<c:otherwise>
										<h5 class="media-heading">
											<strong>GUEST</strong>
										</h5>
										<p class="small text-muted">
											<i class="fa fa-clock-o"></i> Yesterday at 4:32 PM
										</p>
										<p>Lorem ipsum dolor sit amet, consectetur...</p>
									</c:otherwise>
								</c:choose>
							</div>
						</div>
				</a></li>
				<li class="message-preview"><a href="#">
						<div class="media">
							<span class="pull-left"> <img class="media-object"
								src="http://placehold.it/50x50" alt="">
							</span>
							<div class="media-body">
								<h5 class="media-heading">
									<strong>John Smith</strong>
								</h5>
								<p class="small text-muted">
									<i class="fa fa-clock-o"></i> Yesterday at 4:32 PM
								</p>
								<p>Lorem ipsum dolor sit amet, consectetur...</p>
							</div>
						</div>
				</a></li>
				<li class="message-preview"><a href="#">
						<div class="media">
							<span class="pull-left"> <img class="media-object"
								src="http://placehold.it/50x50" alt="">
							</span>
							<div class="media-body">
								<h5 class="media-heading">
									<strong>John Smith</strong>
								</h5>
								<p class="small text-muted">
									<i class="fa fa-clock-o"></i> Yesterday at 4:32 PM
								</p>
								<p>Lorem ipsum dolor sit amet, consectetur...</p>
							</div>
						</div>
				</a></li>
				<li class="message-footer"><a href="#">Read All New
						Messages</a></li>
			</ul></li>
			-->
		<li class="dropdown"><a href="#" class="dropdown-toggle"
			data-toggle="dropdown"><font color="red" id="notificationSize"></font> <i class="fa fa-bell"></i> <b class="caret"></b></a>
			<ul id="notificationList" class="dropdown-menu alert-dropdown" style="width: 500px;">
				<!-- <li><a href="#">Alert Name <span
						class="label label-default">Alert Badge</span></a></li>
				<li><a href="#">Alert Name <span
						class="label label-primary">Alert Badge</span></a></li>
				<li><a href="#">Alert Name <span
						class="label label-success">Alert Badge</span></a></li>
				<li><a href="#">Alert Name <span class="label label-info">Alert
							Badge</span></a></li>
				<li><a href="#">Alert Name <span
						class="label label-warning">Alert Badge</span></a></li>
				<li><a href="#">Alert Name <span class="label label-danger">Alert
							Badge</span></a></li>
				<li class="divider"></li> -->
				<li><a href="ProcessServlet?btnAction=employeeViewNotification">View All</a></li>
			</ul></li>

		<li class="dropdown"><a href="#" class="dropdown-toggle"
			data-toggle="dropdown"><i class="fa fa-user"></i>
				${employee.email} <b class="caret"></b></a> <input type="hidden"
			id="email" value="${employee.email}" />
			<ul class="dropdown-menu">
				<li><a href="#"><i class="fa fa-fw fa-user"></i> Tài khoản</a></li>
				<!--<li><a href="#"><i class="fa fa-fw fa-envelope"></i> Inbox</a></li> -->
				<li><a href="#"><i class="fa fa-fw fa-gear"></i> Thiết lập</a></li>
				<li class="divider"></li>
				<li><a href="ProcessServlet?btnAction=logout"><i
						class="fa fa-fw fa-power-off"></i> Đăng xuất</a></li>
			</ul></li>
	</ul>
	<!-- Sidebar Menu Items - These collapse to the responsive navigation menu on small screens -->
	<div class="collapse navbar-collapse navbar-ex1-collapse">
		<ul class="nav navbar-nav side-nav">
			<li class="active"><a href="ProcessServlet?btnAction=aIndex"><i
					class="fa fa-fw fa-dashboard"></i> Tổng quan</a></li>
			<li><a href="ProcessServlet?btnAction=employeeManageAccount"><i
					class="fa fa-fw fa-users"></i> Kích hoạt tài khoản</a></li>
			<li><a href="ProcessServlet?btnAction=employeeManageDeal"><i
					class="fa fa-fw fa-exchange"></i> Quản lý thương lượng</a></li>
			<li><a href="ProcessServlet?btnAction=employeeManageOrder"><i
					class="fa fa-fw fa-table"></i> Quản lý hóa đơn</a></li>
			<li><a href="ProcessServlet?btnAction=employeeConfiguration"><i
					class="fa fa-fw fa-gears"></i> Cấu hình thông số</a></li>
		</ul>
	</div>
	<!-- /.navbar-collapse -->
</nav>
<div id="notify" class="notify"></div>