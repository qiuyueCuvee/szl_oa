<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>申报信息管理</title>
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
		<li class="active"><a href="${ctx}/declare/slzDeclareinfo/">申报信息列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="slzDeclareinfo" action="${ctx}/declare/slzDeclareinfo/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>部门：</label>
				<form:select path="declareDept" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('szl_dept')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>姓名：</label>
				<form:input path="declareName" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>申报时间：</label>
				<form:input path="declareTime" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>申报物品：</label>
				<form:select path="declareGoods" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('declare_goods')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>申报状态：</label>
				<form:select path="declareStatus" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('declare_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>申报人部门</th>
				<th>姓名</th>
				<th>联系电话</th>
				<th>申报时间</th>
				<th>申报物品</th>
				<th>用途</th>
				<th>参考地址</th>
				<th>备注</th>
				<th>申报单状态</th>
				<th>状态原因</th>
				<th>操作人</th>
				<shiro:hasPermission name="declare:slzDeclareinfo:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="slzDeclareinfo">
			<tr>
				<td><a href="${ctx}/declare/slzDeclareinfo/form?id=${slzDeclareinfo.id}">
					${fns:getDictLabel(slzDeclareinfo.declareDept, 'szl_dept', '')}
				</a></td>
				<td>
					${slzDeclareinfo.declareName}
				</td>
				<td>
					${slzDeclareinfo.declarePhone}
				</td>
				<td>
					${slzDeclareinfo.declareTime}
				</td>
				<td>
					${fns:getDictLabel(slzDeclareinfo.declareGoods, 'declare_goods', '')}
				</td>
				<td>
					${slzDeclareinfo.goodsUse}
				</td>
				<td>
					${slzDeclareinfo.referenceUrl}
				</td>
				<td>
					${slzDeclareinfo.declareRemark}
				</td>
				<td>
					${fns:getDictLabel(slzDeclareinfo.declareStatus, 'declare_status', '')}
				</td>
				<td>
					${slzDeclareinfo.statusReason}
				</td>
				<td>
					${slzDeclareinfo.user.name}
				</td>
				<shiro:hasPermission name="declare:slzDeclareinfo:check"><td>
					<a href="${ctx}/declare/slzDeclareinfo/pass?id=${slzDeclareinfo.id}" onclick="return confirmx('确认要通过该申报信息吗？', this.href)">通过</a>
    				<a href="${ctx}/declare/slzDeclareinfo/goout?id=${slzDeclareinfo.id}">驳回</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>