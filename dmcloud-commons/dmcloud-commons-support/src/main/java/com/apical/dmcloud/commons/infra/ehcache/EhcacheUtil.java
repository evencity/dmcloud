package com.apical.dmcloud.commons.infra.ehcache;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import net.sf.ehcache.cluster.ClusterScheme;

public class EhcacheUtil {  
	  
    private static final String path = "/META-INF/ehcache.xml";  
  
    private URL url;  
  
    private CacheManager manager;  
  
    private static EhcacheUtil ehCache;  
  
    private EhcacheUtil(String path) {  
        url = getClass().getResource(path);  
        //System.err.println(url);
        manager = CacheManager.create(url);  
    }  
  
    public static EhcacheUtil getInstance() {  
        if (ehCache== null) {  
            ehCache= new EhcacheUtil(path);  
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
  
    public boolean isKeyInCache(String cacheName, String key){
    	Cache cache = manager.getCache(cacheName);  
    	return cache.isKeyInCache(key);
    }
    public Cache get(String cacheName) {  
        return manager.getCache(cacheName);  
    }  
  
    public void remove(String cacheName, String key) {  
        Cache cache = manager.getCache(cacheName);
        cache.remove(key);  
        
    }  
    
    public void removeAll(String cacheName, List<String> keys) {  
        Cache cache = manager.getCache(cacheName);
        cache.removeAll(keys);
        
    }  
    
    public List<Cache> getAllCache(){
    	String[] cacheNames = manager.getCacheNames();
    	List<Cache> caches = new ArrayList<Cache>();
    	for(String cacheName : cacheNames){
    		caches.add(manager.getCache(cacheName));
    	}
    	
    	return caches;
    }
  
}  