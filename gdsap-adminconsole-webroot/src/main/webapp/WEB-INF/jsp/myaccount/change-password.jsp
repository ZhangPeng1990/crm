<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/include.inc.jsp"%>
<div class="pageContent">
<form method="post" action="<c:url value='/admin/updatePassword'/>" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
<div class="pageFormContent" layoutH="60">                                                   
	<p class="br">
		<label>
			Old Password
		</label><input type="password" name="oldPassword" class="required"/>
	</p>
	<p class="br">
		<label>
			New Password
		</label><input type="password" name="password" id="newPassword" class="required" minlength="6" maxlength="20" />
	</p>
	<p class="br">
		<label>
			Confirm Password
		</label><input type="password" name="rPassword" class="required" equalTo="#newPassword"/>
	</p>

</div>
<div class="formBar">
	<ul>
		<li><div class="buttonActive"><div class="buttonContent"><button type="submit">Submit</button></div></div></li>
		<li><div class="button"><div class="buttonContent"><button type="button" class="close">Close</button></div></div></li>
	</ul>
</div>
</form>
</div>