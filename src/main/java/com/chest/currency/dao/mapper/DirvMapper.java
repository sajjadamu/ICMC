/*******************************************************************************
 * /* Copyright (C) Indicsoft Technologies Pvt Ltd
 * * All Rights Reserved.
 *******************************************************************************/
package com.chest.currency.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.chest.currency.viewBean.DirvBean;

public class DirvMapper implements RowMapper<DirvBean> {

	@Override
	public DirvBean mapRow(ResultSet resultSet, int rowNum) throws SQLException {
		DirvBean dirv = new DirvBean();
		dirv.setId(resultSet.getInt("id"));
		dirv.setOrder_date(resultSet.getString("order_date"));
		dirv.setRbi_order_no(resultSet.getString("rbi_order_no"));
		dirv.setExpiry_date(resultSet.getString("expiry_date"));
		dirv.setBankName(resultSet.getString("bank_name"));
		dirv.setApprovedCC(resultSet.getString("approved_cc"));
		dirv.setLocation(resultSet.getString("location"));
		dirv.setDenomination(resultSet.getInt("denomination"));
		dirv.setBundle(resultSet.getInt("bundle"));
		dirv.setTotal(resultSet.getInt("total"));
		dirv.setBinNumber(resultSet.getString("bin_number"));
		dirv.setCategory(resultSet.getString("category"));
		dirv.setIcmcId(resultSet.getInt("ICMC_ID"));
		return dirv;
	}

}
