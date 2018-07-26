/*******************************************************************************
 * /* Copyright (C) Indicsoft Technologies Pvt Ltd
 * * All Rights Reserved.
 *******************************************************************************/
package com.chest.currency.entity.model;

import java.util.Calendar;

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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import org.springframework.format.annotation.DateTimeFormat;

import com.chest.currency.enums.DateTimePattern;
import com.chest.currency.enums.State;
import com.chest.currency.enums.Status;
import com.chest.currency.enums.Zone;

@Entity(name="RbiMaster")
@Table(name="RBI_MASTER")
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
@Data
@EqualsAndHashCode(of={"id"})
@ToString

public class RbiMaster {

	@Id
	//@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	@GeneratedValue(generator = "RBI_MASTER_SEQ")
	@SequenceGenerator(name = "RBI_MASTER_SEQ", sequenceName = "RBI_MASTER_SEQ", allocationSize = 100)
	protected Long id;
	
	@Basic
	@Column(name="RBI_NAME")
	protected String rbiname;

	@Enumerated(EnumType.STRING)
	@Column(name="ZONE")
	protected Zone zone;
	
	@Basic
	@Column(name="REGION")
	protected String region;
	
	@Enumerated(EnumType.STRING)
	@Column(name="STATE")
	protected State state;
	
	@Basic
	@Column(name="CITY")
	protected String city;
	
	@Enumerated(EnumType.STRING)
	@Column(name="STATUS")
	protected Status status;
	
	@Basic
	@Column(name = "ADDRESS")
	protected String address;

	@Basic
	@Column(name = "PIN_NO")
	protected Integer pinno;

	
	@Basic
	@Column(name = "INSERT_BY")
	protected String insertBy;

	@Basic
	@Column(name = "UPDATE_BY")
	protected String updateBy;

	@DateTimeFormat(pattern = DateTimePattern.yyyy_MM_dd_HH_mm_ss_SSS)
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "INSERT_TIME")
	protected Calendar insertTime;
	
	@DateTimeFormat(pattern = DateTimePattern.yyyy_MM_dd_HH_mm_ss_SSS)
	@Column(name = "UPDATE_TIME")
	protected Calendar updateTime;
}
