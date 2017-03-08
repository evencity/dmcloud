package com.apical.dmcloud.middle.database.test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import com.apical.dmcloud.commons.infra.ResourceSearchType;
import com.apical.dmcloud.storage.middle.core.domain.Picture;

public class TestPicture
{
	@Test
	public void testGetPicture()
	{
//		long count = Picture.countAllDaysByVehicleId(1, ResourceSearchType.All);
//		System.out.println(count);
		
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date date1 = null;
		Date date2 = null;
		try
		{
			date1 = format.parse("2016-04-07 00:05:00");
			date2 = format.parse("2016-04-09 00:05:00");
		}
		catch (ParseException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		List<Picture> pictures = Picture.queryAllPicturesByDeviceIdAndShotTimerange(1, ResourceSearchType.All,
				date1, date2);
		for(Picture p : pictures)
		{
			System.out.println(p.getFilename());
		}
	}
}
