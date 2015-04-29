<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/include.inc.jsp"%>
<style>
<!--
fieldset.not_init{border-color: red;}
-->
</style>
<div class="pageContext" layoutH="50">
	<c:url var="formUrl" value="/mailhost/update"/>
	<form action="${formUrl}" method="post" class="required-validate pageForm pageFormContent" onsubmit="return validateCallback(this)">
	<input type="hidden" name="hostconfig.hostRefId" value="${item.hostRefId}">
		<fieldset  class="nowrap ${!item.initial ? 'not_init':'' }" >
			<legend>${item.hostRefId.desc }</legend>
			<p><label>Smtp : </label><input type="text" name="hostconfig.smtp" class="required" value="${item.smtp }" size="40"/></p>
			<p><label>Smtp port : </label><input type="text" name="hostconfig.smtpPort" class="required" value="${item.smtpPort }" size="10"/></p>
			<p><label>From Address : </label><input type="text" name="hostconfig.fromEmailAddress" class="required" value="${item.fromEmailAddress }" size="40"/></p>
			<p><label>Username : </label><input type="text" name="hostconfig.emailUsername" class="required" value="${item.emailUsername }" size="40"/></p>
			<p><label>Password : </label><input type="text" name="hostconfig.emailUserPwd" class="required" value="${item.emailUserPwd }" size="40"/></p>
			<p><label>Security : </label>
			<select name="hostconfig.securityable">
				<c:forEach var="yesnoItem" items="${securitys }">
					<c:choose>
						<c:when test="${yesnoItem eq item.securityable}"><option value="${yesnoItem.code }" selected="selected">${yesnoItem.desc }</option></c:when>
						<c:otherwise><option value="${yesnoItem.code }">${yesnoItem.desc }</option></c:otherwise>
					</c:choose>
				</c:forEach>
			</select>
			</p>
			<p><label>Debug Model : </label>
			<select name="hostconfig.debugable">
				<c:forEach var="yesnoItem" items="${debugs }">
					<c:choose>
						<c:when test="${yesnoItem eq item.debugable}"><option value="${yesnoItem.code }" selected="selected">${yesnoItem.desc }</option></c:when>
						<c:otherwise><option value="${yesnoItem.code }">${yesnoItem.desc }</option></c:otherwise>
					</c:choose>
				</c:forEach>
			</select>
			</p>
			<p><label>Charset : </label><input type="text" name="hostconfig.charset" class="required" value="${item.charset }" size="10"/></p>
			<p>
				<div class="buttonActive">
					<div class="buttonContent">
						<button type="submit">Save</button>
					</div>
				</div>
			</p>
		</fieldset>
	</form>
</div>