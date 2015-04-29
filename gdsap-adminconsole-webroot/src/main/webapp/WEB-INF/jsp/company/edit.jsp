<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/include.inc.jsp"%>

<h2 class="contentTitle">Edit Company</h2>
<c:url var="formUrl" value="/company/update"/>
<form method="post" action="${formUrl}" class="required-validate pageForm" onsubmit="return validateCallback(this, navTabAjaxDone);">
	<div class="pageFormContent" layouth="98" style="height: 362px; overflow: auto;">
		<p>
			<label>Company ID</label>
			<input class="required" type="text" size="30" readonly="true" name="companyId" value="${company.companyId}">
		</p>
		
		<p>
			<label>Company Name</label>
			<input class="required" type="text" size="30" name="name" value="${company.name}">
		</p>
		<p>
			<label>Email</label>
			<input type="text" size="30" name="email" value="${company.email}">
		</p>
		<p>
			<label>Address1</label> 
			<input type="text" size="30" name="address1" value="${company.address1}">
		</p>
		<p>
			<label>Address2</label> 
			<input type="text" size="30" name="address2" value="${company.address2}">
		</p>
		<p>
			<label>Address3</label> 
			<input type="text" size="30" name="address3" value="${company.address3}">
		</p>
		<p>
			<label>Postcode</label> 
			<input type="text" size="30" name="postcode" value="${company.postcode}">
		</p>
		<p>
			<label>Post Town</label> 
			<input type="text" size="30" name="posttown" value="${company.posttown}">
		</p>
		<p>
			<label>Website</label>
			<input type="text" size="30" name="website" value="${company.website}">
		</p>
		<p>
			<label>Fax</label>
			<input type="text" size="30" name="fax" value="${company.fax}">
		</p>
		<p>
			<label>Tel</label>
			<input type="text" size="30" name="tel" value="${company.tel}">
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