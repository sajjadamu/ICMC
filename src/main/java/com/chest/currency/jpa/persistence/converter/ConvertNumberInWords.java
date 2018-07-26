/*******************************************************************************
 * /* Copyright (C) Indicsoft Technologies Pvt Ltd
 * * All Rights Reserved.
 *******************************************************************************/
package com.chest.currency.jpa.persistence.converter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConvertNumberInWords {
	
	private static final Logger LOG = LoggerFactory.getLogger(ConvertNumberInWords.class);
	
	private static String getInWords(int n, String ch)
	  {
	    String  one[]={" "," One"," Two"," Three"," Four"," Five"," Six"," Seven"," Eight"," Nine"," Ten"," Eleven"," Twelve"," Thirteen"," Fourteen","Fifteen"," Sixteen"," Seventeen"," Eighteen"," Nineteen"};
	    String ten[]={" "," "," Twenty"," Thirty"," Forty"," Fifty"," Sixty","Seventy"," Eighty"," Ninety"};
	    if(n > 19) {
	    	LOG.info(ten[n/10]+" "+one[n%10]);
	    	if(n > 0){
	    		return ten[n/10]+" "+one[n%10]+" "+ch;
	    	}else{
	    		return ten[n/10]+" "+one[n%10];
	    	}
	    }else{ 
	    	LOG.info(one[n]);
	    	if(n > 0){
	    		return one[n]+" "+ch;
	    	}else{
	    		return one[n];
	    	}
	    }
	  }
	
	public static String getNumberInWords(int number){
		String numInwords = "";
		if(number <= 0){                  
		      LOG.info("Enter numbers greater than 0");
		}else{
			numInwords = numInwords +" "+ getInWords((number/1000000000)," Hundred");
			numInwords = numInwords +" "+ getInWords((number/10000000)%100," Crore");
			numInwords = numInwords +" "+ getInWords(((number/100000)%100)," Lakh");
			numInwords = numInwords +" "+ getInWords(((number/1000)%100)," Thousand");
			numInwords = numInwords +" "+ getInWords(((number/100)%10)," Hundred");
			numInwords = numInwords +" "+ getInWords((number%100)," ");
		}
		return numInwords;
	}
	

}
