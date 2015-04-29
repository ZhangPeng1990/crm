<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/include.inc.jsp"%>

<c:import url="../_frag/pager/pager-form.jsp"></c:import>
<c:url var="formUrl" value="/report/index"/>

<script type="text/javascript">

$(document).ready(function()
{
	searchInputControl();
});
		
//onfocus
function searchInputControl()
{
	var defValue = "${defuShow}";
	
	var $domElement = $("#keywordsSearch");
	if($.trim($domElement.attr("value")) == '')
	{
		$domElement.val(defValue);
		$domElement.css("color", "#afafaf");
	}
	
	$domElement.focus(function(){
		if($domElement.attr("value") == defValue)
		{
			$domElement.val('');
			$domElement.css("color", "");
		}
	});
	
	$domElement.blur(function(){
		if($.trim($domElement.attr("value")) == '')
		{
			$domElement.val(defValue);
			$domElement.css("color", "#afafaf");
		}
	});
}
</script>
<form id="pageForm" rel="pagerForm" method="post" action="${formUrl}" onsubmit="return navTabSearch(this)">
	<input type="hidden" name="pageSize" value="${pageSize}">
	<div class="pageHeader">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>Lodgement Date From:</td>
					<td><input class="date textInput" type="text" dateFmt="dd/MM/yyyy" value="<fmt:formatDate pattern="dd/MM/yyyy" value="${object.startInspectionDate}"></fmt:formatDate>" name="startInspectionDate"></td>
					<td>Lodgement Date To:</td>
					<td><input class="date textInput" type="text" dateFmt="dd/MM/yyyy" value="<fmt:formatDate pattern="dd/MM/yyyy" value="${object.endInspectionDate}"></fmt:formatDate>" name="endInspectionDate"></td>
					<td>ReportStatus:</td>
					<td>
						<select name="reportStatus">
							<option value="">All</option>
							<c:forEach var="src" items="${status}">
								<option value="${src}" ${object.reportStatus.desc eq src.desc ? 'selected="selected"' : ''} >${src.desc}</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<td>Organisation ID:</td>
					<td><input class="textInput" type="text" name="organisationCertificationNumber" value="${object.organisationCertificationNumber}"></td>
					<td>Green Deal Advisor ID:</td>
					<td><input class="textInput" type="text"  name="userName" value="${object.userName}"></td>
					<td>Find What:</td>
					<td><input type="text" name="keywords" id="keywordsSearch" class="textInput" size="30" value="${object.keywords}"/></td>
				</tr>
			</table>
			
			<div class="subBar">
				<ul>						
					<li><div class="buttonActive"><div class="buttonContent"><button type="submit">Search</button></div></div></li>
					<!--  
					<li><a class="button" href="/report/skipSearch" target="dialog" mask="true" title="Advanced Search"><span>Advanced Search</span></a></li>
					-->
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
			<li><a class="edit" href="role/skipUpdate/{selectedObjId}" target="navTab" rel="roleEditNav" warn="Please select an Role"><span>Edit</span></a></li>
			-->
			<c:if test="${totalCount > 0}">
			<c:url var="dowUrl" value="/report/download"/>
			<li><a class="icon" href="${dowUrl}" target="dwzExport" targetType="navTab" title="Are you sure export?"><span>Export</span></a></li>
			</c:if>
		</ul>
	</div>

	<table class="table" layoutH="160" width="100%">
		<thead>
			<tr>
				<th width="14%">RRN</th>
				<th width="14%">OA RRN</th>
				<th width="9%">Lodgement date</th>
				<th width="7%">Organisation ID</th>
				<th width="5">User Type</th>
				<th width="13%">Green Deal Advisor ID</th>
				<th width="13%">D or ND (Default Domestic)</th>
				<th width="10%">Country</th>
				<th width="5%">Postcode</th>
				<th width="10%">Source</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="item" items="${reports}">
			<tr target="selectedObjId" rel="${item.id}">
				<td>${item.rrn}</td>
				<td>${item.oaRrn}</td>
				<td><fmt:formatDate pattern="dd/MM/yyyy" value="${item.lodgeDate}"/></td>
				<td>${item.user.organisationCertificationNumber}</td>
				<td>${item.user.userType}</td>
				<td><a href="user/skipUpdate/${item.user.id}" target="navTab" rel="userNav">${item.user.userName}</a></td>
				<td>Domestic</td>
				<td>${item.reportLocation.desc}</td>
				<td>${item.postcode}</td>
				<td>${item.uploadWay.desc}</td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
	
	<c:import url="../_frag/pager/panel-bar.jsp"></c:import>
</div>