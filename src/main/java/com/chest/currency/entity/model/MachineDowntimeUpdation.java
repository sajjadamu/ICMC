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
import com.chest.currency.enums.DowntimeReason;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity(name = "MachineDowntimeUpdation")
@Table(name = "MACHINE_DOWNTIME_UPDATION")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Data
@EqualsAndHashCode(of = { "id" })
@ToString
public class MachineDowntimeUpdation {

	@Id
	//@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	@GeneratedValue(generator = "MACHINE_DOWNTIME_UPDATION_SEQ")
    @SequenceGenerator(name = "MACHINE_DOWNTIME_UPDATION_SEQ", sequenceName = "MACHINE_DOWNTIME_UPDATION_SEQ", allocationSize = 100)
	protected Long id;
	
	@Basic
	@DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
	@Column(name = "MACHINE_DOWN_DATE_FROM")
	protected Date machineDownDateFrom;
	
	@Basic
	@DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
	@Column(name = "MACHINE_DOWN_DATE_TO")
	protected Date machineDownDateTo;
	
	@Basic
	@DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
	@Column(name = "ENGINEER_ATTEND_CALL")
	protected Date engineerAttendedCall;
	
	@Basic
	@Column(name="MACHINE_TYPE")
	protected String machineType;
	
	@Basic
	@Column(name = "MACHINE_NO")
	protected int machineNo;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "DOWNTIME_REASON")
	protected DowntimeReason downtimeReason;
	
	@Basic
	@Column(name = "REMARKS")
	protected String remarks;
	
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
	
	@Transient
	protected Long hours;
	
}
