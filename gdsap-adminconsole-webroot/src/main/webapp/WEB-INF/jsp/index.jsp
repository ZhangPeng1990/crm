<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%><%@ include file="/include.inc.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=7" />
<title><fmt:message key="ui.title" /></title>

<link href="styles/dwz/themes/azure/style.css" rel="stylesheet" type="text/css" media="screen"/>
<link href="styles/dwz/themes/css/core.css" rel="stylesheet" type="text/css" media="screen"/>
<link href="styles/dwz/themes/css/print.css" rel="stylesheet" type="text/css" media="print"/>

<!--[if IE]>
<link href="styles/dwz/themes/css/ieHack.css" rel="stylesheet" type="text/css" media="screen"/>
<![endif]-->
<style type="text/css">
fieldset {padding:3px; border:1px dotted #dadada; margin-bottom: 5px}
fieldset legend {padding:2px; border:1px dotted #dadada; color:#0088CC; font-weight: bold;}
fieldset legend:hover {background-color: #dddddd;}

table.addr {border: 1px solid #A1C6D9;}
table.addr th {background-color: #CDD7FF; font-weight: bold; padding: 5px}
table.addr td{border-top: 1px solid #A1C6D9;}
table.addr a{color: blue; font-weight: bold;}
table.addr p{width:100%}

table.userRole {border: 1px solid #A1C6D9;}
table.userRole th {background-color: #CDD7FF; font-weight: bold; padding: 5px}
table.userRole td {padding: 3px}
table.userRole label{width:auto;float: none;}

.panelBar .toolBar a.credit span { background-position:0 -846px;}
.dialog .pageFormContent p {width:100%}
.pageFormContent p.default {float: none; clear: both; width: auto;}


/* Extend Fisld List */
.vtab td{ padding:3px;}
.vtab p{ display:block; height:21px; margin:0; padding:3px 0;clear: both;}
.vtab .extendLeft{overflow:auto; border-right:1px dotted #999999; padding:5px 10px 5px; margin-right: 5px}
.vtab .extendLeft li{padding:6px; border:1px solid #0088cc; margin-top:2px;}
.vtab .extendLeft li.selected{ background-color:#fff;}
.vtab .extendLeft li:hover{ background-color:#fff; border:1px solid #00B4FF;}
.vtab .extendLeft li a {}
.vtab .extendLeft li a:hover { cursor:pointer; color:#0088cc;}
.vtab .extendLeft p span{ font-weight:bold; color:#0088cc;}
                          
.vtab .extendRight{overflow:auto;}
.vtab .extendRight p label{ display:block; float:left; height:21px; padding:3px 0; width:80px;}
.vtab .line{ border-bottom:1px dotted #0088cc; height:5px; clear: both; margin: 5px 0}     
.vtab .title{font-weight: bold;}

.special .memberBox { clear:both; padding:3px 0;}
.special td>label {width: 300px; text-align: right; float:left; }
</style>

<script src="styles/dwz/js/speedup.js" type="text/javascript"></script>
<script src="styles/dwz/js/jquery-1.7.2.min.js" type="text/javascript"></script>
<script src="styles/dwz/js/jquery.cookie.js" type="text/javascript"></script>
<script src="styles/dwz/js/jquery.validate.min.js" type="text/javascript"></script>
<script src="styles/dwz/js/jquery.bgiframe.js" type="text/javascript"></script>
<script src="styles/xheditor/xheditor-1.1.14-zh-cn.min.js" type="text/javascript"></script>

<script src="styles/dwz/js/dwz.min.js" type="text/javascript"></script>

<script type="text/javascript">
$(function(){
	DWZ.init("styles/dwz/dwz.frag.xml", {
		loginUrl:"<c:url value='/passport/index'/>", loginTitle:"Login",
		statusCode:{ok:200, error:300, timeout:301}, //【可选】
		pageInfo:{pageNum:"pageNum", numPerPage:"pageSize", orderField:"orderField", orderDirection:"orderDirection"}, //【可选】
		debug:false,	// 调试模式 【true|false】
		callback:function(){
			initEnv();
			$("#themeList").theme({themeBase:"<c:url value='/styles/dwz/themes/'/>"});
		}
	});
});


function epcAddressList(form){
	var postcode = $(form).find("[name=postcode]").val();
	if (postcode) $.post(form.action, {postcode:postcode}, function(html){
		$("#addressSeleter").html(html);
	});
	return false;
}
function selectNdAddress(addressId){
	var form = $("#eaForm");
	if (addressId) $.getJSON("/lodgeAddress.do?method=ndepcAddress", {addressId:addressId}, function(json){
		form.find("[name=ndEaInfo.address1]").val(json.address1);
		form.find("[name=ndEaInfo.address2]").val(json.address2);
		form.find("[name=ndEaInfo.address3]").val(json.address3);
		form.find("[name=ndEaInfo.postcode]").val(json.postcode);
		form.find("[name=ndEaInfo.town]").val(json.town);
		form.find("[name=ndEaInfo.county]").val(json.address4);
//		form.find("[name=ndEaInfo.uprn]").val(json.uprn);
	});
}
function selectAddress(addressId){
	var form = $("#eaForm");
	if (addressId) $.getJSON("/lodgeAddress.do?method=depcAddress", {addressId:addressId}, function(json){
		form.find("[name=DEaInfo.address1]").val(json.address1);
		form.find("[name=DEaInfo.address2]").val(json.address2);
		form.find("[name=DEaInfo.address3]").val(json.address3);
		form.find("[name=DEaInfo.postcode]").val(json.postcode);
		form.find("[name=DEaInfo.town]").val(json.town);
//		form.find("[name=DEaInfo.uprn]").val(json.uprn);
	});
}

function validateUserRole(checkBox, $certDate){
	if(checkBox.checked) $($certDate).addClass("required");
	else $($certDate).removeClass("required");
}
</script>
</head>

<body scroll="no">
	<div id="layout">
		<c:import url="layout/header.jsp" />
		<c:import url="layout/sidebar.jsp" />
		
		<div id="container">
			<div id="navTab" class="tabsPage">
				<div class="tabsPageHeader">
					<div class="tabsLeft">left</div>
					<div class="tabsRight">right</div>
					<div class="tabsMore">more</div>
					<div class="tabsPageHeaderContent">
						<ul class="navTab-tab">
							<li tabid="main" class="main"><a href="#"><span><span class="home_icon"></span>My Home</span></a></li>					
						</ul>
					</div>
				</div>
				<ul class="tabsMoreList">
					<li><a href="javascript:void(0)">My Home</a></li>
				</ul>
				<div class="navTab-panel tabsPageContent layoutBox">
					<div class="page unitBox">
					
						<div class="accountInfo">
							<div class="right">
								<p><fmt:formatDate value="${model.now}" pattern="dd MMMM yyyy, EEEE"/></p>
							</div>
							<p><span>Welcome, ${contextUser.firstName}&nbsp;${contextUser.lastName}</span></p>
							<p>${contextUser.company.name}</p>
						</div>
						
						<c:set var="company" value="${model.user.company}"></c:set>
						<div class="pageFormContent" layoutH="110">
							<p>
								<label>Name of Business:</label><span class="unit">${company.name}</span>
							</p>
							<p>
								<label>Address:</label>
								<!--<span class="unit">
								<c:if test="${!empty company.address1}">${company.address1},</c:if>
								<c:if test="${!empty company.address2}">${company.address2},</c:if>
								<c:if test="${!empty company.town}">${company.town},</c:if>
								${company.postcode}
								</span>-->
							</p>
							<p>
								<label>Phone:</label><span class="unit">${company.phone}</span>
							</p>
							<p>
								<label>Fax:</label><span class="unit">${company.fax}</span>
							</p>
							<p>
								<label>Website:</label><span class="unit">${company.website}</span>
							</p>
							<p>
								<label>Email:</label><span class="unit">${model.user.email}</span>
							</p>
							<p>
								<label>First Name:</label><span class="unit">${model.user.firstName}</span>
							</p>
							<p>
								<label>Last Name:</label><span class="unit">${model.user.lastName}</span>
							</p>
						</div>
			
					</div>
				</div>
			</div>
				
		</div>

	</div>

	<div id="footer">Copyright &copy; 2012 Quidos, All rights reserved.</div>

</body>
</html>