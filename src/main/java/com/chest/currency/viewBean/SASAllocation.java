/*******************************************************************************
 * /* Copyright (C) Indicsoft Technologies Pvt Ltd
 * * All Rights Reserved.
 *******************************************************************************/
package com.chest.currency.viewBean;

import java.util.Date;

public class SASAllocation {
	
	private int id;
	private String freshBin;
	private String issuableBin;
	private int categoryFresh;
	private int categoryIssuable;
	private int denomination;
	private Date insertTime;
	private Date updateTime;
	private String insertBy;
	private String updateBy;
	private int solId;
	
	public int getSolId() {
		return solId;
	}
	public void setSolId(int solId) {
		this.solId = solId;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	
	public String getFreshBin() {
		return freshBin;
	}
	public void setFreshBin(String freshBin) {
		this.freshBin = freshBin;
	}
	public String getIssuableBin() {
		return issuableBin;
	}
	public void setIssuableBin(String issuableBin) {
		this.issuableBin = issuableBin;
	}
	public int getCategoryFresh() {
		return categoryFresh;
	}
	public void setCategoryFresh(int categoryFresh) {
		this.categoryFresh = categoryFresh;
	}
	public int getCategoryIssuable() {
		return categoryIssuable;
	}
	public void setCategoryIssuable(int categoryIssuable) {
		this.categoryIssuable = categoryIssuable;
	}
	public int getDenomination() {
		return denomination;
	}
	public void setDenomination(int denomination) {
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
