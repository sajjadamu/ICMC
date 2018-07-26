/*******************************************************************************
 * /* Copyright (C) Indicsoft Technologies Pvt Ltd
 * * All Rights Reserved.
 *******************************************************************************/
package com.chest.currency.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.chest.currency.viewBean.DorvBean;

public class DORVMapper implements RowMapper<DorvBean> {

	@Override
	public DorvBean mapRow(ResultSet resultSet, int rowNum) throws SQLException {
		DorvBean dorvBean = new DorvBean();
		dorvBean.setId(resultSet.getInt("id"));
		dorvBean.setOrderDate(resultSet.getDate("order_date"));
		dorvBean.setRBIOrderNumber(resultSet.getString("rbi_order_no"));
		dorvBean.setExpiryDate(resultSet.getDate("expiry_date"));
		dorvBean.setBankName(resultSet.getString("bank_name"));
		dorvBean.setLocation(resultSet.getString("location"));
		dorvBean.setApprovedCC(resultSet.getString("approved_cc"));
		return dorvBean;
	}

}
