<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/include.inc.jsp"%>

<c:url var="formUrl" value="/transaction/updateCredits"/>
<form method="post" action="${formUrl}" class="required-validate pageForm" onsubmit="return validateCallback(this, navTabAjaxDone);">
	<input type="hidden" value="${user.id}" name="userId">
	<input type="hidden" value="Add_By_Admin" name="purpose">
	
	<div class="pageFormContent" layouth="70" style="height: 162px; overflow: auto;">
		<p>
			<label>Blance</label>
			<c:forEach var="item" items="${balances}">
				<span class="unit">
				${item.key.desc}: ${item.value} | 
				</span>
			</c:forEach>
		</p>
		
		<p>
			<label>Credit Type</label>
			<select id="creditType" name="creditType" class="required">
				<option value="">choose...</option>
				<c:forEach var="item" items="${balances}">
					<option value="${item.key}">${item.key.desc}</option>
				</c:forEach>
			</select>
		</p>
		
		<p>
			<label>Credits</label>
			<input type="text" id="credits" class="required number" name="creditCount" size="8"/>
		</p>
		
		<p>
			<label>Certification Number</label><span class="unit">${user.userName}</span>
		</p>
		<p>
			<label>Name</label>
			<span class="unit">${user.firstName}&nbsp;${user.surName}</span>
		</p>
		<p>
			<label>E-Mail</label>
			<span class="unit">${user.email}</span>
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
				</div>
			</li>
		</ul>
	</div>
</form>