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

import com.chest.currency.enums.CashSource;
import com.chest.currency.enums.CurrencyType;
import com.chest.currency.enums.OtherStatus;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity(name = "ProcessBundleForCRAPayment")
@Table(name = "PROCESS_BUNDLE_FOR_CRA_PAYMENT")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Data
@EqualsAndHashCode(of = { "id" })
@ToString
public class ProcessBundleForCRAPayment {

	@Id
	@Column(name = "ID")
	@GeneratedValue(generator = "PROCESS_BUNDLE_CRA_PAYMENT_SEQ")
	@SequenceGenerator(name = "PROCESS_BUNDLE_CRA_PAYMENT_SEQ", sequenceName = "PROCESS_BUNDLE_CRA_PAYMENT_SEQ", allocationSize = 100)
	protected Long id;

	@Basic
	@Column(name = "DENOMINATION")
	protected Integer denomination;

	@Basic
	@Column(name = "BUNDLE")
	protected BigDecimal bundle;

	@Column(name = "ICMC_ID")
	protected BigInteger icmcId;

	@Enumerated(EnumType.STRING)
	@Column(name = "CURRENCY_TYPE")
	protected CurrencyType currencyType;

	@Enumerated(EnumType.STRING)
	@Column(name = "CASH_SOURCE")
	protected CashSource cashSource;

	@Basic
	@Column(name = "FILEPATH")
	protected String filepath;

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
	protected OtherStatus status;

	@Basic
	@Column(name = "PENDING_REQUESTED_BUNDLE")
	protected BigDecimal pendingRequestedBundle;

	@Basic
	@Column(name = "CRA_ID")
	protected Long craId;

}
