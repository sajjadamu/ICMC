/*******************************************************************************
 * /* Copyright (C) Indicsoft Technologies Pvt Ltd
 * * All Rights Reserved.
 *******************************************************************************/
package com.chest.currency.jpa.dao;

import java.math.BigInteger;
import java.util.Calendar;
import java.util.List;

import com.chest.currency.entity.model.AccountDetail;
import com.chest.currency.entity.model.BinCapacityDenomination;
import com.chest.currency.entity.model.DelegateRight;
import com.chest.currency.entity.model.ICMC;
import com.chest.currency.entity.model.IcmcPrinter;
import com.chest.currency.entity.model.LamRequestLog;
import com.chest.currency.entity.model.Machine;
import com.chest.currency.entity.model.MachineCompany;
import com.chest.currency.entity.model.MachineMaintenance;
import com.chest.currency.entity.model.MachineModel;
import com.chest.currency.entity.model.PasswordReset;
import com.chest.currency.entity.model.Role;
import com.chest.currency.entity.model.User;
import com.chest.currency.entity.model.ZoneMaster;

public interface UserAdministrationJpaDao {

	public boolean createUser(User user);

	public User isValidUser(String username);

	public User isValidUser(String username, BigInteger icmcId);

	public List<User> getUserList();

	public List<User> getUserListByICMC(BigInteger icmcId);

	public User getUserById(String id);

	public DelegateRight getUserDelegatedRightById(User user);

	public boolean updateUser(User user);
	
	public void deleteUser(User user);

	public List<ICMC> getICMCName(String region);

	public List<ZoneMaster> getRegionList(User user);

	public List<String> getRegion(ZoneMaster zm);

	public List<ICMC> getICMCList(User user);

	public BinCapacityDenomination getBinCapacityById(Long id);

	public boolean updateBinCapacity(BinCapacityDenomination binCapacity);

	// public List<BinCapacityDenomination> getBinCapacity(BigInteger icmcId);

	public List<BinCapacityDenomination> getBinCapacity();

	public BinCapacityDenomination isValidBinCapacityEntry(BinCapacityDenomination binCapacity);

	public boolean saveBinCapacity(BinCapacityDenomination binCapacity);

	public List<AccountDetail> getAccountDetailsList();

	public boolean saveAccountDetails(AccountDetail accountDetail);

	public List<DelegateRight> getDelegateRightList(BigInteger icmcId);

	public boolean insertDelegateRight(DelegateRight delegateRight);

	public DelegateRight delegateRightForModify(Long id);

	public boolean updateDelegateRight(DelegateRight delegateRight);

	public List<ICMC> getICMCForMachineDetails();

	public boolean addMachineModelDetails(MachineModel machineModel);

	public List<MachineModel> getMachineModelDetails();

	/* public List<MachineModel> getMachineModel(BigInteger icmcId); */

	public List<String> getStandardProductivityByModel(String machineModel);

	public boolean addMachineDetails(Machine machine);

	public List<Machine> getMachineDetails(BigInteger icmcId);

	public MachineModel getModelTypeId(long id);

	public boolean updateMachineModel(MachineModel machineModel);

	public Machine getMachineDetailsById(long id);

	public boolean updateMachine(Machine machine);

	public Machine checkMachineIsExistOrNot(BigInteger icmcId, int machineNumber);

	public boolean updateUserPassword(User user);

	public List<Role> getAllRole();

	public void resetFailAttempts(String id);

	public void updateFailAttempts(String id);

	public PasswordReset getPasswordResetByIdAndToken(String userId, String token);

	public PasswordReset savePasswordReset(PasswordReset passwordReset);

	public PasswordReset updatePasswordReset(PasswordReset passwordReset);

	public List<User> getLockedUserList();

	public boolean unlockLockedUser(String id);

	public MachineModel isValidMachineModel(String machineModelType);

	public List<String> getModelTypeAcordingIcmc();

	public List<MachineModel> getMachineModelTypeList();

	public List<MachineCompany> getMachineCompanyNameList();

	public String getRoleType(String roleType);

	public List<String> getIcmc(String region);

	public void resetFailAttempts(User user);

	public List<ICMC> getEnabledICMCList();

	public IcmcPrinter isValidPrinter(String printerName);

	public void savePrinter(IcmcPrinter icmcPrinter);

	public List<IcmcPrinter> getPrinterList();

	public IcmcPrinter getPrinterById(Long id);

	public void updatePrinter(IcmcPrinter icmcPrinter);

	public List<IcmcPrinter> printerList(BigInteger icmc);

	public List<MachineMaintenance> getMaintanenceData(BigInteger icmcId);

	public String getNotificationFromCRA(BigInteger icmcId);

	public String getNotificationFromSASAllocation(BigInteger icmcId);

	public String getNotificationFromIndent(BigInteger icmcId);

	public String getNotificationFromOtherBankAllocation(BigInteger icmcId);

	public String getNotificationFromSoiledAllocation(BigInteger icmcId);

	public String getNotificationFromDiversion(BigInteger icmcId);

	public List<Machine> getMachineList();

	public Calendar getNotificationFromBinTransactionForEOD(BigInteger icmcId);

	public Calendar getNotificationFromBinTransactionBODForEOD(BigInteger icmcId, Calendar sDate, Calendar eDate);

	public String getNotificationForMachineAllocation(BigInteger icmcId);

	public String getNotificationForProcessingOutPut(BigInteger icmcId);

	public DelegateRight getRoleFromDelegatedRights(BigInteger icmcId);

	public LamRequestLog createLamLog(LamRequestLog requestLog);
	
	public void updateLamLog(LamRequestLog requestLog);

}
