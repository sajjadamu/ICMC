/*******************************************************************************
 * /* Copyright (C) Indicsoft Technologies Pvt Ltd
 * * All Rights Reserved.
 *******************************************************************************/
package com.chest.currency.viewBean;

public class IRVBean {
	private int id;
	private String solId;
	private String branch;
	private String SR;
	private String citAgency;
	private String citCustodian;
	private String sackLock;
	private String vehicle;
	private String denomination;
	private String bundle;
	private String total;
	private String grandTotal;
    private String vendor;
    private String job_id;
    private String custodian;
	private String accountNumber;
	
	
	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getCustodian() {
		return custodian;
	}

	public void setCustodian(String custodian) {
		this.custodian = custodian;
	}

	public String getJob_id() {
		return job_id;
	}

	public void setJob_id(String job_id) {
		this.job_id = job_id;
	}

	public String getVendor() {
		return vendor;
	}

	public void setVendor(String vendor) {
		this.vendor = vendor;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSolId() {
		return solId;
	}

	public void setSolId(String solId) {
		this.solId = solId;
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

	public String getCitAgency() {
		return citAgency;
	}

	public void setCitAgency(String citAgency) {
		this.citAgency = citAgency;
	}

	public String getCitCustodian() {
		return citCustodian;
	}

	public void setCitCustodian(String citCustodian) {
		this.citCustodian = citCustodian;
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

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public String getGrandTotal() {
		return grandTotal;
	}

	public void setGrandTotal(String grandTotal) {
		this.grandTotal = grandTotal;
	}

}
