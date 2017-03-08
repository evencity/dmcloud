package com.apical.dmcloud.middle.database.test;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import org.junit.Test;

import com.apical.dmcloud.middle.infra.EntityManagerHelper;
import com.apical.dmcloud.rule.middle.core.domain.DrivingArea;
import com.apical.dmcloud.rule.middle.core.domain.RuleAreaSpeed;
import com.apical.dmcloud.security.middle.core.domain.User;
import com.alibaba.druid.support.hibernate.DruidConnectionProvider;
public class TestUser
{
	@Test
	public void testVerifyUser()
	{
		/*boolean bRes = User.verifyUserByAccountAndPassword("koala", "111111");
		System.out.println(bRes);*/
		
		 int count = 10000;
		 long start = System.currentTimeMillis();
		 while(count > 1){
			 Thread.currentThread().setName("thread--"  + count  + "---");
			 
			 User u = User.getByUserAccount("koala");
			 
			 System.err.println( Thread.currentThread().getName() + u.getName() + "----count:" + (100 -count) + "---"+  (System.currentTimeMillis()-start));
			 count--;
		 }
		
	}
	
	
	public static void main(String args[]){
	/*	for(int i = 1; i< 100; i++ ){
			Runnable run = new Runnable() {
				
				@Override
				public void run() {
					int count = 1000;
					while(count > 1){
						User u = User.getByUserAccount("koala");
						System.out.println(u.getName());
					}
					
				}
			};
			
			new Thread(run).start();
		}*/
		
		final ExecutorService exec = Executors.newFixedThreadPool(100);
		//监控线程池的线程
		Runnable monitor = new Runnable() {
			
			@Override
			public void run() {
				try{
					int cn = 1;
					ThreadPoolExecutor executor =(ThreadPoolExecutor) exec;
					while(true){
						
					      System.out.println(
				                    String.format("[monitor] [%d/%d] Active: %d, Completed: %d, Task: %d, isShutdown: %s, isTerminated: %s",
				                        executor.getPoolSize(),
				                        executor.getCorePoolSize(),
				                        executor.getActiveCount(),
				                        executor.getCompletedTaskCount(),
				                        executor.getTaskCount(),
				                        executor.isShutdown(),
				                        executor.isTerminated()));
					      cn++;
					      Thread.sleep(5000);
					      if(executor.getTaskCount() != 0 && executor.getCompletedTaskCount() == executor.getTaskCount() ){
					    	 //执行完所有任务退出
					    	  System.err.println("--------完成任务-----");
					    	  Thread.sleep(3000);
					    	  exec.shutdown();
					    	  break;
					      }
					}
				
				}catch(Throwable t){
					System.err.println("监控线程：");
					t.printStackTrace();
				}
			}
		};
		new Thread(monitor).start();
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		for(int index = 0; index < 300; index++) {
		final int i = index;	
		final long start =  System.currentTimeMillis();
		Runnable run = new Runnable() {
		public void run() {
		
			 if(Thread.currentThread().getName()== null || !Thread.currentThread().getName().contains("threadx")){
				 Thread.currentThread().setName("threadx--"  + i  + "---");
				 
					//开始事务
					
			 }
		try {
			 int count = 2; //每个线程执行300 次，在每次中异常被捕获继续执行
			 while(count > 1){
				// System.err.println(Thread.currentThread().getName());
				 boolean is = EntityManagerHelper.getEntityManager().getTransaction().isActive();
				 if(!is){
					 EntityManagerHelper.beginTransaction();
				 }
				
				
				 DrivingArea da = new DrivingArea("index_" + i);
				 da.save();
				 System.out.println(Thread.currentThread().getName() + "__" + count );
				 EntityManagerHelper.commitTransaction();
				 if(i % 10 == 0){
					 Thread.sleep(200000);
				 }
				 EntityManagerHelper.getEntityManager().close();
				// System.err.println( Thread.currentThread().getName() + u.getName() + "----count:" + (100 -count) + "---"+  (System.currentTimeMillis()-start));
				 count--;
			 }
		//Thread.sleep(1000);
		} catch (Throwable e) {
			 System.err.println(Thread.currentThread().getName());
			e.printStackTrace();
		}
		}
		};
		exec.execute(run);
		}
		// must shutdown
	
		
	}
}
