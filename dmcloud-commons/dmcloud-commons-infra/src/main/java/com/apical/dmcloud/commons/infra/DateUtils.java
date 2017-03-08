package com.apical.dmcloud.commons.infra;

import java.util.Calendar;
import java.util.Date;

public class DateUtils
{
	/**
	 * 获得当前日期的前几天或后几天的具体日期。
	 * @param now 当前日期
	 * @param day 前几天（day < 0），或后几天（day > 0）
	 * @return 目标日期
	 */
	public static Date getDateByDayInterval(Date now, int day)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(now);
		cal.set(Calendar.DATE, cal.get(Calendar.DATE) + day);
		
		return cal.getTime();
	}
	
	/**
	 * 比较两个时间的大小（不比较日期，只比较时间）
	 * @param date1 时间1
	 * @param date2 时间2
	 * @return 大于0表示时间1晚于时间2，0表示时间相同，小于0表示时间1早于时间2
	 */
	public static int compareTimeInDay(Date date1, Date date2)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(date1);
		int hour1 = cal.get(Calendar.HOUR_OF_DAY);
		int minute1 = cal.get(Calendar.MINUTE);
		int second1 = cal.get(Calendar.SECOND);
		int millisecond1 = cal.get(Calendar.MILLISECOND);
		
		cal.setTime(date2);
		int hour2 = cal.get(Calendar.HOUR_OF_DAY);
		int minute2 = cal.get(Calendar.MINUTE);
		int second2 = cal.get(Calendar.SECOND);
		int millisecond2 = cal.get(Calendar.MILLISECOND);
		
		if(hour1 > hour2)
		{
			return 1;
		}
		else if(hour1 < hour2)
		{
			return -1;
		}
		else if(minute1 > minute2)
		{
			return 1;
		}
		else if(minute1 < minute2)
		{
			return -1;
		}
		else if(second1 > second2)
		{
			return 1;
		}
		else if(second1 < second2)
		{
			return -1;
		}
		else if(millisecond1 > millisecond2)
		{
			return 1;
		}
		else if(millisecond1 < millisecond2)
		{
			return -1;
		}
		else
		{
			return 0;
		}
	}
	
	/**
	 * 判断时间是否为0点
	 * @param date 时间
	 * @return 时间是否为0点
	 */
	public static boolean isZeroClock(Date date)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		int minute = cal.get(Calendar.MINUTE);
		int second = cal.get(Calendar.SECOND);
		int millisecond = cal.get(Calendar.MILLISECOND);
		
		if(hour == 0 && minute == 0 && second == 0 && millisecond == 0)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * 判断是否在两个时间范围内（不比较日期，只比较时间）
	 * @param time 目标时间
	 * @param beginTime 开始时间
	 * @param endTime 结束时间
	 * @return 是否在范围之内
	 */
	public static boolean isBetweenInDay(Date time, Date beginTime, Date endTime)
	{
		int compare = compareTimeInDay(beginTime, endTime);
		if(compare > 0)
		{
			//开始时间大于结束时间，可能是：晚上10点到凌晨2点
			int compareWithBeginTime = compareTimeInDay(time, beginTime);
			int compareWithEndTime = compareTimeInDay(time, endTime);
			if(compareWithBeginTime >= 0 || compareWithEndTime <= 0)
			{
				//在规定的时间范围之内
				return true;
			}
		}
		else
		{
			//开始时间小于结束时间，可能是：晚上9点到晚上11点
			int compareWithBeginTime = compareTimeInDay(time, beginTime);
			int compareWithEndTime = compareTimeInDay(time, endTime);
			if(compareWithBeginTime >= 0 && compareWithEndTime <= 0)
			{
				//在规定的时间范围之内
				return true;
			}
		}
		
		return false;
	}
}
