package uk.co.quidos.adminconsole.web.charge;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import uk.co.quidos.adminconsole.web.BaseController;
import uk.co.quidos.adminconsole.web.Constants;
import uk.co.quidos.gdsap.framework.authority.Admin;
import uk.co.quidos.gdsap.framework.authority.services.AdminServiceMgr;
import uk.co.quidos.gdsap.framework.charge.TransactionRecord;
import uk.co.quidos.gdsap.framework.charge.enums.BalanceType;
import uk.co.quidos.gdsap.framework.charge.enums.TransactionPurpose;
import uk.co.quidos.gdsap.framework.charge.enums.TransactionType;
import uk.co.quidos.gdsap.framework.charge.services.TransactionRecordServiceMgr;
import uk.co.quidos.gdsap.framework.exception.ChargeException;
import uk.co.quidos.gdsap.framework.user.User;
import uk.co.quidos.gdsap.framework.user.services.UserServiceMgr;

@Controller
@RequestMapping("/transaction/")
public class TransactionController  extends BaseController 
{

	@Autowired
	private TransactionRecordServiceMgr transactionRecordServiceMgr;
	
	@Autowired
	private UserServiceMgr userServiceMgr;
	
	@Autowired
	private AdminServiceMgr adminServiceMgr;
	
	@RequestMapping("skipEditCredits/{id}")
	public String skipEditCredits(@PathVariable("id") Long id, Model model)
	{
		User user = userServiceMgr.getUser(id);
		model.addAttribute("user",user);
		
		BalanceType[] balanceTypes = BalanceType.values();
		Map<BalanceType, Double> balances = new HashMap<BalanceType, Double>();
		for(BalanceType bt : balanceTypes)
		{
			balances.put(bt, this.transactionRecordServiceMgr.getBalance(user, bt));
		}
		model.addAttribute("balances",balances);
		return "/charge/editCredits";
	}
	
	@RequestMapping("updateCredits")
	public ModelAndView updateCredits(TransactionForm transaction, HttpServletRequest request)
	{
		Admin admin = (Admin) request.getSession().getAttribute(Constants.AUTHENTICATION_KEY);
		admin = adminServiceMgr.getAdmin(admin.getId());
		User user = userServiceMgr.getUser(transaction.getUserId());
		TransactionPurpose purpose = transaction.getPurpose();
		Double transactionNum = transaction.getCreditCount();
		BalanceType recordType = transaction.getCreditType();
		TransactionType transactionType = TransactionType.Income;
		if(transactionNum == 0)
		{
			return ajaxDoneError("Can not add 0 credits");
		}
		else if(transactionNum < 0)
		{
			transactionType = TransactionType.Charge_Off;
		}
		
		TransactionRecord record = new TransactionRecord(transactionNum, transactionType, recordType, purpose, user);
		record.setAdmin(admin);
		
		try {
			this.transactionRecordServiceMgr.addRecord(record);
		} catch (ChargeException e) {
			return ajaxDoneError(e.getMessage());
		}
		return ajaxDoneSuccess("Success");
	}

}
