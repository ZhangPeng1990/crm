<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/include.inc.jsp"%>

<c:import url="../../_frag/pager/pager-form.jsp"></c:import>
<c:url var="formUrl" value="/authority/acl/list/${group}"/>
<form rel="pagerForm" method="post" action="${formUrl}">
</form>
<div class="pageContent">
	<div class="panelBar">
		<ul class="toolBar">
		    <!-- 
			<li><a class="add" rel="aclNav" target="navTab" href="authority/acl/add" title="Add new ACL"><span>Add</span></a></li>
			 -->
			<li><a class="edit" href="<c:url value='/authority/acl/skipUpdate/{selectedObjId}'/>" target="navTab" rel="userNav" warn="Please select an Acl"><span>Edit</span></a></li>
		</ul>                                       
	</div>
		<table class="table" layoutH="175" width="100%">
		<thead>
			<tr>
				<th width="80">ID</th>
				<th width="100">TITLE</th>
				<th width="80">DES</th>
				<th width="60">ACL_GROUP</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="item" items="${acl}">
			<tr target="selectedObjId" rel="${item.id}">
			    <td>${item.id}</td>
				<td>${item.title}</td>
				<td>${item.des}</td>
				<td>${item.aclGroup}</td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
	<c:import url="../../_frag/pager/panel-bar.jsp?rel=aclBox"></c:import>
</div>
