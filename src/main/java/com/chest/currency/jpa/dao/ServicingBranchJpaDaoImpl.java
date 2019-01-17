package com.chest.currency.jpa.dao;

import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.chest.currency.entity.model.QServicingBranch;
import com.chest.currency.entity.model.ServicingBranch;
import com.chest.currency.enums.Status;
import com.mysema.query.jpa.impl.JPAQuery;


@Repository
public class ServicingBranchJpaDaoImpl implements ServicingBranchJpaDao {
	
	private static final Logger LOG = LoggerFactory.getLogger(ServicingBranchJpaDaoImpl.class);
	
	@PersistenceContext
	private EntityManager em;

	@Override
	public boolean UploadServicingBranch(List<ServicingBranch> servicingList, ServicingBranch sb) {
		LOG.info("Uploading Servicing Branch Records From CSV..");
		for(ServicingBranch sbTemp : servicingList){
			sbTemp.setInsertBy(sb.getInsertBy());
			sbTemp.setUpdateBy(sb.getUpdateBy());
			Calendar now = Calendar.getInstance();
			sbTemp.setInsertTime(now);
			sbTemp.setUpdateTime(now);
			sbTemp.setStatus(Status.ENABLED);
			em.persist(sbTemp);
		}
		return true;
	}
	
	private JPAQuery getFromQueryForServicingBranch() {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QServicingBranch.servicingBranch);
		return jpaQuery;
	}

	@Override
	public List<ServicingBranch> getServicingBranch() {
		LOG.info("Going to Fetch Servicing Branch Records");
		JPAQuery jpaQuery = getFromQueryForServicingBranch();
		//jpaQuery.where(QServicingBranch.servicingBranch.status.eq(Status.ENABLED));
		List<ServicingBranch> servicingBranchList = jpaQuery.list(QServicingBranch.servicingBranch);
		return servicingBranchList;
	}

	@Override
	public ServicingBranch editServicingBranch(Long id) {
		return em.find(ServicingBranch.class, id);
	}

	@Override
	public boolean updateServicingBranch(ServicingBranch servicingBranch) {
		em.merge(servicingBranch);
		return true;
	}

}
