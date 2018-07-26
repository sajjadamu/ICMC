/*******************************************************************************
 * /* Copyright (C) Indicsoft Technologies Pvt Ltd
 * * All Rights Reserved.
 *******************************************************************************/
package com.chest.currency.viewBean;

import java.util.Date;

public class FreshBean {
private int id;
private int denomination;
private double bundle;
private int total;
private String bin;
private String coins;
private String rbiOrderNo;
private String vehicleNumber;
private String potdarName;
private String escort_officer_name;
private Date order_date;
private String notesOrCoins;
private String filepath;
private String hiddenBinNo;
private Date insertTime;
private Date updateTime;
private String insertBy;
private String updateBy;
private int icmcId;

public int getIcmcId() {
	return icmcId;
}
public void setIcmcId(int icmcId) {
	this.icmcId = icmcId;
}
public String getFilepath() {
	return filepath;
}
public void setFilepath(String filepath) {
	this.filepath = filepath;
}
public String getHiddenBinNo() {
	return hiddenBinNo;
}
public void setHiddenBinNo(String hiddenBinNo) {
	this.hiddenBinNo = hiddenBinNo;
}
public String getNotesOrCoins() {
	return notesOrCoins;
}
public void setNotesOrCoins(String notesOrCoins) {
	this.notesOrCoins = notesOrCoins;
}

public String getRbiOrderNo() {
	return rbiOrderNo;
}
public void setRbiOrderNo(String rbiOrderNo) {
	this.rbiOrderNo = rbiOrderNo;
}
public String getVehicleNumber() {
	return vehicleNumber;
}
public void setVehicleNumber(String vehicleNumber) {
	this.vehicleNumber = vehicleNumber;
}
public String getPotdarName() {
	return potdarName;
}
public void setPotdarName(String potdarName) {
	this.potdarName = potdarName;
}
public String getEscort_officer_name() {
	return escort_officer_name;
}
public void setEscort_officer_name(String escort_officer_name) {
	this.escort_officer_name = escort_officer_name;
}
public String getCoins() {
	return coins;
}
public void setCoins(String coins) {
	this.coins = coins;
}
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}

public String getBin() {
	return bin;
}
public void setBin(String bin) {
	this.bin = bin;
}
public String getInsertBy() {
	return insertBy;
}
public void setInsertBy(String insertBy) {
	this.insertBy = insertBy;
}

public int getDenomination() {
	return denomination;
}
public void setDenomination(int denomination) {
	this.denomination = denomination;
}

public double getBundle() {
	return bundle;
}
public void setBundle(double bundle) {
	this.bundle = bundle;
}
public int getTotal() {
	return total;
}
public void setTotal(int total) {
	this.total = total;
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
public Date getOrder_date() {
	return order_date;
}
public void setOrder_date(Date order_date) {
	this.order_date = order_date;
}

}
