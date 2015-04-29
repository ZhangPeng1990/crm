<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/include.inc.jsp"%>

<c:import url="../_frag/pager/pager-form.jsp"></c:import>
<c:url var="formUrl" value="/admin/listByRole/${roleId}"/>
<form rel="pagerForm" method="post" action="${formUrl}" onsubmit="return divSearch(this, 'adminTagBox');">
<input type="hidden" name="roleId" value="${roleId}">
	<div class="pageHeader">
		<div class="searchBar">
			<ul class="searchContent">
				<li>
					<label>Keywords:</label>
					<input type="text" name="keywords" value="${object.keywords}"/>
				</li>
				<li>
					<label>Status:</label>
					<select name="userStatus">
						<option value="">All</option>
						<option value="Active" ${object.userStatus.code==1 ? 'selected="selected"' : ''}>Active</option>
						<option value="Register_UnActived" ${object.userStatus.code==0 ? 'selected="selected"' : ''}>Register_UnActived</option>
						<option value="Forbit" ${object.userStatus.code==2 ? 'selected="selected"' : ''}>Forbit</option>
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
			<!-- 
			<li><a class="add" rel="${rel}" target="navTab" href="admin/add" title="Add new admin"><span>Add</span></a></li>
			 -->
			<li><a class="edit" href="admin/edit/{selectedObjId}" target="navTab" rel="userNav" warn="Please select an admin"><span>Edit</span></a></li>
		</ul>
	</div>
	<table class="table" layoutH="240" width="100%">
		<thead>
			<tr>
				<th width="80">Cert No.</th>
				<th width="100">Cert Name</th>
				<th width="80">Entry Date</th>
				<th width="60">Status</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="item" items="${listAdmin}">
			<tr target="selectedObjId" rel="${item.id}">
				<td>${item.userName}</td>
				<td>${item.firstName}&nbsp;${item.surName}</td>
				<td><fmt:formatDate pattern="dd/MM/yyyy" value="${item.insertTime}"/></td>
				<td>${item.userStatus}</td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
	<c:import url="../_frag/pager/panel-bar.jsp?rel=adminTagBox"></c:import>
</div>
