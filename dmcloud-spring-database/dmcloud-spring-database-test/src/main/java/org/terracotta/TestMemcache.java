package org.terracotta;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;

import org.junit.Test;

import com.apical.dmcloud.commons.infra.memcached.MemcacheContextManager;
import com.whalin.MemCached.MemCachedClient;

public class TestMemcache
{
	@Test
	public void testMemcache()
	{
		long begin = System.currentTimeMillis();
		MemCachedClient memCachedClient = MemcacheContextManager.getInstance().getMemCacheClient();
		
		String value = "qz test!!";
		memCachedClient.set("Key_TestQZ", new Date());
		
		Date value_mem = (Date)memCachedClient.get("Key_TestQZ");
		System.out.println(value_mem);
		long bw = System.currentTimeMillis() - begin;
		System.err.println(bw);
		//System.err.println(getServerIP("14.215.177.37").length);
		//System.err.println(getServerIP("14.215.177.37")[0].getHostAddress());
	}
	
	
    public static String getServerIP(String domain) {  
    	InetAddress[] server = null;;
        try {  
            server = InetAddress.getAllByName(domain);  
        } catch (UnknownHostException e) {  
           // e.printStackTrace();  
        	
        	return "127.0.0.1";
        }  
        if(server.length > 0){
        	return server[0].getHostAddress();
        }
        return "127.0.0.1";  
    }  
  
}
