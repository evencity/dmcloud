package com.apical.dmcloud.spring.database.test;

import java.util.List;

import org.junit.Test;
import org.openkoala.koala.util.KoalaBaseSpringTestCase;

import com.apical.dmcloud.vehicle.core.domain.Driver;

public class TestDriver extends KoalaBaseSpringTestCase {
	@Test
	public void test(){
		
		List<Driver> d = Driver.queryAllDriverByKeyWordsInPage(null,null,"", "", 1, 10);
		long count = Driver.countAllDriverByKeyWords(null,null,null,"");
		System.err.println(  " " + count);
	}
	

}
