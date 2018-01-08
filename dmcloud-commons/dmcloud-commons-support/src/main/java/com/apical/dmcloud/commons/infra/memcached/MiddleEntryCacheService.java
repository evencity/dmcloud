package com.apical.dmcloud.commons.infra.memcached;

import java.util.ArrayList;
import java.util.List;

import com.whalin.MemCached.MemCachedClient;


public class MiddleEntryCacheService {

	
	/** 车辆key */
	static final String Key_Vehicle_Binded_Device = "Vehicle_Binded_Device_";

	/** 规则key */
	static final String Key_Rule_Assigned2Vehicle = "Rule_Assigned2Vehicle_";


	/** GPSData key**/
	static final String Key_GPSData_BelongRule = "GPSData_BelongRule_";
	
	/**
	 * 获取车辆的键
	 * 
	 * @param deviceId
	 * @return key
	 */
	public static String getKey_Vehicle_Binded_Device(Long deviceId) {

		return Key_Vehicle_Binded_Device + deviceId;
	}
	
	
	public static boolean isKeyIn_Vehicle_Binded_DeviceCache(Long deviceId) {

	/*	String key = getKey_Vehicle_Binded_Device(deviceId);
		MemCachedClient mcc = MemcacheContextManager.getInstance().getMemCacheClient();
		return mcc.keyExists(key);*/
		return false;
	}

	public static void saveVehicle_Binded_Device(Long deviceId, Object o) {
	/*	String key = getKey_Vehicle_Binded_Device(deviceId);
		MemCachedClient mcc = MemcacheContextManager.getInstance().getMemCacheClient();
		mcc.set(key,o);*/
	}
	
	
	
	public static Long getVehicle_Binded_Device(Long deviceId) {
	/*	String key = getKey_Vehicle_Binded_Device(deviceId);
		
		try{
			MemCachedClient mcc = MemcacheContextManager.getInstance().getMemCacheClient();

			return (Long)mcc.get(key);
		}catch(Throwable t){
		
			return null;
		}*/
		return null;
		
	}
	
	
	public static void removeVehicle_Binded_Device(Long deviceId) {
	/*	String key = getKey_Vehicle_Binded_Device(deviceId);
		MemCachedClient mcc = MemcacheContextManager.getInstance().getMemCacheClient();

		mcc.delete(key);*/
	}
	

	
	/**
	 * 获取分配车辆的规则key
	 * @param vehicleId
	 * @param ruleClass
	 * @return key
	 */
	public static String getKeyRuleAssigned2Vehicle(Class<?> ruleClass,Long vehicleId) {
		return Key_Rule_Assigned2Vehicle + ruleClass.getSimpleName() + "_" + vehicleId;
		
	}
	
	
	public static boolean isKeyInRuleAssigned2VehicleCache(Class<?> ruleClass,Long vehicleId){
/*		String key = getKeyRuleAssigned2Vehicle(ruleClass, vehicleId);
		MemCachedClient mcc = MemcacheContextManager.getInstance().getMemCacheClient();

		return mcc.keyExists(key);*/
		return false;
	}
	
	/**
	 * 添加规则缓存
	 * @param ruleClass
	 * @param vehicleId
	 * @param o
	 */
	public static void saveRuleAssigned2Vehicle(Class<?> ruleClass, Long vehicleId,Object o ) {
	/*	String key = getKeyRuleAssigned2Vehicle(ruleClass,vehicleId);
		MemCachedClient mcc = MemcacheContextManager.getInstance().getMemCacheClient();

		mcc.set(key, o);*/
	}
	/**
	 * 移除缓存的规则
	 * @param ruleClass
	 * @param vehicleId
	 * @param o
	 */
	public static void removeRuleAssigned2Vehicle(Class<?> ruleClass, Long vehicleId) {
		String key = getKeyRuleAssigned2Vehicle(ruleClass,vehicleId);
		MemCachedClient mcc = MemcacheContextManager.getInstance().getMemCacheClient();

		mcc.delete(key);
	}
	
	
	public static void removeRulesAssigned2Vehicle(Class<?> ruleClass, List<Long> vehicleIds) {
		for(Long vehicleId : vehicleIds){
			String key = getKeyRuleAssigned2Vehicle(ruleClass,vehicleId);
			MemCachedClient mcc = MemcacheContextManager.getInstance().getMemCacheClient();
			mcc.delete(key);
		}
		
		
	}

	/**
	 * 获取缓存的规则实体
	 * @param ruleClass
	 * @param vehicleId
	 * @return 规则实体
	 */
	@SuppressWarnings("unchecked")
	public static <T> List<T> getRuleAssigned2Vehicle(Class<T> ruleClass, Long vehicleId){
//		String key = getKeyRuleAssigned2Vehicle(ruleClass,vehicleId);
//		try{
//			MemCachedClient mcc = MemcacheContextManager.getInstance().getMemCacheClient();
//			 
//			List<T> rule = (ArrayList<T>)mcc.get(key);
//			return rule;
//		}catch(Throwable t){
//		
//			return null;
//		}
		return null;
}
	
	
	
	
	
	public static String  getKey_GPSData_BelongRule(Class<?> gpsDataClass, long gpsDataId){
		return Key_GPSData_BelongRule + gpsDataClass.getSimpleName() + "_"  + gpsDataId;
		
	}
	
	
	
	public static boolean isKeyInGPSData_BelongRuleCache(Class<?> gpsDataClass, long gpsDataId){
	/*	String key = getKey_GPSData_BelongRule(gpsDataClass, gpsDataId);
		MemCachedClient mcc = MemcacheContextManager.getInstance().getMemCacheClient();
		 
		return mcc.keyExists(key);*/
		return false;
	}
	
	/**
	 * 添加规则缓存
	 * @param ruleClass
	 * @param gpsDataId: areaId 或 routeId
	 * @param o
	 */
	public static void saveGPSData_BelongRule(Class<?> gpsDataClass, Long gpsDataId,Object o ) {
		//String key = getKey_GPSData_BelongRule(gpsDataClass,gpsDataId);
		//MemCachedClient mcc = MemcacheContextManager.getInstance().getMemCacheClient();
		// mcc.set(key, o);
	}
	/**
	 * 移除缓存的规则
	 * @param ruleClass
	 * @param vehicleId
	 * @param o
	 */
	public static void removeGPSData_BelongRule(Class<?> gpsDataClass, Long gpsDataId ) {
		//String key = getKey_GPSData_BelongRule(gpsDataClass,gpsDataId);
		//MemCachedClient mcc = MemcacheContextManager.getInstance().getMemCacheClient();
		//mcc.delete(key); 
	}
	
	

	/**
	 * 获取缓存的规则实体
	 * @param ruleClass
	 * @param vehicleId
	 * @return 规则实体
	 */
	@SuppressWarnings("unchecked")
	public static <T> List<T> getGPSData_BelongRule(Class<T> gpsDataClass, Long gpsDataId){
//		String key = getKey_GPSData_BelongRule(gpsDataClass,gpsDataId);
//		try{
//			MemCachedClient mcc = MemcacheContextManager.getInstance().getMemCacheClient();
//			 
//			List<T> gpsData = (ArrayList<T>)mcc.get(key);
//			return gpsData;
//		}catch(Throwable t){
//			return null;
//		}
		return null;
		
	}
	
	/**
	 * 获取客户端实例
	 * @return
	 */
	public static MemCachedClient getMemCachedClient(){
		return MemcacheContextManager.getInstance().getMemCacheClient();
	}


}
