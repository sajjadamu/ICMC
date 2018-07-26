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
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
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
import com.chest.currency.enums.State;

@Entity(name = "HolidayMaster")
@Table(name = "HOLIDAY_MASTER")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Data
@EqualsAndHashCode(of = {"id"})
@ToString
public class HolidayMaster {
	
	@Id
	//@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	@GeneratedValue(generator = "HOLIDAY_MASTER_SEQ")
	@SequenceGenerator(name = "HOLIDAY_MASTER_SEQ", sequenceName = "HOLIDAY_MASTER_SEQ", allocationSize = 100)
	protected int id;
	
	@Basic
	@Column(name = "HOLIDAY_NAME")
	protected String holidayName;
	
	@Basic
	@Column(name = "HOLIDAY_TYPE")
	protected String holidayType;
	
	@Basic
	@Column(name = "HOLIDAY_DATE")
	protected String holidayDate;
	
	@Transient
	@Enumerated(EnumType.STRING)
	protected State state;
	
	@Basic
	@Column(name = "ANDAMAN_AND_NICOBAR_ISLANDS")
	protected String andamanandnicobarislands;
	
	@Basic
	@Column(name = "ANDHRA_PRADESH")
	protected String andhrapradesh;
	
	@Basic
	@Column(name = "ARUNACHAL_PRADESH")
	protected String arunachalPradesh;
	
	@Basic
	@Column(name = "ASSAM")
	protected String assam;
	
	@Basic
	@Column(name = "BIHAR")
	protected String bihar;
	
	@Basic
	@Column(name = "CHANDIGARH")
	protected String chandigarh;
	
	@Basic
	@Column(name = "CHHATTISGARH")
	protected String chhattisgarh;
	
	@Basic
	@Column(name = "DAMAN_AND_DIU")
	protected String damanAndDiu;
	
	@Basic
	@Column(name = "DELHI")
	protected String delhi;
	
	@Basic
	@Column(name = "GOA")
	protected String goa;
	
	@Basic
	@Column(name = "GUJARAT")
	protected String gujarat;
	
	@Basic
	@Column(name = "HIMACHAL_PRADESH")
	protected String himachalpradesh;
	
	@Basic
	@Column(name = "HARYANA")
	protected String haryana;
	
	@Basic
	@Column(name = "JHARKHAND")
	protected String jharkhand;
	
	@Basic
	@Column(name = "JAMMU_AND_KASHMIR")
	protected String jammuAndKashmir;
	
	@Basic
	@Column(name = "KARNATAKA")
	protected String karnataka;
	
	@Basic
	@Column(name = "KERALA")
	protected String kerala;
	
	@Basic
	@Column(name = "MAHARASHTRA")
	protected String maharashtra;
	
	@Basic
	@Column(name = "MEGHALAYA")
	protected String meghalaya;
	
	@Basic
	@Column(name = "MANIPUR")
	protected String manipur;
	
	@Basic
	@Column(name = "MADHYA_PRADESH")
	protected String madhyaPradesh;
	
	@Basic
	@Column(name = "MIZORAM")
	protected String mizoram;
	
	@Basic
	@Column(name = "NAGALAND")
	protected String nagaland;
	
	@Basic
	@Column(name = "ORISSA")
	protected String orissa;
	
	@Basic
	@Column(name = "PUNJAB")
	protected String punjab;
	
	@Basic
	@Column(name = "PONDICHERRY")
	protected String pondichhery;
	
	@Basic
	@Column(name = "RAJASTHAN")
	protected String rajasthan;
	
	@Basic
	@Column(name = "SIKKIM")
	protected String sikkim;
	
	@Basic
	@Column(name = "TAMIL_NADU")
	protected String tamilNadu;
	
	@Basic
	@Column(name = "TRIPURA")
	protected String tripura;
	
	@Basic
	@Column(name = "UTTAR_PRADESH")
	protected String uttarPradesh;
	
	@Basic
	@Column(name = "UTTARANCHAL")
	protected String uttaranchal;
	
	@Basic
	@Column(name = "WEST_BENGAL")
	protected String westBengal;
	
	@Basic
	@Column(name = "DADRA_AND_NAGAR_HAVELI")
	protected String dadraandnagarhaveli;
	
	@Basic
	@Column(name = "LAKSHADWEEP")
	protected String lakshadweep;
	
	@Basic
	@Column(name = "TELANGANA")
	protected String telangana;
	
	@DateTimeFormat(pattern = DateTimePattern.yyyy_MM_dd_HH_mm_ss_SSS)
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "INSERT_TIME")
	protected Calendar insertTime;
	
	@DateTimeFormat(pattern = DateTimePattern.yyyy_MM_dd_HH_mm_ss_SSS)
	@Column(name = "UPDATE_TIME")
	protected Calendar updateTime;
	
	@Basic
	@Column(name = "INSERT_BY")
	protected String insertBy;
	
	@Basic
	@Column(name = "UPDATE_BY")
	protected String updateBy;
	
	}
