/*******************************************************************************
 * /* Copyright (C) Indicsoft Technologies Pvt Ltd
 * * All Rights Reserved.
 *******************************************************************************/
package com.chest.currency.entity.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Calendar;
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

@Entity(name = "CRAlog")
@Table(name = "CRA_LOG")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Data
@EqualsAndHashCode(of = { "id" })
@ToString
public class CRALog {
	
	@Id
	@Column(name = "ID")
	@GeneratedValue(generator = "CRA_SEQ")
	@SequenceGenerator(name = "CRA_SEQ", sequenceName = "CRA_SEQ", allocationSize = 100)
	protected Long id;
	
	@Column(name = "CRA_ID")
	protected Long craId;
	
	@Basic
	@Column(name = "SR_NO")
	protected String srNo;
	
	@Basic
	@Column(name = "SOL_ID")
	protected String solId;
	
	
	@Basic
	@Column(name = "BRANCH")
	protected String branch;
	
	
	@Basic
	@Column(name = "VENDOR")
	protected String vendor;
	
	@Basic
	@Column(name = "MSP_NAME")
	protected String mspName;
	
	@Basic
	@Column(name = "ACCOUNT_NUMBER")
	protected String accountNumber;
	
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
	
	@OneToMany (cascade=CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name="CRA_ID", referencedColumnName="ID")
	protected List<CRAAllocationLog> craAllocationsLog;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "STATUS")
	protected OtherStatus status;
	
	@Basic
	@Column(name = "ACTION")
	protected Integer action;
	
	public String getMspNameWithVendor(){
		return this.getVendor()+"-"+this.getMspName();
	}
	
	@Transient
	protected BigDecimal totalValue;
	
}
