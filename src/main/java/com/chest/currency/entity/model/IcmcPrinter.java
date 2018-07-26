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

import com.chest.currency.enums.Status;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;


@Entity(name = "IcmcPrinter")
@Table(name = "ICMC_PRINTER")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Data
@EqualsAndHashCode(of = { "id" })
@ToString
public class IcmcPrinter {

	@Id
	@Column(name = "ID")
	@GeneratedValue(generator = "ICMC_PRINTER_SEQ")
	@SequenceGenerator(name = "ICMC_PRINTER_SEQ", sequenceName = "ICMC_PRINTER_SEQ", allocationSize = 100)
	protected Long id;

	@Basic
	@Column(name = "PRINTER_NAME", unique=true)
	protected String printerName;
	
	@Basic
	@Column(name = "PRINTER_IP")
	protected String printerIP;
	
	@Basic
	@Column(name = "PORT")
	protected BigInteger port;
	
	@Basic
	@Column(name = "ICMC_ID")
	protected BigInteger icmcId;

	@Enumerated(EnumType.STRING)
	@Column(name = "STATUS" )
	protected Status status;

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
