package com.apical.dmcloud.spring.database.test;

import java.util.List;

import org.junit.Test;
import org.openkoala.koala.util.KoalaBaseSpringTestCase;

import com.apical.dmcloud.security.core.domain.User;
import com.apical.dmcloud.vehicle.core.domain.SIMCard;

public class TestSimCard extends KoalaBaseSpringTestCase{
	
	@Test
	public void test(){
		long count = SIMCard.countAllByKeyWord(null, "11", null, null);
		System.err.println(count);
		
		List<SIMCard> simcards = SIMCard.queryAllByKeyWordInPage(null, "11", null, null, 1, 10);
		
		for(SIMCard sim : simcards){
			System.err.println(sim.getNumber());
		}
	}

}
