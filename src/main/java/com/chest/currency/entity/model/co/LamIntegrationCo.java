package com.chest.currency.entity.model.co;

import java.util.LinkedHashMap;
import java.util.Map;


public class LamIntegrationCo {

	private QueryRequestCo queryrequest;

	public QueryRequestCo getQueryrequest() {
		return queryrequest;
	}

	public void setQueryrequest(QueryRequestCo queryrequest) {
		this.queryrequest = queryrequest;
	}

	public Map<String, Object> getQueryRequest() {
		Map<String, Object> params = new LinkedHashMap<>();

		params.put("queryrequest", this.queryrequest.getQueryRequest());

		return params;
	}

}
