/*******************************************************************************
 * /* Copyright (C) Indicsoft Technologies Pvt Ltd
 * * All Rights Reserved.
 *******************************************************************************/
package com.chest.currency.entity.model;

import java.math.BigDecimal;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.chest.currency.enums.CurrencyType;
import com.chest.currency.enums.Zone;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity(name = "Summary")
@Table(name = "ZONE_WISE_SUMMARY")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Data
@EqualsAndHashCode(of = { "id" })
@ToString
public class Summary {

	@Id
	//@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	@GeneratedValue(generator = "ZONE_WISE_SUMMARY_SEQ")
	@SequenceGenerator(name = "ZONE_WISE_SUMMARY_SEQ", sequenceName = "ZONE_WISE_SUMMARY_SEQ", allocationSize = 100)
	protected Long id;

	@Basic
	@Column(name = "TOTAL_VALUE")
	protected BigDecimal totalValue;

	@Enumerated(EnumType.STRING)
	@Column(name = "BIN_TYPE")
	protected CurrencyType binType;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "ZONE")
	protected Zone zone;

}
