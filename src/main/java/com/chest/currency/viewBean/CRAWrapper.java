/*******************************************************************************
 * /* Copyright (C) Indicsoft Technologies Pvt Ltd
 * * All Rights Reserved.
 *******************************************************************************/
package com.chest.currency.viewBean;

import java.util.List;

import com.chest.currency.entity.model.CRA;
import com.mysema.query.Tuple;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class CRAWrapper {

	CRA cra;
	List<Tuple> tupleList;
}
