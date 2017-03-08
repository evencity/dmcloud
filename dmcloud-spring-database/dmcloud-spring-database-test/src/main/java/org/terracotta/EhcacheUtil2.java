package org.terracotta;

import java.net.URL;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

public class EhcacheUtil2 {  
	  
    private static final String path = "/META-INF/ehcache2.xml";  
  
    private URL url;  
  
    private CacheManager manager;  
  
    private static EhcacheUtil2 ehCache;  
  
    private EhcacheUtil2(String path) {  
        url = getClass().getResource(path);  
        System.err.println(url);
        manager = CacheManager.create(url);  
    }  
  
    public static EhcacheUtil2 getInstance() {  
        if (ehCache== null) {  
            ehCache= new EhcacheUtil2(path);  
        }  
        
        return ehCache;  
    }  
  
    public void put(String cacheName, String key, Object value) {  
        Cache cache = manager.getCache(cacheName);  
        Element element = new Element(key, value);  
        cache.put(element);  
    }  
  
    public Object get(String cacheName, String key) {  
        Cache cache = manager.getCache(cacheName);  
        Element element = cache.get(key);  
        return element == null ? null : element.getObjectValue();  
        
    }  
  
    public Cache get(String cacheName) {  
        return manager.getCache(cacheName);  
    }  
  
    public void remove(String cacheName, String key) {  
        Cache cache = manager.getCache(cacheName);

        cache.remove(key);  
    }  
  
}  