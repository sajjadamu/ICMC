/*******************************************************************************
 * /* Copyright (C) Indicsoft Technologies Pvt Ltd
 * * All Rights Reserved.
 *******************************************************************************/
package com.chest.currency.jpa.dao;

import java.math.BigInteger;
import java.util.List;

import com.chest.currency.entity.model.FakeNote;

public interface FakeNoteJpaDao {

	public boolean fakeNoteEntry(FakeNote fakeNote);
	
	public List<FakeNote> getFakeNoteList(BigInteger icmcId);
	
	public boolean uploadFakeNote(List<FakeNote> fakeNoteList, FakeNote fakeNote);
	
	public FakeNote getFakeNoteById(long id);
	
	public boolean updateFakeNote(FakeNote fakeNote);
}
