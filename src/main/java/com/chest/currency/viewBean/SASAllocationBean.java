/*******************************************************************************
 * /* Copyright (C) Indicsoft Technologies Pvt Ltd
 * * All Rights Reserved.
 *******************************************************************************/
package com.chest.currency.viewBean;

import java.util.Date;

public class SASAllocationBean {
private int id;
private String bin;
private int denomination;
private int categoryFresh;
private int categoryIssubale;
private Date insertTime;
private Date updateTime;
private String insertBy;
private String updateBy;

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
public int getDenomination() {
	return denomination;
}
public void setDenomination(int denomination) {
	this.denomination = denomination;
}

public int getCategoryFresh() {
	return categoryFresh;
}
public void setCategoryFresh(int categoryFresh) {
	this.categoryFresh = categoryFresh;
}
public int getCategoryIssubale() {
	return categoryIssubale;
}
public void setCategoryIssubale(int categoryIssubale) {
	this.categoryIssubale = categoryIssubale;
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
