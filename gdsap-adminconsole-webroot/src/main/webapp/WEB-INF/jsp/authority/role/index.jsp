<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/include.inc.jsp"%>

<c:import url="../../_frag/pager/pager-form.jsp"></c:import>
<c:url var="formUrl" value="/role/index"/>
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
		    <!-- 
			<li><a class="add" rel="roleNav" target="navTab" href="role/add" title="Add new Role"><span>Add</span></a></li>
			 -->
			<li><a class="edit" href="role/skipUpdate/{selectedObjId}" target="navTab" rel="roleEditNav" warn="Please select an Role"><span>Edit</span></a></li>
		</ul>
	</div>

	<table class="table" layoutH="138" width="100%">
		<thead>
			<tr>
				<th width="80">Name</th>
				<th width="100">Des</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="item" items="${roles}">
			<tr target="selectedObjId" rel="${item.id}">
				<td>${item.roleName}</td>
				<td>${item.des}</td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
	
	<c:import url="../../_frag/pager/panel-bar.jsp"></c:import>
</div>
