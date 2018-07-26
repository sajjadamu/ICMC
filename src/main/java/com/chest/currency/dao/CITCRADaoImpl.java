/*******************************************************************************
 * /* Copyright (C) Indicsoft Technologies Pvt Ltd
 * * All Rights Reserved.
 *******************************************************************************/
package com.chest.currency.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.chest.currency.dao.mapper.CITCRAVehicleMapper;
import com.chest.currency.dao.mapper.VehicleNumberMapper;
import com.chest.currency.dao.mapper.VendorForVehicleMapper;
import com.chest.currency.viewBean.CITCRAVehicle;
import com.chest.currency.viewBean.CITCRAVendor;

@Repository
public class CITCRADaoImpl implements CITCRADao {
	
	private static final Logger LOG = LoggerFactory.getLogger(CITCRADaoImpl.class);

	@Autowired
	private JdbcTemplateDao jdbcTemplateDao;

	/*@Override
	public boolean CITCRAVendorAndICMCAsso(CITCRAVendorICMCAssociation association) {
		boolean isSuccessfull = false;
		int count = 0;
		String query = "INSERT INTO CIT_CRA_VENDOR_ICMC_ASSO (VENDOR_ID,ICMC_ID) VALUES (?,?);";
		JdbcTemplate jdbcTemplate = jdbcTemplateDao.getJdbcTemplate();
		count = jdbcTemplate.update(query, association.getVendorId(), association.getIcmcId());
		if (count > 0) {
			isSuccessfull = true;
		} else {
			isSuccessfull = false;
		}
		return isSuccessfull;
	}*/

	/*@Override
	public boolean addInCITCRAVendorAndCITCRAVendorICMCAsso(CITCRAVendor vendor,
			CITCRAVendorICMCAssociation vendorAsso) {
		boolean isAllSuccess=false;
		try
		{
			isAllSuccess=addVendor(vendor);
			if(isAllSuccess)
			{
				isAllSuccess=CITCRAVendorAndICMCAsso(vendorAsso);
			}
		}
		catch(Exception e)
		{
			throw new RuntimeException(e);
		}
		return isAllSuccess;
	}*/
	
	/*@Override
	public int UploadCITCRAVendor(List<CITCRAVendor> vendorList, Map<String, ICMC> map) {
		int count = 0;
		
		for (CITCRAVendor vendor : vendorList) {
			
			KeyHolder keyHolder = new GeneratedKeyHolder();
			count = jdbcTemplateDao.getJdbcTemplate().update(new CITCRAVendorICMCAssoCreator(vendor), keyHolder);
			
			for(String icmc : vendor.getIcmc()){
				CITCRAVendorICMCAssociation association = new CITCRAVendorICMCAssociation();
				association.setVendorId(keyHolder.getKey().intValue());
				association.setIcmcId(map.get(icmc).getId());
				boolean isSaved=CITCRAVendorAndICMCAsso(association);
				if(!isSaved)
				{
					return 0;
				}
			}
		}
		return count;
	}*/

	@Override
	public List<CITCRAVendor> getVendorNameAccordingToICMC(int icmcId) {
		LOG.info("getVendorNameAccordingToICMC");
		JdbcTemplate jdbcTemplate = jdbcTemplateDao.getJdbcTemplate();
		String Query = "select CIT_CRA_VENDOR.NAME from CIT_CRA_VENDOR,CIT_CRA_VENDOR_ICMC_ASSO where CIT_CRA_VENDOR_ICMC_ASSO.VENDOR_ID=CIT_CRA_VENDOR.ID AND CIT_CRA_VENDOR_ICMC_ASSO.ICMC_ID="+icmcId+";";
		List<CITCRAVendor> vendorList = jdbcTemplate.query(Query, new VendorForVehicleMapper());
		return vendorList;
	}

	@Override
	public List<CITCRAVehicle> getVehicleNumberAccordingToVendorForDriver(String vendor) {
		JdbcTemplate jdbcTemplate = jdbcTemplateDao.getJdbcTemplate();
		String Query = "select * from CIT_CRA_VEHICLE where VENDOR_NAME='"+vendor+"';";
		List<CITCRAVehicle> vehicleNumber = jdbcTemplate.query(Query, new CITCRAVehicleMapper());
		return vehicleNumber;
	}

	@Override
	public List<String> getVehicleNumberList() {
		JdbcTemplate jdbcTemplate = jdbcTemplateDao.getJdbcTemplate();
		String query = "select VEHICLE_NUMBER from CIT_CRA_VEHICLE;";
		List<String> vehicleNumberList = jdbcTemplate.query(query, new VehicleNumberMapper());
		return vehicleNumberList;
	}

}
