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
import javax.persistence.Transient;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import com.chest.currency.enums.DateTimePattern;
import com.chest.currency.enums.Status;
import com.chest.currency.enums.Zone;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity(name = "Branch")
@Table(name = "BRANCH")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Data
@EqualsAndHashCode(of = { "id" })
@ToString
public class Branch {

	@Id
	@Column(name = "ID")
	@GeneratedValue(generator = "BRANCH_SEQ")
	@SequenceGenerator(name = "BRANCH_SEQ", sequenceName = "BRANCH_SEQ", allocationSize = 100)
	protected Long id;

	@Basic
	@Column(name = "SOL_ID")
	protected String solId;

	@Basic
	@Column(name = "BRANCH_NAME")
	protected String branch;

	@Basic
	@Column(name = "SERVICING_ICMC")
	protected String servicingICMC;

	@Basic
	@Column(name = "JURISDICTION_ICMC")
	protected String jurisdictionICMC;

	@Basic
	@Column(name = "RBI_NAME")
	protected String rbiName;

	@Enumerated(EnumType.STRING)
	@Column(name = "ZONE")
	protected Zone zone;

	@Basic
	@Column(name = "REGION")
	protected String region;

	@Basic
	@Column(name = "ADDRESS")
	protected String address;

	@Basic
	@Column(name = "CITY")
	protected String city;

	@Basic
	@Column(name = "PINCODE")
	protected Integer pincode;

	@Enumerated(EnumType.STRING)
	@Column(name = "STATUS")
	protected Status status;

	@Basic
	@Column(name = "REASON_FOR_DELETION")
	protected String reasonForDeletion;

	@Basic
	@Column(name = "APPROVAL_FOR_DELETION")
	protected String approvalForDeletion;

	@Basic
	@Column(name = "INSERT_BY")
	protected String insertBy;

	@Basic
	@Column(name = "UPDATE_BY")
	protected String updateBy;

	@DateTimeFormat(pattern = DateTimePattern.yyyy_MM_dd_HH_mm_ss_SSS)
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "INSERT_TIME", nullable = false, updatable = false)
	@CreationTimestamp
	protected Calendar insertTime;

	@DateTimeFormat(pattern = DateTimePattern.yyyy_MM_dd_HH_mm_ss_SSS)
	@Column(name = "UPDATE_TIME", nullable = false)
	@UpdateTimestamp
	protected Calendar updateTime;

	@Transient
	protected Zone zoneHidden;
	@Transient
	protected String regionHidden;

}
