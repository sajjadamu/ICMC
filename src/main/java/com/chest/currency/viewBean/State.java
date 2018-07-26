/*******************************************************************************
 * /* Copyright (C) Indicsoft Technologies Pvt Ltd
 * * All Rights Reserved.
 *******************************************************************************/
package com.chest.currency.viewBean;

public enum State {
	
	ANDAMAN_AND_NICOBAR_ISLANDS("ANDAMAN_AND_NICOBAR_ISLANDS"),
	ANDHRA_PRADESH("ANDHRA_PRADESH"),
	ARUNACHAL_PRADESH("ARUNACHAL_PRADESH"),
	ASSAM("ASSAM"),
	BIHAR("BIHAR"),
	CHANDIGARH("CHANDIGARH"),
	CHHATTISGARH("CHHATTISGARH"),
	DAMAN_AND_DIU("DAMAN_AND_DIU"),
	DELHI("DELHI"),
	GOA("GOA"),
	GUJARAT("GUJARAT"),
	HIMACHAL_PRADESH("HIMACHAL_PRADESH"),
	HARYANA("HARYANA"),
	JHARKHAND("JHARKHAND"),
	JAMMU_AND_KASHMIR("JAMMU_AND_KASHMIR"),
	KARNATAKA("KARNATAKA"),
	KERALA("KERALA"),
	MAHARASHTRA("MAHARASHTRA"),
	MEGHALAYA("MEGHALAYA"),
	MANIPUR("MANIPUR"),
	MADHYA_PRADESH("MADHYA_PRADESH"),
	MIZORAM("MIZORAM"),
	NAGALAND("NAGALAND"),
	ORISSA("ORISSA"),
	PUNJAB("PUNJAB"),
	PONDICHERRY("PONDICHERRY"),
	RAJASTHAN("RAJASTHAN"),
	SIKKIM("SIKKIM"),
	TAMIL_NADU("TAMIL_NADU"),
	TRIPURA("TRIPURA"),
	UP("UTTAR_PRADESH"),
	UTTARANCHAL("UTTARANCHAL"),
	WEST_BENGAL("WEST_BENGAL"),
	DADRA_AND_NAGAR_HAVELI("DADRA_AND_NAGAR_HAVELI"),
	LAKSHADWEEP("LAKSHADWEEP"),
	TELANGANA("TELANGANA");
	
	String state;
	
	public String getState() {
		return state;
	}

	State(String state){
		this.state = state;
	}

}
