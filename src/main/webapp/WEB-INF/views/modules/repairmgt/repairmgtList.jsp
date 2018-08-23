<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>报修信息管理</title>
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
		<li class="active"><a href="${ctx}/repairmgt/repairmgt/">报修信息列表</a></li>
		<c:if test="${whoami == '普通用户'}">
		<shiro:hasPermission name="repairmgt:repairmgt:edit"><li><a href="${ctx}/repairmgt/repairmgt/form">报修信息添加</a></li></shiro:hasPermission>
		</c:if>
	</ul>
	<form:form id="searchForm" modelAttribute="repairmgt" action="${ctx}/repairmgt/repairmgt/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>申请人：</label>
				<form:input path="applicant" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>部门：</label>
				<form:select path="department" class="input-medium">
					<form:option value="" label=""/>
					<form:option value=""> </form:option>
					<form:options items="${fns:getDictList('szl_dept')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>处理进程：</label>
				<form:select path="process" class="input-medium">
					<form:option value="" label=""/>
					<form:option value=""> </form:option>
					<form:options items="${fns:getDictList('process')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>受理人：</label>
				<form:input path="receiver" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>id</th>
				<th>申请人</th>
				<th>部门</th>
				<th>申请事项</th>
				<th>处理进程</th>	
				<th>受理人</th> 
				<th style="width:18%;">解决方案</th>
				<th>解决日期</th>
				<th style="width:15%;">备注信息</th>
				<c:if test="${whoami=='系统管理员'}">
					<th>创建者</th>
					<th>创建时间</th>
					<th>更新者</th>
					<th>更新时间</th>	
				</c:if>
				<shiro:hasPermission name="repairmgt:repairmgt:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="repairmgt">
			<tr>
				<td><a href="${ctx}/repairmgt/repairmgt/form?id=${repairmgt.id}">
					${repairmgt.id}
				</a></td>
				<td style="white-space:nowrap;">
					${repairmgt.applicant}
				</td>
				<td style="white-space:nowrap;">
					${fns:getDictLabel(repairmgt.department, 'szl_dept', '')}
				</td>
				<td>
					${repairmgt.matter}
				</td>
				<td>
					${fns:getDictLabel(repairmgt.process, 'process', '')}
				</td>
				<td>
					${repairmgt.receiver}
				</td>
				<td style="width:18%;">
					${repairmgt.solution}
				</td>
				<td style="white-space:nowrap;">
					<fmt:formatDate value="${repairmgt.donedate}" pattern="yyyy-MM-dd"/>
				</td>
				<td style="width:15%;">
					${repairmgt.remarks}
				</td>
				<c:if test="${whoami=='系统管理员'}">
				<td>
					${repairmgt.createBy.id}
				</td>
				<td>
					<fmt:formatDate value="${repairmgt.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${repairmgt.updateBy.id}
				</td>
				<td>
					<fmt:formatDate value="${repairmgt.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				</c:if>
				<td style="white-space:nowrap;">
					<c:choose>	
						<c:when test="${repairmgt.process > 0}"> 
							<a href="${ctx}/repairmgt/repairmgt/form?id=${repairmgt.id}">修改</a>
							<c:if test="${whoami == '系统管理员'}">
								<a href="${ctx}/repairmgt/repairmgt/delete?id=${repairmgt.id}" onclick="return confirmx('确认要删除该报修信息吗？', this.href)">删除</a>
							</c:if>
						</c:when>
						<c:otherwise>
		    				<a href="${ctx}/repairmgt/repairmgt/form?id=${repairmgt.id}">修改</a>
		    				<c:if test="${whoami != '信息部管理员'}">
								<a href="${ctx}/repairmgt/repairmgt/delete?id=${repairmgt.id}" onclick="return confirmx('确认要删除该报修信息吗？', this.href)">删除</a>
							</c:if>
						</c:otherwise>
					</c:choose>	
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table> 
	<div class="pagination">${page}</div>
</body>
</html>