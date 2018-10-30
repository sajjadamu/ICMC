package com.chest.currency.entity.model.co;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.chest.currency.enums.Activity;

public class QueryRequestCo {

	private Activity activity;

	@NotNull
	@NotBlank
	private String accessRequest;

	@NotNull
	@NotBlank
	private String roles;

	@NotNull
	@NotBlank
	private String additionalDetail;

	public Activity getActivity() {
		return activity;
	}

	public void setActivity(Activity activity) {
		this.activity = activity;
	}

	public String getAccessRequest() {
		return accessRequest;
	}

	public void setAccessRequest(String accessRequest) {
		this.accessRequest = accessRequest;
	}

	public String getRoles() {
		return roles;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}

	public String getAdditionalDetail() {
		return additionalDetail;
	}

	public void setAdditionalDetail(String additionalDetail) {
		this.additionalDetail = additionalDetail;
	}

	public Map<String, ?> getQueryRequest() {
		Map<String, Object> params = new LinkedHashMap<>();

		params.put("activity", this.activity);
		params.put("accessRequest", this.accessRequest);
		params.put("roles", this.roles);
		params.put("additionalDetail", this.additionalDetail);

		return params;
	}

}
