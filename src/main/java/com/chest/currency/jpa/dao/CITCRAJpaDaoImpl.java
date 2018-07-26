/*******************************************************************************
 * /* Copyright (C) Indicsoft Technologies Pvt Ltd
 * * All Rights Reserved.
 *******************************************************************************/
package com.chest.currency.jpa.dao;

import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.chest.currency.entity.model.CITCRADriver;
import com.chest.currency.entity.model.CITCRAVehicle;
import com.chest.currency.entity.model.CITCRAVendor;
import com.chest.currency.entity.model.ICMC;
import com.chest.currency.entity.model.QCITCRADriver;
import com.chest.currency.entity.model.QCITCRAVehicle;
import com.chest.currency.entity.model.QCITCRAVendor;
import com.chest.currency.entity.model.QICMC;
import com.chest.currency.entity.model.QZoneMaster;
import com.chest.currency.entity.model.ZoneMaster;
import com.chest.currency.enums.Status;
import com.mysema.query.jpa.impl.JPAQuery;

@Repository
public class CITCRAJpaDaoImpl implements CITCRAJpaDao {
	
	private static final Logger LOG = LoggerFactory.getLogger(CITCRAJpaDaoImpl.class);
	
	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	ICMCJpaDao iCMCJpaDao;

	@Override
	public List<CITCRAVendor> getCITCRAVendor() {
		LOG.info("Going to fetch CIT_CRA_Vendor List..");
		JPAQuery jpaQuery = getFromQueryForCitCraVendor(); 
		jpaQuery.where(QCITCRAVendor.cITCRAVendor.status.eq(Status.ENABLED));
		List<CITCRAVendor> citcravendor = jpaQuery.list(QCITCRAVendor.cITCRAVendor);
		LOG.info("Fetched citcraVendorList :", citcravendor);
	    return citcravendor;
	}
	
	private JPAQuery getFromQueryForCitCraVendor() {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QCITCRAVendor.cITCRAVendor);
		return jpaQuery;
	}

	
	private JPAQuery getFromQueryForICMC() {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QICMC.iCMC);
		return jpaQuery;
	}
	
	@Override
	public List<ICMC> getICMCName(String region) {
		JPAQuery jpaQuery = getFromQueryForICMC();
		jpaQuery.where(QICMC.iCMC.region.equalsIgnoreCase(region)
				.and(QICMC.iCMC.status.eq(Status.ENABLED)));
		List<ICMC> icmcList = jpaQuery.list(QICMC.iCMC);
		return icmcList;
	}

	@Override
	public boolean addCitCraVendor(CITCRAVendor citcraVendor) {
		em.persist(citcraVendor);
		LOG.info("New CITCRAVendor saved");
		return true;
	}
	
	@Override
	public boolean UploadCITCRAVendor(List<CITCRAVendor> vendorList, CITCRAVendor vendor) {
		for(CITCRAVendor vendorTemp : vendorList){
			vendorTemp.setInsertBy(vendor.getInsertBy());
			vendorTemp.setUpdateBy(vendor.getUpdateBy());
			vendorTemp.setStatus(Status.ENABLED);
			Calendar now = Calendar.getInstance();
			vendorTemp.setInsertTime(now);
			vendorTemp.setUpdateTime(now);
			em.persist(vendorTemp);
		}
		return true;
	}

	@Override
	public CITCRAVendor vendorRecordForModify(Long idCitCraVendor) {
		return em.find(CITCRAVendor.class, idCitCraVendor);
	}

	private JPAQuery getFromQueryForRegion() {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QZoneMaster.zoneMaster);
		return jpaQuery;
	}
	
	@Override
	public List<ZoneMaster> getRegionList(CITCRAVendor vendor) {
		JPAQuery jpaQuery = getFromQueryForRegion();
		jpaQuery.where(QZoneMaster.zoneMaster.zone.eq(vendor.getZone()));
		List<ZoneMaster> reasonList = jpaQuery.list(QZoneMaster.zoneMaster);
		return reasonList;
	}
	
	@Override
	public List<ICMC> getIcmcList(CITCRAVendor vendor) {
		JPAQuery jpaQuery = getFromQueryForICMC();
		jpaQuery.where(QICMC.iCMC.region.equalsIgnoreCase(vendor.getRegion())
				.and(QICMC.iCMC.status.eq(Status.ENABLED)));
		List<ICMC> icmcList = jpaQuery.list(QICMC.iCMC);
		return icmcList;
	}
	
	@Override
	public boolean updateCITCRAVendor(CITCRAVendor vendor) {
		em.merge(vendor);
		return true;
	}

	@Override
	public CITCRAVendor getCitCraVendorById(Long id) {
		return em.find(CITCRAVendor.class, id);
	}

	@Override
	public boolean deleteCITCRAVendor(CITCRAVendor citcraVendor) {
		em.merge(citcraVendor);
		return true;
	}
	
	private JPAQuery getFromQueryForVehical() {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QCITCRAVehicle.cITCRAVehicle);
		return jpaQuery;
	}
	
	@Override
	public List<CITCRAVehicle> getVehicleDetails() {
		LOG.info("Going to fetch CITCRAVehical info...");
		JPAQuery jpaQuery = getFromQueryForVehical(); 
		jpaQuery.where(QCITCRAVehicle.cITCRAVehicle.status.eq(Status.ENABLED));
		List<CITCRAVehicle> citcravehicle = jpaQuery.list(QCITCRAVehicle.cITCRAVehicle);
		LOG.info("Fetched citcravehicleList :", citcravehicle);
	    return citcravehicle;
	}
	
	@Override
	public boolean addVehicle(CITCRAVehicle vehicle) {
		em.persist(vehicle);
		LOG.info("New CIT CRA Vehicle has been Saved");
		return true;
	}
	
	@Override
	public boolean UploadCITCRAVehicle(List<CITCRAVehicle> vehicleList, CITCRAVehicle vehicle) {
		for(CITCRAVehicle vehicleTemp : vehicleList){
			vehicleTemp.setInsertBy(vehicle.getInsertBy());
			vehicleTemp.setUpdateBy(vehicle.getUpdateBy());
			vehicleTemp.setStatus(Status.ENABLED);
			Calendar now = Calendar.getInstance();
			vehicleTemp.setInsertTime(now);
			vehicleTemp.setUpdateTime(now);
			em.persist(vehicleTemp);
		}
		return true;
	}
	
	@Override
	public List<CITCRAVendor> getVendorNameAccordingToICMC(String regionId) {
		JPAQuery jpaQuery = getFromQueryForCitCraVendor();
		jpaQuery.where(QCITCRAVendor.cITCRAVendor.region.eq(regionId));
		List<CITCRAVendor> vendorList = jpaQuery.list(QCITCRAVendor.cITCRAVendor);
		LOG.info("Fetched citcraVendorList :", vendorList);
	    return vendorList;
	}
	
	@Override
	public CITCRAVehicle vehicleRecordForModify(Long id) {
		return em.find(CITCRAVehicle.class, id);
	}
	
	@Override
	public boolean updateCITCRAVehicle(CITCRAVehicle vehicle) {
		em.merge(vehicle);
		return true;
	}
	
	@Override
	public CITCRAVehicle vehicleRecordForRemove(Long id) {
		return em.find(CITCRAVehicle.class, id);
	}
	
	@Override
	public boolean deleteCITCRAVehicle(CITCRAVehicle citcraVendor) {
		em.merge(citcraVendor);
		return true;
	}
	
	private JPAQuery getFromQueryForCitCraDriver() {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QCITCRADriver.cITCRADriver);
		return jpaQuery;
	}
	
	@Override
	public List<CITCRADriver> getVehicleDriverDetails() {
		LOG.info("Going to fetch CITCRADriver info...");
		JPAQuery jpaQuery = getFromQueryForCitCraDriver(); 
		jpaQuery.where(QCITCRADriver.cITCRADriver.status.eq(Status.ENABLED));
		List<CITCRADriver> citcradriver = jpaQuery.list(QCITCRADriver.cITCRADriver);
		
		LOG.info("Fetched citcraDriverList :", citcradriver);
	    return citcradriver;
	}
	
	@Override
	public boolean addDriver(CITCRADriver driver) {
		em.persist(driver);
		LOG.info("New CITCRADRiver save");
		return true;
	}
	
	@Override
	public boolean UploadCITCRADriver(List<CITCRADriver> driverList, CITCRADriver driver) {
		for(CITCRADriver driverTemp : driverList){
			driverTemp.setInsertBy(driver.getInsertBy());
			driverTemp.setUpdateBy(driver.getUpdateBy());
			driverTemp.setStatus(Status.ENABLED);
			Calendar now = Calendar.getInstance();
			driverTemp.setInsertTime(now);
			driverTemp.setUpdateTime(now);
			em.persist(driverTemp);
		}
		return true;
	}

	
	@Override
	public List<CITCRAVehicle> getVehicleNumberAccordingToVendorForDriver(String vendor) {
		JPAQuery jpaQuery = getFromQueryForVehical();
		jpaQuery.where(QCITCRAVehicle.cITCRAVehicle.name.eq(vendor));
		List<CITCRAVehicle> vehicleList = jpaQuery.list(QCITCRAVehicle.cITCRAVehicle);
		LOG.info("Fetched citcraVendorList :", vehicleList);
	    return vehicleList;
	}
	
	@Override
	public CITCRADriver driverRecordForModify(Long id) {
		return em.find(CITCRADriver.class, id);
	}

	@Override
	public List<String> getVehicleNumberList() {
		JPAQuery jpaQuery = getFromQueryForVehical();
		jpaQuery.where(QCITCRAVehicle.cITCRAVehicle.status.eq(Status.ENABLED));
		List<String> vehicleList = jpaQuery.list(QCITCRAVehicle.cITCRAVehicle.number);
		LOG.info("Fetched Vehicle List :", vehicleList);
		return vehicleList;
	}
	
	@Override
	public boolean updateCITCRADriver(CITCRADriver driver) {
		em.merge(driver);
		return true;
	}
	
	@Override
	public CITCRADriver driverRecordForRemove(Long id) {
		return em.find(CITCRADriver.class, id);
	}
	
	@Override
	public boolean deleteCITCRADriver(CITCRADriver driver) {
		em.merge(driver);
		return true;
	}

}
