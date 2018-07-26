/*******************************************************************************
 * /* Copyright (C) Indicsoft Technologies Pvt Ltd
 * * All Rights Reserved.
 *******************************************************************************/
package com.chest.currency.viewBean;

import java.util.Date;

public class ICMCReport {
	private int id;
	private int reportId;
	private String newReportType;
	private String[] reportType;
	private String reportContent;
	private String reportTypeDescription;
	private String regionForDeletion;
	private String approvalForDeletion;
	private String insertBy;
	private String updateBy;
	private Date insertTime;
	private Date updateTime;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getReportId() {
		return reportId;
	}
	public void setReportId(int reportId) {
		this.reportId = reportId;
	}
	public String[] getReportType() {
		return reportType;
	}
	public void setReportType(String[] reportType) {
		this.reportType = reportType;
	}
	public String getNewReportType() {
		return newReportType;
	}
	public void setNewReportType(String newReportType) {
		this.newReportType = newReportType;
	}
	public String getReportContent() {
		return reportContent;
	}
	public void setReportContent(String reportContent) {
		this.reportContent = reportContent;
	}
	public String getReportTypeDescription() {
		return reportTypeDescription;
	}
	public void setReportTypeDescription(String reportTypeDescription) {
		this.reportTypeDescription = reportTypeDescription;
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
}
