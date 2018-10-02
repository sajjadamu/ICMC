package com.chest.currency.entity.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Calendar;
import java.util.Date;

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
import com.chest.currency.enums.CashSource;
import com.chest.currency.enums.CashType;
import com.chest.currency.enums.CurrencyType;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity(name = "FreshFromRBI")
@Table(name = "FRESH_FROM_RBI")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Data
@EqualsAndHashCode(of = { "id" })
@ToString
public class FreshFromRBI {

	@Id
	@Column(name = "ID")
	@GeneratedValue(generator = "FRESH_FROM_RBI_SEQ")
	@SequenceGenerator(name = "FRESH_FROM_RBI_SEQ", sequenceName = "FRESH_FROM_RBI_SEQ", allocationSize = 100)
	protected Long id;

	@Basic
	@Column(name = "DENOMINATION")
	protected Integer denomination;

	@Basic
	@Column(name = "BUNDLE")
	protected BigDecimal bundle;

	@Basic
	@Column(name = "PENDING_BUNDLE_REQUEST")
	protected BigDecimal pendingBundleRequest;

	@Basic
	@Column(name = "TOTAL")
	protected BigDecimal total;

	@Basic
	@Column(name = "BIN_NUM")
	protected String bin;

	@Basic
	@Column(name = "FILEPATH")
	protected String filepath;

	@Enumerated(EnumType.STRING)
	@Column(name = "CASH_TYPE")
	protected CashType cashType;

	@Basic
	@Column(name = "ORDER_DATE")
	protected Date orderDate;

	@Basic
	@Column(name = "RBI_ORDER_NO")
	protected String rbiOrderNo;

	@Basic
	@Column(name = "VEHICLE_NUMBER")
	protected String vehicleNumber;

	@Basic
	@Column(name = "POTDAR_NAME")
	protected String potdarName;

	@Basic
	@Column(name = "ESCORT_OFFICER_NAME")
	protected String escortOfficerName;

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
	@Column(name = "INSERT_TIME", nullable = false, updatable = false)
	@CreationTimestamp
	protected Calendar insertTime;

	@Column(name = "UPDATE_TIME", nullable = false)
	@UpdateTimestamp
	protected Calendar updateTime;

	@Enumerated(EnumType.STRING)
	@Column(name = "BIN_CATEGORY_TYPE")
	protected BinCategoryType binCategoryType;

	@Enumerated(EnumType.STRING)
	@Column(name = "CASH_SOURCE")
	protected CashSource cashSource;

	@Basic
	@Column(name = "POTDAR_STATUS")
	protected String potdarStatus;

	@Enumerated(EnumType.STRING)
	@Column(name = "CURRENCY_TYPE")
	protected CurrencyType currencyType;

	@Basic
	@Column(name = "NO_OF_BAGS")
	protected int noOfBags;
	
	@Transient
	protected int bagSequenceFromDB;

	@Transient
	protected String notesOrCoins;

	@Transient
	protected String binOrBox;

	@Transient
	protected int denom1CoinsPieces;
	@Transient
	protected int denom2CoinsPieces;
	@Transient
	protected int denom5CoinsPieces;
	@Transient
	protected int denom10CoinsPieces;
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
	protected int totalCoinsPieces;
	@Transient
	protected int totalValueOfCoins;

	/**
	 * Default Constructor
	 */
	public FreshFromRBI() {

	}

	public FreshFromRBI(boolean initialize) {
		if (initialize) {
			this.denom1CoinsPieces = 0;
			this.denom2CoinsPieces = 0;
			this.denom5CoinsPieces = 0;
			this.denom10CoinsPieces = 0;
			this.denom1Pieces = BigDecimal.ZERO;
			this.denom2Pieces = BigDecimal.ZERO;
			this.denom5Pieces = BigDecimal.ZERO;
			this.denom10Pieces = BigDecimal.ZERO;
			this.denom20Pieces = BigDecimal.ZERO;
			this.denom50Pieces = BigDecimal.ZERO;
			this.denom100Pieces = BigDecimal.ZERO;
			this.denom500Pieces = BigDecimal.ZERO;
			this.denom1000Pieces = BigDecimal.ZERO;
			this.denom2000Pieces = BigDecimal.ZERO;
			this.denom200Pieces = BigDecimal.ZERO;
			this.totalInPieces = BigDecimal.ZERO;
			this.totalValueOfBankNotes = BigDecimal.ZERO;
			this.totalCoinsPieces = 0;
			this.totalValueOfCoins = 0;
		}
	}

}
