/*******************************************************************************
 * /* Copyright (C) Indicsoft Technologies Pvt Ltd
 * * All Rights Reserved.
 *******************************************************************************/
package com.chest.currency.viewBean;

import java.util.Date;

public class BinCapacity {
private int id;
private Integer binCapacity;
private Integer denomination;
private Date insertTime;
private Date updateTime;
private String updateBy;
private String insertBy;
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public Integer getBinCapacity() {
	return binCapacity;
}
public void setBinCapacity(Integer binCapacity) {
	this.binCapacity = binCapacity;
}
public Integer getDenomination() {
	return denomination;
}
public void setDenomination(Integer denomination) {
	this.denomination = denomination;
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
