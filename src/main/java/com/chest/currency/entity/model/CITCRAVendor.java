/*******************************************************************************
 * /* Copyright (C) Indicsoft Technologies Pvt Ltd
 * * All Rights Reserved.
 *******************************************************************************/
package com.chest.currency.entity.model;

import java.util.Calendar;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.springframework.format.annotation.DateTimeFormat;

import com.chest.currency.enums.DateTimePattern;
import com.chest.currency.enums.Status;
import com.chest.currency.enums.Zone;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;


@Entity(name = "CITCRAVendor")
@Table(name = "CIT_CRA_VENDOR")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Data
@EqualsAndHashCode(of = {"id"})
@ToString
public class CITCRAVendor {
	
	@Id
	//@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	@GeneratedValue(generator = "CIT_CRA_VENDOR_SEQ")
	@SequenceGenerator(name = "CIT_CRA_VENDOR_SEQ", sequenceName = "CIT_CRA_VENDOR_SEQ", allocationSize = 100)
	protected Long id;
	
	@Basic
	@Column(name = "NAME")
	protected String name;
	
	@Basic
	@Column(name = "TYPE_ONE")
	protected String typeOne;
	
	@Basic
	@Column(name = "TYPE_TWO")
	protected String typeTwo;
	
	@Basic
	@Column(name = "TYPE_THREE")
	protected String typeThree;
	
	@Basic
	@Column(name = "FPR_NAME")
	protected String FPRName;
	
	@Basic
	@Column(name = "FPR_NUMBER")
	protected String FPRNumber;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "STATUS" )
	protected Status status;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "ZONE")
	protected Zone zone;
	
	@Basic
	@Column(name = "REGION")
	protected String region;
	
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
	
	@Basic
	@Column(name = "REASON_FOR_DELETION")
	protected String reasonForDeletion;
	
	@Basic
	@Column(name = "APPROVAL_FOR_DELETION")
	protected String approvalForDeletion;
	
	@ManyToMany(targetEntity = ICMC.class, fetch = FetchType.EAGER)
	@JoinTable(name = "CIT_CRA_VENDOR_ICMC_ASSO", joinColumns = @JoinColumn (name = "VENDOR_ID", referencedColumnName = "ID"), 
	inverseJoinColumns = @JoinColumn(name = "ICMC_ID", referencedColumnName = "ID"))
	protected List<ICMC> icmcList;
	
	@Transient
	protected List<Long> icmcIds;
	
}
