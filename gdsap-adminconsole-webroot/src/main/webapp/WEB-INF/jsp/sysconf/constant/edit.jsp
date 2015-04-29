<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/include.inc.jsp"%>

<c:forEach var="src" items="${listsys}" varStatus="status">
    <div class="unit">
		<label style="width:180px">${src.configurationRefId}</label>
		<input type="hidden" value="${src.configurationRefId}" name="configurationRefId">
		<c:choose>
			<c:when test="${type eq 'Template'}">
				<textarea rows="6" cols="80" name="content">${src.content}</textarea>
			</c:when>
			<c:otherwise>
				<input class="textInput" type="text" value="${src.content}" size="30" name="content">
			</c:otherwise>
		</c:choose>
		<span class="info">${src.configurationRefId.desc}</span>
	</div>
</c:forEach>