package com.apical.dmcloud.spring.database.test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.openkoala.koala.util.KoalaBaseSpringTestCase;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.apical.dmcloud.storage.core.domain.StorageInfo;
import com.apical.dmcloud.vehicle.core.domain.Violation;
@TransactionConfiguration(transactionManager="transactionManager", defaultRollback=false)
public class TestViolation extends KoalaBaseSpringTestCase{
	
	@Test
	public void test(){
		Violation v = new Violation();
		v.setUserId(1l);
		List<StorageInfo> infos = new ArrayList<StorageInfo>();
		StorageInfo s = new StorageInfo();
		s.setPath("/userlol//galkja");
		infos.add(s);
		v.setPictures(infos);
		v.save();
		
	}

}
