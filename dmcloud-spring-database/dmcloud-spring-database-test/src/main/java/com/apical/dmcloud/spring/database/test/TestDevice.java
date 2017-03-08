package com.apical.dmcloud.spring.database.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.openkoala.koala.util.KoalaBaseSpringTestCase;

import com.apical.dmcloud.alarm.core.domain.DeviceLoginRecord;
import com.apical.dmcloud.vehicle.core.domain.Device;


public class TestDevice extends KoalaBaseSpringTestCase
{
	@Test
	public void testDeviceLoginHistory() throws ParseException
	{
		/*SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date b = format.parse("2016-03-07");
		Date d = format.parse("2016-04-08");
		
		long count = DeviceLoginRecord.countAllByDeviceIdAndTimerange(1, b, d);
		System.out.println(count);
		
		List<DeviceLoginRecord> us = DeviceLoginRecord.queryAllByDeviceIdAndTimerangeInPage(1, b, d, 1, 5);
		for(DeviceLoginRecord u : us)
		{
			System.out.println(u.getDeviceId());
			System.out.println(u.getLoginIP());
		}*/
		long count = Device.countAllDevicesInByKeyWords("云智汇","012", "", "", "");
		System.err.println(count);
		List<Device> d = Device.queryAllDevicesInByKeyWordsInPage("云智汇","012", "", "", "", 1, 10);
		System.err.println(d.size());
	}
}
