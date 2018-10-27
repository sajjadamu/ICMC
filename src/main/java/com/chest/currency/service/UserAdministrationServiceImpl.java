package com.chest.currency.service;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Calendar;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chest.currency.dao.UserAdministrationDao;
import com.chest.currency.email.MailService;
import com.chest.currency.entity.model.AccountDetail;
import com.chest.currency.entity.model.BinCapacityDenomination;
import com.chest.currency.entity.model.DelegateRight;
import com.chest.currency.entity.model.ICMC;
import com.chest.currency.entity.model.IcmcPrinter;
import com.chest.currency.entity.model.Machine;
import com.chest.currency.entity.model.MachineCompany;
import com.chest.currency.entity.model.MachineMaintenance;
import com.chest.currency.entity.model.MachineModel;
import com.chest.currency.entity.model.PasswordReset;
import com.chest.currency.entity.model.Role;
import com.chest.currency.entity.model.User;
import com.chest.currency.entity.model.ZoneMaster;
import com.chest.currency.enums.Status;
import com.chest.currency.jpa.dao.UserAdministrationJpaDao;
import com.chest.currency.util.UtilityJpa;

@Service
@Transactional
public class UserAdministrationServiceImpl implements UserAdministrationService {

	private static final Logger LOG = LoggerFactory.getLogger(UserAdministrationServiceImpl.class);

	@Autowired
	UserAdministrationDao userAdministrationDao;

	@Autowired
	UserAdministrationJpaDao userAdministrationJpaDao;

	@Autowired
	private MailService mailSender;

	@Override
	@Transactional
	public boolean createUser(User user, String path) {
		boolean isAllSuccess = false;
		try {
			isAllSuccess = userAdministrationJpaDao.createUser(user);
			if (isAllSuccess) {
				this.savePasswordReset(user, null, path);
			}
			LOG.info("New user has been created");
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
		return isAllSuccess;
	}

	public User isValidUser(String userId) {
		User userBean = userAdministrationJpaDao.isValidUser(userId);
		return userBean;
	}

	@Override
	public User isValidUser(String userId, BigInteger icmcId) {
		User userBean = userAdministrationJpaDao.isValidUser(userId, icmcId);
		return userBean;
	}

	public List<User> getUserList() {
		List<User> userList = userAdministrationJpaDao.getUserList();
		return userList;
	}
	
	@Override
	public List<User> getUserListByICMC(BigInteger icmcId) {
		List<User> userList = userAdministrationJpaDao.getUserListByICMC(icmcId);
		return userList;
	}

	public User getUserById(String id) {
		return userAdministrationJpaDao.getUserById(id);
	}

	@Override
	public void updateUser(User user) {

		userAdministrationJpaDao.updateUser(user);
	}

	@Override
	public List<ICMC> getICMCName(String region) {
		List<ICMC> icmcList = userAdministrationJpaDao.getICMCName(region);
		return icmcList;
	}

	@Override
	public List<ZoneMaster> getRegionList(User user) {
		List<ZoneMaster> regionList = userAdministrationJpaDao.getRegionList(user);
		return regionList;
	}

	@Override
	public List<String> getRegion(ZoneMaster zm) {
		List<String> regionList = userAdministrationJpaDao.getRegion(zm);
		return regionList;
	}

	@Override
	public List<ICMC> getICMCList(User user) {
		List<ICMC> icmcList = userAdministrationJpaDao.getICMCList(user);
		return icmcList;
	}

	@Override
	public BinCapacityDenomination isValidBinCapacityEntry(BinCapacityDenomination binCapacity) {
		BinCapacityDenomination bcd = userAdministrationJpaDao.isValidBinCapacityEntry(binCapacity);
		return bcd;
	}

	@Override
	public boolean saveBinCapacity(BinCapacityDenomination binCapacity) {
		boolean isSaved = userAdministrationJpaDao.saveBinCapacity(binCapacity);
		return isSaved;
	}

	@Override
	public BinCapacityDenomination getBinCapacityById(Long id) {
		return userAdministrationJpaDao.getBinCapacityById(id);
	}

	@Override
	public boolean updateBinCapacity(BinCapacityDenomination binCapacity) {
		return userAdministrationJpaDao.updateBinCapacity(binCapacity);
	}

	/*
	 * @Override public List<BinCapacityDenomination> getBinCapacity(BigInteger
	 * icmcId) { List<BinCapacityDenomination> binCapacityList =
	 * userAdministrationJpaDao.getBinCapacity(icmcId); return binCapacityList;
	 * }
	 */

	@Override
	public List<BinCapacityDenomination> getBinCapacity() {
		List<BinCapacityDenomination> binCapacityList = userAdministrationJpaDao.getBinCapacity();
		return binCapacityList;
	}

	@Override
	public List<AccountDetail> getAccountDetailsList() {
		List<AccountDetail> accountDetailsList = userAdministrationJpaDao.getAccountDetailsList();
		return accountDetailsList;
	}

	@Override
	public boolean saveAccountDetails(AccountDetail accountDetail) {
		boolean isAllSuccess = false;
		try {
			isAllSuccess = userAdministrationJpaDao.saveAccountDetails(accountDetail);
			LOG.info("Account Detail has been added");
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
		return isAllSuccess;
	}

	@Override
	public boolean insertDelegateRight(DelegateRight delegateRight) {
		return userAdministrationJpaDao.insertDelegateRight(delegateRight);
	}

	@Override
	public List<DelegateRight> getDelegateRightList(BigInteger icmcId) {
		return userAdministrationJpaDao.getDelegateRightList(icmcId);
	}

	@Override
	public DelegateRight delegateRightForModify(Long id) {
		return userAdministrationJpaDao.delegateRightForModify(id);
	}

	@Override
	public boolean updateDelegateRight(DelegateRight delegateRight) {
		return userAdministrationJpaDao.updateDelegateRight(delegateRight);
	}

	@Override
	public List<ICMC> getICMCForMachineDetails() {
		List<ICMC> icmcList = userAdministrationJpaDao.getICMCForMachineDetails();
		return icmcList;
	}

	@Override
	public boolean addMachineModelDetails(MachineModel machineModel) {
		boolean isAllSuccess = false;
		try {
			isAllSuccess = userAdministrationJpaDao.addMachineModelDetails(machineModel);
			LOG.info("Machine Model Detail has been added");
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
		return isAllSuccess;
	}

	@Override
	public List<MachineModel> getMachineModelDetails() {
		List<MachineModel> machineModelList = userAdministrationJpaDao.getMachineModelDetails();
		return machineModelList;
	}

	/*
	 * @Override public List<MachineModel> getMachineModel(BigInteger icmcId) {
	 * List<MachineModel> machineModel =
	 * userAdministrationJpaDao.getMachineModel(icmcId); return machineModel; }
	 */

	@Override
	public List<String> getStandardProductivityByModel(String machineModel) {
		List<String> productivityList = userAdministrationJpaDao.getStandardProductivityByModel(machineModel);
		return productivityList;
	}

	@Override
	public List<String> getModelTypeAcordingIcmc() {
		List<String> productivityList = userAdministrationJpaDao.getModelTypeAcordingIcmc();
		return productivityList;
	}

	@Override
	public boolean addMachineDetails(Machine machine) {
		boolean isAllSuccess = false;
		try {
			isAllSuccess = userAdministrationJpaDao.addMachineDetails(machine);
			LOG.info("Machine Details has been added");
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
		return isAllSuccess;
	}

	@Override
	public List<Machine> getMachineDetails(BigInteger icmcId) {
		List<Machine> machineList = userAdministrationJpaDao.getMachineDetails(icmcId);
		return machineList;
	}

	@Override
	public MachineModel getModelTypeId(long id) {
		return userAdministrationJpaDao.getModelTypeId(id);
	}

	@Override
	public boolean updateMachineModel(MachineModel machineModel) {
		MachineModel machineModelNew = userAdministrationJpaDao.getModelTypeId(machineModel.getId());
		machineModel.setInsertBy(machineModelNew.getInsertBy());
		machineModel.setInsertTime(machineModelNew.getInsertTime());
		return userAdministrationJpaDao.updateMachineModel(machineModel);
	}

	@Override
	public Machine getMachineDetailsById(long id) {
		return userAdministrationJpaDao.getMachineDetailsById(id);
	}

	@Override
	public boolean updateMachine(Machine machine) {
		Machine machineNew = userAdministrationJpaDao.getMachineDetailsById(machine.getId());
		machine.setInsertBy(machineNew.getInsertBy());
		machine.setInsertTime(machineNew.getInsertTime());
		return userAdministrationJpaDao.updateMachine(machine);
	}

	@Override
	public boolean updateUserPassword(User user) {
		User changeUser = userAdministrationJpaDao.getUserById(user.getId());
		user.setName(changeUser.getName());
		user.setUpdatedBy(changeUser.getUpdatedBy());
		user.setUpdatedDateTime(user.getUpdatedDateTime());
		user.setZoneId(changeUser.getZoneId());
		user.setRegionId(changeUser.getRegionId());
		user.setIcmcId(changeUser.getIcmcId());
		user.setEmail(changeUser.getEmail());
		user.setCreatedDateTime(changeUser.getCreatedDateTime());
		user.setStatus(changeUser.getStatus());
		user.setCreatedBy(changeUser.getCreatedBy());
		return userAdministrationJpaDao.updateUserPassword(user);
	}

	@Override
	public List<Role> getAllRole() {
		List<Role> roleList = userAdministrationJpaDao.getAllRole();
		return roleList;
	}

	@Override
	public PasswordReset getPasswordResetByIdAndToken(String userId, String token) {
		return userAdministrationJpaDao.getPasswordResetByIdAndToken(userId, token);
	}

	@Override
	public PasswordReset savePasswordReset(PasswordReset passwordReset) {
		return userAdministrationJpaDao.savePasswordReset(passwordReset);
	}

	@Override
	public PasswordReset updatePasswordReset(PasswordReset passwordReset) {
		return userAdministrationJpaDao.updatePasswordReset(passwordReset);

	}

	@Override
	public List<User> getLockedUserList() {
		List<User> lockedUserList = userAdministrationJpaDao.getLockedUserList();
		return lockedUserList;
	}

	@Override
	public boolean unlockLockedUser(String id, String path) {
		boolean isAllSuccess = false;
		isAllSuccess = userAdministrationJpaDao.unlockLockedUser(id);
		User user = userAdministrationJpaDao.getUserById(id);
		this.savePasswordReset(user, null, path);
		if (!isAllSuccess) {
			throw new RuntimeException("Error while unlocking Locked User");
		}
		return isAllSuccess;
	}

	@Override
	public void savePasswordReset(User user, PasswordReset passwordReset, String path) {

		if (passwordReset == null) {
			passwordReset = new PasswordReset();
			passwordReset.setUserId(user.getId());
		}
		StringBuffer token = generatePasswordResetToken(passwordReset);
		this.savePasswordReset(passwordReset);

		String bodyMessage = "Please Click below link or Copy it in Browser to reset your password.\n";
		if (path.contains("resetPasswordRequest")) {
			path = path.replace("resetPasswordRequest", "resetPassword");
		} else if (path.contains("saveUser")) {
			path = path.replace("saveUser", "resetPassword");
		} else if (path.contains("unlockLockedUser")) {
			path = path.replace("unlockLockedUser", "resetPassword");
		}
		path = path + "?userId=" + passwordReset.getUserId() + "&token=" + token.toString();

		// send mail to user
		mailSender.resetPasswordEmail(user.getEmail(), "mailServiceForApp@gmail.com", "Password Reset Link",
				bodyMessage + path);
	}

	private StringBuffer generatePasswordResetToken(PasswordReset passwordReset) {
		// generate token
		StringBuffer token = new StringBuffer();
		new SecureRandom().ints(8, 0, UtilityJpa.VALID_PWD_CHARS.size()).map(UtilityJpa.VALID_PWD_CHARS::get)
				.forEach(charval -> token.append(Character.toChars(charval)));
		passwordReset.setToken(token.toString());
		passwordReset.setCreatedBy(passwordReset.getUserId());
		Calendar now = Calendar.getInstance();
		passwordReset.setCreatedDateTime(now);
		passwordReset.setUpdatedBy(passwordReset.getUserId());
		passwordReset.setUpdatedDateTime(now);
		passwordReset.setStatus(Status.ENABLED);
		return token;
	}

	@Override
	public MachineModel isValidMachineModel(String machineModelType) {
		return userAdministrationJpaDao.isValidMachineModel(machineModelType);

	}

	@Override
	public List<MachineModel> getMachineModelTypeList() {
		return userAdministrationJpaDao.getMachineModelTypeList();
	}

	@Override
	public List<MachineCompany> getMachineCompanyNameList() {
		return userAdministrationJpaDao.getMachineCompanyNameList();
	}

	@Override
	public String getRoleType(String roleType) {
		return userAdministrationJpaDao.getRoleType(roleType);
	}

	@Override
	public List<String> getIcmc(String region) {
		return userAdministrationJpaDao.getIcmc(region);
	}

	@Override
	public void resetFailAttempts(User user) {
		userAdministrationJpaDao.resetFailAttempts(user);
	}

	@Override
	public List<ICMC> getEnabledICMCList() {
		return userAdministrationJpaDao.getEnabledICMCList();
	}

	@Override
	public IcmcPrinter isValidPrinter(String printerName) {
		IcmcPrinter icmcPrinter = userAdministrationJpaDao.isValidPrinter(printerName);
		return icmcPrinter;
	}

	@Override
	public void savePrinter(IcmcPrinter icmcPrinter) {
		userAdministrationJpaDao.savePrinter(icmcPrinter);

	}

	@Override
	public List<IcmcPrinter> getPrinterList() {
		return userAdministrationJpaDao.getPrinterList();
	}

	@Override
	public IcmcPrinter getPrinterById(Long id) {
		return userAdministrationJpaDao.getPrinterById(id);
	}

	@Override
	public void updatePrinter(IcmcPrinter icmcPrinter) {
		userAdministrationJpaDao.updatePrinter(icmcPrinter);
	}

	@Override
	public List<IcmcPrinter> printerList(BigInteger icmc) {
		List<IcmcPrinter> icmcPrinterList = userAdministrationJpaDao.printerList(icmc);
		return icmcPrinterList;
	}

	@Override
	public List<MachineMaintenance> getMaintanenceData(BigInteger icmcId) {
		List<MachineMaintenance> date = userAdministrationJpaDao.getMaintanenceData(icmcId);
		return date;
	}

	@Override
	public Machine checkMachineIsExistOrNot(BigInteger icmcId, int machineNo) {
		Machine machine = userAdministrationJpaDao.checkMachineIsExistOrNot(icmcId, machineNo);
		return machine;
	}

	@Override
	public String getNotificationFromCRA(BigInteger icmcId) {
		String message = userAdministrationJpaDao.getNotificationFromCRA(icmcId);
		return message;
	}

	@Override
	public String getNotificationFromSASAllocation(BigInteger icmcId) {
		String message = userAdministrationJpaDao.getNotificationFromSASAllocation(icmcId);
		return message;
	}

	@Override
	public String getNotificationFromIndent(BigInteger icmcId) {
		String message = userAdministrationJpaDao.getNotificationFromIndent(icmcId);
		return message;
	}

	@Override
	public String getNotificationFromOtherBankAllocation(BigInteger icmcId) {
		String message = userAdministrationJpaDao.getNotificationFromOtherBankAllocation(icmcId);
		return message;
	}

	@Override
	public String getNotificationFromSoiledAllocation(BigInteger icmcId) {
		String message = userAdministrationJpaDao.getNotificationFromSoiledAllocation(icmcId);
		return message;
	}

	@Override
	public String getNotificationFromDiversion(BigInteger icmcId) {
		String message = userAdministrationJpaDao.getNotificationFromDiversion(icmcId);
		return message;
	}

	@Override
	public List<Machine> getMachineList() {
		List<Machine> machineList = userAdministrationJpaDao.getMachineList();
		return machineList;
	}

	@Override
	public Calendar getNotificationFromBinTransactionForEOD(BigInteger icmcId) {
		Calendar calendar = userAdministrationJpaDao.getNotificationFromBinTransactionForEOD(icmcId);
		return calendar;
	}

	@Override
	public Calendar getNotificationFromBinTransactionBODForEOD(BigInteger icmcId, Calendar sDate, Calendar eDate) {
		Calendar calendar = userAdministrationJpaDao.getNotificationFromBinTransactionBODForEOD(icmcId, sDate, eDate);
		return calendar;
	}

	@Override
	public String getNotificationForMachineAllocation(BigInteger icmcId) {
		String message = userAdministrationJpaDao.getNotificationForMachineAllocation(icmcId);
		return message;
	}

	@Override
	public String getNotificationForProcessingOutPut(BigInteger icmcId) {
		String message = userAdministrationJpaDao.getNotificationForProcessingOutPut(icmcId);
		return message;
	}

	@Override
	public DelegateRight getRoleFromDelegatedRights(BigInteger icmcId) {
		DelegateRight delegateRight = userAdministrationJpaDao.getRoleFromDelegatedRights(icmcId);
		return delegateRight;
	}

}
