package com.chest.currency.jpa.dao;

import java.util.ArrayList;
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
import com.chest.currency.entity.model.QRbiMaster;
import com.chest.currency.entity.model.QZoneMaster;
import com.chest.currency.entity.model.RbiMaster;
import com.chest.currency.entity.model.ZoneMaster;
import com.chest.currency.enums.Status;
import com.mysema.query.jpa.impl.JPAQuery;

@Repository
public class BranchJpaDaoImpl implements BranchJpaDao {

	private static final Logger LOG = LoggerFactory.getLogger(BranchJpaDaoImpl.class);

	@PersistenceContext
	private EntityManager em;

	private JPAQuery getFromQueryForBranch() {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QBranch.branch1);
		return jpaQuery;
	}

	@Override
	public List<Branch> getBranch() {
		LOG.error("Going to Fetch Retail Branch Records");
		JPAQuery jpaQuery = getFromQueryForBranch();
		jpaQuery.where(QBranch.branch1.status.ne(Status.DELETED));
		List<Branch> branchList = jpaQuery.list(QBranch.branch1);
		LOG.error("Fetched Retail Branch Records:", branchList);
		return branchList;
	}

	private JPAQuery getFromQueryForICMC() {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QICMC.iCMC);
		return jpaQuery;
	}

	@Override
	public List<ICMC> getServicingICMCName() {
		LOG.error("Going to Fetch ICMC Name");
		JPAQuery jpaQuery = getFromQueryForICMC();
		jpaQuery.where(QICMC.iCMC.status.ne(Status.DELETED));
		List<ICMC> icmcList = jpaQuery.list(QICMC.iCMC);
		return icmcList;
	}

	@Override
	public boolean saveBranch(Branch branch) {
		LOG.error("Going to insert branch records");
		em.persist(branch);
		return true;
	}
	
	@Override
	public Branch isSolIdValid(String solId) {
		JPAQuery jpaQuery = getFromQueryForBranch();
		jpaQuery.where(QBranch.branch1.solId.eq(solId)
				.and(QBranch.branch1.status.ne(Status.DELETED)));
		return jpaQuery.singleResult(QBranch.branch1);
	}
	
	@Override
	public Branch isBranchNameValid(String branch) {
		JPAQuery jpaQuery = getFromQueryForBranch();
		jpaQuery.where(QBranch.branch1.branch.equalsIgnoreCase(branch)
				.and(QBranch.branch1.status.ne(Status.DELETED)));
		return jpaQuery.singleResult(QBranch.branch1);
	}

	@Override
	public boolean uploadBranch(List<Branch> branchList, Branch branch) {
		LOG.error("Going to insert list of branches");
		for(Branch branchTemp : branchList){
			branchTemp.setInsertBy(branch.getInsertBy());
			branchTemp.setUpdateBy(branch.getUpdateBy());
			branchTemp.setInsertTime(branch.getInsertTime());
			branchTemp.setUpdateTime(branch.getUpdateTime());
			branchTemp.setStatus(Status.ENABLED);
			em.persist(branchTemp);
		}
		return true;
	}

	@Override
	public boolean RemoveBranch(Branch branch) {
		em.merge(branch);
		return true;
	}

	@Override
	public Branch getBranchById(Long id) {
		return em.find(Branch.class, id);
	}

	@Override
	public void updateBranch(Branch branch) {
		em.merge(branch);
	}

	private JPAQuery getFromQueryForRBIMaster() {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QRbiMaster.rbiMaster);
		return jpaQuery;
	}
	
	@Override
	public List<RbiMaster> getRBIMasterList() {
		JPAQuery jpaQuery = getFromQueryForRBIMaster();
		jpaQuery.where(QRbiMaster.rbiMaster.status.eq(Status.ENABLED));
		List<RbiMaster> rbiMasterList = jpaQuery.list(QRbiMaster.rbiMaster);
		return rbiMasterList;
	}

	@Override
	public List<RbiMaster> getZoneAndRegion(String rbiName) {
		JPAQuery jpaQuery = getFromQueryForRBIMaster();
		jpaQuery.where(QRbiMaster.rbiMaster.rbiname.equalsIgnoreCase(rbiName)
				.and(QRbiMaster.rbiMaster.status.eq(Status.ENABLED)));
		List<RbiMaster> zoneAndRegionList = jpaQuery.list(QRbiMaster.rbiMaster);
		return zoneAndRegionList;
	}

	@Override
	public List<String> getRBINameList() {
		JPAQuery jpaQuery = getFromQueryForRBIMaster();
		jpaQuery.where(QRbiMaster.rbiMaster.status.ne(Status.DELETED));
		List<String> rbiNameList = jpaQuery.list(QRbiMaster.rbiMaster.rbiname);
		return rbiNameList;
	}
	
	private JPAQuery getFromQueryForRegion() {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QZoneMaster.zoneMaster);
		return jpaQuery;
	}

	@Override
	public List<ZoneMaster> getRegionList(Branch branch) {
		List<ZoneMaster> regionList = null;
		if (branch.getZone() != null && branch.getZone().value().length() > 0) {
			JPAQuery jpaQuery = getFromQueryForRegion();
			jpaQuery.where(QZoneMaster.zoneMaster.zone.eq(branch.getZone()));
			regionList = jpaQuery.list(QZoneMaster.zoneMaster);
		} else {
			regionList = new ArrayList<ZoneMaster>();
		}
		return regionList;
	}

}
