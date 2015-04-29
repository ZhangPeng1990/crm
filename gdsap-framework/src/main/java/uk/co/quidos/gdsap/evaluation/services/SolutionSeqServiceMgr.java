/**
 * 
 */
package uk.co.quidos.gdsap.evaluation.services;

import uk.co.quidos.gdsap.evaluation.enums.SolutionType;
import uk.co.quidos.gdsap.framework.sys.business.BusinessObjectServiceMgr;

/**
 * @author peng.shi
 *
 */
public interface SolutionSeqServiceMgr extends BusinessObjectServiceMgr
{
	public static final int DEFAULT_INIT_SEQ_VALUE = 1;
	
	/**
	 * 获取ReportId下，下一个命名方式
	 * 1. 根据reportid查询是否存在value，如果不存在，则插入一条数据，并将value初始化为1
	 * 2. 如果已经存在，则将对象中的数据+1，并更新该条数据，返回+1后的数据
	 * @param reportId
	 * @return
	 */
	public long nextSeq(long reportId, SolutionType solutionType);
	
	/**
	 * 清理report相关
	 * @param report
	 */
	public void delete(long reportId);
}
