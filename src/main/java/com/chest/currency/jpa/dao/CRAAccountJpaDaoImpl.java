package com.chest.currency.jpa.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.chest.currency.entity.model.CRAAccountDetail;
import com.chest.currency.entity.model.ICMC;
import com.chest.currency.entity.model.QCRAAccountDetail;
import com.chest.currency.entity.model.QICMC;
import com.chest.currency.enums.Status;
import com.mysema.query.jpa.impl.JPAQuery;

@Repository
public class CRAAccountJpaDaoImpl implements CRAAccountJpaDao {

	@PersistenceContext
	private EntityManager em;

	@Override
	public boolean addCRAAccountDetails(CRAAccountDetail craAccountDetail) {
		em.merge(craAccountDetail);
		return true;
	}

	private JPAQuery getFromQueryForCRAccountDetail() {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QCRAAccountDetail.cRAAccountDetail);
		return jpaQuery;
	}

	@Override
	public List<CRAAccountDetail> getCRAAccountDetailList() {
		JPAQuery jpaQuery = getFromQueryForCRAccountDetail();
		List<CRAAccountDetail> accountDetailList = jpaQuery.list(QCRAAccountDetail.cRAAccountDetail);
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
	public CRAAccountDetail getCRAAccountDetailById(long id) {
		return em.find(CRAAccountDetail.class, id);
	}

	@Override
	public boolean updateCRAAccountDetail(CRAAccountDetail craAccountDetail) {
		em.merge(craAccountDetail);
		return true;
	}

}
