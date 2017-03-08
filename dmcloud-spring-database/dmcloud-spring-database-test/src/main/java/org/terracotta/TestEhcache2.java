package org.terracotta;

import java.util.Date;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

public class TestEhcache2 {
	
	
	public  static void main(String args[]){
		
		EhcacheUtil2 util = EhcacheUtil2.getInstance();
		for(int i = 1; i < 10000; i ++){
			util.put("colors", "hello" + (i+ 10000), new Date());
			Date d = (Date)util.get("colors", "hello");
			//System.err.println(d);
			if(i%50 == 0){
				Cache cache = util.get("colors");
				StringBuilder s = new StringBuilder();
				s.append("disk:").append(cache.calculateOnDiskSize())
				 .append("\n").append("offHeap:").append(  cache.calculateOffHeapSize())
				 .append("\n").append("memorySize:")
				 .append(cache.calculateInMemorySize())
				 .append("\nLocalsize:").append(i)
				 .append("\nTotelSize:").append(cache.getKeys().size());
					
				  System.err.println(s);
				  Element e =  cache.get("hello1");
				   if(e != null){
					   Date t = (Date)e.getObjectValue();	
						System.err.println(t);
				   }
				   if(i%200 == 0){
						cache.remove("hello1", true);
					}
					
			}
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
	
	}

}
