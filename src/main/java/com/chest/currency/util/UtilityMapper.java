package com.chest.currency.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.ui.ModelMap;

import com.chest.currency.entity.model.BankReceipt;
import com.chest.currency.entity.model.BranchReceipt;
import com.chest.currency.entity.model.CRAAllocation;
import com.chest.currency.entity.model.DSB;
import com.chest.currency.entity.model.DiversionIRV;
import com.chest.currency.entity.model.DiversionORVAllocation;
import com.chest.currency.entity.model.FlatSummary;
import com.chest.currency.entity.model.FreshFromRBI;
import com.chest.currency.entity.model.GrandSummary;
import com.chest.currency.entity.model.MachineAllocation;
import com.chest.currency.entity.model.Mutilated;
import com.chest.currency.entity.model.OtherBankAllocation;
import com.chest.currency.entity.model.RegionSummary;
import com.chest.currency.entity.model.SoiledRemittanceAllocation;
import com.chest.currency.entity.model.User;
import com.chest.currency.enums.CashSource;
import com.chest.currency.enums.CashType;
import com.chest.currency.enums.CurrencyType;
import com.chest.currency.enums.OtherStatus;
import com.chest.currency.enums.Zone;
import com.chest.currency.exception.BaseGuiException;
import com.chest.currency.jpa.persistence.converter.CurrencyFormatter;
import com.mysema.query.Tuple;

public class UtilityMapper {

	public static void mapTupleToGrandSummary(GrandSummary grandSummary, List<Tuple> grandSummaryList) {
		BigDecimal totalValue = BigDecimal.ZERO;
		for (Tuple tuple : grandSummaryList) {
			totalValue = totalValue.add(tuple.get(0, BigDecimal.class));
			if (tuple.get(1, Enum.class).equals(CashType.NOTES)) {
				grandSummary.setTotalNotes(tuple.get(0, BigDecimal.class));
			}
			if (tuple.get(1, Enum.class).equals(CashType.COINS)) {
				grandSummary.setTotalCoins(tuple.get(0, BigDecimal.class));
			}
		}
		grandSummary.setTotal(totalValue);
	}

	public static void mapTupleToZoneWiseGrandSummary(GrandSummary grandSummary, List<Tuple> zoneWiseGrandSummaryList) {

		BigDecimal totalNorth = BigDecimal.ZERO;
		BigDecimal totalSouth = BigDecimal.ZERO;
		BigDecimal totalEast = BigDecimal.ZERO;
		BigDecimal totalWest = BigDecimal.ZERO;

		for (Tuple tuple : zoneWiseGrandSummaryList) {
			if (tuple.get(0, Enum.class).equals(Zone.NORTH)) {
				totalNorth = totalNorth.add(tuple.get(1, BigDecimal.class));
				if (tuple.get(2, Enum.class).equals(CashType.NOTES)) {
					grandSummary.setTotalNorthNotes(tuple.get(1, BigDecimal.class));
				}
				if (tuple.get(2, Enum.class).equals(CashType.COINS)) {
					grandSummary.setTotalNorthCoins(tuple.get(1, BigDecimal.class));
				}
			}
			if (tuple.get(0, Enum.class).equals(Zone.SOUTH)) {
				totalSouth = totalSouth.add(tuple.get(1, BigDecimal.class));
				if (tuple.get(2, Enum.class).equals(CashType.NOTES)) {
					grandSummary.setTotalSouthNotes(tuple.get(1, BigDecimal.class));
				}
				if (tuple.get(2, Enum.class).equals(CashType.COINS)) {
					grandSummary.setTotalSouthCoins(tuple.get(1, BigDecimal.class));
				}
			}
			if (tuple.get(0, Enum.class).equals(Zone.EAST_AND_AP)) {
				totalEast = totalEast.add(tuple.get(1, BigDecimal.class));
				if (tuple.get(2, Enum.class).equals(CashType.NOTES)) {
					grandSummary.setTotalEastNotes(tuple.get(1, BigDecimal.class));
				}
				if (tuple.get(2, Enum.class).equals(CashType.COINS)) {
					grandSummary.setTotalEastCoins(tuple.get(1, BigDecimal.class));
				}
			}
			if (tuple.get(0, Enum.class).equals(Zone.WEST)) {
				totalWest = totalWest.add(tuple.get(1, BigDecimal.class));
				if (tuple.get(2, Enum.class).equals(CashType.NOTES)) {
					grandSummary.setTotalWestNotes(tuple.get(1, BigDecimal.class));
				}
				if (tuple.get(2, Enum.class).equals(CashType.COINS)) {
					grandSummary.setTotalWestCoins(tuple.get(1, BigDecimal.class));
				}
			}
		}
		grandSummary.setTotalNorth(totalNorth);
		grandSummary.setTotalSouth(totalSouth);
		grandSummary.setTotalEast(totalEast);
		grandSummary.setTotalWest(totalWest);
	}

	public static List<FlatSummary> mapTupleToFlatZoneSummary(List<Tuple> flatZoneSummaryTupleList) {

		List<FlatSummary> flatZoneSummaryList = new ArrayList<>();
		FlatSummary flatZoneSummaryTotal = new FlatSummary();
		flatZoneSummaryList.add(getFlatZoneSummary(flatZoneSummaryTotal, flatZoneSummaryTupleList, Zone.NORTH));
		flatZoneSummaryList.add(getFlatZoneSummary(flatZoneSummaryTotal, flatZoneSummaryTupleList, Zone.SOUTH));
		flatZoneSummaryList.add(getFlatZoneSummary(flatZoneSummaryTotal, flatZoneSummaryTupleList, Zone.EAST_AND_AP));
		flatZoneSummaryList.add(getFlatZoneSummary(flatZoneSummaryTotal, flatZoneSummaryTupleList, Zone.WEST));
		flatZoneSummaryList.add(flatZoneSummaryTotal);
		return flatZoneSummaryList;
	}

	private static FlatSummary getFlatZoneSummary(FlatSummary flatZoneSummaryTotal,
			List<Tuple> flatZoneSummaryTupleList, Zone zone) {
		List<Tuple> tupleList = flatZoneSummaryTupleList.stream().filter(tuple -> tuple.get(0, Enum.class).equals(zone))
				.collect(Collectors.toList());

		FlatSummary flatZoneSummary = new FlatSummary();
		flatZoneSummary.setZone(zone);
		for (Tuple tuple : tupleList) {

			if (tuple.get(2, Enum.class).equals(CurrencyType.ATM)) {
				flatZoneSummary.setAtm(tuple.get(1, BigDecimal.class));
				flatZoneSummaryTotal.setAtm(flatZoneSummaryTotal.getAtm().add(flatZoneSummary.getAtm()));
			}
			if (tuple.get(2, Enum.class).equals(CurrencyType.FRESH)) {
				flatZoneSummary.setFresh(tuple.get(1, BigDecimal.class));
				flatZoneSummaryTotal.setFresh(flatZoneSummaryTotal.getFresh().add(flatZoneSummary.getFresh()));
			}
			if (tuple.get(2, Enum.class).equals(CurrencyType.ISSUABLE)) {
				flatZoneSummary.setIssuable(tuple.get(1, BigDecimal.class));
				flatZoneSummaryTotal.setIssuable(flatZoneSummaryTotal.getIssuable().add(flatZoneSummary.getIssuable()));
			}
			if (tuple.get(2, Enum.class).equals(CurrencyType.UNPROCESS)) {
				flatZoneSummary.setUnprocess(tuple.get(1, BigDecimal.class));
				flatZoneSummaryTotal
						.setUnprocess(flatZoneSummaryTotal.getUnprocess().add(flatZoneSummary.getUnprocess()));
			}
			if (tuple.get(2, Enum.class).equals(CurrencyType.SOILED)) {
				flatZoneSummary.setSoiled(tuple.get(1, BigDecimal.class));
				flatZoneSummaryTotal.setSoiled(flatZoneSummaryTotal.getSoiled().add(flatZoneSummary.getSoiled()));
			}
			if (tuple.get(2, Enum.class).equals(CurrencyType.COINS)) {
				flatZoneSummary.setCoins(tuple.get(1, BigDecimal.class));
				flatZoneSummaryTotal.setCoins(flatZoneSummaryTotal.getCoins().add(flatZoneSummary.getCoins()));
			}
		}
		flatZoneSummary.setTotal(getTotal(flatZoneSummary));
		flatZoneSummaryTotal.setTotal(flatZoneSummaryTotal.getTotal().add(flatZoneSummary.getTotal()));
		return flatZoneSummary;
	}

	private static BigDecimal getTotal(FlatSummary flatZoneSummary) {
		BigDecimal total = flatZoneSummary.getAtm();
		total = total.add(flatZoneSummary.getCoins());
		total = total.add(flatZoneSummary.getFresh());
		total = total.add(flatZoneSummary.getIssuable());
		total = total.add(flatZoneSummary.getSoiled());
		total = total.add(flatZoneSummary.getUnprocess());
		return total;
	}

	public static FlatSummary getFlatRegionSummary(List<RegionSummary> regionSummaryList) {

		FlatSummary flatZoneSummary = new FlatSummary();

		for (RegionSummary regionSummary : regionSummaryList) {

			if (regionSummary.getBinType().equals(CurrencyType.ATM)) {
				flatZoneSummary.setAtm(regionSummary.getTotalValue());
			}
			if (regionSummary.getBinType().equals(CurrencyType.FRESH)) {
				flatZoneSummary.setFresh(regionSummary.getTotalValue());
			}
			if (regionSummary.getBinType().equals(CurrencyType.ISSUABLE)) {
				flatZoneSummary.setIssuable(regionSummary.getTotalValue());
			}
			if (regionSummary.getBinType().equals(CurrencyType.UNPROCESS)) {
				flatZoneSummary.setUnprocess(regionSummary.getTotalValue());
			}
			if (regionSummary.getBinType().equals(CurrencyType.SOILED)) {
				flatZoneSummary.setSoiled(regionSummary.getTotalValue());
			}
			if (regionSummary.getBinType().equals(CurrencyType.COINS)) {
				flatZoneSummary.setCoins(regionSummary.getTotalValue());
			}
		}
		flatZoneSummary.setTotal(getTotal(flatZoneSummary));
		return flatZoneSummary;
	}

	public static List<MachineAllocation> mapTuppleToAggregatedBundleToBeReturnedToVault(List<Tuple> tupleList) {

		List<MachineAllocation> machineAllocationList = new ArrayList<>();

		for (Tuple tuple : tupleList) {
			MachineAllocation machineAllocation = new MachineAllocation();
			machineAllocation.setDenomination(tuple.get(0, Integer.class));
			machineAllocation.setCashSource(tuple.get(1, CashSource.class));
			machineAllocation.setPendingBundle(tuple.get(2, BigDecimal.class));
			machineAllocationList.add(machineAllocation);
		}
		return machineAllocationList;
	}

	public static List<MachineAllocation> getEligibleBundleListForMachineAllocationDuringReturnBackToVault(
			List<MachineAllocation> bundleList, BigDecimal issuedBundle, boolean fromProcessingRoom, User user) {
		List<MachineAllocation> eligibleBundleList = new ArrayList<>();
		Calendar now = Calendar.getInstance();

		BigDecimal availableBundle = BigDecimal.ZERO;
		for (MachineAllocation machineAllocation : bundleList) {
			availableBundle = availableBundle.add(machineAllocation.getPendingBundle());
		}

		if (availableBundle.compareTo(issuedBundle) >= 0) {
			for (MachineAllocation machineAllocation : bundleList) {
				BigDecimal tempPendingBundle = machineAllocation.getPendingBundle();
				if (fromProcessingRoom) {
					machineAllocation.setReturnBackToVault(tempPendingBundle);
				}
				if (issuedBundle.compareTo(BigDecimal.ZERO) > 0) {
					if (machineAllocation.getPendingBundle().compareTo(issuedBundle) >= 0) {
						issuedBundle = machineAllocation.getPendingBundle().subtract(issuedBundle);
						machineAllocation.setPendingBundle(issuedBundle);
						if (machineAllocation.getPendingBundle().compareTo(BigDecimal.ZERO) == 0) {
							machineAllocation.setStatus(OtherStatus.PROCESSED);
						}
						machineAllocation.setUpdateBy(user.getId());
						machineAllocation.setUpdateTime(now);
						eligibleBundleList.add(machineAllocation);
						break;
					} else if (machineAllocation.getPendingBundle().compareTo(BigDecimal.ZERO) > 0) {
						issuedBundle = issuedBundle.subtract(machineAllocation.getPendingBundle());
						machineAllocation.setPendingBundle(BigDecimal.ZERO);
						machineAllocation.setStatus(OtherStatus.PROCESSED);
						machineAllocation.setUpdateBy(user.getId());
						machineAllocation.setUpdateTime(now);
						eligibleBundleList.add(machineAllocation);
					}
				}
			}
		}
		if (availableBundle.compareTo(issuedBundle) >= 0) {
			return eligibleBundleList;
		} else {
			throw new BaseGuiException(
					"Required Bundle is not available, TotalAvailableBundle is:" + availableBundle.toPlainString());
		}

	}

	public static Map<String, BranchReceipt> mapTupleToBranchReceipt(List<Tuple> branchDepositTupleList) {
		//List<BranchReceipt> branchReceiptList = new ArrayList<>();
		// BranchReceipt branchReceipt = new BranchReceipt(true);

		Map<String, BranchReceipt> mapList = new LinkedHashMap<>();

		for (Tuple tuple : branchDepositTupleList) {
			mapList.put(tuple.get(0, String.class) +":"+ tuple.get(1, String.class), new BranchReceipt(true));
		}
		BigDecimal denom2000 = BigDecimal.ZERO;
		for (Tuple tuple : branchDepositTupleList) {
			// mapList.put(tuple.get(0, String.class), new BranchReceipt(true));
			BranchReceipt branchReceipt = mapList.get(tuple.get(0, String.class) +":"+ tuple.get(1, String.class));

			branchReceipt.setSolId(tuple.get(1, String.class));
			branchReceipt.setBranch(tuple.get(2, String.class));
			if (tuple.get(3, Integer.class).equals(2000)) {
				denom2000 = denom2000.add(tuple.get(4, BigDecimal.class));
				branchReceipt.setDenom2000Pieces(tuple.get(4, BigDecimal.class));
				//branchReceipt.setDenom2000Pieces(denom2000);
				
			}
			if (tuple.get(3, Integer.class).equals(1000)) {
				branchReceipt.setDenom1000Pieces(tuple.get(4, BigDecimal.class));
			}
			
			if (tuple.get(3, Integer.class).equals(500)) {
				branchReceipt.setDenom500Pieces(tuple.get(4, BigDecimal.class));
			}
			if (tuple.get(3, Integer.class).equals(200)) {
				branchReceipt.setDenom200Pieces(tuple.get(4, BigDecimal.class));
			}
			if (tuple.get(3, Integer.class).equals(100)) {
				branchReceipt.setDenom100Pieces(tuple.get(4, BigDecimal.class));
			}
			if (tuple.get(3, Integer.class).equals(50)) {
				branchReceipt.setDenom50Pieces(tuple.get(4, BigDecimal.class));
			}
			if (tuple.get(3, Integer.class).equals(20)) {
				branchReceipt.setDenom20Pieces(tuple.get(4, BigDecimal.class));
			}
			if (tuple.get(3, Integer.class).equals(10)) {
				branchReceipt.setDenom10Pieces(tuple.get(4, BigDecimal.class));
			}
			if (tuple.get(3, Integer.class).equals(5)) {
				branchReceipt.setDenom5Pieces(tuple.get(4, BigDecimal.class));
			}
			if (tuple.get(3, Integer.class).equals(2)) {
				branchReceipt.setDenom2Pieces(tuple.get(4, BigDecimal.class));
			}
			if (tuple.get(3, Integer.class).equals(1)) {
				branchReceipt.setDenom1Pieces(tuple.get(4, BigDecimal.class));
			}

			branchReceipt.setTotalInPieces(branchReceipt.getDenom2000Pieces().add(branchReceipt.getDenom1000Pieces()
					.add(branchReceipt.getDenom500Pieces().add(branchReceipt.getDenom200Pieces().add(branchReceipt.getDenom100Pieces()
							.add(branchReceipt.getDenom50Pieces().add(branchReceipt.getDenom20Pieces()
									.add(branchReceipt.getDenom10Pieces().add(branchReceipt.getDenom5Pieces().add(branchReceipt.getDenom2Pieces()))))))))));

			branchReceipt.setTotalValueOfBankNotes(branchReceipt.getDenom2000Pieces().multiply(BigDecimal.valueOf(2000))
					.add(branchReceipt.getDenom1000Pieces().multiply(BigDecimal.valueOf(1000)))
					.add(branchReceipt.getDenom500Pieces().multiply(BigDecimal.valueOf(500)))
					.add(branchReceipt.getDenom200Pieces().multiply(BigDecimal.valueOf(200)))
					.add(branchReceipt.getDenom100Pieces().multiply(BigDecimal.valueOf(100)))
					.add(branchReceipt.getDenom50Pieces().multiply(BigDecimal.valueOf(50)))
					.add(branchReceipt.getDenom20Pieces().multiply(BigDecimal.valueOf(20)))
					.add(branchReceipt.getDenom10Pieces().multiply(BigDecimal.valueOf(10)))
					.add(branchReceipt.getDenom5Pieces().multiply(BigDecimal.valueOf(5)))
					.add(branchReceipt.getDenom2Pieces().multiply(BigDecimal.valueOf(2))));

		//	 branchReceiptList.add(branchReceipt);

		}
		return mapList;
	}
	
    

	public static BigDecimal getTotalValueForSingleORVVoucher(List<Tuple> summaryList) {
		BigDecimal totalValue = BigDecimal.ZERO;
		for (Tuple tuple : summaryList) {
			totalValue =BigDecimal.valueOf(2000)
					.multiply(tuple.get(12, BigDecimal.class).multiply(BigDecimal.valueOf(1000)))
					.add(BigDecimal.valueOf(1000)
							.multiply(tuple.get(3, BigDecimal.class).multiply(BigDecimal.valueOf(1000))))
					.add(BigDecimal.valueOf(500)
							.multiply(tuple.get(4, BigDecimal.class).multiply(BigDecimal.valueOf(1000))))
					.add(BigDecimal.valueOf(200)
							.multiply(tuple.get(17, BigDecimal.class).multiply(BigDecimal.valueOf(1000))))
					.add(BigDecimal.valueOf(100)
							.multiply(tuple.get(5, BigDecimal.class).multiply(BigDecimal.valueOf(1000))))
					.add(BigDecimal.valueOf(50)
							.multiply(tuple.get(6, BigDecimal.class).multiply(BigDecimal.valueOf(1000))))
					.add(BigDecimal.valueOf(20)
							.multiply(tuple.get(7, BigDecimal.class).multiply(BigDecimal.valueOf(1000))))
					.add(BigDecimal.valueOf(10)
							.multiply(tuple.get(8, BigDecimal.class).multiply(BigDecimal.valueOf(1000))))
					.add(BigDecimal.valueOf(5)
							.multiply(tuple.get(9, BigDecimal.class).multiply(BigDecimal.valueOf(1000))))
					.add(BigDecimal.valueOf(1)
							.multiply(tuple.get(10, BigDecimal.class).multiply(BigDecimal.valueOf(1000))))
					.add(BigDecimal.valueOf(1)
							.multiply(tuple.get(13, BigDecimal.class).multiply(BigDecimal.valueOf(2500))))
					.add(BigDecimal.valueOf(2)
							.multiply(tuple.get(14, BigDecimal.class).multiply(BigDecimal.valueOf(2500))))
					.add(BigDecimal.valueOf(5)
							.multiply(tuple.get(15, BigDecimal.class).multiply(BigDecimal.valueOf(2500))))
					.add(BigDecimal.valueOf(10)
							.multiply(tuple.get(16, BigDecimal.class).multiply(BigDecimal.valueOf(2000))));
		}
		return totalValue;
	}

	public static Map<String, DSB> mapTupleToDSBReceipt(List<Tuple> dsbDepositTupleList) {
		Map<String, DSB> mapList = new LinkedHashMap<>();

		for (Tuple tuple : dsbDepositTupleList) {
			mapList.put(tuple.get(0, Integer.class) + "" + tuple.get(1, Date.class), new DSB(true));
		}

		for (Tuple tuple : dsbDepositTupleList) {
			DSB dsb = mapList.get(tuple.get(0, Integer.class) + "" + tuple.get(1, Date.class));

			dsb.setReceiptSequence(tuple.get(0, Integer.class));
			dsb.setReceiptDate(tuple.get(1, Date.class));
			dsb.setName(tuple.get(2, String.class));
			dsb.setAccountNumber(tuple.get(3, String.class));
			if (tuple.get(4, Integer.class).equals(2000)) {
				dsb.setDenom2000Pieces(tuple.get(5, BigDecimal.class));
			}
			if (tuple.get(4, Integer.class).equals(1000)) {
				dsb.setDenom1000Pieces(tuple.get(5, BigDecimal.class));
			}
			
			if (tuple.get(4, Integer.class).equals(200)) {
				dsb.setDenom200Pieces(tuple.get(5, BigDecimal.class));
			}
			if (tuple.get(4, Integer.class).equals(500)) {
				dsb.setDenom500Pieces(tuple.get(5, BigDecimal.class));
			}
			if (tuple.get(4, Integer.class).equals(100)) {
				dsb.setDenom100Pieces(tuple.get(5, BigDecimal.class));
			}
			if (tuple.get(4, Integer.class).equals(50)) {
				dsb.setDenom50Pieces(tuple.get(5, BigDecimal.class));
			}
			if (tuple.get(4, Integer.class).equals(20)) {
				dsb.setDenom20Pieces(tuple.get(5, BigDecimal.class));
			}
			if (tuple.get(4, Integer.class).equals(10)) {
				dsb.setDenom10Pieces(tuple.get(5, BigDecimal.class));
			}
			if (tuple.get(4, Integer.class).equals(5)) {
				dsb.setDenom5Pieces(tuple.get(5, BigDecimal.class));
			}
			if (tuple.get(4, Integer.class).equals(2)) {
				dsb.setDenom2Pieces(tuple.get(5, BigDecimal.class));
			}
			if (tuple.get(4, Integer.class).equals(1)) {
				dsb.setDenom1Pieces(tuple.get(5, BigDecimal.class));
			}

			dsb.setTotalInPieces(dsb.getDenom2000Pieces().add(dsb.getDenom1000Pieces()
					.add(dsb.getDenom500Pieces().add(dsb.getDenom200Pieces().add(dsb.getDenom100Pieces().add(dsb.getDenom50Pieces()
							.add(dsb.getDenom20Pieces().add(dsb.getDenom10Pieces().add(dsb.getDenom5Pieces().add(dsb.getDenom2Pieces()))))))))));

			dsb.setTotalValueOfBankNotes(dsb.getDenom2000Pieces().multiply(BigDecimal.valueOf(2000))
					.add(dsb.getDenom1000Pieces().multiply(BigDecimal.valueOf(1000)))
					.add(dsb.getDenom500Pieces().multiply(BigDecimal.valueOf(500)))
					.add(dsb.getDenom200Pieces().multiply(BigDecimal.valueOf(200)))
					.add(dsb.getDenom100Pieces().multiply(BigDecimal.valueOf(100)))
					.add(dsb.getDenom50Pieces().multiply(BigDecimal.valueOf(50)))
					.add(dsb.getDenom20Pieces().multiply(BigDecimal.valueOf(20)))
					.add(dsb.getDenom10Pieces().multiply(BigDecimal.valueOf(10)))
					.add(dsb.getDenom5Pieces().multiply(BigDecimal.valueOf(5)))
					.add(dsb.getDenom2Pieces().multiply(BigDecimal.valueOf(2))));
		}
		return mapList;
	}

	public static Map<String, BankReceipt> mapTupleToBankReceipt(List<Tuple> bankDepositTupleList) {
		Map<String, BankReceipt> mapList = new LinkedHashMap<>();

		for (Tuple tuple : bankDepositTupleList) {
			mapList.put(tuple.get(1, String.class), new BankReceipt(true));
		}

		for (Tuple tuple : bankDepositTupleList) {
			BankReceipt bankReceipt = mapList.get(tuple.get(1, String.class));

			bankReceipt.setBankName(tuple.get(0, String.class));
			bankReceipt.setRtgsUTRNo(tuple.get(1, String.class));
			if (tuple.get(2, Integer.class).equals(2000)) {
				bankReceipt.setDenom2000Pieces(tuple.get(3, BigDecimal.class));
			}
			if (tuple.get(2, Integer.class).equals(1000)) {
				bankReceipt.setDenom1000Pieces(tuple.get(3, BigDecimal.class));
			}
			if (tuple.get(2, Integer.class).equals(500)) {
				bankReceipt.setDenom500Pieces(tuple.get(3, BigDecimal.class));
			}
			if (tuple.get(2, Integer.class).equals(200)) {
				bankReceipt.setDenom200Pieces(tuple.get(3, BigDecimal.class));
			}
			if (tuple.get(2, Integer.class).equals(100)) {
				bankReceipt.setDenom100Pieces(tuple.get(3, BigDecimal.class));
			}
			if (tuple.get(2, Integer.class).equals(50)) {
				bankReceipt.setDenom50Pieces(tuple.get(3, BigDecimal.class));
			}
			if (tuple.get(2, Integer.class).equals(20)) {
				bankReceipt.setDenom20Pieces(tuple.get(3, BigDecimal.class));
			}
			if (tuple.get(2, Integer.class).equals(10)) {
				bankReceipt.setDenom10Pieces(tuple.get(3, BigDecimal.class));
			}
			if (tuple.get(2, Integer.class).equals(5)) {
				bankReceipt.setDenom5Pieces(tuple.get(3, BigDecimal.class));
			}
			if (tuple.get(2, Integer.class).equals(2)) {
				bankReceipt.setDenom2Pieces(tuple.get(3, BigDecimal.class));
			}
			if (tuple.get(2, Integer.class).equals(1)) {
				bankReceipt.setDenom1Pieces(tuple.get(3, BigDecimal.class));
			}

			bankReceipt.setTotalInPieces(bankReceipt.getDenom2000Pieces()
					.add(bankReceipt.getDenom1000Pieces().add(bankReceipt.getDenom500Pieces().add(bankReceipt.getDenom200Pieces().add(bankReceipt
							.getDenom100Pieces().add(bankReceipt.getDenom50Pieces().add(bankReceipt.getDenom20Pieces()
									.add(bankReceipt.getDenom10Pieces().add(bankReceipt.getDenom5Pieces().add(bankReceipt.getDenom2Pieces()))))))))));

			bankReceipt.setTotalValueOfBankNotes(bankReceipt.getDenom2000Pieces().multiply(BigDecimal.valueOf(2000))
					.add(bankReceipt.getDenom1000Pieces().multiply(BigDecimal.valueOf(1000)))
					.add(bankReceipt.getDenom500Pieces().multiply(BigDecimal.valueOf(500)))
					.add(bankReceipt.getDenom200Pieces().multiply(BigDecimal.valueOf(200)))
					.add(bankReceipt.getDenom100Pieces().multiply(BigDecimal.valueOf(100)))
					.add(bankReceipt.getDenom50Pieces().multiply(BigDecimal.valueOf(50)))
					.add(bankReceipt.getDenom20Pieces().multiply(BigDecimal.valueOf(20)))
					.add(bankReceipt.getDenom10Pieces().multiply(BigDecimal.valueOf(10)))
					.add(bankReceipt.getDenom5Pieces().multiply(BigDecimal.valueOf(5)))
					.add(bankReceipt.getDenom2Pieces().multiply(BigDecimal.valueOf(2))));
		}
		return mapList;
	}

	public static ModelMap getSummarisedTotalForCashBookDepositReport(Map<String, BranchReceipt> branchDepositList,
			Map<String, DSB> dsbDepositList, Map<String, BankReceipt> bankDepositList,Map<String, DiversionIRV> dirvDepositList,Map<String, FreshFromRBI> freshFromRBIList, List<Tuple> ibitListValues) {
		ModelMap map = new ModelMap();

		BigDecimal denom1TotalPieces = BigDecimal.ZERO;
		BigDecimal denom2TotalPieces = BigDecimal.ZERO;
		BigDecimal denom5TotalPieces = BigDecimal.ZERO;
		BigDecimal denom10TotalPieces = BigDecimal.ZERO;
		BigDecimal denom20TotalPieces = BigDecimal.ZERO;
		BigDecimal denom50TotalPieces = BigDecimal.ZERO;
		BigDecimal denom100TotalPieces = BigDecimal.ZERO;
		BigDecimal denom200TotalPieces = BigDecimal.ZERO;
		BigDecimal denom500TotalPieces = BigDecimal.ZERO;
		BigDecimal denom1000TotalPieces = BigDecimal.ZERO;
		BigDecimal denom2000TotalPieces = BigDecimal.ZERO;
		BigDecimal totalInPieces = BigDecimal.ZERO;
		BigDecimal totalValueOfBankNotes = BigDecimal.ZERO;

		for (Map.Entry<String, BranchReceipt> entry : branchDepositList.entrySet()) {
			denom1TotalPieces = denom1TotalPieces.add(entry.getValue().getDenom1Pieces());
			denom2TotalPieces = denom2TotalPieces.add(entry.getValue().getDenom2Pieces());
			denom5TotalPieces = denom5TotalPieces.add(entry.getValue().getDenom5Pieces());
			denom10TotalPieces = denom10TotalPieces.add(entry.getValue().getDenom10Pieces());
			denom20TotalPieces = denom20TotalPieces.add(entry.getValue().getDenom20Pieces());
			denom50TotalPieces = denom50TotalPieces.add(entry.getValue().getDenom50Pieces());
			denom100TotalPieces = denom100TotalPieces.add(entry.getValue().getDenom100Pieces());
			denom200TotalPieces = denom200TotalPieces.add(entry.getValue().getDenom200Pieces());
			denom500TotalPieces = denom500TotalPieces.add(entry.getValue().getDenom500Pieces());
			denom1000TotalPieces = denom1000TotalPieces.add(entry.getValue().getDenom1000Pieces());
			denom2000TotalPieces = denom2000TotalPieces.add(entry.getValue().getDenom2000Pieces());
			totalInPieces = totalInPieces.add(entry.getValue().getTotalInPieces());
			totalValueOfBankNotes = totalValueOfBankNotes.add(entry.getValue().getTotalValueOfBankNotes());
		}

		for (Map.Entry<String, DSB> entry : dsbDepositList.entrySet()) {
			denom1TotalPieces = denom1TotalPieces.add(entry.getValue().getDenom1Pieces());
			denom2TotalPieces = denom2TotalPieces.add(entry.getValue().getDenom2Pieces());
			denom5TotalPieces = denom5TotalPieces.add(entry.getValue().getDenom5Pieces());
			denom10TotalPieces = denom10TotalPieces.add(entry.getValue().getDenom10Pieces());
			denom20TotalPieces = denom20TotalPieces.add(entry.getValue().getDenom20Pieces());
			denom50TotalPieces = denom50TotalPieces.add(entry.getValue().getDenom50Pieces());
			denom100TotalPieces = denom100TotalPieces.add(entry.getValue().getDenom100Pieces());
			denom200TotalPieces = denom200TotalPieces.add(entry.getValue().getDenom200Pieces());
			denom500TotalPieces = denom500TotalPieces.add(entry.getValue().getDenom500Pieces());
			denom1000TotalPieces = denom1000TotalPieces.add(entry.getValue().getDenom1000Pieces());
			denom2000TotalPieces = denom2000TotalPieces.add(entry.getValue().getDenom2000Pieces());
			totalInPieces = totalInPieces.add(entry.getValue().getTotalInPieces());
			totalValueOfBankNotes = totalValueOfBankNotes.add(entry.getValue().getTotalValueOfBankNotes());
		}

		for (Map.Entry<String, BankReceipt> entry : bankDepositList.entrySet()) {
			denom1TotalPieces = denom1TotalPieces.add(entry.getValue().getDenom1Pieces());
			denom2TotalPieces = denom2TotalPieces.add(entry.getValue().getDenom2Pieces());
			denom5TotalPieces = denom5TotalPieces.add(entry.getValue().getDenom5Pieces());
			denom10TotalPieces = denom10TotalPieces.add(entry.getValue().getDenom10Pieces());
			denom20TotalPieces = denom20TotalPieces.add(entry.getValue().getDenom20Pieces());
			denom50TotalPieces = denom50TotalPieces.add(entry.getValue().getDenom50Pieces());
			denom100TotalPieces = denom100TotalPieces.add(entry.getValue().getDenom100Pieces());
			denom200TotalPieces = denom200TotalPieces.add(entry.getValue().getDenom200Pieces());
			denom500TotalPieces = denom500TotalPieces.add(entry.getValue().getDenom500Pieces());
			denom1000TotalPieces = denom1000TotalPieces.add(entry.getValue().getDenom1000Pieces());
			denom2000TotalPieces = denom2000TotalPieces.add(entry.getValue().getDenom2000Pieces());
			totalInPieces = totalInPieces.add(entry.getValue().getTotalInPieces());
			totalValueOfBankNotes = totalValueOfBankNotes.add(entry.getValue().getTotalValueOfBankNotes());
		}
		for (Map.Entry<String, DiversionIRV> entry : dirvDepositList.entrySet()) {
			denom1TotalPieces = denom1TotalPieces.add(entry.getValue().getDenom1Pieces());
			denom2TotalPieces = denom2TotalPieces.add(entry.getValue().getDenom2Pieces());
			denom5TotalPieces = denom5TotalPieces.add(entry.getValue().getDenom5Pieces());
			denom10TotalPieces = denom10TotalPieces.add(entry.getValue().getDenom10Pieces());
			denom20TotalPieces = denom20TotalPieces.add(entry.getValue().getDenom20Pieces());
			denom50TotalPieces = denom50TotalPieces.add(entry.getValue().getDenom50Pieces());
			denom100TotalPieces = denom100TotalPieces.add(entry.getValue().getDenom100Pieces());
			denom200TotalPieces = denom200TotalPieces.add(entry.getValue().getDenom200Pieces());
			denom500TotalPieces = denom500TotalPieces.add(entry.getValue().getDenom500Pieces());
			denom1000TotalPieces = denom1000TotalPieces.add(entry.getValue().getDenom1000Pieces());
			denom2000TotalPieces = denom2000TotalPieces.add(entry.getValue().getDenom2000Pieces());
			totalInPieces = totalInPieces.add(entry.getValue().getTotalInPieces());
			totalValueOfBankNotes = totalValueOfBankNotes.add(entry.getValue().getTotalValueOfBankNotes());
		}
		for (Map.Entry<String, FreshFromRBI> entry : freshFromRBIList.entrySet()) {
			denom1TotalPieces = denom1TotalPieces.add(entry.getValue().getDenom1Pieces());
			denom2TotalPieces = denom2TotalPieces.add(entry.getValue().getDenom2Pieces());
			denom5TotalPieces = denom5TotalPieces.add(entry.getValue().getDenom5Pieces());
			denom10TotalPieces = denom10TotalPieces.add(entry.getValue().getDenom10Pieces());
			denom20TotalPieces = denom20TotalPieces.add(entry.getValue().getDenom20Pieces());
			denom50TotalPieces = denom50TotalPieces.add(entry.getValue().getDenom50Pieces());
			denom100TotalPieces = denom100TotalPieces.add(entry.getValue().getDenom100Pieces());
			denom200TotalPieces = denom200TotalPieces.add(entry.getValue().getDenom200Pieces());
			denom500TotalPieces = denom500TotalPieces.add(entry.getValue().getDenom500Pieces());
			denom1000TotalPieces = denom1000TotalPieces.add(entry.getValue().getDenom1000Pieces());
			denom2000TotalPieces = denom2000TotalPieces.add(entry.getValue().getDenom2000Pieces());
			totalInPieces = totalInPieces.add(entry.getValue().getTotalInPieces());
			totalValueOfBankNotes = totalValueOfBankNotes.add(entry.getValue().getTotalValueOfBankNotes());
		}

		for (Tuple tuple : ibitListValues) {
			if (tuple.get(0, Integer.class).equals(5)) {
				denom5TotalPieces = denom5TotalPieces.add(tuple.get(1, BigDecimal.class));
			}
			if (tuple.get(0, Integer.class).equals(10)) {
				denom10TotalPieces = denom10TotalPieces.add(tuple.get(1, BigDecimal.class));
			}
			if (tuple.get(0, Integer.class).equals(20)) {
				denom20TotalPieces = denom20TotalPieces.add(tuple.get(1, BigDecimal.class));
			}
			if (tuple.get(0, Integer.class).equals(50)) {
				denom50TotalPieces = denom50TotalPieces.add(tuple.get(1, BigDecimal.class));
			}
			if (tuple.get(0, Integer.class).equals(100)) {
				denom100TotalPieces = denom100TotalPieces.add(tuple.get(1, BigDecimal.class));
			}
			if (tuple.get(0, Integer.class).equals(200)) {
				denom200TotalPieces = denom200TotalPieces.add(tuple.get(1, BigDecimal.class));
			}
			if (tuple.get(0, Integer.class).equals(500)) {
				denom500TotalPieces = denom500TotalPieces.add(tuple.get(1, BigDecimal.class));
			}
			if (tuple.get(0, Integer.class).equals(1000)) {
				denom1000TotalPieces = denom1000TotalPieces.add(tuple.get(1, BigDecimal.class));
			}
			if (tuple.get(0, Integer.class).equals(2000)) {
				denom2000TotalPieces = denom2000TotalPieces.add(tuple.get(1, BigDecimal.class));
			}
			
		}

		
		map.put("denom1TotalPieces", denom1TotalPieces);
		map.put("denom2TotalPieces", denom2TotalPieces);
		map.put("denom5TotalPieces", denom5TotalPieces);
		map.put("denom10TotalPieces", denom10TotalPieces);
		map.put("denom20TotalPieces", denom20TotalPieces);
		map.put("denom50TotalPieces", denom50TotalPieces);
		map.put("denom100TotalPieces", denom100TotalPieces);
		map.put("denom200TotalPieces", denom200TotalPieces);
		map.put("denom500TotalPieces", denom500TotalPieces);
		map.put("denom1000TotalPieces", denom1000TotalPieces);
		map.put("denom2000TotalPieces", denom2000TotalPieces);
		map.put("totalInPieces", totalInPieces);
		map.put("totalValueOfBankNotes", totalValueOfBankNotes);

		return map;
	}

	public static ModelMap getSummarisedTotalForCashBookWithdrawalReport(Map<String, CRAAllocation> craWithdrawalList,
			Map<String, DiversionORVAllocation> diversionORVAllocationList,
			Map<String, OtherBankAllocation> otherBankAllocationList,
			Map<String, SoiledRemittanceAllocation> soiledAllocationList, List<Tuple> ibitListValues) {
		ModelMap map = new ModelMap();

		BigDecimal denom1TotalPieces = BigDecimal.ZERO;
		BigDecimal denom2TotalPieces = BigDecimal.ZERO;
		BigDecimal denom5TotalPieces = BigDecimal.ZERO;
		BigDecimal denom10TotalPieces = BigDecimal.ZERO;
		BigDecimal denom20TotalPieces = BigDecimal.ZERO;
		BigDecimal denom50TotalPieces = BigDecimal.ZERO;
		BigDecimal denom100TotalPieces = BigDecimal.ZERO;
		BigDecimal denom200TotalPieces = BigDecimal.ZERO;
		BigDecimal denom500TotalPieces = BigDecimal.ZERO;
		BigDecimal denom1000TotalPieces = BigDecimal.ZERO;
		BigDecimal denom2000TotalPieces = BigDecimal.ZERO;
		/*BigDecimal coin1TotalPieces = BigDecimal.ZERO;
		BigDecimal coin2TotalPieces = BigDecimal.ZERO;
		BigDecimal coin5TotalPieces = BigDecimal.ZERO;
		BigDecimal coin10TotalPieces = BigDecimal.ZERO;*/
		BigDecimal totalInPieces = BigDecimal.ZERO;
		BigDecimal totalValueOfBankNotes = BigDecimal.ZERO;

		for (Map.Entry<String, CRAAllocation> entry : craWithdrawalList.entrySet()) {
			denom1TotalPieces = denom1TotalPieces.add(entry.getValue().getDenom1Pieces());
			denom2TotalPieces = denom2TotalPieces.add(entry.getValue().getDenom2Pieces());
			denom5TotalPieces = denom5TotalPieces.add(entry.getValue().getDenom5Pieces());
			denom10TotalPieces = denom10TotalPieces.add(entry.getValue().getDenom10Pieces());
			denom20TotalPieces = denom20TotalPieces.add(entry.getValue().getDenom20Pieces());
			denom50TotalPieces = denom50TotalPieces.add(entry.getValue().getDenom50Pieces());
			denom100TotalPieces = denom100TotalPieces.add(entry.getValue().getDenom100Pieces());
			denom200TotalPieces = denom200TotalPieces.add(entry.getValue().getDenom200Pieces());
			denom500TotalPieces = denom500TotalPieces.add(entry.getValue().getDenom500Pieces());
			denom1000TotalPieces = denom1000TotalPieces.add(entry.getValue().getDenom1000Pieces());
			denom2000TotalPieces = denom2000TotalPieces.add(entry.getValue().getDenom2000Pieces());
		/*	coin1TotalPieces=coin1TotalPieces.add(entry.getValue().getCoin1Pieces());
			coin2TotalPieces=coin1TotalPieces.add(entry.getValue().getCoin2Pieces());
			coin5TotalPieces=coin1TotalPieces.add(entry.getValue().getCoin5Pieces());
			coin10TotalPieces=coin1TotalPieces.add(entry.getValue().getCoin10Pieces());*/
			totalInPieces = totalInPieces.add(entry.getValue().getTotalInPieces());
			totalValueOfBankNotes = totalValueOfBankNotes.add(entry.getValue().getTotalValueOfBankNotes());
		}

		for (Map.Entry<String, DiversionORVAllocation> entry : diversionORVAllocationList.entrySet()) {
			denom1TotalPieces = denom1TotalPieces.add(entry.getValue().getDenom1Pieces());
			denom2TotalPieces = denom2TotalPieces.add(entry.getValue().getDenom2Pieces());
			denom5TotalPieces = denom5TotalPieces.add(entry.getValue().getDenom5Pieces());
			denom10TotalPieces = denom10TotalPieces.add(entry.getValue().getDenom10Pieces());
			denom20TotalPieces = denom20TotalPieces.add(entry.getValue().getDenom20Pieces());
			denom50TotalPieces = denom50TotalPieces.add(entry.getValue().getDenom50Pieces());
			denom100TotalPieces = denom100TotalPieces.add(entry.getValue().getDenom100Pieces());
			denom200TotalPieces = denom200TotalPieces.add(entry.getValue().getDenom200Pieces());
			denom500TotalPieces = denom500TotalPieces.add(entry.getValue().getDenom500Pieces());
			denom1000TotalPieces = denom1000TotalPieces.add(entry.getValue().getDenom1000Pieces());
			denom2000TotalPieces = denom2000TotalPieces.add(entry.getValue().getDenom2000Pieces());
			totalInPieces = totalInPieces.add(entry.getValue().getTotalInPieces());
			totalValueOfBankNotes = totalValueOfBankNotes.add(entry.getValue().getTotalValueOfBankNotes());
		}

		for (Map.Entry<String, OtherBankAllocation> entry : otherBankAllocationList.entrySet()) {
			denom1TotalPieces = denom1TotalPieces.add(entry.getValue().getDenom1Pieces());
			denom2TotalPieces = denom2TotalPieces.add(entry.getValue().getDenom2Pieces());
			denom5TotalPieces = denom5TotalPieces.add(entry.getValue().getDenom5Pieces());
			denom10TotalPieces = denom10TotalPieces.add(entry.getValue().getDenom10Pieces());
			denom20TotalPieces = denom20TotalPieces.add(entry.getValue().getDenom20Pieces());
			denom50TotalPieces = denom50TotalPieces.add(entry.getValue().getDenom50Pieces());
			denom100TotalPieces = denom100TotalPieces.add(entry.getValue().getDenom100Pieces());
			denom200TotalPieces = denom200TotalPieces.add(entry.getValue().getDenom200Pieces());
			denom500TotalPieces = denom500TotalPieces.add(entry.getValue().getDenom500Pieces());
			denom1000TotalPieces = denom1000TotalPieces.add(entry.getValue().getDenom1000Pieces());
			denom2000TotalPieces = denom2000TotalPieces.add(entry.getValue().getDenom2000Pieces());
			totalInPieces = totalInPieces.add(entry.getValue().getTotalInPieces());
			totalValueOfBankNotes = totalValueOfBankNotes.add(entry.getValue().getTotalValueOfBankNotes());
		}

		for (Map.Entry<String, SoiledRemittanceAllocation> entry : soiledAllocationList.entrySet()) {
			denom1TotalPieces = denom1TotalPieces.add(entry.getValue().getDenom1Pieces());
			denom2TotalPieces = denom2TotalPieces.add(entry.getValue().getDenom2Pieces());
			denom5TotalPieces = denom5TotalPieces.add(entry.getValue().getDenom5Pieces());
			denom10TotalPieces = denom10TotalPieces.add(entry.getValue().getDenom10Pieces());
			denom20TotalPieces = denom20TotalPieces.add(entry.getValue().getDenom20Pieces());
			denom50TotalPieces = denom50TotalPieces.add(entry.getValue().getDenom50Pieces());
			denom100TotalPieces = denom100TotalPieces.add(entry.getValue().getDenom100Pieces());
			denom200TotalPieces = denom200TotalPieces.add(entry.getValue().getDenom200Pieces());
			denom500TotalPieces = denom500TotalPieces.add(entry.getValue().getDenom500Pieces());
			denom1000TotalPieces = denom1000TotalPieces.add(entry.getValue().getDenom1000Pieces());
			denom2000TotalPieces = denom2000TotalPieces.add(entry.getValue().getDenom2000Pieces());
			totalInPieces = totalInPieces.add(entry.getValue().getTotalInPieces());
			totalValueOfBankNotes = totalValueOfBankNotes.add(entry.getValue().getTotalValueOfBankNotes());
		}

		for (Tuple tuple : ibitListValues) {
			if (tuple.get(0, Integer.class).equals(5)) {
				denom5TotalPieces = denom5TotalPieces.add(tuple.get(1, BigDecimal.class));
				totalInPieces = totalInPieces.add(denom5TotalPieces);
			}
			if (tuple.get(0, Integer.class).equals(10)) {
				denom10TotalPieces = denom10TotalPieces.add(tuple.get(1, BigDecimal.class));
				totalInPieces = totalInPieces.add(denom10TotalPieces);
			}
			if (tuple.get(0, Integer.class).equals(20)) {
				denom20TotalPieces = denom20TotalPieces.add(tuple.get(1, BigDecimal.class));
				totalInPieces = totalInPieces.add(denom20TotalPieces);
			}
			if (tuple.get(0, Integer.class).equals(50)) {
				denom50TotalPieces = denom50TotalPieces.add(tuple.get(1, BigDecimal.class));
				totalInPieces = totalInPieces.add(denom50TotalPieces);
			}
			if (tuple.get(0, Integer.class).equals(100)) {
				denom100TotalPieces = denom100TotalPieces.add(tuple.get(1, BigDecimal.class));
				totalInPieces = totalInPieces.add(denom100TotalPieces);
			}
			if (tuple.get(0, Integer.class).equals(200)) {
				denom200TotalPieces = denom200TotalPieces.add(tuple.get(1, BigDecimal.class));
				totalInPieces = totalInPieces.add(denom200TotalPieces);
			}
			if (tuple.get(0, Integer.class).equals(500)) {
				denom500TotalPieces = denom500TotalPieces.add(tuple.get(1, BigDecimal.class));
				totalInPieces = totalInPieces.add(denom500TotalPieces);
			}
			if (tuple.get(0, Integer.class).equals(1000)) {
				denom1000TotalPieces = denom1000TotalPieces.add(tuple.get(1, BigDecimal.class));
				totalInPieces = totalInPieces.add(denom1000TotalPieces);
			}
			if (tuple.get(0, Integer.class).equals(2000)) {
				denom2000TotalPieces = denom2000TotalPieces.add(tuple.get(1, BigDecimal.class));
				totalInPieces = totalInPieces.add(denom2000TotalPieces);
			}
			
		}

		map.put("denom1TotalPieces", denom1TotalPieces);
		map.put("denom2TotalPieces", denom2TotalPieces);
		map.put("denom5TotalPieces", denom5TotalPieces);
		map.put("denom10TotalPieces", denom10TotalPieces);
		map.put("denom20TotalPieces", denom20TotalPieces);
		map.put("denom50TotalPieces", denom50TotalPieces);
		map.put("denom100TotalPieces", denom100TotalPieces);
		map.put("denom200TotalPieces", denom200TotalPieces);
		map.put("denom500TotalPieces", denom500TotalPieces);
		map.put("denom1000TotalPieces", denom1000TotalPieces);
		map.put("denom2000TotalPieces", denom2000TotalPieces);
		map.put("totalInPieces", totalInPieces);
		map.put("totalValueOfBankNotes", totalValueOfBankNotes);
		/*map.put("coin1TotalPieces", coin1TotalPieces);
		map.put("coin2TotalPieces", coin2TotalPieces);
		map.put("coin5TotalPieces", coin5TotalPieces);
		map.put("coin10TotalPieces", coin10TotalPieces);*/

		return map;
	}

	public static Map<String, FreshFromRBI> mapTupleToFreshFromRBI(List<Tuple> freshRBINotesTupleList,
			List<Tuple> freshRBICoinsTupleList) {
		Map<String, FreshFromRBI> mapList = new LinkedHashMap<>();

		if (freshRBINotesTupleList != null) {
			for (Tuple tuple : freshRBINotesTupleList) {
				mapList.put(tuple.get(3, String.class), new FreshFromRBI(true));
			}
		}
		if (freshRBICoinsTupleList != null) {
			for (Tuple coinTuple : freshRBICoinsTupleList) {
				mapList.put(coinTuple.get(3, String.class), new FreshFromRBI(true));
			}
		}

		for (Tuple tuple : freshRBINotesTupleList) {
			FreshFromRBI freshNotesFromRBI = mapList.get(tuple.get(3, String.class));

			freshNotesFromRBI.setRbiOrderNo(tuple.get(3, String.class));
			if (tuple.get(0, Integer.class).equals(2000)) {
				freshNotesFromRBI.setDenom2000Pieces(tuple.get(1, BigDecimal.class));
			}
			if (tuple.get(0, Integer.class).equals(1000)) {
				freshNotesFromRBI.setDenom1000Pieces(tuple.get(1, BigDecimal.class));
			}
			
			if (tuple.get(0, Integer.class).equals(500)) {
				freshNotesFromRBI.setDenom500Pieces(tuple.get(1, BigDecimal.class));
			}
			if (tuple.get(0, Integer.class).equals(200)) {
				freshNotesFromRBI.setDenom200Pieces(tuple.get(1, BigDecimal.class));
			}
			if (tuple.get(0, Integer.class).equals(100)) {
				freshNotesFromRBI.setDenom100Pieces(tuple.get(1, BigDecimal.class));
			}
			if (tuple.get(0, Integer.class).equals(50)) {
				freshNotesFromRBI.setDenom50Pieces(tuple.get(1, BigDecimal.class));
			}
			if (tuple.get(0, Integer.class).equals(20)) {
				freshNotesFromRBI.setDenom20Pieces(tuple.get(1, BigDecimal.class));
			}
			if (tuple.get(0, Integer.class).equals(10)) {
				freshNotesFromRBI.setDenom10Pieces(tuple.get(1, BigDecimal.class));
			}
			if (tuple.get(0, Integer.class).equals(5)) {
				freshNotesFromRBI.setDenom5Pieces(tuple.get(1, BigDecimal.class));
			}
			if (tuple.get(0, Integer.class).equals(2)) {
				freshNotesFromRBI.setDenom2Pieces(tuple.get(1, BigDecimal.class));
			}
			if (tuple.get(0, Integer.class).equals(1)) {
				freshNotesFromRBI.setDenom1Pieces(tuple.get(1, BigDecimal.class));
			}

			freshNotesFromRBI.setTotalInPieces(freshNotesFromRBI.getDenom2000Pieces()
					.add(freshNotesFromRBI.getDenom1000Pieces()
							.add(freshNotesFromRBI.getDenom500Pieces()
									.add(freshNotesFromRBI.getDenom200Pieces()
									.add(freshNotesFromRBI.getDenom100Pieces()
										
											.add(freshNotesFromRBI.getDenom50Pieces()
													.add(freshNotesFromRBI.getDenom20Pieces()
															.add(freshNotesFromRBI.getDenom10Pieces()
																	.add(freshNotesFromRBI.getDenom5Pieces()
																	.add(freshNotesFromRBI.getDenom2Pieces()))))))))));

			freshNotesFromRBI
					.setTotalValueOfBankNotes(freshNotesFromRBI.getDenom2000Pieces().multiply(BigDecimal.valueOf(2000))
							.add(freshNotesFromRBI.getDenom1000Pieces().multiply(BigDecimal.valueOf(1000)))
							.add(freshNotesFromRBI.getDenom500Pieces().multiply(BigDecimal.valueOf(500)))
							.add(freshNotesFromRBI.getDenom200Pieces().multiply(BigDecimal.valueOf(200)))
							.add(freshNotesFromRBI.getDenom100Pieces().multiply(BigDecimal.valueOf(100)))
							.add(freshNotesFromRBI.getDenom50Pieces().multiply(BigDecimal.valueOf(50)))
							.add(freshNotesFromRBI.getDenom10Pieces().multiply(BigDecimal.valueOf(10)))
							.add(freshNotesFromRBI.getDenom5Pieces().multiply(BigDecimal.valueOf(5)))
							.add(freshNotesFromRBI.getDenom2Pieces().multiply(BigDecimal.valueOf(2))));
		}

		/*
		 * for(Tuple tuple : freshRBINotesTupleList){ mapList.put(tuple.get(3,
		 * String.class)+""+tuple.get(2, CashType.class), new
		 * FreshFromRBI(true)); }
		 */

		for (Tuple coinTuple : freshRBICoinsTupleList) {
			FreshFromRBI freshCoinsFromRBI = mapList.get(coinTuple.get(3, String.class));

			freshCoinsFromRBI.setRbiOrderNo(coinTuple.get(3, String.class));
			if (coinTuple.get(0, Integer.class).equals(10)) {
				freshCoinsFromRBI.setDenom10CoinsPieces(coinTuple.get(1, Integer.class) * 2000);
			}
			if (coinTuple.get(0, Integer.class).equals(5)) {
				freshCoinsFromRBI.setDenom5CoinsPieces(coinTuple.get(1, Integer.class) * 2500);
			}
			if (coinTuple.get(0, Integer.class).equals(2)) {
				freshCoinsFromRBI.setDenom2CoinsPieces(coinTuple.get(1, Integer.class) * 2500);
			}
			if (coinTuple.get(0, Integer.class).equals(1)) {
				freshCoinsFromRBI.setDenom1CoinsPieces(coinTuple.get(1, Integer.class) * 2500);
			}

			freshCoinsFromRBI.setTotalValueOfCoins(
					freshCoinsFromRBI.getDenom10CoinsPieces() * 10 + freshCoinsFromRBI.getDenom5CoinsPieces() * 5
							+ freshCoinsFromRBI.getDenom2CoinsPieces() * 2 + freshCoinsFromRBI.getDenom1CoinsPieces());
		}
		return mapList;
	}

	public static Map<String, DiversionIRV> mapTupleToDirvReceipt(List<Tuple> dirvDepositTupleList) {
		Map<String, DiversionIRV> mapList = new LinkedHashMap<>();

		for (Tuple tuple : dirvDepositTupleList) {
			mapList.put(tuple.get(3, String.class), new DiversionIRV(true));
		}

		for (Tuple tuple : dirvDepositTupleList) {
			DiversionIRV diversionIRV = mapList.get(tuple.get(3, String.class));

			diversionIRV.setBankName(tuple.get(2, String.class));
			diversionIRV.setRbiOrderNo(tuple.get(3, String.class));
			if (tuple.get(0, Integer.class).equals(2000)) {
				diversionIRV.setDenom2000Pieces(tuple.get(1, BigDecimal.class));
			}
			if (tuple.get(0, Integer.class).equals(1000)) {
				diversionIRV.setDenom1000Pieces(tuple.get(1, BigDecimal.class));
			}
			if (tuple.get(0, Integer.class).equals(500)) {
				diversionIRV.setDenom500Pieces(tuple.get(1, BigDecimal.class));
			}
			if (tuple.get(0, Integer.class).equals(200)) {
				diversionIRV.setDenom200Pieces(tuple.get(1, BigDecimal.class));
			}
			if (tuple.get(0, Integer.class).equals(100)) {
				diversionIRV.setDenom100Pieces(tuple.get(1, BigDecimal.class));
			}
			if (tuple.get(0, Integer.class).equals(50)) {
				diversionIRV.setDenom50Pieces(tuple.get(1, BigDecimal.class));
			}
			if (tuple.get(0, Integer.class).equals(20)) {
				diversionIRV.setDenom20Pieces(tuple.get(1, BigDecimal.class));
			}
			if (tuple.get(0, Integer.class).equals(10)) {
				diversionIRV.setDenom10Pieces(tuple.get(1, BigDecimal.class));
			}
			if (tuple.get(0, Integer.class).equals(5)) {
				diversionIRV.setDenom5Pieces(tuple.get(1, BigDecimal.class));
			}
			if (tuple.get(0, Integer.class).equals(2)) {
				diversionIRV.setDenom2Pieces(tuple.get(1, BigDecimal.class));
			}
			if (tuple.get(0, Integer.class).equals(1)) {
				diversionIRV.setDenom1Pieces(tuple.get(1, BigDecimal.class));
			}

			diversionIRV.setTotalInPieces(diversionIRV.getDenom2000Pieces()
					.add(diversionIRV.getDenom1000Pieces().add(diversionIRV.getDenom500Pieces().add(diversionIRV.getDenom200Pieces().add(diversionIRV
							.getDenom100Pieces().add(diversionIRV.getDenom50Pieces().add(diversionIRV.getDenom20Pieces()
									.add(diversionIRV.getDenom10Pieces().add(diversionIRV.getDenom5Pieces().add(diversionIRV.getDenom2Pieces()))))))))));

			diversionIRV.setTotalValueOfBankNotes(diversionIRV.getDenom2000Pieces().multiply(BigDecimal.valueOf(2000))
					.add(diversionIRV.getDenom1000Pieces().multiply(BigDecimal.valueOf(1000)))
					.add(diversionIRV.getDenom500Pieces().multiply(BigDecimal.valueOf(500)))
					.add(diversionIRV.getDenom200Pieces().multiply(BigDecimal.valueOf(200)))
					.add(diversionIRV.getDenom100Pieces().multiply(BigDecimal.valueOf(100)))
					.add(diversionIRV.getDenom50Pieces().multiply(BigDecimal.valueOf(50)))
					.add(diversionIRV.getDenom20Pieces().multiply(BigDecimal.valueOf(20)))
					.add(diversionIRV.getDenom10Pieces().multiply(BigDecimal.valueOf(10)))
					.add(diversionIRV.getDenom10Pieces().multiply(BigDecimal.valueOf(5)))
					.add(diversionIRV.getDenom5Pieces().multiply(BigDecimal.valueOf(2))));
		}
		return mapList;
	}

	public static String getPRNToPrintForFreshNotes(FreshFromRBI freshFromRBI, String oldtext) {
		String replacedtext = oldtext.replaceAll("bin", "" + freshFromRBI.getBin());
		replacedtext = replacedtext.replaceAll("Branch: ", "" + "OrderNo: ");
		replacedtext = replacedtext.replaceAll("Sol ID :", "" + "Potdar: ");
		replacedtext = replacedtext.replaceAll("branch", "" + freshFromRBI.getRbiOrderNo());
		replacedtext = replacedtext.replaceAll("solId", "" + freshFromRBI.getPotdarName());
		replacedtext = replacedtext.replaceAll("denom", "" + freshFromRBI.getDenomination());
		replacedtext = replacedtext.replaceAll("bundle", "" + freshFromRBI.getBundle());

		String formattedTotal = CurrencyFormatter.inrFormatter(freshFromRBI.getTotal()).toString();
		replacedtext = replacedtext.replaceAll("total", "" + formattedTotal);
		return replacedtext;
	}

	public static String getPRNToPrintForFreshCoins(FreshFromRBI freshFromRBI, String oldtext, int sequence) {
		String replacedtext = oldtext.replaceAll("Bin: ", "" + "Sequence No: ");
		replacedtext = replacedtext.replaceAll("bin", "" + sequence);
		replacedtext = replacedtext.replaceAll("Branch: ", "" + "OrderNo: ");
		replacedtext = replacedtext.replaceAll("Sol ID :", "" + "Potdar: ");
		replacedtext = replacedtext.replaceAll("branch", "" + freshFromRBI.getRbiOrderNo());
		replacedtext = replacedtext.replaceAll("solId", "" + freshFromRBI.getPotdarName());
		replacedtext = replacedtext.replaceAll("denom", "" + freshFromRBI.getDenomination());
		replacedtext = replacedtext.replaceAll("bundle", "" + BigDecimal.ONE);

		String formattedTotal = CurrencyFormatter
				.inrFormatter(freshFromRBI.getTotal().divide(BigDecimal.valueOf(freshFromRBI.getNoOfBags())))
				.toString();
		replacedtext = replacedtext.replaceAll("total", "" + formattedTotal);
		return replacedtext;
	}

	public static Map<String, Mutilated> mapTupleToMutilated(List<Tuple> mutilatedTupleList) {
		Map<String, Mutilated> mapList = new LinkedHashMap<>();

		for (Tuple tuple : mutilatedTupleList) {
			mapList.put(tuple.get(1, String.class), new Mutilated(true));
		}

		for (Tuple tuple : mutilatedTupleList) {
			Mutilated mutilated = mapList.get(tuple.get(1, String.class));

			BigDecimal total = tuple.get(2, BigDecimal.class);

			mutilated.setInsertedTime(tuple.get(0, Calendar.class));

			if (tuple.get(1, Integer.class).equals(2000)) {
				mutilated.setDenom2000Pieces(tuple.get(2, BigDecimal.class));
			}
			if (tuple.get(1, Integer.class).equals(1000)) {
				mutilated.setDenom1000Pieces(tuple.get(2, BigDecimal.class));
			}
			if (tuple.get(1, Integer.class).equals(500)) {
				mutilated.setDenom500Pieces(tuple.get(2, BigDecimal.class));
			}

			if (tuple.get(1, Integer.class).equals(200)) {
				mutilated.setDenom200Pieces(tuple.get(2, BigDecimal.class));
			}

			if (tuple.get(1, Integer.class).equals(100)) {
				mutilated.setDenom100Pieces(tuple.get(2, BigDecimal.class));
			}
			if (tuple.get(1, Integer.class).equals(50)) {
				mutilated.setDenom50Pieces(tuple.get(2, BigDecimal.class));
			}
			if (tuple.get(1, Integer.class).equals(20)) {
				mutilated.setDenom20Pieces(tuple.get(2, BigDecimal.class));
			}
			if (tuple.get(1, Integer.class).equals(10)) {
				mutilated.setDenom10Pieces(tuple.get(2, BigDecimal.class));
			}
			if (tuple.get(1, Integer.class).equals(5)) {
				mutilated.setDenom5Pieces(tuple.get(2, BigDecimal.class));
			}
			if (tuple.get(1, Integer.class).equals(2)) {
				mutilated.setDenom2Pieces(tuple.get(2, BigDecimal.class));
			}
			if (tuple.get(1, Integer.class).equals(1)) {
				mutilated.setDenom1Pieces(tuple.get(2, BigDecimal.class));
			}

			mutilated.setTotalInPieces(mutilated.getDenom2000Pieces()
					.add(mutilated.getDenom1000Pieces().add(mutilated.getDenom500Pieces()
							.add(mutilated.getDenom100Pieces().add(mutilated.getDenom50Pieces()
									.add(mutilated.getDenom20Pieces().add(mutilated.getDenom10Pieces()
											.add(mutilated.getDenom5Pieces().add(mutilated.getDenom200Pieces())))))))));

			mutilated.setTotalValue(mutilated.getDenom2000Pieces().multiply(BigDecimal.valueOf(2000))
					.add(mutilated.getDenom1000Pieces().multiply(BigDecimal.valueOf(1000)))
					.add(mutilated.getDenom500Pieces().multiply(BigDecimal.valueOf(500)))
					.add(mutilated.getDenom100Pieces().multiply(BigDecimal.valueOf(100)))
					.add(mutilated.getDenom200Pieces().multiply(BigDecimal.valueOf(200)))
					.add(mutilated.getDenom50Pieces().multiply(BigDecimal.valueOf(50)))
					.add(mutilated.getDenom20Pieces().multiply(BigDecimal.valueOf(20)))
					.add(mutilated.getDenom10Pieces().multiply(BigDecimal.valueOf(10)))
					.add(mutilated.getDenom5Pieces().multiply(BigDecimal.valueOf(5))));

			if (mutilated.getTotalDenomination500() != null) {
				mutilated.setTotalDenomination500(mutilated.getTotalDenomination500().add(total));
			} else {
				mutilated.setTotalDenomination500(total);
			}
		}

		return mapList;
	}

	public static Map<String, CRAAllocation> mapTupleToCRAAllocation(List<Tuple> craAllocationWithdrawalTupleList) {
		Map<String, CRAAllocation> mapList = new LinkedHashMap<>();

		for (Tuple tuple : craAllocationWithdrawalTupleList) {
			
			
			
			mapList.put(tuple.get(0, Integer.class) + "", new CRAAllocation(true));
		}

		for (Tuple tuple : craAllocationWithdrawalTupleList) {
			CRAAllocation craAllocation = mapList.get(tuple.get(0, Integer.class) + "");

			craAllocation.setCraId(tuple.get(0, long.class));
			
			if (tuple.get(1, Integer.class).equals(2000)) {
				craAllocation.setDenom2000Pieces(craAllocation.getDenom2000Pieces().add(tuple.get(2, BigDecimal.class)));
			}
			if (tuple.get(1, Integer.class).equals(1000)) {
				craAllocation.setDenom1000Pieces(craAllocation.getDenom1000Pieces().add(tuple.get(2, BigDecimal.class)));
			}
			if (tuple.get(1, Integer.class).equals(500)) {
				craAllocation.setDenom500Pieces(craAllocation.getDenom500Pieces().add(tuple.get(2, BigDecimal.class)));
			}
			if (tuple.get(1, Integer.class).equals(200)) {
				craAllocation.setDenom200Pieces(craAllocation.getDenom200Pieces().add(tuple.get(2, BigDecimal.class)));
			}
			if (tuple.get(1, Integer.class).equals(100)) {
				craAllocation.setDenom100Pieces(craAllocation.getDenom100Pieces().add(tuple.get(2, BigDecimal.class)));
			}
			if (tuple.get(1, Integer.class).equals(50)) {
				craAllocation.setDenom50Pieces(craAllocation.getDenom50Pieces().add(tuple.get(2, BigDecimal.class)));
			}
			if (tuple.get(1, Integer.class).equals(20)) {
				craAllocation.setDenom20Pieces(craAllocation.getDenom20Pieces().add(tuple.get(2, BigDecimal.class)));
			}
			if (tuple.get(1, Integer.class).equals(10)) {
				craAllocation.setDenom10Pieces(craAllocation.getDenom10Pieces().add(tuple.get(2, BigDecimal.class)));
			}
			if (tuple.get(1, Integer.class).equals(5)) {
				craAllocation.setDenom5Pieces(craAllocation.getDenom5Pieces().add(tuple.get(2, BigDecimal.class)));
			}
			if (tuple.get(1, Integer.class).equals(2)) {
				craAllocation.setDenom2Pieces(craAllocation.getDenom2Pieces().add(tuple.get(2, BigDecimal.class)));
			}
			if (tuple.get(1, Integer.class).equals(1)) {
				craAllocation.setDenom1Pieces(craAllocation.getDenom1Pieces().add(tuple.get(2, BigDecimal.class)));
			}

			craAllocation.setTotalInPieces(craAllocation.getDenom2000Pieces().add(craAllocation.getDenom1000Pieces()
					.add(craAllocation.getDenom500Pieces().add(craAllocation.getDenom200Pieces().add(craAllocation
							.getDenom100Pieces().add(craAllocation.getDenom50Pieces()
									.add(craAllocation.getDenom20Pieces().add(craAllocation.getDenom10Pieces()
											.add(craAllocation.getDenom5Pieces())))))))));

			craAllocation.setTotalValueOfBankNotes(
					     craAllocation.getDenom2000Pieces().multiply(BigDecimal.valueOf(2000))
					.add(craAllocation.getDenom1000Pieces().multiply(BigDecimal.valueOf(1000)))
					.add(craAllocation.getDenom500Pieces().multiply(BigDecimal.valueOf(500)))
					.add(craAllocation.getDenom100Pieces().multiply(BigDecimal.valueOf(100)))
					.add(craAllocation.getDenom200Pieces().multiply(BigDecimal.valueOf(200)))
					.add(craAllocation.getDenom50Pieces().multiply(BigDecimal.valueOf(50)))
					.add(craAllocation.getDenom20Pieces().multiply(BigDecimal.valueOf(20)))
					.add(craAllocation.getDenom10Pieces().multiply(BigDecimal.valueOf(10)))
					.add(craAllocation.getDenom5Pieces().multiply(BigDecimal.valueOf(5))));
		}
		return mapList;
	}

	public static Map<String, DiversionORVAllocation> mapTupleToDiversionORVAllocation(
			List<Tuple> diversionORVWithdrawalTupleList) {
		Map<String, DiversionORVAllocation> mapList = new LinkedHashMap<>();

		for (Tuple tuple : diversionORVWithdrawalTupleList) {
			mapList.put(tuple.get(0, Integer.class) + "", new DiversionORVAllocation(true));
		}

		for (Tuple tuple : diversionORVWithdrawalTupleList) {
			DiversionORVAllocation orvAllocation = mapList.get(tuple.get(0, Integer.class) + "");

			orvAllocation.setDiversionOrvId(tuple.get(0, long.class));

			if (tuple.get(1, Integer.class).equals(2000)) {
				orvAllocation.setDenom2000Pieces(tuple.get(2, BigDecimal.class));
			}
			if (tuple.get(1, Integer.class).equals(1000)) {
				orvAllocation.setDenom1000Pieces(tuple.get(2, BigDecimal.class));
			}
			if (tuple.get(1, Integer.class).equals(500)) {
				orvAllocation.setDenom500Pieces(tuple.get(2, BigDecimal.class));
			}
			if (tuple.get(1, Integer.class).equals(100)) {
				orvAllocation.setDenom100Pieces(tuple.get(2, BigDecimal.class));
			}
			if (tuple.get(1, Integer.class).equals(200)) {
				orvAllocation.setDenom200Pieces(tuple.get(2, BigDecimal.class));
			}
			if (tuple.get(1, Integer.class).equals(50)) {
				orvAllocation.setDenom50Pieces(tuple.get(2, BigDecimal.class));
			}
			if (tuple.get(1, Integer.class).equals(20)) {
				orvAllocation.setDenom20Pieces(tuple.get(2, BigDecimal.class));
			}
			if (tuple.get(1, Integer.class).equals(10)) {
				orvAllocation.setDenom10Pieces(tuple.get(2, BigDecimal.class));
			}
			if (tuple.get(1, Integer.class).equals(5)) {
				orvAllocation.setDenom5Pieces(tuple.get(2, BigDecimal.class));
			}
			if (tuple.get(1, Integer.class).equals(2)) {
				orvAllocation.setDenom2Pieces(tuple.get(2, BigDecimal.class));
			}
			if (tuple.get(1, Integer.class).equals(1)) {
				orvAllocation.setDenom1Pieces(tuple.get(2, BigDecimal.class));
			}

			orvAllocation.setTotalInPieces(orvAllocation.getDenom2000Pieces().add(orvAllocation.getDenom1000Pieces()
					.add(orvAllocation.getDenom500Pieces().add(orvAllocation.getDenom100Pieces().add(orvAllocation.getDenom200Pieces()
							.add(orvAllocation.getDenom50Pieces().add(orvAllocation.getDenom20Pieces()
									.add(orvAllocation.getDenom10Pieces().add(orvAllocation.getDenom5Pieces())))))))));

			orvAllocation.setTotalValueOfBankNotes(orvAllocation.getDenom2000Pieces().multiply(BigDecimal.valueOf(2000))
					.add(orvAllocation.getDenom1000Pieces().multiply(BigDecimal.valueOf(1000)))
					.add(orvAllocation.getDenom500Pieces().multiply(BigDecimal.valueOf(500)))
					.add(orvAllocation.getDenom100Pieces().multiply(BigDecimal.valueOf(100)))
					.add(orvAllocation.getDenom200Pieces().multiply(BigDecimal.valueOf(200)))
					.add(orvAllocation.getDenom50Pieces().multiply(BigDecimal.valueOf(50)))
					.add(orvAllocation.getDenom20Pieces().multiply(BigDecimal.valueOf(20)))
					.add(orvAllocation.getDenom10Pieces().multiply(BigDecimal.valueOf(10)))
					.add(orvAllocation.getDenom5Pieces().multiply(BigDecimal.valueOf(5))));
		}
		return mapList;
	}

	public static Map<String, OtherBankAllocation> mapTupleToOtherBankAllocation(
			List<Tuple> OtherBankWithdrawalTupleList) {
		Map<String, OtherBankAllocation> mapList = new LinkedHashMap<>();

		for (Tuple tuple : OtherBankWithdrawalTupleList) {
			mapList.put(tuple.get(0, Integer.class) + "", new OtherBankAllocation(true));
		}

		for (Tuple tuple : OtherBankWithdrawalTupleList) {
			OtherBankAllocation otherBankAllocation = mapList.get(tuple.get(0, Integer.class) + "");

			otherBankAllocation.setOtherBankId(tuple.get(0, long.class));

			if (tuple.get(1, Integer.class).equals(2000)) {
				otherBankAllocation.setDenom2000Pieces(tuple.get(2, BigDecimal.class));
			}
			if (tuple.get(1, Integer.class).equals(1000)) {
				otherBankAllocation.setDenom1000Pieces(tuple.get(2, BigDecimal.class));
			}
			if (tuple.get(1, Integer.class).equals(500)) {
				otherBankAllocation.setDenom500Pieces(tuple.get(2, BigDecimal.class));
			}
			if (tuple.get(1, Integer.class).equals(100)) {
				otherBankAllocation.setDenom100Pieces(tuple.get(2, BigDecimal.class));
			}
			
			if (tuple.get(1, Integer.class).equals(200)) {
				otherBankAllocation.setDenom200Pieces(tuple.get(2, BigDecimal.class));
			}
			
			if (tuple.get(1, Integer.class).equals(50)) {
				otherBankAllocation.setDenom50Pieces(tuple.get(2, BigDecimal.class));
			}
			if (tuple.get(1, Integer.class).equals(20)) {
				otherBankAllocation.setDenom20Pieces(tuple.get(2, BigDecimal.class));
			}
			if (tuple.get(1, Integer.class).equals(10)) {
				otherBankAllocation.setDenom10Pieces(tuple.get(2, BigDecimal.class));
			}
			if (tuple.get(1, Integer.class).equals(5)) {
				otherBankAllocation.setDenom5Pieces(tuple.get(2, BigDecimal.class));
			}
			if (tuple.get(1, Integer.class).equals(2)) {
				otherBankAllocation.setDenom2Pieces(tuple.get(2, BigDecimal.class));
			}
			if (tuple.get(1, Integer.class).equals(1)) {
				otherBankAllocation.setDenom1Pieces(tuple.get(2, BigDecimal.class));
			}

			otherBankAllocation.setTotalInPieces(otherBankAllocation.getDenom2000Pieces()
					.add(otherBankAllocation.getDenom1000Pieces()
							.add(otherBankAllocation.getDenom500Pieces()
									.add(otherBankAllocation.getDenom100Pieces()
											.add(otherBankAllocation.getDenom200Pieces()
											.add(otherBankAllocation.getDenom50Pieces()
													.add(otherBankAllocation.getDenom20Pieces()
															.add(otherBankAllocation.getDenom10Pieces()
																	.add(otherBankAllocation.getDenom5Pieces())))))))));

			otherBankAllocation.setTotalValueOfBankNotes(
					otherBankAllocation.getDenom2000Pieces().multiply(BigDecimal.valueOf(2000))
							.add(otherBankAllocation.getDenom1000Pieces().multiply(BigDecimal.valueOf(1000)))
							.add(otherBankAllocation.getDenom500Pieces().multiply(BigDecimal.valueOf(500)))
							.add(otherBankAllocation.getDenom100Pieces().multiply(BigDecimal.valueOf(100)))
							.add(otherBankAllocation.getDenom200Pieces().multiply(BigDecimal.valueOf(200)))
							.add(otherBankAllocation.getDenom50Pieces().multiply(BigDecimal.valueOf(50)))
							.add(otherBankAllocation.getDenom20Pieces().multiply(BigDecimal.valueOf(20)))
							.add(otherBankAllocation.getDenom10Pieces().multiply(BigDecimal.valueOf(10)))
							.add(otherBankAllocation.getDenom5Pieces().multiply(BigDecimal.valueOf(5))));
		}
		return mapList;
	}

	public static Map<String, SoiledRemittanceAllocation> mapTupleToSoiledAllocation(
			List<Tuple> soiledRemittanceWithdrawalTupleList) {
		Map<String, SoiledRemittanceAllocation> mapList = new LinkedHashMap<>();

		for (Tuple tuple : soiledRemittanceWithdrawalTupleList) {
			mapList.put(tuple.get(0, Integer.class) + "", new SoiledRemittanceAllocation(true));
		}

		for (Tuple tuple : soiledRemittanceWithdrawalTupleList) {
			SoiledRemittanceAllocation soiledRemittanceAllocation = mapList.get(tuple.get(0, Integer.class) + "");

			soiledRemittanceAllocation.setSoiledRemittanceId(tuple.get(0, long.class));

			if (tuple.get(1, Integer.class).equals(2000)) {
				soiledRemittanceAllocation.setDenom2000Pieces(tuple.get(2, BigDecimal.class));
			}
			if (tuple.get(1, Integer.class).equals(1000)) {
				soiledRemittanceAllocation.setDenom1000Pieces(tuple.get(2, BigDecimal.class));
			}
			if (tuple.get(1, Integer.class).equals(500)) {
				soiledRemittanceAllocation.setDenom500Pieces(tuple.get(2, BigDecimal.class));
			}
			if (tuple.get(1, Integer.class).equals(100)) {
				soiledRemittanceAllocation.setDenom100Pieces(tuple.get(2, BigDecimal.class));
			}
			if (tuple.get(1, Integer.class).equals(200)) {
				soiledRemittanceAllocation.setDenom200Pieces(tuple.get(2, BigDecimal.class));
			}
			if (tuple.get(1, Integer.class).equals(50)) {
				soiledRemittanceAllocation.setDenom50Pieces(tuple.get(2, BigDecimal.class));
			}
			if (tuple.get(1, Integer.class).equals(20)) {
				soiledRemittanceAllocation.setDenom20Pieces(tuple.get(2, BigDecimal.class));
			}
			if (tuple.get(1, Integer.class).equals(10)) {
				soiledRemittanceAllocation.setDenom10Pieces(tuple.get(2, BigDecimal.class));
			}
			if (tuple.get(1, Integer.class).equals(5)) {
				soiledRemittanceAllocation.setDenom5Pieces(tuple.get(2, BigDecimal.class));
			}
			if (tuple.get(1, Integer.class).equals(2)) {
				soiledRemittanceAllocation.setDenom2Pieces(tuple.get(2, BigDecimal.class));
			}
			if (tuple.get(1, Integer.class).equals(1)) {
				soiledRemittanceAllocation.setDenom1Pieces(tuple.get(2, BigDecimal.class));
			}

			soiledRemittanceAllocation.setTotalInPieces(soiledRemittanceAllocation.getDenom2000Pieces()
					.add(soiledRemittanceAllocation.getDenom1000Pieces().add(soiledRemittanceAllocation
							.getDenom500Pieces().add(soiledRemittanceAllocation.getDenom100Pieces()
									.add(soiledRemittanceAllocation.getDenom200Pieces()
									.add(soiledRemittanceAllocation.getDenom50Pieces()
											.add(soiledRemittanceAllocation.getDenom20Pieces()
													.add(soiledRemittanceAllocation.getDenom10Pieces()
															.add(soiledRemittanceAllocation.getDenom5Pieces())))))))));

			soiledRemittanceAllocation.setTotalValueOfBankNotes(
					soiledRemittanceAllocation.getDenom2000Pieces().multiply(BigDecimal.valueOf(2000))
							.add(soiledRemittanceAllocation.getDenom1000Pieces().multiply(BigDecimal.valueOf(1000)))
							.add(soiledRemittanceAllocation.getDenom500Pieces().multiply(BigDecimal.valueOf(500)))
							.add(soiledRemittanceAllocation.getDenom100Pieces().multiply(BigDecimal.valueOf(100)))
							.add(soiledRemittanceAllocation.getDenom200Pieces().multiply(BigDecimal.valueOf(200)))
							.add(soiledRemittanceAllocation.getDenom50Pieces().multiply(BigDecimal.valueOf(50)))
							.add(soiledRemittanceAllocation.getDenom20Pieces().multiply(BigDecimal.valueOf(20)))
							.add(soiledRemittanceAllocation.getDenom10Pieces().multiply(BigDecimal.valueOf(10)))
							.add(soiledRemittanceAllocation.getDenom5Pieces().multiply(BigDecimal.valueOf(5))));
		}
		return mapList;
	}

}
