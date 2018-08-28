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

import org.springframework.format.annotation.DateTimeFormat;

import com.chest.currency.enums.DateTimePattern;
import com.chest.currency.enums.Status;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity(name = "ServicingBranch")
@Table(name = "SERVICING_BRANCH")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Data
@EqualsAndHashCode(of = { "id" })
@ToString
public class ServicingBranch {

	@Id
	// @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	@GeneratedValue(generator = "SERVICING_BRANCH_SEQ")
	@SequenceGenerator(name = "SERVICING_BRANCH_SEQ", sequenceName = "SERVICING_BRANCH_SEQ", allocationSize = 100)
	protected Long id;

	@Basic
	@Column(name = "ICMC_NAME")
	protected String icmcName;

	@Basic
	@Column(name = "SOL_ID")
	protected Integer solId;

	@Basic
	@Column(name = "BRANCH")
	protected String branchName;

	@Basic
	@Column(name = "RBI_JI")
	protected String rbiJI;

	@Basic
	@Column(name = "RBI_SI")
	protected String rbiSI;

	@Basic
	@Column(name = "CATEGORY")
	protected String category;

	@Basic
	@Column(name = "RBI_ICMC")
	protected String rbiICMC;

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

}
