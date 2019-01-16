package com.chest.currency.entity.model;

import java.math.BigDecimal;
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
import javax.persistence.Transient;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import com.chest.currency.enums.CurrencyType;
import com.chest.currency.enums.DateTimePattern;
import com.chest.currency.enums.OtherStatus;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity(name = "CRAAllocation")
@Table(name = "CRA_ALLOCATION")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Data
@EqualsAndHashCode(of = { "id" })
@ToString
public class CRAAllocation {

	@Id
	@Column(name = "ID")
	@GeneratedValue(generator = "CRA_ALLOCATION_SEQ")
	@SequenceGenerator(name = "CRA_ALLOCATION_SEQ", sequenceName = "CRA_ALLOCATION_SEQ", allocationSize = 100)
	protected Long id;

	@Basic
	@Column(name = "CRA_ID")
	protected Long craId;

	@Basic
	@Column(name = "DENOMINATION")
	protected Integer denomination;

	@Basic
	@Column(name = "BUNDLE")
	protected BigDecimal bundle;

	@Basic
	@Column(name = "TOTAL")
	protected BigDecimal total;

	@Basic
	@Column(name = "BIN_NUM")
	protected String binNumber;

	@Basic
	@Column(name = "ICMC_ID")
	protected BigInteger icmcId;

	@Enumerated(EnumType.STRING)
	@Column(name = "CURRENCY_TYPE")
	protected CurrencyType currencyType;

	@Basic
	@Column(name = "INSERT_BY")
	protected String insertBy;

	@Basic
	@Column(name = "UPDATE_BY")
	protected String updateBy;

	@DateTimeFormat(pattern = DateTimePattern.yyyy_MM_dd_HH_mm_ss_SSS)
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "INSERT_TIME", nullable = false, updatable = false)
	@CreationTimestamp
	protected Calendar insertTime;

	@DateTimeFormat(pattern = DateTimePattern.yyyy_MM_dd_HH_mm_ss_SSS)
	@Column(name = "UPDATE_TIME", nullable = false)
	@UpdateTimestamp
	protected Calendar updateTime;

	@Enumerated(EnumType.STRING)
	@Column(name = "STATUS")
	protected OtherStatus status;

	@Basic
	@Column(name = "VAULT")
	protected BigDecimal vault;

	@Basic
	@Column(name = "FORWARD")
	protected BigDecimal forward;

	@Basic
	@Column(name = "PENDING_REQUESTED_BUNDLE")
	protected BigDecimal pendingRequestedBundle;

	/*
	 * @OneToMany (cascade=CascadeType.ALL, fetch = FetchType.EAGER)
	 * 
	 * @JoinColumn(name="CRA_ALLOCATION_ID", referencedColumnName="ID")
	 * protected List<CRALog> craLog;
	 */

	@Transient
	protected BigDecimal denom1Pieces;
	@Transient
	protected BigDecimal denom2Pieces;
	@Transient
	protected BigDecimal denom5Pieces;
	@Transient
	protected BigDecimal denom10Pieces;
	@Transient
	protected BigDecimal denom20Pieces;
	@Transient
	protected BigDecimal denom50Pieces;
	@Transient
	protected BigDecimal denom100Pieces;
	@Transient
	protected BigDecimal denom200Pieces;
	@Transient
	protected BigDecimal denom500Pieces;
	@Transient
	protected BigDecimal denom1000Pieces;
	@Transient
	protected BigDecimal denom2000Pieces;
	@Transient
	protected BigDecimal totalInPieces;
	@Transient
	protected BigDecimal totalValueOfBankNotes;

	@Transient
	protected BigDecimal coin1Pieces;
	@Transient
	protected BigDecimal coin2Pieces;
	@Transient
	protected BigDecimal coin5Pieces;
	@Transient
	protected BigDecimal coin10Pieces;

	@Transient
	protected String branchName;
	@Transient
	protected String vendor;
	@Transient
	protected String msp;

	public CRAAllocation() {

	}

	public CRAAllocation(boolean initialize) {
		if (initialize) {
			this.denom1Pieces = BigDecimal.ZERO;
			this.denom2Pieces = BigDecimal.ZERO;
			this.denom5Pieces = BigDecimal.ZERO;
			this.denom10Pieces = BigDecimal.ZERO;
			this.denom20Pieces = BigDecimal.ZERO;
			this.denom50Pieces = BigDecimal.ZERO;
			this.denom100Pieces = BigDecimal.ZERO;
			this.denom200Pieces = BigDecimal.ZERO;
			this.denom500Pieces = BigDecimal.ZERO;
			this.denom1000Pieces = BigDecimal.ZERO;
			this.denom2000Pieces = BigDecimal.ZERO;
			this.totalInPieces = BigDecimal.ZERO;
			this.totalValueOfBankNotes = BigDecimal.ZERO;
			this.coin1Pieces = BigDecimal.ZERO;
			this.coin2Pieces = BigDecimal.ZERO;
			this.coin5Pieces = BigDecimal.ZERO;
			this.coin10Pieces = BigDecimal.ZERO;
		}
	}
}
