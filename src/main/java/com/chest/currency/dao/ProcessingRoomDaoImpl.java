/*******************************************************************************
 * /* Copyright (C) Indicsoft Technologies Pvt Ltd
 * * All Rights Reserved.
 *******************************************************************************/
package com.chest.currency.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.chest.currency.dao.mapper.IndentRequestBeanMapper;
import com.chest.currency.dao.mapper.MachineAllocationMapper;
import com.chest.currency.dao.mapper.ProcessMapper;
import com.chest.currency.entity.model.Process;
import com.chest.currency.qrgencode.QRCodeGen;
import com.chest.currency.util.Utility;
import com.chest.currency.viewBean.BinMaster;
import com.chest.currency.viewBean.BinTransaction;
import com.chest.currency.viewBean.IndentRequestBean;
import com.chest.currency.viewBean.MachineAllocationBean;
import com.chest.currency.viewBean.ProcessBean;

@Repository
public class ProcessingRoomDaoImpl implements ProcessingRoomDao {

	private static final Logger LOG = LoggerFactory.getLogger(UserDaoImpl.class);

	DataSource dataSource;

	@Autowired
	private JdbcTemplateDao jdbcTemplateDao;

	@Autowired
	QRCodeGen qrCodeGen;

	public DataSource getDataSource() {
		return this.dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public String getBinForIndentRequest(String denomination, String bundle) {
		Connection connection = null;
		Statement statement = null;
		String Query = "";
		String bin_num = "";
		ResultSet resultSet = null;
		try {
			connection = dataSource.getConnection();
			statement = connection.createStatement();
			Query = "select bin_num from tbl_bin_transaction where denomination='" + denomination
					+ "' and receive_bundle>=" + bundle
					+ " and status=1 and bin_type='Unprocess' order by insert_time asc;";
			resultSet = statement.executeQuery(Query);
			if (resultSet.next()) {
				bin_num = resultSet.getString("bin_num");
			}
		} catch (Exception e) {
			LOG.error(e.getMessage());
			e.printStackTrace();
		}
		return bin_num;
	}

	@Override
	public boolean insertIndentRequest(List<IndentRequestBean> eligibleIndentRequestList) {
		String Query = "";
		boolean isSuccessfull = false;
		int count = 0;
		Query = "insert into tbl_indent (denomination,bundle,bin_num,description,insert_time,update_time,insert_by,update_by) values (?,?,?,?,now(),now(),?,?);";
		JdbcTemplate jdbcTemplate = jdbcTemplateDao.getJdbcTemplate();
		for (IndentRequestBean eligibleIndentRequest : eligibleIndentRequestList) {
			count = jdbcTemplate.update(Query, eligibleIndentRequest.getDenomination(),
					eligibleIndentRequest.getBundle(), eligibleIndentRequest.getBin(),
					eligibleIndentRequest.getDenomination(), eligibleIndentRequest.getInsertBy(),
					eligibleIndentRequest.getUpdateBy());
			if (count > 0) {
				isSuccessfull = true;
			} else {
				isSuccessfull = false;
			}
		}
		return isSuccessfull;
	}

	@Override
	public List<IndentRequestBean> getIndentRequest() {
		JdbcTemplate jdbcTemplate = jdbcTemplateDao.getJdbcTemplate();
		String Query = "select * from tbl_indent where status=1;";
		List<IndentRequestBean> indentList = jdbcTemplate.query(Query, new IndentRequestBeanMapper());
		return indentList;
	}

	@Override
	public boolean updateIndentRequest(IndentRequestBean bean) {
		boolean isSuccessfull = false;
		int count = 0;
		String Query = "update tbl_indent set denomination=?,bundle=?,bin_num=?,update_time=now(),insert_by=?,update_by=? where id =?;";
		JdbcTemplate jdbcTemplate = jdbcTemplateDao.getJdbcTemplate();
		// Timestamp ts = new Timestamp(bean.getUpdateTime().getTime());
		count = jdbcTemplate.update(Query, bean.getDenomination(), bean.getBundle(), bean.getBin(), bean.getInsertBy(),
				bean.getUpdateBy(), bean.getId());
		if (count > 0) {
			isSuccessfull = true;
		} else {
			isSuccessfull = false;

		}
		return isSuccessfull;
	}

	@Override
	public IndentRequestBean getIndentRequestForEdit(int id) {
		JdbcTemplate jdbcTemplate = jdbcTemplateDao.getJdbcTemplate();
		String Query = "select * from tbl_indent where id = " + id + ";";
		IndentRequestBean indentBean = jdbcTemplate.queryForObject(Query, new IndentRequestBeanMapper());
		return indentBean;
	}

	public boolean InsertMachineAllocationData(MachineAllocationBean machineBean) {
		int count = 0;
		boolean isSuccessfull = false;
		String Query = "insert into tbl_machine_allocation (denomination,bundle,machine,issued_bundle,insert_time,update_time,insert_by,update_by) values (?,?,?,?,now(),now(),?,?);";
		JdbcTemplate jdbcTemplate = jdbcTemplateDao.getJdbcTemplate();
		count = jdbcTemplate.update(Query, machineBean.getDenomination(), machineBean.getBundle(),
				machineBean.getMachine(), machineBean.getIssued_bundle(), machineBean.getInsertBy(),
				machineBean.getUpdateBy());
		if (count > 0) {
			isSuccessfull = true;
		} else {
			isSuccessfull = false;
		}
		return isSuccessfull;
	}

	@Override
	public int getBundleFromIndent(int id) {
		JdbcTemplate jdbcTemplate = jdbcTemplateDao.getJdbcTemplate();
		String Query = "select bundle from tbl_indent where id =?";
		Integer bundle = jdbcTemplate.queryForObject(Query, new Object[] { id }, Integer.class);
		return ((bundle == null) ? 0 : bundle.intValue());
	}

	@Override
	public boolean updateBundleInIndent(MachineAllocationBean machine) {
		boolean isSuccessfull = false;
		int count = 0;
		String Query = "update tbl_indent set bundle=" + machine.getBundle() + ",update_time=now() where id =?";
		JdbcTemplate jdbcTemplate = jdbcTemplateDao.getJdbcTemplate();
		count = jdbcTemplate.update(Query, machine.getId());
		if (count > 0) {
			isSuccessfull = true;
		} else {
			isSuccessfull = false;
		}
		return isSuccessfull;
	}

	@Override
	public boolean saveMachineAllocationAndUpdateIndent(MachineAllocationBean machineBean) {
		boolean isAllSuccess = false;
		try {
			isAllSuccess = InsertMachineAllocationData(machineBean);
			if (isAllSuccess) {
				int bundle = getBundleFromIndent(machineBean.getId());
				double updatedBundle = bundle - machineBean.getIssued_bundle();
				machineBean.setBundle(updatedBundle);
				isAllSuccess = updateBundleInIndent(machineBean);
			}

		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
		return isAllSuccess;
	}

	@Override
	public IndentRequestBean getIndentDataByID(int id, Connection connection) throws SQLException {
		Statement statement = null;
		String Query = "";
		ResultSet resultSet = null;
		IndentRequestBean indentData = null;
		statement = connection.createStatement();
		Query = "select * from tbl_indent where id = " + id + ";";
		resultSet = statement.executeQuery(Query);
		while (resultSet.next()) {
			indentData = Utility.mapperIndentRequest(resultSet);
		}
		return indentData;
	}

	@Override
	public BinTransaction getBinFromTransaction(String bin, Connection connection) throws SQLException {
		Statement statement = null;
		ResultSet resultSet = null;
		String Query = "";
		BinTransaction binTxnData = null;
		statement = connection.createStatement();
		Query = "select * from tbl_bin_transaction where bin_num='" + bin + "';";
		resultSet = statement.executeQuery(Query);
		while (resultSet.next()) {
			binTxnData = new BinTransaction();
			binTxnData.setBinNumber(resultSet.getString("bin_num"));
			binTxnData.setDenomination(resultSet.getInt("denomination"));
			binTxnData.setMaxCapacity(resultSet.getInt("max_capacity"));
			binTxnData.setInsertTime(resultSet.getTimestamp("insert_time"));
			binTxnData.setReceiveBundles(resultSet.getDouble("receive_bundle"));
			binTxnData.setBinType(resultSet.getString("bin_type"));
			binTxnData.setStatus(resultSet.getInt("status"));
			binTxnData.setUpdateTime(resultSet.getTimestamp("update_time"));
			binTxnData.setUpdateBy(resultSet.getString("update_by"));
			binTxnData.setInsertBy(resultSet.getString("insert_by"));
		}
		return binTxnData;
	}

	@Override
	public BinMaster getBinMasterForUpdation(String bin, Connection connection) throws SQLException {
		Statement statement = null;
		ResultSet resultSet = null;
		String Query = "";
		BinMaster binData = new BinMaster();
		statement = connection.createStatement();
		Query = "select * from tbl_bin_master where bin = '" + bin + "';";
		resultSet = statement.executeQuery(Query);
		while (resultSet.next()) {
			binData.setId(resultSet.getInt("id"));
			binData.setFirstPriority(resultSet.getString("first_priority"));
			binData.setLocationPriority(resultSet.getInt("loca_priority"));
			binData.setIsAllocated(resultSet.getInt("is_allocated"));
		}
		return binData;
	}

	@Override
	public int updateIndentStatus(IndentRequestBean indentBean, Connection connection) throws SQLException {
		PreparedStatement preparedStatement = null;
		String Query = "";
		int count = 0;
		Query = "update tbl_indent set status=0 where id = ? and update_time=?";
		preparedStatement = connection.prepareStatement(Query);
		preparedStatement.setInt(1, indentBean.getId());
		Timestamp ts = new Timestamp(indentBean.getUpdateTime().getTime());
		preparedStatement.setTimestamp(2, ts);
		count = preparedStatement.executeUpdate();
		return count;
	}

	@Override
	public int updateBinTxn(String bin, Connection connection, double balanceBundle, BinTransaction txnBean)
			throws SQLException {
		PreparedStatement preparedStatement = null;
		String Query = "";
		int count = 0;
		Query = "update tbl_bin_transaction set receive_bundle = " + balanceBundle + " where bin_num = '" + bin
				+ "' and update_time = ?;";// TODO:add
		preparedStatement = connection.prepareStatement(Query);
		Timestamp ts = new Timestamp(txnBean.getUpdateTime().getTime());
		preparedStatement.setTimestamp(1, ts);
		count = preparedStatement.executeUpdate();
		return count;
	}

	@Override
	public int deleteDataFromBinTxn(String bin, Connection connection) throws SQLException {
		PreparedStatement preparedStatement = null;
		String Query = "";
		int count = 0;
		Query = "delete from tbl_bin_transaction where bin_num = '" + bin + "';";
		preparedStatement = connection.prepareStatement(Query);
		count = preparedStatement.executeUpdate();
		return count;
	}

	@Override
	public int updateBinMaster(String bin, Connection connection) throws SQLException {
		PreparedStatement preparedStatement = null;
		String Query = "";
		int count = 0;
		Query = "update tbl_bin_master set is_allocated = 0 where bin_num='" + bin + "';";
		preparedStatement = connection.prepareStatement(Query);
		count = preparedStatement.executeUpdate();
		return count;
	}

	public boolean processIndentRequest(int id, String bin, double bundle) {
		Connection connection = null;
		boolean isAllSuccess = false;
		try {
			connection = dataSource.getConnection();
			connection.setAutoCommit(false);
			IndentRequestBean indentBean = this.getIndentDataByID(id, connection);
			BinTransaction txnBean = this.getBinFromTransaction(bin.trim(), connection);
			if (indentBean != null && txnBean != null && txnBean.getReceiveBundles() != 0
					&& indentBean.getBundle() != 0) {

				double balanceBundle = txnBean.getReceiveBundles() - indentBean.getBundle();
				int count = this.updateIndentStatus(indentBean, connection);
				if (count > 0 && balanceBundle == 0) {
					count = this.deleteDataFromBinTxn(bin.trim(), connection);
					if (count > 0) {
						count = this.updateBinMaster(bin.trim(), connection);
					}
				} else if (count > 0 && balanceBundle > 0) {
					count = this.updateBinTxn(bin.trim(), connection, balanceBundle, txnBean);
				}

				if (count > 0) {
					isAllSuccess = true;
					connection.commit();
				} else {
					connection.rollback();
				}
			}
		} catch (Exception e) {
			LOG.error(e.getMessage());
			try {
				connection.rollback();
			} catch (SQLException e1) {

				LOG.error(e1.getMessage());
			}
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				LOG.error(e.getMessage());
			}
		}

		return isAllSuccess;
	}

	@Override
	public List<BinTransaction> getBinNumListForIndent(int denomination, double bundle) {
		List<BinTransaction> txnList = new ArrayList<BinTransaction>();
		BinTransaction txnData;
		Connection connection = null;
		Statement statement = null;
		String Query = "";
		ResultSet resultSet = null;
		try {
			connection = dataSource.getConnection();
			statement = connection.createStatement();
			Query = "select * from tbl_bin_transaction where denomination='" + denomination
					+ "' and status=1 and bin_type='Unprocess' order by insert_time asc;";
			resultSet = statement.executeQuery(Query);
			while (resultSet.next()) {
				txnData = Utility.mapperBinTransaction(resultSet);
				txnList.add(txnData);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return txnList;
	}

	@Override
	public List<IndentRequestBean> getBinFromIndent(int denomination) {
		Connection connection = null;
		Statement statement = null;
		String Query = "";
		ResultSet resultSet = null;
		IndentRequestBean indentBean;
		List<IndentRequestBean> indentList = new ArrayList<IndentRequestBean>();
		try {
			connection = dataSource.getConnection();
			statement = connection.createStatement();
			Query = "select * from tbl_indent where status=1 and denomination = " + denomination + " ;";
			resultSet = statement.executeQuery(Query);
			while (resultSet.next()) {
				indentBean = Utility.mapperIndentRequest(resultSet);
				indentList.add(indentBean);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return indentList;
	}

	@Override
	public boolean binForIndentFromTxnAndIndent(List<IndentRequestBean> eligibleIndentRequestList) {
		boolean isAllSuccess = false;
		try {
			isAllSuccess = insertIndentRequest(eligibleIndentRequestList);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
		return isAllSuccess;
	}

	public String getProcessQR(Process process) {
		String filepath = qrCodeGen.generateProcessingRoomQR(process);
		String[] str = filepath.split("/html");
		String path = "http://localhost" + str[1]; // for desktop
		return path;
	}

	/*@Override
	public boolean saveProcessingRoom(List<Process> processList) {
		boolean isSuccessfull = false;
		int count = 0;
		String Query = "insert into tbl_process(machine_no,denomination,currency_type,bundle,total,insert_time,bin_number,filepath,update_time,insert_by,update_by) "
				+ "values(?,?,?,?,?,now(),?,?,now(),?,?)";
		JdbcTemplate jdbcTemplate = jdbcTemplateDao.getJdbcTemplate();
		for (Process processBean : processList) {
			processBean.setFilepath(getProcessQR(processBean));
			count = jdbcTemplate.update(Query, processBean.getMachineNo(), processBean.getDenomination(),
					processBean.getType(), processBean.getBundle(), processBean.getTotal(), processBean.getBin(),
					processBean.getFilepath(), processBean.getInsertBy(), processBean.getUpdateBy());
			if (count > 0) {
				isSuccessfull = true;
			} else {
				isSuccessfull = false;
				break;
			}
		}
		return isSuccessfull;
	}*/

	@Override
	public boolean saveTxList(List<BinTransaction> txList) {
		String UpdateBinMasterQuery = "update tbl_bin_master set is_allocated=1 where bin_num=? and is_allocated=0;";
		String insertInBinTxn = "insert into tbl_bin_transaction (bin_num,denomination,max_capacity,insert_time,receive_bundle,bin_type,status,update_time) values (?,?,?,now(),?,?,0,now());";
		String updateInBinTxn = "update tbl_bin_transaction set bin_num=?,denomination=?,max_capacity=?,receive_bundle=?,bin_type=? where id =? and update_time=?;";
		boolean isAllSuccess = false;
		JdbcTemplate jdbcTemplate = jdbcTemplateDao.getJdbcTemplate();
		for (BinTransaction binTx : txList) {
			int countLocal = 0;
			int id = binTx.getId();
			if (id == 0) {
				countLocal = jdbcTemplate.update(UpdateBinMasterQuery, binTx.getBinNumber());
				if (countLocal == 0) {
					LOG.debug("Stale BinMaster:" + binTx.getBinNumber()
							+ " has been updated by other user. Please try again.");
				}
				if (countLocal > 0) {
					countLocal = jdbcTemplate.update(insertInBinTxn, binTx.getBinNumber(), binTx.getDenomination(),
							binTx.getMaxCapacity(), binTx.getReceiveBundles(), binTx.getBinType());
				}
			} else {
				Timestamp ts = new Timestamp(binTx.getUpdateTime().getTime());
				countLocal = jdbcTemplate.update(updateInBinTxn, binTx.getBinNumber(), binTx.getDenomination(),
						binTx.getMaxCapacity(), binTx.getReceiveBundles(), binTx.getBinType(), binTx.getId(), ts);
				if (countLocal == 0) {
					LOG.debug("Stale BinTx:" + binTx.getBinNumber()
							+ " has been updated by other user. Please try again.");
				}
			}
			if (countLocal == 0) {
				isAllSuccess = false;
				break;
			} else {
				isAllSuccess = true;
			}
		}
		return isAllSuccess;
	}

	@Override
	public boolean saveTxListAndProcessing(List<BinTransaction> txList, List<ProcessBean> processList) {

		Connection connection = null;
		boolean isAllSuccess = false;

		try {
			connection = dataSource.getConnection();
			connection.setAutoCommit(false);

			isAllSuccess = saveTxList(txList);
			if (isAllSuccess) {
				//isAllSuccess = saveProcessingRoom(processList);
			}
			if (isAllSuccess) {
				connection.commit();
			} else {
				connection.rollback();
			}
		} catch (Exception e) {
			LOG.error(e.getMessage());
			try {
				connection.rollback();
			} catch (SQLException e1) {

				LOG.error(e1.getMessage());
			}
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				LOG.error(e.getMessage());
			}
		}
		return isAllSuccess;
	}

	@Override
	public List<BinMaster> getPriorityBinListByDenomAndType(int denomination, String processType) {

		List<BinMaster> priorityBinList = new ArrayList<BinMaster>();
		BinMaster binMaster;
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		String Query = "";
		try {
			connection = dataSource.getConnection();
			statement = connection.createStatement();
			Query = "select binM.id, binM.bin_num,binM.first_priority,binM.loca_priority,capD.denomination,capD.max_bundle_capacity from tbl_bin_master binM,tbl_bin_capacity_denomination_wise capD where  capD.denomination='"
					+ denomination + "' AND  first_priority = '" + processType
					+ "' AND  binM.is_allocated='0' order by binM.loca_priority,binM.id asc;";
			resultSet = statement.executeQuery(Query);
			LOG.debug("Prority bin List By Deno And Type == " + Query);
			System.out.println("Bin Master Query==" + Query);
			while (resultSet.next()) {
				binMaster = Utility.mapperBinMaster(resultSet);
				priorityBinList.add(binMaster);
			}
		} catch (Exception e) {
			LOG.error(e.getMessage());
		}
		return priorityBinList;
	}

	@Override
	public List<BinTransaction> getBinTxnListByDenom(int denomination, double receiveBundle, String type) {
		Connection connection = null;
		Statement statement = null;
		ArrayList<BinTransaction> TxnList = new ArrayList<BinTransaction>();
		BinTransaction binData;
		ResultSet resultSet = null;
		String Query = "";
		try {
			connection = dataSource.getConnection();
			statement = connection.createStatement();
			Query = "select * from tbl_bin_transaction where denomination = '" + denomination
					+ "' AND max_capacity>receive_bundle AND bin_type='" + type + "';";
			resultSet = statement.executeQuery(Query);
			LOG.debug("Get Data From tbl_Transaction by Deno For Processing Room ==" + Query);
			while (resultSet.next()) {
				binData = Utility.mapperBinTransaction(resultSet);
				TxnList.add(binData);
			}
		} catch (Exception e) {
			LOG.error(e.getMessage());
		}
		return TxnList;
	}

	@Override
	public int processEntry(ProcessBean processBean) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String Query = "";
		int i = 0;
		try {
			connection = dataSource.getConnection();
			Query = "insert into tbl_process(machine_no,denomination,currency_type,bundle,total,insert_time,update_time,bin_number,filepath,insert_by,update_by) "
					+ "values(?,?,?,?,?,now(),now(),?,?,?,?)";
			preparedStatement = connection.prepareStatement(Query);
			preparedStatement.setInt(1, processBean.getMachineNo());
			preparedStatement.setInt(2, processBean.getDenomination());
			preparedStatement.setString(3, processBean.getType());
			preparedStatement.setDouble(4, processBean.getBundle());
			preparedStatement.setDouble(5, processBean.getTotal());
			preparedStatement.setString(6, processBean.getBin());
			preparedStatement.setString(7, processBean.getFilepath());
			preparedStatement.setString(8, processBean.getInsertBy());
			preparedStatement.setString(9, processBean.getUpdateBy());
			i = preparedStatement.executeUpdate();
		} catch (Exception e) {
			LOG.error(e.getMessage());
		}
		return i;
	}

	@Override
	public List<ProcessBean> getProcessData() {
		JdbcTemplate jdbcTemplate = jdbcTemplateDao.getJdbcTemplate();
		String Query = "select * from tbl_process where status=1 order by insert_time desc";
		List<ProcessBean> processList = jdbcTemplate.query(Query, new ProcessMapper());
		return processList;
	}

	@Override
	public List<BinTransaction> indentSummary() {
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		String Query = "";
		BinTransaction binTxn;
		List<BinTransaction> unprocessList = new ArrayList<BinTransaction>();
		try {
			connection = dataSource.getConnection();
			statement = connection.createStatement();
			Query = "select denomination,sum(receive_bundle) as bundle,insert_time  from tbl_bin_transaction where bin_type='Unprocess' Group By denomination order by insert_time asc;";
			resultSet = statement.executeQuery(Query);
			while (resultSet.next()) {
				binTxn = new BinTransaction();
				binTxn.setDenomination(resultSet.getInt("denomination"));
				binTxn.setReceiveBundles(resultSet.getInt("bundle"));
				binTxn.setInsertTime(resultSet.getDate("insert_time"));
				unprocessList.add(binTxn);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return unprocessList;
	}

	@Override
	public List<IndentRequestBean> getRequestDataForMachineAllocation() {
		JdbcTemplate jdbcTemplate = jdbcTemplateDao.getJdbcTemplate();
		String Query = "select * from tbl_indent where status=0";
		List<IndentRequestBean> indentList = jdbcTemplate.query(Query, new IndentRequestBeanMapper());
		return indentList;
	}

	@Override
	public List<MachineAllocationBean> getMachineAllocationRecordForProcessing() {
		JdbcTemplate jdbcTemplate = jdbcTemplateDao.getJdbcTemplate();
		String Query = "select * from tbl_machine_allocation;";
		List<MachineAllocationBean> machineList = jdbcTemplate.query(Query, new MachineAllocationMapper());
		return machineList;
	}

	@Override
	public boolean updateProcessedStatus(int id) {
		boolean isSuccessfull = false;
		int count = 0;
		String Query = "update tbl_process set status = '0' where id = ?;";
		JdbcTemplate jdbcTemplate = jdbcTemplateDao.getJdbcTemplate();
		count = jdbcTemplate.update(Query, id);
		if (count > 0) {
			isSuccessfull = true;
		} else {
			isSuccessfull = false;

		}
		return isSuccessfull;
	}

	@Override
	public boolean updateBinTxnStatus(String binType) {
		boolean isSuccessfull = false;
		int count = 0;
		String Query = "update tbl_bin_transaction set status = '1' where bin_type= ?;";
		JdbcTemplate jdbcTemplate = jdbcTemplateDao.getJdbcTemplate();
		count = jdbcTemplate.update(Query, binType);
		if (count > 0) {
			isSuccessfull = true;
		} else {
			isSuccessfull = false;

		}
		return isSuccessfull;
	}

	@Override
	public boolean UpdateProcessStatusAndBinTxnStatus(int id, String type) {
		boolean isAllSuccess = false;
		try {
			isAllSuccess = updateProcessedStatus(id);
			if (isAllSuccess) {
				isAllSuccess = updateBinTxnStatus(type);
			}

		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
		return isAllSuccess;
	}

}
