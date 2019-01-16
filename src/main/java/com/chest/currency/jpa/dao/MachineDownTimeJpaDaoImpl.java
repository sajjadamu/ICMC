package com.chest.currency.jpa.dao;

import java.math.BigInteger;
import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.chest.currency.entity.model.Machine;
import com.chest.currency.entity.model.MachineDowntimeUpdation;
import com.chest.currency.entity.model.MachineSoftwareUpdation;
import com.chest.currency.entity.model.QMachine;
import com.chest.currency.entity.model.QMachineDowntimeUpdation;
import com.chest.currency.entity.model.QMachineSoftwareUpdation;
import com.chest.currency.entity.model.QZoneMaster;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.jpa.impl.JPAUpdateClause;

@Repository
public class MachineDownTimeJpaDaoImpl implements MachineDownTimeJpaDao {
	private static final Logger LOG = LoggerFactory.getLogger(BinDashBoardJpaDaoImpl.class);

	@PersistenceContext
	private EntityManager em;

	@Override
	public boolean MachineDownTimeUpdation(MachineDowntimeUpdation machine) {
		LOG.info("Machine Downtime data inserted");
		em.persist(machine);
		return true;
	}

	private JPAQuery getFromQueryForMAchineDownTimeUpdate() {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QMachineDowntimeUpdation.machineDowntimeUpdation);
		return jpaQuery;
	}

	@Override
	public List<MachineDowntimeUpdation> getListmachineDownTime(BigInteger icmcId) {
		LOG.info("Machine Downtime data Fetch all data");
		JPAQuery jpaQuery = getFromQueryForMAchineDownTimeUpdate();
		jpaQuery.where(QMachineDowntimeUpdation.machineDowntimeUpdation.icmcId.eq(icmcId));
		List<MachineDowntimeUpdation> listMachine = jpaQuery.list(QMachineDowntimeUpdation.machineDowntimeUpdation);
		return listMachine;
	}

	@Override
	public MachineDowntimeUpdation machineRecordForModify(Long idMachineDownTime) {
		MachineDowntimeUpdation machineUpdate = em.find(MachineDowntimeUpdation.class, idMachineDownTime);
		return machineUpdate;
	}

	@Override
	public boolean updateMachineDownTimeUpdation(MachineDowntimeUpdation machine) {
		em.merge(machine);
		return true;
	}

	private JPAQuery getFromQueryForMAchineSoftwareUpdate() {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QMachineSoftwareUpdation.machineSoftwareUpdation);
		return jpaQuery;
	}

	@Override
	public List<MachineSoftwareUpdation> getListmachineSoftware(BigInteger icmcId) {
		LOG.info("Machine Software data Fetch all data");
		JPAQuery jpaQuery = getFromQueryForMAchineSoftwareUpdate();
		jpaQuery.where(QMachineSoftwareUpdation.machineSoftwareUpdation.icmcId.eq(icmcId));
		List<MachineSoftwareUpdation> listMachine = jpaQuery.list(QMachineSoftwareUpdation.machineSoftwareUpdation);
		return listMachine;
	}

	@Override
	public boolean insertMachineSoftware(Machine machine) {
		em.merge(machine);
		return true;
	}

	@Override
	public MachineSoftwareUpdation machineSoftwareRecordForModify(Long id) {
		MachineSoftwareUpdation machineUpdate = em.find(MachineSoftwareUpdation.class, id);
		return machineUpdate;
	}

	@Override
	public boolean updateMachineSoftwareUpdation(MachineSoftwareUpdation machine) {
		em.merge(machine);
		return true;
	}

	private JPAQuery getFromQueryForZoneMaster() {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QZoneMaster.zoneMaster);
		return jpaQuery;
	}

	@Override
	public List<String> getListZoneMaster() {
		LOG.info("Machine ZoneMaster Fetch all data");
		JPAQuery jpaQuery = getFromQueryForZoneMaster();
		List<String> listMachine = jpaQuery.list(QZoneMaster.zoneMaster.region);
		return listMachine;
	}

	private JPAQuery getFromQueryForMachineNumber() {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QMachine.machine);
		return jpaQuery;
	}

	@Override
	public List<Machine> getMachineNumber(BigInteger icmcId) {
		JPAQuery jpaQuery = getFromQueryForMachineNumber();
		jpaQuery.where(QMachine.machine.icmcId.eq(icmcId));
		jpaQuery.orderBy(QMachine.machine.machineNo.asc());
		List<Machine> machineList = jpaQuery.list(QMachine.machine);
		return machineList;
	}

	@Override
	public long updateMachineSoftwareUpdation(Machine machine) {
		QMachine qMachine = QMachine.machine;
		long count = new JPAUpdateClause(em, qMachine).where(QMachine.machine.id.eq(machine.getId()))
				.set(QMachine.machine.pmDate, machine.getPmDate()).set(QMachine.machine.sap, machine.getSap())
				.set(QMachine.machine.map, machine.getMap()).set(QMachine.machine.osap, machine.getOsap()).execute();
		return count;
	}

	@Override
	public List<MachineDowntimeUpdation> getListmachineDownTime(BigInteger icmcId, Calendar sDate, Calendar eDate) {
		JPAQuery jpaQuery = getFromQueryForMAchineDownTimeUpdate();
		jpaQuery.where(QMachineDowntimeUpdation.machineDowntimeUpdation.icmcId.eq(icmcId)
				.and(QMachineDowntimeUpdation.machineDowntimeUpdation.insertTime.between(sDate, eDate)));
		List<MachineDowntimeUpdation> machineList = jpaQuery.list(QMachineDowntimeUpdation.machineDowntimeUpdation);
		return machineList;
	}

}
