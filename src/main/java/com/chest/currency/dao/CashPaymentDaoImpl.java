/*******************************************************************************
 * /* Copyright (C) Indicsoft Technologies Pvt Ltd
 * * All Rights Reserved.
 *******************************************************************************/
package com.chest.currency.dao;

import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.chest.currency.dao.mapper.DORVAllocationMapper;
import com.chest.currency.dao.mapper.DORVMapper;
import com.chest.currency.dao.mapper.ORVAllocationMapper;
import com.chest.currency.dao.mapper.ORVMapper;
import com.chest.currency.dao.mapper.SASAllocationMapper;
import com.chest.currency.dao.mapper.SASMapper;
import com.chest.currency.dao.mapper.SoiledAllocationMapper;
import com.chest.currency.dao.mapper.SoiledMapper;
import com.chest.currency.dao.statement.DiversionOrvStatementCreator;
import com.chest.currency.dao.statement.ORVStatementCreator;
import com.chest.currency.dao.statement.SoiledStatementCreator;
import com.chest.currency.qrgencode.QRCodeGen;
import com.chest.currency.viewBean.DorvAllocation;
import com.chest.currency.viewBean.DorvBean;
import com.chest.currency.viewBean.ORVAllocation;
import com.chest.currency.viewBean.ORVBean;
import com.chest.currency.viewBean.SASAllocation;
import com.chest.currency.viewBean.SASBean;
import com.chest.currency.viewBean.SoiledRemittance;
import com.chest.currency.viewBean.SoiledRemittanceAllocation;

@Repository
public class CashPaymentDaoImpl implements CashPaymentDao {

	private static final Logger LOG = LoggerFactory.getLogger(CashPaymentDaoImpl.class);

	DataSource dataSource;

	@Autowired
	QRCodeGen qrCodeGen;

	@Autowired
	private JdbcTemplateDao jdbcTemplateDao;

	public DataSource getDataSource() {
		return this.dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public int bulkIndentRequest(List<SASBean> bulkIndentList) {
		int count = 0;
		String Query = "insert into tbl_sas(sr_no,create_date,notes,branch_ec_name,Date_of_Requirements,SR_originating_Branch_Name,total_Indented_Value,Total_Value_of_coins_Rs_1,Total_Value_of_coins_Rs_2,Total_Value_of_coins_Rs_5,Total_Value_of_coins_Rs_10,Total_Value_of_Notes_Rs_1_F,Total_Value_of_Notes_Rs_2_F,Total_Value_of_Notes_Rs_5_F,Total_Value_of_Notes_Rs_10_F,Total_Value_of_Notes_Rs_20_F,Total_Value_of_Notes_Rs_50_F,Total_Value_of_Notes_Rs_100_F,Total_Value_of_Notes_Rs_500_F,Total_Value_of_Notes_Rs_1000_F,Total_Value_of_Notes_Rs_1_I,Total_Value_of_Notes_Rs_2_I,Total_Value_of_Notes_Rs_5_I,Total_Value_of_Notes_Rs_10_I,Total_Value_of_Notes_Rs_20_I,Total_Value_of_Notes_Rs_50_I,Total_Value_of_Notes_Rs_100_I,Total_Value_of_Notes_Rs_500_I,Total_Value_of_Notes_Rs_1000_I,Action_Taken,Date_of_Dispatch,insert_time,update_time,insert_by,update_by) values (?,?,?,?,?,?,?,?,?,?,?,0,0,0,0,0,0,0,0,0,?,?,?,?,?,?,?,?,?,?,?,now(),now(),?,?);";
		JdbcTemplate jdbcTemplate = jdbcTemplateDao.getJdbcTemplate();
		for (SASBean sas : bulkIndentList) {
			count = jdbcTemplate.update(Query, sas.getSRno(), sas.getCreateDate(), sas.getNotes(),
					sas.getBranchEcName(), sas.getDateOfRequirements(), sas.getSrOriginatingBranchName(),
					sas.getTotalIndentedValue(), sas.getTotalValueOfCoinsRs1(), sas.getTotalValueOfCoinsRs2(),
					sas.getTotalValueOfCoinsRs5(), sas.getTotalValueOfCoinsRs10(), sas.getTotalValueOfNotesRs1_I(),
					sas.getTotalValueOfNotesRs2_I(), sas.getTotalValueOfNotesRs5_I(), sas.getTotalValueOfNotesRs10_I(),
					sas.getTotalValueOfNotesRs20_I(), sas.getTotalValueOfNotesRs50_I(),
					sas.getTotalValueOfNotesRs100_I(), sas.getTotalValueOfNotesRs500_I(),
					sas.getTotalValueOfNotesRs1000_I(), sas.getActiontaken(), sas.getDateOfDispatch(),
					sas.getInsertBy(), sas.getUpdateBy());
		}
		return count;
	}

	@Override
	public List<SASBean> getSASRecord() {
		JdbcTemplate jdbcTemplate = jdbcTemplateDao.getJdbcTemplate();
		String Query = "select * from tbl_sas where status=1;";
		List<SASBean> sasList = jdbcTemplate.query(Query, new SASMapper());
		return sasList;
	}

	@Override
	public int sasUpdate(SASBean sasBean) {
		int count = 0;
		String Query = "update tbl_sas set Total_Value_of_Notes_Rs_1_F=?,Total_Value_of_Notes_Rs_10_F=?,Total_Value_of_Notes_Rs_100_F=?,Total_Value_of_Notes_Rs_1000_F=?,Total_Value_of_Notes_Rs_2_F=?,Total_Value_of_Notes_Rs_20_F=?,Total_Value_of_Notes_Rs_5_F=?,Total_Value_of_Notes_Rs_50_F=?,Total_Value_of_Notes_Rs_500_F=?,Total_Value_of_Notes_Rs_1_I=?,Total_Value_of_Notes_Rs_10_I=?,Total_Value_of_Notes_Rs_1000_I=?,Total_Value_of_Notes_Rs_1000_I=?,Total_Value_of_Notes_Rs_2_I=?,Total_Value_of_Notes_Rs_20_I=?,Total_Value_of_Notes_Rs_5_I=?,Total_Value_of_Notes_Rs_50_I=?,Total_Value_of_Notes_Rs_500_I=?,update_time=now() where id = ?;";
		JdbcTemplate jdbcTemplate = jdbcTemplateDao.getJdbcTemplate();
		count = jdbcTemplate.update(Query, sasBean.getTotalValueOfNotesRs1_F(), sasBean.getTotalValueOfNotesRs10_F(),
				sasBean.getTotalValueOfNotesRs100_F(), sasBean.getTotalValueOfNotesRs1000_F(),
				sasBean.getTotalValueOfNotesRs2_F(), sasBean.getTotalValueOfNotesRs20_F(),
				sasBean.getTotalValueOfNotesRs5_F(), sasBean.getTotalValueOfNotesRs50_F(),
				sasBean.getTotalValueOfNotesRs500_F(), sasBean.getTotalValueOfNotesRs1_I(),
				sasBean.getTotalValueOfNotesRs10_I(), sasBean.getTotalValueOfNotesRs100_I(),
				sasBean.getTotalValueOfNotesRs1000_I(), sasBean.getTotalValueOfNotesRs2_I(),
				sasBean.getTotalValueOfNotesRs20_I(), sasBean.getTotalValueOfNotesRs5_I(),
				sasBean.getTotalValueOfNotesRs50_I(), sasBean.getTotalValueOfNotesRs500_I(), sasBean.getId());
		return count;
	}

	@Override
	public int insertInSASAllocation(SASAllocation sasAllocation) {
		LOG.debug("entered insertInSASAllocation");
		int count = 0;
		String Query = "insert into tbl_sas_allocation (fresh_bin,issuable_bin,denomination,category_fresh,category_issuable,insert_time,update_time,insert_by,update_by) "
				+ "values (?,?,?,?,?,now(),now(),?,?);";
		JdbcTemplate jdbcTemplate = jdbcTemplateDao.getJdbcTemplate();
		count = jdbcTemplate.update(Query, sasAllocation.getFreshBin(), sasAllocation.getIssuableBin(),
				sasAllocation.getDenomination(), sasAllocation.getCategoryFresh(), sasAllocation.getCategoryIssuable(),
				sasAllocation.getInsertBy(), sasAllocation.getUpdateBy());
		LOG.debug("exit insertInSASAllocation");
		return count;
	}

	@Override
	public List<SASAllocation> getSASAllocation() {
		JdbcTemplate jdbcTemplate = jdbcTemplateDao.getJdbcTemplate();
		String Query = "select * from tbl_sas_allocation where status=1";
		List<SASAllocation> sasList = jdbcTemplate.query(Query, new SASAllocationMapper());
		return sasList;
	}

	@Override
	public List<SoiledRemittanceAllocation> getSoiledRemittanceAllocation() {
		JdbcTemplate jdbcTemplate = jdbcTemplateDao.getJdbcTemplate();
		String Query = "select * from tbl_soiled_remittance_allocation;";
		List<SoiledRemittanceAllocation> soiledList = jdbcTemplate.query(Query, new SoiledAllocationMapper());
		return soiledList;
	}

	@Override
	public int updateSASStatus() {
		int count = 0;
		String Query = "update tbl_sas set status=0 where status=1";
		JdbcTemplate jdbcTemplate = jdbcTemplateDao.getJdbcTemplate();
		count = jdbcTemplate.update(Query);
		return count;
	}

	@Override
	public boolean insertInSoiledRemmitance(SoiledRemittance soiledRemittance) {
		boolean isSuccessfull = false;
		int count = 0;
		KeyHolder keyHolder = new GeneratedKeyHolder();
		count = jdbcTemplateDao.getJdbcTemplate().update(new SoiledStatementCreator(soiledRemittance), keyHolder);
		if (count > 0) {
			isSuccessfull = true;
		} else {
			isSuccessfull = false;
		}
		soiledRemittance.setId(keyHolder.getKey().intValue());
		return isSuccessfull;
	}

	@Override
	public boolean insertInSoiledRemmitanceAllocation(List<SoiledRemittanceAllocation> soiledList, SoiledRemittance soiledRemittance) {
		boolean isSuccessfull = false;
		int count = 0;
		String Query = "insert into tbl_soiled_remittance_allocation (soiled_remittance_id,denomination,bundle,total,bin,box,weight,insert_time,update_time,insert_by,update_by) values (?,?,?,?,?,?,?,now(),now(),?,?)";
		JdbcTemplate jdbcTemplate = jdbcTemplateDao.getJdbcTemplate();
		for (SoiledRemittanceAllocation soiledRemittanceAllocation : soiledList) {
			count = jdbcTemplate.update(Query, soiledRemittance.getId(),
					soiledRemittanceAllocation.getDenomination(), soiledRemittanceAllocation.getBundle(),
					soiledRemittanceAllocation.getTotal(), soiledRemittanceAllocation.getBin(),
					soiledRemittanceAllocation.getBox(), soiledRemittanceAllocation.getWeight(),
					soiledRemittance.getInsertBy(), soiledRemittance.getUpdateBy());
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
	public boolean saveSoiledAndSoiledAllocation(SoiledRemittance soiledRemmitance) {
		boolean isAllSuccess = false;
		try {
			isAllSuccess = insertInSoiledRemmitance(soiledRemmitance);
			if (isAllSuccess) {
				
				isAllSuccess = insertInSoiledRemmitanceAllocation(soiledRemmitance.getSoiledAllocationList(), soiledRemmitance);
			}
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
		return isAllSuccess;
	}
	
	@Override
	public boolean insertInDorv(DorvBean dorvbean) {
		boolean isSuccessfull = false;
		//int lastInsertedId = 0;
		int count = 0;
		KeyHolder keyHolder = new GeneratedKeyHolder();
		count = jdbcTemplateDao.getJdbcTemplate().update(new DiversionOrvStatementCreator(dorvbean), keyHolder);
		if (count > 0) {
			isSuccessfull = true;
		} else {
			isSuccessfull = false;
		}
		dorvbean.setId(keyHolder.getKey().intValue());
		return isSuccessfull;
	}

	@Override
	public boolean insertInDorvAllocation(List<DorvAllocation> dorvList, DorvBean dorvbean) {
		boolean isSuccessfull = false;
		int count = 0;
		String Query = "insert into tbl_diversion_orv_allocation (diversion_orv_id,denomination,bundle,category,total,bin,insert_time,update_time,insert_by,update_by) values (?,?,?,?,?,?,now(),now(),?,?);";
		JdbcTemplate jdbcTemplate = jdbcTemplateDao.getJdbcTemplate();
		for (DorvAllocation dorvAllocation : dorvList) {
			count = jdbcTemplate.update(Query, dorvbean.getId(), dorvAllocation.getDenomination(),
					dorvAllocation.getBundle(), dorvAllocation.getCategory(), dorvAllocation.getTotal(), dorvAllocation.getBin(),
					dorvbean.getInsertBy(), dorvbean.getUpdateBy());
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
	public boolean saveDorvAndDorvAllocation(DorvBean dorvBean) {
		boolean isAllSuccess = false;
		try {
			isAllSuccess = insertInDorv(dorvBean);
			if (isAllSuccess) {
				isAllSuccess = insertInDorvAllocation(dorvBean.getDiversionAllocationList(), dorvBean);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return isAllSuccess;
	}

	@Override
	public boolean insertInORV(ORVBean orvBean) {
		boolean isSuccessfull = false;
		int count = 0;
		KeyHolder keyHolder = new GeneratedKeyHolder();
		count = jdbcTemplateDao.getJdbcTemplate().update(new ORVStatementCreator(orvBean), keyHolder);
		if (count > 0) {
			isSuccessfull = true;
		} else {
			isSuccessfull = false;
		}
		orvBean.setId(keyHolder.getKey().intValue());
		return isSuccessfull;
	}

	@Override
	public boolean insertInORVAllocation(List<ORVAllocation> orvList, ORVBean orvBean) {
		int count = 0;
		boolean isSucessfull = false;
		String Query = "insert into tbl_orv_allocation (orv_id,denomination,bundle,total,bin,insert_time,update_time,insert_by,update_by) values (?,?,?,?,?,now(),now(),?,?);";
		JdbcTemplate jdbcTemplate = jdbcTemplateDao.getJdbcTemplate();
		for (ORVAllocation orvBeanAllocation : orvList) {
			count = jdbcTemplate.update(Query,orvBean.getId(),orvBeanAllocation.getDenomination(), orvBeanAllocation.getBundle(), orvBeanAllocation.getTotal(),
					orvBeanAllocation.getBin(), orvBean.getInsertBy(), orvBean.getUpdateBy());
			if (count > 0) {
				isSucessfull = true;
			} else {
				isSucessfull = false;
				break;
			}
		}
		return isSucessfull;
	}

	@Override
	public boolean saveOrvAndOrvAllocation(ORVBean orvBean) {
		boolean isAllSuccess = false;
		try {
			isAllSuccess = insertInORV(orvBean);
			if (isAllSuccess) {
				isAllSuccess = insertInORVAllocation(orvBean.getOrvAllocationList(), orvBean);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return isAllSuccess;
	}

	@Override
	public List<ORVAllocation> getORVAllocationList() {
		JdbcTemplate jdbcTemplate = jdbcTemplateDao.getJdbcTemplate();
		String Query = "select * from tbl_orv_allocation;";
		List<ORVAllocation> orvList = jdbcTemplate.query(Query, new ORVAllocationMapper());
		return orvList;
	}

	@Override
	public List<DorvAllocation> getDORVAllocationList() {
		JdbcTemplate jdbcTemplate = jdbcTemplateDao.getJdbcTemplate();
		String Query = "select * from tbl_diversion_orv_allocation;";
		List<DorvAllocation> dorvList = jdbcTemplate.query(Query, new DORVAllocationMapper());
		return dorvList;
	}

	@Override
	public List<DorvBean> getDORVRecords() {
		JdbcTemplate jdbcTemplate = jdbcTemplateDao.getJdbcTemplate();
		String Query = "select * from tbl_diversion_orv;";
		List<DorvBean> dorvList = jdbcTemplate.query(Query, new DORVMapper());
		return dorvList;
	}

	@Override
	public List<SoiledRemittance> getSoiledRecord() {
		JdbcTemplate jdbcTemplate = jdbcTemplateDao.getJdbcTemplate();
		String Query = "select * from tbl_soiled_remittance;";
		List<SoiledRemittance> soiledList = jdbcTemplate.query(Query, new SoiledMapper());
		return soiledList;
	}

	@Override
	public List<ORVBean> getORVRecords() {
		JdbcTemplate jdbcTeamplte = jdbcTemplateDao.getJdbcTemplate();
		String Query="select * from tbl_orv;";
		List<ORVBean> orvList=jdbcTeamplte.query(Query, new ORVMapper());
		return orvList;
	}
	
	
}
