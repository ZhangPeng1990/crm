<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/include.inc.jsp"%>

<h2 class="contentTitle">Add new User</h2>
<c:url var="formUrl" value="/user/insert"/>
<form method="post" action="${formUrl}" class="required-validate pageForm" onsubmit="return validateCallback(this, navTabAjaxDone);">
	<div class="pageFormContent" layouth="98" style="height: 362px; overflow: auto;">
		<p>
			<label>User Type</label>
			<select name="userType">
				<c:forEach var="src" items="${userTypes}">
					<option value="${src.code}" ${user.userType.code == src.code ? 'selected="selected"' : ''}>${src.desc}</option>
				</c:forEach>
			</select>
		</p>
		
		<p>
			<label>User Name</label>
			<input class="required" type="text" size="30" name="userName" value="${user.userName}">
		</p>
		<p>
			<label>Assessor ID</label>
			<input class="required" type="text" size="30" name="assessorID" value="${user.assessorID}">
		</p>
		<p>
			<label>GDregister Email</label>
			<input type="text" size="30" name="authUsername" value="${user.authUsername}">
		</p>
		<p>
			<label>GDregister Password</label>
			<input type="text" size="30" name="authPassword" value="${user.authPassword}">
		</p>
		<p>
			<label>First Name</label>
			<input type="text" size="30" name="firstName" value="${user.firstName}">
		</p>
		<p>
			<label>Surname</label> 
			<input type="text" size="30" name="surName" value="${user.surName}">
		</p>
		<p>
			<label>Email</label>
			<input type="text" size="30" name="email" value="${user.email}">
		</p>
		<p>
			<label>Organisation</label> 
			<input type="text" size="30" name="organisation" value="${user.organisation}">
		</p>
		<p>
			<label>Org. Web Site</label> 
			<input type="text"size="30" name="organisationWebSite" value="${user.organisationWebSite}">
		</p>
		<p>
			<label>Org. Cert Number</label> 
			<input type="text" size="30" name="organisationCertificationNumber" value="${user.organisationCertificationNumber}">
		</p>
		<p>
			<label>Address1</label> 
			<input type="text" size="30" name="address1" value="${user.address1}">
		</p>
		<p>
			<label>Address2</label> 
			<input type="text" size="30" name="address2" value="${user.address2}">
		</p>
		<p>
			<label>Address3</label> 
			<input type="text" size="30" name="address3" value="${user.address3}">
		</p>
		<p>
			<label>Postcode</label> 
			<input type="text" size="30" name="postcode" value="${user.postcode}">
		</p>
		<p>
			<label>Post Town</label> 
			<input type="text" size="30" name="posttown" value="${user.posttown}">
		</p>
		<p>
			<label>Website</label>
			<input type="text" size="30" name="website" value="${user.website}">
		</p>
		<p>
			<label>Fax</label>
			<input type="text" size="30" name="fax" value="${user.fax}">
		</p>
		<p>
			<label>Tel</label>
			<input type="text" size="30" name="tel" value="${user.tel}">
		</p>
		<p>
			<label>Company Name</label>
			<input type="text" size="30" name="companyName" value="${user.companyName}">
		</p>
		<p>
			<label>Insurer</label>
			<input type="text" size="30" name="insurer" value="${user.insurer}">
		</p>
		<p>
			<label>Policy No</label>
			<input type="text" size="30" name="policyNo" value="${user.policyNo}">
		</p>
		<p>
			<label>Effective Date</label>
			<input class="date textInput" type="text" dateFmt="dd/MM/yyyy" value="<fmt:formatDate pattern="dd/MM/yyyy" value="${user.effectiveDate}"></fmt:formatDate>" name="effectiveDate">
			<a class="inputDateButton" href="javascript:;">Choose</a>
		</p>
		<p>
			<label>Expiry Date</label>
			<input class="date textInput" type="text" dateFmt="dd/MM/yyyy" value="<fmt:formatDate pattern="dd/MM/yyyy" value="${user.expiryDate}"></fmt:formatDate>" name="expiryDate">
			<a class="inputDateButton" href="javascript:;">Choose</a>
		</p>
		<p>
			<label>Pi Limit</label>
			<input type="text" size="10" name="piLimit" value="${user.piLimit}">
		</p>
		<p>
			<label>User Status</label>
			<select name="userStatus">
				<c:forEach var="src" items="${userStatus}">
					<option value="${src.code}">${src}</option>
				</c:forEach>
			</select>
		</p>
		<p></p>
		<p>
			<label>Scot Auth Username</label>
			<input type="text" size="30" name="sctAuthorizationUsername" value="${user.sctAuthorizationUsername}">
		</p>
		<p>
			<label>Scot Auth Password</label>
			<input type="text" size="30" name="sctAuthorizationPassword" value="${user.sctAuthorizationPassword}">
		</p>
		<p>
			<label>Scot Assessor Org Id</label>
			<input type="text" size="30" name="sctAssessorOrganisationid" value="${user.sctAssessorOrganisationid}">
		</p>
		<p>
			<label>SCT Adviser Id</label>
			<input type="text" size="30" name="sctAdviserId" value="${user.sctAdviserId}">
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