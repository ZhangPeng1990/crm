package uk.co.quidos.adminconsole.web.report;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.RowBounds;
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
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import uk.co.quidos.adminconsole.web.BaseController;
import uk.co.quidos.common.util.FileUtils;
import uk.co.quidos.gdsap.evaluation.Report;
import uk.co.quidos.gdsap.evaluation.enums.ReportStatus;
import uk.co.quidos.gdsap.evaluation.services.ReportServiceMgr;
import uk.co.quidos.gdsap.evaluation.services.object.ConditionVO;
import uk.co.quidos.gdsap.framework.sysconf.GlobalConfig;

@Controller
@RequestMapping("/report/")
public class ReportController extends BaseController
{

	@Autowired
	ReportServiceMgr reportServiceMgr;
	
	@RequestMapping("index")
	public String listReports(Model model, ConditionVO vo, HttpServletResponse response, HttpServletRequest request)
	{
		final String keyWordsInputStr = "UPRN, RRN, OA RRN, Post Code";
		model.addAttribute("defuShow",keyWordsInputStr);
		if(vo.getKeywords() != null && vo.getKeywords().trim().equals(keyWordsInputStr))
		{
			vo.setKeywords(null);
		}
		
		ReportStatus[] peportStatus = ReportStatus.values();
		model.addAttribute("status",peportStatus);
		
		RowBounds rb = new RowBounds(vo.getStartIndex(), vo.getPageSize());
		List<Report> reports = reportServiceMgr.adminGetReportsByConditionVO(vo, rb);
		model.addAttribute("reports",reports);
		
		int totalCount = reportServiceMgr.adminGetTotalReportsByConditionVO(vo);
		model.addAttribute("totalCount",totalCount);
		model.addAttribute("pageSize",vo.getPageSize());
		model.addAttribute("object",vo);
		
		return "/report/index";
	}
	
	
	/**
	 * 生成Excel
	 */
	private void createExcel(Map<Integer,List<Report>> map, HttpServletRequest request)
	{
		String path = "";
		String savePath = GlobalConfig.getInstance().getFSDir() + File.separator + "temp";
		if (!new File(savePath).exists())
		{
			new File(savePath).mkdirs();
		}
		HttpSession session = request.getSession();
		path = savePath + File.separator + session.getId() + "Reports.xls";

		HSSFWorkbook workbook = new HSSFWorkbook();
		
		Set<Integer> key = map.keySet();
        for (Iterator<Integer> it = key.iterator(); it.hasNext();)
        {
            Integer s = it.next();
            List<Report> list = map.get(s);
    		// 设置单元格内容格式
    	    HSSFCellStyle style1 = workbook.createCellStyle();
    	    style1.setDataFormat(HSSFDataFormat.getBuiltinFormat("m/d/yy"));
    		
    		HSSFSheet sheet = workbook.createSheet("sheet" + s);
    		
    		HSSFRow row;
    		row = sheet.createRow(0);
    		for(int i = 0 ; i < 8 ; i++)
    		{
    			HSSFCell cell = row.createCell(i);
    			switch(i)
    			{
    				case 0:
    				cell.setCellValue("RRN");
    				break;
    				
    				case 1:
    				cell.setCellValue("OA RRN");
    				break;
    				
    				case 2:
    				cell.setCellValue("Lodgement date");
    				break;
    				
    				case 3:
    				cell.setCellValue("Organisation ID");
    				break;
    				
    				case 4:
    				cell.setCellValue("Green Deal Advisor ID");
    				break;
    				
    				case 5:
    				cell.setCellValue("D or ND (Default Domestic)");
    				break;
    				
    				case 6:
    				cell.setCellValue("Country");
    				break;
    				
    				case 7:
    				cell.setCellValue("Postcode");
    				break;
    			}
    			
    		}
    		
    		for(int a = 0 ; a < list.size() ; a++)
    		{
    			row = sheet.createRow(a + 1);
    			for(int i = 0 ; i < 8 ; i++)
    			{
    				HSSFCell cell = row.createCell(i);
    				switch(i)
    				{
    					case 0:
    					cell.setCellValue(list.get(a).getRrn());
    					break;
    					
    					case 1:
    					cell.setCellValue(list.get(a).getOaRrn());
    					break;
    					
    					case 2:
						if(list.get(a).getLodgeDate() != null)
						{
							cell.setCellStyle(style1);
							cell.setCellValue(list.get(a).getLodgeDate());
						}
						else
						{
							cell.setCellValue("");
						}
						break;
    					
    					case 3:
    					cell.setCellValue(list.get(a).getUser().getOrganisationCertificationNumber());
    					break;
    					
    					case 4:
    					cell.setCellValue(list.get(a).getUser().getUserName());
    					break;
    					
    					case 5:
    					cell.setCellValue("Domestic");
    					break;
    					
    					case 6:
    					cell.setCellValue(list.get(a).getReportLocation() != null ? list.get(a).getReportLocation().getDesc() : "");
    					break;
    					
    					case 7:
    					cell.setCellValue(list.get(a).getPostcode());
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
	private void createExcel(List<Report> list, HttpServletRequest request)
	{
		Assert.notEmpty(list, "noneItems");
		String path = "";
		String savePath = GlobalConfig.getInstance().getFSDir() + File.separator + "temp";
		if (!new File(savePath).exists())
		{
			new File(savePath).mkdirs();
		}
		HttpSession session = request.getSession();
		path = savePath + File.separator + session.getId() + "Reports.xls";
		
		HSSFWorkbook workbook = new HSSFWorkbook();
		
		// 设置单元格内容格式
	    HSSFCellStyle style1 = workbook.createCellStyle();
	    style1.setDataFormat(HSSFDataFormat.getBuiltinFormat("m/d/yy"));
		
		HSSFSheet sheet = workbook.createSheet();
		
		HSSFRow row;
		row = sheet.createRow(0);
		for(int i = 0 ; i < 8 ; i++)
		{
			HSSFCell cell = row.createCell(i);
			switch(i)
			{
				case 0:
				cell.setCellValue("RRN");
				break;
				
				case 1:
				cell.setCellValue("OA RRN");
				break;
				
				case 2:
				cell.setCellValue("Lodgement date");
				break;
				
				case 3:
				cell.setCellValue("Organisation ID");
				break;
				
				case 4:
				cell.setCellValue("Green Deal Advisor ID");
				break;
				
				case 5:
				cell.setCellValue("D or ND (Default Domestic)");
				break;
				
				case 6:
				cell.setCellValue("Country");
				break;
				
				case 7:
				cell.setCellValue("Postcode");
				break;
			}
			
		}
		
		for(int a = 0 ; a < list.size() ; a++)
		{
			row = sheet.createRow(a + 1);
			for(int i = 0 ; i < 8 ; i++)
			{
				HSSFCell cell = row.createCell(i);
				switch(i)
				{
					case 0:
					cell.setCellValue(list.get(a).getRrn());
					break;
					
					case 1:
					cell.setCellValue(list.get(a).getOaRrn());
					break;
					
					case 2:
					if(list.get(a).getLodgeDate() != null)
					{
						cell.setCellStyle(style1);
						cell.setCellValue(list.get(a).getLodgeDate());
					}
					else
					{
						cell.setCellValue("");
					}
					break;
					
					case 3:
					cell.setCellValue(list.get(a).getUser().getOrganisationCertificationNumber());
					break;
					
					case 4:
					cell.setCellValue(list.get(a).getUser().getUserName());
					break;
					
					case 5:
					cell.setCellValue("Domestic");
					break;
					
					case 6:
					cell.setCellValue(list.get(a).getReportLocation() != null ? list.get(a).getReportLocation().getDesc() : "");
					break;
					
					case 7:
					cell.setCellValue(list.get(a).getPostcode());
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
	
	/**
	 * 下载Excel
	 * @param list
	 * @return
	 */
	@RequestMapping("download")
	public String exportExcel(ConditionVO vo,HttpServletResponse response, HttpServletRequest request)
	{
		int totalCount = reportServiceMgr.adminGetTotalReportsByConditionVO(vo);
		
		if(totalCount > 0)
		{
			ConditionVO tempvo = new ConditionVO();
			tempvo = vo;
			int excelPageSize = GlobalConfig.getInstance().getExcelPageSize();
			if(totalCount >= excelPageSize)
			{
				Map<Integer, List<Report>> mapReport = new HashMap<Integer, List<Report>>();
				List<Report> tempReports = new ArrayList<Report>();
				int surplus = totalCount % excelPageSize;
				int time = (totalCount - surplus) / excelPageSize;
				tempvo.setPageSize(excelPageSize);
				for(int i = 0 ; i < time ; i++)
				{
					RowBounds rb = new RowBounds(i * excelPageSize, tempvo.getPageSize());
					tempReports = reportServiceMgr.adminGetReportsByConditionVO(tempvo, rb);
					mapReport.put(i, tempReports);
					if(i == time - 1)
					{
						if(surplus > 0)
						{
							RowBounds rbs = new RowBounds((i + 1) * excelPageSize, tempvo.getPageSize());
							tempReports = reportServiceMgr.adminGetReportsByConditionVO(tempvo, rbs);
							mapReport.put(i + 1, tempReports);
						}
					}
				}
				createExcel(mapReport, request);
			}
			else
			{
				List<Report> excelReports = new ArrayList<Report>();
				tempvo.setPageSize(totalCount);
				RowBounds rb = new RowBounds(tempvo.getStartIndex(), tempvo.getPageSize());
				excelReports = reportServiceMgr.adminGetReportsByConditionVO(tempvo, rb);
				createExcel(excelReports, request);
			}
		}
		
		HttpSession session = request.getSession();
		String savePath = GlobalConfig.getInstance().getFSDir() + File.separator + "temp";
		String path = savePath + File.separator + session.getId() + "Reports.xls";
		
		byte[] buffer = new byte[1024];
		int length = 0;
		InputStream is = null;
		response.reset();
		OutputStream os = null;
		try
		{
			response.reset();
			response.addHeader("Content-Disposition", "attachment;filename=" + "reports.xls");
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
					FileUtils.deleteFile(GlobalConfig.getInstance().getFSDir() + File.separator + "temp", session.getId() + "Reports.xls");
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
					FileUtils.deleteFile(GlobalConfig.getInstance().getFSDir() + File.separator + "temp", session.getId() + "Reports.xls");
				}
			}
		}
		return null;
	}
	
	
	/**
	 * 导出和QUBE-228有同样问题的report
	 * @param list
	 * @return
	 */
	@RequestMapping("checkExport")
	public String checkExportExcel(ConditionVO vo,HttpServletResponse response, HttpServletRequest request)
	{
		vo.setReportStatus(ReportStatus.Lodged);
		
//		Date startInspectionDate = null;
//		Calendar cal = Calendar.getInstance();
//		cal.set(2014, 7, 1, 0,0,0);
//		startInspectionDate = cal.getTime();
		
//		vo.setStartInspectionDate(startInspectionDate);
		
		int totalCount = reportServiceMgr.adminGetTotalReportsByConditionVO(vo);
		RowBounds rb = new RowBounds(0, totalCount);
		List<Report> reports = reportServiceMgr.adminCheckReportsByConditionVO(vo, rb);
		
		if(!CollectionUtils.isEmpty(reports))
		{
			createExcel(reports);
		}
		
		return null;
	}
	
	
	/**
	 * 导出和QUBE-278有同样问题的report
	 * @param list
	 * 查询的节点 
	 * 
		<Occupancy-Assessment-Results> 
	  	<Energy-Use> 
	    	<Space-Heating>3950</Space-Heating> 
	    	<Water-Heating>1208</Water-Heating>
	    </Energy-Use> 
	 * @return
	 */
	@RequestMapping("checkExport/278")
	public String checkAndExport(ConditionVO vo,HttpServletResponse response, HttpServletRequest request)
	{
		vo.setReportStatus(ReportStatus.Lodged);
		
		Date startInspectionDate = null;
		Calendar cal = Calendar.getInstance();
		cal.set(2014, 11, 6, 0,0,0);
		startInspectionDate = cal.getTime();
		
		vo.setStartInspectionDate(startInspectionDate);
		
		int totalCount = reportServiceMgr.adminGetTotalReportsByConditionVO(vo);
		RowBounds rb = new RowBounds(0, totalCount);
		List<Report> reports = reportServiceMgr.adminCheckReportsByConditionVO2(vo, rb);
		
		if(!CollectionUtils.isEmpty(reports))
		{
			createExcel(reports);
		}
		
		return null;
	}
	
	enum ExportEnum
	{
		ID("ID"),
		RRN("rrn"),
		OARRN("oa rrn"),
		InsertDate("Insert Date"),
		LodgementDate("Lodgement date"),
		ReportLocation("Country"),
		Postcode("Postcode"),
		OrganisationID("Organisation ID"),
		GreenDealAdvisorID("Green Deal Advisor ID"),
		D_value("total-(ele+gas+other)"),
		;
		
		private ExportEnum(String desc)
		{
			this.desc = desc;
		}
		
		private String desc;

		public String getDesc() {
			return desc;
		}
		
	}
	
	/**
	 * 生成Excel
	 * 为了导出QUBE-228的问题
	 */
	private void createExcel(List<Report> list)
	{
		Assert.notEmpty(list, "noneItems");
		String path = "";
		String savePath = GlobalConfig.getInstance().getFSDir() + File.separator + "temp";
		if (!new File(savePath).exists())
		{
			new File(savePath).mkdirs();
		}
		path = savePath + File.separator + "errorReports.xls";
		
		HSSFWorkbook workbook = new HSSFWorkbook();
		
		// 设置单元格内容格式
	    HSSFCellStyle style1 = workbook.createCellStyle();
	    style1.setDataFormat(HSSFDataFormat.getBuiltinFormat("m/d/yy"));
		
		HSSFSheet sheet = workbook.createSheet();
		
		HSSFRow row;
		row = sheet.createRow(0);
		for(ExportEnum ee : ExportEnum.values())
		{
			HSSFCell cell = row.createCell(ee.ordinal());
			cell.setCellValue(ee.getDesc());
		}
		
		for(int a = 0 ; a < list.size() ; a++)
		{
			row = sheet.createRow(a + 1);
			for(ExportEnum ee : ExportEnum.values())
			{
				HSSFCell cell = row.createCell(ee.ordinal());
				switch(ee)
				{
					case ID:
					cell.setCellValue(list.get(a).getId());
					break;
					
					case RRN:
					cell.setCellValue(list.get(a).getRrn());
					break;
					
					case OARRN:
					cell.setCellValue(list.get(a).getOaRrn());
					break;
					
					case InsertDate:
					if(list.get(a).getInsertTime() != null)
					{
						cell.setCellStyle(style1);
						cell.setCellValue(list.get(a).getInsertTime());
					}
					else
					{
						cell.setCellValue("");
					}
					break;
						
					case LodgementDate:
					if(list.get(a).getLodgeDate() != null)
					{
						cell.setCellStyle(style1);
						cell.setCellValue(list.get(a).getLodgeDate());
					}
					else
					{
						cell.setCellValue("");
					}
					break;
					
					case ReportLocation:
					cell.setCellValue(list.get(a).getReportLocation() != null ? list.get(a).getReportLocation().getDesc() : "");
					break;
					
					case Postcode:
					cell.setCellValue(list.get(a).getPostcode());
					break;
					
					case OrganisationID:
					cell.setCellValue(list.get(a).getUser().getOrganisationCertificationNumber());
					break;
						
					case GreenDealAdvisorID:
					cell.setCellValue(list.get(a).getUser().getUserName());
					break;
					
					case D_value:
					cell.setCellValue(list.get(a).getdValue());
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
}
