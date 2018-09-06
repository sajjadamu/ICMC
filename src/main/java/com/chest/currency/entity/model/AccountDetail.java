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

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.chest.currency.enums.DebitOrCredit;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity(name = "AccountDetail")
@Table(name = "ACCOUNT_DETAIL")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Data
@EqualsAndHashCode(of = { "id" })
@ToString
public class AccountDetail {

	@Id
	@Column(name = "ID")
	@GeneratedValue(generator = "ACCOUNT_DETAIL_SEQ")
	@SequenceGenerator(name = "ACCOUNT_DETAIL_SEQ", sequenceName = "ACCOUNT_DETAIL_SEQ", allocationSize = 100)
	protected Long id;

	@Basic
	@Column(name = "ACTIVITY")
	protected String activity;

	@Basic
	@Column(name = "USAGE")
	protected String usage;

	@Enumerated(EnumType.STRING)
	@Column(name = "DEBIT_OR_CREDIT")
	protected DebitOrCredit debitOrCredit;

	@Basic
	@Column(name = "ACCOUNT_DETAILS")
	protected String accountDetails;

	@Basic
	@Column(name = "BRANCH_DETAILS")
	protected String branchDetails;

	@Basic
	@Column(name = "INSERT_BY")
	protected String insertBy;

	@Basic
	@Column(name = "UPDATE_BY")
	protected String updateBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "INSERT_TIME", updatable = false, nullable = false)
	@CreationTimestamp
	protected Calendar insertTime;

	@Column(name = "UPDATE_TIME", nullable = false)
	@UpdateTimestamp
	protected Calendar updateTime;
	

}
