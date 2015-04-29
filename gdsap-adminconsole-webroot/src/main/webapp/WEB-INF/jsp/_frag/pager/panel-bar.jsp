 <%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/include.inc.jsp"%>
<div class="panelBar">
	<div class="pages">
		<span>Per Page</span>
		<select name="pageSize" onchange="navTabPageBreak({numPerPage:this.value}<c:if test='${!empty param.rel}'>, '${param.rel}'</c:if>)" >
			<c:forEach begin="10" end="40" step="10" varStatus="s">
				<option value="${s.index}" ${pageSize eq s.index ? 'selected="selected"' : ''}>${s.index}</option>
			</c:forEach>
		</select>
		<span>Total: ${totalCount}</span>
	</div>
	
	<div class="pagination" targetType="navTab" totalCount="${totalCount}" numPerPage="${pageSize}" pageNumShown="10" currentPage="${param.pageNum}" <c:if test="${!empty param.rel}">rel="${param.rel}"</c:if>></div>
</div>