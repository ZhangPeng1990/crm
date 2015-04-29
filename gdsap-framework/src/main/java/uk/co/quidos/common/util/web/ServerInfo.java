package uk.co.quidos.common.util.web;


import java.util.Collection;

import uk.co.quidos.common.context.AppContextHolder;
import uk.co.quidos.gdsap.framework.authority.Admin;
import javax.servlet.http.HttpServletRequest;


public class ServerInfo {
	public static boolean contains(Object o, Collection<?> c) {
		if (c == null) {
			return false;
		}

		return c.contains(o);
	}

	public static Long randomLong(Long start, Long end) {
		return Math.round(Math.random() * (end - start)) + start;
	}

	public static boolean isAjax(HttpServletRequest request) {
		if (request != null && "XMLHttpRequest".equalsIgnoreCase(request.getHeader("X-Requested-With")))
			return true;
		return false;
	}

	public static Admin getUser() {

		return AppContextHolder.getContext().getUser();
	}

	public static boolean isQa() {
//		Admin user = getUser();
//		if (user != null && UserType.QA.equals(user.getUserType()))
//			return true;
		return false;
	}

//	public static String getWebsiteServer() {
//		return getConfigStr(ConfigurationRefId.APP_SERVER_WWW);
//	}
//
//	public static String getAdminServer() {
//		return getConfigStr(ConfigurationRefId.APP_SERVER_IADMIN);
//	}
//
//	public static String getAuditorServer() {
//		return getConfigStr(ConfigurationRefId.APP_SERVER_AUDITOR);
//	}
//
//	public static String getStaticContentUri() {
//		return getConfigStr(ConfigurationRefId.APP_STATIC_ROOTURL);
//	}
//
//	private static String getConfigStr(ConfigurationRefId refId) {
//		ConfigurationServiceMgr confMgr = BusinessFactory.getInstance()
//				.getService(ConfigurationServiceMgr.SERVICE_NAME);
//		return confMgr.getSystemConfig(refId).getString();
//	}

}
