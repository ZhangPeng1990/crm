<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/include.inc.jsp"%>

<h2 class="contentTitle">ACLGroup</h2>

	<div class="pageFormContent">
		<div layoutH="98" style="float:left; display:block; overflow:auto; width:240px; border:solid 1px #CCC; line-height:21px; background:#fff">
		     <ul class="tree treeFolder expand">
				<li><a>ACLGroup</a>
					<ul>
						<c:forEach var="item" items="${groups}">
					        <li>
							<a rel="aclBox" target="ajax" href="authority/acl/list/${item}">${item}</a>
						    </li>
					    </c:forEach>
					</ul>
				</li>
			</ul>
		</div>
	
		<div id="aclBox" class="unitBox" layoutH="98" style="margin-left:246px;border:solid 1px #CCC;">
			<c:import url="/WEB-INF/jsp/authority/acl/listIndex.jsp" charEncoding="UTF-8"></c:import>
		</div>
	</div>

	<div class="formBar">
		<ul>
			<li>
			</li>
			<li>
			</li>
		</ul>
	</div>	
