/*******************************************************************************
 * /* Copyright (C) Indicsoft Technologies Pvt Ltd
 * * All Rights Reserved.
 *******************************************************************************/
package com.chest.currency.viewBean;

import java.util.Date;

public class BranchBean {
private int id;
private Integer solId;
private String branch;
private String address;
private String location;
private String city;
private Integer pincode;
private int status;
private Date insertTime;
private Date updateTime;
private String insertBy;
private String updateBy;
private String reasonForDeletion;
private String approvalForDeletion;
private Integer primaryICMC;
private Integer secondaryICMC;


public Integer getPrimaryICMC() {
	return primaryICMC;
}
public void setPrimaryICMC(Integer primaryICMC) {
	this.primaryICMC = primaryICMC;
}
public Integer getSecondaryICMC() {
	return secondaryICMC;
}
public void setSecondaryICMC(Integer secondaryICMC) {
	this.secondaryICMC = secondaryICMC;
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

public Integer getSolId() {
	return solId;
}
public void setSolId(Integer solId) {
	this.solId = solId;
}
public String getBranch() {
	return branch;
}
public void setBranch(String branch) {
	this.branch = branch;
}
public String getAddress() {
	return address;
}
public void setAddress(String address) {
	this.address = address;
}
public String getLocation() {
	return location;
}
public void setLocation(String location) {
	this.location = location;
}
public String getCity() {
	return city;
}
public void setCity(String city) {
	this.city = city;
}

public Integer getPincode() {
	return pincode;
}
public void setPincode(Integer pincode) {
	this.pincode = pincode;
}
public int getStatus() {
	return status;
}
public void setStatus(int status) {
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


}
