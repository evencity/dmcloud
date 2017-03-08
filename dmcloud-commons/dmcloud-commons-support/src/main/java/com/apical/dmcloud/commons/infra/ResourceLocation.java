package com.apical.dmcloud.commons.infra;

/**
 * 资源存储位置信息
 * @author qiuzeng
 *
 */
public enum ResourceLocation
{
	/**
	 * 未知的，未定义的
	 */
	Undifined,
	
	/**
	 * 百度bcs服务
	 */
	Location_BCS,
	
	/**
	 * 本地hadoop集群
	 */
	Location_Hadoop,
	
	/**
	 * 阿里云oss服务
	 */
	Location_OSS,
}
