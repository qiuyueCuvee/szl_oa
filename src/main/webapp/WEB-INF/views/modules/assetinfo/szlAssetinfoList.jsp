<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>assetinfo管理</title>
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
		<li class="active"><a href="${ctx}/assetinfo/szlAssetinfo/listData">资产管理列表</a></li>
		<%-- <shiro:hasPermission name="assetinfo:szlAssetinfo:edit"><li><a href="${ctx}/assetinfo/szlAssetinfo/form">assetinfo添加</a></li></shiro:hasPermission> --%>
	</ul>
	<form:form id="searchForm" modelAttribute="szlAssetinfo" action="${ctx}/assetinfo/szlAssetinfo/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
			<th>状态</th>
			<th>人员</th>
			<th>资产信息</th>
				<shiro:hasPermission name="assetinfo:szlAssetinfo:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="szlAssetinfo">
			<tr>
			<td>${szlAssetinfo.state}</td>
			<td>${szlAssetinfo.uid}</td>
			<td>${szlAssetinfo.info}</td>
				<shiro:hasPermission name="assetinfo:szlAssetinfo:edit"><td>
    				<a href="${ctx}/assetinfo/szlAssetinfo/form?id=${szlAssetinfo.id}">修改</a>
					<a href="${ctx}/assetinfo/szlAssetinfo/delete?id=${szlAssetinfo.id}" onclick="return confirmx('确认要删除该assetinfo吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>