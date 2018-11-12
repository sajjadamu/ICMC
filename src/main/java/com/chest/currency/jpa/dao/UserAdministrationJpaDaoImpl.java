package com.chest.currency.jpa.dao;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
import com.chest.currency.entity.model.QAccountDetail;
import com.chest.currency.entity.model.QBinCapacityDenomination;
import com.chest.currency.entity.model.QBinTransaction;
import com.chest.currency.entity.model.QBinTransactionBOD;
import com.chest.currency.entity.model.QCRA;
import com.chest.currency.entity.model.QDelegateRight;
import com.chest.currency.entity.model.QDiversionORVAllocation;
import com.chest.currency.entity.model.QICMC;
import com.chest.currency.entity.model.QIcmcPrinter;
import com.chest.currency.entity.model.QIndent;
import com.chest.currency.entity.model.QMachine;
import com.chest.currency.entity.model.QMachineAllocation;
import com.chest.currency.entity.model.QMachineCompany;
import com.chest.currency.entity.model.QMachineMaintenance;
import com.chest.currency.entity.model.QMachineModel;
import com.chest.currency.entity.model.QMutilatedIndent;
import com.chest.currency.entity.model.QOtherBankAllocation;
import com.chest.currency.entity.model.QPasswordReset;
import com.chest.currency.entity.model.QRole;
import com.chest.currency.entity.model.QSASAllocation;
import com.chest.currency.entity.model.QSoiledRemittanceAllocation;
import com.chest.currency.entity.model.QUser;
import com.chest.currency.entity.model.QZoneMaster;
import com.chest.currency.entity.model.Role;
import com.chest.currency.entity.model.User;
import com.chest.currency.entity.model.ZoneMaster;
import com.chest.currency.enums.OtherStatus;
import com.chest.currency.enums.Status;
import com.mysema.query.jpa.impl.JPADeleteClause;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.jpa.impl.JPAUpdateClause;

@Repository
public class UserAdministrationJpaDaoImpl implements UserAdministrationJpaDao {

	private static final Logger LOG = LoggerFactory.getLogger(UserAdministrationJpaDaoImpl.class);

	@Autowired
	int maxFailedLoginAttemp;

	@PersistenceContext
	private EntityManager em;

	@Override
	public boolean createUser(User user) {
		em.persist(user);
		return true;
	}

	@Override
	public User isValidUser(String userId) {
		JPAQuery jpaQuery = getFromQueryForUser();
		jpaQuery.where(QUser.user.id.equalsIgnoreCase(userId).and(QUser.user.status.eq(Status.ENABLED)));
		return jpaQuery.singleResult(QUser.user);
	}

	@Override
	public User isValidUser(String username, BigInteger icmcId) {
		JPAQuery jpaQuery = getFromQueryForUser();
		jpaQuery.where(QUser.user.id.equalsIgnoreCase(username)
				.and(QUser.user.status.eq(Status.ENABLED).and(QUser.user.icmcId.eq(icmcId))));
		return jpaQuery.singleResult(QUser.user);
	}

	@Override
	public List<User> getUserList() {
		LOG.info("Going to fetch Users:");

		User user1 = isValidUser("inayat");
		if (user1 != null) {
			LOG.info("user with Id {} already exist:", user1.getId());
		}
		JPAQuery jpaQuery = getFromQueryForUser();
		jpaQuery.where(QUser.user.status.eq(Status.ENABLED));
		List<User> users = jpaQuery.list(QUser.user);
		LOG.info("Fetched Users:", users);
		return users;
	}

	private JPAQuery getFromQueryForUser() {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QUser.user);
		return jpaQuery;
	}

	@Override
	public boolean updateUser(User user) {
		em.merge(user);
		return true;
	}

	@Override
	public void deleteUser(User user) {
		new JPADeleteClause(em, QUser.user).where(QUser.user.id.eq(user.getId())).execute();
	}

	@Override
	public User getUserById(String id) {
		LOG.info("Featchin User by ldap id");
		JPAQuery jpaQuery = getFromQueryForUser();
		jpaQuery.where(QUser.user.id.equalsIgnoreCase(id));
		return jpaQuery.singleResult(QUser.user);
		// return em.find(User.class, id);
	}

	@Override
	public DelegateRight getUserDelegatedRightById(User user) {
		LocalDate localDate = LocalDate.now();
		Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

		JPAQuery jpaQuery = getFromQueryForDelegateRight();
		jpaQuery.where(
				QDelegateRight.delegateRight.periodFrom.loe(date).and(QDelegateRight.delegateRight.periodTo.goe(date))
						.and(QDelegateRight.delegateRight.userId.eq(user.getId()))
						.and(QDelegateRight.delegateRight.status.eq(Status.ENABLED)));

		DelegateRight delegateRight = jpaQuery.singleResult(QDelegateRight.delegateRight);
		return delegateRight;
	}

	private JPAQuery getFromQueryForICMC() {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QICMC.iCMC);
		return jpaQuery;
	}

	@Override
	public List<ICMC> getICMCName(String region) {
		List<ICMC> icmcList = null;
		JPAQuery jpaQuery = getFromQueryForICMC();
		if (region != null && region.trim().length() > 0) {
			jpaQuery.where(QICMC.iCMC.region.equalsIgnoreCase(region).and(QICMC.iCMC.status.eq(Status.ENABLED)));
			icmcList = jpaQuery.list(QICMC.iCMC);
		} else {
			icmcList = new ArrayList<ICMC>();
		}
		return icmcList;
	}

	private JPAQuery getFromQueryForRegion() {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QZoneMaster.zoneMaster);
		return jpaQuery;
	}

	@Override
	public List<ZoneMaster> getRegionList(User user) {
		List<ZoneMaster> regionList = null;
		if (user.getZoneId() != null && user.getZoneId().value().length() > 0) {
			JPAQuery jpaQuery = getFromQueryForRegion();
			jpaQuery.where(QZoneMaster.zoneMaster.zone.eq(user.getZoneId()));
			regionList = jpaQuery.list(QZoneMaster.zoneMaster);
		} else {
			regionList = new ArrayList<ZoneMaster>();
		}
		return regionList;
	}

	@Override
	public List<String> getRegion(ZoneMaster zm) {
		JPAQuery jpaQuery = getFromQueryForRegion();
		jpaQuery.where(QZoneMaster.zoneMaster.zone.eq(zm.getZone()));
		List<String> reasonList = jpaQuery.list(QZoneMaster.zoneMaster.region);
		return reasonList;
	}

	@Override
	public List<ICMC> getICMCList(User user) {
		List<ICMC> icmcList = null;
		if (user.getRegionId() != null && user.getRegionId().trim().length() > 0) {
			JPAQuery jpaQuery = getFromQueryForICMC();
			jpaQuery.where(
					QICMC.iCMC.region.equalsIgnoreCase(user.getRegionId()).and(QICMC.iCMC.status.eq(Status.ENABLED)));
			icmcList = jpaQuery.list(QICMC.iCMC);
		} else {
			icmcList = new ArrayList<ICMC>();
		}
		return icmcList;
	}

	private JPAQuery getFromQueryForBinCapacityDenomination() {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QBinCapacityDenomination.binCapacityDenomination);
		return jpaQuery;
	}

	@Override
	public BinCapacityDenomination getBinCapacityById(Long id) {
		return em.find(BinCapacityDenomination.class, id);
	}

	public boolean updateBinCapacity(BinCapacityDenomination binCapacity) {
		em.merge(binCapacity);
		return true;
	}

	@Override
	public List<BinCapacityDenomination> getBinCapacity() {
		JPAQuery jpaQuery = getFromQueryForBinCapacityDenomination();
		List<BinCapacityDenomination> binCapacityList = jpaQuery.list(QBinCapacityDenomination.binCapacityDenomination);
		return binCapacityList;
	}

	@Override
	public BinCapacityDenomination isValidBinCapacityEntry(BinCapacityDenomination binCapacity) {
		JPAQuery jpaQuery = getFromQueryForBinCapacityDenomination();
		jpaQuery.where(QBinCapacityDenomination.binCapacityDenomination.denomination.eq(binCapacity.getDenomination())
				.and(QBinCapacityDenomination.binCapacityDenomination.vaultSize.eq(binCapacity.getVaultSize()).and(
						QBinCapacityDenomination.binCapacityDenomination.currencyType.eq(binCapacity.getCurrencyType())
				/*
				 * .and(QBinCapacityDenomination.binCapacityDenomination.icmcId.eq(
				 * binCapacity.getIcmcId()))
				 */)));
		BinCapacityDenomination bcd = jpaQuery.singleResult(QBinCapacityDenomination.binCapacityDenomination);
		return bcd;
	}

	@Override
	public boolean saveBinCapacity(BinCapacityDenomination binCapacity) {
		em.persist(binCapacity);
		return true;
	}

	private JPAQuery getFromQueryForAccountDetail() {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QAccountDetail.accountDetail);
		return jpaQuery;
	}

	@Override
	public List<AccountDetail> getAccountDetailsList() {
		JPAQuery jpaQuery = getFromQueryForAccountDetail();
		List<AccountDetail> accountDetailsList = jpaQuery.list(QAccountDetail.accountDetail);
		return accountDetailsList;
	}

	@Override
	public boolean saveAccountDetails(AccountDetail accountDetail) {
		em.persist(accountDetail);
		return true;
	}

	private JPAQuery getFromQueryForDelegateRight() {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QDelegateRight.delegateRight);
		return jpaQuery;
	}

	public List<DelegateRight> getDelegateRightList(BigInteger icmcId) {
		JPAQuery jpaQuery = getFromQueryForDelegateRight();
		jpaQuery.where(QDelegateRight.delegateRight.icmcId.eq(icmcId)
				.and(QDelegateRight.delegateRight.status.eq(Status.ENABLED)));
		List<DelegateRight> delegateRightList = jpaQuery.list(QDelegateRight.delegateRight);
		return delegateRightList;
	}

	public boolean insertDelegateRight(DelegateRight delegateRight) {
		em.persist(delegateRight);
		return true;
	}

	public DelegateRight delegateRightForModify(Long id) {
		return em.find(DelegateRight.class, id);
	}

	public boolean updateDelegateRight(DelegateRight delegateRight) {
		em.merge(delegateRight);
		return true;
	}

	@Override
	public List<ICMC> getICMCForMachineDetails() {
		JPAQuery jpaQuery = getFromQueryForICMC();
		jpaQuery.where(QICMC.iCMC.status.eq(Status.ENABLED));
		List<ICMC> icmcList = jpaQuery.list(QICMC.iCMC);
		return icmcList;
	}

	@Override
	public boolean addMachineModelDetails(MachineModel machineModel) {
		em.persist(machineModel);
		return true;
	}

	private JPAQuery getFromQueryForMachineModelDetails() {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QMachineModel.machineModel);
		return jpaQuery;
	}

	@Override
	public List<MachineModel> getMachineModelDetails() {
		JPAQuery jpaQuery = getFromQueryForMachineModelDetails();
		jpaQuery.where(QMachineModel.machineModel.status.ne(Status.DELETED));
		List<MachineModel> machineModelList = jpaQuery.list(QMachineModel.machineModel);
		return machineModelList;
	}

	/*
	 * @Override public List<MachineModel> getMachineModel(BigInteger icmcId) {
	 * JPAQuery jpaQuery = getFromQueryForMachineModelDetails();
	 * //jpaQuery.where(QMachineModel.machineModel.icmcId.eq(icmcId));
	 * List<MachineModel> machineModelList =
	 * jpaQuery.list(QMachineModel.machineModel); return machineModelList; }
	 */

	@Override
	public List<String> getStandardProductivityByModel(String machineModel) {
		JPAQuery jpaQuery = getFromQueryForMachineModelDetails();
		jpaQuery.where(QMachineModel.machineModel.machineModelType.equalsIgnoreCase(machineModel));
		List<String> productivity = jpaQuery.list(QMachineModel.machineModel.standardProductivity);
		return productivity;
	}

	@Override
	public List<String> getModelTypeAcordingIcmc() {
		JPAQuery jpaQuery = getFromQueryForMachineModelDetails();
		List<String> productivity = jpaQuery.list(QMachineModel.machineModel.machineModelType);
		return productivity;

	}

	@Override
	public boolean addMachineDetails(Machine machine) {
		em.persist(machine);
		return true;
	}

	private JPAQuery getFromQueryForMachineDetails() {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QMachine.machine);
		return jpaQuery;
	}

	private JPAQuery getFromQueryForBinTransaction() {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QBinTransaction.binTransaction);
		return jpaQuery;
	}

	@Override
	public List<Machine> getMachineDetails(BigInteger icmcId) {
		JPAQuery jpaQuery = getFromQueryForMachineDetails();
		jpaQuery.where(QMachine.machine.status.ne(Status.DELETED).and(QMachine.machine.icmcId.eq(icmcId)));
		List<Machine> machineList = jpaQuery.list(QMachine.machine);
		return machineList;
	}

	@Override
	public MachineModel getModelTypeId(long id) {
		return em.find(MachineModel.class, id);
	}

	@Override
	public boolean updateMachineModel(MachineModel machineModel) {
		em.merge(machineModel);
		return true;
	}

	@Override
	public Machine getMachineDetailsById(long id) {
		return em.find(Machine.class, id);
	}

	@Override
	public boolean updateMachine(Machine machine) {
		em.merge(machine);
		return true;
	}

	@Override
	public boolean updateUserPassword(User user) {
		em.merge(user);
		return true;
	}

	private JPAQuery getFromQueryForRoles() {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QRole.role);
		return jpaQuery;
	}

	private JPAQuery getFromQueryForPasswordReset() {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QPasswordReset.passwordReset);
		return jpaQuery;
	}

	@Override
	public List<Role> getAllRole() {
		JPAQuery jpaQuery = getFromQueryForRoles();
		jpaQuery.where(QRole.role.status.eq(Status.ENABLED));
		List<Role> roleList = jpaQuery.list(QRole.role);
		return roleList;
	}

	@Override
	@Transactional
	public void resetFailAttempts(String id) {
		User user = this.getUserById(id);
		resetFailAttempts(user);
	}

	@Override
	@Transactional
	public void updateFailAttempts(String id) {
		User user = this.getUserById(id);
		if (user != null) {
			int loginFailedAttempt = user.getFaildeLogons() != null ? user.getFaildeLogons() + 1 : 1;

			user.setFaildeLogons(loginFailedAttempt);
			user.setLastAuthFail(Calendar.getInstance());
			if (loginFailedAttempt > maxFailedLoginAttemp) {
				user.setIsAccountLocked('Y');
			}
		}
		this.updateUser(user);

	}

	@Override
	public PasswordReset getPasswordResetByIdAndToken(String userId, String token) {
		JPAQuery jpaQuery = getFromQueryForPasswordReset();
		jpaQuery.where(QPasswordReset.passwordReset.userId.eq(userId).and(QPasswordReset.passwordReset.token.eq(token))
				.and(QPasswordReset.passwordReset.status.eq(Status.ENABLED)));
		return jpaQuery.singleResult(QPasswordReset.passwordReset);
	}

	@Override
	public PasswordReset savePasswordReset(PasswordReset passwordReset) {
		em.persist(passwordReset);
		return passwordReset;
	}

	@Override
	public PasswordReset updatePasswordReset(PasswordReset passwordReset) {

		new JPAUpdateClause(em, QPasswordReset.passwordReset)
				.where(QPasswordReset.passwordReset.userId.eq(passwordReset.getUserId())
						.and(QPasswordReset.passwordReset.token.eq(passwordReset.getToken())))
				.set(QPasswordReset.passwordReset.status, passwordReset.getStatus()).execute();

		return passwordReset;
	}

	@Override
	public List<User> getLockedUserList() {
		JPAQuery jpaQuery = getFromQueryForUser();
		jpaQuery.where(QUser.user.isAccountLocked.eq('Y').and(QUser.user.status.eq(Status.ENABLED)));
		List<User> lockedUserList = jpaQuery.list(QUser.user);
		return lockedUserList;
	}

	@Override
	public boolean unlockLockedUser(String id) {
		new JPAUpdateClause(em, QUser.user).where(QUser.user.id.eq(id).and(QUser.user.status.eq(Status.ENABLED)))
				.set(QUser.user.isAccountLocked, 'N').execute();
		return true;
	}

	public JPAQuery getFromQueryForMachineModel() {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QMachineModel.machineModel);
		return jpaQuery;
	}

	@Override
	public MachineModel isValidMachineModel(String machineModelType) {
		JPAQuery jpaQuery = getFromQueryForMachineModel();
		jpaQuery.where(QMachineModel.machineModel.machineModelType.equalsIgnoreCase(machineModelType));
		return jpaQuery.singleResult(QMachineModel.machineModel);
	}

	@Override
	public List<MachineModel> getMachineModelTypeList() {
		JPAQuery jpaQuery = getFromQueryForMachineModel();
		jpaQuery.where(QMachineModel.machineModel.status.ne(Status.DELETED));
		return jpaQuery.list(QMachineModel.machineModel);
	}

	public JPAQuery getFromQueryForMachineCompany() {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QMachineCompany.machineCompany);
		return jpaQuery;
	}

	@Override
	public List<MachineCompany> getMachineCompanyNameList() {
		JPAQuery jpaQuery = getFromQueryForMachineCompany();
		jpaQuery.where(QMachineCompany.machineCompany.status.ne(Status.DELETED));
		return jpaQuery.list(QMachineCompany.machineCompany);
	}

	public JPAQuery getFromQueryForUiRole() {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QRole.role);
		return jpaQuery;
	}

	@Override
	public String getRoleType(String roleType) {
		JPAQuery jpaQuery = getFromQueryForUiRole();
		jpaQuery.where(QRole.role.status.ne(Status.DELETED).and(QRole.role.id.eq(roleType)));
		return jpaQuery.singleResult(QRole.role.icmcAccess).toString();
	}

	@Override
	public List<String> getIcmc(String region) {
		JPAQuery jpaQuery = getFromQueryForICMC();
		jpaQuery.where(QICMC.iCMC.status.ne(Status.DELETED).and(QICMC.iCMC.region.eq(region)));
		return jpaQuery.list(QICMC.iCMC.name);
	}

	@Override
	public void resetFailAttempts(User user) {
		if (user != null && user.getFaildeLogons() != null) {
			user.setFaildeLogons(null);
			user.setLastAuthFail(null);
			user.setIsAccountLocked(null);
			this.updateUser(user);
		}
	}

	@Override
	public List<ICMC> getEnabledICMCList() {
		JPAQuery jpaQuery = getFromQueryForICMC();
		jpaQuery.where(QICMC.iCMC.status.eq(Status.ENABLED));
		return jpaQuery.list(QICMC.iCMC);
	}

	@Override
	public IcmcPrinter isValidPrinter(String printerName) {
		JPAQuery jpaQuery = getFromQueryForIcmcPrinter();
		jpaQuery.where(QIcmcPrinter.icmcPrinter.printerName.equalsIgnoreCase(printerName)
				.and(QIcmcPrinter.icmcPrinter.status.ne(Status.DELETED)));
		return jpaQuery.singleResult(QIcmcPrinter.icmcPrinter);
	}

	private JPAQuery getFromQueryForIcmcPrinter() {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QIcmcPrinter.icmcPrinter);
		return jpaQuery;
	}

	@Override
	public void savePrinter(IcmcPrinter icmcPrinter) {
		em.persist(icmcPrinter);
	}

	@Override
	public List<IcmcPrinter> getPrinterList() {
		LOG.info("Going to fetch Printers List:");
		JPAQuery jpaQuery = getFromQueryForIcmcPrinter();
		jpaQuery.where(QIcmcPrinter.icmcPrinter.status.ne(Status.DELETED));
		List<IcmcPrinter> icmcPrinterList = jpaQuery.list(QIcmcPrinter.icmcPrinter);
		LOG.info("Fetched Printers List:", icmcPrinterList);
		return icmcPrinterList;
	}

	@Override
	public IcmcPrinter getPrinterById(Long id) {
		return em.find(IcmcPrinter.class, id);
	}

	@Override
	public void updatePrinter(IcmcPrinter icmcPrinter) {
		em.merge(icmcPrinter);
	}

	@Override
	public List<IcmcPrinter> printerList(BigInteger icmc) {
		JPAQuery jpaQuery = getFromQueryForIcmcPrinter();
		jpaQuery.where(
				QIcmcPrinter.icmcPrinter.icmcId.eq(icmc).and(QIcmcPrinter.icmcPrinter.status.eq(Status.ENABLED)));
		List<IcmcPrinter> icmcPrinterList = jpaQuery.list(QIcmcPrinter.icmcPrinter);
		return icmcPrinterList;
	}

	public JPAQuery getFromQueryForMutilatedFullValue() {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QMutilatedIndent.mutilatedIndent);
		return jpaQuery;
	}

	public JPAQuery getFromQueryForMachineMaintenance() {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QMachineMaintenance.machineMaintenance);
		return jpaQuery;
	}

	@Override
	public List<MachineMaintenance> getMaintanenceData(BigInteger icmcId) {
		JPAQuery jpaQuery = getFromQueryForMachineMaintenance();
		jpaQuery.where(QMachineMaintenance.machineMaintenance.icmcId.eq(icmcId)
				.and(QMachineMaintenance.machineMaintenance.status.eq(Status.ENABLED)));
		List<MachineMaintenance> date = jpaQuery.list(QMachineMaintenance.machineMaintenance);
		return date;
	}

	@Override
	public Machine checkMachineIsExistOrNot(BigInteger icmcId, int machineNo) {
		JPAQuery jpaQuery = getFromQueryForMachineDetails();
		jpaQuery.where(QMachine.machine.icmcId.eq(icmcId).and(QMachine.machine.machineNo.eq(machineNo)));
		Machine machine = jpaQuery.singleResult(QMachine.machine);
		return machine;
	}

	private JPAQuery getFromQueryForCRA() {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QCRA.cRA);
		return jpaQuery;
	}

	@Override
	public String getNotificationFromCRA(BigInteger icmcId) {
		JPAQuery jpaQuery = getFromQueryForCRA();
		jpaQuery.where(QCRA.cRA.icmcId.eq(icmcId)
				.and(QCRA.cRA.status.eq(OtherStatus.REQUESTED).or(QCRA.cRA.status.eq(OtherStatus.PROCESSED))));
		;
		String status = jpaQuery.singleResult(QCRA.cRA.insertBy);
		return status;
	}

	private JPAQuery getFromQueryForSASAllocation() {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QSASAllocation.sASAllocation);
		return jpaQuery;
	}

	@Override
	public String getNotificationFromSASAllocation(BigInteger icmcId) {
		JPAQuery jpaQuery = getFromQueryForSASAllocation();
		jpaQuery.where(QSASAllocation.sASAllocation.icmcId.eq(icmcId)
				.and(QSASAllocation.sASAllocation.status.eq(OtherStatus.REQUESTED)));
		String status = jpaQuery.singleResult(QSASAllocation.sASAllocation.binNumber);
		return status;
	}

	private JPAQuery getFromQueryForIndent() {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QIndent.indent);
		return jpaQuery;
	}

	@Override
	public String getNotificationFromIndent(BigInteger icmcId) {
		JPAQuery jpaQuery = getFromQueryForIndent();
		jpaQuery.where(QIndent.indent.icmcId.eq(icmcId).and(QIndent.indent.status.eq(OtherStatus.REQUESTED)));
		String status = jpaQuery.singleResult(QIndent.indent.bin);
		return status;
	}

	private JPAQuery getFromQueryForOtherBankAllocation() {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QOtherBankAllocation.otherBankAllocation);
		return jpaQuery;
	}

	@Override
	public String getNotificationFromOtherBankAllocation(BigInteger icmcId) {
		JPAQuery jpaQuery = getFromQueryForOtherBankAllocation();
		jpaQuery.where(QOtherBankAllocation.otherBankAllocation.icmcId.eq(icmcId)
				.and(QOtherBankAllocation.otherBankAllocation.status.eq(OtherStatus.REQUESTED)));
		String message = jpaQuery.singleResult(QOtherBankAllocation.otherBankAllocation.binNumber);
		return message;
	}

	private JPAQuery getFromQueryForSoiledAllocation() {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QSoiledRemittanceAllocation.soiledRemittanceAllocation);
		return jpaQuery;
	}

	@Override
	public String getNotificationFromSoiledAllocation(BigInteger icmcId) {
		JPAQuery jpaQuery = getFromQueryForSoiledAllocation();
		jpaQuery.where(QSoiledRemittanceAllocation.soiledRemittanceAllocation.icmcId.eq(icmcId)
				.and(QSoiledRemittanceAllocation.soiledRemittanceAllocation.status.eq(OtherStatus.REQUESTED)));
		String message = jpaQuery.singleResult(QSoiledRemittanceAllocation.soiledRemittanceAllocation.box);
		return message;
	}

	private JPAQuery getFromQueryForDiversion() {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QDiversionORVAllocation.diversionORVAllocation);
		return jpaQuery;
	}

	@Override
	public String getNotificationFromDiversion(BigInteger icmcId) {
		JPAQuery jpaQuery = getFromQueryForDiversion();
		jpaQuery.where(QDiversionORVAllocation.diversionORVAllocation.icmcId.eq(icmcId)
				.and(QDiversionORVAllocation.diversionORVAllocation.status.eq(OtherStatus.REQUESTED)));
		String message = jpaQuery.singleResult(QDiversionORVAllocation.diversionORVAllocation.binNumber);
		return message;
	}

	@Override
	public List<Machine> getMachineList() {
		JPAQuery jpaQuery = getFromQueryForMachineDetails();
		List<Machine> machineListDetails = jpaQuery.list(QMachine.machine);
		return machineListDetails;
	}

	@Override
	public Calendar getNotificationFromBinTransactionForEOD(BigInteger icmcId) {
		JPAQuery jpaQuery = getFromQueryForBinTransaction();
		jpaQuery.where(QBinTransaction.binTransaction.icmcId.eq(icmcId));
		jpaQuery.orderBy(QBinTransaction.binTransaction.updateTime.desc());
		Calendar calendar = jpaQuery.singleResult(QBinTransaction.binTransaction.updateTime);
		return calendar;
	}

	@Override
	public Calendar getNotificationFromBinTransactionBODForEOD(BigInteger icmcId, Calendar sDate, Calendar eDate) {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QBinTransactionBOD.binTransactionBOD);
		jpaQuery.where(QBinTransactionBOD.binTransactionBOD.icmcId.eq(icmcId)
				.and(QBinTransactionBOD.binTransactionBOD.updateTime.between(sDate, eDate)));
		Calendar dateEOD = jpaQuery.singleResult(QBinTransactionBOD.binTransactionBOD.updateTime);
		LOG.info("dateEOD binTransactionEOD " + dateEOD);
		return dateEOD;
	}

	@Override
	public String getNotificationForMachineAllocation(BigInteger icmcId) {
		JPAQuery jpaQuery = getFromQueryForIndent();
		jpaQuery.where(QIndent.indent.icmcId.eq(icmcId).and(QIndent.indent.status.eq(OtherStatus.ACCEPTED))
				.and(QIndent.indent.bundle.gt(0)));
		String message = jpaQuery.singleResult(QIndent.indent.insertBy);
		return message;
	}

	@Override
	public String getNotificationForProcessingOutPut(BigInteger icmcId) {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QMachineAllocation.machineAllocation);
		jpaQuery.where(QMachineAllocation.machineAllocation.icmcId.eq(icmcId)
				.and(QMachineAllocation.machineAllocation.pendingBundle.gt(0)));
		String message = jpaQuery.singleResult(QMachineAllocation.machineAllocation.insertBy);
		return message;
	}

	@Override
	public DelegateRight getRoleFromDelegatedRights(BigInteger icmcId) {
		JPAQuery jpaQuery = getFromQueryForDelegateRight();
		jpaQuery.where(QDelegateRight.delegateRight.icmcId.eq(icmcId)
				.and(QDelegateRight.delegateRight.status.eq(Status.ENABLED)))
				.orderBy(QDelegateRight.delegateRight.id.desc());
		DelegateRight delegateRight = jpaQuery.singleResult(QDelegateRight.delegateRight);
		return delegateRight;
	}

	@Override
	public List<User> getUserListByICMC(BigInteger icmcId) {
		LOG.info("Going to fetch Users: by icmc");

		JPAQuery jpaQuery = getFromQueryForUser();
		jpaQuery.where(QUser.user.status.eq(Status.ENABLED).and(QUser.user.icmcId.eq(icmcId)));
		List<User> users = jpaQuery.list(QUser.user);
		LOG.info("Fetched Users:", users);
		return users;
	}

	@Override
	public LamRequestLog createLamLog(LamRequestLog requestLog) {
		em.persist(requestLog);
		return requestLog;
	}

	@Override
	public void updateLamLog(LamRequestLog requestLog) {
		em.merge(requestLog);
	}
}
