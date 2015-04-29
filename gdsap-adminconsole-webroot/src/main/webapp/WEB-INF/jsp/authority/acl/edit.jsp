<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/include.inc.jsp"%>

<h2 class="contentTitle">Edit ACL</h2>
<c:url var="formUrl" value="/authority/acl/update"/>
<form  method="post" action="${formUrl}" class="required-validate pageForm" onsubmit="return validateCallback(this, navTabAjaxDone);">
    
	<div class="pageFormContent" layouth="98" style="height: 362px; overflow: auto;">
	    <p>
	        <label>ID</label>
	        <input class="required" type="text" size="30" name="id" value="${acl.id}">
	    </p>
		<p>
			<label>Title</label>
			<input class="required" type="text" size="30" name="title" value="${acl.title}">
		</p>
		<p>
			<label>DES</label>
			<input class="required" type="text" size="30" name="des" value="${acl.des}">
		</p>
		<p>
			<label>ACLGroup</label>
			<select class="valid" name="aclGroup">
				<c:forEach var="item" items="${groups}">
					<option value="${item}" ${acl.aclGroup eq item ? 'selected="selected"' : ''}>${item}</option>
				</c:forEach>
			</select>
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