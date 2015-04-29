package uk.co.quidos.gdsap.framework.charge.services.impl;

import uk.co.quidos.gdsap.framework.charge.CreditPrice;
import uk.co.quidos.gdsap.framework.charge.TransactionRecord;
import uk.co.quidos.gdsap.framework.charge.UserBalance;
import uk.co.quidos.gdsap.framework.charge.enums.BalanceType;
import uk.co.quidos.gdsap.framework.charge.enums.TransactionPurpose;
import uk.co.quidos.gdsap.framework.charge.enums.TransactionType;
import uk.co.quidos.gdsap.framework.charge.persistence.object.GdsapChargeCreditPrice;
import uk.co.quidos.gdsap.framework.charge.persistence.object.GdsapChargeTransactionRecord;
import uk.co.quidos.gdsap.framework.charge.persistence.object.GdsapChargeUserBalance;
import uk.co.quidos.gdsap.framework.sys.lang.enums.EnumUtils;

public class BeanUtils
{	
	public static CreditPrice toCreditPrice(GdsapChargeCreditPrice gdsapChargeCreditPrice)
	{
		CreditPrice creditPrice = new CreditPrice();
		creditPrice.setCreditType(gdsapChargeCreditPrice.getCreditType() != null ? (BalanceType)EnumUtils.getByCode(gdsapChargeCreditPrice.getCreditType(), BalanceType.class) : null);
		creditPrice.setId(gdsapChargeCreditPrice.getId());
		creditPrice.setMinNum(gdsapChargeCreditPrice.getMinNum());
		creditPrice.setPrice(gdsapChargeCreditPrice.getPrice());
		creditPrice.setQuidosVat(gdsapChargeCreditPrice.getQuidosVat());
		creditPrice.setRegisterFee(gdsapChargeCreditPrice.getRegisterFee());
		creditPrice.setRegisterVat(gdsapChargeCreditPrice.getRegisterVat());
		return creditPrice;
	}
	
	public static GdsapChargeCreditPrice toGdsapChargeCreditPrice(CreditPrice creditPrice)
	{
		GdsapChargeCreditPrice gdsapChargeCreditPrice = new GdsapChargeCreditPrice();
		gdsapChargeCreditPrice.setCreditType(creditPrice.getCreditType() != null ? creditPrice.getCreditType().getCode() : null);
		gdsapChargeCreditPrice.setId(creditPrice.getId());
		gdsapChargeCreditPrice.setMinNum(creditPrice.getMinNum());
		gdsapChargeCreditPrice.setPrice(creditPrice.getPrice());
		gdsapChargeCreditPrice.setQuidosVat(creditPrice.getQuidosVat());
		gdsapChargeCreditPrice.setRegisterFee(creditPrice.getRegisterFee());
		gdsapChargeCreditPrice.setRegisterVat(creditPrice.getRegisterVat());
		return gdsapChargeCreditPrice;
	}
	
	public static UserBalance toUserBalance(GdsapChargeUserBalance gdsapChargeUserBalance)
	{
		UserBalance userBalance = new UserBalance();
		userBalance.setBalance(gdsapChargeUserBalance.getBalance());
		userBalance.setType((BalanceType)EnumUtils.getByCode(gdsapChargeUserBalance.getBalanceType(), BalanceType.class));
		return userBalance;
	}
	
	public static GdsapChargeUserBalance toGdsapChargeUserBalance(UserBalance userBalance)
	{
		GdsapChargeUserBalance gdsapChargeUserBalance = new GdsapChargeUserBalance();
		gdsapChargeUserBalance.setBalance(userBalance.getBalance());
		gdsapChargeUserBalance.setBalanceType(userBalance.getType().getCode());
		gdsapChargeUserBalance.setUserId(userBalance.getUser().getId());
		return gdsapChargeUserBalance;
	}
	
	public static GdsapChargeTransactionRecord toGdsapChargeTransactionRecord(TransactionRecord transactionRecord)
	{
		GdsapChargeTransactionRecord gdsapChargeTransactionRecord = new GdsapChargeTransactionRecord();
		gdsapChargeTransactionRecord.setAdminUserName(transactionRecord.getAdmin() != null ? transactionRecord.getAdmin().getUserName() : transactionRecord.getAdminUserName());
		gdsapChargeTransactionRecord.setAdminId(transactionRecord.getAdmin() != null ? transactionRecord.getAdmin().getId() : null);
		gdsapChargeTransactionRecord.setId(transactionRecord.getId());
		gdsapChargeTransactionRecord.setInsertTime(transactionRecord.getInsertTime());
		gdsapChargeTransactionRecord.setPurpose(transactionRecord.getPurpose() != null ? transactionRecord.getPurpose().getCode() : null);
		gdsapChargeTransactionRecord.setRecordType(transactionRecord.getRecordType() != null ? transactionRecord.getRecordType().getCode() : null);
		gdsapChargeTransactionRecord.setTransactionNum(transactionRecord.getTransactionNum());
		gdsapChargeTransactionRecord.setTransactionType(transactionRecord.getTransactionType() != null ? transactionRecord.getTransactionType().getCode() : null);
		gdsapChargeTransactionRecord.setUserName(transactionRecord.getUser() != null ? transactionRecord.getUser().getUserName() : transactionRecord.getUserName());
		gdsapChargeTransactionRecord.setUserId(transactionRecord.getUser() != null ? transactionRecord.getUser().getId() : null);
		gdsapChargeTransactionRecord.setReportId(transactionRecord.getReport() != null ? transactionRecord.getReport().getId() : null);
		return gdsapChargeTransactionRecord;
	}
	
	public static TransactionRecord toTransactionRecord(GdsapChargeTransactionRecord gdsapChargeTransactionRecord)
	{
		TransactionRecord transactionRecord = new TransactionRecord();
		transactionRecord.setAdminUserName(gdsapChargeTransactionRecord.getAdminUserName());
		transactionRecord.setId(gdsapChargeTransactionRecord.getId());
		transactionRecord.setInsertTime(gdsapChargeTransactionRecord.getInsertTime());
		transactionRecord.setPurpose(gdsapChargeTransactionRecord.getPurpose() != null ? (TransactionPurpose)EnumUtils.getByCode(gdsapChargeTransactionRecord.getPurpose(), TransactionPurpose.class) : null);
		transactionRecord.setRecordType(gdsapChargeTransactionRecord.getRecordType() != null ? (BalanceType)EnumUtils.getByCode(gdsapChargeTransactionRecord.getRecordType(), BalanceType.class) : null);
		transactionRecord.setTransactionNum(gdsapChargeTransactionRecord.getTransactionNum());
		transactionRecord.setTransactionType(gdsapChargeTransactionRecord.getTransactionType() != null ? (TransactionType)EnumUtils.getByCode(gdsapChargeTransactionRecord.getTransactionType(), TransactionType.class) : null);
		transactionRecord.setUserName(gdsapChargeTransactionRecord.getUserName());
		return transactionRecord;
	}
}
