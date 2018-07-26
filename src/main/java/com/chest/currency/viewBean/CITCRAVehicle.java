/*******************************************************************************
 * /* Copyright (C) Indicsoft Technologies Pvt Ltd
 * * All Rights Reserved.
 *******************************************************************************/
package com.chest.currency.viewBean;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class CITCRAVehicle {
	private int id;
	private String name;
	private String number;
	
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date boughtDate;

	private String regCityName;
	private String insurance;
	
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date fitnessExpiryDate;
	
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date pollutionExpiryDate;
	
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date permitDate;
	
	private Date insertTime;
	private Date updateTime;
	private String updateBy;
	private String insertBy;
   
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public Date getBoughtDate() {
		return boughtDate;
	}

	public void setBoughtDate(Date boughtDate) {
		this.boughtDate = boughtDate;
	}

	public String getRegCityName() {
		return regCityName;
	}

	public void setRegCityName(String regCityName) {
		this.regCityName = regCityName;
	}

	public String getInsurance() {
		return insurance;
	}

	public void setInsurance(String insurance) {
		this.insurance = insurance;
	}

	public Date getFitnessExpiryDate() {
		return fitnessExpiryDate;
	}

	public void setFitnessExpiryDate(Date fitnessExpiryDate) {
		this.fitnessExpiryDate = fitnessExpiryDate;
	}

	public Date getPollutionExpiryDate() {
		return pollutionExpiryDate;
	}

	public void setPollutionExpiryDate(Date pollutionExpiryDate) {
		this.pollutionExpiryDate = pollutionExpiryDate;
	}

	public Date getPermitDate() {
		return permitDate;
	}

	public void setPermitDate(Date permitDate) {
		this.permitDate = permitDate;
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
