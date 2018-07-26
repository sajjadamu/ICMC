/*******************************************************************************
 * /* Copyright (C) Indicsoft Technologies Pvt Ltd
 * * All Rights Reserved.
 *******************************************************************************/
package com.chest.currency.entity.model;

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
import javax.persistence.Transient;

import com.chest.currency.enums.Zone;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity(name = "ZoneMaster")
@Table(name = "ZONE_MASTER")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@EqualsAndHashCode(of = {"id"})
@Data
@ToString
public class ZoneMaster {
	
	@Id
	//@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	@GeneratedValue(generator = "ZONE_MASTER_SEQ")
	@SequenceGenerator(name = "ZONE_MASTER_SEQ", sequenceName = "ZONE_MASTER_SEQ", allocationSize = 100)
	protected Long id;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "ZONE")
	protected Zone zone;
	
	@Basic
	@Column(name = "REGION")
	protected String region;
	
	@Transient
	protected String icmcName;

}
