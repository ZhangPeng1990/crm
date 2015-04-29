<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/include.inc.jsp"%>

<h2 class="contentTitle">${type}</h2>
<c:url var="formUrl" value="/systemConfig/update"/>
<form:form method="post" action="${formUrl}" class="required-validate pageForm" onsubmit="return validateCallback(this, navTabAjaxDone);" modelAttribute="form">
	<div class="pageFormContent">
		<div layoutH="98" style="float:left; display:block; overflow:auto; width:240px; border:solid 1px #CCC; line-height:21px; background:#fff">
		     <ul class="tree treeFolder expand">
				<li><a>${type}</a>
					<ul>
						<c:forEach var="src" items="${listg}">
					        <li>
							<a rel="extAttrBox" target="ajax" href="systemConfig/systemconstant/getCons/${src}/${type}">${src}</a>
						    </li>
					    </c:forEach>
					</ul>
				</li>
			</ul>
		</div>
	
		<div id="extAttrBox" class="unitBox" layoutH="98" style="margin-left:246px;border:solid 1px #CCC;">
			<c:import url="/WEB-INF/jsp/sysconf/constant/edit.jsp" charEncoding="UTF-8"></c:import>
		</div>
		
	</div>
	
    <div class="formBar">
		<ul>
			<li>
				<div class="buttonActive"><div class="buttonContent"><button type="submit">Save</button></div></div>
			</li>
			<li>
				<div class="button"><div class="buttonContent"><button class="close" type="button">Close</button></div></div>
			</li>
		</ul>
	</div>
</form:form>