package com.chest.currency.entity.model;

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

import com.chest.currency.enums.Activity;
import com.chest.currency.enums.LamStatus;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity(name = "LamRequestLog")
@Table(name = "LAM_REQUEST_LOG")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Data
@EqualsAndHashCode(of = { "id" })
@ToString
public class LamRequestLog {

	@Id
	@Column(name = "ID")
	@GeneratedValue(generator = "LAM_REQUEST_LOG_SEQ")
	@SequenceGenerator(name = "LAM_REQUEST_LOG_SEQ", sequenceName = "LAM_REQUEST_LOG_SEQ", allocationSize = 100)
	protected Long id;

	@Enumerated(EnumType.STRING)
	@Column(name = "ACTIVITY")
	protected Activity activity;

	@Basic
	@Column(name = "ACCESS_REQUEST")
	protected String accesRequest;

	@Basic
	@Column(name = "ROLES")
	protected String roles;

	@Basic
	@Column(name = "ADDITIONAL_DETAILS")
	protected String additionalDetails;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATED_DATETIME")
	protected Calendar createdDateTime;

	@Column(name = "UPDATED_DATETIME")
	protected Calendar updatedDateTime;
	
	@Basic
	@Column(name = "REQUEST_URL")
	protected String requestUrl;

	@Enumerated(EnumType.STRING)
	@Column(name = "RESPONSE")
	protected LamStatus response;

}
