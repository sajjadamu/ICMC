package com.chest.currency.util;

import java.math.BigInteger;
import java.util.Calendar;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.chest.currency.entity.model.ICMC;
import com.chest.currency.entity.model.LamRequestLog;
import com.chest.currency.entity.model.Role;
import com.chest.currency.entity.model.User;
import com.chest.currency.entity.model.co.QueryRequestCo;
import com.chest.currency.enums.Activity;
import com.chest.currency.enums.Status;

public class UtilityService {

	private static final Logger LOG = LoggerFactory.getLogger(UtilityService.class);
	static PasswordEncoder psw = new BCryptPasswordEncoder();

	public static User setUserDetail(QueryRequestCo queryRequest, ICMC icmc) {
		User user = new User();
		String accessRequest = queryRequest.getAccessRequest();
		// String additionalDetail = queryRequest.getAdditionalDetail();
		String[] userDetails = accessRequest.split(Pattern.quote("|"));

		user.setCreatedBy(userDetails[0]);
		user.setUpdatedBy(userDetails[0]);
		user.setId(userDetails[2]);
		user.setName(userDetails[3]);
		user.setEmail(userDetails[6]);
		user.setCreatedDateTime(Calendar.getInstance());
		user.setUpdatedDateTime(Calendar.getInstance());
		if (queryRequest.getRoles() != null) {
			String roles = queryRequest.getRoles();
			String[] roleName = roles.split(Pattern.quote("|"));
			String getRole = null;
			if (roleName.length == 2) {
				getRole = roleName[1];
			} else {
				getRole = roleName[2];
			}
			Role role = new Role();
			role.setId(getRole);
			user.setRole(role);
		}
		user.setPassword(psw.encode("null"));
		user.setZoneId(icmc.getZone());
		user.setRegionId(icmc.getRegion());
		user.setStatus(Status.ENABLED);
		user.setIcmcId(BigInteger.valueOf(icmc.getId()));
		LOG.info("set user detail " + user);
		return user;
	}

	public static LamRequestLog setLamRequestLog(QueryRequestCo queryRequest, HttpServletRequest request) {
		LamRequestLog requestLog = new LamRequestLog();

		requestLog.setAccesRequest(queryRequest.getAccessRequest());
		requestLog.setRoles(queryRequest.getRoles());
		requestLog.setAdditionalDetails(queryRequest.getAdditionalDetail());
		requestLog.setRequestUrl(request.getRequestURI().toString());
		requestLog.setCreatedDateTime(Calendar.getInstance());
		requestLog.setUpdatedDateTime(Calendar.getInstance());
		if (null != queryRequest.getActivity())
			requestLog.setActivity((Activity.valueOf(queryRequest.getActivity())));
		LOG.info("set lam requestLog " + requestLog);

		return requestLog;
	}

}
