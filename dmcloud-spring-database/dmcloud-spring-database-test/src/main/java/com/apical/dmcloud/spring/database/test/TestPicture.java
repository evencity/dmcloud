package com.apical.dmcloud.spring.database.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.openkoala.koala.commons.InvokeResult;
import org.openkoala.koala.util.KoalaBaseSpringTestCase;

import com.apical.dmcloud.commons.infra.ResourceSearchType;
import com.apical.dmcloud.rule.core.domain.RuleNight;
import com.apical.dmcloud.storage.core.domain.Picture;

public class TestPicture extends KoalaBaseSpringTestCase
{
//	@Test
//	public void testQueryDay() throws ParseException
//	{
//		long count = Picture.countAllDaysByVehicleId(1, ResourceSearchType.All);
//		System.out.println(count);
//		
//		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//		Date b = format.parse("2016-03-07");
//		Date d = format.parse("2016-04-08");
//		count = Picture.countAllDaysByVehicleIdAndTimerange(1, ResourceSearchType.All, b, d);
//		System.out.println(count);
//	}
	
	//@Test
	public void testQueryPictures() throws ParseException
	{
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date b = format.parse("2016-03-07");
		Date d = format.parse("2016-04-08");
		
		long count = Picture.countAllPicturesByVehicleIdAndTimerange(1, ResourceSearchType.All, b, d);
		System.out.println(count);
		
		List<Picture> ps = Picture.queryAllPicturesByVehicleIdInPage(1, ResourceSearchType.All, b, d, 1, 5);
		for(Picture p : ps)
		{
			System.out.println(p.getFilename());
		}
	}
	//@Test
	public  void countAllVechilesById(){
		long x  = RuleNight.countAllVechilesById(2l);
	}
	
	@Test
	public void aa(){
		
		
		long companyId = 1l;
		try{
			String jqpl = "SELECT _vehicle.number"			

					+ " FROM  Vehicle _vehicle left join _vehicle.devices _device where _device.vehicle IS NULL";
		
		
			List<String> numbers = Picture.getRepository()
						.createJpqlQuery(jqpl)
						//.addParameter("companyId", companyId)
						.list();
			
			System.err.println("numbers:" + numbers.size()+numbers);
		}catch(Throwable t){
			logger.error("查询未绑定车辆设备信息异常:{}",t);
		}
	}
}
