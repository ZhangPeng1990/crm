<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/include.inc.jsp"%>

<h2 class="contentTitle">Add new Role</h2>
<c:url var="formUrl" value="/role/insert"/>
<form id="pageForm" name="roleAddForm" method="post" action="${formUrl}" class="required-validate pageForm" onsubmit="return validateCallback(this, navTabAjaxDone);">
<input type="hidden" name="pageTags" >
<input type="hidden" name="acls" >
	<div class="pageFormContent" layouth="98" style="height: 362px; overflow: auto;">
	    <p>
			<label>ID</label>
			<input class="required" type="text" size="30" name="role.id">
		</p>
		<p>
			<label>RoleName</label>
			<input class="required" type="text" size="30" name="role.roleName">
		</p>
		<p>
			<label>Des</label>
			<input class="required" type="text" size="30" name="role.des">
		</p>
		<p>
		</p>
		<p>
			<label>PageTags</label>
		</p>
		<p>
		<table border="0" width="400">
			<tr>
				<td width="40%">
				<select style="height: 200px; WIDTH: 300px" multiple name="list1" size="10" ondblclick="moveOption(document.roleAddForm.list1, document.roleAddForm.list2)">
					<c:forEach var="src" items="${allPsgeTags}">
						<option value="${src.id}">${src.title}</option>
					</c:forEach>
				</select>
				</td>
				<td width="20%" align="center">
					<input type="button" value="addAll" onclick="moveAllOption(document.roleAddForm.list1, document.roleAddForm.list2)">
					<br/><br />
					<input type="button" value="add" onclick="moveOption(document.roleAddForm.list1, document.roleAddForm.list2)">
					<br /><br />
					<input type="button" value="remove" onclick="moveOption(document.roleAddForm.list2, document.roleAddForm.list1)">
					<br /><br />
					<input type="button" value="removeAll" onclick="moveAllOption(document.roleAddForm.list2, document.roleAddForm.list1)">
				</td>
				<td width="40%">
				<select style="height: 200px; WIDTH: 300px" multiple name="list2" size="12" ondblclick="moveOption(document.roleAddForm.list2, document.roleAddForm.list1)">
				</select>
				</td>

			</tr>
		</table>
		</p>
		<p>
		</p>
		<p>
		<label>ACL</label>
		</p>
		<p>
		<table border="0" width="400">
			<tr>
				<td width="40%">
				<select style="height: 200px; WIDTH: 300px" multiple name="list3" size="10" ondblclick="moveOption1(document.roleAddForm.list3, document.roleAddForm.list4)">
					<c:forEach var="src" items="${allAcls}">
						<option value="${src.id}">${src.title}</option>
					</c:forEach>
				</select>
				</td>
				<td width="20%" align="center">
					<input type="button" value="addAll" onclick="moveAllOption1(document.roleAddForm.list3, document.roleAddForm.list4)">
					<br/><br />
					<input type="button" value="add" onclick="moveOption1(document.roleAddForm.list3, document.roleAddForm.list3)">
					<br /><br />
					<input type="button" value="remove" onclick="moveOption1(document.roleAddForm.list4, document.roleAddForm.list3)">
					<br /><br />
					<input type="button" value="removeAll" onclick="moveAllOption1(document.roleAddForm.list4, document.roleAddForm.list3)">
				</td>
				<td width="40%">
				<select style="height: 200px; WIDTH: 300px" multiple name="list4" size="12" ondblclick="moveOption1(document.roleAddForm.list4, document.roleAddForm.list3)">
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
		document.roleAddForm.pageTags.value = getvalue(document.roleAddForm.list2);
	}

	function moveOption(e1, e2) {
		var fromObjOptions = e1.options;
		for ( var i = 0; i < fromObjOptions.length; i++) {
			if (fromObjOptions[i].selected) {
				e2.appendChild(fromObjOptions[i]);
				i--;
			}
		}
		document.roleAddForm.pageTags.value = getvalue(document.roleAddForm.list2);
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
	//role
	function moveAllOption1(e1, e2) {
		var fromObjOptions = e1.options;
		for ( var i = 0; i < fromObjOptions.length; i++) {
			fromObjOptions[0].selected = true;
			e2.appendChild(fromObjOptions[i]);
			i--;
		}
		document.roleAddForm.acls.value = getvalue(document.roleAddForm.list4);
	}

	function moveOption1(e1, e2) {
		var fromObjOptions = e1.options;
		for ( var i = 0; i < fromObjOptions.length; i++) {
			if (fromObjOptions[i].selected) {
				e2.appendChild(fromObjOptions[i]);
				i--;
			}
		}
		document.roleAddForm.acls.value = getvalue(document.roleAddForm.list4);
	}

	function getvalue1(geto) {
		var allvalue = "";
		for ( var i = 0; i < geto.options.length; i++) {
			allvalue += geto.options[i].value + ",";
		}
		return allvalue;
	}

	function changepos1(obj, index) {
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