/*******************************************************************************
 * /* Copyright (C) Indicsoft Technologies Pvt Ltd
 * * All Rights Reserved.
 *******************************************************************************/
package com.chest.currency.viewBean;

public class CashProcessing {
	private int id;
	private String machineNo;
	private String type;
	private String descripancy;
	private String solId;
	private String branch;
	private String denomination;
	private String bundle;
	private String account_teller_cam;
	private String date;
	private String sReading;
	private String eReading;
	private String noteNumber;
	private String SR;
	private String account_teller_cam_value;
	private String filepath;
	
	public String getFilepath() {
		return filepath;
	}

	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}

	public String getAccount_teller_cam() {
		return account_teller_cam;
	}

	public void setAccount_teller_cam(String account_teller_cam) {
		this.account_teller_cam = account_teller_cam;
	}

	public String getAccount_teller_cam_value() {
		return account_teller_cam_value;
	}

	public void setAccount_teller_cam_value(String account_teller_cam_value) {
		this.account_teller_cam_value = account_teller_cam_value;
	}

	public String getNoteNumber() {
		return noteNumber;
	}

	public void setNoteNumber(String noteNumber) {
		this.noteNumber = noteNumber;
	}

	public String getSR() {
		return SR;
	}

	public void setSR(String sR) {
		SR = sR;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMachineNo() {
		return machineNo;
	}

	public void setMachineNo(String machineNo) {
		this.machineNo = machineNo;
	}

	public String getSolId() {
		return solId;
	}

	public void setSolId(String solId) {
		this.solId = solId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDescripancy() {
		return descripancy;
	}

	public void setDescripancy(String descripancy) {
		this.descripancy = descripancy;
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
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

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getsReading() {
		return sReading;
	}

	public void setsReading(String sReading) {
		this.sReading = sReading;
	}

	public String geteReading() {
		return eReading;
	}

	public void seteReading(String eReading) {
		this.eReading = eReading;
	}

}
