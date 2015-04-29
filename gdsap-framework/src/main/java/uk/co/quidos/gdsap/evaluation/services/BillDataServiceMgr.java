/**
 * 
 */
package uk.co.quidos.gdsap.evaluation.services;

import uk.co.quidos.gdsap.evaluation.BillDataComm;
import uk.co.quidos.gdsap.evaluation.BillDataEle;
import uk.co.quidos.gdsap.evaluation.BillDataLPG;
import uk.co.quidos.gdsap.evaluation.BillDataMg;
import uk.co.quidos.gdsap.framework.sys.business.BusinessObjectServiceMgr;

/**
 * @author peng.shi
 */
public interface BillDataServiceMgr extends BusinessObjectServiceMgr
{
	/*
	BillData addBillData(BillData billData);

	BillData updateBillData(BillData billData);

	BillData getBillData(long reportId);

	void deleteBillData(long reportId);
	*/
	
	/**
	 * 添加Mains Gas 部分
	 * @param billDataMg
	 * @return
	 */
	BillDataMg addBillDataMg(BillDataMg billDataMg);

	/**
	 * 修改Mains Gas 
	 * @param billDataMg
	 * @return
	 */
	BillDataMg updateBillDataMg(BillDataMg billDataMg);
	
	/**
	 * 获取Mains Gas 
	 * @param reportId
	 * @return
	 */
	BillDataMg getBillDataMg(long reportId);
	
	/**
	 * 删除 Mains Gas
	 * @param reportId
	 */
	void deleteBillDataMg(long reportId);
	
	/**
	 * 添加 Comm 社区
	 * @param billDataComm
	 * @return
	 */
	BillDataComm addBillDataComm(BillDataComm billDataComm);
	
	/**
	 * 更新 Comm 社区
	 * @param billDataComm
	 * @return
	 */
	BillDataComm updateBillDataComm(BillDataComm billDataComm);
	
	/**
	 * 获取 Comm 社区
	 * @param reportId
	 * @return
	 */
	BillDataComm getBillDataComm(long reportId);
	
	/**
	 * 删除 Comm 社区
	 * @param reportId
	 */
	void deleteBillDataComm(long reportId);
	
	/**
	 * @param billDataEle
	 * @return
	 */
	BillDataEle addBillDataEle(BillDataEle billDataEle);
	
	/**
	 * @param billDataEle
	 * @return
	 */
	BillDataEle updateBillDataEle(BillDataEle billDataEle);
	
	/**
	 * @param reportId
	 */
	void deleteBillDataEle(long reportId);
	
	/**
	 * @param reportId
	 * @return
	 */
	BillDataEle getBillDataEle(long reportId);
	

	BillDataLPG addBillDataLPG(BillDataLPG billDataMg);

	BillDataLPG updateBillDataLPG(BillDataLPG billDataMg);
	
	BillDataLPG getBillDataLPG(long reportId);
	
	void deleteBillDataLPG(long reportId);
	
}
