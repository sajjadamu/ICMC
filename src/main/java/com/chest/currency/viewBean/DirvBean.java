/*******************************************************************************
 * /* Copyright (C) Indicsoft Technologies Pvt Ltd
 * * All Rights Reserved.
 *******************************************************************************/
package com.chest.currency.viewBean;

import java.util.Date;

public class DirvBean {
	
	private int id;
	private String order_date;
	private String rbi_order_no;
	private String expiry_date;
	private String bankName;
	private String approvedCC;
	private int denomination;
	private int bundle;
	private int total;
	private String binNumber;
	private String location;
	private String filepath;
	private String hiddenBinNo;
	private String category;
	private Date insertTime;
	private Date updateTime;
	private String insertBy;
	private String updateBy;
	private int icmcId;
	
	public int getIcmcId() {
		return icmcId;
	}
	public void setIcmcId(int icmcId) {
		this.icmcId = icmcId;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getFilepath() {
		return filepath;
	}
	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}
	public String getHiddenBinNo() {
		return hiddenBinNo;
	}
	public void setHiddenBinNo(String hiddenBinNo) {
		this.hiddenBinNo = hiddenBinNo;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getOrder_date() {
		return order_date;
	}
	public void setOrder_date(String order_date) {
		this.order_date = order_date;
	}
	public String getRbi_order_no() {
		return rbi_order_no;
	}
	public void setRbi_order_no(String rbi_order_no) {
		this.rbi_order_no = rbi_order_no;
	}
	public String getExpiry_date() {
		return expiry_date;
	}
	public void setExpiry_date(String expiry_date) {
		this.expiry_date = expiry_date;
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
	
	
	public int getDenomination() {
		return denomination;
	}
	public void setDenomination(int denomination) {
		this.denomination = denomination;
	}
	public int getBundle() {
		return bundle;
	}
	public void setBundle(int bundle) {
		this.bundle = bundle;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public String getBinNumber() {
		return binNumber;
	}
	public void setBinNumber(String binNumber) {
		this.binNumber = binNumber;
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
	
}
