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
		<li class="active"><a href="${ctx}/hrattence/szlHrAttence/">考勤异常列表</a></li>
		<%-- <shiro:hasPermission name="hrattence:szlHrAttence:edit"><li><a href="${ctx}/hrattence/szlHrAttence/form">hrattence添加</a></li></shiro:hasPermission> --%>
	</ul>
	 <form:form id="searchForm" modelAttribute="szlHrAttence" action="${ctx}/hrattence/szlHrAttence/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<!-- <ul class="ul-form">
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>  -->
	</form:form>
	
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
	
		<thead>
			<tr>
				<shiro:hasPermission name="hrattence:szlHrAttence:edit"><th>工号</th></shiro:hasPermission>
				<shiro:hasPermission name="hrattence:szlHrAttence:edit"><th>姓名</th></shiro:hasPermission>
				<shiro:hasPermission name="hrattence:szlHrAttence:edit"><th>所属部门</th></shiro:hasPermission>
				<shiro:hasPermission name="hrattence:szlHrAttence:edit"><th>日期</th></shiro:hasPermission>
				<shiro:hasPermission name="hrattence:szlHrAttence:edit"><th>上班</th></shiro:hasPermission>
				<shiro:hasPermission name="hrattence:szlHrAttence:edit"><th>下班</th></shiro:hasPermission>
				<shiro:hasPermission name="hrattence:szlHrAttence:edit"><th>备注</th></shiro:hasPermission>
				<shiro:hasPermission name="hrattence:szlHrAttence:edit"><th>迟到时间</th></shiro:hasPermission>
				<shiro:hasPermission name="hrattence:szlHrAttence:edit"><th>早退时间</th></shiro:hasPermission>
				<shiro:hasPermission name="hrattence:szlHrAttence:edit"><th>缺勤时间</th></shiro:hasPermission>
				<shiro:hasPermission name="hrattence:szlHrAttence:edit"><th>合计</th></shiro:hasPermission>
				<shiro:hasPermission name="hrattence:szlHrAttence:edit"><th>操作</th></shiro:hasPermission>
				
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="szlHrAttence">
		<%-- <c:if test="${szlHrAttence.sum > 0}"> --%>
			<tr>
				<shiro:hasPermission name="hrattence:szlHrAttence:edit"><td>
    				${szlHrAttence.number}
				</td></shiro:hasPermission>
				<shiro:hasPermission name="hrattence:szlHrAttence:edit"><td>
    				${szlHrAttence.hrStaffName}
				</td></shiro:hasPermission>
				<shiro:hasPermission name="hrattence:szlHrAttence:edit"><td>
    				${szlHrAttence.hrStaffDept}
				</td></shiro:hasPermission>
				<shiro:hasPermission name="hrattence:szlHrAttence:edit"><td>
				<fmt:formatDate value="${szlHrAttence.date}" pattern="yyyy-MM-dd"/>
				</td></shiro:hasPermission>
				<shiro:hasPermission name="hrattence:szlHrAttence:edit"><td>
				 ${szlHrAttence.starttime} 
				</td></shiro:hasPermission>
				<shiro:hasPermission name="hrattence:szlHrAttence:edit"><td>
				 ${szlHrAttence.endtime} 
				</td></shiro:hasPermission>
				<shiro:hasPermission name="hrattence:szlHrAttence:edit"><td>
				 ${szlHrAttence.remark} 
				</td></shiro:hasPermission>
				<shiro:hasPermission name="hrattence:szlHrAttence:edit"><td>
				 ${szlHrAttence.latetime} 
				</td></shiro:hasPermission>
				<shiro:hasPermission name="hrattence:szlHrAttence:edit"><td>
				 ${szlHrAttence.earlytime} 
				</td></shiro:hasPermission>
				<shiro:hasPermission name="hrattence:szlHrAttence:edit"><td>
				 ${szlHrAttence.absenttime} 
				</td></shiro:hasPermission>
				<shiro:hasPermission name="hrattence:szlHrAttence:edit"><td>
				 ${szlHrAttence.sum} 
				</td></shiro:hasPermission>
				<shiro:hasPermission name="hrattence:szlHrAttence:edit"><td>
    				<a href="${ctx}/hrattence/szlHrAttence/form?id=${szlHrAttence.id}">修改</a>
					<%-- <a href="${ctx}/hrattence/szlHrAttence/delete?id=${szlHrAttence.id}" onclick="return confirmx('确认要删除该hrattence吗？', this.href)">删除</a> --%>
				</td></shiro:hasPermission>
			</tr>
		<%-- 	</c:if> --%>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>

