<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/include.inc.jsp"%>
<div class="pageContent">
<form method="post" action="<c:url value='/admin/updateProfile'/>" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
<div class="pageFormContent" layoutH="60">                                                   
		<p>
			<label>UserName</label>
			<input type="hidden" name="admin.id" value="${AUTHENTICATION_ADMIN.id}">
			${AUTHENTICATION_ADMIN.userName }
		</p>
		<p>
			<label>Email</label>
			<input class="required email" type="text" value="${AUTHENTICATION_ADMIN.email }" size="30" name="admin.email">
		</p>
		<p>
			<label>First Name</label>
			<input class="required" type="text" value="${AUTHENTICATION_ADMIN.firstName}" size="30" name="admin.firstName">
		</p>
		<p>
			<label>Sur Name</label>
			<input class="required" type="text" value="${AUTHENTICATION_ADMIN.surName }" size="30" name="admin.surName">
		</p>
		<p>
			<label>Address1</label>
			<input type="text" value="${AUTHENTICATION_ADMIN.address1}" size="30" name="admin.address1">
		</p>
		<p>
			<label>Address2</label>
			<input type="text" value="${AUTHENTICATION_ADMIN.address2 }" size="30" name="admin.address2">
		</p>
		<p>
			<label>Address3</label>
			<input type="text" value="${AUTHENTICATION_ADMIN.address3 }" size="30" name="admin.address3">
		</p>
		<p>
			<label>Postcode</label>
			<input class="required" type="text" value="${AUTHENTICATION_ADMIN.postcode }" size="30" name="admin.postcode">
		</p>
		<p>
			<label>Posttown</label>
			<input class="required" type="text" value="${AUTHENTICATION_ADMIN.posttown }" size="30" name="admin.posttown">
		</p>
		<p>
			<label>Website</label>
			<input class="required" type="text" value="${AUTHENTICATION_ADMIN.website }" size="30" name="admin.website">
		</p>
		<p>
			<label>Fax</label>
			<input class="required" type="text" value="${AUTHENTICATION_ADMIN.fax }" size="30" name="admin.fax">
		</p>
		<p>
			<label>Tel</label>
			<input class="required" type="text" value="${AUTHENTICATION_ADMIN.tel }" size="30" name="admin.tel">
		</p>
		<p>
			<label>Company Name</label>
			<input class="required" type="text" value="${AUTHENTICATION_ADMIN.companyName }" size="30" name="admin.companyName">
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