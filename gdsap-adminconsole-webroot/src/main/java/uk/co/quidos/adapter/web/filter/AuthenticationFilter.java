package uk.co.quidos.adapter.web.filter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import uk.co.quidos.adminconsole.web.Constants;
import uk.co.quidos.common.context.AppContextHolder;
import uk.co.quidos.common.util.web.ServerInfo;
import uk.co.quidos.gdsap.framework.authority.Admin;
import uk.co.quidos.gdsap.framework.user.enums.UserStatus;



/**
 * 系统权限验证Filter，主要用户登录状态是否过期
 * @author peng.shi
 */
public class AuthenticationFilter implements Filter
{

	private String[] unsecureUris = null;

	private static final String PARAM_URI = "uri";
	private static final String DEBUG = "debug";
	private static final String LOGIN_URL = "loginUrl";
	private static final String BACK_TO_URL = "backToUrl";

	private boolean debug = false;
	private FilterConfig config = null;
	private String loginUrl = null;

	private void log(String info) {
		this.config.getServletContext().log(info);
	}
	
	private boolean validate(HttpSession session) {
		Admin user = (Admin)session.getAttribute(Constants.AUTHENTICATION_KEY);
		
		if (user != null && (UserStatus.Active.equals(user.getUserStatus()))) {
			return true;
		}
		
		return false;
	}
	
	public void destroy() {
		this.unsecureUris = null;
		this.config = null;
	}

	public void doFilter(ServletRequest servletRequest,
			ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpSession session = request.getSession();
		
		String queryString = request.getQueryString();
		String requestUri = request.getRequestURI();

		System.out.println(requestUri+"?"+queryString);
		for (String unsecureUri : this.unsecureUris) {
			String unsecureQuery = null;
			String uri = request.getContextPath()+unsecureUri;
			boolean unsecure = false;

			if (unsecureUri.indexOf("?") >= 0) {
				uri = unsecureUri.substring(0, unsecureUri.indexOf("?"));
				unsecureQuery = unsecureUri
						.substring(unsecureUri.indexOf("?") + 1);
				unsecure = requestUri.startsWith(uri)
						&& queryString.indexOf(unsecureQuery) >= 0;
			} else {
				unsecure = requestUri.startsWith(uri);
			}

			if (unsecure) {
				if (debug) {
					this.log("uri " + requestUri + " will not be protected.");
				}
				
				filterChain.doFilter(servletRequest, servletResponse);

				return;
			}
		}

		if (debug) this.log("validate authentication.");
		if (!validate(session)) {

			HttpServletResponse response = (HttpServletResponse) servletResponse;
			StringBuffer sb = request.getRequestURL();
			String query = request.getQueryString();
			if (query != null && !"".equals(query)) {
				sb.append('?').append(query);
			}

			String backToUrl = sb.toString();
			System.out.println("backToUrl = "+backToUrl);
			if (ServerInfo.isAjax(request)) {
				PrintWriter out = servletResponse.getWriter();
				out.println("{\"statusCode\":\"301\", \"message\":\"Session Timeout! Please re-sign in!\"}");
			} else {
				request.setAttribute("statusCode", 301); // for iframeDone callback
				response.sendRedirect(response.encodeRedirectURL(request.getContextPath()+this.loginUrl + java.net.URLEncoder.encode(backToUrl, "UTF-8")));
			}
			return;
		}

		Admin user = (Admin)session.getAttribute(Constants.AUTHENTICATION_KEY);
		AppContextHolder.getContext().setUser(user);

		if (debug) this.log("validate authentication finished, the authentication has permission to enter this uri.");
		
		filterChain.doFilter(request, servletResponse);
	}

	public void init(FilterConfig config) throws ServletException {
		this.config = config;
		String uri = config.getInitParameter(PARAM_URI);
		if (uri != null) {
			this.unsecureUris = uri.split(",");
			for (int i = 0; i < this.unsecureUris.length; i++) {
				if (this.unsecureUris[i] == null
						|| "".equals(this.unsecureUris[i].trim())) {
					continue;
				}

				this.unsecureUris[i] = this.unsecureUris[i].trim();
			}
		}

		this.debug = Boolean.parseBoolean(config.getInitParameter(DEBUG));

		if (config.getInitParameter(LOGIN_URL) != null) {
			StringBuffer sb = new StringBuffer(config
					.getInitParameter(LOGIN_URL));
			if (sb.indexOf("?") >= 0) {
				sb.append("&");
			} else {
				sb.append("?");
			}
			sb.append(BACK_TO_URL).append("=");
			this.loginUrl  = sb.toString();
		}
	
	}

}
