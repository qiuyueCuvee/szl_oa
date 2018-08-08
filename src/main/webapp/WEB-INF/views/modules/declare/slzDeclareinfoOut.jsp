<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>申报信息管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#statusReason").val("")
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
		<li><a href="${ctx}/declare/slzDeclareinfo/">申报信息列表</a></li>
		<li class="active"><a href="${ctx}/declare/slzDeclareinfo/out?id=${slzDeclareinfo.id}">申报信息驳回<shiro:lacksPermission name="declare:slzDeclareinfo:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="slzDeclareinfo" action="${ctx}/declare/slzDeclareinfo/out" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">申报人部门：</label>
			<div class="controls">
				<form:input path="declareDept" htmlEscape="false" maxlength="255" class="input-xlarge required" readonly="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">姓名：</label>
			<div class="controls">
				<form:input path="declareName" htmlEscape="false" maxlength="255" class="input-xlarge required" readonly="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">联系电话：</label>
			<div class="controls">
				<form:input path="declarePhone" htmlEscape="false" maxlength="255" class="input-xlarge " readonly="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">申报物品：</label>
			<div class="controls">
				<form:input path="declareGoods" htmlEscape="false" maxlength="255" class="input-xlarge required" readonly="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">用途：</label>
			<div class="controls">
				<form:input path="goodsUse" htmlEscape="false" maxlength="255" class="input-xlarge " readonly="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">参考地址：</label>
			<div class="controls">
				<form:input path="referenceUrl" htmlEscape="false" maxlength="255" class="input-xlarge " readonly="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注：</label>
			<div class="controls">
				<form:textarea path="declareRemark" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge " readonly="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">驳回原因：</label>
			<div class="controls">
				<form:textarea id="statusReason" path="statusReason" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge " disabled="disabled"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="declare:slzDeclareinfo:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="驳 回"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>