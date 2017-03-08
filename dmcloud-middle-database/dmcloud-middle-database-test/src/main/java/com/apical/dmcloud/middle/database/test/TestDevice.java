package com.apical.dmcloud.middle.database.test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.apical.dmcloud.middle.infra.EntityManagerHelper;
import com.apical.dmcloud.vehicle.middle.core.DeviceNotExistException;
import com.apical.dmcloud.vehicle.middle.core.domain.Device;

public class TestDevice {

	
	@Test
	public void test(){
		EntityManagerHelper.beginTransaction();
		
		//将计算结果保存到数据库中
		List<Device> ds = Device.findAllDevicesInPage("云智汇", 1, 10);
		
		for(Device d: ds){
			System.err.println(d.getNumber());
		}
		String n1 = Device.getP2PUUIDById(99l);
		System.err.println(n1);
		String n2 = Device.getP2PUUIDByDeviceNumber("0123465791");
		System.err.println(n2);
		try {
			Long d =Device.getIdByDeviceNumber("0123465791");
			System.err.println(d);
			
		} catch (DeviceNotExistException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//提交事务
		EntityManagerHelper.commitTransaction();
	}
	
	


}
