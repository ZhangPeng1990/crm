<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/include.inc.jsp"%>

<c:import url="../_frag/pager/pager-form.jsp"></c:import>
<c:url var="formUrl" value="/user/index"/>
<form id="pageForm" rel="pagerForm" method="post" action="${formUrl}" onsubmit="return navTabSearch(this)">
	<input type="hidden" name="pageSize" value="${pageSize}">
	<div class="pageHeader">
		<div class="searchBar">
			<ul class="searchContent">
				<li>
					<label>Keywords:</label>
					<input type="text" name="keywords" value="${param.keywords}"/>
				</li>
				<li>
					<label>UserStatus:</label>
					<select name="userStatus">
						<option value="">All</option>
						<option value="Active" ${object.userStatus.code == 1 ? 'selected="selected"' : ''}>Active</option>
						<option value="InActive" ${object.userStatus.code == 2 ? 'selected="selected"' : ''}>InActive</option>
					</select>
				</li>
				<li>
					<label>User Type:</label>
					<select name="userType">
						<option value="">All</option>
						<option value="GDA" ${object.userType.code == 0 ? 'selected="selected"' : ''}>GDA</option>
						<option value="GDP" ${object.userType.code == 1 ? 'selected="selected"' : ''}>GDP</option>
					</select>
				</li>
			</ul>
			<div class="subBar">
				<ul>						
					<li><div class="buttonActive"><div class="buttonContent"><button type="submit">Search</button></div></div></li>
				</ul>
			</div>
		</div>
	</div>
</form>

<div class="pageContent">

	<div class="panelBar">
		<ul class="toolBar">
		<li><a class="edit" href="user/skipUpdate/{selectedObjId}" target="navTab" rel="userNav" warn="Please select an user"><span>Edit</span></a></li>
		<li><a class="edit" href="user/skipUpdateUserName/{selectedObjId}" target="dialog" mask="true" width="480" title="Update UserName" warn="Please select an user"><span>Edit UserName</span></a></li>
		<c:if test="${totalCount > 0}">
			<c:url var="dowUrl" value="/user/download"/>
			<li><a class="icon" href="${dowUrl}" target="dwzExport" targetType="navTab" title="Are you sure export?"><span>Export</span></a></li>
		</c:if>
		<!-- 
		<li><a class="icon" href="transaction/skipEditCredits/{selectedObjId}" target="dialog" mask="true" width="600" title="Edit Credits" warn="Please select an user"><span>Edit Credits</span></a></li>
		 -->
		</ul>
	</div>

	<table class="table" layoutH="138" width="100%">
		<thead>
			<tr>
				<th width="80" orderField="USERNAME" class="${param.orderField eq 'USERNAME' ? param.orderDirection : ''}">UserName</th>
				<th width="80" orderField="ASSESSOR_ID" class="${param.orderField eq 'ASSESSOR_ID' ? param.orderDirection : ''}">Assessor ID</th>
				<th width="100" orderField="Org_Cert_Number" class="${param.orderField eq 'Org_Cert_Number' ? param.orderDirection : ''}">Org.Cert Number</th>
				<th width="80" orderField="USER_TYPE" class="${param.orderField eq 'USER_TYPE' ? param.orderDirection : ''}">User Type</th>
				<th width="100" orderField="EMAIL" class="${param.orderField eq 'EMAIL' ? param.orderDirection : ''}">Email</th>
				<th width="80" orderField="INSERT_DATE" class="${param.orderField eq 'INSERT_DATE' ? param.orderDirection : ''}">Insert Date</th>
				<th width="60" orderField="ACCESS_LEVEL" class="${param.orderField eq 'ACCESS_LEVEL' ? param.orderDirection : ''}">AccessLevel</th>
				<th width="60" orderField="STATUS" class="${param.orderField eq 'STATUS' ? param.orderDirection : ''}">UserStatus</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="item" items="${users}">
			<tr target="selectedObjId" rel="${item.id}">
				<td><a href="user/skipUpdate/${item.id}" target="navTab" rel="userNav">${item.userName}</a></td>
				<td>${item.assessorID}</td>
				<td>${item.organisationCertificationNumber}</td>
				<td>${item.userType}</td>
				<td>${item.email}</td>
				<td><fmt:formatDate pattern="dd/MM/yyyy" value="${item.insertTime}"/></td>
				<td>${item.accessLevel.desc}</td>
				<td>${item.userStatus.desc}</td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
	
	<c:import url="../_frag/pager/panel-bar.jsp"></c:import>
</div>
