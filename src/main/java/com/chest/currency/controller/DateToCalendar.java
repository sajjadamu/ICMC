/*******************************************************************************
 * /* Copyright (C) Indicsoft Technologies Pvt Ltd
 * * All Rights Reserved.
 *******************************************************************************/
package com.chest.currency.controller;

import java.util.Calendar;
import java.util.Date;

import org.supercsv.cellprocessor.CellProcessorAdaptor;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.cellprocessor.ift.DateCellProcessor;
import org.supercsv.exception.SuperCsvCellProcessorException;
import org.supercsv.util.CsvContext;

public class DateToCalendar extends CellProcessorAdaptor implements DateCellProcessor {

	public DateToCalendar() {
	}

	public DateToCalendar(final CellProcessor next) {
		super(next);
	}

	@SuppressWarnings("unchecked")
	public Object execute(final Object value, final CsvContext context) {
		validateInputNotNull(value, context);

		if (!(value instanceof Date)) {
			throw new SuperCsvCellProcessorException(Date.class, value, context, this);
		}

		Calendar result = Calendar.getInstance();
		result.setTime((Date) value);

		return next.execute(result, context);

	}
}