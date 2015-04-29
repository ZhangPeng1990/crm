package uk.co.quidos.gdsap.framework.charge.services.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import uk.co.quidos.gdsap.evaluation.services.ReportServiceMgr;
import uk.co.quidos.gdsap.framework.authority.services.AdminServiceMgr;
import uk.co.quidos.gdsap.framework.charge.CreditPrice;
import uk.co.quidos.gdsap.framework.charge.TransactionRecord;
import uk.co.quidos.gdsap.framework.charge.UserBalance;
import uk.co.quidos.gdsap.framework.charge.enums.BalanceType;
import uk.co.quidos.gdsap.framework.charge.enums.TransactionType;
import uk.co.quidos.gdsap.framework.charge.persistence.GdsapChargeCreditPriceMapper;
import uk.co.quidos.gdsap.framework.charge.persistence.GdsapChargeTransactionRecordMapper;
import uk.co.quidos.gdsap.framework.charge.persistence.GdsapChargeUserBalanceMapper;
import uk.co.quidos.gdsap.framework.charge.persistence.object.GdsapChargeCreditPrice;
import uk.co.quidos.gdsap.framework.charge.persistence.object.GdsapChargeTransactionRecord;
import uk.co.quidos.gdsap.framework.charge.persistence.object.GdsapChargeUserBalance;
import uk.co.quidos.gdsap.framework.charge.services.TransactionRecordServiceMgr;
import uk.co.quidos.gdsap.framework.charge.services.object.ConditionVO;
import uk.co.quidos.gdsap.framework.exception.ChargeException;
import uk.co.quidos.gdsap.framework.exception.ObjectDuplicateException;
import uk.co.quidos.gdsap.framework.sys.business.AbsBusinessObjectServiceMgr;
import uk.co.quidos.gdsap.framework.user.User;
import uk.co.quidos.gdsap.framework.user.services.UserServiceMgr;

@Service("transactionRecordServiceMgr")
@Transactional
public class TransactionRecordServiceMgrImpl extends AbsBusinessObjectServiceMgr implements TransactionRecordServiceMgr
{
	@Autowired
	private GdsapChargeUserBalanceMapper gdsapChargeUserBalanceMapper;

	@Autowired
	private GdsapChargeTransactionRecordMapper gdsapChargeTransactionRecordMapper;
	
	@Autowired
	private GdsapChargeCreditPriceMapper gdsapChargeCreditPriceMapper;
	
	@Autowired
	private ReportServiceMgr reportServiceMgr;
	
	@Autowired
	private UserServiceMgr userServiceMgr;
	
	@Autowired
	private AdminServiceMgr adminServiceMgr;
	
	@Override
	public int getTotalRecord(ConditionVO vo)
	{
		String userName = null;
		Date startInsertTime = null;
		Date endInspectionDate = null;
		Double transactionNum = null;
		Integer transactionType = null;
		Integer recordType = null;
		Integer purpose = null;
		if(vo != null)
		{
			userName = vo.getUserName();
			startInsertTime = vo.getStartInspectionDate();
			endInspectionDate = vo.getEndInspectionDate();
			if(endInspectionDate != null){
				Calendar cal = Calendar.getInstance();
				cal.setTime(endInspectionDate);
				cal.add(Calendar.HOUR, 24);
				endInspectionDate = cal.getTime();
			}
			transactionNum = vo.getTransactionNum();
			transactionType = vo.getTransactionType() != null ? vo.getTransactionType().getCode() : null;
			recordType = vo.getRecordType() != null ? vo.getRecordType().getCode() : null;
			purpose = vo.getPurpose() != null ? vo.getPurpose().getCode() : null;
		}
		
		return this.gdsapChargeTransactionRecordMapper.findNumberByCondition(userName, startInsertTime, endInspectionDate, transactionNum, transactionType, recordType, purpose);
	}
	
	@Override
	public List<TransactionRecord> getRecordByConditionVO(ConditionVO vo, int offset, int limit) {
		String userName = null;
		Date startInsertTime = null;
		Date endInspectionDate = null;
		Double transactionNum = null;
		Integer transactionType = null;
		Integer recordType = null;
		Integer purpose = null;
		if(vo != null)
		{
			userName = vo.getUserName();
			startInsertTime = vo.getStartInspectionDate();
			endInspectionDate = vo.getEndInspectionDate();
			if(endInspectionDate != null){
				Calendar cal = Calendar.getInstance();
				cal.setTime(endInspectionDate);
				cal.add(Calendar.HOUR, 24);
				endInspectionDate = cal.getTime();
			}
			transactionNum = vo.getTransactionNum();
			transactionType = vo.getTransactionType() != null ? vo.getTransactionType().getCode() : null;
			recordType = vo.getRecordType() != null ? vo.getRecordType().getCode() : null;
			purpose = vo.getPurpose() != null ? vo.getPurpose().getCode() : null;
		}
		
		List<GdsapChargeTransactionRecord> models = this.gdsapChargeTransactionRecordMapper.findPageBreakByCondition(userName, startInsertTime, endInspectionDate, transactionNum, transactionType, recordType, purpose, new RowBounds(offset, limit));
		if(!CollectionUtils.isEmpty(models))
		{
			List<TransactionRecord> records = new ArrayList<TransactionRecord>();
			for(GdsapChargeTransactionRecord gctr : models)
			{
				TransactionRecord tr = BeanUtils.toTransactionRecord(gctr);
				tr.setAdmin(gctr.getAdminId() != null ? this.adminServiceMgr.getAdmin(gctr.getAdminId()) : null);
				tr.setReport(gctr.getReportId() != null ? this.reportServiceMgr.getReport(gctr.getReportId()) : null);
				tr.setUser(gctr.getUserId() != null ? this.userServiceMgr.getUser(gctr.getUserId()) : null);
				records.add(tr);
			}
			return records;
		}
		return null;
	}

	@Override
	public synchronized TransactionRecord addRecord(TransactionRecord record) throws ChargeException {
		Assert.notNull(record);
		Assert.notNull(record.getTransactionNum());
		Assert.notNull(record.getUser());
		Assert.notNull(record.getPurpose());
		Assert.notNull(record.getTransactionType());
		Assert.notNull(record.getRecordType());
		
		Double transaction = record.getTransactionNum();
		UserBalance balance = this.getUserBalance(record.getUser(), record.getRecordType());
		if(balance == null)
		{
			balance = new UserBalance(record.getUser(), record.getRecordType());
			balance.setBalance(0.0);
			this.addUserBalance(balance);
		}
		
		if(TransactionType.Charge_Off.equals(record.getTransactionType()))
		{
			if(!this.haveEnoughBalance(record.getUser(), record.getRecordType(), record.getTransactionNum()))
			{
				throw new ChargeException("Lack of balance !");
			}
			if(transaction > 0)
			{
				transaction = 0 - transaction;
			}
		}
		else
		{
			if(transaction < 0)
			{
				transaction = 0 - transaction;
			}
		}
		
		balance.setBalance(balance.getBalance() + transaction);
		this.updateUserBalance(balance);
		
		record.setInsertTime(new Date());
		GdsapChargeTransactionRecord gdsapChargeTransactionRecord = BeanUtils.toGdsapChargeTransactionRecord(record);
		this.gdsapChargeTransactionRecordMapper.insert(gdsapChargeTransactionRecord);
		record.setId(gdsapChargeTransactionRecord.getId());
		return record;
	}


	@Override
	public UserBalance getUserBalance(User user, BalanceType type) {
		Assert.notNull(user);
		Assert.notNull(type);
		GdsapChargeUserBalance gdsapChargeUserBalance = this.gdsapChargeUserBalanceMapper.load(user.getId(), type.getCode());
		if(gdsapChargeUserBalance == null)
		{
			return null;
		}
		UserBalance balance = BeanUtils.toUserBalance(gdsapChargeUserBalance);
		balance.setUser(this.userServiceMgr.getUser(gdsapChargeUserBalance.getUserId()));
		return balance;
	}

	@Override
	public Double getBalance(User user, BalanceType type) {
		Double balance = 0.0;
		balance = this.getUserBalance(user, type) != null ? getUserBalance(user, type).getBalance() : 0.0;
		return balance;
	}

	private UserBalance addUserBalance(UserBalance balance) {
		Assert.notNull(balance.getType());
		Assert.notNull(balance.getUser());
		GdsapChargeUserBalance gdsapChargeUserBalance = BeanUtils.toGdsapChargeUserBalance(balance);
		this.gdsapChargeUserBalanceMapper.insert(gdsapChargeUserBalance);
		return balance;
	}

	private UserBalance updateUserBalance(UserBalance balance) {
		Assert.notNull(balance.getType());
		Assert.notNull(balance.getUser());
		GdsapChargeUserBalance gdsapChargeUserBalance = this.gdsapChargeUserBalanceMapper.load(balance.getUser().getId(), balance.getType().getCode());
		gdsapChargeUserBalance.setBalance(balance.getBalance());
		this.gdsapChargeUserBalanceMapper.updateBalance(gdsapChargeUserBalance);
		return balance;
	}

	@Override
	public boolean haveEnoughBalance(User user, BalanceType type, Double num) {
		if(num < 0)
		{
			num = 0 - num;
		}
		
		if(this.getBalance(user, type) >= num)
		{
			return true;
		}
		return false;
	}
	
	@Override
	public CreditPrice getCreditPrice(BalanceType type, Double num)
	{
		Assert.notNull(type);
		Assert.notNull(num);
		CreditPrice price = null;
		List<GdsapChargeCreditPrice> prices = this.gdsapChargeCreditPriceMapper.selectByType(type.getCode());
		if(!CollectionUtils.isEmpty(prices))
		{
			for(GdsapChargeCreditPrice p : prices)
			{
				if(num >= p.getMinNum())
				{
					return BeanUtils.toCreditPrice(p);
				}
			}
		}
		return price;
	}
	
	@Override
	public CreditPrice addCreditPrice(CreditPrice price) throws ObjectDuplicateException
	{
		Assert.notNull(price.getMinNum());
		Assert.notNull(price.getPrice());
		Assert.notNull(price.getCreditType());

		GdsapChargeCreditPrice p = this.gdsapChargeCreditPriceMapper.selectByType_MinNum(price.getCreditType().getCode(), price.getMinNum());
		if(p != null)
		{
			throw new ObjectDuplicateException();
		}
		p = BeanUtils.toGdsapChargeCreditPrice(price);
		this.gdsapChargeCreditPriceMapper.insert(p);
		price.setId(p.getId());
		return price;
	}
	
	@Override
	public void deleteCreditPrice(Integer id)
	{
		this.gdsapChargeCreditPriceMapper.deleteByPrimaryKey(id);
	}
	
	@Override
	public List<CreditPrice> getCreditPrices(BalanceType type)
	{
		Assert.notNull(type);
		List<CreditPrice> prices = null;
		List<GdsapChargeCreditPrice> ps = this.gdsapChargeCreditPriceMapper.selectByType(type.getCode());
		if(!CollectionUtils.isEmpty(ps))
		{
			prices = new ArrayList<CreditPrice>();
			for(GdsapChargeCreditPrice p : ps)
			{
				prices.add(BeanUtils.toCreditPrice(p));
			}
		}
		return prices;
	}
}
