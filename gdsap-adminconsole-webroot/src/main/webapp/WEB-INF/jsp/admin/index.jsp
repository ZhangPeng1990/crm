<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/include.inc.jsp"%>

<h2 class="contentTitle">Roles</h2>


	<div class="pageFormContent">
		<div layoutH="98" style="float:left; display:block; overflow:auto; width:240px; border:solid 1px #CCC; line-height:21px; background:#fff">
		     <ul class="tree treeFolder expand">
				<li><a>Roles</a>
					<ul>
						<li><a rel="adminTagBox" target="ajax" href="admin/listByRole/''">All</a></li>
						<c:forEach var="src" items="${setRole}">
					        <li>
							<a rel="adminTagBox" target="ajax" href="admin/listByRole/${src.id}">${src.roleName}</a>
						    </li>
					    </c:forEach>
					</ul>
				</li>
			</ul>
		</div>
	
		<div id="adminTagBox" class="unitBox" layoutH="98" style="margin-left:246px;border:solid 1px #CCC;">
			<c:import url="/WEB-INF/jsp/admin/listIndex.jsp" charEncoding="UTF-8"></c:import>
		</div>
	</div>

	<div class="formBar">
		<ul>
			<li></li>
			<li></li>
		</ul>
	</div>	
