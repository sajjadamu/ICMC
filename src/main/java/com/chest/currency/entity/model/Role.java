/*******************************************************************************
 * /* Copyright (C) Indicsoft Technologies Pvt Ltd
 * * All Rights Reserved.
 *******************************************************************************/
package com.chest.currency.entity.model;

import java.util.Calendar;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import com.chest.currency.enums.IcmcAccess;
import com.chest.currency.enums.PermissionName;
import com.chest.currency.enums.Status;

@Entity(name = "Role")
@Table(name = "UI_ROLE")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Data
@EqualsAndHashCode(of = {"id"})
@ToString
public class Role {
	
	@Id
	@Column(name = "ID")
	protected String id;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "ICMC_ACCESS" )
	protected IcmcAccess icmcAccess;
	
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
	
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable( name = "UI_ROLE_PERMISSION", joinColumns = @JoinColumn (name = "ROLE_NAME"))
	@Column(name = "FUNCTION_PERMISSION" )
	@Enumerated(EnumType.STRING)
	protected List<PermissionName> rolePermission;
	
}
