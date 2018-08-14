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
	 <form:form id="searchForm" modelAttribute="szlHrAttence" action="${ctx}/hrattence/calendar/" method="post" class="breadcrumb form-search">
		  <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>   
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
		${szlHrAttence.html}
		</thead>
		
		<tbody>
		<%-- <c:forEach items="${list}" var="map">
			<tr>
			<td>
					 			 ${szlHrAttence.hrStaffName} 
							</td>
				<c:forEach var="tmp"  items="${map}" >
	 					
		 			    <c:forEach items="${tmp.value}" var="element">  
		 			      <c:if test="${szlHrAttence.number == tmp.key}">  
					 		<td>
					 			 ${element.status} 
							</td>
							 </c:if>  
						</c:forEach>
	 			</c:forEach>
			</tr>
		</c:forEach> --%>
		</tbody>
	</table>
	  <div class="pagination">${page}</div>   
</body>
</html>

