package com.apical.dmcloud.commons.infra;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * gps数据信息
 * @author qiuzeng
 *
 */
public class GPSDataInfo implements Serializable
{
	private static final long serialVersionUID = 1L;

	/**
	 * 日期输出格式
	 */
	private static DateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	
	/**
	 * 速度（单位为千米/小时，即km/h）
	 */
	private float speed = 0.0f;
	
	/**
	 * 时间
	 */
	private Date time = null;
	
	/**
	 * 经度
	 */
	private float longitude = 0.0f;
	
	/**
	 * 纬度
	 */
	private float latitude = 0.0f;

	/**
	 * 获取速度（单位为千米/小时，即km/h）
	 * @return 速度
	 */
	public float getSpeed()
	{
		return speed;
	}

	/**
	 * 设置速度（单位为千米/小时，即km/h）
	 * @param speed 速度
	 */
	public void setSpeed(float speed)
	{
		this.speed = speed;
	}

	/**
	 * 获取时间
	 * @return 时间
	 */
	public Date getTime()
	{
		return time;
	}

	/**
	 * 设置时间
	 * @param time 时间
	 */
	public void setTime(Date time)
	{
		this.time = time;
	}

	/**
	 * 获取经度
	 * @return 经度
	 */
	public float getLongitude()
	{
		return longitude;
	}

	/**
	 * 设置经度
	 * @param longitude 经度
	 */
	public void setLongitude(float longitude)
	{
		this.longitude = longitude;
	}

	/**
	 * 获取纬度
	 * @return 纬度
	 */
	public float getLatitude()
	{
		return latitude;
	}

	/**
	 * 设置纬度
	 * @param latitude 纬度
	 */
	public void setLatitude(float latitude)
	{
		this.latitude = latitude;
	}
	
	@Override
	public String toString()
	{
		//输出格式：时间,经度,纬度,速度
		String output = timeFormat.format(this.time) + "," + this.longitude + "," + this.latitude
				+ "," + this.speed;
		
		return output;
	}
	
	/**
	 * 解析gps字符串信息
	 * @param value gps字符串
	 * @return gps信息
	 * @throws ParseException 
	 */
	public static GPSDataInfo parse(String value) throws ParseException
	{
		if(value == null)
		{
			return null;
		}
		
		String[] components = value.split(",");
		if((components == null) || (components.length != 4))
		{
			return null;
		}
		
		GPSDataInfo info = new GPSDataInfo();
		info.setTime(timeFormat.parse(components[0]));
		info.setLongitude(Float.parseFloat(components[1]));
		info.setLatitude(Float.parseFloat(components[2]));
		info.setSpeed(Float.parseFloat(components[3]));
		
		return info;
	}
}
