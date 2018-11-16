package com.khy.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {

	private static SimpleDateFormat dateFormat = new SimpleDateFormat();
	private static SimpleDateFormat sdf = new SimpleDateFormat("MMddHHmm");
	private static SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMddHHmmss");

	public static String getUid(){
		String uid = sdf.format(new Date());
		return uid;
	}
	
	public static String formatDate(Date date,String pattern){
		dateFormat.applyPattern("yyyy-MM-dd HH:mm:ss");
		String ret = dateFormat.format(date);
		return ret;
	}
	public static String formatDate(Date date){
		dateFormat.applyPattern("yyyy-MM-dd HH:mm:ss");
		String ret = dateFormat.format(date);
		return ret;
	}
	
	public static String getFileName(){
		String fileName = sdf1.format(new Date());
		return fileName;
	}
	
}
