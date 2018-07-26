/*******************************************************************************
 * /* Copyright (C) Indicsoft Technologies Pvt Ltd
 * * All Rights Reserved.
 *******************************************************************************/
package com.chest.currency.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import com.chest.currency.viewBean.HolidayMaster;

public class HolidayMapper implements RowMapper<HolidayMaster> {
	public HolidayMaster mapRow(ResultSet resultSet, int rowNum) throws SQLException{
		HolidayMaster hm = new HolidayMaster();
		hm.setId(resultSet.getInt("ID"));
		hm.setHolidayName(resultSet.getString("HOLIDAY_NAME"));
		hm.setHolidayType(resultSet.getString("HOLIDAY_TYPE"));
		hm.setHolidayDate(resultSet.getString("HOLIDAY_DATE"));
		hm.setAndaman(resultSet.getString("ANDAMAN_AND_NICOBAR_ISLANDS"));
		hm.setAndhra(resultSet.getString("ANDHRA_PRADESH"));
		hm.setArunachal(resultSet.getString("ARUNACHAL_PRADESH"));
		hm.setAssam(resultSet.getString("ASSAM"));
		hm.setBihar(resultSet.getString("BIHAR"));
		hm.setChandigarh(resultSet.getString("CHANDIGARH"));
		hm.setChhattisgarh(resultSet.getString("CHHATTISGARH"));
		hm.setDamanAndDiu(resultSet.getString("DAMAN_AND_DIU"));
		hm.setDelhi(resultSet.getString("DELHI"));
		hm.setGoa(resultSet.getString("GOA"));
		hm.setGujarat(resultSet.getString("GUJARAT"));
		hm.setHimachal(resultSet.getString("HIMACHAL_PRADESH"));
		hm.setHaryana(resultSet.getString("HARYANA"));
		hm.setJharkhand(resultSet.getString("JHARKHAND"));
		hm.setJammuAndKashmir(resultSet.getString("JAMMU_AND_KASHMIR"));
		hm.setKarnataka(resultSet.getString("KARNATAKA"));
		hm.setKerala(resultSet.getString("KERALA"));
		hm.setMaharashtra(resultSet.getString("MAHARASHTRA"));
		hm.setMeghalaya(resultSet.getString("MEGHALAYA"));
		hm.setManipur(resultSet.getString("MANIPUR"));
		hm.setMadhyaPradesh(resultSet.getString("MADHYA_PRADESH"));
		hm.setMizoram(resultSet.getString("MIZORAM"));
		hm.setNagaland(resultSet.getString("NAGALAND"));
		hm.setOrissa(resultSet.getString("ORISSA"));
		hm.setPunjab(resultSet.getString("PUNJAB"));
		hm.setPondichhery(resultSet.getString("PONDICHERRY"));
		hm.setRajasthan(resultSet.getString("RAJASTHAN"));
		hm.setSikkim(resultSet.getString("SIKKIM"));
		hm.setTamilNadu(resultSet.getString("TAMIL_NADU"));
		hm.setTripura(resultSet.getString("TRIPURA"));
		hm.setUttarPradesh(resultSet.getString("UTTAR_PRADESH"));
		hm.setUttaranchal(resultSet.getString("UTTARANCHAL"));
		hm.setWestBengal(resultSet.getString("WEST_BENGAL"));
		hm.setDadarAndNagarHaveli(resultSet.getString("DADRA_AND_NAGAR_HAVELI"));
		hm.setLakshadweep(resultSet.getString("LAKSHADWEEP"));
		hm.setTelangana(resultSet.getString("TELANGANA"));
		return hm;
	}

}
