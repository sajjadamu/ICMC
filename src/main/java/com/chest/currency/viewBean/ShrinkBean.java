/*******************************************************************************
 * /* Copyright (C) Indicsoft Technologies Pvt Ltd
 * * All Rights Reserved.
 *******************************************************************************/
package com.chest.currency.viewBean;

import java.util.Date;

public class ShrinkBean {
	private int id;
	private String customerName;
	private String branch;
	private String cashierName;
	private Date insertTime;
	private Date updateTime;
	private String insertBy;
	private String updateBy;
	private Integer solId;
	private int status;
	private String bin_type;
	private int denomination;
	private int available_capacity;
	private int accountNumber;
	private int shrinkID;
	private String filepath;
	private String cashType;
	private double  bundle;
	private int packets;
	private int total;
	private int capacity;
	private String bin;
	private int hiddenBinNo;
	private String currencyType;
	private String coins;
	private Integer ICMCId;
	
	public Integer getICMCId() {
		return ICMCId;
	}
	public void setICMCId(Integer iCMCId) {
		ICMCId = iCMCId;
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
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getBranch() {
		return branch;
	}
	public void setBranch(String branch) {
		this.branch = branch;
	}
	public String getCashierName() {
		return cashierName;
	}
	public void setCashierName(String cashierName) {
		this.cashierName = cashierName;
	}
	public Date getInsertTime() {
		return insertTime;
	}
	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}
	public String getInsertBy() {
		return insertBy;
	}
	public void setInsertBy(String insertBy) {
		this.insertBy = insertBy;
	}
	public Integer getSolId() {
		return solId;
	}
	public void setSolId(Integer solId) {
		this.solId = solId;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getBin_type() {
		return bin_type;
	}
	public void setBin_type(String bin_type) {
		this.bin_type = bin_type;
	}
	public int getDenomination() {
		return denomination;
	}
	public void setDenomination(int denomination) {
		this.denomination = denomination;
	}
	public int getAvailable_capacity() {
		return available_capacity;
	}
	public void setAvailable_capacity(int available_capacity) {
		this.available_capacity = available_capacity;
	}
	public int getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(int accountNumber) {
		this.accountNumber = accountNumber;
	}
	public int getShrinkID() {
		return shrinkID;
	}
	public void setShrinkID(int shrinkID) {
		this.shrinkID = shrinkID;
	}
	public String getFilepath() {
		return filepath;
	}
	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}
	public String getCashType() {
		return cashType;
	}
	public void setCashType(String cashType) {
		this.cashType = cashType;
	}
	public double getBundle() {
		return bundle;
	}
	public void setBundle(double bundle) {
		this.bundle = bundle;
	}
	public int getPackets() {
		return packets;
	}
	public void setPackets(int packets) {
		this.packets = packets;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public int getCapacity() {
		return capacity;
	}
	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}
	public String getBin() {
		return bin;
	}
	public void setBin(String bin) {
		this.bin = bin;
	}
	public int getHiddenBinNo() {
		return hiddenBinNo;
	}
	public void setHiddenBinNo(int hiddenBinNo) {
		this.hiddenBinNo = hiddenBinNo;
	}
	public String getCurrencyType() {
		return currencyType;
	}
	public void setCurrencyType(String currencyType) {
		this.currencyType = currencyType;
	}
	public String getCoins() {
		return coins;
	}
	public void setCoins(String coins) {
		this.coins = coins;
	}
}