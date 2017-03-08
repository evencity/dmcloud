package com.apical.dmcloud.middle.database.test;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.Test;

import com.apical.dmcloud.rule.middle.core.domain.RuleAreaSpeed;
import com.apical.dmcloud.vehicle.middle.core.VehicleNotExistException;
import com.apical.dmcloud.vehicle.middle.core.domain.Device;
public class TestRuleRAareSpeed {
	@Test
	public void test(){
		 
		for(int i = 1 ; i< 5; i++){
			long d1 = new Date().getTime();
			try{
				
			
			List<RuleAreaSpeed>  rules = RuleAreaSpeed.queryAllRulesByVehicleId(3l);
			long d2 = new Date().getTime();
			System.err.println(d2- d1);
			for(RuleAreaSpeed rule : rules){
				System.err.println(rule.getName());
			//	System.err.println(rule.getArea().getName());
			}
			}catch(Throwable t){
				t.printStackTrace();
			}
		}
	/*	
		for(int i = 1; i< 5; i++ ){
			try {
				long id = Device.getVehicleIdByDeviceId(2);
				System.err.println("ID:" + id);
			} catch (VehicleNotExistException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		*/
		
		

		// only two threads
/*		ExecutorService exec = Executors.newFixedThreadPool(2);
		for(int index = 0; index < 2; index++) {
		final int i = index;	
		Runnable run = new Runnable() {
		public void run() {
		long time = (100-i)*200;

		try {
			 Thread.currentThread().setName("my name is "  + i);
			 
			 List<RuleAreaSpeed>  rules = RuleAreaSpeed.queryAllRulesByVehicleId(3l);
			 
			 
			 System.err.println( Thread.currentThread().getName() + rules.size());
			 
			 
		Thread.sleep(time);
		} catch (InterruptedException e) {
			
			System.err.println(e);
		}
		}
		};
		exec.execute(run);
		}
		// must shutdown
		exec.shutdown();
*/
	

		
		
	}

	
	public static void main(String args[]){
		for(int i = 1; i< 100; i++ ){
			Runnable run = new Runnable() {
				
				@Override
				public void run() {
					 List<RuleAreaSpeed>  rules = RuleAreaSpeed.queryAllRulesByVehicleId(3l);
					 
					 System.err.println(rules.size());
					 System.err.println( Thread.currentThread().getName() + rules.size());
				}
			};
			
			new Thread(run).start();
		}
	}
}
