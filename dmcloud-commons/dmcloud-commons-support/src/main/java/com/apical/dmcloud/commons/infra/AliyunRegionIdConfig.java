package com.apical.dmcloud.commons.infra;

/**
 * 切换代码，部署到不同阿里云的region里面
 * @author lgx
 * 2017-5-17
 */
public class AliyunRegionIdConfig {
	
	public static final String cnHangzhou = "cn-hangzhou";//杭州
	public static final String apSsoutheast1 = "ap-southeast-1";//东南亚1（新加坡）
	//public static final String regionId = apSsoutheast1;//配置这项就可以指定使用哪个regionId
	//public static final String regionId = cnHangzhou;//配置这项就可以指定使用哪个regionId
	public static final String regionId = cnHangzhou;//配置这项就可以指定使用哪个regionId
	
	
}
