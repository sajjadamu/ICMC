package com.chest.currency.entity.model.co;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;

@XmlRootElement(name = "queryrequest")
public class QueryRequestCo {

	@NotEmpty
	@NotBlank
	@XmlElement(name = "Activity")
	@JsonProperty("Activity")
	private String Activity;

	@NotEmpty
	@NotBlank
	@XmlElement(name = "AccessRequest")
	@JsonProperty("AccessRequest")
	private String AccessRequest;

	@XmlElement(name = "Roles")
	@JsonProperty("Roles")
	private String Roles;

	@XmlElement(name = "AdditionalDetail")
	@JsonProperty("AdditionalDetail")
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

	public Map<String, ?> getQueryRequest() {
		Map<String, Object> params = new LinkedHashMap<>();

		params.put("Activity", this.Activity);
		params.put("AccessRequest", this.AccessRequest);
		params.put("Roles", this.Roles);
		params.put("AdditionalDetail", this.AdditionalDetail);

		return params;
	}

}
