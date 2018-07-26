/*******************************************************************************
 * /* Copyright (C) Indicsoft Technologies Pvt Ltd
 * * All Rights Reserved.
 *******************************************************************************/
package com.chest.currency.viewBean;

public enum ReportTypeEnum {
	
	TE1_TE2("TE1_TE2"),
	Bin_Summary("Bin_Summary"),
	Cash_Movement("Cash_Movement"),
	IO2("IO2"),
	Chest_Slip("Chest_Slip"),
	IRV_ORV("IRV_ORV"),
	Coin_Distribution("Coin_Distribution"),
	Discrepancy("Discrepancy"),
	DN2("DN2"),
	Cash_Balancing("Cash_Balancing"),
	TR64_Soiled("TR64_Soiled"),
	Cash_book_Deposit_Withdrawl("Cash_book_Deposit_Withdrawl"),
	Suspense_Cash("Suspense_Cash"),
	CAT_5("CAT_5"),
	CCR("CCR"),
	Monthly_RBI("Monthly_RBI");
	
	String reportType;
	
	public String getReportType(){
		return reportType;
	}
	
	ReportTypeEnum(String reportType){
		this.reportType = reportType;
	}

}
