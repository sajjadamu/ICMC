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

import com.chest.currency.enums.CurrencyType;
import com.chest.currency.enums.DateTimePattern;
import com.chest.currency.enums.OtherStatus;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity(name = "AuditorIndent")
@Table(name = "AUDITOR_INDENT")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Data
@EqualsAndHashCode(of = { "id" })
@ToString
public class AuditorIndent {

	@Id
	@Column(name = "ID")
	@GeneratedValue(generator = "AUDITOR_INDENT_SEQ")
	@SequenceGenerator(name = "AUDITOR_INDENT_SEQ", sequenceName = "AUDITOR_INDENT_SEQ", allocationSize = 100)
	protected Long id;

	@Basic
	@Column(name = "BIN_NUM")
	protected String binNumber;

	@Basic
	@Column(name = "DENOMINATION")
	protected int denomination;

	@Basic
	@Column(name = "BUNDLE")
	protected BigDecimal bundle;

	@Basic
	@Column(name = "PENDING_BUNDLE_REQUEST")
	protected BigDecimal pendingBundleRequest;

	@Enumerated(EnumType.STRING)
	@Column(name = "BIN_TYPE")
	protected CurrencyType binType;

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
	@CreationTimestamp
	protected Calendar insertTime;

	@Column(name = "UPDATE_TIME", nullable = false)
	@UpdateTimestamp
	protected Calendar updateTime;

}
