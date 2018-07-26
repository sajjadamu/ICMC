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
import javax.persistence.Transient;

import org.springframework.format.annotation.DateTimeFormat;

import com.chest.currency.enums.DateTimePattern;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity(name="BinRegister")
@Table(name="BIN_REGISTER")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Data
@EqualsAndHashCode(of = { "id" })
@ToString
public class BinRegister {

	@Id
	//@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	@GeneratedValue(generator = "BIN_REGISTER_SEQ")
	@SequenceGenerator(name = "BIN_REGISTER_SEQ", sequenceName = "BIN_REGISTER_SEQ", allocationSize = 100)
	protected Long id;
	
	@Basic
	@Column(name = "BIN_NUM")
	protected String binNumber;
	
	@Basic
	@Column(name = "DENOMINATION")
	protected int denomination;

	
	@Basic
	@Column(name = "RECEIVE_BUNDLE")
	protected BigDecimal receiveBundle;
	

	@Basic
	@Column(name = "WITHDRAWAL_BUNDLE")
	protected BigDecimal withdrawalBundle;
	
	
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
	@Column(name="TYPE")
	protected String type;
	
	@Basic
	@Column(name="DEPOSIT_WITHDRAWAL")
	protected String depositOrWithdrawal;
	
	@Transient
	protected BigDecimal balance;

}
