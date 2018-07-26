/*******************************************************************************
 * /* Copyright (C) Indicsoft Technologies Pvt Ltd
 * * All Rights Reserved.
 *******************************************************************************/
/**
 * 
 */
package com.chest.currency.service;

import java.util.List;

import com.chest.currency.entity.model.Jurisdiction;

/**
 * @author root
 *
 */
public interface JurisdictionService {
	
	public boolean createJurisdiction(Jurisdiction jurisdiction);

	public List<Jurisdiction> getJurisdictionList();

	public Jurisdiction getJurisdictionById(int id);

	public boolean updateJurisdiction(Jurisdiction jurisdiction);
	
	public boolean UploadJurisdiction(List<Jurisdiction> jurisdictionList, Jurisdiction jurisdiction);
}
