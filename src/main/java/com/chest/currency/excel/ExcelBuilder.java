/*******************************************************************************
 * /* Copyright (C) Indicsoft Technologies Pvt Ltd
 * * All Rights Reserved.
 *******************************************************************************/
package com.chest.currency.excel;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.springframework.web.servlet.view.document.AbstractJExcelView;

import com.chest.currency.viewBean.IRVBean;

/**
 * This class builds an Excel spreadsheet document using JExcelApi library.
 * @author www.codejava.net
 *
 */
public class ExcelBuilder extends AbstractJExcelView {

	@Override
	protected void buildExcelDocument(Map<String, Object> model,
			WritableWorkbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		// get data model which is passed by the Spring container
		List<IRVBean> listBooks = (List<IRVBean>) model.get("list");
		
		// create a new Excel sheet
		WritableSheet sheet = workbook.createSheet("Java Books", 0);
		
		// create header row
		sheet.addCell(new Label(0, 0, "SR"));
		sheet.addCell(new Label(1, 0, "Sol ID"));
		sheet.addCell(new Label(2, 0, "Branch"));
		sheet.addCell(new Label(3, 0, "Total"));
		//sheet.addCell(new Label(4, 0, "Price"));
		
		// create data rows
		int rowCount = 1;
		
		for (IRVBean aBook : listBooks) {
			sheet.addCell(new Label(0, rowCount, aBook.getSR()));
			sheet.addCell(new Label(1, rowCount, aBook.getSolId()));
			sheet.addCell(new Label(2, rowCount, aBook.getBranch()));
			sheet.addCell(new Label(3, rowCount, aBook.getTotal()));
			//sheet.addCell(new jxl.write.Number(4, rowCount, aBook.getPrice()));
			
			rowCount++;
		}
	}
}