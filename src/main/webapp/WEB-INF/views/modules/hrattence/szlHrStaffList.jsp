<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>hrattence管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/hrattence/szlHrStaff/">hrattence列表</a></li>
		<shiro:hasPermission name="hrattence:szlHrStaff:edit"><li><a href="${ctx}/hrattence/szlHrStaff/form">hrattence添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="szlHrStaff" action="${ctx}/hrattence/szlHrStaff/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>name：</label>
				<form:input path="name" htmlEscape="false" maxlength="10" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>name</th>
				<shiro:hasPermission name="hrattence:szlHrStaff:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="szlHrStaff">
			<tr>
				<td><a href="${ctx}/hrattence/szlHrStaff/form?id=${szlHrStaff.id}">
					${szlHrStaff.name}
				</a></td>
				<shiro:hasPermission name="hrattence:szlHrStaff:edit"><td>
    				<a href="${ctx}/hrattence/szlHrStaff/form?id=${szlHrStaff.id}">修改</a>
					<a href="${ctx}/hrattence/szlHrStaff/delete?id=${szlHrStaff.id}" onclick="return confirmx('确认要删除该hrattence吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>