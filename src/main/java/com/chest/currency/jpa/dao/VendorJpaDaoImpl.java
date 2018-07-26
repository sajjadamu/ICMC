/*******************************************************************************
 * /* Copyright (C) Indicsoft Technologies Pvt Ltd
 * * All Rights Reserved.
 *******************************************************************************/
package com.chest.currency.jpa.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.chest.currency.entity.model.QVendor;
import com.chest.currency.entity.model.Vendor;
import com.chest.currency.enums.Status;
import com.mysema.query.jpa.impl.JPAQuery;

@Repository
public class VendorJpaDaoImpl implements VendorJpaDao {
	
	private static final Logger LOG = LoggerFactory.getLogger(VendorJpaDaoImpl.class);
	
	@PersistenceContext
	private EntityManager em;

	@Override
	public boolean addVendor(Vendor vendor) {
		LOG.info("Going to save Vendor Record");
		em.persist(vendor);
		return true;
	}

	@Override
	public List<Vendor> getVendorList() {
		LOG.info("Going to fetch Vendor Records..");
		JPAQuery jpaQuery = getFromQueryForVendor();
		jpaQuery.where(QVendor.vendor.status.eq(Status.ENABLED));
		List<Vendor> vendorList = jpaQuery.list(QVendor.vendor);
		return vendorList;
	}
	
	private JPAQuery getFromQueryForVendor(){
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QVendor.vendor);
		return jpaQuery;
	}
	
}
