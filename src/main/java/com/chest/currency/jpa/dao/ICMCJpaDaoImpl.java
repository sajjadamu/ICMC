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

import com.chest.currency.entity.model.Branch;
import com.chest.currency.entity.model.ICMC;
import com.chest.currency.entity.model.QBranch;
import com.chest.currency.entity.model.QICMC;
import com.chest.currency.entity.model.QZoneMaster;
import com.chest.currency.enums.Status;
import com.mysema.query.jpa.impl.JPAQuery;

@Repository
public class ICMCJpaDaoImpl implements ICMCJpaDao {

	private static final Logger LOG = LoggerFactory.getLogger(UserAdministrationJpaDaoImpl.class);

	@PersistenceContext
	private EntityManager em;

	@Override
	public boolean createICMC(ICMC icmc) {
		em.persist(icmc);
		LOG.info("New ICMC has been created");
		return true;
	}
	
	@Override
	public ICMC isIcmcNameValid(String name) {
		JPAQuery jpaQuery = getFromQueryForICMC();
		jpaQuery.where(QICMC.iCMC.name.equalsIgnoreCase(name)
				.and(QICMC.iCMC.status.ne(Status.DELETED)));
		return jpaQuery.singleResult(QICMC.iCMC);
	}

	@Override
	public List<ICMC> getICMCList() {
		JPAQuery jpaQuery = getFromQueryForICMC();
		jpaQuery.where(QICMC.iCMC.status.ne(Status.DELETED));
		List<ICMC> icmcList = jpaQuery.list(QICMC.iCMC);
		return icmcList;
	}

	private JPAQuery getFromQueryForICMC() {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QICMC.iCMC);
		return jpaQuery;
	}

	@Override
	public ICMC getICMCById(Long id) {
		return em.find(ICMC.class, id);
	}

	@Override
	public boolean removeICMC(ICMC icmc) {
		em.merge(icmc);
		return true;
	}
	
	@Override
	public boolean uploadICMC(List<ICMC> icmcList, ICMC icmc) {
		LOG.info("Going to insert list of ICMC's");
		for(ICMC icmcTemp : icmcList){
			icmcTemp.setInsertBy(icmc.getInsertBy());
			icmcTemp.setUpdateBy(icmc.getUpdateBy());
			icmcTemp.setInsertTime(icmc.getInsertTime());
			icmcTemp.setUpdateTime(icmc.getUpdateTime());
			icmcTemp.setStatus(Status.DISABLED);
			em.persist(icmcTemp);
		}
		return true;
	}

	@Override
	public List<ICMC> getICMCList(List<Long> icmcIds) {
		JPAQuery query = getFromQueryForICMC();
		query.where(QICMC.iCMC.id.in(icmcIds));
		return query.list(QICMC.iCMC);
	}

	@Override
	public boolean updateICMC(ICMC icmc) {
		em.merge(icmc);
		return true;
	}
	
	private JPAQuery getFromQueryForBranch() {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QBranch.branch1);
		return jpaQuery;
	}

	@Override
	public List<Branch> getRBINameZoneAndRegion(String linkBranchSolId) {
		JPAQuery jpaQuery = getFromQueryForBranch();
		jpaQuery.where(QBranch.branch1.solId.eq(linkBranchSolId)
				.and(QBranch.branch1.status.eq(Status.ENABLED)));
		List<Branch> branchList = jpaQuery.list(QBranch.branch1);
		return branchList;
	}
	
	private JPAQuery getFromQueryForRegion() {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QZoneMaster.zoneMaster);
		return jpaQuery;
	}
	
	@Override
	public List<String> getRegionList(ICMC icmc) {
		JPAQuery jpaQuery = getFromQueryForRegion();
		jpaQuery.where(QZoneMaster.zoneMaster.zone.eq(icmc.getZone()));
		List<String> reasonList = jpaQuery.list(QZoneMaster.zoneMaster.region);
		return reasonList;
	}

}
