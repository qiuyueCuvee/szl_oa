<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>考勤管理</title>
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
		<li class="active"><a href="${ctx}/hrattence/calendar/">考勤表</a></li>
		<%-- <shiro:hasPermission name="hrattence:szlHrAttence:edit"><li><a href="${ctx}/hrattence/szlHrAttence/form">hrattence添加</a></li></shiro:hasPermission> --%>
	</ul>
	 <form:form id="searchForm" modelAttribute="szlHrAttence" action="${ctx}/hrattence/calendar/list" method="post" class="breadcrumb form-search">
		  <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>   
		  <li><label>工号：</label>
				<form:input path="number" value="0000" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li> 
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul> 
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
		${szlHrAttence.html}
		</thead>
		
		<tbody>
		</tbody>
	</table>
	  <div class="pagination">${page}</div>   
</body>
</html>

