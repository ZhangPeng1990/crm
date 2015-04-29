package uk.co.quidos.gdsap.framework.charge;

import uk.co.quidos.gdsap.framework.charge.enums.BalanceType;
import uk.co.quidos.gdsap.framework.sys.business.AbsBusinessObject;

public class CreditPrice extends AbsBusinessObject {
	
	private static final long serialVersionUID = 7053597088533471988L;

	private Integer id;

	private Integer minNum;

	private Double price;

	private BalanceType creditType;

	private Double quidosVat;

	private Double registerFee;

	private Double registerVat;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getMinNum() {
		return minNum;
	}

	public void setMinNum(Integer minNum) {
		this.minNum = minNum;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public BalanceType getCreditType() {
		return creditType;
	}

	public void setCreditType(BalanceType creditType) {
		this.creditType = creditType;
	}

	public Double getQuidosVat() {
		return quidosVat;
	}

	public void setQuidosVat(Double quidosVat) {
		this.quidosVat = quidosVat;
	}

	public Double getRegisterFee() {
		return registerFee;
	}

	public void setRegisterFee(Double registerFee) {
		this.registerFee = registerFee;
	}

	public Double getRegisterVat() {
		return registerVat;
	}

	public void setRegisterVat(Double registerVat) {
		this.registerVat = registerVat;
	}

}
