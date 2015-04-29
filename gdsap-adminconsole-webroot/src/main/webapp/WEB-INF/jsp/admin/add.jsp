<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/include.inc.jsp"%>
<h2 class="contentTitle">Add new User</h2>
<c:url var="formUrl" value="/admin/insert"/>
<form id="pageForm" name="adminAddForm" method="post" action="${formUrl}" class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone);">
<input type="hidden" name="roleIds" >
	<div class="pageFormContent" layouth="98" style="height: 362px; overflow: auto;">
		<p>
			<label>UserName</label>
			<input class="required" type="text" size="30" name="admin.userName">
		</p>
		<p>
			<label>FirstName</label>
			<input class="required" type="text" size="30" name="admin.firstName">
		</p>
		<p>
			<label>SurName</label>
			<input class="required" type="text" value="" size="30" name="admin.surName">
		</p>
		<p>
			<label>Email</label>
			<input class="required email" type="text" value="" size="30" name="admin.email">
		</p>
		<p>
			<label>Telephone</label>
			<input class="required phone" type="text" value="" size="30" name="admin.tel">
		</p>
		<p>
			<label>Address1</label>
			<input class="required" type="text" value="" size="30" name="admin.address1">
		</p>
		<p>
			<label>Address2</label>
			<input class="required" type="text" value="" size="30" name="admin.address2">
		</p>
		<p>
			<label>Address3</label>
			<input class="required" type="text" value="" size="30" name="admin.address3">
		</p>
		<p>
			<label>UserStatus</label>
			<select name="admin.userStatus">
			<c:forEach var="src" items="${userStatus}">
				<option value="${src}" >${src}</option>
			</c:forEach>
			</select>
		</p>
		<p></p>
		<p>
		  <label>Role</label>
		</p>
		<p>
		<table border="0" width="400">
			<tr>
				<td width="40%">
				<select style="height: 200px; WIDTH: 300px" multiple name="list1" size="10" ondblclick="moveOption(document.adminAddForm.list1, document.adminAddForm.list2)">
					<c:forEach var="src" items="${roles}">
						<option value="${src.id}">${src.roleName}</option>
					</c:forEach>
				</select>
				</td>
				<td width="20%" align="center">
					<input type="button" value="addAll" onclick="moveAllOption(document.adminAddForm.list1, document.adminAddForm.list2)">
					<br/><br />
					<input type="button" value="add" onclick="moveOption(document.adminAddForm.list1, document.adminAddForm.list2)">
					<br /><br />
					<input type="button" value="remove" onclick="moveOption(document.adminAddForm.list2, document.adminAddForm.list1)">
					<br /><br />
					<input type="button" value="removeAll" onclick="moveAllOption(document.adminAddForm.list2, document.adminAddForm.list1)">
				</td>
				<td width="40%">
				<select class="required" style="height: 200px; WIDTH: 300px" multiple name="list2" size="12" ondblclick="moveOption(document.adminAddForm.list2, document.adminAddForm.list1)">
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
	document.adminAddForm.roleIds.value = getvalue(document.adminAddForm.list2);
}

function moveOption(e1, e2) {
	var fromObjOptions = e1.options;
	for ( var i = 0; i < fromObjOptions.length; i++) {
		if (fromObjOptions[i].selected) {
			e2.appendChild(fromObjOptions[i]);
			i--;
		}
	}
	document.adminAddForm.roleIds.value = getvalue(document.adminAddForm.list2);
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