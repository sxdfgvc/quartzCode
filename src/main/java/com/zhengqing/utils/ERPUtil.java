package com.zhengqing.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ERPUtil {

    // 随机生成6位随机验证码
    public static String createRandomVcode(){
        //验证码
        String vcode = "";
        for (int i = 0; i < 6; i++) {
            vcode = vcode + (int)(Math.random() * 9);
        }
        System.out.println("vcode = " + vcode);
        return vcode;
    }
    
	   /**
	 * 根据指定的日期格式得到当前时间
	 * @param simpleDateFormat 日期格式，例如yyyy-MM-dd 或 yyyy-MM-dd hh:mm:ss 或 yyyy-MM-dd HH:mm:ss
	 * @return  返回当前的时间，如果出现异常，则返回""
	 */
	public static String getCurrentTime(String simpleDateFormat){
		TimeZone time = TimeZone.getTimeZone("GMT+8"); //设置为东八区
		TimeZone.setDefault(time);//全局 设置时区
		try
		{
			String currentTime = "";
			SimpleDateFormat dateFormat = new SimpleDateFormat(simpleDateFormat);
			currentTime = dateFormat.format(new Date());
			return currentTime;
		}
		catch(Exception e)
		{
			return "";
		}
	}
	
	// 获取当前时间
	public static String getCurrentTime(){
		return getCurrentTime("yyyy-MM-dd HH:mm:ss");
	}
	
	// 获取上几个月的第一天
	public static String getBeforeFirstMonthdate(int num) {
		SimpleDateFormat format=new SimpleDateFormat("yyyyMMdd");
		Calendar calendar=Calendar.getInstance();
		calendar.add(Calendar.MONTH, -num);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		return format.format(calendar.getTime());
	}
	// 获取上几个月的最后一天
	public static String getBeforeLastMonthdate(int num) {
		SimpleDateFormat format=new SimpleDateFormat("yyyyMMdd");
		Calendar calendar=Calendar.getInstance();
		int month=calendar.get(Calendar.MONTH);
		calendar.set(Calendar.MONTH, month-num);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		return format.format(calendar.getTime());
	}
	
	// 获取上几周的第一天
	public static String getBeforeFirstWeekdate(int num) {
		SimpleDateFormat format=new SimpleDateFormat("yyyyMMdd");
		Calendar calendar=Calendar.getInstance();
		calendar.add(Calendar.WEEK_OF_YEAR, -num);
		calendar.set(Calendar.DAY_OF_WEEK, 1);
		return format.format(calendar.getTime());
	}
	// 获取上几周的最后一天
	public static String getBeforeLastWeekdate(int num) {
		SimpleDateFormat format=new SimpleDateFormat("yyyyMMdd");
		Calendar calendar=Calendar.getInstance();
		int week=calendar.get(Calendar.WEEK_OF_YEAR);
		calendar.set(Calendar.WEEK_OF_YEAR, week-num);
		calendar.set(Calendar.DAY_OF_WEEK, calendar.getActualMaximum(Calendar.DAY_OF_WEEK));
		return format.format(calendar.getTime());
	}

	// 获取当前日期后延 N 天后日期
	public static int getTheDate(int bias){
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		try{
			String timeStr = "";
			Date date = dateFormat.parse(getCurrentTime("yyyyMMdd"));
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.add(Calendar.DATE, bias);
			Date date2 =  calendar.getTime();
			timeStr = dateFormat.format(date2);
			return Integer.parseInt(timeStr);
		}catch(Exception e){
			return 0;
		}
	}

	// 时间差 秒
	public static long getTimeDiff_sec(String time1,String time2) {  
		long sec = 0;
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
		    Date d1 = df.parse(time1); 
		    Date d2 = df.parse(time2);
		    long diff = d2.getTime() - d1.getTime();
		    sec = diff / 1000;
		}catch (Exception e) {
			
		}
		return sec;
	}
	
	// 时间差 分
	public static long getTimeDiff_min(String time1,String time2) {  
		long min = 0;
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try{
		    Date d1 = df.parse(time1);
		    Date d2 = df.parse(time2);
		    long diff = d2.getTime() - d1.getTime();
		    min = diff / (1000 * 60);
		}catch (Exception e){
			
		}
		return min;
	}
    
    //封装返回值
	public static Map<String,Object> returnMap(Object o, Integer pageNum, Long total){
		HashMap<String, Object> map = new HashMap<>();
		map.put("list",o);
		if(pageNum!=null){
			map.put("pageNum",pageNum);
		}
		if(total!=null){
			map.put("total",total);
		}
		return map;
	}
	//封装返回值
	public static Map<String,Object> returnMap(Object o){
		HashMap<String, Object> map = new HashMap<>();
		map.put("list",o);
		return map;
	}
	
	public static String replaceBlank(String str) {
		String dest = "";
		if (str!=null) {
			Pattern p = Pattern.compile("\r\n*|\n*|\r*");
			Matcher m = p.matcher(str);
			dest = m.replaceAll("");
			dest =dest.replaceAll("[ ]+<", "<");
		}
		return dest;
	}

	// 字符串md5(base64) 加密    （区别于md5加密）
	public static String md5EncryptBase64(String str) {
		String s = str;
		if (s == null) {
			return "";
		} else {
			String value = null;
			MessageDigest md5 = null;
			try {
				md5 = MessageDigest.getInstance("MD5");
			} catch (NoSuchAlgorithmException ex) {
				
			}
			sun.misc.BASE64Encoder baseEncoder = new sun.misc.BASE64Encoder();
			try {
				value = baseEncoder.encode(md5.digest(s.getBytes("utf-8")));
			} catch (Exception ex) {

			}
			return value;
		}
	}
	
}
