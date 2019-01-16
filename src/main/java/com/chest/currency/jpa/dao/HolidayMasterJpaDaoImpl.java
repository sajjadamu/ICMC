package com.chest.currency.jpa.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.chest.currency.entity.model.HolidayMaster;
import com.chest.currency.entity.model.QHolidayMaster;
import com.chest.currency.enums.State;
import com.mysema.query.jpa.impl.JPAQuery;

@Repository
public class HolidayMasterJpaDaoImpl implements HolidayMasterJpaDao {
	
	private static final Logger LOG = LoggerFactory.getLogger(HolidayMasterJpaDaoImpl.class);
	
	@PersistenceContext
	private EntityManager em;

	@Override
	public List<HolidayMaster> getHolidayList(String state) {
		LOG.info("Fetching Holiday List");
		JPAQuery jpaQuery = getFromQueryForHolidayMaster();
		
		if(State.ANDAMAN_AND_NICOBAR_ISLANDS.name().equalsIgnoreCase(state)){
			jpaQuery.where((QHolidayMaster.holidayMaster.andamanandnicobarislands.eq("Y")));
		}else if(State.ANDHRA_PRADESH.name().equalsIgnoreCase(state)){
			jpaQuery.where((QHolidayMaster.holidayMaster.andhrapradesh.eq("Y")));
		}else if(State.ARUNACHAL_PRADESH.name().equalsIgnoreCase(state)){
			jpaQuery.where((QHolidayMaster.holidayMaster.arunachalPradesh.eq("Y")));
		}else if(State.ASSAM.name().equalsIgnoreCase(state)){
			jpaQuery.where((QHolidayMaster.holidayMaster.assam.eq("Y")));
		}else if(State.BIHAR.name().equalsIgnoreCase(state)){
			jpaQuery.where((QHolidayMaster.holidayMaster.bihar.eq("Y")));
		}else if(State.CHANDIGARH.name().equalsIgnoreCase(state)){
			jpaQuery.where((QHolidayMaster.holidayMaster.chandigarh.eq("Y")));
		}else if(State.CHHATTISGARH.name().equalsIgnoreCase(state)){
			jpaQuery.where((QHolidayMaster.holidayMaster.chhattisgarh.eq("Y")));
		}else if(State.DAMAN_AND_DIU.name().equalsIgnoreCase(state)){
			jpaQuery.where((QHolidayMaster.holidayMaster.damanAndDiu.eq("Y")));
		}else if(State.DELHI.name().equalsIgnoreCase(state)){
			jpaQuery.where((QHolidayMaster.holidayMaster.delhi.eq("Y")));
		}else if(State.GOA.name().equalsIgnoreCase(state)){
			jpaQuery.where((QHolidayMaster.holidayMaster.goa.eq("Y")));
		}else if(State.GUJARAT.name().equalsIgnoreCase(state)){
			jpaQuery.where((QHolidayMaster.holidayMaster.gujarat.eq("Y")));
		}else if(State.HIMACHAL_PRADESH.name().equalsIgnoreCase(state)){
			jpaQuery.where((QHolidayMaster.holidayMaster.himachalpradesh.eq("Y")));
		}else if(State.HARYANA.name().equalsIgnoreCase(state)){
			jpaQuery.where((QHolidayMaster.holidayMaster.haryana.eq("Y")));
		}else if(State.JHARKHAND.name().equalsIgnoreCase(state)){
			jpaQuery.where((QHolidayMaster.holidayMaster.jharkhand.eq("Y")));
		}else if(State.JAMMU_AND_KASHMIR.name().equalsIgnoreCase(state)){
			jpaQuery.where((QHolidayMaster.holidayMaster.jammuAndKashmir.eq("Y")));
		}else if(State.KARNATAKA.name().equalsIgnoreCase(state)){
			jpaQuery.where((QHolidayMaster.holidayMaster.karnataka.eq("Y")));
		}else if(State.KERALA.name().equalsIgnoreCase(state)){
			jpaQuery.where((QHolidayMaster.holidayMaster.kerala.eq("Y")));
		}else if(State.MAHARASHTRA.name().equalsIgnoreCase(state)){
			jpaQuery.where((QHolidayMaster.holidayMaster.maharashtra.eq("Y")));
		}else if(State.MEGHALAYA.name().equalsIgnoreCase(state)){
			jpaQuery.where((QHolidayMaster.holidayMaster.meghalaya.eq("Y")));
		}else if(State.MANIPUR.name().equalsIgnoreCase(state)){
			jpaQuery.where((QHolidayMaster.holidayMaster.manipur.eq("Y")));
		}else if(State.MADHYA_PRADESH.name().equalsIgnoreCase(state)){
			jpaQuery.where((QHolidayMaster.holidayMaster.madhyaPradesh.eq("Y")));
		}else if(State.MIZORAM.name().equalsIgnoreCase(state)){
			jpaQuery.where((QHolidayMaster.holidayMaster.mizoram.eq("Y")));
		}else if(State.NAGALAND.name().equalsIgnoreCase(state)){
			jpaQuery.where((QHolidayMaster.holidayMaster.nagaland.eq("Y")));
		}else if(State.ORISSA.name().equalsIgnoreCase(state)){
			jpaQuery.where((QHolidayMaster.holidayMaster.orissa.eq("Y")));
		}else if(State.PUNJAB.name().equalsIgnoreCase(state)){
			jpaQuery.where((QHolidayMaster.holidayMaster.punjab.eq("Y")));
		}else if(State.PONDICHERRY.name().equalsIgnoreCase(state)){
			jpaQuery.where((QHolidayMaster.holidayMaster.pondichhery.eq("Y")));
		}else if(State.RAJASTHAN.name().equalsIgnoreCase(state)){
			jpaQuery.where((QHolidayMaster.holidayMaster.rajasthan.eq("Y")));
		}else if(State.SIKKIM.name().equalsIgnoreCase(state)){
			jpaQuery.where((QHolidayMaster.holidayMaster.sikkim.eq("Y")));
		}else if(State.TAMIL_NADU.name().equalsIgnoreCase(state)){
			jpaQuery.where((QHolidayMaster.holidayMaster.tamilNadu.eq("Y")));
		}else if(State.TRIPURA.name().equalsIgnoreCase(state)){
			jpaQuery.where((QHolidayMaster.holidayMaster.tripura.eq("Y")));
		}else if(State.UP.name().equalsIgnoreCase(state)){
			jpaQuery.where((QHolidayMaster.holidayMaster.uttarPradesh.eq("Y")));
		}else if(State.UTTARANCHAL.name().equalsIgnoreCase(state)){
			jpaQuery.where((QHolidayMaster.holidayMaster.uttaranchal.eq("Y")));
		}else if(State.WEST_BENGAL.name().equalsIgnoreCase(state)){
			jpaQuery.where((QHolidayMaster.holidayMaster.westBengal.eq("Y")));
		}else if(State.DADRA_AND_NAGAR_HAVELI.name().equalsIgnoreCase(state)){
			jpaQuery.where((QHolidayMaster.holidayMaster.dadraandnagarhaveli.eq("Y")));
		}else if(State.LAKSHADWEEP.name().equalsIgnoreCase(state)){
			jpaQuery.where((QHolidayMaster.holidayMaster.lakshadweep.eq("Y")));
		}else if(State.TELANGANA.name().equalsIgnoreCase(state)){
			jpaQuery.where((QHolidayMaster.holidayMaster.telangana.eq("Y")));
		}
		
		List<HolidayMaster> holidayList = jpaQuery.list(QHolidayMaster.holidayMaster);
		return holidayList;
	}
	
	private JPAQuery getFromQueryForHolidayMaster(){
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QHolidayMaster.holidayMaster);
		return jpaQuery;
	}

	@Override
	public boolean bulkHolidayRequest(List<HolidayMaster> bulkHolidayList, HolidayMaster holiday) {
		for(HolidayMaster holidayTemp : bulkHolidayList){
			holidayTemp.setInsertBy(holiday.getInsertBy());
			holidayTemp.setUpdateBy(holiday.getUpdateBy());
			holidayTemp.setInsertTime(holiday.getInsertTime());
			holidayTemp.setUpdateTime(holiday.getUpdateTime());
			em.persist(holidayTemp);
		}
		return true;
	}

}
