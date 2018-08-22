<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>加班申请管理</title>
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
		<li class="active"><a href="${ctx}/overtime/szlHrOvertime/">加班申请列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="szlHrOvertime" action="${ctx}/overtime/szlHrOvertime/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>工号：</label>
				<form:input path="number" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>申请时间：</label>
				<input name="applyTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${szlHrOvertime.applyTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li><label>工作时长：</label>
				<form:input path="workHours" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>审批状态：</label>
				<form:select path="overtimeStatus" class="input-medium">
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
				<th>加班开始时间</th>
				<th>加班结束时间</th>
				<th>工作时长</th>
				<th>工作内容</th>
				<th>备注</th>
				<th>审批状态</th>
				<th>状态原因</th>
			    <shiro:hasPermission name="overtime:szlHrOvertime:check"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="szlHrOvertime">
			<tr>
				<td>${szlHrOvertime.number}</td>
				<td>${szlHrOvertime.hrStaffName}</td>
				<td>${szlHrOvertime.hrStaffDept}</td>
				<td>${szlHrOvertime.applyTime}</td>
				<td>${szlHrOvertime.startTime}</td>
				<td>${szlHrOvertime.endTime}</td>
				<td>${szlHrOvertime.workHours}</td>
				<td>${szlHrOvertime.workContent}</td>
				<td>${szlHrOvertime.overtimeRemark}</td>
				<td>${fns:getDictLabel(szlHrOvertime.overtimeStatus, 'declare_status', '')}</td>
				<td>${szlHrOvertime.statusReason}</td>
				<shiro:hasPermission name="overtime:szlHrOvertime:check">
				<td>
				<c:choose>  
					   <c:when test="${szlHrOvertime.overtimeStatus == '3'}" >      
						 <a href="${ctx}/overtime/szlHrOvertime/pass?id=${szlHrOvertime.id}" onclick="return confirmx('确认要通过该加班信息吗？', this.href)">通过</a>
					   </c:when>  
					   <c:when test="${szlHrOvertime.overtimeStatus == '2'}" >      
    					 <a href="${ctx}/overtime/szlHrOvertime/goout?id=${szlHrOvertime.id}">驳回</a>
					   </c:when>  
					   <c:otherwise>  
						 <a href="${ctx}/overtime/szlHrOvertime/pass?id=${szlHrOvertime.id}" onclick="return confirmx('确认要通过该加班信息吗？', this.href)">通过</a>
    					 <a href="${ctx}/overtime/szlHrOvertime/goout?id=${szlHrOvertime.id}">驳回</a>
					   </c:otherwise>  
					</c:choose> 
				</td>
				</shiro:hasPermission> 
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>