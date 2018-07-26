/*******************************************************************************
 * /* Copyright (C) Indicsoft Technologies Pvt Ltd
 * * All Rights Reserved.
 *******************************************************************************/
package com.chest.currency.jpa.dao;

import java.math.BigInteger;
import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Repository;
import org.supercsv.cellprocessor.ParseInt;

import com.chest.currency.controller.ParseBigInteger;
import com.chest.currency.entity.model.BinTransaction;
import com.chest.currency.entity.model.BoxMaster;
import com.chest.currency.entity.model.BranchReceipt;
import com.chest.currency.entity.model.CoinsSequence;
import com.chest.currency.entity.model.QBinMaster;
import com.chest.currency.entity.model.QBinTransaction;
import com.chest.currency.entity.model.QBinTransactionBOD;
import com.chest.currency.entity.model.QBoxMaster;
import com.chest.currency.entity.model.QBranch;
import com.chest.currency.entity.model.QCoinsSequence;
import com.chest.currency.entity.model.QOtherBank;
import com.chest.currency.entity.model.QOtherBankAllocation;
import com.chest.currency.entity.model.QRbiMaster;
import com.chest.currency.entity.model.RbiMaster;
import com.chest.currency.entity.model.User;
import com.chest.currency.enums.BinCategoryType;
import com.chest.currency.enums.CurrencyType;
import com.chest.currency.enums.OtherStatus;
import com.chest.currency.exception.BaseGuiException;
import com.mysema.query.jpa.JPQLQuery;
import com.mysema.query.jpa.impl.JPADeleteClause;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.jpa.impl.JPAUpdateClause;
import com.chest.currency.entity.model.QBinMaster;

@Repository
public class BinTransactionJpaDaoImpl implements BinTransactionJpaDao {

	@PersistenceContext
	private  EntityManager em;
	
	@Override
	public boolean UploadBinTransaction(List<BinTransaction> binList,BinTransaction binTransaction,BigInteger icmcId) {
		for(BinTransaction binTemp : binList){
			em.persist(binTemp);
			if(binTemp.getBinCategoryType().equals(BinCategoryType.BIN)){
			long isUpdate=	this.updateBinMaster(icmcId, binTemp);
			if(isUpdate==0){
				throw new BaseGuiException(binTemp.getBinNumber() +" Bin is not proper defined in Bin Master or Bin Transaction,Please first define");
			}
			}
			else if (binTemp.getBinCategoryType().equals(BinCategoryType.BOX)) {
				long isUpdate= this.updateBoxMaster(icmcId, binTemp);
				if(isUpdate==0){
					throw new BaseGuiException(binTemp.getBinNumber() +" Box is not proper defined in Box Master or Bin Transaction,please first define");
				}
			}
			else if(binTemp.getBinCategoryType().equals(BinCategoryType.BAG)){
				boolean isUpdate=this.insertCoinsSequence(icmcId, binTemp);
				if(!isUpdate){
					throw new BaseGuiException("Problem in saving Coin Sequence");
				}
			}
			
		}
		return true;
	}
	
	private  long updateBinMaster(BigInteger icmcId, BinTransaction binTemp){
		QBinMaster qBinMaster = QBinMaster.binMaster;
		long count = new JPAUpdateClause(em, qBinMaster)
				.where(QBinMaster.binMaster.icmcId.eq(icmcId)
				.and(QBinMaster.binMaster.binNumber.eq(binTemp.getBinNumber())))
				.set(QBinMaster.binMaster.isAllocated, 1).execute();
		return count;
		
	}
	
	private long updateBoxMaster(BigInteger icmcId, BinTransaction binTemp){
		QBoxMaster qBoxMaster = QBoxMaster.boxMaster;
		long count = new JPAUpdateClause(em, qBoxMaster)
				.where(QBoxMaster.boxMaster.icmcId.eq(icmcId)
				.and(QBoxMaster.boxMaster.boxName.eq(binTemp.getBinNumber())))
				.set(QBoxMaster.boxMaster.isAllocated, 1).execute();
		return count;
		
	}
	
	private boolean insertCoinsSequence(BigInteger icmcId, BinTransaction binTemp){
		CoinsSequence coinsSequence=new CoinsSequence();
		coinsSequence.setIcmcId(icmcId);
		coinsSequence.setSequence(binTemp.getReceiveBundle().intValueExact());
		coinsSequence.setDenomination(binTemp.getDenomination());
		coinsSequence.setInsertTime(Calendar.getInstance());
		coinsSequence.setUpdateTime(Calendar.getInstance());
		em.persist(coinsSequence);
		return true;
	}
	
	@Override
	public BoxMaster isValidBox(BigInteger icmcId, String boxname) {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QBoxMaster.boxMaster);
		jpaQuery.where(QBoxMaster.boxMaster.icmcId.eq(icmcId)
		.and(QBoxMaster.boxMaster.boxName.equalsIgnoreCase(boxname)));
		BoxMaster dbBoxName=jpaQuery.singleResult(QBoxMaster.boxMaster);
		return dbBoxName;
	}

	@Override
	public boolean UploadBoxMaster(List<BoxMaster> boxlist,BoxMaster boxmaster) {
		for(BoxMaster boxTemp : boxlist){
			em.persist(boxTemp);
		}
		return true;
	}

	@Override
	public BinTransaction isValidBin(BigInteger icmcId, String binNumber) {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QBinTransaction.binTransaction);
		jpaQuery.where(QBinTransaction.binTransaction.icmcId.eq(icmcId).and(QBinTransaction.binTransaction.binNumber.equalsIgnoreCase(binNumber)));
		BinTransaction dbBinName=jpaQuery.singleResult(QBinTransaction.binTransaction);
		return dbBinName;
	}

	@Override
	public boolean uploadBranchReceipt(List<BranchReceipt> branchReceiptList, BranchReceipt branchReceipt) {
		new JPADeleteClause(em, QBinTransactionBOD.binTransactionBOD).where(QBinTransactionBOD.binTransactionBOD.icmcId.eq(branchReceipt.getIcmcId())).execute();
		for(BranchReceipt br : branchReceiptList){
			BinTransaction isAvailable=this.binAvailability(br);
			if(isAvailable==null){
				throw new BaseGuiException(br.getBin()+","+br.getDenomination()+","+br.getCashSource()+","+br.getCurrencyType()+","+br.getBinCategoryType()+","+"fromate is not match with Bin Transaction");
			}
			em.persist(br);
		}
		return true;
	}

	@Override
	public List<BinTransaction> getBinTransaction(BigInteger icmcId) {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QBinTransaction.binTransaction);
		jpaQuery.where(QBinTransaction.binTransaction.icmcId.eq(icmcId)
				.and(QBinTransaction.binTransaction.binType.eq(CurrencyType.UNPROCESS)));
		List<BinTransaction> binTransaction = jpaQuery.list(QBinTransaction.binTransaction);
		return binTransaction;
	}
 
	private BinTransaction binAvailability(BranchReceipt brReceipt){
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QBinTransaction.binTransaction);
		jpaQuery.where(QBinTransaction.binTransaction.icmcId.eq(brReceipt.getIcmcId())
				.and(QBinTransaction.binTransaction.binNumber.eq(brReceipt.getBin()))
				.and(QBinTransaction.binTransaction.denomination.eq(brReceipt.getDenomination()))
				.and(QBinTransaction.binTransaction.receiveBundle.eq(brReceipt.getBundle()))
				.and(QBinTransaction.binTransaction.binType.eq(brReceipt.getCurrencyType()))
				.and(QBinTransaction.binTransaction.cashSource.eq(brReceipt.getCashSource()))
				.and(QBinTransaction.binTransaction.binCategoryType.eq(brReceipt.getBinCategoryType())));
		BinTransaction binTransaction = jpaQuery.singleResult(QBinTransaction.binTransaction);
		return binTransaction;
	}
}
