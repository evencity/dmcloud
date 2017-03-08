package com.apical.dmcloud.commons.infra;

import java.io.IOException;
import java.util.Properties;

public class SystemProperties
{
	/**
	 * 从配置文件中载入资源存储位置信息
	 * @return 资源存储位置信息
	 */
	public int loadStorageLocation()
	{
		int locationIndex = CONSTANT.StorageLocation_Undefined;
		Properties properties = new Properties();
		try
		{
			properties.load(getClass().getResourceAsStream("/storage.properties"));
			
			String location = properties.getProperty("storage.location");
			if(location.compareToIgnoreCase("hadoop") == 0)
			{
				locationIndex = CONSTANT.StorageLocation_Hadoop;
			}
			else if(location.compareToIgnoreCase("oss") == 0)
			{
				locationIndex = CONSTANT.StorageLocation_OSS;
			}
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		CONSTANT.StorageLocation = locationIndex;
		
		return locationIndex;
	}
}
