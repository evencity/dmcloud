package com.apical.dmcloud.commons.infra.memcached;

import java.io.IOException;
import java.net.InetAddress;

import com.apical.dmcloud.commons.infra.net.NetworkUtils;
import com.whalin.MemCached.MemCachedClient;
import com.whalin.MemCached.SockIOPool;

public class MemcacheContextManager
{
	private static SockIOPool pool = null;
	private static MemCachedClient cachedClient = null;
	
	private static class SingletonHolder
	{
		//静态初始化器，由JVM来保证线程安全
		private static MemcacheContextManager nInstance = new MemcacheContextManager();
	}
	
	public MemcacheContextManager()
	{
		initializeMemcacheClientContext();
	}
	
	/**
	 * 获取Memcache上下文对象
	 * @return MemcacheContextManager - Memcache上下文对象
	 */
	public static MemcacheContextManager getInstance()
	{
		return SingletonHolder.nInstance;
	}
	
	/**
	 * 初始化memcache客户端
	 */
	private static void initializeMemcacheClientContext()
	{
		if(cachedClient == null)
		{
			synchronized (MemcacheContextManager.class)
			{
				if(cachedClient == null)
				{
					//初始化memcache客户端
					cachedClient = new MemCachedClient();
					//获取连接池的实例
					pool = SockIOPool.getInstance();
					
					//设置服务器信息
					try
					{
						InetAddress address = InetAddress.getLocalHost();
						//判断云中心的服务器是否可以连接，如果可以，就直接使用这个
						if(NetworkUtils.isIPAddressReachable(address.getHostAddress(),
								MemcacheConstant.ip_release))
						{
							pool.setServers(MemcacheConstant.MemcacheServers_Release);
						}
						else
						{
							pool.setServers(MemcacheConstant.MemcacheServers_Debug);
						}
					}
					catch (IOException e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					pool.setWeights(MemcacheConstant.MemcacheWeights);
					
					//设置初始连接数、最小连接数、最大连接数、最大处理时间
					pool.setInitConn(MemcacheConstant.MemcacheInitConnections);
					pool.setMinConn(MemcacheConstant.MemcacheMinConnections);
					pool.setMaxConn(MemcacheConstant.MemcacheMaxConnections);
					pool.setMaxIdle(MemcacheConstant.MemcacheMaxIdleTime);
					
					//设置连接池守护线程的睡眠时间
					pool.setMaintSleep(MemcacheConstant.MemcacheSleepTime);
					
					//设置TCP参数，连接超时
					pool.setNagle(MemcacheConstant.MemcacheNagle);
					pool.setSocketTO(MemcacheConstant.MemcacheReadTimeout);
					pool.setSocketConnectTO(MemcacheConstant.MemcacheConnectTimeout);
					
					//初始化并启动连接池
					pool.initialize();
				}
			}
		}
	}
	
	/**
	 * 获取memcache客户端对象
	 * @return memcache客户端对象
	 */
	public MemCachedClient getMemCacheClient()
	{
		return cachedClient;
		
	}
}
