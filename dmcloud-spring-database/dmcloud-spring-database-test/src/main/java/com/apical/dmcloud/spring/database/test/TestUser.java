package com.apical.dmcloud.spring.database.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.openkoala.koala.util.KoalaBaseSpringTestCase;

import com.apical.dmcloud.security.core.domain.User;
import com.apical.dmcloud.security.core.domain.UserLoginRecord;


public class TestUser extends KoalaBaseSpringTestCase
{
//	@Test
//	public void testVerifyUser()
//	{
//		boolean bres = User.verifyUserByAccountAndPassword("1371455246", "888888");
//		System.out.println(bres);
//	}
	
	//@Test
	public void testUserLoginHistory() throws ParseException
	{/*
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date b = format.parse("2016-03-07");
		Date d = format.parse("2016-03-08");
		
		long count = UserLoginRecord.countAllByUserIdAndTimerange(37, b, d);
		System.out.println(count);
		
		List<UserLoginRecord> us = UserLoginRecord.queryAllByUserIdAndTimerangeInPage(37, b, d, 1, 5);
		for(UserLoginRecord u : us)
		{
			System.out.println(u.getUserId());
			System.out.println(u.getLoginPlace());
		}*/
		 int count = 10000;
		 long start = System.currentTimeMillis();
		 while(count > 1){
			 Thread.currentThread().setName("thread--"  + count  + "---");
			 
			 User u = User.getByUserAccount("koala");
			 
			 System.err.println( Thread.currentThread().getName() + u.getName() + "----count:" + (100 -count) + "---"+  (System.currentTimeMillis()-start));
			 count--;
		 }
	}
	
	@Test
	public void  getCompnayIdTest(){
		System.err.println(User.isExcitSubAccout(1249L));
	}

}
