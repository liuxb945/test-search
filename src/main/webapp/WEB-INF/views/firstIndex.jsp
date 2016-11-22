<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()+ path + "/";
%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Bootstrap 101 Template</title>

<!-- Bootstrap -->
<link href="<%=basePath%>static/css/bootstrap.min.css" rel="stylesheet">

<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
      <script src="http://cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="http://cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<body>
	<form role="form" action="search/search.do" method="post">
		<div class="form-group">
			<label for="name">一级分类</label>
			<form:select id="firstSel" class="form-control" path="items1" items="${items1}" itemLabel="name" itemValue="id"></form:select>
			<label for="name">二级分类</label>
			<form:select id="secondtSel" class="form-control" path="items1" items="${items1}" itemLabel="name" itemValue="id"></form:select>
			<div class="col-lg-6">
				<div class="input-group">
					<input type="text" class="form-control"> <span
						class="input-group-btn">
						<button class="btn btn-default" type="submit">搜索</button>
					</span>
				</div>
				<!-- /input-group -->
			</div>
		</div>
	</form>

	<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
	<script src="http://cdn.bootcss.com/jquery/1.11.1/jquery.min.js"></script>
	<!-- Include all compiled plugins (below), or include individual files as needed -->
	<script src="<%=basePath%>static/js/bootstrap.min.js"></script>
	<script type="text/javascript">
		$(function() {
			$("#firstSel").change(function() {
				var val = $("#firstSel").val();
				window.location.href="category1?cat="+val;
			});
		});
	</script>
</body>
</html>