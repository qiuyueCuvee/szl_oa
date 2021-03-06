<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>请假信息管理</title>
	<meta name="decorator" content="default"/>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/leave/szlHrLeave/">请假信息列表</a></li>
		<li class="active"><a href="${ctx}/leave/szlHrLeave/form?id=${szlHrLeave.id}">请假申请</a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="szlHrLeave" action="${ctx}/leave/szlHrLeave/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">工号：</label>
			<div class="controls">
				<form:input id="number" path="number" htmlEscape="false" maxlength="255" class="input-xlarge required" readonly="true" value="${number}"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">申请时间：</label>
			<div class="controls">
				<input name="applyTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="${today}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">请假类别：</label>
			<div class="controls">
				<form:select path="leaveType" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('oa_leave_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">请假事由：</label>
			<div class="controls">
				<form:input path="leaveReason" htmlEscape="false" maxlength="255" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">开始时间：</label>
			<div class="controls">
				<input name="startTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${szlHrLeave.startTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">结束时间：</label>
			<div class="controls">
				<input name="endTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${szlHrLeave.endTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">请假时长(小时)：</label>
			<div class="controls">
				<form:input path="leaveHours" htmlEscape="false" maxlength="255" class="input-xlarge required"  onkeyup="value=value.replace(/[^\d{1,}\.\d{1,}|\d{1,}]/g,'')"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注：</label>
			<div class="controls">
				<form:textarea path="leaveRemark" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="leave:szlHrLeave:edit">
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="提 交"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
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
/* function getHours() {
	var number=$("#number").val();
	$.ajax({
		 type : "GET",
		 url : "${ctx}/leave/szlHrLeave/getHours",
		 data : {number:number},
		 dataType: "json",
			 success : function(data) {
				 console.log(data)
				 //$("#inputForm").submit();
			 }
	});
} */
</script>
</body>
</html>