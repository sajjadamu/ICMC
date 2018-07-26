/*******************************************************************************
 * /* Copyright (C) Indicsoft Technologies Pvt Ltd
 * * All Rights Reserved.
 *******************************************************************************/
package com.chest.currency.viewBean;

import java.util.Date;

public class BinTransaction {
private int id;
private String binNumber;
private int denomination;
private int maxCapacity;
private double currentBundles;
private Date insertTime;
private double receiveBundles;
private String binType;
private Date updateTime;
private String insertBy;
private String updateBy;
private String colorCode;
private Integer ICMCId;


public Integer getICMCId() {
	return ICMCId;
}
public void setICMCId(Integer iCMCId) {
	ICMCId = iCMCId;
}
public String getColorCode() {
	return colorCode;
}
public void setColorCode(String colorCode) {
	this.colorCode = colorCode;
}
private int status;

public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getBinNumber() {
	return binNumber;
}
public void setBinNumber(String binNumber) {
	this.binNumber = binNumber;
}
public int getDenomination() {
	return denomination;
}
public void setDenomination(int denomination) {
	this.denomination = denomination;
}
public int getMaxCapacity() {
	return maxCapacity;
}
public void setMaxCapacity(int maxCapacity) {
	this.maxCapacity = maxCapacity;
}

public Date getInsertTime() {
	return insertTime;
}
public void setInsertTime(Date insertTime) {
	this.insertTime = insertTime;
}
public double getReceiveBundles() {
	return receiveBundles;
}
public void setReceiveBundles(double receiveBundles) {
	this.receiveBundles = receiveBundles;
}
public String getBinType() {
	return binType;
}
public void setBinType(String binType) {
	this.binType = binType;
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
public double getCurrentBundles() {
	return currentBundles;
}
public void setCurrentBundles(double currentBundles) {
	this.currentBundles = currentBundles;
}
public int getStatus() {
	return status;
}
public void setStatus(int status) {
	this.status = status;
}

}
