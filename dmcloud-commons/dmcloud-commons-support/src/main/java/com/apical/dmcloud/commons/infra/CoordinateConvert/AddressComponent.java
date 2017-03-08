package com.apical.dmcloud.commons.infra.CoordinateConvert;

public class AddressComponent
{
	/**
	 * 国家
	 */
	private String country = null;
	
	/**
	 * 省份
	 */
	private String province = null;
	
	/**
	 * 城市
	 */
	private String city = null;
	
	/**
	 * 区县名
	 */
	private String district = null;
	
	/**
	 * 街道名
	 */
	private String street = null;

	/**
	 * 获取国家名称
	 * @return 国家名称
	 */
	public String getCountry()
	{
		return country;
	}

	/**
	 * 设置国家名称
	 * @param country 国家名称
	 */
	public void setCountry(String country)
	{
		this.country = country;
	}

	/**
	 * 获取国家名称
	 * @return 国家名称
	 */
	public String getProvince()
	{
		return province;
	}

	/**
	 * 设置省份名称
	 * @param province 省份名称
	 */
	public void setProvince(String province)
	{
		this.province = province;
	}

	/**
	 * 获取省份名称
	 * @return 省份名称
	 */
	public String getCity()
	{
		return city;
	}

	/**
	 * 设置城市名称
	 * @param city 城市名称
	 */
	public void setCity(String city)
	{
		this.city = city;
	}

	/**
	 * 获取区县名
	 * @return 区县名
	 */
	public String getDistrict()
	{
		return district;
	}

	/**
	 * 设置区县名
	 * @param district 区县名
	 */
	public void setDistrict(String district)
	{
		this.district = district;
	}

	/**
	 * 获取街道名
	 * @return 街道名
	 */
	public String getStreet()
	{
		return street;
	}

	/**
	 * 设置街道名
	 * @param street 街道名
	 */
	public void setStreet(String street)
	{
		this.street = street;
	}
}
