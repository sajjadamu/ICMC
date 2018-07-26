/*******************************************************************************
 * /* Copyright (C) Indicsoft Technologies Pvt Ltd
 * * All Rights Reserved.
 *******************************************************************************/
/**
 * 
 */
package com.chest.currency.entity.model;

import java.math.BigInteger;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

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
@Entity(name = "SoiledRemittance")
@Table(name = "SOILED_REMITTANCE")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Data
@EqualsAndHashCode(of = { "id" })
@ToString
public class SoiledRemittance {
	
	@Id
	//@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	@GeneratedValue(generator = "SOILED_REMITTANCE_SEQ")
	@SequenceGenerator(name = "SOILED_REMITTANCE_SEQ", sequenceName = "SOILED_REMITTANCE_SEQ", allocationSize = 100)
	protected Long id;
	
	@Basic
	@Column(name = "ICMC_ID")
	protected BigInteger icmcId;

	@Basic
	@Column(name = "REMITTANCE_ORDER_NO")
	protected BigInteger remittanceOrderNo;

	@Basic
	@Column(name = "ORDER_DATE")
	protected Date orderDate;

	@Basic
	@Column(name = "APPROVED_REMITTANCE_DATE")
	protected Date approvedRemittanceDate;

	@Basic
	@Column(name = "NOTES")
	protected String notes;

	@Basic
	@Column(name = "TYPES")
	protected String types;

	@Basic
	@Column(name = "VEHICLE_NUMBER")
	protected String vehicleNumber;

	@Basic
	@Column(name = "LOCATION")
	protected String location;

	@Basic
	@Column(name = "INSERT_BY")
	protected String insertBy;

	@Enumerated(EnumType.STRING)
	@Column(name = "STATUS")
	protected OtherStatus status;
	
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
	
	@OneToMany (cascade=CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name="SOILED_REMITTANCE_ID", referencedColumnName="ID")
	protected List<SoiledRemittanceAllocation> remittanceAllocations;
}
