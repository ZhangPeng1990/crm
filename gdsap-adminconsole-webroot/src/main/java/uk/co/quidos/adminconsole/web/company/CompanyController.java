package uk.co.quidos.adminconsole.web.company;



import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import uk.co.quidos.adminconsole.web.BaseController;
import uk.co.quidos.gdsap.evaluation.Company;
import uk.co.quidos.gdsap.evaluation.services.CompanyServiceMgr;
import uk.co.quidos.gdsap.evaluation.services.object.ConditionVO;
import uk.co.quidos.gdsap.framework.exception.ObjectDuplicateException;

@Controller
@RequestMapping("/company/")
public class CompanyController extends BaseController {
	
	@Autowired
	CompanyServiceMgr companyServiceMgr;
	
	@RequestMapping("skipAdd")
	public String skipAdd(){
		return "/company/add";
	}
	
    @RequestMapping("insert")
    public ModelAndView insert(@ModelAttribute("form")Company company){
    	
    	try {
			companyServiceMgr.addCompany(company);
		} catch (ObjectDuplicateException e) {
			e.printStackTrace();
			String message = this.getMessage("msg.companyname.unique");
			return ajaxDoneError(message);
		}
    	return ajaxDoneSuccess("Success");
    }
    
    @RequestMapping("listCompany")
    public String listCompany(HttpServletRequest request,ConditionVO object,Model model){
    	
    	List<Company> companys = this.companyServiceMgr.getCompanysByConditionVO(object, object.getStartIndex(), object.getPageSize());
		int total = this.companyServiceMgr.countAll(object);
		model.addAttribute("totalCount", total);
		model.addAttribute("companys",companys);
		model.addAttribute("pageSize",object.getPageSize());
		model.addAttribute("object",object);
    	return "/company/list";
    }
    
    @RequestMapping("skipUpdate/{id}")
    public String skipUpdate(@PathVariable("id") String id,Model model){
    	Company company = this.companyServiceMgr.getCompany(id); 	
    	model.addAttribute("company",company);
    	return "/company/edit";
    }
    
    @RequestMapping("update")
    public ModelAndView update(@ModelAttribute("form")Company company){
    	Company c = this.companyServiceMgr.getCompany(company.getCompanyId());
    	c.setName(company.getName());
    	c.setEmail(company.getEmail());
    	c.setAddress1(company.getAddress1());
    	c.setAddress2(company.getAddress2());
    	c.setAddress3(company.getAddress2());
    	c.setPostcode(company.getPostcode());
    	c.setPosttown(company.getPosttown());
    	c.setWebsite(company.getWebsite());
    	c.setFax(company.getFax());
    	c.setTel(company.getTel());
    	this.companyServiceMgr.updateCompanyInfo(c);
    	return ajaxDoneSuccess("Success");
    }
}
