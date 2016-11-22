<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()+ path + "/";
%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
			<select class="form-control" name="cat1" id="cat1">
			   <option value="">请选择</option>
               <c:forEach var="list" items="${items1}">
                 <c:choose>
                    <c:when test="${param.cat1==list.id}">
                       <option selected="selected" value="<c:out value="${list.id}"/>"> 
                             <c:out value="${list.name}"/>
                       </option>
                    </c:when>
                    <c:otherwise>
                        <option  value="<c:out value="${list.id}"/>"> 
                           <c:out value="${list.name}"/>
                        </option>
                    </c:otherwise>
                  </c:choose>
                </c:forEach>
              </select>
			<label for="name">二级分类</label>
			<select class="form-control" name="cat2" id="cat2">
			   <option value="">请选择</option>
               <c:forEach var="list" items="${items2}">
                 <c:choose>
                    <c:when test="${param.cat2==list.id}">
                       <option selected="selected" value="<c:out value="${list.id}"/>"> 
                             <c:out value="${list.name}"/>
                       </option>
                    </c:when>
                    <c:otherwise>
                        <option  value="<c:out value="${list.id}"/>"> 
                           <c:out value="${list.name}"/>
                        </option>
                    </c:otherwise>
                  </c:choose>
                </c:forEach>
              </select>
              <label for="name">三级分类</label>
			<select class="form-control" name="cat3" id="cat3">
			   <option value="">请选择</option>
               <c:forEach var="list" items="${items3}">
                 <c:choose>
                    <c:when test="${param.cat3==list.id}">
                       <option selected="selected" value="<c:out value="${list.id}"/>"> 
                             <c:out value="${list.name}"/>
                       </option>
                    </c:when>
                    <c:otherwise>
                        <option  value="<c:out value="${list.id}"/>"> 
                           <c:out value="${list.name}"/>
                        </option>
                    </c:otherwise>
                  </c:choose>
                </c:forEach>
              </select>
			<label for="name">排序字段</label> 
			<select class="form-control" name="sortSel" id="sortSel">
               <c:forEach var="list" items="${sorts}">
                 <c:choose>
                    <c:when test="${param.sortby==list.id}">
                       <option selected="selected" value="<c:out value="${list.id}"/>"> 
                             <c:out value="${list.name}"/>
                       </option>
                    </c:when>
                    <c:otherwise>
                        <option  value="<c:out value="${list.id}"/>"> 
                           <c:out value="${list.name}"/>
                        </option>
                    </c:otherwise>
                  </c:choose>
                </c:forEach>
              </select>
              <div class="col-lg-12">
                <div class="input-group">
                    <input id="s_w" type="text" value="${param.s_w }" class="form-control">
                    <span class="input-group-btn">
                        <button id="btnSearch" class="btn btn-default" type="button">搜索</button>
                    </span>
                </div><!-- /input-group -->
            </div><!-- /.col-lg-6 -->
            <br/>
			<label for="name">商品数量：<c:out value="${searchList.getData().size()}"/></label>
			<br/>
			<label for="name">分类信息：</label>
			<ul class="list-group">
				<c:forEach var="list" items="${searchList.getGroups().get(0).getChildren()}">
					<li class="list-group-item"><c:out value="${list.name}"/>----数量：<c:out value="${list.count}"/></li>
				</c:forEach>	
			</ul>
			<label for="name">商品列表：</label>
			<ul class="list-group">
				<c:forEach var="list" items="${searchList.getData()}">
					<li class="list-group-item"><c:out value="${list.name}"/>,<c:out value="${list.id}"/>----<c:out value="${list.oneCategoryName}"/>---<c:out value="${list.twoCategoryName}"/>---<c:out value="${list.threeCategoryName}"/></li>
				</c:forEach>	
			</ul>
		</div>
	</form>

	<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
	<script src="http://cdn.bootcss.com/jquery/1.11.1/jquery.min.js"></script>
	<!-- Include all compiled plugins (below), or include individual files as needed -->
	<script src="<%=basePath%>static/js/bootstrap.min.js"></script>
	<script type="text/javascript">
		$(function() {
			$("#cat1").change(function() {
				var cat1 = $("#cat1").val();
				if (typeof(cat1) == "undefined") { 
					   cat1=""; 
					}
				listCategory(cat1,"","","","");
			});
			$("#cat2").change(function() {
				var cat1 = $("#cat1").val();
				if (typeof(cat1) == "undefined") { 
					   cat1=""; 
					} 
				var cat2 = $("#cat2").val();
				if (typeof(cat2) == "undefined") { 
					   cat2=""; 
					}
				listCategory(cat1,cat2,"","","");
			});
			$("#cat3").change(function() {
				var cat1 = $("#cat1").val();
				if (typeof(cat1) == "undefined") { 
					   cat1=""; 
					} 
				var cat2 = $("#cat2").val();
				if (typeof(cat2) == "undefined") { 
					   cat2=""; 
					} 
				var cat3 = $("#cat3").val();
				if (typeof(cat3) == "undefined") { 
					   cat3=""; 
					} 
				listCategory(cat1,cat2,cat3,"","");
			});
			$("#sortSel").change(function() {
				var cat1 = $("#cat1").val();
				if (typeof(cat1) == "undefined") { 
					   cat1=""; 
					} 
				var cat2 = $("#cat2").val();
				if (typeof(cat2) == "undefined") { 
					   cat2=""; 
					} 
				var cat3 = $("#cat3").val();
				if (typeof(cat3) == "undefined") { 
					   cat3=""; 
					} 
				var sortSel = $("#sortSel").val();
				if (typeof(sortSel) == "undefined") { 
					sortSel=""; 
					}
				listCategory(cat1,cat2,cat3,sortSel,"");
			});
			$("#btnSearch").click(function() {
				var cat1 = $("#cat1").val();
				if (typeof(cat1) == "undefined") { 
					   cat1=""; 
					} 
				var cat2 = $("#cat2").val();
				if (typeof(cat2) == "undefined") { 
					   cat2=""; 
					} 
				var cat3 = $("#cat3").val();
				if (typeof(cat3) == "undefined") { 
					   cat3=""; 
					} 
				var sortSel = $("#sortSel").val();
				if (typeof(sortSel) == "undefined") { 
					sortSel=""; 
					}
				var sw = $("#s_w").val();
				listCategory(cat1,cat2,cat3,sortSel,sw);
			});
		});
		function listCategory(cat1,cat2,cat3,sortby,sw){
			 
			window.location.href="category?cat1="+cat1+"&cat2="+cat2+"&cat3="+cat3+"&sortby="+sortby+"&s_w="+sw;
		}
	</script>
</body>
</html>