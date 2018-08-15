<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>请假信息管理</title>
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
		<li class="active"><a href="${ctx}/leave/szlHrLeave/">请假信息列表</a></li>
		<shiro:hasPermission name="leave:szlHrLeave:edit"><li><a href="${ctx}/leave/szlHrLeave/form">请假信息添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="szlHrLeave" action="${ctx}/leave/szlHrLeave/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>工号：</label>
				<form:input path="number" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>申请时间：</label>
				<input name="applyTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${szlHrLeave.applyTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li><label>请假类别：</label>
				<form:select path="leaveType" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('oa_leave_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>审批状态：</label>
				<form:select path="leaveStatus" class="input-medium">
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
				<th>工号</th>
				<th>姓名</th>
				<th>部门</th>
				<th>申请时间</th>
				<th>请假类别</th>
				<th>请假事由</th>
				<th>开始时间</th>
				<th>结束时间</th>
				<th>请假时长</th>
				<th>备注</th>
				<th>审批状态</th>
				<th>状态原因</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="szlHrLeave">
			<tr>
				<td>${szlHrLeave.number}</td>
				<td>${szlHrLeave.hrStaffName}</td>
				<td>${szlHrLeave.hrStaffDept}</td>
				<td>
					${szlHrLeave.applyTime}
				</td>
				<td>
					${fns:getDictLabel(szlHrLeave.leaveType, 'oa_leave_type', '')}
				</td>
				<td>
					${szlHrLeave.leaveReason}
				</td>
				<td>
					${szlHrLeave.startTime}
				</td>
				<td>
					${szlHrLeave.endTime}
				</td>
				<td>
					${szlHrLeave.leaveHours}
				</td>
				<td>
					${szlHrLeave.leaveRemark}
				</td>
				<td>
					${fns:getDictLabel(szlHrLeave.leaveStatus, 'declare_status', '')}
				</td>
				<td>
					${szlHrLeave.statusReason}
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>