package com.apical.dmcloud.commons.test;

import com.apical.dmcloud.commons.infra.memcached.MiddleEntryCacheService;

public class testMemcached {
	
	

	public void test(){
		MiddleEntryCacheService.saveVehicle_Binded_Device(22l, new Long(22));
		
		Long aa = MiddleEntryCacheService.getVehicle_Binded_Device(23l);
		System.err.println(aa);
	
	}

}
