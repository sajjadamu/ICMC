package com.chest.currency.entity.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Calendar;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import com.chest.currency.enums.BinCategoryType;
import com.chest.currency.enums.DateTimePattern;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;


@Entity(name = "Sas")
@Table(name = "SAS")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Data
@EqualsAndHashCode(of = { "id" })
@ToString
public class Sas {

	@Id
	@Column(name = "ID")
	@GeneratedValue(generator = "SAS_SEQ")
	@SequenceGenerator(name = "SAS_SEQ", sequenceName = "SAS_SEQ", allocationSize = 100)
	protected Long id;

	@Basic
	@Column(name = "SR_NO")
	protected String srNo;

	@Basic
	@Column(name = "SOL_ID")
	protected String solID;

	@Basic
	@Column(name = "BRANCH")
	protected String branch;

	@Basic
	@Column(name = "TOTAL_VALUE")
	protected BigDecimal totalValue;

	@Basic
	@Column(name = "TOTAL_VALUE_OF_COINS_RS_1")
	protected BigDecimal totalValueOfCoinsRs1;

	@Basic
	@Column(name = "TOTAL_VALUE_OF_COINS_RS_10")
	protected BigDecimal totalValueOfCoinsRs10;

	@Basic
	@Column(name = "TOTAL_VALUE_OF_COINS_RS_2")
	protected BigDecimal totalValueOfCoinsRs2;

	@Basic
	@Column(name = "TOTAL_VALUE_OF_COINS_RS_5")
	protected BigDecimal totalValueOfCoinsRs5;

	@Basic
	@Column(name = "TOTAL_VALUE_OF_NOTES_RS_1_F")
	protected BigDecimal totalValueOfNotesRs1F;

	@Basic
	@Column(name = "TOTAL_VALUE_OF_NOTES_RS_10_F")
	protected BigDecimal totalValueOfNotesRs10F;

	@Basic
	@Column(name = "TOTAL_VALUE_OF_NOTES_RS_100_F")
	protected BigDecimal totalValueOfNotesRs100F;

	@Basic
	@Column(name = "TOTAL_VALUE_OF_NOTES_RS_1000_F")
	protected BigDecimal totalValueOfNotesRs1000F;

	@Basic
	@Column(name = "TOTAL_VALUE_OF_NOTES_RS_2_F")
	protected BigDecimal totalValueOfNotesRs2F;

	@Basic
	@Column(name = "TOTAL_VALUE_OF_NOTES_RS_20_F")
	protected BigDecimal totalValueOfNotesRs20F;

	@Basic
	@Column(name = "TOTAL_VALUE_OF_NOTES_RS_5_F")
	protected BigDecimal totalValueOfNotesRs5F;

	@Basic
	@Column(name = "TOTAL_VALUE_OF_NOTES_RS_50_F")
	protected BigDecimal totalValueOfNotesRs50F;

	@Basic
	@Column(name = "TOTAL_VALUE_OF_NOTES_RS_500_F")
	protected BigDecimal totalValueOfNotesRs500F;

	@Basic
	@Column(name = "TOTAL_VALUE_OF_NOTES_RS_1_I")
	protected BigDecimal totalValueOfNotesRs1I;

	@Basic
	@Column(name = "TOTAL_VALUE_OF_NOTES_RS_10_I")
	protected BigDecimal totalValueOfNotesRs10I;

	@Basic
	@Column(name = "TOTAL_VALUE_OF_NOTES_RS_100_I")
	protected BigDecimal totalValueOfNotesRs100I;

	@Basic
	@Column(name = "TOTAL_VALUE_OF_NOTES_RS_1000_I")
	protected BigDecimal totalValueOfNotesRs1000I;

	@Basic
	@Column(name = "TOTAL_VALUE_OF_NOTES_RS_2_I")
	protected BigDecimal totalValueOfNotesRs2I;

	@Basic
	@Column(name = "TOTAL_VALUE_OF_NOTES_RS_20_I")
	protected BigDecimal totalValueOfNotesRs20I;

	@Basic
	@Column(name = "TOTAL_VALUE_OF_NOTES_RS_5_I")
	protected BigDecimal totalValueOfNotesRs5I;

	@Basic
	@Column(name = "TOTAL_VALUE_OF_NOTES_RS_50_I")
	protected BigDecimal totalValueOfNotesRs50I;

	@Basic
	@Column(name = "TOTAL_VALUE_OF_NOTES_RS_500_I")
	protected BigDecimal totalValueOfNotesRs500I;

	@Basic
	@Column(name = "DATE_OF_DISPATCH")
	protected String dateOfDispatch;

	@Basic
	@Column(name = "ACTION_TAKEN")
	protected String actionTaken;

	@Basic
	@Column(name = "INSERT_BY")
	protected String insertBy;

	@Basic
	@Column(name = "UPDATE_BY")
	protected String updateBy;

	@DateTimeFormat(pattern = DateTimePattern.yyyy_MM_dd_HH_mm_ss_SSS)
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "INSERT_TIME", updatable = false, nullable = false)
	@CreationTimestamp
	protected Calendar insertTime;

	@DateTimeFormat(pattern = DateTimePattern.yyyy_MM_dd_HH_mm_ss_SSS)
	@Column(name = "UPDATE_TIME", nullable = false)
	@UpdateTimestamp
	protected Calendar updateTime;

	@Basic
	@Column(name = "ICMC_ID")
	protected BigInteger icmcId;

	@Transient
	protected Integer denomination;

	@Transient
	@Enumerated(EnumType.STRING)
	protected BinCategoryType binCategoryType;

	@Basic
	@Column(name = "RELEASED_NOTES_1_I")
	protected BigDecimal releasedNotes1I;

	@Basic
	@Column(name = "RELEASED_NOTES_1_F")
	protected BigDecimal releasedNotes1F;

	@Basic
	@Column(name = "RELEASED_NOTES_10_I")
	protected BigDecimal releasedNotes10I;

	@Basic
	@Column(name = "RELEASED_NOTES_10_F")
	protected BigDecimal releasedNotes10F;

	@Basic
	@Column(name = "RELEASED_NOTES_100_I")
	protected BigDecimal releasedNotes100I;

	@Basic
	@Column(name = "RELEASED_NOTES_100_F")
	protected BigDecimal releasedNotes100F;

	@Basic
	@Column(name = "RELEASED_NOTES_100_A")
	protected BigDecimal releasedNotes100A;

	@Basic
	@Column(name = "RELEASED_NOTES_1000_I")
	protected BigDecimal releasedNotes1000I;

	@Basic
	@Column(name = "RELEASED_NOTES_1000_F")
	protected BigDecimal releasedNotes1000F;

	@Basic
	@Column(name = "RELEASED_NOTES_1000_A")
	protected BigDecimal releasedNotes1000A;

	@Basic
	@Column(name = "RELEASED_NOTES_2_I")
	protected BigDecimal releasedNotes2I;

	@Basic
	@Column(name = "RELEASED_NOTES_2_F")
	protected BigDecimal releasedNotes2F;

	@Basic
	@Column(name = "RELEASED_NOTES_20_I")
	protected BigDecimal releasedNotes20I;

	@Basic
	@Column(name = "RELEASED_NOTES_20_F")
	protected BigDecimal releasedNotes20F;

	@Basic
	@Column(name = "RELEASED_NOTES_5_I")
	protected BigDecimal releasedNotes5I;

	@Basic
	@Column(name = "RELEASED_NOTES_5_F")
	protected BigDecimal releasedNotes5F;

	@Basic
	@Column(name = "RELEASED_NOTES_50_I")
	protected BigDecimal releasedNotes50I;

	@Basic
	@Column(name = "RELEASED_NOTES_50_F")
	protected BigDecimal releasedNotes50F;

	@Basic
	@Column(name = "RELEASED_NOTES_500_I")
	protected BigDecimal releasedNotes500I;

	@Basic
	@Column(name = "RELEASED_NOTES_500_F")
	protected BigDecimal releasedNotes500F;

	@Basic
	@Column(name = "RELEASED_NOTES_500_A")
	protected BigDecimal releasedNotes500A;

	@Basic
	@Column(name = "TOTAL_VALUE_OF_NOTES_RS_100_A")
	protected BigDecimal totalValueOfNotesRs100A;

	@Basic
	@Column(name = "TOTAL_VALUE_OF_NOTES_RS_1000_A")
	protected BigDecimal totalValueOfNotesRs1000A;

	@Basic
	@Column(name = "RELEASED_COINS_1")
	protected BigDecimal releasedCoins1;

	@Basic
	@Column(name = "RELEASED_COINS_2")
	protected BigDecimal releasedCoins2;

	@Basic
	@Column(name = "RELEASED_COINS_5")
	protected BigDecimal releasedCoins5;

	@Basic
	@Column(name = "RELEASED_COINS_10")
	protected BigDecimal releasedCoins10;

	@Basic
	@Column(name = "TOTAL_VALUE_OF_NOTES_RS_500_A")
	protected BigDecimal totalValueOfNotesRs500A;

	@Basic
	@Column(name = "FILE_NAME")
	protected String fileName;

	@Basic
	@Column(name = "ENABLE_DISABLE")
	protected int enabledisable;

	@Basic
	@Column(name = "RELEASED_NOTES_2000_I")
	protected BigDecimal releasedNotes2000I;

	@Basic
	@Column(name = "RELEASED_NOTES_2000_F")
	protected BigDecimal releasedNotes2000F;

	@Basic
	@Column(name = "RELEASED_NOTES_2000_A")
	protected BigDecimal releasedNotes2000A;

	@Basic
	@Column(name = "TOTAL_VALUE_OF_NOTES_RS_2000_A")
	protected BigDecimal totalValueOfNotesRs2000A;

	@Basic
	@Column(name = "TOTAL_VALUE_OF_NOTES_RS_2000_F")
	protected BigDecimal totalValueOfNotesRs2000F;

	@Basic
	@Column(name = "TOTAL_VALUE_OF_NOTES_RS_2000_I")
	protected BigDecimal totalValueOfNotesRs2000I;

	@Basic
	@Column(name = "RELEASED_NOTES_200_I")
	protected BigDecimal releasedNotes200I;

	@Basic
	@Column(name = "RELEASED_NOTES_200_F")
	protected BigDecimal releasedNotes200F;

	@Basic
	@Column(name = "RELEASED_NOTES_200_A")
	protected BigDecimal releasedNotes200A;

	@Basic
	@Column(name = "TOTAL_VALUE_OF_NOTES_RS_200_A")
	protected BigDecimal totalValueOfNotesRs200A;

	@Basic
	@Column(name = "TOTAL_VALUE_OF_NOTES_RS_200_F")
	protected BigDecimal totalValueOfNotesRs200F;

	@Basic
	@Column(name = "TOTAL_VALUE_OF_NOTES_RS_200_I")
	protected BigDecimal totalValueOfNotesRs200I;

	@Basic
	@Column(name = "TOTAL_VALUE_OF_NOTES_RS_1_U")
	protected BigDecimal totalValueOfNotesRs1U;

	@Basic
	@Column(name = "RELEASED_NOTES_1_U")
	protected BigDecimal releasedNotes1U;

	@Basic
	@Column(name = "TOTAL_VALUE_OF_NOTES_RS_2_U")
	protected BigDecimal totalValueOfNotesRs2U;

	@Basic
	@Column(name = "RELEASED_NOTES_2_U")
	protected BigDecimal releasedNotes2U;

	@Basic
	@Column(name = "TOTAL_VALUE_OF_NOTES_RS_5_U")
	protected BigDecimal totalValueOfNotesRs5U;

	@Basic
	@Column(name = "RELEASED_NOTES_5_U")
	protected BigDecimal releasedNotes5U;

	@Basic
	@Column(name = "TOTAL_VALUE_OF_NOTES_RS_10_U")
	protected BigDecimal totalValueOfNotesRs10U;

	@Basic
	@Column(name = "RELEASED_NOTES_10_U")
	protected BigDecimal releasedNotes10U;

	@Basic
	@Column(name = "TOTAL_VALUE_OF_NOTES_RS_20_U")
	protected BigDecimal totalValueOfNotesRs20U;

	@Basic
	@Column(name = "RELEASED_NOTES_20_U")
	protected BigDecimal releasedNotes20U;

	@Basic
	@Column(name = "TOTAL_VALUE_OF_NOTES_RS_50_U")
	protected BigDecimal totalValueOfNotesRs50U;

	@Basic
	@Column(name = "RELEASED_NOTES_50_U")
	protected BigDecimal releasedNotes50U;

	@Basic
	@Column(name = "TOTAL_VALUE_OF_NOTES_RS_100_U")
	protected BigDecimal totalValueOfNotesRs100U;

	@Basic
	@Column(name = "RELEASED_NOTES_100_U")
	protected BigDecimal releasedNotes100U;

	@Basic
	@Column(name = "TOTAL_VALUE_OF_NOTES_RS_200_U")
	protected BigDecimal totalValueOfNotesRs200U;

	@Basic
	@Column(name = "RELEASED_NOTES_200_U")
	protected BigDecimal releasedNotes200U;

	@Basic
	@Column(name = "TOTAL_VALUE_OF_NOTES_RS_500_U")
	protected BigDecimal totalValueOfNotesRs500U;

	@Basic
	@Column(name = "RELEASED_NOTES_500_U")
	protected BigDecimal releasedNotes500U;

	@Basic
	@Column(name = "TOTAL_VALUE_OF_NOTES_RS_2000_U")
	protected BigDecimal totalValueOfNotesRs2000U;

	@Basic
	@Column(name = "RELEASED_NOTES_2000_U")
	protected BigDecimal releasedNotes2000U;

	@Basic
	@Column(name = "STATUS")
	protected int status;

	@Basic
	@Column(name = "PROCESSED_OR_UNPROCESSED")
	protected String processedOrUnprocessed;

	@Basic
	@Column(name = "TOTAL_VALUE_OF_NOTES_RS_1_S")
	protected BigDecimal totalValueOfNotesRs1S;

	@Basic
	@Column(name = "TOTAL_VALUE_OF_NOTES_RS_2_S")
	protected BigDecimal totalValueOfNotesRs2S;

	@Basic
	@Column(name = "TOTAL_VALUE_OF_NOTES_RS_5_S")
	protected BigDecimal totalValueOfNotesRs5S;

	@Basic
	@Column(name = "TOTAL_VALUE_OF_NOTES_RS_10_S")
	protected BigDecimal totalValueOfNotesRs10S;

	@Basic
	@Column(name = "TOTAL_VALUE_OF_NOTES_RS_20_S")
	protected BigDecimal totalValueOfNotesRs20S;

	@Basic
	@Column(name = "TOTAL_VALUE_OF_NOTES_RS_50_S")
	protected BigDecimal totalValueOfNotesRs50S;

	@Basic
	@Column(name = "TOTAL_VALUE_OF_NOTES_RS_100_S")
	protected BigDecimal totalValueOfNotesRs100S;

	@Basic
	@Column(name = "TOTAL_VALUE_OF_NOTES_RS_200_S")
	protected BigDecimal totalValueOfNotesRs200S;

	@Basic
	@Column(name = "TOTAL_VALUE_OF_NOTES_RS_500_S")
	protected BigDecimal totalValueOfNotesRs500S;

	@Basic
	@Column(name = "TOTAL_VALUE_OF_NOTES_RS_1000_S")
	protected BigDecimal totalValueOfNotesRs1000S;

	@Basic
	@Column(name = "TOTAL_VALUE_OF_NOTES_RS_2000_S")
	protected BigDecimal totalValueOfNotesRs2000S;

	/**
	 * Default Constructor
	 */
	public Sas() {

	}

	public Sas(boolean initialize) {
		if (initialize) {
			// Initialize to zero
			this.totalValue = BigDecimal.ZERO;

			this.totalValueOfCoinsRs1 = BigDecimal.ZERO;
			this.totalValueOfCoinsRs2 = BigDecimal.ZERO;
			this.totalValueOfCoinsRs5 = BigDecimal.ZERO;
			this.totalValueOfCoinsRs10 = BigDecimal.ZERO;

			this.totalValueOfNotesRs1F = BigDecimal.ZERO;
			this.totalValueOfNotesRs1I = BigDecimal.ZERO;

			this.totalValueOfNotesRs2F = BigDecimal.ZERO;
			this.totalValueOfNotesRs2I = BigDecimal.ZERO;

			this.totalValueOfNotesRs5F = BigDecimal.ZERO;
			this.totalValueOfNotesRs5I = BigDecimal.ZERO;

			this.totalValueOfNotesRs10F = BigDecimal.ZERO;
			this.totalValueOfNotesRs10I = BigDecimal.ZERO;

			this.totalValueOfNotesRs20F = BigDecimal.ZERO;
			this.totalValueOfNotesRs20I = BigDecimal.ZERO;

			this.totalValueOfNotesRs50F = BigDecimal.ZERO;
			this.totalValueOfNotesRs50I = BigDecimal.ZERO;

			this.totalValueOfNotesRs100F = BigDecimal.ZERO;
			this.totalValueOfNotesRs100A = BigDecimal.ZERO;
			this.totalValueOfNotesRs100I = BigDecimal.ZERO;

			this.totalValueOfNotesRs500F = BigDecimal.ZERO;
			this.totalValueOfNotesRs500A = BigDecimal.ZERO;
			this.totalValueOfNotesRs500I = BigDecimal.ZERO;

			this.totalValueOfNotesRs1000F = BigDecimal.ZERO;
			this.totalValueOfNotesRs1000A = BigDecimal.ZERO;
			this.totalValueOfNotesRs1000I = BigDecimal.ZERO;

			this.totalValueOfNotesRs2000F = BigDecimal.ZERO;
			this.totalValueOfNotesRs2000A = BigDecimal.ZERO;
			this.totalValueOfNotesRs2000I = BigDecimal.ZERO;

			this.totalValueOfNotesRs200F = BigDecimal.ZERO;
			this.totalValueOfNotesRs200A = BigDecimal.ZERO;
			this.totalValueOfNotesRs200I = BigDecimal.ZERO;

			this.totalValueOfNotesRs1U = BigDecimal.ZERO;
			this.totalValueOfNotesRs2U = BigDecimal.ZERO;
			this.totalValueOfNotesRs5U = BigDecimal.ZERO;
			this.totalValueOfNotesRs10U = BigDecimal.ZERO;
			this.totalValueOfNotesRs20U = BigDecimal.ZERO;
			this.totalValueOfNotesRs50U = BigDecimal.ZERO;
			this.totalValueOfNotesRs100U = BigDecimal.ZERO;
			this.totalValueOfNotesRs200U = BigDecimal.ZERO;
			this.totalValueOfNotesRs500U = BigDecimal.ZERO;
			this.totalValueOfNotesRs2000U = BigDecimal.ZERO;

			this.totalValueOfNotesRs1S = BigDecimal.ZERO;
			this.totalValueOfNotesRs2S = BigDecimal.ZERO;
			this.totalValueOfNotesRs5S = BigDecimal.ZERO;
			this.totalValueOfNotesRs10S = BigDecimal.ZERO;
			this.totalValueOfNotesRs20S = BigDecimal.ZERO;
			this.totalValueOfNotesRs50S = BigDecimal.ZERO;
			this.totalValueOfNotesRs100S = BigDecimal.ZERO;
			this.totalValueOfNotesRs200S = BigDecimal.ZERO;
			this.totalValueOfNotesRs500S = BigDecimal.ZERO;
			this.totalValueOfNotesRs1000S = BigDecimal.ZERO;
			this.totalValueOfNotesRs2000S = BigDecimal.ZERO;

			this.releasedNotes1I = BigDecimal.ZERO;
			this.releasedNotes1F = BigDecimal.ZERO;

			this.releasedNotes2I = BigDecimal.ZERO;
			this.releasedNotes2F = BigDecimal.ZERO;

			this.releasedNotes5I = BigDecimal.ZERO;
			this.releasedNotes5F = BigDecimal.ZERO;

			this.releasedNotes10I = BigDecimal.ZERO;
			this.releasedNotes10F = BigDecimal.ZERO;

			this.releasedNotes20I = BigDecimal.ZERO;
			this.releasedNotes20F = BigDecimal.ZERO;

			this.releasedNotes50I = BigDecimal.ZERO;
			this.releasedNotes50F = BigDecimal.ZERO;

			this.releasedNotes100I = BigDecimal.ZERO;
			this.releasedNotes100F = BigDecimal.ZERO;
			this.releasedNotes100A = BigDecimal.ZERO;

			this.releasedNotes500I = BigDecimal.ZERO;
			this.releasedNotes500F = BigDecimal.ZERO;
			this.releasedNotes500A = BigDecimal.ZERO;

			this.releasedNotes1000I = BigDecimal.ZERO;
			this.releasedNotes1000F = BigDecimal.ZERO;
			this.releasedNotes1000A = BigDecimal.ZERO;

			this.releasedNotes2000I = BigDecimal.ZERO;
			this.releasedNotes2000F = BigDecimal.ZERO;
			this.releasedNotes2000A = BigDecimal.ZERO;

			this.releasedNotes200I = BigDecimal.ZERO;
			this.releasedNotes200F = BigDecimal.ZERO;
			this.releasedNotes200A = BigDecimal.ZERO;

			this.releasedNotes1U = BigDecimal.ZERO;
			this.releasedNotes2U = BigDecimal.ZERO;
			this.releasedNotes5U = BigDecimal.ZERO;
			this.releasedNotes10U = BigDecimal.ZERO;
			this.releasedNotes20U = BigDecimal.ZERO;
			this.releasedNotes50U = BigDecimal.ZERO;
			this.releasedNotes100U = BigDecimal.ZERO;
			this.releasedNotes200U = BigDecimal.ZERO;
			this.releasedNotes500U = BigDecimal.ZERO;
			this.releasedNotes2000U = BigDecimal.ZERO;
		}
	}
}
