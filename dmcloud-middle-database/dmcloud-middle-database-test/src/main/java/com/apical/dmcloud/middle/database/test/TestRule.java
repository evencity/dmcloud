package com.apical.dmcloud.middle.database.test;

import java.util.Date;
import java.util.List;

import org.junit.Test;

import com.apical.dmcloud.commons.infra.DateUtils;
import com.apical.dmcloud.rule.middle.core.RuleAssignmentException;
import com.apical.dmcloud.rule.middle.core.domain.DrivingArea;
import com.apical.dmcloud.rule.middle.core.domain.DrivingAreaGPSData;
import com.apical.dmcloud.rule.middle.core.domain.DrivingRoute;
import com.apical.dmcloud.rule.middle.core.domain.DrivingRouteGPSData;
import com.apical.dmcloud.rule.middle.core.domain.Rule;
import com.apical.dmcloud.rule.middle.core.domain.RuleAreaSpeed;
import com.apical.dmcloud.rule.middle.core.domain.RuleFatigue;
import com.apical.dmcloud.rule.middle.core.domain.RuleNight;
import com.apical.dmcloud.rule.middle.core.domain.RuleNoentry;
import com.apical.dmcloud.rule.middle.core.domain.RuleNoout;
import com.apical.dmcloud.rule.middle.core.domain.RuleRouteOffset;
import com.apical.dmcloud.rule.middle.core.domain.RuleSubSection;
import com.apical.dmcloud.rule.middle.core.domain.RuleTakePicture;
import com.apical.dmcloud.rule.middle.core.domain.RuleTakeVideo;

public class TestRule
{
	@Test
	public void testRuleSubSection() {
		RuleSubSection ruleSubSection = RuleSubSection.get(RuleSubSection.class, 8L);
			boolean need = DateUtils.isBetweenInDay(new Date(),
					ruleSubSection.getBeginTime(), ruleSubSection.getEndTime());
		
		System.out.println("ddddddddddddd"+need);
	}
	
//	@Test
//	public void testQueryRule()
//	{
//		long count = RuleAreaSpeed.countAllRules();
//		System.out.println(count);
//		List<RuleAreaSpeed> rules = RuleAreaSpeed.queryAllRulesInPage(1, 5);
//		for(RuleAreaSpeed r : rules)
//		{
//			System.out.println(r.getName());
//			System.out.println(r.getDescription());
//			System.out.println(r.getVoice());
//			System.out.println(r.getPhones());
//			System.out.println(r.getMails());
//		}
		
//		long count = RuleFatigue.countAllRules();
//		System.out.println(count);
//		List<RuleFatigue> rules = RuleFatigue.queryAllRulesInPage(1, 5);
//		for(RuleFatigue r : rules)
//		{
//			System.out.println(r.getName());
//			System.out.println(r.getDescription());
//			System.out.println(r.getVoice());
//			System.out.println(r.getPhones());
//			System.out.println(r.getMails());
//		}
		
//		long count = RuleNight.countAllRules();
//		System.out.println(count);
//		List<RuleNight> rules = RuleNight.queryAllRulesInPage(1, 5);
//		for(RuleNight r : rules)
//		{
//			System.out.println(r.getName());
//			System.out.println(r.getDescription());
//			System.out.println(r.getVoice());
//			System.out.println(r.getPhones());
//			System.out.println(r.getMails());
//		}
		
//		long count = RuleNoentry.countAllRules();
//		System.out.println(count);
//		List<RuleNoentry> rules = RuleNoentry.queryAllRulesInPage(1, 5);
//		for(RuleNoentry r : rules)
//		{
//			System.out.println(r.getName());
//			System.out.println(r.getDescription());
//			System.out.println(r.getVoice());
//			System.out.println(r.getPhones());
//			System.out.println(r.getMails());
//		}
		
//		long count = RuleNoentry.countAllRules();
//		System.out.println(count);
//		List<RuleNoentry> rules = RuleNoentry.queryAllRulesInPage(1, 5);
//		for(RuleNoentry r : rules)
//		{
//			System.out.println(r.getName());
//			System.out.println(r.getDescription());
//			System.out.println(r.getVoice());
//			System.out.println(r.getPhones());
//			System.out.println(r.getMails());
//		}
		
//		long count = RuleNoout.countAllRules();
//		System.out.println(count);
//		List<RuleNoout> rules = RuleNoout.queryAllRulesInPage(1, 5);
//		for(RuleNoout r : rules)
//		{
//			System.out.println(r.getName());
//			System.out.println(r.getDescription());
//			System.out.println(r.getVoice());
//			System.out.println(r.getPhones());
//			System.out.println(r.getMails());
//		}
		
//		long count = RuleRouteOffset.countAllRules();
//		System.out.println(count);
//		List<RuleRouteOffset> rules = RuleRouteOffset.queryAllRulesInPage(1, 5);
//		for(RuleRouteOffset r : rules)
//		{
//			System.out.println(r.getName());
//			System.out.println(r.getDescription());
//			System.out.println(r.getVoice());
//			System.out.println(r.getPhones());
//			System.out.println(r.getMails());
//		}
		
//		long count = RuleSubSection.countAllRules();
//		System.out.println(count);
//		List<RuleSubSection> rules = RuleSubSection.queryAllRulesInPage(1, 5);
//		for(RuleSubSection r : rules)
//		{
//			System.out.println(r.getName());
//			System.out.println(r.getDescription());
//			System.out.println(r.getVoice());
//			System.out.println(r.getPhones());
//			System.out.println(r.getMails());
//		}
		
//		long count = RuleTakePicture.countAllRules();
//		System.out.println(count);
//		List<RuleTakePicture> rules = RuleTakePicture.queryAllRulesInPage(1, 5);
//		for(RuleTakePicture r : rules)
//		{
//			System.out.println(r.getName());
//			System.out.println(r.getDescription());
//		}
		
//		long count = RuleTakeVideo.countAllRules();
//		System.out.println(count);
//		List<RuleTakeVideo> rules = RuleTakeVideo.queryAllRulesInPage(1, 5);
//		for(RuleTakeVideo r : rules)
//		{
//			System.out.println(r.getName());
//			System.out.println(r.getDescription());
//		}
//	}
	
//	@Test
//	public void testRuleAssignment()
//	{
//		boolean count = RuleAreaSpeed.isAssignedToVehicle(1);
//		System.out.println(count);
//		
//		try {
//			boolean bres = RuleAreaSpeed.assignToVehicle(1, 1);
//			System.out.println(bres);
//		} catch (RuleAssignmentException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		boolean res = RuleAreaSpeed.isAssignedToVehicle(1, 1);
//		System.out.println(res);
//		
//		long count1 = RuleAreaSpeed.countAllRulesByVehicleId(4);
//		System.out.println(count1);
//		
//		List<RuleAreaSpeed> rules = RuleAreaSpeed.queryAllRulesByVehicleId(1);
//		for(RuleAreaSpeed r : rules)
//		{
//			System.out.println(r.getName());
//			System.out.println(r.getDescription());
//			System.out.println(r.getVoice());
//			System.out.println(r.getPhones());
//			System.out.println(r.getMails());
//		}
//	}
	
//	@Test
//	public void testDrivingArea()
//	{
//		long count = DrivingArea.countAllDrivingArea();
//		System.out.println("行车区域总数量：" + count);
//		List<DrivingArea> areaList = DrivingArea.queryAllDrivingAreaInPage(1, 2);
//		for(DrivingArea area : areaList)
//		{
//			System.out.println(area.getName());
//			System.out.println(area.getDescription());
//			
//			List<DrivingAreaGPSData> datas = DrivingAreaGPSData.queryAllByAreaId(area.getId());
//			for(DrivingAreaGPSData data : datas)
//			{
//				System.out.println(data.getLongitude());
//				System.out.println(data.getLatitude());
//			}
//			System.out.println();
//		}
//		
//		List<DrivingAreaGPSData> datas = DrivingAreaGPSData.queryAllByAreaId(1);
//		for(DrivingAreaGPSData data : datas)
//		{
//			System.out.println(data.getLongitude());
//			System.out.println(data.getLatitude());
//		}
//	}
	
	//@Test
	public void testDrivingRoute()
	{
		long count = DrivingRoute.countAllDrivingRoute();
		System.out.println("行车区域总数量：" + count);
		List<DrivingRoute> areaList = DrivingRoute.queryAllDrivingRouteInPage(1, 2);
		for(DrivingRoute area : areaList)
		{
			System.out.println(area.getName());
			System.out.println(area.getDescription());
			
			List<DrivingRouteGPSData> datas = DrivingRouteGPSData.queryAllByRouteId(area.getId());
			for(DrivingRouteGPSData data : datas)
			{
				System.out.println(data.getLongitude());
				System.out.println(data.getLatitude());
			}
			System.out.println();
		}
		
		List<DrivingRouteGPSData> datas = DrivingRouteGPSData.queryAllByRouteId(1);
		for(DrivingRouteGPSData data : datas)
		{
			System.out.println(data.getLongitude());
			System.out.println(data.getLatitude());
		}
	}
	
	@Test
	public void testRuleFatigue(){
		//RuleFatigue.getByVehicleId(2l);
		
	}
}
