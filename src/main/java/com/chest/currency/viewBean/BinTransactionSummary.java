/*******************************************************************************
 * /* Copyright (C) Indicsoft Technologies Pvt Ltd
 * * All Rights Reserved.
 *******************************************************************************/
package com.chest.currency.viewBean;

import java.math.BigDecimal;

public class BinTransactionSummary {
private int denomination;
private String binType;
private BigDecimal receiveBundle;
private String icmc;


public String getIcmc() {
	return icmc;
}
public void setIcmc(String icmc) {
	this.icmc = icmc;
}
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

}
