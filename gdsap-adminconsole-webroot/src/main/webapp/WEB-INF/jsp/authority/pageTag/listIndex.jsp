<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/include.inc.jsp"%>
<c:import url="../../_frag/pager/pager-form.jsp"></c:import>

<c:url var="formUrl" value="/pageTag/list/${group}"/>
<form rel="pagerForm" method="post" action="${formUrl}">
</form>
<div class="pageContent">
	<div class="panelBar">
		<ul class="toolBar">
		    <!-- 
			<li><a class="add" rel="pageTagNav" target="navTab" href="pageTag/add" title="Add new PageTag"><span>Add</span></a></li>
			 -->
			<li><a class="delete" href="pageTag/delete/{selectedObjId}?rel=pageTagBox" target="ajaxTodo" warn="Please select a pageTag." title="Are you sure remove?"><span>Remove</span></a></li>
			<li><a class="edit" href="pageTag/skipUpdate/{selectedObjId}" target="navTab" rel="userNav" warn="Please select a pageTag"><span>Edit</span></a></li>
		</ul>
	</div>
    <c:import url="../../_frag/pager/pager-form.jsp"></c:import>
	<table class="table" layoutH="175" width="100%">
		<thead>
			<tr>
				<th width="80">Title</th>
				<th width="100">Url</th>
				<th width="80">Sequence</th>
				<th width="60">Group</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="item" items="${setPageTag}">
			<tr target="selectedObjId" rel="${item.id}">
				<td>${item.title}</td>
				<td>${item.url}</td>
				<td>${item.sequence}</td>
				<td>${item.pagetagGroup}</td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
	
	<c:import url="../../_frag/pager/panel-bar.jsp?rel=pageTagBox"></c:import>
</div>
