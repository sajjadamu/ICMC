/*******************************************************************************
 * /* Copyright (C) Indicsoft Technologies Pvt Ltd
 * * All Rights Reserved.
 *******************************************************************************/
package com.chest.currency.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.chest.currency.viewBean.FreshBean;

public class FreshMapper implements RowMapper<FreshBean> {

	@Override
	public FreshBean mapRow(ResultSet resultSet, int rowNum) throws SQLException {
		FreshBean freshBean = new FreshBean();
		freshBean.setId(resultSet.getInt("id"));
		freshBean.setBundle(resultSet.getInt("bundle"));
		freshBean.setTotal(resultSet.getInt("total"));
		freshBean.setBin(resultSet.getString("bin"));
		freshBean.setNotesOrCoins(resultSet.getString("notes_or_coins"));
		freshBean.setOrder_date(resultSet.getDate("order_date"));
		freshBean.setDenomination(resultSet.getInt("denomination"));
		freshBean.setIcmcId(resultSet.getInt("ICMC_ID"));
		return freshBean;
	}

}
