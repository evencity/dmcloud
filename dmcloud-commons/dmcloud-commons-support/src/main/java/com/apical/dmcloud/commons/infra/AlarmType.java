package com.apical.dmcloud.commons.infra;

/**
 * 报警类型，请根据中间件协议设置
 * @author qiuzeng
 *
 */
public enum AlarmType
{
	/**
	 * 不使用（作为占位符）由于枚举类型只能从0开始，由于mysql主键是从1开始的
	 * 0
	 */
	BoUse,
	
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
	
	/**
	 * 急刹车报警-13
	 */
	Scram,
	
	/**
	 * 急加速报警-14
	 */
	Accelerate,
	
	/**
	 * 急转弯-15
	 */
	Suddenturn,
	
	/**
	 * 停车车内移动监测报警-16
	 */
	CarInsideMoving,
	
	/**
	 * 停车燃油减少防盗报警-17
	 */
	FuelDecrease,
	
	/**
	 * 胎压超过阈值报警-18
	 */
	TirePressureExceed,
	
	/**
	 * 胎压低于阈值报警-19
	 */
	TirePressureBelow,
}
