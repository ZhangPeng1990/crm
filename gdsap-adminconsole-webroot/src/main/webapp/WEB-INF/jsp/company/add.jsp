<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/include.inc.jsp"%>

<h2 class="contentTitle">Add new Company</h2>
<c:url var="formUrl" value="/company/insert"/>
<form method="post" action="${formUrl}" class="required-validate pageForm" onsubmit="return validateCallback(this, navTabAjaxDone);">
	<div class="pageFormContent" layouth="98" style="height: 362px; overflow: auto;">
		<p>
			<label>Company ID</label>
			<input class="required" type="text" size="30" name="companyId">
		</p>
		
		<p>
			<label>Company Name</label>
			<input class="required" type="text" size="30" name="name" >
		</p>
		<p>
			<label>Email</label>
			<input type="text" size="30" name="email" >
		</p>
		<p>
			<label>Address1</label> 
			<input type="text" size="30" name="address1" >
		</p>
		<p>
			<label>Address2</label> 
			<input type="text" size="30" name="address2" >
		</p>
		<p>
			<label>Address3</label> 
			<input type="text" size="30" name="address3" >
		</p>
		<p>
			<label>Postcode</label> 
			<input type="text" size="30" name="postcode" >
		</p>
		<p>
			<label>Post Town</label> 
			<input type="text" size="30" name="posttown" >
		</p>
		<p>
			<label>Website</label>
			<input type="text" size="30" name="website" >
		</p>
		<p>
			<label>Fax</label>
			<input type="text" size="30" name="fax" >
		</p>
		<p>
			<label>Tel</label>
			<input type="text" size="30" name="tel" >
		</p>
		<p></p>
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