/*******************************************************************************
 * /* Copyright (C) Indicsoft Technologies Pvt Ltd
 * * All Rights Reserved.
 *******************************************************************************/
package com.chest.currency.jpa.persistence.converter;

import java.sql.Date;
import java.time.LocalDate;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class InstantConverter implements AttributeConverter<LocalDate, Date>{

	public Date convertToDatabaseColumn(LocalDate attributeValue){
		if(attributeValue != null){
			return Date.valueOf(attributeValue);
		}
		return null;
	}
	
	public LocalDate convertToEntityAttribute(Date databaseValue){
		if(databaseValue != null){
			return databaseValue.toLocalDate();
		}
		return null;
	}
}
