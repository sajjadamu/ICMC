/*******************************************************************************
 * /* Copyright (C) Indicsoft Technologies Pvt Ltd
 * * All Rights Reserved.
 *******************************************************************************/
package com.chest.currency.entity.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.chest.currency.enums.OtherStatus;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity(name = "DiversionORV")
@Table(name = "DIVERSION_ORV")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Data
@EqualsAndHashCode(of = { "id" })
@ToString

public class DiversionORV {
	
	@Id
	//@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	@GeneratedValue(generator = "DIVERSION_ORV_SEQ")
	@SequenceGenerator(name = "DIVERSION_ORV_SEQ", sequenceName = "DIVERSION_ORV_SEQ", allocationSize = 100)
	protected Long id;
	
	@Basic
	@Column(name = "ORDER_DATE")
	protected Date orderDate;
	
	@Basic
	@Column(name = "RBI_ORDER_NO")
	protected Long rbiOrderNo;
	
	@Basic
	@Column(name = "EXPIRY_DATE")
	protected Date expiryDate;
	
	@Basic
	@Column(name = "BANK_NAME")
	protected String bankName;
	
	@Basic
	@Column(name = "APPROVED_CC")
	protected String approvedCC;

	@Basic
	@Column(name = "LOCATION")
	protected String location;
	
	@Basic
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
	
	@Enumerated(EnumType.STRING)
	@Column(name = "STATUS")
	protected OtherStatus otherStatus;
	
	@OneToMany (cascade=CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name="DIVERSION_ORV_ID", referencedColumnName="ID")
	protected List<DiversionORVAllocation> diversionAllocations;
	
	@Transient
	protected BigDecimal totalValue;
	
	@Transient
	public  String getDiversionORVDetails(){
		return getId()+" - "+getBankName();
	}
}
