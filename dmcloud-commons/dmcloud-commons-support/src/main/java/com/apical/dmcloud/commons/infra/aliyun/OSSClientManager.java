package com.apical.dmcloud.commons.infra.aliyun;

import com.aliyun.oss.ClientConfiguration;
import com.aliyun.oss.OSSClient;
import com.apical.dmcloud.commons.infra.CONSTANT;

public class OSSClientManager
{
	private static OSSClientManager nInstance = null;
	private static OSSClientManager nIntervalInstance = null;
	
	/**
	 * oss客户端
	 */
	private OSSClient client = null;
	
	/**
	 * 是否为内外
	 */
	private boolean bInternal = false;
	
	private OSSClientManager(boolean bInternal)
	{
		this.bInternal = bInternal;
		init();
	}
	
	/**
	 * 获取oss客户端管理对象实例（公网的endpoint，没有公网ip的ecs服务器，请不要使用这个接口）
	 * @return oss客户端管理对象实例
	 */
	public static OSSClientManager getInstance()
	{
		if(nInstance == null)
		{
			synchronized(OSSClientManager.class)
			{
				if(nInstance == null)
				{
					nInstance = new OSSClientManager(false);
				}
			}
		}
		
		return nInstance;
	}
	
	/**
	 * 获取oss客户端管理对象实例（内网的endpoint，没有公网ip的ecs服务器，请使用这个接口）
	 * @return oss客户端管理对象实例
	 */
	public static OSSClientManager getInternalInstance()
	{
		if(nIntervalInstance == null)
		{
			synchronized(OSSClientManager.class)
			{
				if(nIntervalInstance == null)
				{
					nIntervalInstance = new OSSClientManager(true);
				}
			}
		}
		
		return nIntervalInstance;
	}
	
	/**
	 * oss客户端初始化
	 * @return 初始化是否成功
	 */
	private boolean init()
	{
		if(client == null)
		{
			client = initOSSClient();
		}
		
		if(client != null)
			return true;
		else
			return false;
	}
	
	/**
	 * 创建oss客户端
	 * @return oss客户端
	 */
	private OSSClient initOSSClient()
	{
		//配置oss客户端
		ClientConfiguration configuratin = new ClientConfiguration();
		//configuratin.setMaxConnections(CONSTANT.OSS_MaxConnections);
		configuratin.setMaxConnections(32);
		
		String endpoint = null;
		if(bInternal)
		{
			endpoint = CONSTANT.OSS_Endpoint_Internal;
		}
		else
		{
			endpoint = CONSTANT.OSS_Endpoint;
		}
		
		OSSClient client = new OSSClient(endpoint, CONSTANT.OSS_AccessKeyId,
				CONSTANT.OSS_AccessKeySecret);

		return client;
	}
	
	/**
	 * 在回收此对象时，关闭oss客户端
	 */
	protected void finalize() throws Throwable
	{
		closeOSSClient();
	}
	
	/**
	 * 获取oss客户端
	 * @return oss客户端
	 */
	public OSSClient getOSSClient()
	{
		return client;
	}
	
	/**
	 * 关闭oss客户端
	 */
	public void closeOSSClient()
	{
		if(client != null)
		{
			client.shutdown();
		}
	}
}
