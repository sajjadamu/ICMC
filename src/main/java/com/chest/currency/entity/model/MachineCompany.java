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
import javax.persistence.Transient;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import org.springframework.format.annotation.DateTimeFormat;

import com.chest.currency.enums.DateTimePattern;
import com.chest.currency.enums.Status;

@Entity(name="MachineCompany")
@Table(name="MACHINE_COMPANY")
@Data
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
@EqualsAndHashCode(of={"id"})
@ToString

public class MachineCompany {
	
	@Id
	//@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	@GeneratedValue(generator = "MACHINE_COMPANY_SEQ")
	@SequenceGenerator(name = "MACHINE_COMPANY_SEQ", sequenceName = "MACHINE_COMPANY_SEQ", allocationSize = 100)
	protected Long id;
	
	@Basic
	@Column(name = "COMPANY_NAME")
	protected String companyname;
	
	@Basic
	@Column(name = "EMAIL")
	protected String email;

	@Basic
	@Column(name = "PHONE_NUMBER")
	protected Long phonenumber;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "STATUS")
	protected Status status;
	
	
	@Basic
	@Column(name = "ADDRESS")
	protected String address;
	
	
	@DateTimeFormat(pattern = DateTimePattern.yyyy_MM_dd)
	@Column(name = "PURCHASE_DATE")
	protected Date purchasedate;
	
	@Transient
	protected Long diffDay;
	
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
	

	

}
