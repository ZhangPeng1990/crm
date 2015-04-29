<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/include.inc.jsp"%>

<h2 class="contentTitle">Edit User</h2>
<c:url var="formUrl" value="/user/updateUserName"/>
<form method="post" action="${formUrl}" class="required-validate pageForm" onsubmit="return validateCallback(this, navTabAjaxDone);">
	<input type="hidden" value="${user.id}" name="id">
	<div class="pageFormContent" layouth="98" style="height: 162px; overflow: auto;">
		<p>
			<label>UserName</label>
			<input class="required" type="text" size="15" name="userName" value="${user.userName}">
		</p>
	</div>
	<div class="formBar">
		<ul>
			<li>
				<div class="buttonActive">
					<div class="buttonContent">
						<button type="submit">Save</button>
					</div>
				</div></li>
			<li>
				<div class="button">
					<div class="buttonContent">
						<button class="close" type="button">Close</button>
					</div>
				</div></li>
		</ul>
	</div>
</form>