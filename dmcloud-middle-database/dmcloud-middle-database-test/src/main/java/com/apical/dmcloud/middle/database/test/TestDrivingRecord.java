package com.apical.dmcloud.middle.database.test;

import java.util.Date;

import org.junit.Test;

import com.apical.dmcloud.alarm.middle.core.domain.DeviceLoginRecord;
import com.apical.dmcloud.driving.middle.core.domain.DeviceDrivingRecord;
import com.apical.dmcloud.middle.infra.EntityManagerHelper;

public class TestDrivingRecord
{
	//@Test
	public void testUpdateDriving()
	{
		try
		{
			DeviceDrivingRecord drivingRecord = new DeviceDrivingRecord();
			drivingRecord.setAverageSpeed(12f);
			drivingRecord.setMinSpeed(10f);
			drivingRecord.setMaxSpeed(14f);
			drivingRecord.setMileage(11234.3);
			drivingRecord.setLongitude(112.24f);
			drivingRecord.setLatitude(23.12f);
			
			//开始事务
			EntityManagerHelper.beginTransaction();
			
			//将计算结果保存到数据库中
			DeviceDrivingRecord.updateSpeedInfo(1, drivingRecord);
			
			//提交事务
			EntityManagerHelper.commitTransaction();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			//回滚事务
			EntityManagerHelper.rollbackTransaction();
		}
		finally
		{
			//关闭实体管理器
			EntityManagerHelper.closeEntityManager();
		}
	}
	
	@Test
	public void testUpdateDrivingRecord()
	{
		try
		{
			DeviceLoginRecord drivingRecord = new DeviceLoginRecord();
			
			drivingRecord.setLoginTime(new Date());
			
			//开始事务
			EntityManagerHelper.beginTransaction();
			
			drivingRecord.setDeviceId(3l);
			//将计算结果保存到数据库中
			drivingRecord.save();
			
			Long  id = drivingRecord.getId();
			System.err.println("id:" + id);
			
			//提交事务
			EntityManagerHelper.commitTransaction();
			
			
			EntityManagerHelper.beginTransaction();
			DeviceLoginRecord drivingRecord2 = DeviceLoginRecord.get(DeviceLoginRecord.class, id);
			
			drivingRecord2.setLogoutTime(new Date());
			drivingRecord2.save();
			EntityManagerHelper.commitTransaction();
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			//回滚事务
			EntityManagerHelper.rollbackTransaction();
		}
		finally
		{
			//关闭实体管理器
			EntityManagerHelper.closeEntityManager();
		}
	}
}
