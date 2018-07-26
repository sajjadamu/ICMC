/*******************************************************************************
 * /* Copyright (C) Indicsoft Technologies Pvt Ltd
 * * All Rights Reserved.
 *******************************************************************************/
package com.chest.currency.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

import com.chest.currency.viewBean.SASBean;

public class SASMapper implements RowMapper<SASBean> {
	public SASBean mapRow(ResultSet resultSet, int rowNum) throws SQLException {
		SASBean sasBean = new SASBean();
		sasBean.setId(resultSet.getInt("id"));
		sasBean.setSRno(resultSet.getString("sr_no"));
		sasBean.setCreateDate(resultSet.getString("create_date"));
		sasBean.setNotes(resultSet.getString("notes"));
		sasBean.setBranchEcName(resultSet.getString("branch_ec_name"));
		sasBean.setDateOfRequirements(resultSet.getString("Date_of_Requirements"));
		sasBean.setSrOriginatingBranchName(resultSet.getString("SR_originating_Branch_Name"));
		sasBean.setTotalIndentedValue(resultSet.getInt("total_indented_value"));
		sasBean.setTotalValueOfCoinsRs1(resultSet.getInt("Total_Value_of_Coins_Rs_1"));
		sasBean.setTotalValueOfCoinsRs10(resultSet.getInt("Total_Value_of_Coins_Rs_10"));
		sasBean.setTotalValueOfCoinsRs2(resultSet.getInt("Total_Value_of_Coins_Rs_2"));
		sasBean.setTotalValueOfCoinsRs5(resultSet.getInt("Total_Value_of_Coins_Rs_5"));
		sasBean.setTotalValueOfNotesRs1_I(resultSet.getInt("Total_Value_of_Notes_Rs_1_I"));
		sasBean.setTotalValueOfNotesRs10_I(resultSet.getInt("Total_Value_of_Notes_Rs_10_I"));
		sasBean.setTotalValueOfNotesRs100_I(resultSet.getInt("Total_Value_of_Notes_Rs_100_I"));
		sasBean.setTotalValueOfNotesRs1000_I(resultSet.getInt("Total_Value_of_Notes_Rs_1000_I"));
		sasBean.setTotalValueOfNotesRs2_I(resultSet.getInt("Total_Value_of_Notes_Rs_2_I"));
		sasBean.setTotalValueOfNotesRs20_I(resultSet.getInt("Total_Value_of_Notes_Rs_20_I"));
		sasBean.setTotalValueOfNotesRs5_I(resultSet.getInt("Total_Value_of_Notes_Rs_5_I"));
		sasBean.setTotalValueOfNotesRs50_I(resultSet.getInt("Total_Value_of_Notes_Rs_50_I"));
		sasBean.setTotalValueOfNotesRs500_I(resultSet.getInt("Total_Value_of_Notes_Rs_500_I"));
		sasBean.setTotalValueOfNotesRs1_F(resultSet.getInt("Total_Value_of_Notes_Rs_1_F"));
		sasBean.setTotalValueOfNotesRs10_F(resultSet.getInt("Total_Value_of_Notes_Rs_10_F"));
		sasBean.setTotalValueOfNotesRs100_F(resultSet.getInt("Total_Value_of_Notes_Rs_100_F"));
		sasBean.setTotalValueOfNotesRs1000_F(resultSet.getInt("Total_Value_of_Notes_Rs_1000_F"));
		sasBean.setTotalValueOfNotesRs2_F(resultSet.getInt("Total_Value_of_Notes_Rs_2_F"));
		sasBean.setTotalValueOfNotesRs20_F(resultSet.getInt("Total_Value_of_Notes_Rs_20_F"));
		sasBean.setTotalValueOfNotesRs5_F(resultSet.getInt("Total_Value_of_Notes_Rs_5_F"));
		sasBean.setTotalValueOfNotesRs50_F(resultSet.getInt("Total_Value_of_Notes_Rs_50_F"));
		sasBean.setTotalValueOfNotesRs500_F(resultSet.getInt("Total_Value_of_Notes_Rs_500_F"));
		sasBean.setActiontaken(resultSet.getString("Action_Taken"));
		sasBean.setDateOfDispatch(resultSet.getString("Date_of_Dispatch"));
		return sasBean;
	}

}
