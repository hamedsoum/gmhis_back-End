package com.gmhis_backk;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.context.SecurityContextHolder;

public class AppUtils {
	public static String getBaseRoute(HttpServletRequest request) {
		String route = request.getRequestURI();
		String baseContext = request.getServletContext().getContextPath();
		String[] sentences = route.split("/\\d+");
		String realRoute = StringUtils.remove(sentences[0], baseContext);
		return realRoute;
	}
	
	public static String getUsername() {
		return SecurityContextHolder.getContext().getAuthentication().getName();
	}

}
