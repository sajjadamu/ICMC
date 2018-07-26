/*******************************************************************************
 * /* Copyright (C) Indicsoft Technologies Pvt Ltd
 * * All Rights Reserved.
 *******************************************************************************/
package com.chest.currency.viewBean;

import java.util.Date;

public class SoiledRemittanceAllocation {
private int id;
private int soiledRemittanceId;
private int denomination;
private int bundle;
private int total;
private String bin;
private String box;
private String weight;
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

public int getSoiledRemittanceId() {
	return soiledRemittanceId;
}
public void setSoiledRemittanceId(int soiledRemittanceId) {
	this.soiledRemittanceId = soiledRemittanceId;
}
public int getDenomination() {
	return denomination;
}
public void setDenomination(int denomination) {
	this.denomination = denomination;
}
public int getBundle() {
	return bundle;
}
public void setBundle(int bundle) {
	this.bundle = bundle;
}
public int getTotal() {
	return total;
}
public void setTotal(int total) {
	this.total = total;
}
public String getBin() {
	return bin;
}
public void setBin(String bin) {
	this.bin = bin;
}
public String getBox() {
	return box;
}
public void setBox(String box) {
	this.box = box;
}
public String getWeight() {
	return weight;
}
public void setWeight(String weight) {
	this.weight = weight;
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
