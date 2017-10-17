package com.apical.dmcloud.spring.database.test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.junit.Test;
import org.openkoala.koala.util.KoalaBaseSpringTestCase;
import org.springframework.beans.factory.annotation.Autowired;

import com.apical.dmcloud.commons.infra.AlarmType;
import com.apical.dmcloud.vehicle.core.domain.P2PUUID;
public class TestP2PUUID extends KoalaBaseSpringTestCase
{
	
	@Test
	public void testP2PUUID() throws Exception
	{
		System.err.println("AlarmType:"+AlarmType.AreaSpeedLimit);
		/*Set<Integer> set = new HashSet<Integer>();
		set.add(1);
		set.add(2);
		long countAll = P2PUUID.countAll("826S8MGY48ZZ",set);
		List<P2PUUID> findAllInPage = P2PUUID.findAllInPage("826S8MGY48ZZ",set, 1, 10);
		System.err.println("findAllInPage"+findAllInPage);
		for(P2PUUID p : findAllInPage){
			System.err.println("uuid::"+p.getP2puuid());
		}
		System.err.println("countAll:"+countAll);*/
		/*Set set = new HashSet<String>();
		set.add("TR8MU814KAEU9V1G111A");
		set.add("SSPBAGS87MNA5JKJ111A");
		set.add("ZR5GUZUZLN533WG5111A");
		for(int i=0;i<10;i++){
			set.add("zzhzzhzzh"+i);
		}
		List<String> existP2PUUIDs = P2PUUID.isExistP2PUUIDs(set);
		for(String s :existP2PUUIDs){
			System.err.println("s::"+s);
		}
		System.err.println(existP2PUUIDs.size());*/
/*		List<P2PUUID> list = new ArrayList<P2PUUID>();
		System.err.println("begin:"+System.currentTimeMillis() );
		long currentTimeMillis = System.currentTimeMillis();
		for(int i=0;i<1000;i++){
			P2PUUID p=new P2PUUID("uuu"+i);
			list.add(p);
		}
		
		System.err.println("end:"+System.currentTimeMillis() );
		long currentTimeMillis2 = System.currentTimeMillis();
		
		System.err.println("间隔：：：：："+(currentTimeMillis2-currentTimeMillis));*/
		List<P2PUUID> list = new ArrayList<P2PUUID>();
		/*for(int i = 0;i < 1000; i++){
			P2PUUID p2puuid = new P2PUUID("p2puuid"+i,1);
			list.add(p2puuid);
		}
		long currentTimeMillis2 = System.currentTimeMillis();
		P2PUUID.saveBatch(list);
		long currentTimeMillis = System.currentTimeMillis();
		System.err.println("间隔：：：：："+(currentTimeMillis-currentTimeMillis2));*/
		/*long countAll = P2PUUID.countAllNoUse();*/
		/*List<P2PUUID> nouseP2PUUIDs = P2PUUID.getNouseP2PUUIDs(3);
		for(P2PUUID s :nouseP2PUUIDs){
			System.err.println("s::"+s.getP2puuid());
		}*/
		/*P2PUUID byP2PUUID = P2PUUID.getByP2PUUID("ZR5GUZUZLN533WG5111A");
		System.err.println("byP2PUUID:"+byP2PUUID);*/
	}
}
