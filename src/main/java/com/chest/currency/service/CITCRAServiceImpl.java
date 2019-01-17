package com.chest.currency.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chest.currency.dao.CITCRADaoImpl;
import com.chest.currency.entity.model.CITCRADriver;
import com.chest.currency.entity.model.CITCRAVehicle;
import com.chest.currency.entity.model.CITCRAVendor;
import com.chest.currency.entity.model.ICMC;
import com.chest.currency.entity.model.ZoneMaster;
import com.chest.currency.jpa.dao.CITCRAJpaDao;

@Service
@Transactional
public class CITCRAServiceImpl implements CITCRAService {

	private static final Logger LOG = LoggerFactory.getLogger(CITCRAServiceImpl.class);

	@Autowired
	CITCRAJpaDao citCraJpaDao;

	@Autowired
	CITCRADaoImpl citCraDao;

	@Override
	public boolean addCitCraVendor(CITCRAVendor citcraVendor) {
		LOG.info("CIT CRA VENDOR SAVED");
		return citCraJpaDao.addCitCraVendor(citcraVendor);
	}

	@Override
	public List<ICMC> getICMCName(String region) {
		return citCraJpaDao.getICMCName(region);
	}

	@Override
	@Transactional
	public boolean UploadCITCRAVendor(List<CITCRAVendor> vendorList, CITCRAVendor vendor) {
		boolean isAllSuccess = false;
		try {
			isAllSuccess = citCraJpaDao.UploadCITCRAVendor(vendorList, vendor);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
		return isAllSuccess;
	}

	/*
	 * @Override public boolean UploadCITCRAVendor(List<CITCRAVendor>
	 * vendorList) {
	 * 
	 * //get ICMC Records and store in a HashMap with Key:ICMC Name and Value as
	 * a ICMC Record boolean isAllSuccess = false; Map<String, ICMC> map = new
	 * HashMap<>(); List<ICMC> icmcList = citCraJpaDao.getICMCName(); for(ICMC
	 * icmc : icmcList) { map.put(icmc.getName(), icmc); } isAllSuccess =
	 * citCraJpaDao.UploadCITCRAVendor(vendorList,map); if (isAllSuccess) {
	 * throw new RuntimeException("Error while CSV File Uploading"); } return
	 * isAllSuccess; }
	 */

	/*
	 * @Override public List<ICMC> getICMCName() { List<ICMC>
	 * icmcList=citCraJpaDao.getICMCName(); return icmcList; }
	 */

	@Override
	public CITCRAVendor vendorRecordForModify(Long idCitCraVendor) {
		return citCraJpaDao.vendorRecordForModify(idCitCraVendor);
	}

	@Override
	public List<CITCRAVendor> getCITCRAVendor() {
		LOG.info("Fetched CIT_CRA_VENDOR List..");
		return citCraJpaDao.getCITCRAVendor();

	}

	@Override
	public List<ZoneMaster> getRegionList(CITCRAVendor vendor) {
		return citCraJpaDao.getRegionList(vendor);
	}

	@Override
	public List<ICMC> getIcmcList(CITCRAVendor vendor) {
		return citCraJpaDao.getIcmcList(vendor);
	}

	@Override
	public void updateCITCRAVendor(CITCRAVendor vendor) {
		LOG.info("CIT CRA VENDOR UPDATED");
		citCraJpaDao.updateCITCRAVendor(vendor);
	}

	@Override
	public CITCRAVendor getCitCraVendorById(Long id) {
		return citCraJpaDao.getCitCraVendorById(id);
	}

	@Override
	public boolean deleteCITCRAVendor(CITCRAVendor vendor) {
		LOG.info("CIT CRA Vendor Removed");
		return citCraJpaDao.deleteCITCRAVendor(vendor);
	}

	@Override
	public List<CITCRAVehicle> getVehicleDetails() {
		return citCraJpaDao.getVehicleDetails();
	}

	@Override
	public boolean addVehicle(CITCRAVehicle vehicle) {
		boolean isAllSuccess = false;
		try {
			isAllSuccess = citCraJpaDao.addVehicle(vehicle);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
		return isAllSuccess;
	}

	@Override
	@Transactional
	public boolean UploadCITCRAVehicle(List<CITCRAVehicle> vehicleList, CITCRAVehicle vehicle) {
		boolean isAllSuccess = false;
		try {
			isAllSuccess = citCraJpaDao.UploadCITCRAVehicle(vehicleList, vehicle);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
		return isAllSuccess;
	}

	@Override
	public List<CITCRAVendor> getVendorNameAccordingToICMC(String regionId) {
		return citCraJpaDao.getVendorNameAccordingToICMC(regionId);
	}

	@Override
	public CITCRAVehicle vehicleRecordForModify(Long id) {
		return citCraJpaDao.vehicleRecordForModify(id);
	}

	@Override
	public boolean updateCITCRAVehicle(CITCRAVehicle vehicle) {
		LOG.info("CIT CRA VEHICLE UPDATED");
		return citCraJpaDao.updateCITCRAVehicle(vehicle);
	}

	@Override
	public CITCRAVehicle vehicleRecordForRemove(Long id) {
		return citCraJpaDao.vehicleRecordForRemove(id);
	}

	@Override
	public boolean deleteCITCRAVehicle(CITCRAVehicle vehicle) {
		LOG.info("CIT CRA VEHICLE UPDATE");
		return citCraJpaDao.deleteCITCRAVehicle(vehicle);
	}

	@Override
	public List<CITCRADriver> getVehicleDriver() {
		return citCraJpaDao.getVehicleDriverDetails();
	}

	@Override
	public boolean addDriver(CITCRADriver driver) {
		LOG.info("CIT CRA DRIVER SAVED");
		return citCraJpaDao.addDriver(driver);
	}

	@Override
	public boolean UploadCITCRADriver(List<CITCRADriver> driverList, CITCRADriver driver) {
		boolean isAllSuccess = false;
		try {
			isAllSuccess = citCraJpaDao.UploadCITCRADriver(driverList, driver);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
		return isAllSuccess;
	}

	@Override
	public List<CITCRAVehicle> getVehicleNumberAccordingToVendorForDriver(String vendor) {
		return citCraJpaDao.getVehicleNumberAccordingToVendorForDriver(vendor);
	}

	@Override
	public CITCRADriver driverRecordForModify(Long id) {
		return citCraJpaDao.driverRecordForModify(id);
	}

	@Override
	public List<String> getVehicleNumberList() {
		return citCraJpaDao.getVehicleNumberList();
	}

	@Override
	public boolean updateCITCRADriver(CITCRADriver driver) {
		LOG.info("CIT CRA DRIVER UPDATED");
		return citCraJpaDao.updateCITCRADriver(driver);
	}

	@Override
	public CITCRADriver driverRecordForRemove(Long id) {
		return citCraJpaDao.driverRecordForRemove(id);
	}

	@Override
	public boolean deleteCITCRADriver(CITCRADriver driver) {
		return citCraJpaDao.deleteCITCRADriver(driver);
	}

	/*
	 * @Override public CITCRAVendor getCITCRAVendor(int id){ CITCRAVendor
	 * citcraVendors=citCraDao.getCITCRAVendor(id); return citcraVendors; }
	 * 
	 * @Override public boolean
	 * CITCRAVendorAndICMCAsso(CITCRAVendorICMCAssociation association) {
	 * boolean isSaved=citCraDao.CITCRAVendorAndICMCAsso(association); return
	 * isSaved; }
	 */
}
