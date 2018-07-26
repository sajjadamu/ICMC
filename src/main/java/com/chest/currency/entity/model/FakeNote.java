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
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

import com.chest.currency.enums.DateTimePattern;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity(name = "fakeNote")
@Table(name = "FAKE_NOTE")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Data
@EqualsAndHashCode(of = { "id" })
@ToString
public class FakeNote {

	@Id
	//@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	@GeneratedValue(generator = "FAKE_NOTE_SEQ")
	@SequenceGenerator(name = "FAKE_NOTE_SEQ", sequenceName = "FAKE_NOTE_SEQ", allocationSize = 100)
	protected Long id;
	
	@DateTimeFormat(pattern = DateTimePattern.yyyy_MM_dd)
	@Column(name = "FAKE_NOTE_DATE")
	protected Date fakeNoteDate;
	
	@Basic
	@Column(name = "SOL_ID")
	protected Integer solId;
	
	@Basic
	@Column(name = "BRANCH")
	protected String branch;
	
	@Basic
	@Column(name = "DENOMINATION")
	protected Integer denomination;
	
	@Basic
	@Column(name = "FAKE_NOTE_SERIAL_NUMBER")
	protected String fakeNoteSerialNumber;
	
	@Basic
	@Column(name = "CUST_TELLER_CAM")
	protected String custTellerCam;
	
	@Basic
	@Column(name = "ACCOUNT_NUMBER")
	protected String accountNumber;
	
	@Column(name = "ICMC_ID")
	protected BigInteger icmcId;
	
	@Basic
	@Column(name = "INSERT_BY")
	protected String insertBy;

	@Basic
	@Column(name = "UPDATE_BY")
	protected String updateBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "INSERT_TIME")
	protected Calendar insertTime;

	@Column(name = "UPDATE_TIME")
	protected Calendar updateTime;
	
}
