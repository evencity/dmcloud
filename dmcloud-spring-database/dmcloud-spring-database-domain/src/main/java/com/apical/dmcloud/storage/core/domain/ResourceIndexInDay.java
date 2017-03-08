package com.apical.dmcloud.storage.core.domain;

public class ResourceIndexInDay
{
	public ResourceIndexInDay()
	{	
	}
	
	/**
	 * 资源所属的车辆ID
	 */
	private Long vehicleId = 0L;
	
	/**
	 * 图片实际数量
	 * 当增加或删除报警图片后，该数值也要相应地增加或减少
	 */
	private long pictureCount = 0;
	
	/**
	 * 录像实际数量
	 * 当增加或删除报警图片后，该数值也要相应地增加或减少
	 */
	private long videoCount = 0;
	
	/**
	 * 年份
	 */
	private int year = 2015;
	
	/**
	 * 月份
	 */
	private int month = 1;
	
	/**
	 * 天数
	 */
	private int day = 1;

	/**
	 * 获取车辆信息id
	 * @return 车辆信息id
	 */
	public Long getVehicleId() {
		return vehicleId;
	}

	/**
	 * 设置车辆信息id
	 * @param vehicleId 车辆信息id
	 */
	public void setVehicleId(Long vehicleId) {
		this.vehicleId = vehicleId;
	}

	/**
	 * 获取报警图片实际数量
	 * @return 图片实际数量
	 */
	public long getPictureCount()
	{
		return pictureCount;
	}

	/**
	 * 设置报警图片实际数量
	 * @param pictureCount 图片实际数量
	 */
	public void setPictureCount(long pictureCount)
	{
		this.pictureCount = pictureCount;
	}

	/**
	 * 获取录像实际数量
	 * @return 录像实际数量
	 */
	public long getVideoCount()
	{
		return videoCount;
	}

	/**
	 * 设置录像实际数量
	 * @param videoCount 录像实际数量
	 */
	public void setVideoCount(long videoCount)
	{
		this.videoCount = videoCount;
	}

	/**
	 * 获取年份
	 * @return 年份
	 */
	public int getYear()
	{
		return year;
	}

	/**
	 * 设置年份
	 * @param year 年份
	 */
	public void setYear(int year)
	{
		this.year = year;
	}

	/**
	 * 获取月份
	 * @return 月份
	 */
	public int getMonth()
	{
		return month;
	}

	/**
	 * 设置月份
	 * @param month 月份
	 */
	public void setMonth(int month)
	{
		this.month = month;
	}

	/**
	 * 获取天数
	 * @return 天数
	 */
	public int getDay()
	{
		return day;
	}

	/**
	 * 设置天数
	 * @param day 天数
	 */
	public void setDay(int day)
	{
		this.day = day;
	}
}
