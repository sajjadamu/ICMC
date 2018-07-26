/*******************************************************************************
 * /* Copyright (C) Indicsoft Technologies Pvt Ltd
 * * All Rights Reserved.
 *******************************************************************************/
package com.chest.currency.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.chest.currency.dao.mapper.BinTransactionSummaryMapper;
import com.chest.currency.dao.mapper.ICMCMapper;
import com.chest.currency.viewBean.BinColor;
import com.chest.currency.viewBean.BinMaster;
import com.chest.currency.viewBean.BinTransaction;
import com.chest.currency.viewBean.BinTransactionSummary;
import com.chest.currency.viewBean.ICMC;

@Repository
public class BinDashboardDaoImpl implements BinDashboardDao {
	private static final Logger LOG = LoggerFactory.getLogger(BinDashboardDaoImpl.class);

	DataSource dataSource;

	@Autowired
	private JdbcTemplateDao jdbcTemplateDao;

	public DataSource getDataSource() {
		return this.dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public List<BinColor> getBinNumAndColorCode(int icmcId) {
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		String Query = "";
		List<BinColor> dataList = new ArrayList<BinColor>();
		BinColor binData;
		try {
			connection = dataSource.getConnection();
			statement = connection.createStatement();
			Query = "select tbl_bin_master.bin_num,tbl_bin_master.icmc_id,tbl_bin_master.first_priority,tbl_bin_type_color.color_code,"
					+ "tbl_bin_type_color.html_color_code from tbl_bin_master,tbl_bin_type_color "
					+ "where tbl_bin_type_color.bin_type=tbl_bin_master.first_priority and tbl_bin_master.icmc_id="
					+ icmcId + ";";
			resultSet = statement.executeQuery(Query);
			LOG.debug("COLOR CODE QUERY===" + Query);
			while (resultSet.next()) {
				binData = new BinColor();
				binData.setBinNum(resultSet.getString("bin_num"));
				binData.setHtmlColorCode(resultSet.getString("html_color_code"));
				dataList.add(binData);
			}

		} catch (Exception e) {
			LOG.error(e.getMessage());
		}
		return dataList;
	}

	@Override
	public List<String> getAvailableCapacity(String bin) {
		List<String> list = new ArrayList<String>();
		Connection connection = null;
		Statement statement = null;
		String Query = "";
		ResultSet resultSet = null;
		try {
			connection = dataSource.getConnection();
			statement = connection.createStatement();
			Query = "select max_capacity,denomination,bin_type,bin_num,receive_bundle from tbl_bin_transaction where status=1 and bin_num = '"
					+ bin + "';";
			LOG.debug("Bin Details Query==" + Query);
			resultSet = statement.executeQuery(Query);
			if (resultSet.next()) {
				list.add(resultSet.getString("bin_num"));
				list.add(resultSet.getString("denomination"));
				list.add(resultSet.getString("bin_type"));
				list.add(resultSet.getString("max_capacity"));
				list.add(resultSet.getString("receive_bundle"));

			}
		} catch (Exception e) {
			LOG.error(e.getMessage());
		}
		return list;
	}

	@Override
	public List<BinTransactionSummary> getBinSummary(int denomination, String binType) {
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		String Query = "";
		List<BinTransactionSummary> binTxnList = new ArrayList<>();
		BinTransactionSummary binSummary;
		try {
			connection = dataSource.getConnection();
			statement = connection.createStatement();
			Query = "select sum(receive_bundle) as bundle from tbl_bin_transaction where denomination = " + denomination
					+ " and bin_type = '" + binType + "';";
			resultSet = statement.executeQuery(Query);
			System.out.println("Query==" + Query);
			while (resultSet.next()) {
				binSummary = new BinTransactionSummary();
				/*
				 * binSummary.setFresh(resultSet.getString("bundle"));
				 * binSummary.setUnprocess(resultSet.getString("bundle"));
				 * binSummary.setIssuable(resultSet.getString("bundle"));
				 * binSummary.setSoiled(resultSet.getString("bundle"));
				 * binSummary.setATM(resultSet.getString("bundle"));
				 */
				binTxnList.add(binSummary);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return binTxnList;
	}

	@Override
	public List<BinMaster> getBinMasterRecord() {
		// TODO Auto-generated method stub
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		String Query = "";
		List<BinMaster> binList = new ArrayList<>();
		BinMaster binData;
		try {
			connection = dataSource.getConnection();
			statement = connection.createStatement();
			Query = "select * from tbl_bin_master;";
			resultSet = statement.executeQuery(Query);
			while (resultSet.next()) {
				binData = new BinMaster();
				binData.setFirstPriority(resultSet.getString("first_priority"));
				binData.setSecondPriority(resultSet.getString("second_priority"));
				binData.setThirdPriority(resultSet.getString("third_priority"));
				binData.setLocationPriority(resultSet.getInt("loca_priority"));
				binData.setBinNumber(resultSet.getString("bin_num"));
				binList.add(binData);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return binList;
	}

	@Override
	public int insertDataInBinMaster(BinMaster binMaster) {
		int count = 0;
		String Query = "insert into tbl_bin_master (first_priority,second_priority,third_priority,loca_priority,bin_num,icmc_id,insert_time,update_time,insert_by,update_by) values (?,?,?,?,?,?,now(),now(),?,?)";
		JdbcTemplate jdbcTemplate = jdbcTemplateDao.getJdbcTemplate();
		count = jdbcTemplate.update(Query, binMaster.getFirstPriority(), binMaster.getSecondPriority(),
				binMaster.getThirdPriority(), binMaster.getLocationPriority(), binMaster.getBinNumber(),
				binMaster.getICMCId(), binMaster.getInsertBy(), binMaster.getUpdateBy());
		return count;
	}

	@Override
	public List<BinTransaction> searchBin(int denomination, String binType, int icmcId) {
		Connection connection = null;
		Statement statement = null;
		String Query = "";
		ResultSet resultSet = null;
		BinTransaction binTransaction;
		List<BinTransaction> binTxList = new ArrayList<>();
		try {
			connection = dataSource.getConnection();
			statement = connection.createStatement();
			Query = "select tbl_txn.bin_type,tbl_txn.bin_num,tbl_type.html_color_code,tbl_type.bin_type from tbl_bin_transaction tbl_txn,tbl_bin_type_color tbl_type where tbl_txn.bin_type=tbl_type.bin_type AND tbl_txn.denomination = "
					+ denomination + " and tbl_txn.bin_type='" + binType + "' and tbl_txn.ICMC_ID=" + icmcId + ";";
			resultSet = statement.executeQuery(Query);
			System.out.println("QUERY==" + Query);
			while (resultSet.next()) {
				binTransaction = new BinTransaction();
				binTransaction.setBinNumber(resultSet.getString("bin_num"));
				binTransaction.setColorCode(resultSet.getString("html_color_code"));
				binTxList.add(binTransaction);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return binTxList;

	}

	@Override
	public List<BinTransactionSummary> getRecordForSummary(int icmcId) {
		JdbcTemplate jdbcTemplate = jdbcTemplateDao.getJdbcTemplate();
		String Query = "select sum(receive_bundle) as receive_bundle, bin_type, denomination from tbl_bin_transaction where icmc_id = "
				+ icmcId + " group by bin_type, denomination";
		List<BinTransactionSummary> binTxnList = jdbcTemplate.query(Query, new BinTransactionSummaryMapper());
		return binTxnList;
	}

	@Override
	public List<ICMC> getICMCName() {
		JdbcTemplate jdbcTemplate = jdbcTemplateDao.getJdbcTemplate();
		String Query = "select * from ICMC;";
		List<ICMC> list = jdbcTemplate.query(Query, new ICMCMapper());
		return list;
	}
}
