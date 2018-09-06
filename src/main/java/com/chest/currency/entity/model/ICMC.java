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

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import com.chest.currency.enums.Status;
import com.chest.currency.enums.Zone;

@Entity(name = "ICMC")
@Table(name = "ICMC")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Data
@EqualsAndHashCode(of = { "id" })
@ToString
public class ICMC {

	@Id
	@Column(name = "ID")
	@GeneratedValue(generator = "ICMC_SEQ")
	@SequenceGenerator(name = "ICMC_SEQ", sequenceName = "ICMC_SEQ", allocationSize = 100)
	protected Long id;

	@Basic
	@Column(name = "NAME")
	protected String name;

	@Basic
	@Column(name = "CHEST_CODE")
	protected String chestCode;

	@Basic
	@Column(name = "LINK_BRANCH_SOLID")
	protected String linkBranchSolId;

	@Basic
	@Column(name = "RBI_NAME")
	protected String rbiName;

	@Basic
	@Column(name = "ADDRESS")
	protected String address;

	@Enumerated(EnumType.STRING)
	@Column(name = "ZONE")
	protected Zone zone;

	@Basic
	@Column(name = "REGION")
	protected String region;

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

	@Column(name = "REASON_FOR_DELETION")
	protected String reasonForDeletion;

	@Column(name = "APPROVAL_FOR_DELETION")
	protected String approvalForDeletion;

	@Transient
	protected String rbiNameHidden;
	@Transient
	protected Zone zoneHidden;
	@Transient
	protected String regionHidden;

}
