/*******************************************************************************
 * /* Copyright (C) Indicsoft Technologies Pvt Ltd
 * * All Rights Reserved.
 *******************************************************************************/
package com.chest.currency.viewBean;

import java.util.Date;

public class ServicingBranch {
	private int id;
	private String icmcName;
	private Integer solId;
	private String branchName;
	private String rbiJI;
	private String rbiSI;
	private String category;
	private String RBIicmc;
	private String status;
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
	public String getIcmcName() {
		return icmcName;
	}
	public void setIcmcName(String icmcName) {
		this.icmcName = icmcName;
	}
	public Integer getSolId() {
		return solId;
	}
	public void setSolId(Integer solId) {
		this.solId = solId;
	}
	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	public String getRbiJI() {
		return rbiJI;
	}
	public void setRbiJI(String rbiJI) {
		this.rbiJI = rbiJI;
	}
	public String getRbiSI() {
		return rbiSI;
	}
	public void setRbiSI(String rbiSI) {
		this.rbiSI = rbiSI;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getRBIicmc() {
		return RBIicmc;
	}
	public void setRBIicmc(String rBIicmc) {
		RBIicmc = rBIicmc;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
