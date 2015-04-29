package uk.co.quidos.gdsap.framework.charge.services.object;

import java.util.Date;

import uk.co.quidos.dal.BaseConditionVO;
import uk.co.quidos.gdsap.framework.charge.enums.BalanceType;
import uk.co.quidos.gdsap.framework.charge.enums.TransactionPurpose;
import uk.co.quidos.gdsap.framework.charge.enums.TransactionType;

public class ConditionVO extends BaseConditionVO 
{

	private String userName;
	private Date startInspectionDate;
	private Date endInspectionDate;
	private Double transactionNum;
	private TransactionType transactionType;
	private BalanceType recordType;
	private TransactionPurpose purpose;
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Date getStartInspectionDate() {
		return startInspectionDate;
	}
	public void setStartInspectionDate(Date startInspectionDate) {
		this.startInspectionDate = startInspectionDate;
	}
	public Date getEndInspectionDate() {
		return endInspectionDate;
	}
	public void setEndInspectionDate(Date endInspectionDate) {
		this.endInspectionDate = endInspectionDate;
	}
	public Double getTransactionNum() {
		return transactionNum;
	}
	public void setTransactionNum(Double transactionNum) {
		this.transactionNum = transactionNum;
	}
	public TransactionType getTransactionType() {
		return transactionType;
	}
	public void setTransactionType(TransactionType transactionType) {
		this.transactionType = transactionType;
	}
	public BalanceType getRecordType() {
		return recordType;
	}
	public void setRecordType(BalanceType recordType) {
		this.recordType = recordType;
	}
	public TransactionPurpose getPurpose() {
		return purpose;
	}
	public void setPurpose(TransactionPurpose purpose) {
		this.purpose = purpose;
	}
}
