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

import com.chest.currency.enums.BinCategoryType;
import com.chest.currency.enums.CurrencyType;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity(name = "bankReceipt")
@Table(name = "BANK_RECEIPT")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Data
@EqualsAndHashCode(of = { "id" })
@ToString
public class BankReceipt {

	@Id
	@Column(name = "ID")
	@GeneratedValue(generator = "BANK_RECEIPT_SEQ")
	@SequenceGenerator(name = "BANK_RECEIPT_SEQ", sequenceName = "BANK_RECEIPT_SEQ", allocationSize = 100)
	protected Long id;

	@Basic
	@Column(name = "DENOMINATION")
	protected Integer denomination;

	@Basic
	@Column(name = "BUNDLE")
	protected BigDecimal bundle;

	@Basic
	@Column(name = "RTGS_UTR_NO")
	protected String rtgsUTRNo;

	@Basic
	@Column(name = "TOTAL")
	protected BigDecimal total;

	@Basic
	@Column(name = "BIN_NUM")
	protected String binNumber;

	@Basic
	@Column(name = "FILE_PATH")
	protected String filepath;

	@Basic
	@Column(name = "ICMC_ID")
	protected BigInteger icmcId;

	@Basic
	@Column(name = "STATUS")
	protected int status;

	@Basic
	@Column(name = "BANK_NAME")
	protected String bankName;

	@Basic
	@Column(name = "SOL_ID")
	protected String solId;

	@Basic
	@Column(name = "BRANCH")
	protected String branch;

	@Basic
	@Column(name = "INSERT_BY")
	protected String insertBy;

	@Basic
	@Column(name = "UPDATE_BY")
	protected String updateBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "INSERT_TIME", nullable = false, updatable = false)
	@CreationTimestamp
	protected Calendar insertTime;

	@Column(name = "UPDATE_TIME", nullable = false)
	@UpdateTimestamp
	protected Calendar updateTime;

	@Enumerated(EnumType.STRING)
	@Column(name = "CURRENCY_TYPE")
	protected CurrencyType currencyType;

	@Enumerated(EnumType.STRING)
	@Column(name = "BIN_CATEGORY_TYPE")
	protected BinCategoryType binCategoryType;
	
	@Basic
	@Column(name = "IS_INDENT", nullable = false, columnDefinition = "TINYINT(1)")
	protected Boolean isIndent = false;

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

	/**
	 * Default Constructor
	 */
	public BankReceipt() {

	}

	public BankReceipt(boolean initialize) {
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
		}
	}

}
