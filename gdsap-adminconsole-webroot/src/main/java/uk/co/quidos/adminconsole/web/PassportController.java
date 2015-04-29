package uk.co.quidos.adminconsole.web;


import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import uk.co.quidos.common.util.exception.UserException;
import uk.co.quidos.common.util.web.ServerInfo;
import uk.co.quidos.gdsap.framework.authority.Admin;
import uk.co.quidos.gdsap.framework.authority.services.AdminServiceMgr;
import uk.co.quidos.gdsap.framework.user.enums.UserStatus;

@Controller
@RequestMapping("/passport")
public class PassportController extends BaseController {

	@Autowired
	AdminServiceMgr adminServiceMgr;
	
	@RequestMapping("/index")
	public String index(HttpServletRequest request, ModelMap model) {
		if (ServerInfo.isAjax(request)) {
			return "/login_dialog";
		}
		return "/login";
	}

	@RequestMapping("/login")
	public ModelAndView login(@RequestParam("username")String username, @RequestParam("password") String password, HttpServletRequest request) {
		
        if(username == null || username.equals("") || password == null || password.equals("")){
        	return new ModelAndView("login", "error", getMessage("msg.login.failure"));
        }
        
		try {
			Admin user = adminServiceMgr.login(username, password);
			if (user != null && UserStatus.Active.equals(user.getUserStatus())) {
				request.getSession().setAttribute(Constants.AUTHENTICATION_KEY, user);
				
				String backToUrl = request.getParameter("backToUrl");
				if (backToUrl == null || backToUrl.trim().length() == 0) {
					backToUrl = "/";
				} else {
					try {
						backToUrl = java.net.URLDecoder.decode(backToUrl, "UTF-8");
					} catch (UnsupportedEncodingException e) {
						return new ModelAndView("login", "error", getMessage("msg.login.failure"));
					}
				}
	
				System.out.println("backToUrl: " + backToUrl);
				
				return new ModelAndView("redirect:"+backToUrl);
			}
		} catch (UserException e1) {
			return new ModelAndView("login", "error", getMessage("msg.login.failure"));
		}
		
		return new ModelAndView("login", "error", getMessage("msg.login.failure"));
	}

	@RequestMapping("/logout")
	public ModelAndView logout(HttpServletRequest request) {

		request.getSession().removeAttribute(Constants.AUTHENTICATION_KEY);

		return new ModelAndView("/login");
	}
}