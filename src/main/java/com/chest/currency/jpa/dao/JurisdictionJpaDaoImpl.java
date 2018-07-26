/*******************************************************************************
 * /* Copyright (C) Indicsoft Technologies Pvt Ltd
 * * All Rights Reserved.
 *******************************************************************************/
/**
 * 
 */
package com.chest.currency.jpa.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.chest.currency.entity.model.Jurisdiction;
import com.chest.currency.entity.model.QJurisdiction;
import com.chest.currency.enums.Status;
import com.mysema.query.jpa.impl.JPAQuery;

/**
 * @author root
 *
 */
@Repository
public class JurisdictionJpaDaoImpl implements JurisdictionJpaDao {

	private static final Logger LOG = LoggerFactory.getLogger(UserAdministrationJpaDaoImpl.class);

	@PersistenceContext
	private EntityManager em;

	@Override
	public boolean createJurisdiction(Jurisdiction jurisdiction) {
		LOG.info("JURISDICTION CREATED");
		em.persist(jurisdiction);
		return true;
	}

	@Override
	public List<Jurisdiction> getJurisdictionList() {
		JPAQuery jpaQuery = getFromQueryForJurisdiction();
		jpaQuery.where(QJurisdiction.jurisdiction1.status.eq(Status.ENABLED));
		List<Jurisdiction> jurisdictionList = jpaQuery.list(QJurisdiction.jurisdiction1);
		return jurisdictionList;
	}

	private JPAQuery getFromQueryForJurisdiction() {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QJurisdiction.jurisdiction1);
		return jpaQuery;
	}

	@Override
	public Jurisdiction getJurisdictionById(int id) {
		return em.find(Jurisdiction.class, id);
	}

	@Override
	public boolean updateJurisdiction(Jurisdiction jurisdiction) {
		em.merge(jurisdiction);
		return true;
	}

	@Override
	public boolean UploadJurisdiction(List<Jurisdiction> jurisdictionList, Jurisdiction jurisdiction) {
		LOG.info("Going to insert list of branches");
		for(Jurisdiction juri : jurisdictionList){
			juri.setInsertBy(jurisdiction.getInsertBy());
			juri.setUpdateBy(jurisdiction.getUpdateBy());
			juri.setInsertTime(jurisdiction.getInsertTime());
			juri.setUpdateTime(jurisdiction.getUpdateTime());
			juri.setStatus(Status.ENABLED);
			em.persist(juri);
		}
		return true;
	}

}
