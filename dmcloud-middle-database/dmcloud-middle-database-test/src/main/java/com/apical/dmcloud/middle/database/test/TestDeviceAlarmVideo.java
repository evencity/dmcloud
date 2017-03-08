package com.apical.dmcloud.middle.database.test;


import java.util.Date;

import org.junit.Test;

import com.apical.dmcloud.alarm.middle.core.domain.DeviceAlarmVideo;
import com.apical.dmcloud.middle.infra.EntityManagerHelper;
import com.apical.dmcloud.storage.middle.core.domain.Video;

public class TestDeviceAlarmVideo {

	@Test
	public void test() {
		try {
			EntityManagerHelper.beginTransaction();

			// 将计算结果保存到数据库中

			// 提交事务

		
			Video v = new Video();
			v.setResourceStorageInfoId(1l);
			v.setUploadTime(new Date());
			v.setUploadDate(new Date());
			v.save();
			EntityManagerHelper.commitTransaction();
			System.err.println(v.getId());
			EntityManagerHelper.beginTransaction();
			DeviceAlarmVideo d = new DeviceAlarmVideo();
			d.setAlarmVideo(v);
			//d.setVideoId(v.getId());
			d.setStoragePath("ddddd");
			d.save();
			EntityManagerHelper.commitTransaction();
			System.err.println(d.getId());
			
		} catch (Throwable t) {
			t.printStackTrace();
		}

	}
}
