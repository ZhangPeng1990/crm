<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%><%@ include file="/include.inc.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title><fmt:message key="ui.title" /></title>
<link href="<c:url value='/styles/dwz/themes/default/style.css'/>" rel="stylesheet" type="text/css" />
<link href="<c:url value='/styles/dwz/themes/css/core.css'/>" rel="stylesheet" type="text/css" />
<link href="<c:url value='/styles/dwz/themes/css/login.css'/>" rel="stylesheet" type="text/css" />
<script src="<c:url value='/styles/dwz/js/jquery-1.7.2.min.js'/>" type="text/javascript"></script>
<script src="<c:url value='/styles/dwz/js/jquery.validate.min.js'/>" type="text/javascript"></script>
<script src="<c:url value='/styles/dwz/js/dwz.alertMsg.js'/>" type="text/javascript"></script>
<script type="text/javascript">
$(function(){
	<c:if test="${!empty error}">alertMsg.error("${error}")</c:if>
	

});

function toggleBox(boxId){
	var $box = $("#"+boxId);
	if ($box.is(":visible")) $box.slideUp();
	else $box.slideDown();
}

function validateCallback(form) {
	var $form = $(form);
	if (!$form.valid()) {
		return false;
	}

	$.ajax({
		type: form.method || 'POST',
		url:$form.attr("action"),
		data:$form.serializeArray(),
		dataType:"json",
		cache: false,
		success: function(){
			if (json.statusCode == 200) {
				if (json.message) alertMsg.correct(json.message);
			} else {
				if (json.message) alertMsg.error(json.message);
			}
		}
	});
	return false;
}

</script>
</head>

<body>
	<div id="login">
		<div id="login_header">
			<h1 class="login_logo">
				<img src="<c:url value='/styles/dwz/themes/default/images/logo.png'/>" />
			</h1>
			<div class="login_info"><fmt:message key="ui.title" /></div>
			<div class="login_headerContent">
				<div class="navList">
					<ul>
						<li><a href="#">Help</a></li>
					</ul>
				</div>
				<h2 class="login_title">Please log in here</h2>
			</div>
		</div>
		<div id="login_content">
			<div class="loginForm">
				<form method="post" action="<c:url value='/passport/login'/>">
					<p>
						<label>Username：</label>
						<input type="text" name="username" size="20" class="login_input" />
					</p>
					<p>
						<label>Password：</label>
						<input type="password" name="password" size="20" class="login_input" />
					</p>
					
					<div class="login_bar">
						<input class="sub" type="submit" value=""/>
					</div>
				</form>
			</div>
			<div class="login_banner"><img src="<c:url value='/styles/dwz/themes/default/images/login_banner.jpg'/>" /></div>
			<div class="login_main">
				<ul class="helpList">
					<li><a href="javascript:toggleBox('forgotPwd')">Forgot Password?</a></li>
					<li id="forgotPwd" style="background: none; display: none">
					
					<span class="info">Please enter your username to retrieve your password.</span>
					<form method="post" action="<c:url value='/user/forgetPassword'/>" class="required-validate" onsubmit="return validateCallback(this);">
						<p>
							<label>Username：</label>
							<input type="text" name="username" class="textInput required" size="20"/>
						</p>
						<p><input type="submit" value="Submit"/></p>
					</form>
					</li>
				</ul>

				<div class="login_inner">
					<p></p>
				</div>
			</div>
		</div>
		<div id="login_footer">
			Copyright &copy; 2009 iQ-Energy Inc. All Rights Reserved.
		</div>
	</div>
</body>
</html>
