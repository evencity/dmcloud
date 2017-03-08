package org.terracotta;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.apical.dmcloud.security.core.domain.User;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

public class TestEhcache {
	
	
	public  static void main(String args[]){
		
		EhcacheUtil util = EhcacheUtil.getInstance();
		for(int i = 1; i < 10000; i ++){
			User u1 = new User();
			User u2 = new User();
			List<User> u = new ArrayList<User>();
			u.add(u2);
			u.add(u1);
			util.put("colors", "hello" + i, u);
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
				Element e = cache.get("hello1");
				Element e2 = cache.get("hello10");
				System.err.println("EEEEEEEEEEE:");
				System.err.println(e);
				if(e != null){
					List<User> t = (List<User>)e.getObjectValue();	
					System.err.println(t);

				}else{
					System.err.println("对象不存在");
				}
				System.err.println(s);
		        
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
