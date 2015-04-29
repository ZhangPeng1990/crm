package uk.co.quidos.adminconsole.web;

import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import uk.co.quidos.gdsap.framework.authority.Admin;
import uk.co.quidos.gdsap.framework.authority.PageTag;
import uk.co.quidos.gdsap.framework.authority.enums.PagetagGroup;
import uk.co.quidos.gdsap.framework.authority.services.AdminServiceMgr;

/**
 * @author peng.shi
 */
@Controller
public class IndexAction
{
	@Autowired
	AdminServiceMgr adminServiceMgr;
	
//	@RequestMapping("/index")
//	public String execute()
//	{
////		BaseUser user = (BaseUser) request.getSession().getAttribute(Constants.AUTHENTICATION_KEY);
////		Admin admin = adminServiceMgr.getAdmin(user.getId());
////		Map<PagetagGroup, Set<PageTag>> pageTagsMap = admin.getPageTagsMap();
////		model.addAttribute("pageTagsMap",pageTagsMap);
//		return "index";
//	}
	
	@RequestMapping("")
	public String execute(Model model,HttpServletRequest request)
	{
		Admin admin = (Admin) request.getSession().getAttribute(Constants.AUTHENTICATION_KEY);
		admin = adminServiceMgr.getAdmin(admin.getId());
		Map<PagetagGroup, Set<PageTag>> pageTagsMap = admin.getPageTagsMap();
		model.addAttribute("pageTagsMap",pageTagsMap);
		return "index";
	}
}
