/*******************************************************************************
 * /* Copyright (C) Indicsoft Technologies Pvt Ltd
 * * All Rights Reserved.
 *******************************************************************************/
package com.chest.currency.viewBean;

import java.util.Date;

public class CITCRAVendor {
	private int id;
	private String name;
	private String typeOne;
	private String typeTwo;
	private String typeThree;
	private String FPRName;
	private String FPRNumber;
	private String status;
	private Date insertTime;
	private Date updateTime;
	private String updateBy;
	private String insertBy;
	private String reasonForDeletion;
	private String approvalForDeletion;
	private String zone;
	private String region;
	private String[] icmc;

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

	public String[] getIcmc() {
		return icmc;
	}

	public void setIcmc(String[] icmc) {
		this.icmc = icmc;
	}

	public String getReasonForDeletion() {
		return reasonForDeletion;
	}

	public void setReasonForDeletion(String reasonForDeletion) {
		this.reasonForDeletion = reasonForDeletion;
	}

	public String getApprovalForDeletion() {
		return approvalForDeletion;
	}

	public void setApprovalForDeletion(String approvalForDeletion) {
		this.approvalForDeletion = approvalForDeletion;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTypeOne() {
		return typeOne;
	}

	public void setTypeOne(String typeOne) {
		this.typeOne = typeOne;
	}

	public String getTypeTwo() {
		return typeTwo;
	}

	public void setTypeTwo(String typeTwo) {
		this.typeTwo = typeTwo;
	}

	public String getTypeThree() {
		return typeThree;
	}

	public void setTypeThree(String typeThree) {
		this.typeThree = typeThree;
	}

	public String getFPRName() {
		return FPRName;
	}

	public void setFPRName(String fPRName) {
		FPRName = fPRName;
	}

	public String getFPRNumber() {
		return FPRNumber;
	}

	public void setFPRNumber(String fPRNumber) {
		FPRNumber = fPRNumber;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public String getInsertBy() {
		return insertBy;
	}

	public void setInsertBy(String insertBy) {
		this.insertBy = insertBy;
	}

}
