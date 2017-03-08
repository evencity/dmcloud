package com.apical.dmcloud.spring.database.test;


import org.junit.Test;
import org.openkoala.koala.util.KoalaBaseSpringTestCase;


import com.apical.dmcloud.vehicle.core.domain.Company;
import com.apical.dmcloud.vehicle.core.domain.SIMCard;

public class TestDemo  extends KoalaBaseSpringTestCase{

	@Test
	public void testCountAllCompany()
	{
		long count  = Company.countAllCompanys();
		System.out.println(count);
	}
	@Test
	public void testCountAllSIMCardsByCompany()
	{
		long count  = SIMCard.countAllSIMCardsByCompany("apdl");
		System.out.println(count);
	}
}
