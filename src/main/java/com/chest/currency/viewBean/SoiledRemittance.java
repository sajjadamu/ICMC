/*******************************************************************************
 * /* Copyright (C) Indicsoft Technologies Pvt Ltd
 * * All Rights Reserved.
 *******************************************************************************/
package com.chest.currency.viewBean;

import java.util.ArrayList;
import java.util.List;

public class SoiledRemittance {
	private int id;
	private java.sql.Date orderDate;
	private String remittanceOrderNumber;
	private java.sql.Date approvedRemmitanceDate;
	private String notes;
	private String type;
	private String vehicleNumber;
	private String location;
	private String insertTime;
	private String updateTime;
	private String insertBy;
	private String updateBy;
	
	
	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	private List<SoiledRemittanceAllocation> soiledAllocationList = new ArrayList<>();

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	
	public String getRemittanceOrderNumber() {
		return remittanceOrderNumber;
	}

	public void setRemittanceOrderNumber(String remittanceOrderNumber) {
		this.remittanceOrderNumber = remittanceOrderNumber;
	}

	public java.sql.Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(java.sql.Date orderDate) {
		this.orderDate = orderDate;
	}

	public java.sql.Date getApprovedRemmitanceDate() {
		return approvedRemmitanceDate;
	}

	public void setApprovedRemmitanceDate(java.sql.Date approvedRemmitanceDate) {
		this.approvedRemmitanceDate = approvedRemmitanceDate;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(String insertTime) {
		this.insertTime = insertTime;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getInsertBy() {
		return insertBy;
	}

	public void setInsertBy(String insertBy) {
		this.insertBy = insertBy;
	}

	public List<SoiledRemittanceAllocation> getSoiledAllocationList() {
		return soiledAllocationList;
	}

	public void setSoiledAllocationList(List<SoiledRemittanceAllocation> soiledAllocationList) {
		this.soiledAllocationList = soiledAllocationList;
	}

	public String getVehicleNumber() {
		return vehicleNumber;
	}

	public void setVehicleNumber(String vehicleNumber) {
		this.vehicleNumber = vehicleNumber;
	}

}
