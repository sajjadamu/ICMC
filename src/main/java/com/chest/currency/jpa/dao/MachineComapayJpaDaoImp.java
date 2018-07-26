/*******************************************************************************
 * /* Copyright (C) Indicsoft Technologies Pvt Ltd
 * * All Rights Reserved.
 *******************************************************************************/
package com.chest.currency.jpa.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.chest.currency.entity.model.MachineCompany;
import com.chest.currency.entity.model.QMachineCompany;
import com.chest.currency.enums.Status;
import com.mysema.query.jpa.impl.JPAQuery;

@Repository
public class MachineComapayJpaDaoImp implements MachineCompanyJpaDao {

	@PersistenceContext
	protected EntityManager em;
	
	
	public JPAQuery getFromQueryForMachineCompany()
	{
		JPAQuery jpaQuery=new JPAQuery(em);
		jpaQuery.from(QMachineCompany.machineCompany);
		return jpaQuery;
	}
	
	@Override
	public List<MachineCompany> getMachineCompany() {
		JPAQuery jpaQuery=getFromQueryForMachineCompany();
		jpaQuery.where(QMachineCompany.machineCompany.status.ne(Status.DELETED));
		List<MachineCompany> machinCompanyList 	=jpaQuery.list(QMachineCompany.machineCompany);
		return machinCompanyList;
	}
	
	@Override
	public boolean insertMachineCompany(MachineCompany machineCompany) {
		em.persist(machineCompany);
		return true;
	}
	
	@Override
	public MachineCompany getMachineCompanyForModify(Long id) {
		MachineCompany machineCompany=em.find(MachineCompany.class, id);
		return machineCompany;
	}
	
	@Override
	public boolean updateMachineCompany(MachineCompany machineCompany) {
		em.merge(machineCompany);
		return true;
	}

	@Override
	public MachineCompany isValidMachineCompany(String companyname) {
		JPAQuery jpaQuery=getFromQueryForMachineCompany();
		jpaQuery.where(QMachineCompany.machineCompany.companyname.equalsIgnoreCase(companyname));
		MachineCompany singalMachineCompanyMaster=jpaQuery.singleResult(QMachineCompany.machineCompany);
		return singalMachineCompanyMaster;
	}
}
