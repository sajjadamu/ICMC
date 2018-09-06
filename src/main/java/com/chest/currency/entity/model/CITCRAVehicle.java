/*******************************************************************************
 * /* Copyright (C) Indicsoft Technologies Pvt Ltd
 * * All Rights Reserved.
 *******************************************************************************/
package com.chest.currency.entity.model;

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
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import com.chest.currency.enums.DateTimePattern;
import com.chest.currency.enums.Status;

@Entity(name = "CITCRAVehicle")
@Table(name = "CIT_CRA_VEHICLE")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Data
@EqualsAndHashCode(of = { "id" })
@ToString
public class CITCRAVehicle {

	@Id
	@Column(name = "ID")
	@GeneratedValue(generator = "CIT_CRA_VEHICLE_SEQ")
	@SequenceGenerator(name = "CIT_CRA_VEHICLE_SEQ", sequenceName = "CIT_CRA_VEHICLE_SEQ", allocationSize = 100)
	protected Long id;

	@Basic
	@Column(name = "VENDOR_NAME")
	protected String name;

	@Basic
	@Column(name = "VEHICLE_NUMBER")
	protected String number;

	@DateTimeFormat(pattern = DateTimePattern.yyyy_MM_dd)
	@Column(name = "BOUGHT_DATE")
	protected Date boughtDate;

	@Basic
	@Column(name = "REGISTRATION_CITY")
	protected String regCityName;

	@Basic
	@Column(name = "VEHICLE_INSURANCE")
	protected String insurance;

	@DateTimeFormat(pattern = DateTimePattern.yyyy_MM_dd)
	@Column(name = "FITNESS_EXPIRY_DATE")
	protected Date fitnessExpiryDate;

	@DateTimeFormat(pattern = DateTimePattern.yyyy_MM_dd)
	@Column(name = "POLLUTION_EXPIRY_DATE")
	protected Date pollutionExpiryDate;

	@DateTimeFormat(pattern = DateTimePattern.yyyy_MM_dd)
	@Column(name = "PERMIT_DATE")
	protected Date permitDate;

	@Enumerated(EnumType.STRING)
	@Column(name = "STATUS")
	protected Status status;

	@DateTimeFormat(pattern = DateTimePattern.yyyy_MM_dd_HH_mm_ss_SSS)
	@Column(name = "INSERT_TIME", nullable = false, updatable = false)
	@CreationTimestamp
	protected Calendar insertTime;

	@DateTimeFormat(pattern = DateTimePattern.yyyy_MM_dd_HH_mm_ss_SSS)
	@Column(name = "UPDATE_TIME", nullable = false)
	@UpdateTimestamp
	protected Calendar updateTime;

	@Basic
	@Column(name = "UPDATE_BY")
	protected String updateBy;

	@Basic
	@Column(name = "INSERT_BY")
	protected String insertBy;

	@Basic
	@Column(name = "REASON_FOR_DELETION")
	protected String reasonForDeletion;

	@Basic
	@Column(name = "APPROVAL_FOR_DELETION")
	protected String approvalForDeletion;

}
