/*******************************************************************************
 * /* Copyright (C) Indicsoft Technologies Pvt Ltd
 * * All Rights Reserved.
 *******************************************************************************/
package com.chest.currency.entity.model;

import java.math.BigInteger;
import java.util.Calendar;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.format.annotation.DateTimeFormat;

import com.chest.currency.enums.DateTimePattern;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity(name = "AssignVaultCustodian")
@Table(name = "ASSIGN_VAULT_CUSTODIAN")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Data
@EqualsAndHashCode(of = {"id"})
@ToString
public class AssignVaultCustodian {

	@Id
	//@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	@GeneratedValue(generator = "ASSIGN_VAULT_CUSTODIAN_SEQ")
	@SequenceGenerator(name = "ASSIGN_VAULT_CUSTODIAN_SEQ", sequenceName = "ASSIGN_VAULT_CUSTODIAN_SEQ", allocationSize = 100)
	protected Long id;
	
	@Basic
	@Column(name = "HANDING_OVER_CHARGE")
	protected String handingOverCharge;
	
	@Basic
	@Column(name = "REASON")
	protected String reason;
	
	@Basic
	@Column(name = "REMARKS")
	protected String remarks;
	
	@Basic
	@Column(name = "USER_ID")
	protected String userId;
	
	@Basic
	@Column(name = "CUSTODIAN")
	protected String custodian;
	
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
	@Column(name = "INSERT_TIME")
	protected Calendar insertTime;
	
	@DateTimeFormat(pattern = DateTimePattern.yyyy_MM_dd_HH_mm_ss_SSS)
	@Column(name = "UPDATE_TIME")
	protected Calendar updateTime;
	
	@Transient
	protected String userName;

}
