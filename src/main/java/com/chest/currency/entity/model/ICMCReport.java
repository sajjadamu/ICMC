/*******************************************************************************
 * /* Copyright (C) Indicsoft Technologies Pvt Ltd
 * * All Rights Reserved.
 *******************************************************************************/
package com.chest.currency.entity.model;

import java.util.Calendar;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

import com.chest.currency.enums.DateTimePattern;
import com.chest.currency.enums.ReportType;
import com.chest.currency.enums.Status;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity(name = "ICMCReport")
@Table(name = "ICMC_REPORT")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Data
@EqualsAndHashCode(of = {"id"})
@ToString
public class ICMCReport {
	
	@Id
	//@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	@GeneratedValue(generator = "ICMC_REPORT_SEQ")
	@SequenceGenerator(name = "ICMC_REPORT_SEQ", sequenceName = "ICMC_REPORT_SEQ", allocationSize = 100)
	protected int id;
	
	@Basic
	@Column(name = "CUSTOM_REPORT_TYPE")
	protected String newReportType;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "STATUS")
	protected Status status;
	
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
	
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "ICMC_REPORT_ASSOCIATION", joinColumns = @JoinColumn (name = "REPORT_ID"))
	@Column(name = "REPORT_TYPE")
	@Enumerated(EnumType.STRING)
	protected Set<ReportType> reportType;
	
	@Basic
	@Column(name = "REASON_FOR_DELETION")
	protected String regionForDeletion;
	
	@Basic
	@Column(name = "APPROVAL_FOR_DELETION")
	protected String approvalForDeletion;

}
