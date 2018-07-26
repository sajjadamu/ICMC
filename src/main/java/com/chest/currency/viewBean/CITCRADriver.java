/*******************************************************************************
 * /* Copyright (C) Indicsoft Technologies Pvt Ltd
 * * All Rights Reserved.
 *******************************************************************************/
package com.chest.currency.viewBean;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class CITCRADriver {
	public int id;
	private String vendorName;
	private String vehicleNumber;
	private String driverName;
	private String licenseNumber;
	private String licenseIssuedState;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date licenseIssuedDated;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date licenseExpiryDate;
	private String status;
	private String insertBy;
	private String updateBy;
	private Date insertTime;
	private Date updateTime;
	private String reasonForDeletion;
	private String approvalForDeletion;
	
	public String getReasonForDeletion() {
		return reasonForDeletion;
	}
	public void setReasonForDeletion(String reasonForDeletion) {
		this.reasonForDeletion = reasonForDeletion;
	}
	public String getApprovalForDeletion() {
		return approvalForDeletion;
	}
	public void setApprovalForDeletion(String approvalForDeletion) {
		this.approvalForDeletion = approvalForDeletion;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getVendorName() {
		return vendorName;
	}
	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}
	public String getVehicleNumber() {
		return vehicleNumber;
	}
	public void setVehicleNumber(String vehicleNumber) {
		this.vehicleNumber = vehicleNumber;
	}
	public String getDriverName() {
		return driverName;
	}
	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}
	public String getLicenseNumber() {
		return licenseNumber;
	}
	public void setLicenseNumber(String licenseNumber) {
		this.licenseNumber = licenseNumber;
	}
	public String getLicenseIssuedState() {
		return licenseIssuedState;
	}
	public void setLicenseIssuedState(String licenseIssuedState) {
		this.licenseIssuedState = licenseIssuedState;
	}

	public Date getLicenseIssuedDated() {
		return licenseIssuedDated;
	}
	public void setLicenseIssuedDated(Date licenseIssuedDated) {
		this.licenseIssuedDated = licenseIssuedDated;
	}
	public Date getLicenseExpiryDate() {
		return licenseExpiryDate;
	}
	public void setLicenseExpiryDate(Date licenseExpiryDate) {
		this.licenseExpiryDate = licenseExpiryDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
