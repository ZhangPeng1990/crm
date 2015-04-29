package uk.co.quidos.adminconsole.web.user;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import uk.co.quidos.adminconsole.web.BaseController;
import uk.co.quidos.common.util.AuthUtils;
import uk.co.quidos.common.util.FileUtils;
import uk.co.quidos.common.util.RandomStringUtil;
import uk.co.quidos.gdsap.framework.enums.UserType;
import uk.co.quidos.gdsap.framework.exception.ObjectDuplicateException;
import uk.co.quidos.gdsap.framework.sys.lang.enums.EnumUtils;
import uk.co.quidos.gdsap.framework.sysconf.GlobalConfig;
import uk.co.quidos.gdsap.framework.user.User;
import uk.co.quidos.gdsap.framework.user.enums.AccessLevel;
import uk.co.quidos.gdsap.framework.user.enums.UserStatus;
import uk.co.quidos.gdsap.framework.user.services.UserServiceMgr;
import uk.co.quidos.gdsap.framework.user.services.object.ConditionVO;

@Controller
@RequestMapping("/user/")
public class UserController extends BaseController{

	@Autowired
	UserServiceMgr userServiceMgr;
	
	@RequestMapping("index")
	public String index(HttpServletRequest request, Model model, ConditionVO object)
	{
		List<User> users = userServiceMgr.getUsers(object, object.getStartIndex(), object.getPageSize());
		int totalCount = userServiceMgr.getTotalUsers(object);
		model.addAttribute("users",users);
		model.addAttribute("totalCount",totalCount);
		model.addAttribute("pageSize",object.getPageSize());
		model.addAttribute("object",object);
		return "/user/index";
	}
	
	@RequestMapping("skipAdd")
	public String skipAdd(Model model)
	{
		UserStatus[] userStatus = UserStatus.values();
		model.addAttribute("userStatus",userStatus);
		AccessLevel[] accessLecels = AccessLevel.values();
		model.addAttribute("accessLecels",accessLecels);
		UserType[] userTypes = UserType.values();
		model.addAttribute("userTypes",userTypes);
		return "/user/add";
	}
	
	/**
	 * 下载Excel
	 * @param list
	 * @return
	 */
	@RequestMapping("download")
	public String exportExcel(ConditionVO object,HttpServletResponse response, HttpServletRequest request)
	{
		int totalCount = userServiceMgr.getTotalUsers(object);
		int pageSize = totalCount;
		if(totalCount > 0)
		{
			int excelPageSize = GlobalConfig.getInstance().getExcelPageSize();
			if(totalCount >= excelPageSize)
			{
				Map<Integer, List<User>> mapUser = new HashMap<Integer, List<User>>();
				List<User> tempUsers = new ArrayList<User>();
				int surplus = totalCount % excelPageSize;
				int time = (totalCount - surplus) / excelPageSize;
				pageSize = excelPageSize;
				for(int i = 0 ; i < time ; i++)
				{
					tempUsers = userServiceMgr.getUsers(object, i * excelPageSize, pageSize);
					mapUser.put(i, tempUsers);
					if(i == time - 1)
					{
						if(surplus > 0)
						{
							tempUsers = userServiceMgr.getUsers(object, (i + 1) * excelPageSize, pageSize);
							mapUser.put(i + 1, tempUsers);
						}
					}
				}
				createExcel(mapUser, request);
			}
			else
			{
				List<User> excelUsers = new ArrayList<User>();
				excelUsers = userServiceMgr.getUsers(object, 0, pageSize);
				createExcel(excelUsers, request);
			}
		}
		
		HttpSession session = request.getSession();
		String savePath = GlobalConfig.getInstance().getFSDir() + File.separator + "temp";
		String path = savePath + File.separator + session.getId() + "Users.xls";
		
		byte[] buffer = new byte[1024];
		int length = 0;
		InputStream is = null;
		response.reset();
		OutputStream os = null;
		try
		{
			response.reset();
			response.addHeader("Content-Disposition", "attachment;filename=" + "Users.xls");
			response.setContentType("application/octet-stream");
			os = response.getOutputStream();
			is = new FileInputStream(new File(path));
			while ((length = is.read(buffer, 0, buffer.length)) != -1)
			{
				os.write(buffer, 0, length);
			}
			os.flush();
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		} finally 
		{
			if (is != null)
			{
				try
				{
					is.close();
				} catch (IOException e1)
				{
					e1.printStackTrace();
					throw new RuntimeException(e1);
				}finally
				{
					FileUtils.deleteFile(GlobalConfig.getInstance().getFSDir() + File.separator + "temp", session.getId() + "Users.xls");
				}
			}
			if (os != null)
			{
				try
				{
					os.close();
				} catch (IOException e2)
				{
					e2.printStackTrace();
					throw new RuntimeException(e2);
				}finally
				{
					FileUtils.deleteFile(GlobalConfig.getInstance().getFSDir() + File.separator + "temp", session.getId() + "Users.xls");
				}
			}
		}
		return null;
	}
	
	enum ExportFileds{
		USERNAME("User Name"),ASSESSOR_ID("Assessor ID"),ORG_CERT_NUMBER("Org.Cert Number"),
		EMAIL("Email"),INSERT_DATE("Insert Date"),ACCESSLEVEL("Access Level"),
		USERSTATUS("User Status"), ASSESSOR_NAME("Assessor Name"),QUIDOS_ID("Quidos ID"),
		INSURER("Insurer"),POLICY_NO("Policy No"),EFFECTIVE_DATECHOOSE("Effective DateChoose"),
		EXPIRY_DATECHOOSE("Expiry DateChoose"),PI_LIMIT("Pi Limit");
		
		private ExportFileds(String desc){
			this.desc = desc;
		}
		
		private String desc;

		public String getDesc() {
			return desc;
		}
		
	}
	
	/**
	 * 生成Excel
	 */
	private void createExcel(Map<Integer,List<User>> map, HttpServletRequest request)
	{
		String path = "";
		String savePath = GlobalConfig.getInstance().getFSDir() + File.separator + "temp";
		if (!new File(savePath).exists())
		{
			new File(savePath).mkdirs();
		}
		HttpSession session = request.getSession();
		path = savePath + File.separator + session.getId() + "Users.xls";

		HSSFWorkbook workbook = new HSSFWorkbook();
		
		Set<Integer> key = map.keySet();
        for (Iterator<Integer> it = key.iterator(); it.hasNext();)
        {
            Integer s = it.next();
            List<User> list = map.get(s);
    		// 设置单元格内容格式
    	    HSSFCellStyle style1 = workbook.createCellStyle();
    	    style1.setDataFormat(HSSFDataFormat.getBuiltinFormat("m/d/yy"));
    		
    		HSSFSheet sheet = workbook.createSheet("sheet" + s);
    		
    		HSSFRow row;
    		row = sheet.createRow(0);
    		for(ExportFileds exportFileds : ExportFileds.values())
    		{
    			HSSFCell cell = row.createCell(exportFileds.ordinal());
    			switch(exportFileds)
    			{
    				case USERNAME:
    				cell.setCellValue(exportFileds.getDesc());
    				break;
    				
    				case ASSESSOR_ID:
    				cell.setCellValue(exportFileds.getDesc());
    				break;
    				
    				case ORG_CERT_NUMBER:
    				cell.setCellValue(exportFileds.getDesc());
    				break;
    				
    				case EMAIL:
    				cell.setCellValue(exportFileds.getDesc());
    				break;
    				
    				case INSERT_DATE:
    				cell.setCellValue(exportFileds.getDesc());
    				break;
    				
    				case ACCESSLEVEL:
    				cell.setCellValue(exportFileds.getDesc());
    				break;
    				
    				case USERSTATUS:
    				cell.setCellValue(exportFileds.getDesc());
    				break;
    				
    				case ASSESSOR_NAME:
        			cell.setCellValue(exportFileds.getDesc());
        			break;
        			
    				case QUIDOS_ID:
        			cell.setCellValue(exportFileds.getDesc());
        			break;
        			
    				case INSURER:
        			cell.setCellValue(exportFileds.getDesc());
        			break;
        				
    				case POLICY_NO:
        			cell.setCellValue(exportFileds.getDesc());
        			break;
        				
    				case EFFECTIVE_DATECHOOSE:
       				cell.setCellValue(exportFileds.getDesc());
       				break;
        				
    				case EXPIRY_DATECHOOSE:
       				cell.setCellValue(exportFileds.getDesc());
       				break;
        				
    				case PI_LIMIT:
       				cell.setCellValue(exportFileds.getDesc());
       				break;
    			}
    			
    		}
    		
    		for(int a = 0 ; a < list.size() ; a++)
    		{
    			row = sheet.createRow(a + 1);
    			for(ExportFileds exportFileds : ExportFileds.values())
    			{
    				HSSFCell cell = row.createCell(exportFileds.ordinal());
    				switch(exportFileds)
    				{
    					case USERNAME:
    					cell.setCellValue(list.get(a).getUserName());
    					break;
    					
    					case ASSESSOR_ID:
    					cell.setCellValue(list.get(a).getAssessorID());
    					break;
    					
    					case ORG_CERT_NUMBER:
    					cell.setCellValue(list.get(a).getOrganisationCertificationNumber());
						break;
    					
    					case EMAIL:
    					cell.setCellValue(list.get(a).getEmail());
    					break;
    					
    					case INSERT_DATE:
    					cell.setCellStyle(style1);
    					cell.setCellValue(list.get(a).getInsertTime());
    					break;
    					
    					case ACCESSLEVEL:
    					cell.setCellValue(list.get(a).getAccessLevel() != null ? list.get(a).getAccessLevel().getDesc() : "");
    					break;
    					
    					case USERSTATUS:
    					cell.setCellValue(list.get(a).getUserStatus() != null ? list.get(a).getUserStatus().getDesc() : "");
    					break;
    					
    					case ASSESSOR_NAME:
    	        		cell.setCellValue(list.get(a).getFirstName()+","+list.get(a).getSurName());
    	        		break;
    	        		
    	    			case QUIDOS_ID:
    	        		cell.setCellValue(list.get(a).getAssessorID());
    	        		break;
    	        			
    	    			case INSURER:
    	       			cell.setCellValue(list.get(a).getInsurer());
    	       			break;
    	        				
    	   				case POLICY_NO:
    	       			cell.setCellValue(list.get(a).getPolicyNo());
            			break;
    	        				
   	    				case EFFECTIVE_DATECHOOSE:
   	    				cell.setCellStyle(style1);
   	    				if(!"".equals(list.get(a).getEffectiveDate()) && list.get(a).getEffectiveDate() !=null){
   	    					cell.setCellValue(list.get(a).getEffectiveDate());
   	    				}else{
   	    					cell.setCellValue("");
   	    				}
   	       				break;
    	        				
    	    			case EXPIRY_DATECHOOSE:
    	    			cell.setCellStyle(style1);	
    	    			if(!"".equals(list.get(a).getExpiryDate()) && list.get(a).getExpiryDate() !=null){
   	    					cell.setCellValue(list.get(a).getExpiryDate());
   	    				}else{
   	    					cell.setCellValue("");
   	    				}
   	       				break;
    	        				
   	    				case PI_LIMIT:
   	       				cell.setCellValue(list.get(a).getPiLimit());
   	       				break;
    				}
    				
    			}
    		}
        }
		
		
	    
		FileOutputStream fOut;
		try {
			fOut = new FileOutputStream(path);
			workbook.write(fOut);
			fOut.flush();
			fOut.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 生成Excel
	 */
	private void createExcel(List<User> list, HttpServletRequest request)
	{
		Assert.notEmpty(list, "noneItems");
		String path = "";
		String savePath = GlobalConfig.getInstance().getFSDir() + File.separator + "temp";
		if (!new File(savePath).exists())
		{
			new File(savePath).mkdirs();
		}
		HttpSession session = request.getSession();
		path = savePath + File.separator + session.getId() + "Users.xls";
		
		HSSFWorkbook workbook = new HSSFWorkbook();
		
		// 设置单元格内容格式
	    HSSFCellStyle style1 = workbook.createCellStyle();
	    style1.setDataFormat(HSSFDataFormat.getBuiltinFormat("m/d/yy"));
		
		HSSFSheet sheet = workbook.createSheet();
		
		HSSFRow row;
		row = sheet.createRow(0);
		for(ExportFileds exportFileds : ExportFileds.values())
		{
			HSSFCell cell = row.createCell(exportFileds.ordinal());
			switch(exportFileds)
			{
				case USERNAME:
				cell.setCellValue(exportFileds.getDesc());
				break;
				
				case ASSESSOR_ID:
				cell.setCellValue(exportFileds.getDesc());
				break;
				
				case ORG_CERT_NUMBER:
				cell.setCellValue(exportFileds.getDesc());
				break;
				
				case EMAIL:
				cell.setCellValue(exportFileds.getDesc());
				break;
				
				case INSERT_DATE:
				cell.setCellValue(exportFileds.getDesc());
				break;
				
				case ACCESSLEVEL:
				cell.setCellValue(exportFileds.getDesc());
				break;
				
				case USERSTATUS:
				cell.setCellValue(exportFileds.getDesc());
				break;
				
				case ASSESSOR_NAME:
    			cell.setCellValue(exportFileds.getDesc());
    			break;
    			
				case QUIDOS_ID:
    			cell.setCellValue(exportFileds.getDesc());
    			break;
    			
				case INSURER:
    			cell.setCellValue(exportFileds.getDesc());
    			break;
    				
				case POLICY_NO:
    			cell.setCellValue(exportFileds.getDesc());
    			break;
    				
				case EFFECTIVE_DATECHOOSE:
   				cell.setCellValue(exportFileds.getDesc());
   				break;
    				
				case EXPIRY_DATECHOOSE:
   				cell.setCellValue(exportFileds.getDesc());
   				break;
    				
				case PI_LIMIT:
   				cell.setCellValue(exportFileds.getDesc());
   				break;	
			}
			
		}
		
		for(int a = 0 ; a < list.size() ; a++)
		{
			row = sheet.createRow(a + 1);
			for(ExportFileds exportFileds : ExportFileds.values())
			{
				HSSFCell cell = row.createCell(exportFileds.ordinal());
				switch(exportFileds)
				{
				case USERNAME:
					cell.setCellValue(list.get(a).getUserName());
					break;
					
					case ASSESSOR_ID:
					cell.setCellValue(list.get(a).getAssessorID());
					break;
					
					case ORG_CERT_NUMBER:
					cell.setCellValue(list.get(a).getOrganisationCertificationNumber());
					break;
					
					case EMAIL:
					cell.setCellValue(list.get(a).getEmail());
					break;
					
					case INSERT_DATE:
					cell.setCellStyle(style1);
					cell.setCellValue(list.get(a).getInsertTime());
					break;
					
					case ACCESSLEVEL:
					cell.setCellValue(list.get(a).getAccessLevel() != null ? list.get(a).getAccessLevel().getDesc() : "");
					break;
					
					case USERSTATUS:
					cell.setCellValue(list.get(a).getUserStatus() != null ? list.get(a).getUserStatus().getDesc() : "");
					break;
					
					case ASSESSOR_NAME:
	        		cell.setCellValue(list.get(a).getFirstName()+","+list.get(a).getSurName());
	        		break;
	        		
	    			case QUIDOS_ID:
	        		cell.setCellValue(list.get(a).getAssessorID());
	        		break;
	        			
	    			case INSURER:
	       			cell.setCellValue(list.get(a).getInsurer());
	       			break;
	        				
	   				case POLICY_NO:
	       			cell.setCellValue(list.get(a).getPolicyNo());
        			break;
	        				
	    			case EFFECTIVE_DATECHOOSE:
	    				cell.setCellStyle(style1);
	    				if(!"".equals(list.get(a).getEffectiveDate()) && list.get(a).getEffectiveDate() !=null){
	    					cell.setCellValue(list.get(a).getEffectiveDate());
	    				}else{
	    					cell.setCellValue("");
	    				}
	       				break;
	        				
	    			case EXPIRY_DATECHOOSE:
	    			cell.setCellStyle(style1);	
	    			if(!"".equals(list.get(a).getExpiryDate()) && list.get(a).getExpiryDate() !=null){
	    					cell.setCellValue(list.get(a).getExpiryDate());
	    				}else{
	    					cell.setCellValue("");
	    				}
	       				break;
	        				
	    		  case PI_LIMIT:
	    			  if(!"".equals(list.get(a).getPiLimit()) && list.get(a).getPiLimit() != null){
	    				  cell.setCellValue(list.get(a).getPiLimit());
	    			  }else{
	    				  cell.setCellValue("");
	    			  }
	       				break;
				}
				
			}
		}
	    
		FileOutputStream fOut;
		try {
			fOut = new FileOutputStream(path);
			workbook.write(fOut);
			fOut.flush();
			fOut.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping("insert")
	public ModelAndView insert(@ModelAttribute("form")UserForm user) 
	{
		try {
			User u = new User();
			u.setUserName(user.getUserName());
			u.setAssessorID(user.getAssessorID());
			u.setUserPwd(RandomStringUtil.getString(8));
			u.setUserStatus((UserStatus)EnumUtils.getByCode(user.getUserStatus(), UserStatus.class));
			u.setUserType((UserType)EnumUtils.getByCode(user.getUserType(), UserType.class));
			u.setFirstName(user.getFirstName());
			u.setRegisterCode(user.getRegisterCode());
			u.setSurName(user.getSurName());
			u.setEmail(user.getEmail());
			u.setAccessLevel((AccessLevel)EnumUtils.getByCode(user.getAccessLevel(), AccessLevel.class));
			u.setOrganisation(user.getOrganisation());
			u.setOrganisationWebSite(user.getOrganisationWebSite());
			u.setOrganisationCertificationNumber(user.getOrganisationCertificationNumber());
			u.setAddress1(user.getAddress1());
			u.setAddress2(user.getAddress2());
			u.setAddress3(user.getAddress3());
			u.setPostcode(user.getPostcode());
			u.setPosttown(user.getPosttown());
			u.setWebsite(user.getWebsite());
			u.setFax(user.getFax());
			u.setTel(user.getTel());
			u.setCompanyName(user.getCompanyName());
			u.setAuthUsername(user.getAuthUsername());
			u.setAuthPassword(user.getAuthPassword());
			u.setInsurer(user.getInsurer());
			u.setPolicyNo(user.getPolicyNo());
			u.setEffectiveDate(user.getEffectiveDate());
			u.setExpiryDate(user.getExpiryDate());
			u.setPiLimit(user.getPiLimit());
			u.setSctAuthorizationUsername(user.getSctAuthorizationUsername());
			u.setSctAuthorizationPassword(user.getSctAuthorizationPassword());
			u.setSctAssessorOrganisationid(user.getSctAssessorOrganisationid());
			u.setSctAdviserId(user.getSctAdviserId());
			userServiceMgr.addUser(u);
		} catch (ObjectDuplicateException e) {
			e.printStackTrace();
			String message = this.getMessage("msg.username.unique");
			return ajaxDoneError(message);
		}
		return ajaxDoneSuccess("Success");
	}
	
	@RequestMapping("skipUpdate/{id}")
	public String skipUpdate(@PathVariable("id") Long id, Model model)
	{
		User user = userServiceMgr.getUser(id);
		model.addAttribute("user",user);
		UserStatus[] userStatus = UserStatus.values();
		model.addAttribute("userStatus",userStatus);
		AccessLevel[] accessLecels = AccessLevel.values();
		model.addAttribute("accessLecels",accessLecels);
		UserType[] userTypes = UserType.values();
		model.addAttribute("userTypes",userTypes);
		
		String qubeLoginHost = GlobalConfig.getInstance().getHost();
		qubeLoginHost += AuthUtils.getAuthId(user.getUserName(), user.getUserPwd());
		model.addAttribute("qubeLoginHost",qubeLoginHost);
		return "/user/edit";
	}
	
	@RequestMapping("skipUpdateUserName/{id}")
	public String skipUpdateUserName(@PathVariable("id") Long id, Model model)
	{
		User user = userServiceMgr.getUser(id);
		model.addAttribute("user",user);
		return "/user/editUserName";
	}
	
	@RequestMapping("update")
	public ModelAndView update(@ModelAttribute("form")UserForm user)
	{
		User u = userServiceMgr.getUser(user.getId());
		if(!u.getUserName().equals(user.getUserName()))
		{
			try {
				u.setUserName(user.getUserName());
				userServiceMgr.updateUserName(u);
			} catch (ObjectDuplicateException e) {
				e.printStackTrace();
				return ajaxDoneError("User name is already in use");
			}
		}
		u.setAssessorID(user.getAssessorID());
		u.setUserType((UserType)EnumUtils.getByCode(user.getUserType(), UserType.class));
		u.setUserStatus((UserStatus)EnumUtils.getByCode(user.getUserStatus(), UserStatus.class));
		u.setFirstName(user.getFirstName());
		u.setSurName(user.getSurName());
		u.setEmail(user.getEmail());
		u.setAccessLevel((AccessLevel)EnumUtils.getByCode(user.getAccessLevel(), AccessLevel.class));
		u.setOrganisation(user.getOrganisation());
		u.setOrganisationWebSite(user.getOrganisationWebSite());
		u.setOrganisationCertificationNumber(user.getOrganisationCertificationNumber());
		u.setAddress1(user.getAddress1());
		u.setAddress2(user.getAddress2());
		u.setAddress3(user.getAddress3());
		u.setPostcode(user.getPostcode());
		u.setPosttown(user.getPosttown());
		u.setWebsite(user.getWebsite());
		u.setFax(user.getFax());
		u.setTel(user.getTel());
		u.setCompanyName(user.getCompanyName());
		u.setAuthUsername(user.getAuthUsername());
		u.setAuthPassword(user.getAuthPassword());
		u.setInsurer(user.getInsurer());
		u.setPolicyNo(user.getPolicyNo());
		u.setEffectiveDate(user.getEffectiveDate());
		u.setExpiryDate(user.getExpiryDate());
		u.setPiLimit(user.getPiLimit());
		u.setSctAuthorizationUsername(user.getSctAuthorizationUsername());
		u.setSctAuthorizationPassword(user.getSctAuthorizationPassword());
		u.setSctAssessorOrganisationid(user.getSctAssessorOrganisationid());
		u.setSctAdviserId(user.getSctAdviserId());
		userServiceMgr.updateUser(u);
		return ajaxDoneSuccess("Success");
	}
	
	@RequestMapping("updateUserName")
	public ModelAndView updateUserName(User user)
	{
		User u = userServiceMgr.getUser(user.getId());
		u.setUserName(user.getUserName());
		try {
			userServiceMgr.updateUserName(u);
		} catch (ObjectDuplicateException e) {
			e.printStackTrace();
			return ajaxDoneError("User name is already in use");
		}
		return ajaxDoneSuccess("Success");
	}
}
