package com.chest.currency.jpa.dao;

import java.math.BigInteger;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.chest.currency.entity.model.DSBAccountDetail;
import com.chest.currency.entity.model.ICMC;
import com.chest.currency.entity.model.QDSBAccountDetail;
import com.chest.currency.entity.model.QICMC;
import com.chest.currency.enums.Status;
import com.mysema.query.jpa.impl.JPAQuery;

@Repository
public class DSBAccountDetailsJpaDaoImpl implements DSBAccountDetailsJpaDao {

	@PersistenceContext
	private EntityManager em;

	@Override
	public boolean addDSBAccountDetails(DSBAccountDetail dsbAccountDetail) {
		em.merge(dsbAccountDetail);
		return true;
	}

	private JPAQuery getFromQueryForDSBAccountDetail() {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QDSBAccountDetail.dSBAccountDetail);
		return jpaQuery;
	}

	@Override
	public List<DSBAccountDetail> getDSBAccountDetailList() {
		JPAQuery jpaQuery = getFromQueryForDSBAccountDetail();
		List<DSBAccountDetail> accountDetailList = jpaQuery.list(QDSBAccountDetail.dSBAccountDetail);
		return accountDetailList;
	}

	private JPAQuery getFromQueryForICMC() {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QICMC.iCMC);
		return jpaQuery;
	}

	@Override
	public List<ICMC> getICMCName() {
		JPAQuery jpaQuery = getFromQueryForICMC();
		jpaQuery.where(QICMC.iCMC.status.eq(Status.ENABLED));
		List<ICMC> icmcList = jpaQuery.list(QICMC.iCMC);
		return icmcList;
	}

	@Override
	public DSBAccountDetail getDSBAccountDetailById(long id) {
		return em.find(DSBAccountDetail.class, id);
	}

	@Override
	public boolean updateDSBAccountDetail(DSBAccountDetail dsbAccountDetail) {
		em.merge(dsbAccountDetail);
		return true;
	}

	@Override
	public DSBAccountDetail isVendorNameValid(String vendorName, BigInteger icmcId) {
		JPAQuery jpaQuery = getFromQueryForDSBAccountDetail();
		jpaQuery.where(QDSBAccountDetail.dSBAccountDetail.icmcId.eq(icmcId)
				.and(QDSBAccountDetail.dSBAccountDetail.dsbVendorName.eq(vendorName)));
		DSBAccountDetail vendorNameFromDSB = jpaQuery.singleResult(QDSBAccountDetail.dSBAccountDetail);
		return vendorNameFromDSB;
	}
}
