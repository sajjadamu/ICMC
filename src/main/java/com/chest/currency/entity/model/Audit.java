/*package com.chest.currency.entity.model;

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

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import com.chest.currency.enums.DateTimePattern;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity(name = "Audit")
@Table(name = "AUDIT")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Data
@EqualsAndHashCode(of = { "id" })
@ToString
public class Audit {

	@Id
	@Column(name = "ID")
	@GeneratedValue(generator = "AUDIT_SEQ")
	@SequenceGenerator(name = "AUDIT_SEQ", sequenceName = "AUDIT_SEQ", allocationSize = 100)
	protected Long id;

	@Basic
	@Column(name = "USER_ID")
	protected String userId;

	@Basic
	@Column(name = "URL")
	protected String url;

	@DateTimeFormat(pattern = DateTimePattern.yyyy_MM_dd_HH_mm_ss_SSS)
	@Column(name = "INSERT_TIME", nullable = false, updatable = false)
	@CreationTimestamp
	protected Calendar insertTime;

	@Basic
	@Column(name = "ICMC_ID")
	protected BigInteger icmcId;

}
*/