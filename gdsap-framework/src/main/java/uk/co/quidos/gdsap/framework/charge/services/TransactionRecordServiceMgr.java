package uk.co.quidos.gdsap.framework.charge.services;

import java.util.List;

import uk.co.quidos.gdsap.framework.charge.CreditPrice;
import uk.co.quidos.gdsap.framework.charge.TransactionRecord;
import uk.co.quidos.gdsap.framework.charge.UserBalance;
import uk.co.quidos.gdsap.framework.charge.enums.BalanceType;
import uk.co.quidos.gdsap.framework.charge.services.object.ConditionVO;
import uk.co.quidos.gdsap.framework.exception.ChargeException;
import uk.co.quidos.gdsap.framework.exception.ObjectDuplicateException;
import uk.co.quidos.gdsap.framework.sys.business.BusinessObjectServiceMgr;
import uk.co.quidos.gdsap.framework.user.User;

public interface TransactionRecordServiceMgr extends BusinessObjectServiceMgr
{
	public static final String SERVICE_NAME = "transactionRecordServiceMgr";
	
	/**
	 * 获取用户账户下指定类型的积分点数
	 * @param user
	 * @param type
	 * @return
	 */
	UserBalance getUserBalance(User user, BalanceType type);
	
	/**
	 * 获取用户账户下指定类型的积分点数
	 * @param user
	 * @param type
	 * @return
	 */
	Double getBalance(User user, BalanceType type);
	
	List<TransactionRecord> getRecordByConditionVO(ConditionVO vo, int offset, int limit);
	
	/**
	 * 添加交易记录，同时自动修改用户的余额
	 * @param record
	 * @return
	 * @throws ChargeException
	 */
	TransactionRecord addRecord(TransactionRecord record) throws ChargeException;
	
	int getTotalRecord(ConditionVO vo);
	
	/**
	 * 检测指定用户的账户下是否有足够的type的点数
	 * @param user
	 * @param type
	 * @param num
	 * @return
	 */
	boolean haveEnoughBalance(User user, BalanceType type, Double num);
	
	/**
	 * 根据积分数量和积分类型获取价格
	 * @param type
	 * @param num
	 * @return
	 */
	CreditPrice getCreditPrice(BalanceType type, Double num);
	
	/**
	 * 添加CreditPrice, 如果type和minNum重复抛异常
	 * @param price
	 * @return
	 * @throws ObjectDuplicateException
	 */
	CreditPrice addCreditPrice(CreditPrice price) throws ObjectDuplicateException;
	
	/**
	 * 删除CreditPrice
	 * @param id
	 */
	void deleteCreditPrice(Integer id);
	
	List<CreditPrice> getCreditPrices(BalanceType type);
}
