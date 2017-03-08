package com.apical.dmcloud.commons.infra;

/**
 * 报警类型，请根据中间件协议设置
 * @author qiuzeng
 *
 */
public enum AlarmType
{
	/**
	 * 普通--0
	 */
	Normal,
	
	/**
	 * 报警--1
	 */
	Alarm,
	
	/**
	 * 震动报警--2
	 */
	Vibration,
	
	/**
	 * 撞车报警--3
	 */
	Crash,
	
	/**
	 * 超速报警--4
	 */
	Overspeed,
	
	/**
	 * 路线偏移报警--5
	 */
	RouteOffset,
	
	/**
	 * 越界--6
	 */
	CrossBorder,
	
	/**
	 * 疲劳驾驶报警--7
	 */
	Fatigue,
	
	/**
	 * 区域禁入报警--8
	 */
	NoEntry,
	
	/**
	 * 区域禁出报警--9
	 */
	NoOut,
	
	/**
	 * 区域限速报警--10
	 */
	AreaSpeedLimit,
	
	/**
	 * 分段限速报警--11
	 */
	SubSectionSpeed,
	
	/**
	 * 夜间行车报警--12
	 */
	NightDriving,
}
