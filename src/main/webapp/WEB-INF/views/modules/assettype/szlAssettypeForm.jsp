<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>assettype管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#inputForm").validate({
				submitHandler: function(form){
					var jsonstring = getvalue();
					$('#jsonstring').val(jsonstring);
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
		
		var template = {
				"电脑":[	{"title":"cpu",	"type":"input",	"name":"cpu"},	
						{"title":"硬盘号",	"type":"input",	"name":"diskno"},
						{"title":"内存",	"type":"input",	"name":"mem"},
						{"title":"品牌",	"type":"input",	"name":"trade"},
						{"title":"型号",	"type":"input",	"name":"tradetype"},
						{"title":"硬盘大小",	"type":"input",	"name":"disksize"},
						{"title":"领用日期",	"type":"input",	"name":"date","style":"date"},],
				"u盘":[  {"title":"容量",	"type":"input",	"name":"cap"}, 
						{"title":"型号",	"type":"input",	"name":"type"}, 
						{"title":"领用日期",	"type":"input",	"name":"time","style":"date"},
						{"title":"品牌",	"type":"select","name":"brand","option":["金士顿","东芝"]},]
					  };
		function json2Form(fm, json){
			fm.empty();
			for(var i = 0; i < json.length; ++i){
				var str = "";
				if(json[i].type == 'input'){
					fm.append($('<span>'+json[i].title+':</span>'));
					fm.append($('<input type = '+json[i].style+ ' name=json_'+json[i].name+' /><br/>'));
				}
				else if(json[i].type == 'select'){
					fm.append($('<span>'+json[i].title+':</span>'));
					fm.append($('<select name=json_'+json[i].name+' id="select_'+json[i].name+'"></select>'));
					json[i].option.forEach(function(val, index){
						$('#select_'+json[i].name).append($('<option value="'+val+'">'+val+'</option>'));
					});
					
				}
				
			}
		}

		function getvalue(){
			 var json = [];
			 
			  if($("select[name='title']").val()=="u盘"){
				 json.push({
					 容量:$("input[name='json_cap']").val(),
					 品牌:$("select[name='json_brand']").val(),
					 型号:$("input[name='json_type']").val(),
					 领用日期:$("input[name='json_time']").val(),
			     });
				 var jsonstr = JSON.stringify(json);
			  }
			  if($("select[name='title']").val()=="电脑"){
				 json.push({
					cpu:$("input[name='json_cpu']").val(),
					内存:$("input[name='json_mem']").val(),
					硬盘号:$("input[name='json_diskno']").val(),
					品牌:$("input[name='json_trade']").val(),
					型号:$("input[name='json_tradetype']").val(),
					硬盘大小:$("input[name='json_disksize']").val(),
					领用日期:$("input[name='json_date']").val(),
			     });
				 var jsonstr = JSON.stringify(json);
				  }
		    return jsonstr; 
		    
		}

		$(function(){
			$('#title').change(function(){
				var type = $("#title").find("option:selected").text();
				json2Form($('#info'), template[type]);
			});

		})
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<%-- <li><a href="${ctx}/assettype/szlAssettype/">assettype列表</a></li> --%>
		<li class="active"><a href="${ctx}/assettype/szlAssettype/form?id=${szlAssettype.id}">资产录入<shiro:hasPermission name="assettype:szlAssettype:edit">${not empty szlAssettype.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="assettype:szlAssettype:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="szlAssettype" action="${ctx}/assettype/szlAssettype/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div >
			<span >资产类型：</span>
			<span >
				<form:select path="title"    htmlEscape="false"  class="input-xlarge ">
					<form:option value=" " label="请选择"/>
					<form:option value="电脑" label="电脑"/>
					<%-- <form:options items="${fns:getDictList('asset_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/> --%>
					<form:option value="u盘" label="u盘"/>					
				</form:select>
			</span>
			
		</div>
			<div class="control-group" id="info">
			</div>
			
		<div class="form-actions">
			<shiro:hasPermission name="assettype:szlAssettype:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
		<input type="hidden" name="jsonstring" id="jsonstring">
	</form:form>
</body>
</html>
