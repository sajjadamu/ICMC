/*******************************************************************************
 * /* Copyright (C) Indicsoft Technologies Pvt Ltd
 * * All Rights Reserved.
 *******************************************************************************/
package com.chest.currency.service;

import java.math.BigInteger;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chest.currency.entity.model.FakeNote;
import com.chest.currency.jpa.dao.FakeNoteJpaDao;

@Service
@Transactional
public class FakeNoteServiceImpl implements FakeNoteService {
	
	private static final Logger LOG = LoggerFactory.getLogger(FakeNoteServiceImpl.class);

	@Autowired
	protected FakeNoteJpaDao fakeNoteJpaDao;

	@Override
	public boolean fakeNoteEntry(FakeNote fakeNote) {
		LOG.info("Fake Note Entry");
		boolean isSucessfull = fakeNoteJpaDao.fakeNoteEntry(fakeNote);
		return isSucessfull;
	}

	@Override
	public List<FakeNote> getFakeNoteList(BigInteger icmcId) {
		List<FakeNote> fakeNoteList = fakeNoteJpaDao.getFakeNoteList(icmcId);
		return fakeNoteList;
	}

	@Override
	public boolean uploadFakeNote(List<FakeNote> fakeNoteList, FakeNote fakeNote) {
		boolean isSuccessfull = fakeNoteJpaDao.uploadFakeNote(fakeNoteList, fakeNote);
		return isSuccessfull;
	}

	@Override
	public FakeNote getFakeNoteById(long id) {
		return fakeNoteJpaDao.getFakeNoteById(id);
	}

	@Override
	public boolean updateFakeNote(FakeNote fakeNote) {
		boolean isSuccessfull = fakeNoteJpaDao.updateFakeNote(fakeNote);
		return isSuccessfull;
	}

}
