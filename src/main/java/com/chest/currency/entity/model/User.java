package com.chest.currency.entity.model;

import java.math.BigInteger;
import java.util.Calendar;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import com.chest.currency.enums.Status;
import com.chest.currency.enums.Zone;

@Entity(name = "User")
@Table(name = "UI_USER")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Data
@EqualsAndHashCode(of = { "id" })
@ToString
public class User {

	@Id
	@Column(name = "ID")
	protected String id;

	@Basic
	@Column(name = "NAME")
	protected String name;

	@Basic
	@Column(name = "PASSWORD")
	protected String password;

	@Basic
	@Column(name = "PASSWORD_HASH")
	protected String passwordHash;

	@Basic
	@Column(name = "PASSWORD_SALT")
	protected String passwordSalt;

	@Basic
	@Column(name = "EMAIL")
	protected String email;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ROLE_ID")
	protected Role role;

	@Transient
	protected String roleId;

	@Enumerated(EnumType.STRING)
	@Column(name = "ZONE_ID")
	protected Zone zoneId;

	@Basic
	@Column(name = "REGION_ID")
	protected String regionId;

	@Basic
	@Column(name = "ICMC_ID")
	protected BigInteger icmcId;

	@Enumerated(EnumType.STRING)
	@Column(name = "STATUS")
	protected Status status;

	@Basic
	@Column(name = "CREATED_BY")
	protected String createdBy;

	@Basic
	@Column(name = "UPDATED_BY")
	protected String updatedBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATED_DATETIME")
	protected Calendar createdDateTime;

	@Column(name = "UPDATED_DATETIME")
	protected Calendar updatedDateTime;

	@Basic
	@Column(name = "LAST_AUTH_OK")
	protected String lastAuthOk;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "LAST_AUTH_FAIL")
	protected Calendar lastAuthFail;

	@Basic
	@Column(name = "FAILED_LOGONS")
	protected Integer faildeLogons;

	@Basic
	@Column(name = "IS_ACCOUNT_LOCKED")
	protected Character isAccountLocked;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ICMC_PRINTER_ID")
	protected IcmcPrinter icmcPrinter;

	@Transient
	protected Long icmcPrinterId;

}
