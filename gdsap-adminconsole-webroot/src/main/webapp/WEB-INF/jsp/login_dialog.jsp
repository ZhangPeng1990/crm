<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%><%@ include file="/include.inc.jsp"%>

<div class="pageContent">
	
	<form method="post" action="<c:url value='/passport/login'/>" class="pageForm" onsubmit="return validateCallback(this, dialogAjaxDone)">
		<div class="pageFormContent" layoutH="58">
			<div class="unit">
				<label>Username：</label>
				<input type="text" name="username" class="required"/>
			</div>
			<div class="unit">
				<label>Password：</label>
				<input type="password" name="password" class="required"/>
			</div>
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">Log In</button></div></div></li>
			</ul>
		</div>
	</form>
	
</div>

