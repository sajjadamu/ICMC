/*******************************************************************************
 * /* Copyright (C) Indicsoft Technologies Pvt Ltd
 * * All Rights Reserved.
 *******************************************************************************/
/**
 * 
 */
package com.chest.currency.dao.mapper;

import java.math.BigDecimal;
import java.util.List;

import com.chest.currency.entity.model.Sas;
import com.chest.currency.enums.CurrencyType;
import com.chest.currency.viewBean.SASReleased;

/**
 * @author root
 *
 */
public class SASReleaseMapper {
	public static Sas mapSasReleaseToSas(List<SASReleased> sasReleaseList, Sas sas) {
		SASReleased sasReleased = null;
		if (sas.getTotalValueOfNotesRs1F().compareTo(BigDecimal.ZERO) != 0
				&& sas.getTotalValueOfNotesRs1F().compareTo(BigDecimal.ZERO) > 0
				&& sas.getTotalValueOfNotesRs1F().compareTo(sas.getReleasedNotes1F()) > 0) {
			sasReleased = new SASReleased();
			sasReleased.setId(sas.getId());
			sasReleased.setDenomination(1);
			sasReleased.setCategory(CurrencyType.FRESH);
			sasReleased.setBundle(sas.getTotalValueOfNotesRs1F());
			sasReleaseList.add(sasReleased);
		}
		if (sas.getTotalValueOfNotesRs1I().compareTo(BigDecimal.ZERO) != 0
				&& sas.getTotalValueOfNotesRs1I().compareTo(BigDecimal.ZERO) > 0
				&& sas.getTotalValueOfNotesRs1I().compareTo(sas.getReleasedNotes1I()) > 0) {
			sasReleased = new SASReleased();
			sasReleased.setId(sas.getId());
			sasReleased.setDenomination(1);
			sasReleased.setCategory(CurrencyType.ISSUABLE);
			sasReleased.setBundle(sas.getTotalValueOfNotesRs1I());
			sasReleaseList.add(sasReleased);
		}
		if (sas.getTotalValueOfNotesRs10F().compareTo(BigDecimal.ZERO) != 0
				&& sas.getTotalValueOfNotesRs10F().compareTo(BigDecimal.ZERO) > 0
				&& sas.getTotalValueOfNotesRs10F().compareTo(sas.getReleasedNotes10F()) > 0) {
			sasReleased = new SASReleased();
			sasReleased.setId(sas.getId());
			sasReleased.setDenomination(10);
			sasReleased.setCategory(CurrencyType.FRESH);
			sasReleased.setBundle(sas.getTotalValueOfNotesRs10F());
			sasReleaseList.add(sasReleased);
		}
		if (sas.getTotalValueOfNotesRs10I().compareTo(BigDecimal.ZERO) != 0
				&& sas.getTotalValueOfNotesRs10I().compareTo(BigDecimal.ZERO) > 0
				&& sas.getTotalValueOfNotesRs10I().compareTo(sas.getReleasedNotes10I()) > 0) {
			sasReleased = new SASReleased();
			sasReleased.setId(sas.getId());
			sasReleased.setDenomination(10);
			sasReleased.setCategory(CurrencyType.ISSUABLE);
			sasReleased.setBundle(sas.getTotalValueOfNotesRs10I());
			sasReleaseList.add(sasReleased);
		}
		if (sas.getTotalValueOfNotesRs100F().compareTo(BigDecimal.ZERO) != 0
				&& sas.getTotalValueOfNotesRs100F().compareTo(BigDecimal.ZERO) > 0
				&& sas.getTotalValueOfNotesRs100F().compareTo(sas.getReleasedNotes100F()) > 0) {
			sasReleased = new SASReleased();
			sasReleased.setId(sas.getId());
			sasReleased.setDenomination(100);
			sasReleased.setCategory(CurrencyType.FRESH);
			sasReleased.setBundle(sas.getTotalValueOfNotesRs100F());
			sasReleaseList.add(sasReleased);
		}
		if (sas.getTotalValueOfNotesRs100I().compareTo(BigDecimal.ZERO) != 0
				&& sas.getTotalValueOfNotesRs100I().compareTo(BigDecimal.ZERO) > 0
				&& sas.getTotalValueOfNotesRs100I().compareTo(sas.getReleasedNotes100I()) > 0) {
			sasReleased = new SASReleased();
			sasReleased.setId(sas.getId());
			sasReleased.setDenomination(100);
			sasReleased.setCategory(CurrencyType.ISSUABLE);
			sasReleased.setBundle(sas.getTotalValueOfNotesRs100I());
			sasReleaseList.add(sasReleased);
		}
		if (sas.getTotalValueOfNotesRs1000F().compareTo(BigDecimal.ZERO) != 0
				&& sas.getTotalValueOfNotesRs1000F().compareTo(BigDecimal.ZERO) > 0
				&& sas.getTotalValueOfNotesRs1000F().compareTo(sas.getReleasedNotes1000F()) > 0) {
			sasReleased = new SASReleased();
			sasReleased.setId(sas.getId());
			sasReleased.setDenomination(1000);
			sasReleased.setCategory(CurrencyType.FRESH);
			sasReleased.setBundle(sas.getTotalValueOfNotesRs1000F());
			sasReleaseList.add(sasReleased);
		}

		if (sas.getTotalValueOfNotesRs1000I().compareTo(BigDecimal.ZERO) != 0
				&& sas.getTotalValueOfNotesRs1000I().compareTo(BigDecimal.ZERO) > 0
				&& sas.getTotalValueOfNotesRs1000I().compareTo(sas.getReleasedNotes1000I()) > 0) {
			sasReleased = new SASReleased();
			sasReleased.setId(sas.getId());
			sasReleased.setDenomination(1000);
			sasReleased.setCategory(CurrencyType.ISSUABLE);
			sasReleased.setBundle(sas.getTotalValueOfNotesRs1000I());
			sasReleaseList.add(sasReleased);
		}

		if (sas.getTotalValueOfNotesRs2F().compareTo(BigDecimal.ZERO) != 0
				&& sas.getTotalValueOfNotesRs2F().compareTo(BigDecimal.ZERO) > 0
				&& sas.getTotalValueOfNotesRs2F().compareTo(sas.getReleasedNotes2F()) > 0) {
			sasReleased = new SASReleased();
			sasReleased.setId(sas.getId());
			sasReleased.setDenomination(2);
			sasReleased.setCategory(CurrencyType.FRESH);
			sasReleased.setBundle(sas.getTotalValueOfNotesRs2F());
			sasReleaseList.add(sasReleased);
		}
		if (sas.getTotalValueOfNotesRs2I().compareTo(BigDecimal.ZERO) != 0
				&& sas.getTotalValueOfNotesRs2I().compareTo(BigDecimal.ZERO) > 0
				&& sas.getTotalValueOfNotesRs2I().compareTo(sas.getReleasedNotes2I()) > 0) {
			sasReleased = new SASReleased();
			sasReleased.setId(sas.getId());
			sasReleased.setDenomination(2);
			sasReleased.setCategory(CurrencyType.ISSUABLE);
			sasReleased.setBundle(sas.getTotalValueOfNotesRs2I());
			sasReleaseList.add(sasReleased);
		}

		if (sas.getTotalValueOfNotesRs20F().compareTo(BigDecimal.ZERO) != 0
				&& sas.getTotalValueOfNotesRs20F().compareTo(BigDecimal.ZERO) > 0
				&& sas.getTotalValueOfNotesRs20F().compareTo(sas.getReleasedNotes20F()) > 0) {
			sasReleased = new SASReleased();
			sasReleased.setId(sas.getId());
			sasReleased.setDenomination(20);
			sasReleased.setCategory(CurrencyType.FRESH);
			sasReleased.setBundle(sas.getTotalValueOfNotesRs20F());
			sasReleaseList.add(sasReleased);
		}
		if (sas.getTotalValueOfNotesRs20I().compareTo(BigDecimal.ZERO) != 0
				&& sas.getTotalValueOfNotesRs20I().compareTo(BigDecimal.ZERO) > 0
				&& sas.getTotalValueOfNotesRs20I().compareTo(sas.getReleasedNotes20I()) > 0) {
			sasReleased = new SASReleased();
			sasReleased.setId(sas.getId());
			sasReleased.setDenomination(20);
			sasReleased.setCategory(CurrencyType.ISSUABLE);
			sasReleased.setBundle(sas.getTotalValueOfNotesRs20I());
			sasReleaseList.add(sasReleased);
		}

		if (sas.getTotalValueOfNotesRs5F().compareTo(BigDecimal.ZERO) != 0
				&& sas.getTotalValueOfNotesRs5F().compareTo(BigDecimal.ZERO) > 0
				&& sas.getTotalValueOfNotesRs5F().compareTo(sas.getReleasedNotes5F()) > 0) {
			sasReleased = new SASReleased();
			sasReleased.setId(sas.getId());
			sasReleased.setDenomination(5);
			sasReleased.setCategory(CurrencyType.FRESH);
			sasReleased.setBundle(sas.getTotalValueOfNotesRs5F());
			sasReleaseList.add(sasReleased);
		}

		if (sas.getTotalValueOfNotesRs5I().compareTo(BigDecimal.ZERO) != 0
				&& sas.getTotalValueOfNotesRs5I().compareTo(BigDecimal.ZERO) > 0
				&& sas.getTotalValueOfNotesRs5I().compareTo(sas.getReleasedNotes5I()) > 0) {
			sasReleased = new SASReleased();
			sasReleased.setId(sas.getId());
			sasReleased.setDenomination(5);
			sasReleased.setCategory(CurrencyType.ISSUABLE);
			sasReleased.setBundle(sas.getTotalValueOfNotesRs5I());
			sasReleaseList.add(sasReleased);
		}

		if (sas.getTotalValueOfNotesRs50F().compareTo(BigDecimal.ZERO) != 0
				&& sas.getTotalValueOfNotesRs50F().compareTo(BigDecimal.ZERO) > 0
				&& sas.getTotalValueOfNotesRs50F().compareTo(sas.getReleasedNotes50F()) > 0) {
			sasReleased = new SASReleased();
			sasReleased.setId(sas.getId());
			sasReleased.setDenomination(50);
			sasReleased.setCategory(CurrencyType.FRESH);
			sasReleased.setBundle(sas.getTotalValueOfNotesRs50F());
			sasReleaseList.add(sasReleased);
		}

		if (sas.getTotalValueOfNotesRs50I().compareTo(BigDecimal.ZERO) != 0
				&& sas.getTotalValueOfNotesRs50I().compareTo(BigDecimal.ZERO) > 0
				&& sas.getTotalValueOfNotesRs50I().compareTo(sas.getReleasedNotes50I()) > 0) {
			sasReleased = new SASReleased();
			sasReleased.setId(sas.getId());
			sasReleased.setDenomination(50);
			sasReleased.setCategory(CurrencyType.ISSUABLE);
			sasReleased.setBundle(sas.getTotalValueOfNotesRs50I());
			sasReleaseList.add(sasReleased);
		}

		if (sas.getTotalValueOfNotesRs500F().compareTo(BigDecimal.ZERO) != 0
				&& sas.getTotalValueOfNotesRs500F().compareTo(BigDecimal.ZERO) > 0
				&& sas.getTotalValueOfNotesRs500F().compareTo(sas.getReleasedNotes500F()) > 0) {
			sasReleased = new SASReleased();
			sasReleased.setId(sas.getId());
			sasReleased.setDenomination(500);
			sasReleased.setCategory(CurrencyType.FRESH);
			sasReleased.setBundle(sas.getTotalValueOfNotesRs500F());
			sasReleaseList.add(sasReleased);
		}
		if (sas.getTotalValueOfNotesRs500I().compareTo(BigDecimal.ZERO) != 0
				&& sas.getTotalValueOfNotesRs500I().compareTo(BigDecimal.ZERO) > 0
				&& sas.getTotalValueOfNotesRs500I().compareTo(sas.getReleasedNotes500I()) > 0) {
			sasReleased = new SASReleased();
			sasReleased.setId(sas.getId());
			sasReleased.setDenomination(500);
			sasReleased.setCategory(CurrencyType.ISSUABLE);
			sasReleased.setBundle(sas.getTotalValueOfNotesRs500I());
			sasReleaseList.add(sasReleased);
		}
		return sas;
	}

	public static List<SASReleased> mapSasToSasRelease(List<SASReleased> sasReleaseList, Sas sas) {
		//
		return sasReleaseList;
	}
}
