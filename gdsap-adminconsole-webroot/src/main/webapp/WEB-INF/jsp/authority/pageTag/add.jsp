<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/include.inc.jsp"%>

<h2 class="contentTitle">Add new PageTag</h2>
<c:url var="formUrl" value="/pageTag/insert"/>
<form id="pageForm" method="post" action="${formUrl}" class="required-validate pageForm" onsubmit="return validateCallback(this, navTabAjaxDone);">
	<div class="pageFormContent" layouth="98" style="height: 362px; overflow: auto;">
		<p>
			<label>Title</label>
			<input class="required" type="text" size="30" name="title">
		</p>
		<p>
			<label>Url</label>
			<input class="required" type="text" size="30" name="url">
		</p>
		<p>
			<label>Group</label>
			<select class="required valid" name="pagetagGroup">
			    <option value="" selected="selected">choose...</option>
				<c:forEach var="src" items="${groups}">
					<option value="${src}">${src}</option>
				</c:forEach>
			</select>
		</p>
		<p>
			<label>Sequence</label>
			<input class="required digits" type="text" value="" size="30" name="sequence">
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