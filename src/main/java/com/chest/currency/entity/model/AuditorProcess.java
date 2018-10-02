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

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import com.chest.currency.enums.BinCategoryType;
import com.chest.currency.enums.CashSource;
import com.chest.currency.enums.CurrencyType;
import com.chest.currency.enums.DateTimePattern;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;


@Entity(name = "AuditorProcess")
@Table(name = "AUDITOR_PROCESS")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Data
@EqualsAndHashCode(of = { "id" })
@ToString
public class AuditorProcess {

	@Id
	@Column(name = "ID")
	@GeneratedValue(generator = "AUDITOR_PROCESS_SEQ")
	@SequenceGenerator(name = "AUDITOR_PROCESS_SEQ", sequenceName = "AUDITOR_PROCESS_SEQ", allocationSize = 100)
	protected Long id;

	@Basic
	@Column(name = "DENOMINATION")
	protected Integer denomination;

	@Enumerated(EnumType.STRING)
	@Column(name = "CURRENCY_TYPE")
	protected CurrencyType currencyType;

	@Basic
	@Column(name = "BUNDLE")
	protected BigDecimal bundle;

	@Enumerated(EnumType.STRING)
	@Column(name = "CASH_SOURCE")
	protected CashSource cashSource;

	@Basic
	@Column(name = "TOTAL")
	protected BigDecimal total;

	@Basic
	@Column(name = "FILEPATH")
	protected String filepath;

	@Basic
	@Column(name = "BIN_NUM")
	protected String binNumber;

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
	@CreationTimestamp
	protected Calendar insertTime;

	@DateTimeFormat(pattern = DateTimePattern.yyyy_MM_dd_HH_mm_ss_SSS)
	@Column(name = "UPDATE_TIME", nullable = false)
	@UpdateTimestamp
	protected Calendar updateTime;

	@Enumerated(EnumType.STRING)
	@Column(name = "BIN_CATEGORY_TYPE")
	protected BinCategoryType binCategoryType;

}
