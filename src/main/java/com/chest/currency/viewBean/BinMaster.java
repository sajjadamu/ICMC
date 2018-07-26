/*******************************************************************************
 * /* Copyright (C) Indicsoft Technologies Pvt Ltd
 * * All Rights Reserved.
 *******************************************************************************/
package com.chest.currency.viewBean;

import java.util.Date;

public class BinMaster {
	
private int id;
private String firstPriority;
private String secondPriority;
private String thirdPriority;
private int locationPriority;
private String binNumber;
private int isAllocated;
private int maxBundleCapacity;
private int denom;
private Date insertTime;
private Date updateTime;
private String updateBy;
private String insertBy;
private String branch;
private Integer ICMCId;


public Integer getICMCId() {
	return ICMCId;
}
public void setICMCId(Integer iCMCId) {
	ICMCId = iCMCId;
}
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getFirstPriority() {
	return firstPriority;
}
public void setFirstPriority(String firstPriority) {
	this.firstPriority = firstPriority;
}
public String getSecondPriority() {
	return secondPriority;
}
public void setSecondPriority(String secondPriority) {
	this.secondPriority = secondPriority;
}
public String getThirdPriority() {
	return thirdPriority;
}
public void setThirdPriority(String thirdPriority) {
	this.thirdPriority = thirdPriority;
}
public int getLocationPriority() {
	return locationPriority;
}
public void setLocationPriority(int locationPriority) {
	this.locationPriority = locationPriority;
}
public String getBinNumber() {
	return binNumber;
}
public void setBinNumber(String binNumber) {
	this.binNumber = binNumber;
}
public int getIsAllocated() {
	return isAllocated;
}
public void setIsAllocated(int isAllocated) {
	this.isAllocated = isAllocated;
}
public int getDenom() {
	return denom;
}
public void setDenom(int denom) {
	this.denom = denom;
}
public int getMaxBundleCapacity() {
	return maxBundleCapacity;
}
public void setMaxBundleCapacity(int maxBundleCapacity) {
	this.maxBundleCapacity = maxBundleCapacity;
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
public String getBranch() {
	return branch;
}
public void setBranch(String branch) {
	this.branch = branch;
}

}
