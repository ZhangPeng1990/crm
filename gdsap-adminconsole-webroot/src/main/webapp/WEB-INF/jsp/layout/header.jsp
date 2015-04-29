<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%><%@ include file="/include.inc.jsp"%>
<div id="header">
	<div class="headerNav">
		<a class="logo" href="/" target="_blank">Logo</a>
		
		<ul class="nav">
			<li><a href="<c:url value='/admin/changeProfile'/>" target="dialog" mask="true" width="680">Profile</a></li>
			<li><a href="<c:url value='/admin/changePwd'/>" target="dialog" mask="true" width="680">Change Password</a></li>
			<li><a href="<c:url value='/passport/logout'/>">Log out</a></li>
		</ul>
		<!-- <ul id="themeList" class="themeList">
			<li class="default" theme="default"><div class="selected">Blue</div></li>
			<li class="green" theme="green"><div>Green</div></li>
			<li class="purple" theme="purple"><div>Purple</div></li>
			<li class="silver" theme="silver"><div>Silver</div></li>
			<li class="azure" theme="azure"><div>Azure</div></li>
		</ul> -->
	</div>
</div>