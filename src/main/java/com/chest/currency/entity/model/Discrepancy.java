/*******************************************************************************
 * /* Copyright (C) Indicsoft Technologies Pvt Ltd
 * * All Rights Reserved.
 *******************************************************************************/
/**
 * 
 */
package com.chest.currency.entity.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import org.springframework.format.annotation.DateTimeFormat;

import com.chest.currency.enums.DateTimePattern;
@Entity(name = "Discrepancy")
@Table(name = "DISCREPANCY")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Data
@EqualsAndHashCode(of = { "id" })
@ToString
public class Discrepancy {
	
	@Id
	@Column(name = "ID")
	@GeneratedValue(generator = "DISCREPANCY_SEQ")
	@SequenceGenerator(name = "DISCREPANCY_SEQ", sequenceName ="DISCREPANCY_SEQ", allocationSize = 100)
	protected Long id;

	@Basic
	@Column(name = "DISCREPANCY_DATE")
	protected Date discrepancyDate;

	@Basic
	@Column(name = "MACHINE_NUMBER")
	protected Integer machineNumber;
	
	@Basic
	@Column(name = "SOL_ID")
	protected Integer solId;
	
	@Basic
	@Column(name = "STATUS")
	protected Integer status;

	@Basic
	@Column(name = "BRANCH")
	protected String branch;

	@Basic
	@Column(name = "FILE_PATH")
	protected String filepath;

	@Basic
	@Column(name = "ACCOUNT_TELLER_CAM")
	protected String accountTellerCam;

	@Basic
	@Column(name = "CUSTOMER_NAME")
	protected String customerName;
	
	@Basic
	@Column(name = "ACCOUNT_NUMBER")
	protected String accountNumber;
	
	@Basic
	@Column(name = "ICMC_ID")
	protected BigInteger icmcId;
	
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
	@Column(name = "NORMAL_OR_SUSPENSE")
	protected String normalOrSuspense;

	@Basic
	@Column(name = "SR_NO")
	protected String srNo;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "DISCREPANCY_ID", referencedColumnName = "ID")
	protected List<DiscrepancyAllocation> discrepancyAllocations;
	
	@Transient
	protected Integer denomination;
	
	@Transient
	protected BigDecimal sairremTotal;
	@Transient
	protected BigDecimal samutcurTotal;
	@Transient
	protected BigDecimal sadscashTotal;
	@Transient
	protected BigDecimal excessTotal;

}
