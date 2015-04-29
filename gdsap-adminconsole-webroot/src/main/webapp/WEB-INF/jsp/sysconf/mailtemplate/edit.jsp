<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/include.inc.jsp"%>

<h2 class="contentTitle">Edit MailTemplate</h2>
<c:url var="formUrl" value="/mailtemplate/update"/>
<form:form id="pageForm" method="post" action="${formUrl}" class="required-validate pageForm" onsubmit="return validateCallback(this, navTabAjaxDone);" modelAttribute="form">
    <input type="hidden" name="mailTemplateRefId" value="${mailtemplate.mailTemplateRefId}">
	<div class="pageFormContent" layouth="98" style="height: 181px; overflow: auto;">
		<p>
			<label>Title</label>
			<input class="required" type="text" size="30" name="title" value="${mailtemplate.title}">
		</p>
		<p>
			<label></label>
		</p>
		<p>
			<label>Content</label>
			<textarea rows="6" cols="80" name="content">${mailtemplate.content}</textarea>
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
</form:form>