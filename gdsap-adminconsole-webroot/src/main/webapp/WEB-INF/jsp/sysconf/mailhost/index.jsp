<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/include.inc.jsp"%>

<c:import url="../../_frag/pager/pager-form.jsp"></c:import>
<c:url var="formUrl" value="/mailhost/index"/>
<form id="pageForm" rel="pagerForm" method="post" action="${formUrl}" onsubmit="return navTabSearch(this)">
	
	<div class="pageHeader">
		<div class="searchBar">
			<ul class="searchContent">
				<li>
				</li>
				<li>
				</li>
			</ul>
			<div class="subBar">
				<ul>						
					<li></li>
				</ul>
			</div>
		</div>
	</div>
</form>

<div class="pageContent">

	<div class="panelBar">
		<ul class="toolBar">
			<li><a class="edit" href="mailhost/skipUpdate/{selectedObjId}" target="navTab" rel="mialHostNav" warn="Please select an MialHost"><span>Edit</span></a></li>
		</ul>
	</div>

	<table class="table" layoutH="138" width="100%">
		<thead>
			<tr>
				<th width="80">SMTP</th>
				<th width="100">SMTPPORT</th>
				<th width="80">FROMEMAILADDRESS</th>
				<th width="60">emailusername</th>
				<th width="60">securityable</th>
				<th width="60">debugable</th>
				<th width="60">charset</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="item" items="${listc}">
			<tr target="selectedObjId" rel="${item.hostRefId}">
				<td>${item.smtp}</td>
				<td>${item.smtpPort}</td>
				<td>${item.fromEmailAddress}</td>
				<td>${item.emailUsername}</td>
				<td>${item.securityable}</td>
				<td>${item.debugable}</td>
				<td>${item.charset}</td>
			</tr>
			</c:forEach>
			
		</tbody>
	</table>
	
	<c:import url="../../_frag/pager/panel-bar.jsp"></c:import>
</div>
