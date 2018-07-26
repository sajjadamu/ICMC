/*******************************************************************************
 * /* Copyright (C) Indicsoft Technologies Pvt Ltd
 * * All Rights Reserved.
 *******************************************************************************/
package com.chest.currency.jpa.dao;

import java.math.BigInteger;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.chest.currency.entity.model.FakeNote;
import com.chest.currency.entity.model.QFakeNote;
import com.mysema.query.jpa.impl.JPAQuery;

@Repository
public class FakeNoteJpaDaoImpl implements FakeNoteJpaDao {
	
	private static final Logger LOG = LoggerFactory.getLogger(FakeNoteJpaDaoImpl.class);

	@PersistenceContext
	private EntityManager em;

	@Override
	public boolean fakeNoteEntry(FakeNote fakeNote) {
		LOG.info("");
		em.persist(fakeNote);
		return true;
	}
	
	private JPAQuery getFromQueryForFakeNote() {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QFakeNote.fakeNote);
		return jpaQuery;
	}

	@Override
	public List<FakeNote> getFakeNoteList(BigInteger icmcId) {
		JPAQuery jpaQuery = getFromQueryForFakeNote();
		jpaQuery.where(QFakeNote.fakeNote.icmcId.eq(icmcId));
		List<FakeNote> fakeNoteList = jpaQuery.list(QFakeNote.fakeNote);
		return fakeNoteList;
	}

	@Override
	public boolean uploadFakeNote(List<FakeNote> fakeNoteList, FakeNote fakeNote) {
		LOG.info("Going to insert list of Fake note");
		for(FakeNote fake : fakeNoteList){
			fake.setInsertBy(fakeNote.getInsertBy());
			fake.setUpdateBy(fakeNote.getUpdateBy());
			fake.setInsertTime(fakeNote.getInsertTime());
			fake.setUpdateTime(fakeNote.getUpdateTime());
			em.persist(fake);
		}
		return true;
	}

	@Override
	public FakeNote getFakeNoteById(long id) {
		return em.find(FakeNote.class,id);
	}

	@Override
	public boolean updateFakeNote(FakeNote fakeNote) {
		em.merge(fakeNote);
		return true;
	}

}
