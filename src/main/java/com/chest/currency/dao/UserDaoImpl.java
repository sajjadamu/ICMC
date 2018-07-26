/*******************************************************************************
 * /* Copyright (C) Indicsoft Technologies Pvt Ltd
 * * All Rights Reserved.
 *******************************************************************************/
package com.chest.currency.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.chest.currency.qrgencode.QRCodeGen;
import com.chest.currency.viewBean.BinMaster;
import com.chest.currency.viewBean.CashProcessing;
import com.chest.currency.viewBean.DSBBean;
import com.chest.currency.viewBean.DirvBean;
import com.chest.currency.viewBean.FreshBean;
import com.chest.currency.viewBean.IRVBean;
import com.chest.currency.viewBean.IndentRequestBean;
import com.chest.currency.viewBean.MachineAllocationBean;
import com.chest.currency.viewBean.ProcessBean;
import com.chest.currency.viewBean.ShrinkBean;
import com.chest.currency.viewBean.vendorBean;

@Repository
public class UserDaoImpl implements UserDao {

	private static final Logger LOG = 	LoggerFactory.getLogger(UserDaoImpl.class);
	
	@Autowired
	DataSource dataSource;
	
	@Autowired
	QRCodeGen qrCodeGen;
	
	public DataSource getDataSource() {
		return this.dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	

	public ArrayList<ShrinkBean> getShrinkList() {
		Connection connection = null;
		Statement statement = null;
		String Query = "";
		ResultSet resultSet = null;
		ArrayList<ShrinkBean> shrinkList = new ArrayList<ShrinkBean>();
		ShrinkBean shrinkBean;
		try {
			connection = dataSource.getConnection();
			statement = connection.createStatement();
			Query = "select * from tbl_shrink  where status=1 order by id desc";
			resultSet = statement.executeQuery(Query);
			while (resultSet.next()) {
				shrinkBean = new ShrinkBean();
				shrinkBean.setId(resultSet.getInt("id"));
				shrinkBean.setBranch(resultSet.getString("branch"));
				shrinkBean.setSolId(resultSet.getInt("sol_id"));
				shrinkBean.setDenomination(resultSet.getInt("denominations"));
				shrinkBean.setBundle(resultSet.getInt("bundle"));
				shrinkBean.setTotal(resultSet.getInt("total"));
				shrinkBean.setBin(resultSet.getString("bin_num"));
				shrinkBean.setInsertTime(resultSet.getDate("insert_time"));
				shrinkList.add(shrinkBean);
			}

		} catch (Exception e) {
			LOG.error(e.getMessage());
		}
		return shrinkList;
	}

	public String getCapacityByBinNum(String binNum) {
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		String Query = "";
		String capacity = "";
		try {
			connection = dataSource.getConnection();
			statement = connection.createStatement();
			Query = "select capacity from tbl_bin_master where bin_num = " + binNum + ";";
			resultSet = statement.executeQuery(Query);
			if (resultSet.next()) {
				capacity = resultSet.getString("capacity");
			}
		} catch (Exception e) {
			LOG.error(e.getMessage());
		}
		return capacity;
	}


	public String getAvailBin(String denomination) {
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		String Query = "";
		String binNumber = "";
		try {
			connection = dataSource.getConnection();
			statement = connection.createStatement();
			Query = "select bin_num from tbl_bin_master where denomination = " + denomination
					+ " and bin_type = 'Unprocessed';";
			resultSet = statement.executeQuery(Query);
			if (resultSet.next()) {
				binNumber = resultSet.getString("bin_num");
			}
		} catch (Exception e) {
			LOG.error(e.getMessage());
		}
		return binNumber;
	}

	public String getBranchName(String solID) {
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		String Query = "";
		String branchName = "";
		try {
			connection = dataSource.getConnection();
			statement = connection.createStatement();
			Query = "select branch_name from tbl_branch where sol_id = '" + solID + "';";
			resultSet = statement.executeQuery(Query);
			//// LOG.debug("branch Name QUery==="+Query);
			if (resultSet.next()) {
				branchName = resultSet.getString("branch_name");
			}
		} catch (Exception e) {
			LOG.error(e.getMessage());
		}
		return branchName;
	}


	public ArrayList<FreshBean> getFreshData() {
		ArrayList<FreshBean> freshList = new ArrayList<FreshBean>();
		FreshBean freshBean;
		Connection connection = null;
		Statement statement = null;
		String Query = "";
		ResultSet resultSet = null;
		try {
			connection = dataSource.getConnection();
			statement = connection.createStatement();
			Query = "select * from tbl_fresh";
			resultSet = statement.executeQuery(Query);
			while (resultSet.next()) {
				freshBean = new FreshBean();
				freshBean.setDenomination(resultSet.getInt("denomination"));
				freshBean.setBundle(resultSet.getInt("bundle"));
				freshBean.setTotal(resultSet.getInt("total"));
				freshBean.setBin(resultSet.getString("bin"));
				freshBean.setInsertTime(resultSet.getTimestamp("insert_time"));
				freshBean.setNotesOrCoins(resultSet.getString("notes_or_coins"));
				freshBean.setOrder_date(resultSet.getDate("order_date"));
				freshBean.setRbiOrderNo(resultSet.getString("rbi_order_no"));
				freshList.add(freshBean);
			}
		} catch (Exception e) {
			LOG.error(e.getMessage());
		}
		return freshList;
	}

	public int freshEntry(FreshBean freshBean) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String Query = "";
		int i = 0;
		try {
			connection = dataSource.getConnection();
			Query = "insert into tbl_fresh (denomination,bundle,total,bin,insert_date,coins,order_date,rbi_order_no,vehicle_number,potdar_name,escort_officer_name) values (?,?,?,?,now(),?,?,?,?,?,?);";
			preparedStatement = connection.prepareStatement(Query);
			preparedStatement.setInt(1, freshBean.getDenomination());
			preparedStatement.setDouble(2, freshBean.getBundle());
			preparedStatement.setInt(3, freshBean.getTotal());
			preparedStatement.setString(4, freshBean.getBin());
			preparedStatement.setString(5, freshBean.getCoins());
			preparedStatement.setDate(6,(Date) freshBean.getOrder_date());
			preparedStatement.setString(7, freshBean.getRbiOrderNo());
			preparedStatement.setString(8, freshBean.getVehicleNumber());
			preparedStatement.setString(9, freshBean.getPotdarName());
			preparedStatement.setString(10, freshBean.getEscort_officer_name());
			i = preparedStatement.executeUpdate();
		} catch (Exception e) {
			LOG.error(e.getMessage());
		}
		return i;
	}

	public ArrayList<String> getCustodianAndVehicle(String vendor) {
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		String Query = "";
		ArrayList<String> list = new ArrayList<String>();
		vendorBean vendorBean;
		String custodian_name = "";
		try {
			connection = dataSource.getConnection();
			statement = connection.createStatement();
			Query = "select CIT_custodian_name from tbl_custodian where vendor_name = '" + vendor + "';";
			resultSet = statement.executeQuery(Query);
			while (resultSet.next()) {
				vendorBean = new vendorBean();
				vendorBean.setCustodian(resultSet.getString("CIT_custodian_name"));
				custodian_name = resultSet.getString("CIT_custodian_name");
				list.add(custodian_name);
			}
		} catch (Exception e) {
			LOG.error(e.getMessage());
		}
		return list;
	}

	public ArrayList<String> getVehicleNumber(String vendor) {
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		String Query = "";
		ArrayList<String> list = new ArrayList<String>();
		String vehicle_no = "";
		try {
			connection = dataSource.getConnection();
			statement = connection.createStatement();
			Query = "select vehicle_no from tbl_vehicle where vendor_name = '" + vendor + "';";
			resultSet = statement.executeQuery(Query);
			while (resultSet.next()) {
				vehicle_no = resultSet.getString("vehicle_no");
				list.add(vehicle_no);
			}
		} catch (Exception e) {
			LOG.error(e.getMessage());
		}
		return list;
	}

	public void saveORVEntry(String[] sql) {
		Connection connection = null;
		try {
			connection = dataSource.getConnection();
			Statement statement = connection.createStatement();

			for (int i = 0; i < sql.length; i++) {
				statement.addBatch(sql[i]);
			}
			statement.executeBatch();
		} catch (Exception e) {
			LOG.error(e.getMessage());
		}

	}

	public ArrayList<String> getBinByDenomination(String denomination) {
		ArrayList<String> binList = new ArrayList<String>();
		// BinMaster binData;
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		String Query = "";
		String binNum = "";
		try {
			connection = dataSource.getConnection();
			statement = connection.createStatement();
			Query = "select bin_num from tbl_bin_master where denomination = '" + denomination + "';";
			// LOG.debug("QUERy==" + Query);
			resultSet = statement.executeQuery(Query);
			while (resultSet.next()) {
				// binData = new BinMaster();
				// binData.setBinNumber(resultSet.getString("bin_num"));
				binNum = resultSet.getString("bin_num");
				binList.add(binNum);
			}
		} catch (Exception e) {
			LOG.error(e.getMessage());
		}
		return binList;
	}

	public void saveIRVEntry(String[] sql) {
		Connection connection = null;
		try {
			connection = dataSource.getConnection();
			Statement statement = connection.createStatement();

			for (int i = 0; i < sql.length; i++) {
				statement.addBatch(sql[i]);
			}
			statement.executeBatch();
		} catch (Exception e) {
			LOG.error(e.getMessage());
		}

	}

	public ArrayList<IRVBean> getIRVData() {
		ArrayList<IRVBean> IRVList = new ArrayList<IRVBean>();
		IRVBean irvData;
		Connection connection = null;
		Statement statement = null;
		String Query = "";
		ResultSet resultSet = null;
		try {
			connection = dataSource.getConnection();
			statement = connection.createStatement();
			Query = "select * from tbl_irv";
			resultSet = statement.executeQuery(Query);
			while (resultSet.next()) {
				irvData = new IRVBean();
				irvData.setSR(resultSet.getString("sr"));
				irvData.setSolId(resultSet.getString("sol_id"));
				irvData.setBranch(resultSet.getString("branch"));
				irvData.setVendor(resultSet.getString("vendor"));
				irvData.setCustodian(resultSet.getString("custodian"));
				irvData.setVehicle(resultSet.getString("vehicle"));
				irvData.setDenomination(resultSet.getString("denomination"));
				irvData.setBundle(resultSet.getString("bundle"));
				irvData.setTotal(resultSet.getString("total"));
				irvData.setSackLock(resultSet.getString("sack_no"));
				irvData.setAccountNumber(resultSet.getString("account_no"));
				IRVList.add(irvData);
			}
		} catch (Exception e) {
			LOG.error(e.getMessage());
		}
		return IRVList;
	}

	public ArrayList<IRVBean> getIRVVoucher(String sDate) {

		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		String Query = "";
		ArrayList<IRVBean> irvList = new ArrayList<IRVBean>();
		IRVBean irvData;

		try {
			connection = dataSource.getConnection();
			statement = connection.createStatement();
			Query = "select sr,sol_id,branch,total from tbl_irv where insert_date = '" + sDate + "';";
			resultSet = statement.executeQuery(Query);
			while (resultSet.next()) {
				irvData = new IRVBean();
				irvData.setBranch(resultSet.getString("branch"));
				irvData.setSolId(resultSet.getString("sol_id"));
				irvData.setSR(resultSet.getString("sr"));
				irvData.setTotal(resultSet.getString("total"));
				irvList.add(irvData);
			}
		} catch (Exception e) {
			LOG.error(e.getMessage());
		}
		return irvList;
	}


	public ArrayList<BinMaster> getBinDetailsByDenoAndType(String denomination, String type) {
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		String Query = "";
		ArrayList<BinMaster> binList = new ArrayList<BinMaster>();
		BinMaster binData;
		try {
			connection = dataSource.getConnection();
			statement = connection.createStatement();
			Query = "select tbl_bin_master.bin_num,tbl_bin_master.bin_type,tbl_bin_master.capacity,tbl_bin_type.color_code,tbl_bin_type.html_color_code from tbl_bin_master,tbl_bin_type where tbl_bin_type.bin_type=tbl_bin_master.bin_type AND tbl_bin_master.denomination='"
					+ denomination + "' AND tbl_bin_master.bin_type='" + type + "'; ";
			resultSet = statement.executeQuery(Query);
			while (resultSet.next()) {
				binData = new BinMaster();
				binData.setBinNumber(resultSet.getString("bin_num"));
				//binData.setBinType(resultSet.getString("bin_type"));
				//binData.setCapacity(resultSet.getString("capacity"));
				//binData.setColorCode(resultSet.getString("color_code"));
				//binData.setHtmlColorCode(resultSet.getString("html_color_code"));
				binList.add(binData);
			}
		} catch (Exception e) {
			LOG.error(e.getMessage());
		}
		return binList;
	}

	public int cashProcessing(CashProcessing cash) {
		int i = 0;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String Query = "";
		try {
			connection = dataSource.getConnection();
			Query = "insert into tbl_discrepancy_processing (machine_no,date,sol_id,branch,denomination,account_teller_cam,account_teller_cam_value,discrepancy,note_number,SR,insert_time,filepath) values (?,?,?,?,?,?,?,?,?,?,now(),?);";
			preparedStatement = connection.prepareStatement(Query);
			preparedStatement.setString(1, cash.getMachineNo());
			preparedStatement.setString(2, cash.getDate());
			preparedStatement.setString(3, cash.getSolId());
			preparedStatement.setString(4, cash.getBranch());
			preparedStatement.setString(5, cash.getDenomination());
			preparedStatement.setString(6, cash.getAccount_teller_cam());
			preparedStatement.setString(7, cash.getAccount_teller_cam_value());
			preparedStatement.setString(8, cash.getDescripancy());
			preparedStatement.setString(9, cash.getNoteNumber());
			preparedStatement.setString(10, cash.getSR());
			preparedStatement.setString(11, cash.getFilepath());
			i = preparedStatement.executeUpdate();

		} catch (Exception e) {
			LOG.error(e.getMessage());
		}

		return i;
	}

	public ArrayList<CashProcessing> getCashProcessingDetails() {
		
		ArrayList<CashProcessing> cashList = new ArrayList<CashProcessing>();
		CashProcessing cash;
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		String Query = "";
		try {
			connection = dataSource.getConnection();
			statement = connection.createStatement();
			Query = "select * from tbl_discrepancy_processing;";
			resultSet = statement.executeQuery(Query);
			while (resultSet.next()) {
				cash = new CashProcessing();
				cash.setMachineNo(resultSet.getString("machine_no"));
				cash.setDate(resultSet.getString("date"));
				cash.setDescripancy(resultSet.getString("discrepancy"));
				cash.setSolId(resultSet.getString("sol_id"));
				cash.setBranch(resultSet.getString("branch"));
				cash.setDenomination(resultSet.getString("denomination"));
				cash.setAccount_teller_cam(resultSet.getString("account_teller_cam"));
				cash.setAccount_teller_cam_value(resultSet.getString("account_teller_cam_value"));
				cash.setNoteNumber(resultSet.getString("note_number"));
				cash.setSR(resultSet.getString("SR"));

				cashList.add(cash);
			}

		} catch (Exception e) {
			LOG.error(e.getMessage());
		}
		return cashList;
	}


	public ArrayList<DirvBean> getDirvData() {
		
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		String Query = "";
		DirvBean dirv;
		ArrayList<DirvBean> irvList = new ArrayList<DirvBean>();
		try {
			connection = dataSource.getConnection();
			statement = connection.createStatement();
			Query = "select * from tbl_diversion_irv;";
			resultSet = statement.executeQuery(Query);
			while (resultSet.next()) {
				dirv = new DirvBean();
				dirv.setOrder_date(resultSet.getString("order_date"));
				dirv.setBankName(resultSet.getString("bank_name"));
				dirv.setLocation(resultSet.getString("location"));
				dirv.setDenomination(resultSet.getInt("denomination"));
				dirv.setBundle(resultSet.getInt("bundle"));
				dirv.setTotal(resultSet.getInt("total"));
				dirv.setBinNumber(resultSet.getString("bin_number"));
				irvList.add(dirv);
			}
		} catch (Exception e) {
			LOG.error(e.getMessage());
		}
		return irvList;
	}

	
	public int insertCurrentNoOfBundlesAndAvailCapacity(BinMaster binMaster) {
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String Query = "";
		int i = 0;
		try {
			connection = dataSource.getConnection();
			Query = "update tbl_bin_master set current_num_of_bundles=?,available_capacity=? where bin_num=?";
			preparedStatement = connection.prepareStatement(Query);
			//preparedStatement.setString(1, binMaster.getCurrentNoOfBundles());
			//preparedStatement.setString(2, binMaster.getAvailableCapacity());
			preparedStatement.setString(3, binMaster.getBinNumber());
			LOG.debug("Bin Updation===" + preparedStatement);
			i = preparedStatement.executeUpdate();
		} catch (Exception e) {
			LOG.error(e.getMessage());
		}
		return i;
	}

	public String getAvailableCapacityForShrink(String bin) {
		Connection connection = null;
		Statement statement = null;
		String Query = "";
		ResultSet resultSet = null;
		String capacity = "";
		try {
			connection = dataSource.getConnection();
			statement = connection.createStatement();
			Query = "select capacity from tbl_bin_master where bin_num = '" + bin + "';";
			resultSet = statement.executeQuery(Query);
			if (resultSet.next()) {
				capacity = resultSet.getString("capacity");
			}
		} catch (Exception e) {
			LOG.error(e.getMessage());
		}
		return capacity;
	}

	public ArrayList<String> getBranch(String region) {
		
		ArrayList<String> list = new ArrayList<String>();
		Connection connection = null;
		Statement statement = null;
		String Query = "";
		ResultSet resultSet = null;
		try {
			connection = dataSource.getConnection();
			statement = connection.createStatement();
			Query = "select branch from tbl_zone_master where region = '" + region + "';";
			resultSet = statement.executeQuery(Query);
			while (resultSet.next()) {
				list.add(resultSet.getString("branch"));
			}
		} catch (Exception e) {
			LOG.error(e.getMessage());
		}
		return list;
	}

	public int updateBinForWithdrawl(BinMaster bean) {
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String Query = "";
		int i = 0;
		try {
			connection = dataSource.getConnection();
			Query = "update tbl_bin_master set available_capacity=? where id = ?";
			preparedStatement = connection.prepareStatement(Query);
			//preparedStatement.setString(1, bean.getAvailableCapacity());
			preparedStatement.setInt(2, bean.getId());
			i = preparedStatement.executeUpdate();
		} catch (Exception e) {
			LOG.error(e.getMessage());
		}
		return i;
	}

	public int updateBinForDeposit(BinMaster bean) {
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String Query = "";
		int i = 0;
		try {
			connection = dataSource.getConnection();
			Query = "update tbl_bin_master set available_capacity=? where id = ?";
			preparedStatement = connection.prepareStatement(Query);
			//preparedStatement.setString(1, bean.getAvailableCapacity());
			preparedStatement.setInt(2, bean.getId());
			i = preparedStatement.executeUpdate();
		} catch (Exception e) {
			LOG.error(e.getMessage());
		}
		return i;
	}

	public int insertUnprocessedForCustodian(ShrinkBean bean) {
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String Query = "";
		int i = 0;
		try {
			connection = dataSource.getConnection();
			Query = "insert into tbl_unprocessed_custodian (sol_id,branch,denominations,bundle,total,bin_num,insert_time) values (?,?,?,?,?,?,?)";
			preparedStatement = connection.prepareStatement(Query);
			preparedStatement.setInt(1, bean.getSolId());
			preparedStatement.setString(2, bean.getBranch());
			preparedStatement.setInt(3, bean.getDenomination());
			//preparedStatement.setInt(4, bean.getBundle());
			preparedStatement.setInt(5, bean.getTotal());
			preparedStatement.setString(6, bean.getBin());
			preparedStatement.setDate(7, (Date) bean.getInsertTime());
			i = preparedStatement.executeUpdate();
		} catch (Exception e) {
			LOG.error(e.getMessage());
		}
		return i;
	}

	public int updateBinCapacityStatus(BinMaster bean) {
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String Query = "";
		int i = 0;
		try {
			connection = dataSource.getConnection();
			Query = "update tbl_bin_master set current_number_of_bundles=?,available_capacity where bin_num=?";
			preparedStatement = connection.prepareStatement(Query);
			//preparedStatement.setString(1, bean.getCurrentNoOfBundles());
			//preparedStatement.setString(2, bean.getAvailableCapacity());
			preparedStatement.setString(3, bean.getBinNumber());
			i = preparedStatement.executeUpdate();
		} catch (Exception e) {
			LOG.error(e.getMessage());
		}
		return i;
	}

	public String getavailableCapacity(String bin_num) {
		
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		String Query = "";
		String available_capacity = "";
		try {
			connection = dataSource.getConnection();
			statement = connection.createStatement();
			Query = "select available_capacity from tbl_bin_master where bin_num = '" + bin_num + "';";
			resultSet = statement.executeQuery(Query);
			if (resultSet.next()) {
				available_capacity = resultSet.getString("available_capacity");
				LOG.debug(available_capacity);
			}
		} catch (Exception e) {
			LOG.error(e.getMessage());
		}
		return available_capacity;
	}

	public int updateBinMasterForAvailableCapacity(ShrinkBean bean) {
		Connection con = null;

		PreparedStatement ps = null;
		String Query = "";
		int i = 0;
		try {
			con = dataSource.getConnection();
			Query = "update tbl_bin_master set available_capacity = available_capacity-" + bean.getBundle()
					+ " where bin_num = '" + bean.getBin() + "'";
			ps = con.prepareStatement(Query);
			i = ps.executeUpdate();
			// LOG.debug("UPDATE CAPACITY === >" + ps);
		} catch (Exception e) {
			LOG.error(e.getMessage());
		}
		return i;
	}

	public String getBinForIndent(String denomination, String bundle) {
		
		Connection connection = null;
		Statement statement = null;
		String Query = "";
		ResultSet resultSet = null;
		String bin_num = "";
		try {
			connection = dataSource.getConnection();
			statement = connection.createStatement();
			Query = "select bin_num from tbl_unprocessed_custodian where denominations = '" + denomination
					+ "' and bundle >= " + bundle + ";";
			LOG.debug("Query===" + Query);
			resultSet = statement.executeQuery(Query);

			if (resultSet.next()) {
				bin_num = resultSet.getString("bin_num");
			}
		} catch (Exception e) {
			LOG.error(e.getMessage());
		}
		return bin_num;
	}

	public int insertUpdatedIndent(IndentRequestBean bean) {
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String Query = "";
		int i = 0;
		try {
			connection = dataSource.getConnection();
			Query = "insert into tbl_indent_bin (denomination,bundle,bin) values (?,?,?);";
			preparedStatement = connection.prepareStatement(Query);
			preparedStatement.setInt(1, bean.getDenomination());
			preparedStatement.setDouble(2, bean.getBundle());
			preparedStatement.setString(3, bean.getBin());
			i = preparedStatement.executeUpdate();
		} catch (Exception e) {
			LOG.error(e.getMessage());
		}
		return i;
	}


	public int updateIndentBundle(MachineAllocationBean bean) {
		
		int i = 0;
		PreparedStatement preparedStatement = null;
		Connection connection = null;
		String Query = "";
		try {
			connection = dataSource.getConnection();
			Query = "update tbl_indent set bundle = bundle - " + bean.getIssued_bundle() + " where id = " + bean.getId()
					+ ";";
			preparedStatement = connection.prepareStatement(Query);
			LOG.debug("Update Bundle==" + preparedStatement);
			i = preparedStatement.executeUpdate();
		} catch (Exception e) {
			LOG.error(e.getMessage());
		}
		return i;
	}

	public int insertProcessedForCustodian(ProcessBean bean) {
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String Query = "";
		int i = 0;
		try {
			connection = dataSource.getConnection();
			Query = "insert into tbl_processed_custodian (machine,type,denomination,bundle,bin,total) values (?,?,?,?,?,?);";
			preparedStatement = connection.prepareStatement(Query);
			preparedStatement.setInt(1, bean.getMachineNo());
			preparedStatement.setString(2, bean.getType());
			preparedStatement.setInt(3, bean.getDenomination());
			preparedStatement.setDouble(4, bean.getBundle());
			preparedStatement.setString(5, bean.getBin());
			preparedStatement.setDouble(6, bean.getTotal());
			// preparedStatement.setString(7, bean.getInsertDate());
			i = preparedStatement.executeUpdate();
		} catch (Exception e) {
			LOG.error(e.getMessage());
		}
		return i;
	}

	public int updatebinForProcess(ProcessBean bean) {
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String Query = "";
		int i = 0;
		try {
			connection = dataSource.getConnection();
			Query = "update tbl_bin_master set current_num_of_bundles=?,available_capacity=capacity-" + bean.getBundle()
					+ " where bin_num = " + bean.getBin() + ";";
			preparedStatement = connection.prepareStatement(Query);
			preparedStatement.setDouble(1, bean.getBundle());
			i = preparedStatement.executeUpdate();
		} catch (Exception e) {
			LOG.error(e.getMessage());
		}
		return i;
	}

	public String selectBinForORVBranchFromProcessCustodian(String denomination, String bundle) {
		
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		String Query = "";
		String bin = "";
		try {
			connection = dataSource.getConnection();
			statement = connection.createStatement();
			Query = "select bin from tbl_processed_custodian where (type='ATM' ^ 'Issuable') AND denomination='"
					+ denomination + "' and bundle >= " + bundle + ";";
			LOG.debug(Query);
			resultSet = statement.executeQuery(Query);
			if (resultSet.next()) {
				bin = resultSet.getString("bin");
			}
		} catch (Exception e) {
			LOG.error(e.getMessage());
		}
		return bin;
	}


	public int updateShrinkStatus(String id) {
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String Query = "";
		int i = 0;
		try {
			connection = dataSource.getConnection();
			Query = "update tbl_shrink set status = '0' where id = '" + id + "';";
			preparedStatement = connection.prepareStatement(Query);
			i = preparedStatement.executeUpdate();
			LOG.debug("prepa==" + preparedStatement);
		} catch (Exception e) {
			LOG.error(e.getMessage());
		}
		return i;
	}

	public int updateIndentStatus(String id) {
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String Query = "";
		int i = 0;
		try {
			connection = dataSource.getConnection();
			Query = "update tbl_indent set status = '0' where id = '" + id + "';";
			preparedStatement = connection.prepareStatement(Query);
			i = preparedStatement.executeUpdate();
			LOG.debug("update Indent Status==" + preparedStatement);
		} catch (Exception e) {
			LOG.error(e.getMessage());
		}
		return i;
	}

	public int updateProcessedStatus(String id) {
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String Query = "";
		int i = 0;
		try {
			connection = dataSource.getConnection();
			Query = "update tbl_process set status = '0' where id = '" + id + "';";
			preparedStatement = connection.prepareStatement(Query);
			i = preparedStatement.executeUpdate();
			LOG.debug("prepa==" + preparedStatement);
		} catch (Exception e) {
			LOG.error(e.getMessage());
		}
		return i;
	}

	public int updateORVStatus(String id) {
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String Query = "";
		int i = 0;
		try {
			connection = dataSource.getConnection();
			Query = "update tbl_orv set status = '0' where id = '" + id + "';";
			preparedStatement = connection.prepareStatement(Query);
			i = preparedStatement.executeUpdate();
			LOG.debug("prepa==" + preparedStatement);
		} catch (Exception e) {
			LOG.error(e.getMessage());
		}
		return i;
	}


	public int checkBinIsAvailableInTransaction(int denomination) {
		
		Connection connection = null;
		Statement statement = null;
		int countDenom = 0;
		String Query = "";
		ResultSet resultSet = null;
		try {
			connection = dataSource.getConnection();
			statement = connection.createStatement();
			Query = "select count(*) as countDenom from tbl_bin_transaction where denomination = '" + denomination + "';";
			resultSet = statement.executeQuery(Query);
			LOG.debug("Bin Transaction Count=="+Query);
			if (resultSet.next()) {
				countDenom = resultSet.getInt("countDenom");
			}
		} catch (Exception e) {
			LOG.error(e.getMessage());
		}
		return countDenom;
	}

	public String getPriorityBinFromtbl_bin_transaction(String denomination) {
		
		Connection connection = null;
		Statement statement = null;
		String bin_num = "";
		String Query = "";
		ResultSet resultSet = null;
		try {
			connection = dataSource.getConnection();
			statement = connection.createStatement();
			Query = "select bin_num from tbl_bin_transaction where denomination = '" + denomination + "';";
			resultSet = statement.executeQuery(Query);
			if (resultSet.next()) {
				bin_num = resultSet.getString("bin_num");
			}
		} catch (Exception e) {
			LOG.error(e.getMessage());
		}
		return bin_num;
	}

	public int updateIsAllocated(String bin) {
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String Query = "";
		int i = 0;
		try {
			connection = dataSource.getConnection();
			Query = "update tbl_bin_master set is_allocated='1' where bin_num = '" + bin + "';";
			preparedStatement = connection.prepareStatement(Query);
			i = preparedStatement.executeUpdate();
		} catch (Exception e) {
			LOG.error(e.getMessage());
		}
		return i;
	}


	public int updateCurrenctBundle(String bin) {
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		int i = 0;
		try {
			connection = dataSource.getConnection();
			String query1 = "select sum(receive_bundle) from tbl_bin_transaction where bin_num = '" + bin + "';";
			LOG.debug("query1===" + query1);
			preparedStatement = connection.prepareStatement(query1);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				LOG.debug("resultset===" + resultSet.getString(1));
				String Query = "update tbl_bin_transaction set current_bundles = " + resultSet.getString(1)
						+ " where bin_num = '" + bin + "';";
				preparedStatement = connection.prepareStatement(Query);
				i = preparedStatement.executeUpdate();
				LOG.debug("sum QUery===" + preparedStatement);
			}
		} catch (Exception e) {
			LOG.error(e.getMessage());
		}

		return i;
	}

	public String getMaxCapacity(String denomination) {
		
		Connection connection = null;
		Statement statement = null;
		String Query = "";
		ResultSet resultSet = null;
		String capacity = "";
		try {
			connection = dataSource.getConnection();
			statement = connection.createStatement();
			Query = "select max_bundle_capacity from tbl_bin_capacity_denomination_wise where denomination = '"
					+ denomination + "';";
			resultSet = statement.executeQuery(Query);
			if (resultSet.next()) {
				capacity = resultSet.getString("max_bundle_capacity");
			}
		} catch (Exception e) {
			LOG.error(e.getMessage());
		}
		return capacity;
	}

	public ArrayList<String> getBinForIndentRequestBinUpdation(String denomination) {
		
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		ArrayList<String> binList = new ArrayList<String>();
		String Query = "";
		try {
			connection = dataSource.getConnection();
			statement = connection.createStatement();
			Query = "select distinct(bin_num) from tbl_bin_transaction where denomination = '" + denomination
					+ "' and bin_type='Unprocess' order by insert_time asc;";
			resultSet = statement.executeQuery(Query);
			while (resultSet.next()) {
				binList.add(resultSet.getString("bin_num"));
			}
		} catch (Exception e) {
			LOG.error(e.getMessage());
		}
		return binList;
	}

	public int ifDsbIsProcessing(DSBBean bean) {
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String Query = "";
		int i = 0;
		try {
			connection = dataSource.getConnection();
			Query = "insert into tbl_indent (denomination,bundle,status,insert_time) values (?,?,0,now());";
			preparedStatement = connection.prepareStatement(Query);
			preparedStatement.setInt(1, bean.getDenomination());
			preparedStatement.setDouble(2, bean.getBundle());
			LOG.debug("DSB Processing Query==" + preparedStatement);
			i = preparedStatement.executeUpdate();
		} catch (Exception e) {
			LOG.error(e.getMessage());
		}
		return i;
	}

		public void saveORVpayment(String[] sql) {
		Connection connection = null;
		try {
			connection = dataSource.getConnection();
			Statement statement = connection.createStatement();

			for (int i = 0; i < sql.length; i++) {
				statement.addBatch(sql[i]);
			}
			statement.executeBatch();

		} catch (Exception e) {
			LOG.error(e.getMessage());
		}
	}

}