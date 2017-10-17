package com.apical.dmcloud.spring.database.test;

import java.util.List;

import org.junit.Test;
import org.openkoala.koala.util.KoalaBaseSpringTestCase;

import com.apical.dmcloud.alarm.core.AlarmRecordNotExistException;
import com.apical.dmcloud.alarm.core.domain.DeviceAlarmPicture;
import com.apical.dmcloud.alarm.core.domain.DeviceAlarmRecord;
import com.apical.dmcloud.alarm.core.domain.DeviceAlarmType;
import com.apical.dmcloud.alarm.core.domain.DeviceAlarmVideo;
import com.apical.dmcloud.alarm.core.domain.DeviceLoginRecord;
import com.apical.dmcloud.driving.core.domain.DeviceDrivingRecord;
import com.apical.dmcloud.security.core.domain.Authorization;
import com.apical.dmcloud.security.core.domain.Permission;
import com.apical.dmcloud.security.core.domain.User;
import com.apical.dmcloud.security.core.domain.UserLoginRecord;

public class TestAuthorization extends KoalaBaseSpringTestCase
{
	@Test
	public void testUserAuthorization()
	{
//		List<User> usrs= User.findAllUsersByCompanyIdInPage(1, 1, 5);
//		for(User u : usrs)
//		{
//			System.out.println(u.getUserAccount());
//		}
		
//		long count = UserLoginRecord.countAllByUserId(37);
//		System.out.println(count);
//		List<UserLoginRecord> usrs= UserLoginRecord.queryAllByUserIdInPage(37, 1, 10);
//		for(UserLoginRecord u : usrs)
//		{
//			System.out.println("登录时间：" + u.getLoginTime());
//			System.out.println("注销时间：" + u.getLogoutTime());
//			System.out.println("登录地点：" + u.getLoginPlace());
//			System.out.println();
//		}
		
//		long count = DeviceDrivingRecord.countAllByVehicleId(1);
//		System.out.println(count);
//		List<DeviceDrivingRecord> usrs= DeviceDrivingRecord.queryAllByVehicleIdInPage(1, 1, 5);
//		for(DeviceDrivingRecord u : usrs)
//		{
//			System.out.println("上传时间：" + u.getUploadTime());
//			System.out.println("开始地点：" + u.getStartPlace());
//			System.out.println("结束地点：" + u.getEndPlace());
//			System.out.println();
//		}
		
//		long count = DeviceAlarmType.countAllAlarmType();
//		System.out.println(count);
//		List<DeviceAlarmType> usrs= DeviceAlarmType.queryAllAlarmTypeInPage(1, 5);
//		for(DeviceAlarmType u : usrs)
//		{
//			System.out.println("报警类型名称：" + u.getName());
//			System.out.println("报警类型描述：" + u.getDescription());
//			System.out.println();
//		}
		
//		long count = DeviceLoginRecord.countAllByDeviceId(1);
//		System.out.println(count);
//		List<DeviceLoginRecord> usrs= DeviceLoginRecord.queryAllByDeviceIdInPage(1, 1, 5);
//		for(DeviceLoginRecord u : usrs)
//		{
//			System.out.println("登录ip：" + u.getLoginIP());
//			System.out.println("登录时间：" + u.getLoginTime());
//			System.out.println("注销时间：" + u.getLogoutTime());
//			System.out.println();
//		}
		
//		long count = 0;
//		try {
//			count = DeviceAlarmRecord.queryIdByDeviceIdAndAlarmUId(1, "UID123456");
//		} catch (AlarmRecordNotExistException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		System.out.println(count);
//		
//		DeviceAlarmRecord record = DeviceAlarmRecord.queryByDeviceIdAndAlarmUId(1, "UID123456");
//		if(record != null)
//		{
//			System.out.println("设备id：" + record.getDeviceId());
//			System.out.println("报警uid：" + record.getAlarmUId());
//			System.out.println("报警内容：" + record.getAlarmContent());
//		}
		
//		long acount = DeviceAlarmRecord.countAllByDeviceId(1);
//		System.out.println(acount);
//		List<DeviceAlarmRecord> usrs= DeviceAlarmRecord.queryAllByDeviceIdInPage(1, 1, 5);
//		for(DeviceAlarmRecord u : usrs)
//		{
//			System.out.println("设备id：" + u.getDeviceId());
//			System.out.println("报警uid：" + u.getAlarmUId());
//			System.out.println("报警内容：" + u.getAlarmContent());
//			System.out.println();
//		}
		
//		acount = DeviceAlarmRecord.countAllByVehicleId(1);
//		System.out.println(acount);
//		List<DeviceAlarmRecord> usrs1= DeviceAlarmRecord.queryAllByVehicleIdInPage(1, 1, 5);
//		for(DeviceAlarmRecord u : usrs1)
//		{
//			System.out.println("车辆id：" + u.getVehicleId());
//			System.out.println("报警uid：" + u.getAlarmUId());
//			System.out.println("报警内容：" + u.getAlarmContent());
//			System.out.println();
//		}
		
		/*long acount = DeviceAlarmVideo.countDeviceAlarmVideosByAlarmId(1);
		System.out.println(acount);
		List<DeviceAlarmVideo> usrs= DeviceAlarmVideo.queryDeviceAlarmVideosByAlarmId(1);
		for(DeviceAlarmVideo u : usrs)
		{
			System.out.println("设备报警id：" + u.getDeviceAlarmId());
			System.out.println("图片id：" + u.getVideoId());
			System.out.println("存储路径：" + u.getStoragePath());
			System.out.println();
		}*/
		List<DeviceAlarmType> list = DeviceAlarmType.queryAllAlarmType();
		for(DeviceAlarmType deviceAlarmType : list){
			System.err.println("deviceAlarmType:"+deviceAlarmType);
		}
	}
}
