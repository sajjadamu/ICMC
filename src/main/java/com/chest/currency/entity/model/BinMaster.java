
package com.chest.currency.entity.model;

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

import com.chest.currency.enums.CurrencyType;
import com.chest.currency.enums.VaultSize;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author root
 *
 */
@Entity(name = "BinMaster")
@Table(name = "BIN_MASTER")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Data
@EqualsAndHashCode(of = { "id" })
@ToString
public class BinMaster {

	@Id
	@Column(name = "ID")
	@GeneratedValue(generator = "BIN_MASTER_SEQ")
	@SequenceGenerator(name = "BIN_MASTER_SEQ", sequenceName = "BIN_MASTER_SEQ", allocationSize = 100)
	protected Long id;

	@Enumerated(EnumType.STRING)
	@Column(name = "FIRST_PRIORITY")
	protected CurrencyType firstPriority;

	@Enumerated(EnumType.STRING)
	@Column(name = "SECOND_PRIORITY")
	protected CurrencyType secondPriority;

	@Enumerated(EnumType.STRING)
	@Column(name = "THIRD_PRIORITY")
	protected CurrencyType thirdPriority;

	@Enumerated(EnumType.STRING)
	@Column(name = "FOURTH_PRIORITY")
	protected CurrencyType fourthPriority;

	@Enumerated(EnumType.STRING)
	@Column(name = "FIFTH_PRIORITY")
	protected CurrencyType fifthPriority;

	@Basic
	@Column(name = "LOCA_PRIORITY")
	protected Integer locationPriority;

	@Basic
	@Column(name = "BIN_NUM")
	protected String binNumber;

	@Basic
	@Column(name = "IS_ALLOCATED")
	protected Integer isAllocated;

	@Basic
	@Column(name = "ICMC_ID")
	protected BigInteger icmcId;

	@Basic
	@Column(name = "INSERT_BY")
	protected String insertBy;

	@Transient
	protected String color;

	@Transient
	protected String oldNewIcmc;

	@Basic
	@Column(name = "UPDATE_BY")
	protected String updateBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "INSERT_TIME", nullable = false, updatable = false)
	@CreationTimestamp
	protected Calendar insertTime;

	@Column(name = "UPDATE_TIME")
	@UpdateTimestamp
	protected Calendar updateTime;

	@Enumerated(EnumType.STRING)
	@Column(name = "VAULT_SIZE")
	protected VaultSize vaultSize;

}
