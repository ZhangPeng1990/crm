/**
 * 
 */
package uk.co.quidos.gdsap.evaluation.services;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.dom4j.Document;

import uk.co.quidos.gdsap.evaluation.Company;
import uk.co.quidos.gdsap.evaluation.EnergyUse;
import uk.co.quidos.gdsap.evaluation.Report;
import uk.co.quidos.gdsap.evaluation.enums.ReportStatus;
import uk.co.quidos.gdsap.evaluation.services.object.ConditionVO;
import uk.co.quidos.gdsap.framework.enums.UploadWay;
import uk.co.quidos.gdsap.framework.exception.ObjectDuplicateException;
import uk.co.quidos.gdsap.framework.sys.business.BusinessObjectServiceMgr;
import uk.co.quidos.gdsap.framework.sys.exception.ServiceException;
import uk.co.quidos.gdsap.framework.user.User;
import uk.co.quidos.gdsap.framework.webservice.ServiceRequest;

/**
 * @author peng.shi
 *
 */
public interface ReportServiceMgr extends BusinessObjectServiceMgr
{
	public static final String SERVICE_NAME = "reportServiceMgr";
	
	/**
	 * 从Xml中进行读取,
	 * Report基本数据从ReportHeader节点读取
	 * 1. Heat Proportion 初始化方式,xml中获取一下字段
	 * <HIP:Habitable-Room-Count>
	 * 并读取里边的整数值，如果为1，则添加一个RoomType == liveRoom的对象到DB中
	 * 如果  > 1 ，则添加一个 添加一个RoomType == liveRoom的对象到DB中，其他RoomType 为 OtherRoom类型,初始化时其他YesNo 字段全部设定为No
	 * @param doc
	 * @return
	 */
	Report addReport(Document doc,String fileName,User user,Report temReport) throws ObjectDuplicateException;
	
	/**
	 * Installer 用户导入GDIP report
	 * @param user
	 * @param report
	 * @return
	 * @throws ObjectDuplicateException
	 */
	Report addReport(User user,Report report) throws ObjectDuplicateException;
	
	/**
	 * 提供给第三方通过web service创建报告使用
	 * @param serviceRequest
	 * @return
	 * @throws ServiceException
	 */
	boolean addReport(ServiceRequest serviceRequest, UploadWay uploadWay) throws ServiceException;
	
	/**
	 * 提供给第三方通过web service lodge报告使用
	 * @param serviceRequest
	 * @return
	 * @throws ServiceException
	 */
	boolean lodgeReport(ServiceRequest serviceRequest, UploadWay uploadWay) throws ServiceException;
	
	/**
	 * Inspection Date，Completion Date，Registration Date，UpdateTime，Related party disclosure 
	 * @param report
	 * @return
	 */
	Report updateBaseReportInfo(Report report);
	
	Report updateReportAddressInfo(Report report);
	
	/**
	 * 直接从DB中读取
	 * @param id
	 * @return
	 */
	EnergyUse getEnergyUser(long reportId);
	
	/**
	 * 获取Report
	 * @param id
	 * @return
	 */
	Report getReport(long id);
	
	/**
	 * 获取Report
	 * @param rrn
	 * @param reportStatus
	 * @return
	 */
	Report getReportByRRN(String rrn, ReportStatus reportStatus);
	
	/**
	 * 获取Report
	 * @param company
	 * @param companyJobRef
	 * @return
	 */
	Report getReport(Company company, String companyJobRef);
	
	/**
	 * 获取Report
	 * @param oaRrn
	 * @param reportStatus
	 * @return
	 */
	Report getReportByOARRN(String oaRrn, ReportStatus reportStatus);
	
	/**
	 * 通过ConditionVO 查询 ConditionVO
	 * @param vo
	 * @param offset
	 * @param limit
	 * @return
	 */
	List<Report> getReportsByConditionVO(ConditionVO vo);
	
	/**
	 * 通过Total reports
	 * @param vo
	 * @return
	 */
	int getTotalReportsByConditionVO(ConditionVO vo);
	
	/**
	 * admin通过ConditionVO 查询 ConditionVO
	 * @param vo
	 * @param offset
	 * @param limit
	 * @return
	 */
	List<Report> adminGetReportsByConditionVO(ConditionVO vo, RowBounds rb);
	
	/**
	 * admin 检查report， 类似QUBE-228的问题
	 * @param vo
	 * @param offset
	 * @param limit
	 * @return
	 */
	List<Report> adminCheckReportsByConditionVO(ConditionVO vo, RowBounds rb);
	/**
	 * admin 检查report， 类似QUBE-278的问题
	 * @param vo
	 * @param offset
	 * @param limit
	 * @return
	 */
	List<Report> adminCheckReportsByConditionVO2(ConditionVO vo, RowBounds rb);
	
	/**
	 * admin通过Total reports
	 * @param vo
	 * @return
	 */
	int adminGetTotalReportsByConditionVO(ConditionVO vo);
	
	/**
	 * 获取全部Reports
	 * @return
	 */
	List<Report> getReports();
	
	/**
	 * 删除Report
	 * @param id
	 */
	void deleteReport(long id);
	
	/**
	 * 删除Report
	 * @param id
	 */
	void installerDeleteReport(Long reportId, Long userId);
	
	/**
	 * 是否能点击第三步的Save&Next进入Billdata
	 */
	boolean enterBilldata(Report report);
	
	/**
	 * 查看是否有权限查看/编辑Billdata
	 * @param report
	 * @return
	 */
	boolean viewBilldata(Report report);
	
	/**
	 * 查看是否有权限查看/编辑Cal 
	 * @param report
	 * @return
	 */
	boolean viewCal(Report report);
	
	/**
	 * 删除Report 下的 Solutions 以及 Recommendations
	 * @param report
	 */
	void deleteSolutionsWithRecommendations(Report report);
	
	/**
	 * @param report
	 * @return
	 */
	Report updateOARrn(Report report);
	
	/**
	 * @param report
	 * @return
	 */
	Report updateGdipRrn(Report report);
	
	/**
	 * 初始化Report,当前Report 必须为UNOCCUPIED_PROPERTY 为Yes
	 * @throws Exception 
	 */
	Report initialReport(Document doc,String fileName,User user,Report temReport) throws Exception;

	/**
	 * 更新ReportStatus 状态
	 * @param report
	 * @param reportStatus
	 * @return
	 */
	Report updateReportStatus(Report report,ReportStatus reportStatus);
	
	/**
	 * 第三方公司通过web service lodge report
	 * @param request
	 * @return
	 * @throws ServiceException
	 */
	Report thirdPartyLodgeReport(ServiceRequest request) throws ServiceException;
}
