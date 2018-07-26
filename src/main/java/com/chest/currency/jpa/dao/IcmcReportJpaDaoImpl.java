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

import com.chest.currency.entity.model.ICMCReport;
import com.chest.currency.entity.model.QICMCReport;
import com.chest.currency.enums.Status;
import com.mysema.query.jpa.impl.JPAQuery;

@Repository
public class IcmcReportJpaDaoImpl implements IcmcReportJpaDao {
	
	private static final Logger LOG = LoggerFactory.getLogger(IcmcReportJpaDaoImpl.class);
	
	@PersistenceContext
	private EntityManager em;

	@Override
	public ICMCReport isCustomReportTypeNameValid(String newReportType) {
		JPAQuery jpaQuery = getFromQueryForICMCReport();
		jpaQuery.where(QICMCReport.iCMCReport.newReportType.equalsIgnoreCase(newReportType)
				.and(QICMCReport.iCMCReport.status.eq(Status.ENABLED)));
		return jpaQuery.singleResult(QICMCReport.iCMCReport);
	}
	
	private JPAQuery getFromQueryForICMCReport(){
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QICMCReport.iCMCReport);
		return jpaQuery;
	}
	
	@Override
	public List<ICMCReport> getICMCReportList() {
		LOG.info("Going to fetch ICMC Reports..");
		JPAQuery jpaQuery = getFromQueryForICMCReport(); 
		jpaQuery.where(QICMCReport.iCMCReport.status.eq(Status.ENABLED));
		List<ICMCReport> icmcReport = jpaQuery.list(QICMCReport.iCMCReport);
		 
		 LOG.info("Fetched ICMC Reports:", icmcReport);
	     return icmcReport;
	}
	
	@Override
	public boolean saveICMCReport(ICMCReport icmcReport) {
		em.persist(icmcReport);
		LOG.info("New ICMC Report Created");
		return true;
	}
	
	@Override
	public ICMCReport getReportById(int id) {
		return em.find(ICMCReport.class, id);
	}
	
	@Override
	public boolean deleteReport(ICMCReport icmcReport) {
		em.merge(icmcReport);
		return true;
	}

}
