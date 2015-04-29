<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/include.inc.jsp"%>
<div id="leftside">
	<div id="sidebar_s">
		<div class="collapse">
			<div class="toggleCollapse"><div></div></div>
		</div>
	</div>
	<div id="sidebar">
		<div class="toggleCollapse"><h2>Menu</h2><div>up</div></div>
		<div class="accordion" fillSpace="sidebar">
		
		<c:forEach var="src" items="${pageTagsMap}">
			<div class="accordionHeader">
				<h2><span>Folder</span>${src.key}</h2>
			</div>
			<div class="accordionContent">
				<ul class="tree treeFolder">
				<c:forEach var="items" items="${pageTagsMap[src.key]}">
					<li><a href="${items.url}" target="navTab" rel="${items.id}">${items.title}</a></li>
				</c:forEach>
				</ul>
			</div>
		</c:forEach>
		</div>
	</div>
</div>