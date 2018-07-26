/*******************************************************************************
 * /* Copyright (C) Indicsoft Technologies Pvt Ltd
 * * All Rights Reserved.
 *******************************************************************************/
package com.chest.currency.viewBean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DorvBean {
	
	private int id;
	private java.sql.Date orderDate;
	private String RBIOrderNumber;
	private java.sql.Date expiryDate;
	private String bankName;
	private String approvedCC;
	private String location;
	
	private Date insertTime;
	private Date updateTime;
	private String insertBy;
	private String updateBy;
	
	private List<DorvAllocation> diversionAllocationList = new ArrayList<>();
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getRBIOrderNumber() {
		return RBIOrderNumber;
	}
	public void setRBIOrderNumber(String rBIOrderNumber) {
		RBIOrderNumber = rBIOrderNumber;
	}
	
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getApprovedCC() {
		return approvedCC;
	}
	public void setApprovedCC(String approvedCC) {
		this.approvedCC = approvedCC;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
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
	public List<DorvAllocation> getDiversionAllocationList() {
		return diversionAllocationList;
	}
	public void setDiversionAllocationList(List<DorvAllocation> diversionAllocationList) {
		this.diversionAllocationList = diversionAllocationList;
	}
	public java.sql.Date getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(java.sql.Date orderDate) {
		this.orderDate = orderDate;
	}
	public java.sql.Date getExpiryDate() {
		return expiryDate;
	}
	public void setExpiryDate(java.sql.Date expiryDate) {
		this.expiryDate = expiryDate;
	}
	
}