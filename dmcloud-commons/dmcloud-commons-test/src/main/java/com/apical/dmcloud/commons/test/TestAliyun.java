package com.apical.dmcloud.commons.test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.AppendObjectRequest;
import com.aliyun.oss.model.AppendObjectResult;
import com.aliyun.oss.model.ObjectMetadata;
import com.apical.dmcloud.commons.infra.CONSTANT;
import com.apical.dmcloud.commons.infra.aliyun.OSSClientManager;

public class TestAliyun
{
	
	public static void main(String args[])
	{
		String gsp = "kl" + System.getProperty ("line.separator") + "mn";
		
		OSSClientManager manager = OSSClientManager.getInstance();
		OSSClient client = manager.getOSSClient();
		
		InputStream input = new ByteArrayInputStream(gsp.getBytes());
		//client.putObject(CONSTANT.OSS_Bucket_DMCloud, "image/11.txt", input);
		
		AppendObjectRequest request = new AppendObjectRequest(CONSTANT.OSS_Bucket_DMCloud,
				"image/11.txt", input);
		request = request.withPosition(499L);
		
		AppendObjectResult result = null;
		try
		{
			result = client.appendObject(request);
		}
		catch(OSSException e)
		{
			if(e.getErrorCode().equalsIgnoreCase("PositionNotEqualToLength"))
			{
				System.out.println("eeesfr!!!");
				System.out.println(result);
				
				
			}
		}
		
		ObjectMetadata meta = client.getObjectMetadata(CONSTANT.OSS_Bucket_DMCloud,
				"image/11.txt");
		InputStream input11 = new ByteArrayInputStream(gsp.getBytes());
		AppendObjectRequest request11 = new AppendObjectRequest(CONSTANT.OSS_Bucket_DMCloud,
				"image/11.txt", input11);
		request11 = request11.withPosition(meta.getContentLength());
		result = client.appendObject(request11);
		
		System.out.println("end!!!!");
	}
}
