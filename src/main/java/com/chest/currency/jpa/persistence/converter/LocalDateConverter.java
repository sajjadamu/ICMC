/*******************************************************************************
 * /* Copyright (C) Indicsoft Technologies Pvt Ltd
 * * All Rights Reserved.
 *******************************************************************************/
package com.chest.currency.jpa.persistence.converter;

import java.sql.Timestamp;
import java.time.Instant;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class LocalDateConverter implements AttributeConverter<Instant, Timestamp>{

	public Timestamp convertToDatabaseColumn(Instant attributeValue){
		if(attributeValue != null){
			return Timestamp.from(attributeValue);
		}
		return null;
	}
	
	public Instant convertToEntityAttribute(Timestamp databaseValue){
		if(databaseValue != null){
			return databaseValue.toInstant();
		}
		return null;
	}
}
