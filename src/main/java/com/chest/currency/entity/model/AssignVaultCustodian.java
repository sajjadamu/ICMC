
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
import javax.persistence.Transient;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity(name = "AssignVaultCustodian")
@Table(name = "ASSIGN_VAULT_CUSTODIAN")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Data
@EqualsAndHashCode(of = { "id" })
@ToString
public class AssignVaultCustodian {

	@Id
	@Column(name = "ID")
	@GeneratedValue(generator = "ASSIGN_VAULT_CUSTODIAN_SEQ")
	@SequenceGenerator(name = "ASSIGN_VAULT_CUSTODIAN_SEQ", sequenceName = "ASSIGN_VAULT_CUSTODIAN_SEQ", allocationSize = 100)
	protected Long id;

	@Basic
	@Column(name = "HANDING_OVER_CHARGE")
	protected String handingOverCharge;

	@Basic
	@Column(name = "REASON")
	protected String reason;

	@Basic
	@Column(name = "REMARKS")
	protected String remarks;

	@Basic
	@Column(name = "USER_ID")
	protected String userId;

	@Basic
	@Column(name = "CUSTODIAN")
	protected String custodian;

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
	@Column(name = "INSERT_TIME", updatable = false, nullable = false)
	@CreationTimestamp
	protected Calendar insertTime;

	@Column(name = "UPDATE_TIME", nullable = false)
	@UpdateTimestamp
	protected Calendar updateTime;

	@Column(name = "IS_ASSIGN", nullable = false)
	protected Boolean isAssign = true;

	@Transient
	protected String userName;
	
	@Transient
	protected String handOveredUserName;

}
