package com.chest.currency.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HtmlUtils {
    public final static String nameRegex="^[a-zA-Z0-9\\w\\s_]{1,45}$";

    public final static String hifn="^[(.)\\_]+$";

    public final static String space="^([a-zA-Z0-9_]+([\\s]{0,1})([a-zA-Z0-9])*)*$";
    

    public static boolean isHtml(String s) {
    	boolean flag = false;
    Matcher matcher2=Pattern.compile(hifn).matcher(s);
    if(matcher2.matches())
    {
       // System.out.println("Error");
        flag=false;
    }
    Matcher matcher1=Pattern.compile(space).matcher(s);
    if(!matcher1.matches())
    {
       // System.out.println(" ERROR"); 
    	flag = false;
    }
    if(!s.isEmpty())
    {    
    if(s.charAt(0)==' ' || s.charAt(0)=='1' ||s.charAt(0)=='2' ||s.charAt(0)=='3' ||s.charAt(0)=='4' ||s.charAt(0)=='5' ||s.charAt(0)=='6' ||s.charAt(0)=='7' ||s.charAt(0)=='8' ||s.charAt(0)=='9' || s.charAt(0)=='0')
        //System.out.println(" ERROR"); 
    	flag = false;
    }
    Matcher matcher=Pattern.compile(nameRegex).matcher(s);
    
    if(matcher.matches()){
       // System.out.println("success");
        //flag = "success";
    	flag=true;
    }
	return flag;
    
    }
}