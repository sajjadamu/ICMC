package com.chest.currency.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chest.currency.entity.model.Vendor;
import com.chest.currency.jpa.dao.VendorJpaDao;

@Service
@Transactional
public class VendorServiceImpl implements VendorService {

	private static final Logger LOG = LoggerFactory.getLogger(VendorServiceImpl.class);

	@Autowired
	VendorJpaDao vendorJpaDao;

	@Override
	public boolean addVendor(Vendor vendor) {
		LOG.info("Vendor record has been saved");
		return vendorJpaDao.addVendor(vendor);
	}

	@Override
	public List<Vendor> getVendorList() {
		LOG.info("Fetched Vendor Records:");
		return vendorJpaDao.getVendorList();
	}

}
