/*******************************************************************************
 * /* Copyright (C) Indicsoft Technologies Pvt Ltd
 * * All Rights Reserved.
 *******************************************************************************/
package com.chest.currency.viewBean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ORVBean {
	private int id;
	private Integer solId;
	private String branch;
	private String SR;
	private String sackLock;
	private String vehicle;
    private String vendor;
	private String accountNumber;
	private String custodian;
	private Date insertTime;
    private Date updateTime;
    private String updateBy;
    private String insertBy;

	private List<ORVAllocation> orvAllocationList = new ArrayList<>();

    
	public List<ORVAllocation> getOrvAllocationList() {
		return orvAllocationList;
	}
	public void setOrvAllocationList(List<ORVAllocation> orvAllocationList) {
		this.orvAllocationList = orvAllocationList;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getBranch() {
		return branch;
	}
	public void setBranch(String branch) {
		this.branch = branch;
	}
	public String getSR() {
		return SR;
	}
	public void setSR(String sR) {
		SR = sR;
	}
	public String getSackLock() {
		return sackLock;
	}
	public void setSackLock(String sackLock) {
		this.sackLock = sackLock;
	}
	public String getVehicle() {
		return vehicle;
	}
	public void setVehicle(String vehicle) {
		this.vehicle = vehicle;
	}
	public String getVendor() {
		return vendor;
	}
	public void setVendor(String vendor) {
		this.vendor = vendor;
	}
	
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
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
	public String getCustodian() {
		return custodian;
	}
	public void setCustodian(String custodian) {
		this.custodian = custodian;
	}
	public Integer getSolId() {
		return solId;
	}
	public void setSolId(Integer solId) {
		this.solId = solId;
	}

}
