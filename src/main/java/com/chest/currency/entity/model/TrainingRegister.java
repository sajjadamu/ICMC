package com.chest.currency.entity.model;

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

import org.springframework.format.annotation.DateTimeFormat;

import com.chest.currency.enums.DateTimePattern;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity(name = "TrainingRegister")
@Table(name = "TRAINING_REGISTER")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Data
@EqualsAndHashCode(of = { "id" })
@ToString
public class TrainingRegister {

	@Id
	// @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	@GeneratedValue(generator = "TRAINING_REGISTER_SEQ")
	@SequenceGenerator(name = "TRAINING_REGISTER_SEQ", sequenceName = "TRAINING_REGISTER_SEQ", allocationSize = 100)
	protected Long id;

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
	@Column(name = "TRAINING_DATE")
	protected String trainingDate;

	@Basic
	@Column(name = "SUBJECT")
	protected String subject;

	@Basic
	@Column(name = "NAME_OF_TRAINER")
	protected String nameOfTrainer;

	// code for the alter table
	@Basic
	@Column(name = "LOCATION")
	protected String location;

	@Basic
	@Column(name = "EMPLOYEE_NAME")
	protected String employeeName;

	@Basic
	@Column(name = "EMPLOYEE_ID")
	protected String employeeId;

	@Basic
	@Column(name = "DURATION")
	protected String duration;

	/*
	 * @Basic
	 * 
	 * @Column(name = "TRAINING_tIME") protected String trainingTime;
	 */
	// end for the code of alter table
	@Basic
	@Column(name = "REMARKS")
	protected String remarks;

}
