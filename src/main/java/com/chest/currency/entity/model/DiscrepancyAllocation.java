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
import javax.persistence.Transient;

import org.springframework.format.annotation.DateTimeFormat;

import com.chest.currency.enums.DateTimePattern;
import com.chest.currency.enums.OtherStatus;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author root
 *
 */
@Entity(name = "DiscrepancyAllocation")
@Table(name = "DISCREPANCY_ALLOCATION")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Data
@EqualsAndHashCode(of = { "id" })
@ToString
public class DiscrepancyAllocation {
	
	@Id
	//@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	@GeneratedValue(generator = "DISCREPANCY_ALLOCATION_SEQ")
	@SequenceGenerator(name = "DISCREPANCY_ALLOCATION_SEQ", sequenceName = "DISCREPANCY_ALLOCATION_SEQ", allocationSize = 100)
	protected Long id;
	
	@Basic
	@Column(name = "DISCREPANCY_ID")
	protected Long discrepancyId;
	
	@Basic
	@Column(name = "DENOMINATION")
	protected Integer denomination;

	@Basic
	@Column(name = "DIS_VALUE")
	protected Integer value;
	
	@Basic
	@Column(name = "SR_NUMBER")
	protected String srNumber;
	
	@Basic
	@Column(name = "NUMBER_OF_NOTES")
	protected Integer numberOfNotes;
	
	@Basic
	@Column(name = "NOTE_SERIAL_NUMBER")
	protected String noteSerialNumber;
	
	@Basic
	@Column(name = "DISCREPANCY_TYPE")
	protected String discrepancyType;

	@Basic
	@Column(name = "MUTIL_TYPE")
	protected String mutilType;
	
	@Basic
	@Column(name = "REMARKS")
	protected String remarks;
	
	@Basic
	@Column(name = "ICMC_ID")
	protected BigInteger icmcId;
	
	@Basic
	@Column(name = "PRINT_YEAR")
	protected Integer printYear;
	
	@Basic
	@Column(name = "DATE_ON_SHRINK_WRAP")
	protected Date dateOnShrinkWrap;

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
	@Column(name = "DISCREPANCY_DATE")
	protected Date discrepancyDate;

	@Basic
	@Column(name = "MACHINE_NUMBER")
	protected Integer machineNumber;
	
	@Basic
	@Column(name = "SOL_ID")
	protected Integer solId;

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

	@Enumerated(EnumType.STRING)
	@Column(name = "STATUS")
	protected OtherStatus status;
	
	@Transient
	protected BigDecimal sairremTotal;
	@Transient
	protected BigDecimal samutcurTotal;
	@Transient
	protected BigDecimal sadscashTotal;
	@Transient
	protected BigDecimal excessTotal;
	
	
	@Transient
	protected BigDecimal sairrem;
	@Transient
	protected BigDecimal samutcur;
	@Transient
	protected BigDecimal sadscash;
	@Transient
	protected BigDecimal excess;
	
	@Transient
	protected BigDecimal fakeNotes2000Total;
	@Transient
	protected BigDecimal fakeNotes500Total;
	@Transient
	protected BigDecimal fakeNotes100Total;
	@Transient
	protected BigDecimal fakeNotesTotalValue;
	
	@Transient
	protected BigDecimal mutilatedNotes2000Total;
	@Transient
	protected BigDecimal mutilatedNotes500Total;
	@Transient
	protected BigDecimal mutilatedNotes100Total;
	@Transient
	protected BigDecimal mutilatedNotesTotalValue;
	
	@Transient
	protected BigDecimal shortageNotes2000Total;
	@Transient
	protected BigDecimal shortageNotes500Total;
	@Transient
	protected BigDecimal shortageNotes100Total;
	@Transient
	protected BigDecimal shortageNotesTotalValue;
	
	@Transient
	protected BigDecimal excessNotes2000Total;
	@Transient
	protected BigDecimal excessNotes500Total;
	@Transient
	protected BigDecimal excessNotes100Total;
	@Transient
	protected BigDecimal excessNotesTotalValue;
	
	
	
	@Transient
	protected Integer denom1Pieces;
	@Transient
	protected Integer denom2Pieces;
	@Transient
	protected Integer denom5Pieces;
	@Transient
	protected Integer denom10Pieces;
	@Transient
	protected Integer denom20Pieces;
	@Transient
	protected Integer denom50Pieces;
	@Transient
	protected Integer denom100Pieces;
	@Transient
	protected Integer denom200Pieces;
	@Transient
	protected Integer denom500Pieces;
	@Transient
	protected Integer denom1000Pieces;
	@Transient
	protected Integer denom2000Pieces;
	@Transient
	protected Integer totalInPieces;
	@Transient
	protected Integer totalValueOfBankNotes;
	/**
	 * Default Constructor
	 */
	public DiscrepancyAllocation(){
		
	}
	
	public DiscrepancyAllocation(boolean initialize){
		if(initialize){
			this.fakeNotes2000Total = BigDecimal.ZERO;
			this.fakeNotes500Total = BigDecimal.ZERO;
			this.fakeNotes100Total = BigDecimal.ZERO;
			this.fakeNotesTotalValue = BigDecimal.ZERO;
			
			this.mutilatedNotes2000Total = BigDecimal.ZERO;
			this.mutilatedNotes500Total = BigDecimal.ZERO;
			this.mutilatedNotes100Total = BigDecimal.ZERO;
			this.mutilatedNotesTotalValue = BigDecimal.ZERO;
			
			this.shortageNotes2000Total = BigDecimal.ZERO;
			this.shortageNotes500Total = BigDecimal.ZERO;
			this.shortageNotes100Total = BigDecimal.ZERO;
			this.shortageNotesTotalValue = BigDecimal.ZERO;
			
			this.excessNotes2000Total = BigDecimal.ZERO;
			this.excessNotes500Total = BigDecimal.ZERO;
			this.excessNotes100Total = BigDecimal.ZERO;
			this.excessNotesTotalValue = BigDecimal.ZERO;
			
			this.denom1Pieces = 0;
			this.denom2Pieces = 0;
			this.denom5Pieces = 0;
			this.denom10Pieces = 0;
			this.denom20Pieces = 0;
			this.denom50Pieces = 0;
			this.denom100Pieces = 0;
			this.denom200Pieces = 0;
			this.denom500Pieces = 0;
			this.denom1000Pieces = 0;
			this.denom2000Pieces = 0;
			this.totalInPieces = 0;
			this.totalValueOfBankNotes = 0;
		}
		
	}

}
