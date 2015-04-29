<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/include.inc.jsp"%>
<h2 class="contentTitle">Add new User</h2>
<c:url var="formUrl" value="/admin/update"/>
<form id="pageForm" name="adminEditForm" method="post" action="${formUrl}" class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone);">
<input type="hidden" name="admin.id" value="${admin.id}">
<input type="hidden" name="roleIds" value="${roleIds}">
	<div class="pageFormContent" layouth="98" style="height: 362px; overflow: auto;">
		<p>
			<label>UserName</label>
			<input class="required" type="text" size="30" name="admin.userName" value="${admin.userName}" readonly="readonly">
		</p>
		<p>
			<label>Password</label>
			<input type="text" name="admin.userPwd" class="required" size="30" maxlength="20" value="${admin.userPwd}"/>
		</p>
		<p>
			<label>FirstName</label>
			<input class="required" type="text" size="30" name="admin.firstName" value="${admin.firstName}">
		</p>
		<p>
			<label>SurName</label>
			<input class="required" type="text" value="${admin.surName}" size="30" name="admin.surName">
		</p>
		<p>
			<label>Email</label>
			<input class="required email" type="text" value="${admin.email}" size="30" name="admin.email">
		</p>
		<p>
			<label>Telephone</label>
			<input class="required phone" type="text" value="${admin.tel}" size="30" name="admin.tel">
		</p>
		<p>
			<label>Address1</label>
			<input class="required" type="text" value="${admin.address1}" size="30" name="admin.address1">
		</p>
		<p>
			<label>Address2</label>
			<input class="required" type="text" value="${admin.address2}" size="30" name="admin.address2">
		</p>
		<p>
			<label>Address3</label>
			<input class="required" type="text" value="${admin.address3}" size="30" name="admin.address3">
		</p>
		<p>
			<label>UserStatus</label>
			<select name="admin.userStatus">
			<c:forEach var="src" items="${userStatus}">
				<option value="${src}" ${src.code == admin.userStatus.code ? 'selected="selected"' : ''}>${src}</option>
			</c:forEach>
			</select>
		</p>
		<p>
		  <label>Role</label>
		</p>
		<p>
		<table border="0" width="400">
			<tr>
				<td width="40%">
				<select style="height: 200px; WIDTH: 300px" multiple name="list1" size="10" ondblclick="moveOption(document.adminEditForm.list1, document.adminEditForm.list2)">
					<c:forEach var="src" items="${allRoles}">
						<option value="${src.id}">${src.roleName}</option>
					</c:forEach>
				</select>
				</td>
				<td width="20%" align="center">
					<input type="button" value="addAll" onclick="moveAllOption(document.adminEditForm.list1, document.adminEditForm.list2)">
					<br/><br />
					<input type="button" value="add" onclick="moveOption(document.adminEditForm.list1, document.adminEditForm.list2)">
					<br /><br />
					<input type="button" value="remove" onclick="moveOption(document.adminEditForm.list2, document.adminEditForm.list1)">
					<br /><br />
					<input type="button" value="removeAll" onclick="moveAllOption(document.adminEditForm.list2, document.adminEditForm.list1)">
				</td>
				<td width="40%">
				<select style="height: 200px; WIDTH: 300px" multiple name="list2" size="12" ondblclick="moveOption(document.adminEditForm.list2, document.adminEditForm.list1)">
					<c:forEach var="src" items="${admin.roles}">
						<option value="${src.id}">${src.roleName}</option>
					</c:forEach>
				</select>
				</td>

			</tr>
		</table>
		</p>
	</div>
	<div class="formBar">
		<ul>
			<li>
				<div class="buttonActive">
					<div class="buttonContent">
						<button type="submit">Save</button>
					</div>
				</div>
			</li>
			<li>
				<div class="button">
					<div class="buttonContent">
						<button class="close" type="button">Close</button>
					</div>
				</div>
			</li>
		</ul>
	</div>
</form>
<script type="text/javascript">
function moveAllOption(e1, e2) {
	var fromObjOptions = e1.options;
	for ( var i = 0; i < fromObjOptions.length; i++) {
		fromObjOptions[0].selected = true;
		e2.appendChild(fromObjOptions[i]);
		i--;
	}
	document.adminEditForm.roleIds.value = getvalue(document.adminEditForm.list2);
}

function moveOption(e1, e2) {
	var fromObjOptions = e1.options;
	for ( var i = 0; i < fromObjOptions.length; i++) {
		if (fromObjOptions[i].selected) {
			e2.appendChild(fromObjOptions[i]);
			i--;
		}
	}
	document.adminEditForm.roleIds.value = getvalue(document.adminEditForm.list2);
}

function getvalue(geto) {
	var allvalue = "";
	for ( var i = 0; i < geto.options.length; i++) {
		allvalue += geto.options[i].value + ",";
	}
	return allvalue;
}

function changepos1111(obj, index) {
	if (index == -1) {
		if (obj.selectedIndex > 0) {
			obj.options(obj.selectedIndex).swapNode(
					obj.options(obj.selectedIndex - 1))
		}
	} else if (index == 1) {
		if (obj.selectedIndex < obj.options.length - 1) {
			obj.options(obj.selectedIndex).swapNode(
					obj.options(obj.selectedIndex + 1))
		}
	}

}
</script>