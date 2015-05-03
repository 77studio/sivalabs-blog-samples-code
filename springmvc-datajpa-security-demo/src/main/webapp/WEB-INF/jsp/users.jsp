<!DOCTYPE html>
<%@include file="taglib.jsp"%>
<html>
<head>
<title>用户管理</title>
<link rel="stylesheet"
	href='<spring:url value="resources/css/styles.css"/>' />
<script type="text/javascript"
	src='<spring:url value="resources/jquery/jquery-1.10.2.js"/>'></script>
<script type="text/javascript"
	src='<spring:url value="resources/js/app.js"/>'></script>
<script type="text/javascript">

</script>
</head>
<body>
	<h2>Administrator Home Page</h2>
	<p>
		<a href="${rootURL}welcome">Home</a>
	</p>
	<p>
		<a href="${rootURL}logout">Logout</a>
	</p>
	<div class="table-responsive">
		<table class="table">
			<caption>接收用户</caption>
			<thead>
				<tr>
					<th>编号</th>
					<th>姓名</th>
					<th>邮箱</th>
					<th>手机</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td>产品1</td>
					<td>23/11/2013</td>
					<td>23/11/2013</td>
					<td>23/11/2013</td>
					<td>待发货</td>
				</tr>
			</tbody>
		</table>
	</div>
</body>
</html>