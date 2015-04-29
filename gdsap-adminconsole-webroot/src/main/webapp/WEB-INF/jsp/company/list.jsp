<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/include.inc.jsp"%>

<c:import url="../_frag/pager/pager-form.jsp"></c:import>
<c:url var="formUrl" value="/company/listCompany"/>
<form id="pageForm" rel="pagerForm" method="post" action="${formUrl}" onsubmit="return navTabSearch(this)">
	<input type="hidden" name="pageSize" value="${pageSize}">
	<div class="pageHeader">
		<div class="searchBar">
			<ul class="searchContent">
				<li>
					<label>Keywords:</label>
					<input type="text" name="keywords" value="${param.keywords}"/>
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
		<li><a class="edit" href="company/skipUpdate/{selectedObjId}" target="navTab" rel="userNav" warn="Please select a Company"><span>Edit</span></a></li>
		</ul>
	</div>

	<table class="table" layoutH="138" width="100%">
		<thead>
			<tr>
				<th width="80">ID</th>
				<th width="80">Company Name</th>
				<th width="70">Email</th>
				<th width="80" >address</th>
				<th width="60" >PostCode</th>
				<th width="60" >PostTowm</th>
				<th width="80">web Site</th>
				<th width="50">Tel</th>
				<th width="50">Fax</th>
			</tr>
		</thead>
		<tbody>
		 <c:forEach var="item" items="${companys}">
			<tr target="selectedObjId" rel="${item.companyId}">
			  <td>${item.companyId}</td>
			  <td>${item.name}</td>
			  <td>${item.email}</td>
			  <td>${item.address1},${item.address2},${item.address3}</td>
			  <td>${item.postcode}</td>
			  <td>${item.posttown}</td>
			  <td>${item.website}</td>
			  <td>${item.tel}</td>
			  <td>${item.fax}</td>
			</tr>
		 </c:forEach>	
		</tbody>
	</table>
	
	<c:import url="../_frag/pager/panel-bar.jsp"></c:import>
</div>
