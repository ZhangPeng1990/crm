package uk.co.quidos.adminconsole.web.charge;

import uk.co.quidos.gdsap.framework.charge.enums.BalanceType;
import uk.co.quidos.gdsap.framework.charge.enums.TransactionPurpose;

public class TransactionForm {

	private Long userId;
	private TransactionPurpose purpose;
	private BalanceType creditType;
	private Double creditCount;
	
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public TransactionPurpose getPurpose() {
		return purpose;
	}
	public void setPurpose(TransactionPurpose purpose) {
		this.purpose = purpose;
	}
	public BalanceType getCreditType() {
		return creditType;
	}
	public void setCreditType(BalanceType creditType) {
		this.creditType = creditType;
	}
	public Double getCreditCount() {
		return creditCount;
	}
	public void setCreditCount(Double creditCount) {
		this.creditCount = creditCount;
	}
}
