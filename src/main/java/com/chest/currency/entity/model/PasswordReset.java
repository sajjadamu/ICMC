/*******************************************************************************
 * /* Copyright (C) Indicsoft Technologies Pvt Ltd
 * * All Rights Reserved.
 *******************************************************************************/
package com.chest.currency.entity.model;

import java.util.Calendar;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.chest.currency.enums.Status;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * JPA annotated class mapped to UI_USER_PASSWORD_RESET
 * @author peerhasan
 *
 */
@Entity(name = "PasswordReset")
@Table(name = "UI_USER_PASSWORD_RESET")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Data
@EqualsAndHashCode(of = {"userId"})
@ToString
public class PasswordReset {
	
	@Id
	@Column(name = "USER_ID")
	protected String userId;
	
	@Basic
	@Column(name = "TOKEN" )
	protected String token;
	

	@Enumerated(EnumType.STRING)
	@Column(name = "STATUS" )
	protected Status status;
	
	@Basic
	@Column(name = "CREATED_BY" )
	protected String createdBy;
	
	@Basic
	@Column(name = "UPDATED_BY" )
	protected String updatedBy;
	
	@Temporal( TemporalType.TIMESTAMP )
	@Column(name = "CREATED_DATETIME" )
	protected Calendar createdDateTime;
	
	@Column(name = "UPDATED_DATETIME" )
	protected Calendar updatedDateTime;
	
	@Transient
	protected String password;
	
	@Transient
	protected String rePassword;
	
}
