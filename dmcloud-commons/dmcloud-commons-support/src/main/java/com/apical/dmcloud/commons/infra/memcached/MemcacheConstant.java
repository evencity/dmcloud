package com.apical.dmcloud.commons.infra.memcached;

import com.apical.dmcloud.commons.infra.AliyunRegionIdConfig;

public class MemcacheConstant
{
	//=================================================================
	//memcache参数配置
	/**
	 * memcache服务器列表
	 */
	public static String ip_release = "";
	public static String[] MemcacheServers_Release = {};
	
	static {
		if (AliyunRegionIdConfig.regionId.equals(AliyunRegionIdConfig.cnHangzhou)) {//杭州
			ip_release = "2eb93f77c6614239.m.cnhzaliqshpub001.ocs.aliyuncs.com";
			MemcacheServers_Release = new String[]{ip_release};
		} else if (AliyunRegionIdConfig.regionId.equals(AliyunRegionIdConfig.apSsoutheast1)) {//亚太东南1
			ip_release = "m-t4neb6caeff0a654.memcache.singapore.rds.aliyuncs.com";
			MemcacheServers_Release = new String[]{ip_release};
		}
	}
	/**
	 * memcache服务器列表--本地测试memcache集群
	 */
	public static String ip_debug = "192.168.0.236";
	public static String[] MemcacheServers_Debug = {"192.168.0.236:11211"};
	
	/**
	 * memcache服务器列表权重
	 */
	public static Integer[] MemcacheWeights = {3};
	
	/**
	 * 初始化连接数量
	 */
	public static int MemcacheInitConnections = 10;
	
	/**
	 * 最小连接数量
	 */
	public static int MemcacheMinConnections = 10;
	
	/**
	 * 最大连接数量
	 */
	public static int MemcacheMaxConnections = 1000;
	
	/**
	 * 最大处理时间
	 */
	public static int MemcacheMaxIdleTime = 1000 * 60 * 60;
	
	/**
	 * 最大处理时间
	 */
	public static int MemcacheSleepTime = 60;
	
	/**
	 * 最大处理时间
	 */
	public static boolean MemcacheNagle = false;
	
	/**
	 * memcache读超时
	 */
	/*public static int MemcacheReadTimeout = 60;*/
	
	public static int MemcacheReadTimeout = 2500;
	
	/**
	 * memcache连接超时
	 */
	public static int MemcacheConnectTimeout = 0;
	//=================================================================
	
	
	//=================================================================
	//memcache key值命名空间定义
	/**
	 * 全局命名空间--用户
	 */
	public static String KEY_Global_User_Namespace = "Global_User_";
	/**
	 * 全局命名空间--产品
	 */
	public static String KEY_Global_Product_Namespace = "Global_Product_";
	
	/**
	 * 用户产品绑定信息命名空间--用户
	 */
	public static String KEY_Bind_User_Namespace = "Bind_User_";
	/**
	 * 用户产品绑定信息命名空间--产品
	 */
	public static String KEY_Bind_Product_Namespace = "Bind_Product_";
	
	/**
	 * 产品信息命名空间--产品系列
	 */
	public static String KEY_Product_Series_Namespace = "Product_Series_";
	/**
	 * 产品信息命名空间--产品型号
	 */
	public static String KEY_Product_Model_Namespace = "Product_Model_";
	/**
	 * 产品信息命名空间--产品信息
	 */
	public static String KEY_Product_Product_Namespace = "Product_Product_";
	
	/**
	 * 在线数量命名空间--用户在线数量
	 */
	public static String KEY_Online_User_Namespace = "Online_User_";
	/**
	 * 在线数量命名空间--设备在线数量
	 */
	public static String KEY_Online_Device_Namespace = "Online_Product_";
	//=================================================================
}
