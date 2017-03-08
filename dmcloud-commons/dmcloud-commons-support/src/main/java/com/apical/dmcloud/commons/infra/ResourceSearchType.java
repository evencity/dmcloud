package com.apical.dmcloud.commons.infra;

/**
 * 资源搜索类型
 * @author qiuzeng
 *
 */
public enum ResourceSearchType
{
	/**
	 * 未定义
	 */
	Undefined,
	
	/**
	 * 搜索所有图片
	 */
	All,
	
	/**
	 * 搜索正常图片，非报警图片
	 */
	Normal,
	
	/**
	 * 搜索报警图片
	 */
	Alarm,
}
