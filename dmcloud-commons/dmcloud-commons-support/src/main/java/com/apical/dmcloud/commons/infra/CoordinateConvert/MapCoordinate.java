package com.apical.dmcloud.commons.infra.CoordinateConvert;

/**
 * 经纬度坐标
 * @author qiuzeng
 *
 */
public class MapCoordinate
{
	/**
	 * longitude：经度值
	 */
	private double longitude = 0;
	
	/**
	 * latitude：纬度值
	 */
	private double latitude = 0;
	
	public MapCoordinate()
	{
		this.longitude = 0;
		this.latitude = 0;
	}
	
	/**
	 * 构造函数：MapCoodinate(double longitude, double latitude)
	 * 直接利用参数初始化经纬度值
	 * @param latitude - 经度值
	 * @param longitude - 经度值
	 */
	public MapCoordinate(double latitude, double longitude)
	{
		this.latitude = latitude;
		this.longitude = longitude;
	}

	/**
	 * 获取经度值
	 * @return 经度值
	 */
	public double getLongitude()
	{
		return longitude;
	}

	public void setLongitude(double longitude)
	{
		this.longitude = longitude;
	}

	public double getLatitude()
	{
		return latitude;
	}

	public void setLatitude(double latitude)
	{
		this.latitude = latitude;
	}
}
