package uk.co.quidos.gdsap.framework.charge;

import java.io.Serializable;

import uk.co.quidos.gdsap.framework.charge.enums.BalanceType;
import uk.co.quidos.gdsap.framework.sys.business.AbsBusinessObject;
import uk.co.quidos.gdsap.framework.user.User;

/**
 * 用户余额业务对象
 * 可能会有多种类型的余额，所以用一个对象来记录方便扩展
 * 保证一个user一种type的余额数据库只有一条记录
 * 所以数据库用userId和type.code作为复合主键
 * @author ZP
 *
 */
public class UserBalance extends AbsBusinessObject 
{
	private static final long serialVersionUID = 1L;

	public UserBalance(){}
	
	public UserBalance(User user, BalanceType type)
	{
		this.user = user;
		this.type = type;
	}
	
	private User user;
	
	/*/
	 * 用于区分余额类型，因为可能会用专款专用的情况
	 */
	private BalanceType type;
	
	/**
	 * 余额
	 */
	private Double balance;
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public BalanceType getType() {
		return type;
	}

	public void setType(BalanceType type) {
		this.type = type;
	}

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	@Override
	public Serializable getId() {
		return user.getId().toString() + type.getCode().toString();
	}
}
