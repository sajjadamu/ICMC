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

import com.chest.currency.enums.Status;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author root
 *
 */
@Entity(name = "Jurisdiction")
@Table(name = "JURISDICTION")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Data
@EqualsAndHashCode(of = { "id" })
@ToString
public class Jurisdiction {

	@Id
	// @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	@GeneratedValue(generator = "JURISDICTION_SEQ")
	@SequenceGenerator(name = "JURISDICTION_SEQ", sequenceName = "JURISDICTION_SEQ", allocationSize = 100)
	protected int id;

	@Basic
	@Column(name = "SOL_ID")
	protected Integer solId;

	@Basic
	@Column(name = "BRANCH_NAME")
	protected String branchName;

	@Basic
	@Column(name = "ICMC_NAME")
	protected String icmcName;

	@Basic
	@Column(name = "JURISDICTION")
	protected String jurisdiction;

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
