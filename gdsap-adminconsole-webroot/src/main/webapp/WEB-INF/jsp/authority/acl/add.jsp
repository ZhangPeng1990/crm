<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/include.inc.jsp"%>

<h2 class="contentTitle">Add new Acl</h2>
<c:url var="formUrl" value="/authority/acl/insert"/>
<form id="pageForm" method="post" action="${formUrl}" class="required-validate pageForm" onsubmit="return validateCallback(this, navTabAjaxDone);">
	<div class="pageFormContent" layouth="98" style="height: 362px; overflow: auto;">
		<p>
			<label>ID</label>
			<input class="required" type="text" size="30" name="id">
		</p>
		<p>
			<label>Title</label>
		    <input class="required" type="text" value="" size="30" name="title">
		</p>
		<p>
		    <label>AclGroup</label> 
			<select name="aclGroup">
			    <option value="Content">Content</option>
				<option value="Auditor">Auditor</option>
				<option value="Other">Other</option>
			</select>	
		</p>
		<p>
			<label>Des</label>
			<input class="required" type="text" value="" size="30" name="des">
		</p>
		
	</div>
	<div class="formBar">
		<ul>
			<li>
				<div class="buttonActive">
					<div class="buttonContent">
						<button type="submit">Save</button>
					</div>
				</div>
			</li>
			<li>
				<div class="button">
					<div class="buttonContent">
						<button class="close" type="button">Close</button>
					</div>
				</div>
			</li>
		</ul>
	</div>
</form>