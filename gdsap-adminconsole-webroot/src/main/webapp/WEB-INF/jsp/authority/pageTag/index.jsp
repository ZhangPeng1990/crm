<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/include.inc.jsp"%>

<h2 class="contentTitle">PagetagGroup</h2>

	<div class="pageFormContent">
		<div layoutH="98" style="float:left; display:block; overflow:auto; width:240px; border:solid 1px #CCC; line-height:21px; background:#fff">
		     <ul class="tree treeFolder expand">
				<li><a>PagetagGroup</a>
					<ul>
						<c:forEach var="src" items="${groups}">
					        <li>
							<a rel="pageTagBox" target="ajax" href="pageTag/list/${src}">${src}</a>
						    </li>
					    </c:forEach>
					</ul>
				</li>
			</ul>
		</div>
	
		<div id="pageTagBox" class="unitBox" layoutH="98" style="margin-left:246px;border:solid 1px #CCC;">
			<c:import url="/WEB-INF/jsp/authority/pageTag/listIndex.jsp" charEncoding="UTF-8"></c:import>
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
