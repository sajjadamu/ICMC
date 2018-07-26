/*******************************************************************************
 * /* Copyright (C) Indicsoft Technologies Pvt Ltd
 * * All Rights Reserved.
 *******************************************************************************/
package com.chest.currency.viewBean;

import java.util.Date;

public class MachineAllocationBean {
private int id;
private int denomination;
private double bundle;
private Double issued_bundle;
private int machine;
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
public Double getIssued_bundle() {
	return issued_bundle;
}
public void setIssued_bundle(Double issued_bundle) {
	this.issued_bundle = issued_bundle;
}
public int getMachine() {
	return machine;
}
public void setMachine(int machine) {
	this.machine = machine;
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