<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/include.inc.jsp"%>

<c:import url="../../_frag/pager/pager-form.jsp"></c:import>
<c:url var="formUrl" value="/mailtemplate/index"/>
<form id="pageForm" rel="pagerForm" method="post" action="${formUrl}" onsubmit="return navTabSearch(this)">
	<div class="pageHeader">
		<div class="searchBar">
			<ul class="searchContent">
				<li>
					<label></label>
				</li>
				<li>
					<label></label>
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
			<li><a class="edit" href="mailtemplate/skipUpdate/{selectedObjId}" target="navTab" rel="userNav" warn="Please select an MailTemplate"><span>Edit</span></a></li>
		</ul>
	</div>

	<table class="table" layoutH="138" width="100%">
		<thead>
			<tr>
				<th width="80" orderField="USER_NAME" class="${param.orderField eq 'USER_NAME' ? param.orderDirection : ''}">Title</th>
				<th width="100" orderField="FIRST_NAME" class="${param.orderField eq 'FIRST_NAME' ? param.orderDirection : ''}">InsertTime</th>
				<th width="80" orderField="INSERT_DATE" class="${param.orderField eq 'INSERT_DATE' ? param.orderDirection : ''}">UpdateTime</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="item" items="${listmt}">
			<tr target="selectedObjId" rel="${item.mailTemplateRefId}">
				<td>${item.title}</td>
				<td><fmt:formatDate pattern="dd/MM/yyyy" value="${item.insertTime}"/></td>
				<td><fmt:formatDate pattern="dd/MM/yyyy" value="${item.updateTime}"/></td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
	
	<c:import url="../../_frag/pager/panel-bar.jsp"></c:import>
</div>
