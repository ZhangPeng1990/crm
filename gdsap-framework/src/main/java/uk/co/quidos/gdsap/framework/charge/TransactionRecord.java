package uk.co.quidos.gdsap.framework.charge;

import java.sql.Timestamp;
import java.util.Date;

import org.springframework.util.Assert;

import uk.co.quidos.common.util.DateUtil;
import uk.co.quidos.gdsap.evaluation.Report;
import uk.co.quidos.gdsap.framework.authority.Admin;
import uk.co.quidos.gdsap.framework.charge.enums.BalanceType;
import uk.co.quidos.gdsap.framework.charge.enums.TransactionPurpose;
import uk.co.quidos.gdsap.framework.charge.enums.TransactionType;
import uk.co.quidos.gdsap.framework.sys.business.AbsBusinessObject;
import uk.co.quidos.gdsap.framework.user.User;

/**
 * 交易记录业务对象
 * @author ZP
 *
 */
public class TransactionRecord extends AbsBusinessObject 
{

	private static final long serialVersionUID = 5380467984764365913L;

	public TransactionRecord(){}
	
	public TransactionRecord(Double transactionNum, TransactionType transactionType, BalanceType recordType, TransactionPurpose purpose, User user)
	{
		Assert.notNull(transactionNum);
		Assert.notNull(transactionType);
		Assert.notNull(recordType);
		Assert.notNull(purpose);
		Assert.notNull(user);
		this.transactionNum = transactionNum;
		this.transactionType = transactionType;
		this.recordType = recordType;
		this.purpose = purpose;
		this.user = user;
	}
	
	private Long id;
	
	/**
	 * 交易金额
	 * 如 充值了几个点或扣除几个点的数量
	 */
	private Double transactionNum;
	
	/**
	 * 交易类型, 用来区分是充值还是扣除
	 */
	private TransactionType transactionType;
	
	/**
	 * 用于区分交易积分的种类
	 */
	private BalanceType recordType;
	/**
	 * 交易用途，如：用于GDIP lodge
	 */
	private TransactionPurpose purpose;
	
	private User user;
	
	/**
	 * 当事人的userName, 如user从数据库删除的情况下仍然可以看到userName
	 */
	private String userName;
	
	private Admin admin;//ADMIN_ID
	/**
	 * 当admin手动添加时记录该admin的userName;
	 */
	private String adminUserName;
	
	/**
	 * 如果是lodge report扣除的情况下需要记录report
	 */
	private Report report;
	
	/**
	 * 详细的用途描述
	 */
	private String descDetailed;
	
	private Date insertTime;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public TransactionPurpose getPurpose() {
		return purpose;
	}

	public void setPurpose(TransactionPurpose purpose) {
		this.purpose = purpose;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getAdminUserName() {
		return adminUserName;
	}

	public void setAdminUserName(String adminUserName) {
		this.adminUserName = adminUserName;
	}

	public String getDescDetailed() {
		TransactionPurpose tp = this.purpose;
		String[] values = null;
		switch(tp)
		{
			case Add_By_Admin:
				values = new String[]{this.adminUserName, transactionNum.toString()};
				break;
				
			case Purchase_From_website:
				values = new String[]{this.user.getUserName(), transactionNum.toString()};
				break;
				
			case GDIP_Charge:
				values = new String[]{this.user.getUserName(), transactionNum.toString(), this.report.getGdipRrn()};
				break;
		}
		
		if(values != null)
		{
			descDetailed = "In " + DateUtil.timestamp2String(new Timestamp(insertTime.getTime()), DateUtil.PATTERN_STANDARD) + "," + tp.getDesc_detailed(values);
		}
		return descDetailed;
	}

	public Report getReport() {
		return report;
	}

	public void setReport(Report report) {
		this.report = report;
	}

	public BalanceType getRecordType() {
		return recordType;
	}

	public void setRecordType(BalanceType recordType) {
		this.recordType = recordType;
	}

	public void setDescDetailed(String descDetailed) {
		this.descDetailed = descDetailed;
	}
	
	public Admin getAdmin() {
		return admin;
	}

	public void setAdmin(Admin admin) {
		this.admin = admin;
	}

	public static void main(String[] args) {
		TransactionRecord tr = new TransactionRecord();
		User u = new User();
		u.setUserName("ZP");
		Report r = new Report();
		r.setGdipRrn("145-1245-12354-7895-2561");
		tr.setUser(u);
		tr.setReport(r);
		tr.setPurpose(TransactionPurpose.Purchase_From_website);
		tr.setInsertTime(new Date());
		tr.setTransactionNum(5.0);
		System.out.println(tr.getDescDetailed());
	}
}
