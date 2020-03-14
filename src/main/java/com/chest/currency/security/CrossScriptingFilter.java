package com.chest.currency.security;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("XSS")
public class CrossScriptingFilter implements Filter {

	
	private static Logger logger = LoggerFactory.getLogger(CrossScriptingFilter.class);

	private FilterConfig filterConfig;

	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;	
	}

	public void destroy() {
		this.filterConfig = null;
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		logger.info("Inlter CrossScriptingFilter  ..............." + request);
		chain.doFilter(new RequestWrapper((HttpServletRequest) request), response);
		
		
	/*	HttpServletRequest req = (HttpServletRequest) request;
		logger.info("Outlter CrossScriptingFilter ..............." + req.getRequestURL());
		logger.info("Outlter getRequestURI ..............." + req.getRequestURI());
		logger.info("Outlter CrossScriptingFilter ..............." + request.getReader());
		try {
			HttpSession session = req.getSession();
			User user = (User) session.getAttribute("login");
			if (user != null) {
				Audit audit = new Audit();
				audit.setUserId(user.getId());
				audit.setUrl(req.getRequestURI());
				audit.setInsertTime(Calendar.getInstance());
				if (user.getIcmcId() != null)
					audit.setIcmcId(user.getIcmcId());
				// BinDashboardServiceImpl binDashboardService = new
				// BinDashboardServiceImpl();
				//binDashboardService.saveAudit(audit);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		
		

	}
}
