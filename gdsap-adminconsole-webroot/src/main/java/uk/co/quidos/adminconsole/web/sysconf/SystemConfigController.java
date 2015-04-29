package uk.co.quidos.adminconsole.web.sysconf;


import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.PathVariable;

import uk.co.quidos.adminconsole.web.BaseController;



@Controller
@RequestMapping("/systemConfig/")
public class SystemConfigController extends BaseController{
//	
//	@Autowired
//	ConfigurationServiceMgr configurationServiceMgr;
//	
//	@RequestMapping("index/{type}")
//	public String index(Model model,@PathVariable("type") String type)
//	{
//		Type t = (Type)EnumUtils.getByCode(type, Type.class);
//		Set<ConfigGroup> listg = configurationServiceMgr.getConfigGroupsByType(t);
//		model.addAttribute("type", t);
//		model.addAttribute("listg", listg);
//		if(type.equals("Template"))
//		{
//			return "/sysconf/template/index";
//		}
//		else
//		{
//			return "/sysconf/constant/index";
//		}
//	}
//	
//	@RequestMapping("systemconstant/getCons/{group}/{type}")
//	public String getCons(Model model,@PathVariable("group") String group,@PathVariable("type")String type)
//	{
//		Type t = (Type)EnumUtils.getByCode(type, Type.class);
//		ConfigGroup g = (ConfigGroup)EnumUtils.getByCode(group, ConfigGroup.class);
//		Map<ConfigGroup, List<SystemConfig>> mapCon = configurationServiceMgr.getSystemConfigsByGroup();
//		List<SystemConfig> listsys = mapCon.get(g);
//		model.addAttribute("type", t);
//		model.addAttribute("listsys", listsys);
//
//		if(type.equals("Template"))
//		{
//			return "/sysconf/template/edit";
//		}
//		else
//		{
//			return "/sysconf/constant/edit";
//		}
//	}
//	
//	@RequestMapping("update")
//	public ModelAndView update(@ModelAttribute("form") SystemConfigForm form)
//	{
//		String[] ids = form.getConfigurationRefId();
//		if(ids != null && ids.length > 0)
//		{
//			for(int i = 0 ; i < ids.length ; i++)
//			{
//				ConfigurationRefId sc = (ConfigurationRefId)EnumUtils.getByCode(ids[i], ConfigurationRefId.class);
//				SystemConfig s = configurationServiceMgr.getSystemConfig(sc);
//				s.setContent(form.getContent()[i]);
//				configurationServiceMgr.updateSystemConfig(s);
//			}
//		}
//		return ajaxDoneSuccess("Success");
//	}
//	
//	@RequestMapping("add")
//	public String add()
//	{
//		return null;
//	}
//	
//	@RequestMapping("insert")
//	public ModelAndView insert()
//	{
//		return ajaxDoneSuccess("Success");
//	}
//	
//	@RequestMapping("skipUpdate")
//	public String skipUpdate()
//	{
//		return null;
//	}
//	
}
