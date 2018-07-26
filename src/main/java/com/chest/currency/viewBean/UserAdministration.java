/*******************************************************************************
 * /* Copyright (C) Indicsoft Technologies Pvt Ltd
 * * All Rights Reserved.
 *******************************************************************************/
package com.chest.currency.viewBean;

import java.util.Date;

public class UserAdministration {
	private int id;
	private String reportType;
	private String reportContent;
	private String zone;
	private String region;
	private String icmcName;
	private String binName;
	private String profile;
	private String rights;
	private String userId;
	private String insertBy;
	private String updateBy;
	private Date insertTime;
	private Date updateTime;
	private String regionForDeletion;
	private String approvalForDeletion;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getReportType() {
		return reportType;
	}
	public void setReportType(String reportType) {
		this.reportType = reportType;
	}
	public String getReportContent() {
		return reportContent;
	}
	public void setReportContent(String reportContent) {
		this.reportContent = reportContent;
	}
	public String getZone() {
		return zone;
	}
	public void setZone(String zone) {
		this.zone = zone;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getIcmcName() {
		return icmcName;
	}
	public void setIcmcName(String icmcName) {
		this.icmcName = icmcName;
	}
	public String getBinName() {
		return binName;
	}
	public void setBinName(String binName) {
		this.binName = binName;
	}
	public String getProfile() {
		return profile;
	}
	public void setProfile(String profile) {
		this.profile = profile;
	}
	public String getRights() {
		return rights;
	}
	public void setRights(String rights) {
		this.rights = rights;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getInsertBy() {
		return insertBy;
	}
	public void setInsertBy(String insertBy) {
		this.insertBy = insertBy;
	}
	public String getUpdateBy() {
		return updateBy;
	}
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}
	public Date getInsertTime() {
		return insertTime;
	}
	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public String getRegionForDeletion() {
		return regionForDeletion;
	}
	public void setRegionForDeletion(String regionForDeletion) {
		this.regionForDeletion = regionForDeletion;
	}
	public String getApprovalForDeletion() {
		return approvalForDeletion;
	}
	public void setApprovalForDeletion(String approvalForDeletion) {
		this.approvalForDeletion = approvalForDeletion;
	}
}
