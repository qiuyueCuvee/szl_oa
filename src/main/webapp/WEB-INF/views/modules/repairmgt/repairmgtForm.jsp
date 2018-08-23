<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>报修信息管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/repairmgt/repairmgt/">报修信息列表</a></li>
		<li class="active"><a href="${ctx}/repairmgt/repairmgt/form?id=${repairmgt.id}">报修信息<shiro:hasPermission name="repairmgt:repairmgt:edit">${not empty repairmgt.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="repairmgt:repairmgt:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="repairmgt" action="${ctx}/repairmgt/repairmgt/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<c:choose>	
		<c:when test="${whoami =='普通用户'}">
		<div class="control-group">
			<label class="control-label col-sm-4">
				<span class="required" style="color:red">*</span>申请人: </label>
			<div class="controls">
				<form:input path="applicant" htmlEscape="false" maxlength="64" class="input-xlarge form-control required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label col-sm-4">
				<span class="required" style="color:red">*</span>部门：</label>
			<div class="controls">
				<form:select path="department" class="input-xlarge form-control required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('szl_dept')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label col-sm-4">
				<span class="required" style="color:red">*</span>申请事项：</label>
			<div class="controls">
				<form:input path="matter" htmlEscape="false" maxlength="64" class="input-xlarge form-control required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注信息：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="500" class="input-xxlarge "/>
			</div>
		</div>
		<div>
			<form:input type="hidden" path="process" value="0" />
		</div>
		</c:when>
		<c:otherwise>
		<div class="control-group">
			 <label class="control-label col-sm-4">
				<span class="required" style="color:red">*</span>受理人: </label>
			<div class="controls">
				<form:input path="receiver" htmlEscape="false" maxlength="64" class="input-xlarge form-control required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">处理进程：</label>
			<div class="controls">
				<form:select path="process" class="input-xlarge" >
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('process')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">解决日期：</label>
			<div class="controls">
				<input name="donedate" type="text" maxlength="20" class="input-xlarge Wdate "
					value="<fmt:formatDate value="${repairmgt.donedate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">解决方案：</label>
			<div class="controls">
				<form:textarea path="solution" htmlEscape="false" rows="4" maxlength="500" class="input-xxlarge "/>
			</div>
		</div>
		</c:otherwise>
		</c:choose>	
		<div class="form-actions">
			<shiro:hasPermission name="repairmgt:repairmgt:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>