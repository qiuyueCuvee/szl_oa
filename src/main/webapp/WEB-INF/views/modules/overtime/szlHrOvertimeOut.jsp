<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>加班申请管理</title>
<meta name="decorator" content="default"/>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/overtime/szlHrOvertime/">加班申请列表</a></li>
		<li class="active"><a href="${ctx}/overtime/szlHrOvertime/out?id=${szlHrOvertime.id}">加班申请驳回</a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="szlHrOvertime" action="${ctx}/overtime/szlHrOvertime/out" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">工号：</label>
			<div class="controls">
				<form:input path="number" htmlEscape="false" maxlength="255" class="input-xlarge required" readonly="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">申请时间：</label>
			<div class="controls">
				<form:input path="applyTime" htmlEscape="false" maxlength="255" class="input-xlarge required" readonly="true" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">加班开始时间：</label>
			<div class="controls">
				<form:input path="startTime" htmlEscape="false" maxlength="255" class="input-xlarge required" readonly="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">加班结束时间：</label>
			<div class="controls">
				<form:input path="endTime" htmlEscape="false" maxlength="255" class="input-xlarge required" readonly="true" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">工作时长(小时)：</label>
			<div class="controls">
				<form:input path="workHours" htmlEscape="false" maxlength="255" class="input-xlarge required" readonly="true" />
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">工作内容：</label>
			<div class="controls">
				<form:textarea path="workContent" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge required" readonly="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注：</label>
			<div class="controls">
				<form:textarea path="overtimeRemark" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge " readonly="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">驳回原因：</label>
			<div class="controls">
				<form:textarea id="statusReason" path="statusReason" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="overtime:szlHrOvertime:check"><input id="btnSubmit" class="btn btn-primary" type="submit" value="驳 回"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
<script type="text/javascript">
$(document).ready(function() {
	$("#statusReason").val("")
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
</body>
</html>