package com.apical.dmcloud.commons.infra.ehcache;

import java.util.ArrayList;
import java.util.List;

import net.sf.ehcache.Cache;

public class MiddleEntryCacheServiceImpl {

	static EhcacheUtil ehcache;

	static {
		ehcache = EhcacheUtil.getInstance();
	}
	
	/**
	 * 车辆实体缓存名
	 */
	static final String VehicleIdCacheName = "Vehicle";
	
	/**
	 * 规则实体缓存名
	 */
	static final String RuleCacheName = "Rule";
	
	/**
	 * gps数据实体缓存名
	 */
	
	static final String GPSDataCacheName = "GPSData";

	
	
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

		String key = getKey_Vehicle_Binded_Device(deviceId);
		return ehcache.isKeyInCache(VehicleIdCacheName, key);
	}

	public static void saveVehicle_Binded_Device(Long deviceId, Object o) {
		String key = getKey_Vehicle_Binded_Device(deviceId);
		ehcache.put(VehicleIdCacheName, key, o);
	}
	
	
	
	public static Long getVehicle_Binded_Device(Long deviceId) {
		String key = getKey_Vehicle_Binded_Device(deviceId);
		return (Long)ehcache.get(VehicleIdCacheName, key);
	}
	
	
	public static void removeVehicle_Binded_Device(Long deviceId) {
		String key = getKey_Vehicle_Binded_Device(deviceId);
		ehcache.remove(VehicleIdCacheName, key);
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
		String key = getKeyRuleAssigned2Vehicle(ruleClass, vehicleId);
		return ehcache.isKeyInCache(RuleCacheName, key);
	}
	
	/**
	 * 添加规则缓存
	 * @param ruleClass
	 * @param vehicleId
	 * @param o
	 */
	public static void saveRuleAssigned2Vehicle(Class<?> ruleClass, Long vehicleId,Object o ) {
		String key = getKeyRuleAssigned2Vehicle(ruleClass,vehicleId);
		ehcache.put(RuleCacheName, key, o);
	}
	/**
	 * 移除缓存的规则
	 * @param ruleClass
	 * @param vehicleId
	 * @param o
	 */
	public static void removeRuleAssigned2Vehicle(Class<?> ruleClass, Long vehicleId) {
		String key = getKeyRuleAssigned2Vehicle(ruleClass,vehicleId);
		ehcache.remove(RuleCacheName, key);
	}
	
	
	public static void removeRulesAssigned2Vehicle(Class<?> ruleClass, List<Long> vehicleIds) {
		List<String> keys = new ArrayList<String>();
		for(Long vehicleId : vehicleIds){
			String key = getKeyRuleAssigned2Vehicle(ruleClass,vehicleId);
			keys.add(key);
		}
		
		ehcache.removeAll(RuleCacheName, keys);
		
	}

	/**
	 * 获取缓存的规则实体
	 * @param ruleClass
	 * @param vehicleId
	 * @return 规则实体
	 */
	@SuppressWarnings("unchecked")
	public static <T> List<T> getRuleAssigned2Vehicle(Class<T> ruleClass, Long vehicleId){
		String key = getKeyRuleAssigned2Vehicle(ruleClass,vehicleId);
		 List<T> rule = (ArrayList<T>)ehcache.get(RuleCacheName, key);
		return rule;
	}
	
	
	
	
	public static String  getKey_GPSData_BelongRule(Class<?> gpsDataClass, long gpsDataId){
		return Key_GPSData_BelongRule + gpsDataClass.getSimpleName() + "_"  + gpsDataId;
		
	}
	
	
	
	public static boolean isKeyInGPSData_BelongRuleCache(Class<?> gpsDataClass, long gpsDataId){
		String key = getKey_GPSData_BelongRule(gpsDataClass, gpsDataId);
		return ehcache.isKeyInCache(GPSDataCacheName, key);
	}
	
	/**
	 * 添加规则缓存
	 * @param ruleClass
	 * @param vehicleId
	 * @param o
	 */
	public static void saveGPSData_BelongRule(Class<?> gpsDataClass, Long gpsDataId,Object o ) {
		String key = getKey_GPSData_BelongRule(gpsDataClass,gpsDataId);
		ehcache.put(GPSDataCacheName, key, o);
	}
	/**
	 * 移除缓存的规则
	 * @param ruleClass
	 * @param vehicleId
	 * @param o
	 */
	public static void removeGPSData_BelongRule(Class<?> gpsDataClass, Long gpsDataId ) {
		String key = getKey_GPSData_BelongRule(gpsDataClass,gpsDataId);
		ehcache.remove(GPSDataCacheName, key);
	}
	
	

	/**
	 * 获取缓存的规则实体
	 * @param ruleClass
	 * @param vehicleId
	 * @return 规则实体
	 */
	@SuppressWarnings("unchecked")
	public static <T> List<T> getGPSData_BelongRule(Class<T> gpsDataClass, Long gpsDataId){
		String key = getKey_GPSData_BelongRule(gpsDataClass,gpsDataId);
		List<T> gpsData = (ArrayList<T>)ehcache.get(GPSDataCacheName, key);
		return gpsData;
	}
	
	
	/**
	 * 获取所有缓存信息
	 * @return   List<Cache>
	 */
	public static List<Cache> getAllCache(){
		return ehcache.getAllCache();
				
	}

}
