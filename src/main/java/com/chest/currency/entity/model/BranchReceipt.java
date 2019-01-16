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

import org.springframework.format.annotation.DateTimeFormat;

import com.chest.currency.enums.BinCategoryType;
import com.chest.currency.enums.CashSource;
import com.chest.currency.enums.CurrencyType;
import com.chest.currency.enums.DateTimePattern;
import com.chest.currency.enums.OtherStatus;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity(name = "BranchReceipt")
@Table(name = "BRANCH_RECEIPT")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Data
@EqualsAndHashCode(of = { "id" })
@ToString
public class BranchReceipt {
	
	@Id
	@Column(name = "ID")
	@GeneratedValue(generator = "BRANCH_RECEIPT_SEQ")
	@SequenceGenerator(name = "BRANCH_RECEIPT_SEQ", sequenceName = "BRANCH_RECEIPT_SEQ", allocationSize = 100)
	protected Long id;

	@Basic
	@Column(name = "SOL_ID")
	protected String solId;

	@Basic
	@Column(name = "BRANCH")
	protected String branch;
	
	@Basic
	@Column(name = "SR_NUMBER")
	protected String srNumber;

	@Basic
	@Column(name = "DENOMINATION")
	protected Integer denomination;

	@Basic
	@Column(name = "BUNDLE")
	protected BigDecimal bundle;

	@Basic
	@Column(name = "TOTAL")
	protected BigDecimal total;
	
	@Transient
	protected BigDecimal bundleAndDenominationTotal;

	@Basic
	@Column(name = "BIN_NUM")
	protected String bin;

	@Basic
	@Column(name = "FILEPATH")
	protected String filepath;

	@Enumerated(EnumType.STRING)
	@Column(name = "STATUS")
	protected OtherStatus status;
	
	@Basic
	@Column(name = "ICMC_ID")
	protected BigInteger icmcId;

	@Basic
	@Column(name = "INSERT_BY")
	protected String insertBy;

	@Basic
	@Column(name = "UPDATE_BY")
	protected String updateBy;
	
	@DateTimeFormat(pattern = DateTimePattern.yyyy_MM_dd_HH_mm_ss_SSS)
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "INSERT_TIME", nullable = false, updatable = false)
	protected Calendar insertTime;
	
	@DateTimeFormat(pattern = DateTimePattern.yyyy_MM_dd_HH_mm_ss_SSS)
	@Column(name = "UPDATE_TIME", nullable = false)
	protected Calendar updateTime;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "CURRENCY_TYPE")
	protected CurrencyType currencyType;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "CASH_SOURCE")
	protected CashSource cashSource;
	
	@Basic
	@Column(name = "PENDING_BUNDLE_REQUEST")
	protected BigDecimal pendingBundleRequest;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "BIN_CATEGORY_TYPE")
	protected BinCategoryType binCategoryType;
	
	@Basic
	@Column(name="PROCESSED_OR_UNPROCESSED")
	protected String processedOrUnprocessed;
	
	@Transient
	protected BigDecimal requestBundle;
	
	@Transient
	protected boolean checkShrinkWrap=false;
	
	@Basic
	@Column(name = "SAS_ID")
	protected Long sasId;
	
	@Transient
	private boolean isFromProcessingRoom;
	
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
	protected String receiptTime;
	
	/**
	 * Default Constructor
	 */
	public BranchReceipt(){
		
	}
	public BranchReceipt(boolean initialize){
		if(initialize){
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
		}
	}
	
}
