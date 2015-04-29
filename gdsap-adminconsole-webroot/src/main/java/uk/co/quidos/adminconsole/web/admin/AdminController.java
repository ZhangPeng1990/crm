package uk.co.quidos.adminconsole.web.admin;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import uk.co.quidos.adminconsole.web.BaseController;
import uk.co.quidos.common.context.AppContextHolder;
import uk.co.quidos.common.util.RandomStringUtil;
import uk.co.quidos.gdsap.framework.authority.Admin;
import uk.co.quidos.gdsap.framework.authority.Role;
import uk.co.quidos.gdsap.framework.authority.services.ACLServiceMgr;
import uk.co.quidos.gdsap.framework.authority.services.AdminServiceMgr;
import uk.co.quidos.gdsap.framework.authority.services.PageTagServiceMgr;
import uk.co.quidos.gdsap.framework.authority.services.RoleServiceMgr;
import uk.co.quidos.gdsap.framework.authority.services.object.QueryAdminObject;
import uk.co.quidos.gdsap.framework.exception.ObjectDuplicateException;
import uk.co.quidos.gdsap.framework.sys.business.BusinessFactory;
import uk.co.quidos.gdsap.framework.user.enums.AccessLevel;
import uk.co.quidos.gdsap.framework.user.enums.UserStatus;

@Controller
@RequestMapping("/admin/")
public class AdminController extends BaseController{

	@Autowired
	RoleServiceMgr roleServiceMgr;
	@Autowired
	PageTagServiceMgr pageTagServiceMgr;
	@Autowired
	ACLServiceMgr aclServiceMgr;
	@Autowired
	AdminServiceMgr adminMgr;
	
	@RequestMapping("/index")
	public String index(Model model) {
		Set<Role> setRole = roleServiceMgr.getRoles();
		model.addAttribute("setRole", setRole);
		return "/admin/index";
	}
	
	@RequestMapping("/listByRole")
	public String listByRole(QueryAdminObject object, Model model) {
		model.addAttribute("pageSize",object.getPageSize());
		model.addAttribute("object",object);
		return "/admin/listIndex";
	}
	
	@RequestMapping("/listByRole/{roleId}")
	public String listByRole(@PathVariable("roleId") String roleId, QueryAdminObject object, Model model) {
		
		if(roleId != null && !roleId.equals("") && !roleId.equals("''"))
		{
			Role role = roleServiceMgr.getRole(roleId);
			List<Admin> list = adminMgr.getAdmins(role, object);
			int totalCount = adminMgr.getTotalAdmins(role,object);
			model.addAttribute("listAdmin", list);
			model.addAttribute("totalCount",totalCount);
		}
		else
		{
			List<Admin> list = adminMgr.getAdmins(null, object);
			int totalCount = adminMgr.getTotalAdmins(null,object);
			model.addAttribute("listAdmin", list);
			model.addAttribute("totalCount",totalCount);
		}
		
		model.addAttribute("roleId", roleId);
		model.addAttribute("pageSize",object.getPageSize());
		model.addAttribute("object",object);
		return "/admin/listIndex";
	}

	@RequestMapping("/add")
	public String add(Model model) {
		
		//获取全部Role
		UserStatus[] userStatus = UserStatus.values();
		model.addAttribute("userStatus",userStatus);
		AccessLevel[] accessLecels = AccessLevel.values();
		model.addAttribute("accessLecels",accessLecels);
		Set<Role> roles = roleServiceMgr.getRoles();
		model.addAttribute("roles",roles);
		return "/admin/add";
	}

	@RequestMapping("/insert")
	public ModelAndView insert(@ModelAttribute("form")AdminForm form) {
		Admin admin = form.getAdmin();
		
		admin.setUserPwd(RandomStringUtil.getString(8));
		String roleIds = form.getRoleIds();
		String roleId[] = roleIds.split(",");
		if(roleId != null && roleId.length > 0)
		{
			Set<Role> setRole = new LinkedHashSet<Role>();
			for(String id : roleId)
			{
				Role role = roleServiceMgr.getRole(id);
				setRole.add(role);
			}
			admin.setRoles(setRole);
		}
		
		try {
			adminMgr.addAdmin(admin);
		} catch (ObjectDuplicateException e) {
			e.printStackTrace();
			return ajaxDoneError("exits");
		}
		return ajaxDoneSuccess("Success");
	}

	@RequestMapping("/edit/{id}")
	public String edit(@PathVariable("id") int id, Model model) {
		UserStatus[] userStatus = UserStatus.values();
		model.addAttribute("userStatus",userStatus);
		AccessLevel[] accessLecels = AccessLevel.values();
		model.addAttribute("accessLecels",accessLecels);
		Admin admin = adminMgr.getAdmin(id);
		Set<Role> setRole = admin.getRoles();
		Set<Role> roles = roleServiceMgr.getRoles();//roles全部Role
		String roleIds = "";
		for(Role hasRole : setRole)
		{
			roleIds += hasRole.getId() + ",";
			if(roles.contains(hasRole))
			{
				roles.remove(hasRole);
			}
		}
		
		model.addAttribute("roleIds",roleIds);
		model.addAttribute("allRoles",roles);
		model.addAttribute("admin", admin);
		return "/admin/edit";
	}

	@RequestMapping("/update")
	public ModelAndView update(@ModelAttribute("form")AdminForm form) {
		Admin admin = adminMgr.getAdmin(form.getAdmin().getId());
		
		admin.setUserName(form.getAdmin().getUserName());
		admin.setUserPwd(form.getAdmin().getUserPwd());
		admin.setUserStatus(form.getAdmin().getUserStatus());
		admin.setFirstName(form.getAdmin().getFirstName());
		admin.setSurName(form.getAdmin().getSurName());
		admin.setEmail(form.getAdmin().getEmail());
		admin.setRegisterCode(form.getAdmin().getRegisterCode());
		admin.setAccessLevel(form.getAdmin().getAccessLevel());
		admin.setOrganisation(form.getAdmin().getOrganisation());
		admin.setOrganisationWebSite(form.getAdmin().getOrganisationWebSite());
		admin.setOrganisationCertificationNumber(form.getAdmin().getOrganisationCertificationNumber());
		admin.setAddress1(form.getAdmin().getAddress1());
		admin.setAddress2(form.getAdmin().getAddress2());
		admin.setAddress3(form.getAdmin().getAddress3());
		admin.setPostcode(form.getAdmin().getPostcode());
		admin.setPosttown(form.getAdmin().getPosttown());
		admin.setWebsite(form.getAdmin().getWebsite());
		admin.setFax(form.getAdmin().getFax());
		admin.setTel(form.getAdmin().getTel());
		admin.setCompanyName(form.getAdmin().getCompanyName());
		
		String roleIds = form.getRoleIds();
		String roleId[] = roleIds.split(",");
		if(roleId != null && roleId.length > 0)
		{
			Set<Role> setRole = new LinkedHashSet<Role>();
			for(String id : roleId)
			{
				Role role = roleServiceMgr.getRole(id);
				setRole.add(role);
			}
			admin.setRoles(setRole);
		}
		adminMgr.updateAdmin(admin);
		return ajaxDoneSuccess("Success");
	}
	
	@RequestMapping("/changeProfile")
	public String changeProfile(ModelMap model) {
		return "/myaccount/profile";
	}

	@RequestMapping("/updateProfile")
	public ModelAndView updateProfile(@ModelAttribute("form") AdminForm form) {
		Admin admin = adminMgr.getAdmin(form.getAdmin().getId());
		admin.setFirstName(form.getAdmin().getFirstName());
		admin.setSurName(form.getAdmin().getSurName());
		admin.setEmail(form.getAdmin().getEmail());
		admin.setAddress1(form.getAdmin().getAddress1());
		admin.setAddress2(form.getAdmin().getAddress2());
		admin.setAddress3(form.getAdmin().getAddress3());
		admin.setPostcode(form.getAdmin().getPostcode());
		admin.setPosttown(form.getAdmin().getPosttown());
		admin.setWebsite(form.getAdmin().getWebsite());
		admin.setFax(form.getAdmin().getFax());
		admin.setTel(form.getAdmin().getTel());
		admin.setCompanyName(form.getAdmin().getCompanyName());
		adminMgr.updateAdminBaseInfo(admin);
		return ajaxDoneSuccess(getMessage("msg.operation.success"));
	}
	
	@RequestMapping("/changePwd")
	public String changePwd(ModelMap model) {

		return "/myaccount/change-password";
	}

	@RequestMapping("/updatePassword")
	public ModelAndView updatePassword(@RequestParam("oldPassword") String oldPassword,@RequestParam("password") String password) {
		AdminServiceMgr userMgr = BusinessFactory.getInstance().getService(AdminServiceMgr.SERVICE_NAME);
		Admin user = AppContextHolder.getContext().getUser();

		if (oldPassword == null || !oldPassword.equals(user.getUserPwd())) {
			return ajaxDoneError(getMessage("msg.oldpassword.invalid"));
		}
		Admin admin = adminMgr.getAdmin(user.getId());
		admin.setUserPwd(password);
		userMgr.updateAdmin(admin);
		return ajaxDoneSuccess(getMessage("msg.operation.success"));
	}
}
