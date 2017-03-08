package com.apical.dmcloud.middle.database.test;

import org.junit.Test;

import com.apical.dmcloud.driving.middle.core.domain.DeviceDrivingRecord;
import com.apical.dmcloud.middle.infra.EntityManagerHelper;
import com.apical.dmcloud.storage.middle.core.domain.StorageInfo;
import com.apical.dmcloud.vehicle.middle.core.domain.Vehicle;
import com.apical.dmcloud.vehicle.middle.core.domain.VehicleStatus;
import com.apical.dmcloud.commons.infra.ResourceLocation;
public class TestVehicle
{
//	@Test
//	public void testVehicleStatus()
//	{
//		try
//		{
//			EntityManagerHelper.beginTransaction();
//			
//			VehicleStatus status = new VehicleStatus();
//			status.setId(5L);
//			status.setVehicleId(2L);
//			status.setIsOnline(false);
//			
//			VehicleStatus.saveOrUpdateByVehicleId(status);
//			
//			EntityManagerHelper.commitTransaction();
//		}
//		catch(Exception e)
//		{
//			EntityManagerHelper.rollbackTransaction();
//		}
//		finally
//		{
//			EntityManagerHelper.closeEntityManager();
//		}
//	}
	
	public static void main(String args[])
	{
		try
		{
			VehicleStatus status = VehicleStatus.getByVehicleId(2);
			if(status != null)
			{
				System.out.println(status.getIsOnline());
			}
			else
			{
				System.out.println("status== null!");
			}
			
			VehicleStatus status1 = VehicleStatus.getByVehicleId(20);
			if(status1 != null)
			{
				System.out.println(status1.getIsOnline());
			}
			else
			{
				System.out.println("status1== null!");
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	@Test
	public void test(){
		EntityManagerHelper.beginTransaction();
		
		//将计算结果保存到数据库中
		Vehicle.findAllVehiclesInPage("apical", 1, 10);
		StorageInfo info = new StorageInfo();
		info.setLocation(ResourceLocation.Location_BCS);
		info.setPath("xxxxxx");
		info.save();
		System.err.println(info.getId());
		//提交事务
		EntityManagerHelper.commitTransaction();
	}
}
