/*******************************************************************************
 * /* Copyright (C) Indicsoft Technologies Pvt Ltd
 * * All Rights Reserved.
 *******************************************************************************/
package com.chest.currency.viewBean;

import java.util.Date;

public class IndentRequestBean {
private Integer id;
private Integer denomination;
private Double bundle;
private String bin;
private String insertBy;
private String updateBy;
private Date insertTime;
private Date updateTime;
private int status;
private String description;

public String getBin() {
	return bin;
}
public void setBin(String bin) {
	this.bin = bin;
}
public Integer getId() {
	return id;
}
public void setId(Integer id) {
	this.id = id;
}
public Integer getDenomination() {
	return denomination;
}
public void setDenomination(Integer denomination) {
	this.denomination = denomination;
}


public Double getBundle() {
	return bundle;
}
public void setBundle(Double bundle) {
	this.bundle = bundle;
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

public int getStatus() {
	return status;
}
public Date getUpdateTime() {
	return updateTime;
}
public void setUpdateTime(Date updateTime) {
	this.updateTime = updateTime;
}
public void setStatus(int status) {
	this.status = status;
}
public String getDescription() {
	return description;
}
public void setDescription(String description) {
	this.description = description;
}

}
