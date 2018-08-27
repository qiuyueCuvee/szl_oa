<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>考勤管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#number").val("");
			
			$("#btnExport").click(function(){
				top.$.jBox.confirm("确认要考勤数据吗？","系统提示",function(v,h,f){
					if(v=="ok"){
						$("#searchForm").attr("action","${ctx}/hrattence/calendar/export");
						$("#searchForm").submit();
					}
				},{buttonsFocus:1});
				top.$('.jbox-body .jbox-icon').css('top','55px');
			});
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
				<form:input path="number"  htmlEscape="true" maxlength="255" class="input-medium"/>
			</li> 
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
			<input id="btnExport" class="btn btn-primary" type="button" value="导出"/>
	</form:form>
	
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
		${szlHrAttence.html}
		</thead>
		
		<tbody>
		
		</tbody>
		
	</table>
	√出勤    ×迟到    #早退    △事假    *病假(年假、婚假、丧假具体标注)   ◻倒休
	  <div class="pagination">${page}</div>   
</body>
</html>

