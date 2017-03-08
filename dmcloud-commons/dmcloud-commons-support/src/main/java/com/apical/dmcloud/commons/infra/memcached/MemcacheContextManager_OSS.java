/*package com.apical.dmcloud.commons.infra.memcached;

import java.io.IOException;
import java.net.InetAddress;

import com.apical.dmcloud.commons.infra.net.NetworkUtils;
import net.spy.memcached.AddrUtil;
import net.spy.memcached.BinaryConnectionFactory;
import net.spy.memcached.MemcachedClient;


public class MemcacheContextManager_OSS
{
	private static MemcachedClient cachedClient = null;
	
	
	private static class SingletonHolder
	{
		//静态初始化器，由JVM来保证线程安全
		private static MemcacheContextManager_OSS nInstance = new MemcacheContextManager_OSS();
	}
	
	public MemcacheContextManager_OSS()
	{
		initializeMemcacheClientContext();
	}
	
	*//**
	 * 获取Memcache上下文对象
	 * @return MemcacheContextManager - Memcache上下文对象
	 *//*
	public static MemcacheContextManager_OSS getInstance()
	{
		return SingletonHolder.nInstance;
	}
	
	*//**
	 * 初始化memcache客户端
	 *//*
	private static void initializeMemcacheClientContext()
	{
		if(cachedClient == null)
		{
			synchronized (MemcacheContextManager_OSS.class)
			{
				if(cachedClient == null)
				{
					
					//设置服务器信息
					try
					{
						InetAddress address = InetAddress.getLocalHost();
						//判断云中心的服务器是否可以连接，如果可以，就直接使用这个
						if(NetworkUtils.isIPAddressReachable(address.getHostAddress(),
								MemcacheConstant.ip_release))
						{
                            cachedClient = new MemcachedClient(new BinaryConnectionFactory(), AddrUtil.getAddresses(MemcacheConstant.MemcacheServers_Release[0]));
                            
                  
						}
						else
						{
							   cachedClient = new MemcachedClient(new BinaryConnectionFactory(), AddrUtil.getAddresses(MemcacheConstant.MemcacheServers_Debug[0]));
						}
					}
					catch (IOException e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				
				}
			}
		}
	}
	
	*//**
	 * 获取memcache客户端对象
	 * @return memcache客户端对象
	 *//*
	public MemcachedClient getMemCacheClient()
	{
		return cachedClient;
		
	}
}
*/