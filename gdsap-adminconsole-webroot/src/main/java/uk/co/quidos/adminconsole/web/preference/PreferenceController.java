package uk.co.quidos.adminconsole.web.preference;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


import uk.co.quidos.adminconsole.web.BaseController;
import uk.co.quidos.dal.BaseConditionVO;
import uk.co.quidos.gdsap.framework.common.Preference;
import uk.co.quidos.gdsap.framework.common.enums.ControlType;
import uk.co.quidos.gdsap.framework.common.enums.InputType;
import uk.co.quidos.gdsap.framework.common.enums.PreType;
import uk.co.quidos.gdsap.framework.common.enums.PreferenceRelId;
import uk.co.quidos.gdsap.framework.common.services.PreferenceServiceMgr;
import uk.co.quidos.gdsap.framework.exception.ObjectDuplicateException;
import uk.co.quidos.gdsap.framework.sys.lang.enums.EnumUtils;

@Controller
@RequestMapping("/preference/")
public class PreferenceController extends BaseController{

	@Autowired
	PreferenceServiceMgr preferenceServiceMgr;
	
	@RequestMapping("index")
	public String index(Model model, BaseConditionVO object)
	{
		List<Preference> listPreferences = preferenceServiceMgr.getPreferences(PreType.SystemTemplate, object.getStartIndex(), object.getPageSize());
		model.addAttribute("listPreferences",listPreferences);
		int totalCount = preferenceServiceMgr.getTotalPreferences(PreType.SystemTemplate);
		model.addAttribute("totalCount",totalCount);
		model.addAttribute("pageSize",object.getPageSize());
		
		return "/sysconf/template/index";
	}
	
	@RequestMapping("delete/{id}")
	public ModelAndView delete(@PathVariable("id") String id)
	{
		preferenceServiceMgr.deletePreference(id);
		return ajaxDoneSuccess("Success");
	}
	
	@RequestMapping("add")
	public String add(Model model)
	{
		PreferenceRelId[] ids = PreferenceRelId.values();
		model.addAttribute("ids", ids);
		PreType[] preTyps = PreType.values();
		model.addAttribute("preTyps", preTyps);
		InputType[] inputTypes = InputType.values();
		model.addAttribute("inputTypes", inputTypes);
		ControlType[] controlTypes = ControlType.values();
		model.addAttribute("controlTypes", controlTypes);
		return "/sysconf/template/add";
	}
	
	@RequestMapping("insert")
	public ModelAndView insert(@ModelAttribute("form")PreferenceForm form){
		Preference preference = new Preference();
		preference.setPreferenceRelId(form.getPreferenceRelId() != null ? PreferenceRelId.valueOf(form.getPreferenceRelId()) : null);
		preference.setContent(form.getContent());
		preference.setControlType(form.getControlType() != null ? (ControlType)EnumUtils.getByCode(form.getControlType(), ControlType.class) : null);
		preference.setPreType(form.getPreType() != null ? (PreType)EnumUtils.getByCode(form.getPreType(), PreType.class) : null);
		preference.setInputType(form.getInputType() != null ? (InputType)EnumUtils.getByCode(form.getInputType(), InputType.class) : null);
		preference.setTitle(form.getTitle());
		try {
			preferenceServiceMgr.addPreference(preference);
		} catch (ObjectDuplicateException e) {
			e.printStackTrace();
			return ajaxDoneError("Already exists");
		}
		return ajaxDoneSuccess("Success");
	}
	
	@RequestMapping("skipUpdate/{id}")
	public String skipUpdate(@PathVariable("id") String id , Model model)
	{
		PreferenceRelId preferenceRelId = PreferenceRelId.valueOf(id);
		Preference preference = preferenceServiceMgr.getPreference(preferenceRelId);
		model.addAttribute("preference", preference);
		PreferenceRelId[] ids = PreferenceRelId.values();
		model.addAttribute("ids", ids);
		PreType[] preTyps = PreType.values();
		model.addAttribute("preTyps", preTyps);
		InputType[] inputTypes = InputType.values();
		model.addAttribute("inputTypes", inputTypes);
		ControlType[] controlTypes = ControlType.values();
		model.addAttribute("controlTypes", controlTypes);
		return "/sysconf/template/edit";
	}
	
	@RequestMapping("update")
	public ModelAndView update(@RequestParam("id") String id, @ModelAttribute("form")PreferenceForm form)
	{
		Preference preference = preferenceServiceMgr.getPreference(PreferenceRelId.valueOf(id));
		preference.setPreferenceRelId(form.getPreferenceRelId() != null ? PreferenceRelId.valueOf(form.getPreferenceRelId()) : null);
		preference.setContent(form.getContent());
		preference.setControlType(form.getControlType() != null ? (ControlType)EnumUtils.getByCode(form.getControlType(), ControlType.class) : null);
		preference.setPreType(form.getPreType() != null ? (PreType)EnumUtils.getByCode(form.getPreType(), PreType.class) : null);
		preference.setTitle(form.getTitle());
		preference.setInputType(form.getInputType() != null ? (InputType)EnumUtils.getByCode(form.getInputType(), InputType.class) : null);
		preferenceServiceMgr.updatePreference(preference);
		return ajaxDoneSuccess("Success");
	}
}
