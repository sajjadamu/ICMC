package com.chest.currency.entity.model.co;

import java.util.LinkedHashMap;
import java.util.Map;

public class QueryRequestCo {

//	QueryRequestCo queryrequest;

	private String Activity;

	private String AccessRequest;

	private String Roles;

	private String AdditionalDetail;


	public String getActivity() {
		return Activity;
	}

	public void setActivity(String activity) {
		Activity = activity;
	}

	public String getAccessRequest() {
		return AccessRequest;
	}

	public void setAccessRequest(String accessRequest) {
		AccessRequest = accessRequest;
	}

	public String getRoles() {
		return Roles;
	}

	public void setRoles(String roles) {
		Roles = roles;
	}

	public String getAdditionalDetail() {
		return AdditionalDetail;
	}

	public void setAdditionalDetail(String additionalDetail) {
		AdditionalDetail = additionalDetail;
	}

	/*
	 * public QueryRequestCo getQueryrequest() { return queryrequest; }
	 * 
	 * public void setQueryrequest(QueryRequestCo queryrequest) { this.queryrequest
	 * = queryrequest; }
	 */
	public Map<String, ?> getQueryRequest() {
		Map<String, Object> params = new LinkedHashMap<>();

		params.put("activity", this.Activity);
		params.put("accessRequest", this.AccessRequest);
		params.put("roles", this.Roles);
		params.put("additionalDetail", this.AdditionalDetail);

		return params;
	}

	/*
	 * public Map<String, ?> getQueryRequest() { Map<String, Object> params = new
	 * LinkedHashMap<>();
	 * 
	 * params.put("queryrequest", this.getQueryrequest().getRequest());
	 * 
	 * return params; }
	 */

}
