/*******************************************************************************
 * /* Copyright (C) Indicsoft Technologies Pvt Ltd
 * * All Rights Reserved.
 *******************************************************************************/
package com.chest.currency.jpa.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.chest.currency.entity.model.QRbiMaster;
import com.chest.currency.entity.model.QZoneMaster;
import com.chest.currency.entity.model.RbiMaster;
import com.chest.currency.entity.model.ZoneMaster;
import com.chest.currency.enums.Status;
import com.mysema.query.jpa.impl.JPAQuery;


@Repository
public class RbiMasterJpaDaoImp implements RbiMasterJpaDao {
	
	private static final Logger LOG = LoggerFactory.getLogger(RbiMasterJpaDaoImp.class);
	
	@PersistenceContext
	public EntityManager em;
	
	@Override
	public boolean saveRbiMaster(RbiMaster rbiMaster) {
		em.persist(rbiMaster);
		return true;
	}
	
	private JPAQuery getFromQueryForRbiMaster(){
		JPAQuery jpaQuery=new JPAQuery(em);
		jpaQuery.from(QRbiMaster.rbiMaster);
		return jpaQuery;
	}
	
	@Override
	public List<RbiMaster> getAllRbiMaster() {
		JPAQuery jpaQuery = getFromQueryForRbiMaster();
		jpaQuery.where(QRbiMaster.rbiMaster.status.ne(Status.DELETED));
		List<RbiMaster>list=jpaQuery.list(QRbiMaster.rbiMaster);
		return list;
	}

	@Override
	public RbiMaster getRbiMasterObject(Long id) {
		return em.find(RbiMaster.class, id);
	}
	
	@Override
	public boolean updateRbiMaster(RbiMaster rbiMaster) {
		em.merge(rbiMaster);
		return true;
	}
	
	private JPAQuery getFromQueryForRegion() {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QZoneMaster.zoneMaster);
		return jpaQuery;
	}

	@Override
	public List<ZoneMaster> getRegionList(RbiMaster rbi) {
		List<ZoneMaster> regionList = null;
		if (rbi.getZone() != null && rbi.getZone().value().length() > 0) {
			JPAQuery jpaQuery = getFromQueryForRegion();
			jpaQuery.where(QZoneMaster.zoneMaster.zone.eq(rbi.getZone()));
			regionList = jpaQuery.list(QZoneMaster.zoneMaster);
		}else{
			regionList = new ArrayList<ZoneMaster>();
		}
		return regionList;
	}
	
	@Override
	public RbiMaster isValidRbiName(String rbiName) {
		JPAQuery jpaQuery = getFromQueryForRbiMaster();
		jpaQuery.where(QRbiMaster.rbiMaster.rbiname.equalsIgnoreCase(rbiName)
				.and(QRbiMaster.rbiMaster.status.ne(Status.DELETED)));
		return jpaQuery.singleResult(QRbiMaster.rbiMaster);
	}

	@Override
	public boolean uploadRBIMaster(List<RbiMaster> rbiMasterList, RbiMaster rbiMaster) {
		LOG.info("Going to insert list of RBI's");
		for(RbiMaster rbiTemp : rbiMasterList){
			rbiTemp.setInsertBy(rbiMaster.getInsertBy());
			rbiTemp.setUpdateBy(rbiMaster.getUpdateBy());
			rbiTemp.setInsertTime(rbiMaster.getInsertTime());
			rbiTemp.setUpdateTime(rbiMaster.getUpdateTime());
			rbiTemp.setStatus(Status.ENABLED);
			em.persist(rbiTemp);
		}
		return true;
	}
	
}
