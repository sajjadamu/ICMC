/*******************************************************************************
 * /* Copyright (C) Indicsoft Technologies Pvt Ltd
 * * All Rights Reserved.
 *******************************************************************************/
package com.chest.currency.dao;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.ResultSet;
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

import com.chest.currency.dao.mapper.BinMasterMapper;
import com.chest.currency.dao.mapper.BinTransactionMapper;
import com.chest.currency.dao.mapper.DSBMapper;
import com.chest.currency.dao.mapper.DirvMapper;
import com.chest.currency.dao.mapper.FreshMapper;
import com.chest.currency.entity.model.BranchReceipt;
import com.chest.currency.entity.model.DiversionIRV;
import com.chest.currency.entity.model.Indent;
import com.chest.currency.qrgencode.QRCodeGen;
import com.chest.currency.viewBean.BinMaster;
import com.chest.currency.viewBean.BinTransaction;
import com.chest.currency.viewBean.DSBBean;
import com.chest.currency.viewBean.DirvBean;
import com.chest.currency.viewBean.FreshBean;
import com.chest.currency.viewBean.IndentRequestBean;
import com.chest.currency.viewBean.ShrinkBean;

@Repository
public class CashReceiptDaoImpl implements CashReceiptDao {

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

	/*@Override
	public boolean saveShrinkList(List<ShrinkBean> shrinkList) {
		boolean isSuccessfull = false;
		int count;
		String query = "insert into tbl_shrink(sol_id,branch,denominations,bin_num,bundle,total,filepath,status,insert_time,update_time,insert_by,update_by,ICMC_ID) "
				+ "values(?,?,?,?,?,?,?,'1',now(),now(),?,?,?)";
		JdbcTemplate jdbcTemplate = jdbcTemplateDao.getJdbcTemplate();
		for (ShrinkBean shrinkBean : shrinkList) {
			shrinkBean.setFilepath(getQrCode(shrinkBean));
			count = jdbcTemplate.update(query, shrinkBean.getSolId(), shrinkBean.getBranch(),
					shrinkBean.getDenomination(), shrinkBean.getBin(), shrinkBean.getBundle(), shrinkBean.getTotal(),
					shrinkBean.getFilepath(), shrinkBean.getInsertBy(), shrinkBean.getUpdateBy(),
					shrinkBean.getICMCId());
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
	public boolean saveDirvList(List<DirvBean> dirvList) {
		int count = 0;
		boolean isSuccessfull = false;
		String Query = "insert into tbl_diversion_irv(order_date,rbi_order_no,expiry_date,bank_name,approved_cc,location,denomination,bin_number,bundle,total,insert_time,update_time,file_path,category,insert_by,update_by,ICMC_ID) "
				+ "values(?,?,?,?,?,?,?,?,?,?,now(),now(),?,?,?,?,?)";
		JdbcTemplate jdbcTemplate = jdbcTemplateDao.getJdbcTemplate();
		for (DirvBean dirvBean : dirvList) {
			dirvBean.setFilepath(getDirvQrCode(dirvBean));
			count = jdbcTemplate.update(Query, dirvBean.getOrder_date(), dirvBean.getRbi_order_no(),
					dirvBean.getExpiry_date(), dirvBean.getBankName(), dirvBean.getApprovedCC(), dirvBean.getLocation(),
					dirvBean.getDenomination(), dirvBean.getBinNumber(), dirvBean.getBundle(), dirvBean.getTotal(),
					dirvBean.getFilepath(), dirvBean.getCategory(), dirvBean.getInsertBy(), dirvBean.getUpdateBy(),
					dirvBean.getIcmcId());
			if (count > 0) {
				isSuccessfull = true;
			} else {
				isSuccessfull = false;
				break;
			}
		}
		return isSuccessfull;
	}

	@Override
	public boolean saveRBIFresh(List<FreshBean> freshList) {
		int count = 0;
		boolean isSuccessfull = false;
		String Query = "insert into tbl_fresh(denomination,bundle,total,bin,insert_time,update_time,notes_or_coins,order_date,rbi_order_no,vehicle_number,potdar_name,escort_officer_name,filepath,insert_by,update_by,ICMC_ID) "
				+ "values(?,?,?,?,now(),now(),?,?,?,?,?,?,?,?,?,?)";
		JdbcTemplate jdbcTemplate = jdbcTemplateDao.getJdbcTemplate();
		for (FreshBean freshBean : freshList) {
			freshBean.setFilepath(getRBIQrCode(freshBean));
			count = jdbcTemplate.update(Query, freshBean.getDenomination(), freshBean.getBundle(), freshBean.getTotal(),
					freshBean.getBin(), freshBean.getNotesOrCoins(), freshBean.getOrder_date(),
					freshBean.getRbiOrderNo(), freshBean.getVehicleNumber(), freshBean.getPotdarName(),
					freshBean.getEscort_officer_name(), freshBean.getFilepath(), freshBean.getInsertBy(),
					freshBean.getUpdateBy(), freshBean.getIcmcId());
			if (count > 0) {
				isSuccessfull = true;
			} else {
				isSuccessfull = false;
				break;
			}
		}
		return isSuccessfull;
	}

	@Override
	public boolean saveDSBList(List<DSBBean> dsbList) {
		boolean isSuccessfull = false;
		int count = 0;
		String Query = "insert into tbl_dsb(denomination,bundle,total,bin_num,insert_time,name,processing_or_vault,filepath,update_time,insert_by,update_by,ICMC_ID) "
				+ "values(?,?,?,?,now(),?,?,?,now(),?,?,?)";
		JdbcTemplate jdbcTemplate = jdbcTemplateDao.getJdbcTemplate();
		for (DSBBean dsbBean : dsbList) {
			dsbBean.setFilePath(getDSBQrCode(dsbBean));
			count = jdbcTemplate.update(Query, dsbBean.getDenomination(), dsbBean.getBundle(), dsbBean.getTotal(),
					dsbBean.getBin(), dsbBean.getName(), dsbBean.getProcessinORVault(), dsbBean.getFilePath(),
					dsbBean.getInsertBy(), dsbBean.getUpdateBy(), dsbBean.getIcmcId());
			if (count > 0) {
				isSuccessfull = true;
			} else {
				isSuccessfull = false;
				break;
			}
		}
		return isSuccessfull;
	}

	@Override
	public List<BinMaster> getPriorityBinListByDenomAndType(int denomination, String processType, int icmcId) {

		JdbcTemplate jdbcTemplate = jdbcTemplateDao.getJdbcTemplate();
		String query = "select binM.icmc_id, binM.id, binM.bin_num,binM.first_priority,binM.loca_priority,binM.insert_by,binM.update_by,capD.denomination,capD.max_bundle_capacity from tbl_bin_master binM,tbl_bin_capacity_denomination_wise capD where  capD.denomination='"
				+ denomination + "' AND  first_priority = '" + processType + "' AND binM.icmc_id=" + icmcId
				+ " AND binM.is_allocated='0' order by binM.loca_priority,binM.id asc;";

		List<BinMaster> priorityBinList = jdbcTemplate.query(query, new BinMasterMapper());

		return priorityBinList;
	}

	@Override
	public List<BinTransaction> getBinTxnListByDenom(int denomination, double receiveBundle) {

		JdbcTemplate jdbcTemplate = jdbcTemplateDao.getJdbcTemplate();

		String query = "select * from tbl_bin_transaction where denomination = '" + denomination
				+ "' AND max_capacity>receive_bundle";

		List<BinTransaction> TxnList = jdbcTemplate.query(query, new BinTransactionMapper());

		LOG.debug("Get Data From tbl_Transaction by Demo ==" + query);

		return TxnList;
	}

	public boolean saveTxList(List<BinTransaction> txList) {
		String UpdateBinMasterQuery = "update tbl_bin_master set is_allocated=1 where bin_num=? and is_allocated=0;";
		String insertInBinTxn = "insert into tbl_bin_transaction (bin_num,denomination,max_capacity,insert_time,receive_bundle,bin_type,update_time,insert_by,update_by,ICMC_ID) values (?,?,?,now(),?,?,now(),?,?,?);";
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
							binTx.getMaxCapacity(), binTx.getReceiveBundles(), binTx.getBinType(), binTx.getInsertBy(),
							binTx.getUpdateBy(), binTx.getICMCId());
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

	private String getPath(String filepath) {
		String[] str = filepath.split("/html");
		String path = "http://localhost" + str[1]; // for desktop
		return path;
	}


	public String getQrCode(ShrinkBean shrinkBean) {
		String filepath = "";//qrCodeGen.generateQR(shrinkBean);
		String path = getPath(filepath);
		return path;
	}

	public String getDSBQrCode(DSBBean dsbBean) {
		String filepath = "";//qrCodeGen.generateDSBQR(dsbBean);
		String path = getPath(filepath);
		return path;
	}

	public String getDirvQrCode(DirvBean dirvBean) {
		String filepath = "";//qrCodeGen.generateDirvQR(dirvBean);
		String path = getPath(filepath);
		return path;
	}

	public String getRBIQrCode(FreshBean freshBean) {
		String filepath = "";//qrCodeGen.generateFreshFromRBIQR(freshBean);
		String path = getPath(filepath);
		return path;
	}

	/*@Override
	public boolean saveTxListAndShrink(List<BinTransaction> txList, List<ShrinkBean> shrinkBeanList) {
		boolean isAllSuccess = false;
		try {
			isAllSuccess = saveTxList(txList);
			if (isAllSuccess) {
				isAllSuccess = saveShrinkList(shrinkBeanList);
			}
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
		return isAllSuccess;
	}*/

	@Override
	public boolean saveTxListAndDSB(List<BinTransaction> txList, List<DSBBean> dSBBeanList) {

		boolean isAllSuccess = false;
		try {
			isAllSuccess = saveTxList(txList);
			if (isAllSuccess) {
				isAllSuccess = saveDSBList(dSBBeanList);
			}

		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
		return isAllSuccess;
	}

	@Override
	public boolean saveTxListAndDiversion(List<BinTransaction> txList, List<DirvBean> dirvBeanList) {
		boolean isAllSuccess = false;
		try {
			isAllSuccess = saveTxList(txList);
			if (isAllSuccess) {
				isAllSuccess = saveDirvList(dirvBeanList);
			}
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
		return isAllSuccess;
	}

	@Override
	public boolean saveTxListAndRBIFresh(List<BinTransaction> txList, List<FreshBean> freshBeanList) {
		boolean isAllSuccess = false;
		try {
			isAllSuccess = saveTxList(txList);
			if (isAllSuccess) {
				isAllSuccess = saveRBIFresh(freshBeanList);
			}

		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
		return isAllSuccess;
	}

	@Override
	public boolean saveDSBForProcessing(DSBBean bean) {
		String Query = "";
		int count = 0;
		boolean isSuccessfull = false;
		Query = "insert into tbl_dsb (name,denomination,bundle,total,bin_num,processing_or_vault,filepath,insert_time,update_time,insert_by,update_by,ICMC_ID) values (?,?,?,?,?,?,?,now(),now(),?,?,?);";
		bean.setFilePath(getDSBQrCode(bean));
		JdbcTemplate jdbcTemplate = jdbcTemplateDao.getJdbcTemplate();
		count = jdbcTemplate.update(Query, bean.getName(), bean.getDenomination(), bean.getBundle(), bean.getTotal(),
				bean.getBin(), bean.getProcessinORVault(), bean.getFilePath(), bean.getInsertBy(), bean.getUpdateBy(),
				bean.getIcmcId());

		if (count > 0) {
			isSuccessfull = true;
		} else {
			isSuccessfull = false;
		}

		return isSuccessfull;
	}

	@Override
	public boolean saveIndentFromDSB(IndentRequestBean indentBean) {
		String Query = "";
		int count = 0;
		boolean isSuccessfull = false;
		Query = "insert into tbl_indent (denomination,bundle,status,insert_time,update_time,insert_by,update_by) values (?,?,0,now(),now(),?,?);";
		JdbcTemplate jdbcTemplate = jdbcTemplateDao.getJdbcTemplate();
		count = jdbcTemplate.update(Query, indentBean.getDenomination(), indentBean.getBundle(),
				indentBean.getInsertBy(), indentBean.getUpdateBy());
		if (count > 0) {
			isSuccessfull = true;
		} else {
			isSuccessfull = false;
		}
		return isSuccessfull;
	}
	
	
	
	@Override
	public boolean saveTxListAndDSB(DSBBean dsbBean, IndentRequestBean indentBean) {
		boolean isAllSuccess = false;
		isAllSuccess = saveDSBForProcessing(dsbBean);
		if (isAllSuccess) {
			isAllSuccess = saveIndentFromDSB(indentBean);
		}
		return isAllSuccess;
	}

	@Override
	public List<ShrinkBean> getShrinkList(int icmcId) {
		Connection connection = null;
		Statement statement = null;
		String Query = "";
		ResultSet resultSet = null;
		List<ShrinkBean> shrinkList = new ArrayList<ShrinkBean>();
		ShrinkBean shrinkBean;
		try {
			connection = dataSource.getConnection();
			statement = connection.createStatement();
			Query = "select * from tbl_shrink  where ICMC_ID=" + icmcId + " AND status=1 order by id desc";
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
				shrinkBean.setICMCId(resultSet.getInt("ICMC_ID"));
				shrinkList.add(shrinkBean);
			}

		} catch (Exception e) {
			LOG.error(e.getMessage());
		}
		return shrinkList;
	}

	@Override
	public List<DSBBean> getDSBRecord(int icmcId) {
		JdbcTemplate jdbcTemplate = jdbcTemplateDao.getJdbcTemplate();
		String Query = "select * from tbl_dsb where ICMC_ID=" + icmcId + " order by id desc;";
		List<DSBBean> dsbList = jdbcTemplate.query(Query, new DSBMapper());
		return dsbList;
	}

	@Override
	public List<FreshBean> getFreshRecord(int icmcId) {
		JdbcTemplate jdbcTemplate = jdbcTemplateDao.getJdbcTemplate();
		String Query = "select * from tbl_fresh where ICMC_ID=" + icmcId + " order by id desc;";
		List<FreshBean> freshList = jdbcTemplate.query(Query, new FreshMapper());
		return freshList;
	}

	@Override
	public List<DirvBean> getDirvRecord(int icmcId) {
		JdbcTemplate jdbcTemplate = jdbcTemplateDao.getJdbcTemplate();
		String Query = "select * from tbl_diversion_irv where ICMC_ID=" + icmcId + " order by id desc;";
		List<DirvBean> dirvList = jdbcTemplate.query(Query, new DirvMapper());
		return dirvList;
	}

	
	
}
