/*******************************************************************************
 * /* Copyright (C) Indicsoft Technologies Pvt Ltd
 * * All Rights Reserved.
 *******************************************************************************/
package com.chest.currency.viewBean;

import java.math.BigDecimal;

public class BinTransactionAllSummary {
private int denomination;
private String binType;
private BigDecimal receiveBundle;
private BigDecimal fresh;
private BigDecimal unprocess;
private BigDecimal issuable;
private BigDecimal ATM;
private BigDecimal soiled;
private BigDecimal total;
private BigDecimal value;
public int getDenomination() {
	return denomination;
}
public void setDenomination(int denomination) {
	this.denomination = denomination;
}
public String getBinType() {
	return binType;
}
public void setBinType(String binType) {
	this.binType = binType;
}
public BigDecimal getReceiveBundle() {
	return receiveBundle;
}
public void setReceiveBundle(BigDecimal receiveBundle) {
	this.receiveBundle = receiveBundle;
}
public BigDecimal getFresh() {
	return fresh;
}
public void setFresh(BigDecimal fresh) {
	this.fresh = fresh;
}
public BigDecimal getUnprocess() {
	return unprocess;
}
public void setUnprocess(BigDecimal unprocess) {
	this.unprocess = unprocess;
}
public BigDecimal getIssuable() {
	return issuable;
}
public void setIssuable(BigDecimal issuable) {
	this.issuable = issuable;
}
public BigDecimal getATM() {
	return ATM;
}
public void setATM(BigDecimal aTM) {
	ATM = aTM;
}
public BigDecimal getSoiled() {
	return soiled;
}
public void setSoiled(BigDecimal soiled) {
	this.soiled = soiled;
}
public BigDecimal getTotal() {
	return total;
}
public void setTotal(BigDecimal total) {
	this.total = total;
}
public BigDecimal getValue() {
	return value;
}
public void setValue(BigDecimal value) {
	this.value = value;
}



}
