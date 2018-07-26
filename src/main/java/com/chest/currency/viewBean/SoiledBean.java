/*******************************************************************************
 * /* Copyright (C) Indicsoft Technologies Pvt Ltd
 * * All Rights Reserved.
 *******************************************************************************/
package com.chest.currency.viewBean;

public class SoiledBean {
private int id;
private java.sql.Date orderDate;
private String RemittanceOrderNumber;
private java.sql.Date approvedRemittanceDate;
private String notes;
private String vehicleNumber;
private String location;
private String denomination;
private String bundle;
private String bin;
private String total;
private String box;
private String weight;
private String notesType;

public String getNotesType() {
	return notesType;
}
public void setNotesType(String notesType) {
	this.notesType = notesType;
}
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}

public java.sql.Date getOrderDate() {
	return orderDate;
}
public void setOrderDate(java.sql.Date orderDate) {
	this.orderDate = orderDate;
}
public String getRemittanceOrderNumber() {
	return RemittanceOrderNumber;
}
public void setRemittanceOrderNumber(String remittanceOrderNumber) {
	RemittanceOrderNumber = remittanceOrderNumber;
}

public java.sql.Date getApprovedRemittanceDate() {
	return approvedRemittanceDate;
}
public void setApprovedRemittanceDate(java.sql.Date approvedRemittanceDate) {
	this.approvedRemittanceDate = approvedRemittanceDate;
}
public String getNotes() {
	return notes;
}
public void setNotes(String notes) {
	this.notes = notes;
}
public String getVehicleNumber() {
	return vehicleNumber;
}
public void setVehicleNumber(String vehicleNumber) {
	this.vehicleNumber = vehicleNumber;
}
public String getLocation() {
	return location;
}
public void setLocation(String location) {
	this.location = location;
}
public String getDenomination() {
	return denomination;
}
public void setDenomination(String denomination) {
	this.denomination = denomination;
}
public String getBundle() {
	return bundle;
}
public void setBundle(String bundle) {
	this.bundle = bundle;
}
public String getBin() {
	return bin;
}
public void setBin(String bin) {
	this.bin = bin;
}
public String getTotal() {
	return total;
}
public void setTotal(String total) {
	this.total = total;
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

}
