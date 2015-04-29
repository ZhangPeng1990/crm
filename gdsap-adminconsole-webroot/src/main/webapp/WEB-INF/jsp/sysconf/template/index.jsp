<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/include.inc.jsp"%>

<c:import url="../../_frag/pager/pager-form.jsp"></c:import>
<c:url var="formUrl" value="/preference/index"/>
<form id="pageForm" rel="pagerForm" method="post" action="${formUrl}" onsubmit="return navTabSearch(this)">
	<div class="pageHeader">
		<div class="searchBar">
			<div class="subBar">
			</div>
		</div>
	</div>
</form>

<div class="pageContent">

	<div class="panelBar">
		<ul class="toolBar">
		<li><a class="delete" href="preference/delete/{selectedObjId}?rel=pageTagBox" target="ajaxTodo" warn="Please select a item." title="Are you sure remove?"><span>Remove</span></a></li>
		<li><a class="edit" href="preference/skipUpdate/{selectedObjId}" target="navTab" rel="userNav" warn="Please select an item"><span>Edit</span></a></li>
		</ul>
	</div>

	<table class="table" layoutH="115" width="100%">
		<thead>
			<tr>
				<th width="80">PreType</th>
				<th width="100"}">Title</th>
				<th width="60">InsertDate</th>
				<th width="60">InputType</th>
				<th width="60">ControlType</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="item" items="${listPreferences}">
			<tr target="selectedObjId" rel="${item.preferenceRelId}">
				<td>${item.preType.desc}</td>
				<td>${item.title}</td>
				<td><fmt:formatDate pattern="dd/MM/yyyy" value="${item.insertTime}"/></td>
				<td>${item.inputType.desc}</td>
				<td>${item.controlType.desc}</td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
	
	<c:import url="../../_frag/pager/panel-bar.jsp"></c:import>
</div>
