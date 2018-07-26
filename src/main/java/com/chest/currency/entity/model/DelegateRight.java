/*******************************************************************************
 * /* Copyright (C) Indicsoft Technologies Pvt Ltd
 * * All Rights Reserved.
 *******************************************************************************/
package com.chest.currency.entity.model;

import java.math.BigInteger;
import java.util.Calendar;
import java.util.Date;

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
import javax.persistence.ManyToOne;
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
import com.chest.currency.enums.Status;

@Entity(name="DelegateRight")
@Table(name="DELEGATE_RIGHT")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Data
@EqualsAndHashCode(of = {"id"})
@ToString
public class DelegateRight {
	
	@Id
	//@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	@GeneratedValue(generator = "DELEGATE_SEQ")
	@SequenceGenerator(name = "DELEGATE_SEQ", sequenceName = "DELEGATE_SEQ", allocationSize = 100)
	protected Long id;
	
	@Basic
	@Column(name = "USER_ID")
	protected String userId;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ROLE")
	protected Role role;
	
	@Transient
	protected String roleId;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "STATUS")
	protected Status status;
	
	@DateTimeFormat(pattern = DateTimePattern.yyyy_MM_dd)
	@Column(name = "PERIOD_FROM")
	protected Date periodFrom;
	
	@DateTimeFormat(pattern = DateTimePattern.yyyy_MM_dd)
	@Column(name = "PERIOD_TO")
	protected Date periodTo;

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

}
