package com.chest.currency.entity.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Calendar;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

import com.chest.currency.enums.DateTimePattern;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity(name = "SuspenseOpeningBalance")
@Table(name = "SUSPENSE_OPENING_BALANCE")
@Data
@EqualsAndHashCode(of = { "id" })
@ToString
public class SuspenseOpeningBalance {

	@Id
	@Column(name = "ID")
	@GeneratedValue(generator = "SUSPENSE_OPENING_BALANCE_SEQ")
	@SequenceGenerator(name = "SUSPENSE_OPENING_BALANCE_SEQ", sequenceName = "SUSPENSE_OPENING_BALANCE_SEQ", allocationSize = 100)

	protected Long id;

	@Basic
	@Column(name = "DENOMINATION_1")
	protected BigDecimal denomination1;

	@Basic
	@Column(name = "DENOMINATION_2")
	protected BigDecimal denomination2;

	@Basic
	@Column(name = "DENOMINATION_5")
	protected BigDecimal denomination5;

	@Basic
	@Column(name = "DENOMINATION_10")
	protected BigDecimal denomination10;

	@Basic
	@Column(name = "DENOMINATION_20")
	protected BigDecimal denomination20;

	@Basic
	@Column(name = "DENOMINATION_50")
	protected BigDecimal denomination50;

	@Basic
	@Column(name = "DENOMINATION_100")
	protected BigDecimal denomination100;

	@Basic
	@Column(name = "DENOMINATION_500")
	protected BigDecimal denomination500;

	@Basic
	@Column(name = "DENOMINATION_200")
	protected BigDecimal denomination200;

	@Basic
	@Column(name = "DENOMINATION_1000")
	protected BigDecimal denomination1000;

	@Basic
	@Column(name = "DENOMINATION_2000")
	protected BigDecimal denomination2000;

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
	@Column(name = "INSERT_TIME")
	protected Calendar insertTime;

	@Column(name = "UPDATE_TIME")
	protected Calendar updateTime;

	@Basic
	@Column(name = "CURRENT_VERSION")
	protected String currentVersion;

	@Basic
	@Column(name = "WITHDRAWAL_10")
	protected BigDecimal withdrawal_10;

	@Basic
	@Column(name = "WITHDRAWAL_20")
	protected BigDecimal withdrawal_20;

	@Basic
	@Column(name = "WITHDRAWAL_50")
	protected BigDecimal withdrawal_50;

	@Basic
	@Column(name = "WITHDRAWAL_100")
	protected BigDecimal withdrawal_100;

	@Basic
	@Column(name = "WITHDRAWAL_200")
	protected BigDecimal withdrawal_200;

	@Basic
	@Column(name = "WITHDRAWAL_500")
	protected BigDecimal withdrawal_500;

	@Basic
	@Column(name = "WITHDRAWAL_2000")
	protected BigDecimal withdrawal_2000;

	@Basic
	@Column(name = "DEPOSIT_10")
	protected BigDecimal deposit_10;

	@Basic
	@Column(name = "DEPOSIT_20")
	protected BigDecimal deposit_20;

	@Basic
	@Column(name = "DEPOSIT_50")
	protected BigDecimal deposit_50;

	@Basic
	@Column(name = "DEPOSIT_100")
	protected BigDecimal deposit_100;

	@Basic
	@Column(name = "DEPOSIT_200")
	protected BigDecimal deposit_200;

	@Basic
	@Column(name = "DEPOSIT_500")
	protected BigDecimal deposit_500;

	@Basic
	@Column(name = "DEPOSIT_2000")
	protected BigDecimal deposit_2000;

	@Basic
	@Column(name = "REPLENISHMENT_2000")
	protected BigDecimal replenishment_2000;

	@Basic
	@Column(name = "REPLENISHMENT_500")
	protected BigDecimal replenishment_500;

	@Basic
	@Column(name = "REPLENISHMENT_200")
	protected BigDecimal replenishment_200;

	@Basic
	@Column(name = "REPLENISHMENT_100")
	protected BigDecimal replenishment_100;

	@Basic
	@Column(name = "REPLENISHMENT_50")
	protected BigDecimal replenishment_50;

	@Basic
	@Column(name = "REPLENISHMENT_20")
	protected BigDecimal replenishment_20;

	@Basic
	@Column(name = "REPLENISHMENT_10")
	protected BigDecimal replenishment_10;

	@Basic
	@Column(name = "DEPLETION_2000")
	protected BigDecimal depletion_2000;

	@Basic
	@Column(name = "DEPLETION_500")
	protected BigDecimal depletion_500;

	@Basic
	@Column(name = "DEPLETION_200")
	protected BigDecimal depletion_200;

	@Basic
	@Column(name = "DEPLETION_100")
	protected BigDecimal depletion_100;

	@Basic
	@Column(name = "DEPLETION_50")
	protected BigDecimal depletion_50;

	@Basic
	@Column(name = "DEPLETION_20")
	protected BigDecimal depletion_20;

	@Basic
	@Column(name = "DEPLETION_10")
	protected BigDecimal depletion_10;

	@Basic
	@Column(name = "SR_NUMBER")
	protected String srNumber;

}
