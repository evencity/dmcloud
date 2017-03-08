package com.apical.dmcloud.spring.database.test;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.openkoala.koala.util.KoalaBaseSpringTestCase;

import com.apical.dmcloud.commons.infra.LicencePlate;
import com.apical.dmcloud.vehicle.core.VehicleNotExistException;
import com.apical.dmcloud.vehicle.core.domain.Vehicle;

public class TestVehicle extends KoalaBaseSpringTestCase{
	//@Test
	public void test(){
		try {
			System.err.println(new Date().getTime());
			Vehicle.getByDriverId(19l);
			System.err.println(new Date().getTime());
		} catch (VehicleNotExistException e) {
			e.printStackTrace();
		}
	}
//	@Test
	public void countAllByKeyWord(){
		long count = Vehicle.countAllByKeyWord(1l, "49", "", LicencePlate.BluePlate, "");
		System.err.println("count:" + count);
		List<Vehicle> vehiches = Vehicle.queryAllByKeyWordInPage(1l, "49", "", null, "", 1, 10);
		for(Vehicle vehicle : vehiches){
			System.err.println(vehicle.getNumber());
		}
		
	}
	//@Test
	public void testqueryVehicle(){
		List<Vehicle> Vehicles = Vehicle.queryAllByKeyWordInPage(1l, null, null, null, null, 1, 10);
		System.err.println(Vehicles.size());
		for(Vehicle v : Vehicles){
			System.err.println(v.getNumber());
		}
	}
	
//	@Test
	public void testexclude(){
		List<Long> exclude = new ArrayList<Long>();
		exclude.add(82l);
		exclude.add(97l);
		exclude.add(102l);
		exclude.add(104l);
		List<Vehicle>  vs= Vehicle.findByIds(exclude);
		for(Vehicle v:vs){
			System.err.println(v.getId());
		}
	}
	
	@Test
	public void testc(){
		 List<Long> LongList = new ArrayList<Long>();
		 List bigIntList = new ArrayList<BigInteger>();
		 bigIntList.add(new BigInteger("1"));
		 LongList = bigIntList;
		 for(int i = 0; i < bigIntList.size();i ++ ){
			 if( bigIntList.get(i) instanceof Long){
				 System.err.println("is Long type");
			 }else{
				 System.err.println("is not Long type");
			 }
			 
			 if( LongList.get(i) instanceof Long){
				 System.err.println("is Long type");
			 }else{
				 System.err.println("is not Long type");
			 }
			 
			 
			 if( bigIntList.get(i) instanceof BigInteger){
				 System.err.println("is BigInteger type");
			 }else{
				 System.err.println("is not BigInteger type");
			 }
			 
			 try{
				 for(Long l : LongList){
					 System.err.println(l);
				 }
			 }catch(Throwable t){
				t.printStackTrace();
			 }
			 
			 
		 }
	}
	
	

}
