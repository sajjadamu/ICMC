/*******************************************************************************
 * /* Copyright (C) Indicsoft Technologies Pvt Ltd
 * * All Rights Reserved.
 *******************************************************************************/
package com.chest.currency.viewBean;

import java.math.BigDecimal;
import java.util.List;

import com.chest.currency.entity.model.Indent;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class IndentWrapper {

	List<Indent> indentList;
	BigDecimal totalBundle;
}
