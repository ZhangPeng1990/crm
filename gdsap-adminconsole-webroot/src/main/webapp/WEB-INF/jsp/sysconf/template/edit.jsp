<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/include.inc.jsp"%>
<h2 class="contentTitle">Add SystemConfig</h2>
<c:url var="formUrl" value="/preference/update"/>
<form id="pageForm" name="adminAddForm" method="post" action="${formUrl}" class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone);">
<input type="hidden" name="id" value="${preference.preferenceRelId}">
	<div class="pageFormContent" layouth="98" style="height: 362px; overflow: auto;">
		<p>
			<label>ID</label>
			<select name="preferenceRelId">
			<c:forEach var="src" items="${ids}">
				<option value="${src}" ${src.code eq preference.preferenceRelId.code ? 'selected="selected"' : ''}>${src}</option>
			</c:forEach>
			</select>
		</p>
		<p>
			<label>PreType</label>
			<select name="preType">
			<c:forEach var="src" items="${preTyps}">
				<option value="${src.code}" ${src.code eq preference.preType.code ? 'selected="selected"' : ''}>${src}</option>
			</c:forEach>
			</select>
		</p>
		<p>
			<label>Title</label>
			<input class="required" type="text" size="30" name="title" value="${preference.title}">
		</p>
		<p>
			<label>Input Type</label>
			<select name="inputType">
			<c:forEach var="src" items="${inputTypes}">
				<option value="${src.code}" ${src.code eq preference.inputType.code ? 'selected="selected"' : ''}>${src}</option>
			</c:forEach>
			</select>
		</p><p>
			<label>Control Type</label>
			<select name="controlType">
			<c:forEach var="src" items="${controlTypes}">
				<option value="${src.code}">${src}</option>
			</c:forEach>
			</select>
		</p>
		<p></p>
		<p></p>
		<p></p>
		<p>
			<label>Content</label>
			<textarea rows="5" cols="20" name="content" style="width: 750px; height: 238px;">${preference.content}</textarea>
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
