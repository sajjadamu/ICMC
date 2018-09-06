package com.chest.currency.entity.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Calendar;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
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

import com.chest.currency.enums.DateTimePattern;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity(name = "cashTransfer")
@Table(name = "CASH_TRANSFER")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Data
@EqualsAndHashCode(of = { "id" })
@ToString
public class CashTransfer {

	@Id
	@Column(name = "ID")
	@GeneratedValue(generator = "CASH_TRANSFER_SEQ")
	@SequenceGenerator(name = "CASH_TRANSFER_SEQ", sequenceName = "CASH_TRANSFER_SEQ", allocationSize = 100)
	protected Long id;

	@Basic
	@Column(name = "TRANSFER_TYPE")
	protected String transferType;

	@Basic
	@Column(name = "FROM_BIN_OR_BOX")
	protected String fromBinOrBox;

	@Basic
	@Column(name = "TO_BIN_OR_BOX")
	protected String toBinOrBox;

	@Basic
	@Column(name = "RECEIVE_BUNDLE")
	protected BigDecimal receiveBundle;

	@Basic
	@Column(name = "DENOMINATION")
	protected int denomination;

	@Basic
	@Column(name = "REMARKS")
	protected String remarks;

	@Basic
	@Column(name = "REASON")
	protected String reason;

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

	@Column(name = "UPDATE_TIME", nullable = false)
	@UpdateTimestamp
	protected Calendar updateTime;

}
