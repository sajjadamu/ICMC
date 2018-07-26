/*******************************************************************************
 * /* Copyright (C) Indicsoft Technologies Pvt Ltd
 * * All Rights Reserved.
 *******************************************************************************/
package com.chest.currency.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.chest.currency.viewBean.DSBBean;

public class DSBMapper implements RowMapper<DSBBean> {

	@Override
	public DSBBean mapRow(ResultSet resultSet, int rowNum) throws SQLException {
		DSBBean dsbBean = new DSBBean();
		dsbBean.setId(resultSet.getInt("id"));
		dsbBean.setName(resultSet.getString("name"));
		dsbBean.setDenomination(resultSet.getInt("denomination"));
		dsbBean.setBin(resultSet.getString("bin_num"));
		dsbBean.setTotal(resultSet.getInt("total"));
		dsbBean.setBundle(resultSet.getInt("bundle"));
		dsbBean.setProcessinORVault(resultSet.getString("processing_or_vault"));
		dsbBean.setIcmcId(resultSet.getInt("ICMC_ID") );
		return dsbBean;
	}

}
