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
		<li class="active"><a href="${ctx}/overtime/szlHrOvertime/form?id=${szlHrOvertime.id}">加班申请<shiro:hasPermission name="overtime:szlHrOvertime:edit">${not empty szlHrOvertime.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="overtime:szlHrOvertime:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="szlHrOvertime" action="${ctx}/overtime/szlHrOvertime/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">工号：</label>
			<div class="controls">
				<form:input path="number" htmlEscape="false" maxlength="255" class="input-xlarge required" readonly="true" value="${number}"/>
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
			<label class="control-label">加班开始时间：</label>
			<div class="controls">
				<input id="startTime" name="startTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${szlHrOvertime.startTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});" onchange=""/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">加班结束时间：</label>
			<div class="controls">
				<input id="endTime" name="endTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${szlHrOvertime.endTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});" onchange=""/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">工作时长(小时)：</label>
			<div class="controls">
				<form:input id="workHours" path="workHours" htmlEscape="false" maxlength="255" class="input-xlarge required" readonly="true"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">工作内容：</label>
			<div class="controls">
				<form:textarea path="workContent" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注：</label>
			<div class="controls">
				<form:textarea path="overtimeRemark" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="overtime:szlHrOvertime:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="提 交"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
<script type="text/javascript">
$(document).ready(function() {
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

//计算两个时间相差了几个小时
function getIntervalHour(startTime,endTime) {
	var StartTime = new Date(startTime);//将字符串转化为时间
	var EndTime = new Date(endTime);
    var ms = EndTime.getTime() - StartTime.getTime();
    if (ms < 0){ 
    	return 0;
    }else{
    	var hours= Math.floor(ms/1000/60/60);
	    return hours;
    } 
}
//开始时间发生变化
$("#startTime").change(function(){
	 var startTime=$("#startTime").val();
	 var endTime=$("#endTime").val();
	 var workHours=getIntervalHour(startTime, endTime);
	 //写入工作时长
	 $("#workHours").val(workHours); 
});
//结束时间发生变化
$("#endTime").change(function(){
	var startTime=$("#startTime").val();
	var endTime=$("#endTime").val();
	var workHours=getIntervalHour(startTime, endTime);
	//写入工作时长
	$("#workHours").val(workHours); 
});
</script>
</body>
</html>