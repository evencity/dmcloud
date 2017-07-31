package com.apical.dmcloud.commons.infra.aliyun;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.Calendar;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.AppendObjectRequest;
import com.aliyun.oss.model.AppendObjectResult;
import com.aliyun.oss.model.OSSObject;
import com.apical.dmcloud.commons.infra.CONSTANT;
import com.apical.dmcloud.commons.infra.StringUtils;

public class OSSServiceUtils
{
	/**
	 * 按照时间戳来生成文件名
	 * @param srcFileName 源文件
	 * @param timeStamp 时间戳
	 * @return 生成的文件名
	 */
	public static String generateFilenameWithExtention(String srcFileName, long timeStamp)
	{
		//在这里，我们需要生成保存在oss中的文件名，带文件扩展名
		//目前，保存在hadoop中的文件，其文件名的生成策略为：时间戳（13个字符）-随机字符串（8个字符）
		//这里生成文件名的策略同生成表ResourceIndex的行健是一样的
		String desFilename = null;
		String extention = StringUtils.getFileExtension(srcFileName);
		desFilename = Long.toString(timeStamp) + "-"
				+ StringUtils.getRandomString(8) + "." + extention;
		
		return desFilename;
	}
	
	/**
	 * 生成小尺寸缩略图的存储文件名
	 * @param srcFileName 缩略图原图存储在oss上的文件名
	 * @param srcThumbnail 缩略图源文件
	 * @param size 尺寸（1表示小尺寸，2表示中等尺寸，3表示大尺寸）
	 * @return 文件名
	 */
	public static String generateThumbnailWithExtention(String srcFileName,
			String srcThumbnail, int size)
	{
		//在这里，我们需要生成保存在oss中的缩略图，带文件扩展名
		//目前，保存在hadoop中的文件，其文件名的生成策略为：时间戳（13个字符）-随机字符串（8个字符）
		//这里生成文件名的策略同生成表ResourceIndex的行健是一样的
		String dpi = null;
		if(size == 1)
		{
			dpi = "-ldpi.";
		}
		else if(size == 2)
		{
			dpi = "-mdpi.";
		}
		else if(size == 3)
		{
			dpi = "-hdpi.";
		}
		else
		{
			dpi = "-ldpi.";
		}
		String extention = StringUtils.getFileExtension(srcThumbnail);
		String desFilename = srcFileName + dpi + extention;
		
		return desFilename;
	}
	
	/**
	 * 设置用户头像文件的存储路径
	 * @param userId 用户id
	 * @param filename 文件名
	 * @return 头像文件的目标存储路径
	 */
	public static String setUserPhotoPathInOSS(long userId, String filename)
	{
		//设置用户头像文件的存储路径
		String desPath = CONSTANT.OSS_Directory_UserPhoto + userId + "/" + filename;
		
		return desPath;
	}
	
	/**
	 * 设置用户资源的存储路径
	 * @param userId 用户id
	 * @param resourceType 资源类型，1表示视频，2表示图片
	 * @param isUrgent 是否是紧急资源
	 * @param desFilename 目标文件名
	 * @return 存储路径
	 */
	public static String setUserResourcePathInOSS(long userId, int resourceType,
			boolean isUrgent, String desFilename)
	{
		String desPath = null;
		//资源是否是紧急报警文件
		if(!isUrgent)
		{
			if(resourceType == 1)
			{
				desPath = CONSTANT.OSS_Directory_UserVideo;
			}
			else if(resourceType == 2)
			{
				desPath = CONSTANT.OSS_Directory_UserImage;
			}
			else
			{
				return null;
			}
		}
		else
		{
			if(resourceType == 1)
			{
				desPath = CONSTANT.OSS_Directory_AlarmVideo_User;
			}
			else if(resourceType == 2)
			{
				desPath = CONSTANT.OSS_Directory_AlarmImage_User;
			}
			else
			{
				return null;
			}
		}
		
		//加上用户id
		desPath = desPath + userId;
		
		//获取当前的系统日期
		Calendar cal = Calendar.getInstance();
		desPath = desPath + String.format("/%d-%02d-%02d/", cal.get(Calendar.YEAR),
				(cal.get(Calendar.MONTH) + 1), cal.get(Calendar.DAY_OF_MONTH));
		
		//加上文件名
		desPath = desPath + desFilename;
		
		return desPath;
	}
	
	/**
	 * 设置行车资源的存储路径
	 * @param productId 产品id
	 * @param resourceType 资源类型，1表示视频，2表示图片
	 * @param isUrgent 是否是紧急资源
	 * @param desFilename 目标文件名
	 * @return 存储路径
	 */
	public static String setDrivingResourcePathInOSS(long productId, int resourceType,
			boolean isUrgent, String desFilename)
	{
		String desPath = null;
		//资源是否是紧急报警文件
		if(!isUrgent)
		{
			if(resourceType == 1)
			{
				desPath = CONSTANT.OSS_Directory_DrivingVideo_Device;
			}
			else if(resourceType == 2)
			{
				desPath = CONSTANT.OSS_Directory_DrivingImage_Device;
			}
			else
			{
				return null;
			}
		}
		else
		{
			if(resourceType == 1)
			{
				desPath = CONSTANT.OSS_Directory_AlarmVideo_Device;
			}
			else if(resourceType == 2)
			{
				desPath = CONSTANT.OSS_Directory_AlarmImage_Device;
			}
			else
			{
				return null;
			}
		}
		
		//加上用户id
		desPath = desPath + productId;
		
		//获取当前的系统日期
		Calendar cal = Calendar.getInstance();
		desPath = desPath + String.format("/%d-%02d-%02d/", cal.get(Calendar.YEAR),
				(cal.get(Calendar.MONTH) + 1), cal.get(Calendar.DAY_OF_MONTH));
		
		//加上文件名
		desPath = desPath + desFilename;
		
		return desPath;
	}
	
	/**
	 * 设置行车资源的存储路径
	 * @param desFilename 目标文件名
	 * @return 存储路径
	 */
	public static String setThumbnailPathInOSS(String desFilename)
	{
		//获取当前的系统日期
		Calendar cal = Calendar.getInstance();
		String desPath = CONSTANT.OSS_Directory_Thumbnail + String.format("%d-%02d-%02d/",
				cal.get(Calendar.YEAR), (cal.get(Calendar.MONTH) + 1), cal.get(Calendar.DAY_OF_MONTH));
		
		//加上文件名
		desPath = desPath + desFilename;
		
		return desPath;
	}
	
	/**
	 * 设置广告资源的存储路径
	 * @param desFilename 目标文件名
	 * @return 存储路径
	 */
	public static String setAdvertisementPathInOSS(String desFilename)
	{
		//加上文件名
		String desPath = CONSTANT.OSS_Directory_Advertisement + desFilename;
		
		return desPath;
	}
	
	/**
	 * 判断文件在oss上是否存在
	 * @param filePath 文件存储路径
	 * @return 文件是否存在
	 */
	public static boolean isExistInOSS(OSSClientManager manager, String filePath)
	{
		OSSClient client = manager.getOSSClient();
		if(client == null)
		{
			return false;
		}
		
		return client.doesObjectExist(CONSTANT.OSS_Bucket_DMCloud, filePath);
	}
	
	/**
	 * 将文件上传到oss
	 * @param srcFilePath 源文件存储路径
	 * @param desFilePath 目标文件存储路径
	 */
	public static boolean uploadFileToOSS(OSSClientManager manager, String srcFilePath, String desFilePath)
	{
		OSSClient client = manager.getOSSClient();
		if(client == null)
		{
			return false;
		}
		
		File srcFile = new File(srcFilePath);
		client.putObject(CONSTANT.OSS_Bucket_DMCloud, desFilePath, srcFile);
		
		return true;
	}
	
	/**
	 * 从oss上下载文件
	 * @param srcFilePath 源文件存储路径
	 * @param desFilePath 目标文件存储路径
	 */
	public static boolean downloadFileInOSS(OSSClientManager manager, String srcFilePath, String desFilePath)
			throws IOException
	{
		OSSClient client = manager.getOSSClient();
		if(client == null)
		{
			return false;
		}
		
		OSSObject object = client.getObject(CONSTANT.OSS_Bucket_DMCloud, srcFilePath);
		byte buffer[] = new byte[CONSTANT.BYTES_4K];
		OutputStream out = null;
		InputStream in = object.getObjectContent();
		try
		{
			out = new BufferedOutputStream(new FileOutputStream(desFilePath));
			
			int bytesRead = 0;
			while((bytesRead = in.read(buffer)) > 0)
			{
				out.write(buffer, 0, bytesRead);
			}
			out.flush();
		}
		finally
		{
			try
			{
				if(in != null)
				{
					in.close();
					in = null;
				}
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}
				
			try
			{
				if(out != null)
				{
					out.close();
					out = null;
				}
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}
		}
		
		return true;
	}
	
	/**
	 * 从oss上下载文件
	 * @param srcFileURL 源文件URL
	 * @param desFilePath 目标文件存储路径
	 */
	public static boolean downloadFileInOSS(OSSClientManager manager, URL srcFileURL, String desFilePath)
			throws IOException
	{
		String keyname = srcFileURL.getPath();
		keyname = keyname.substring(1);
		return downloadFileInOSS(manager, keyname, desFilePath);
	}
	
	/**
	 * 删除存储在oss上的文件
	 * @param srcFilePath 源文件存储路径
	 */
	public static boolean deleteFileInOSS(OSSClientManager manager, String srcFilePath)
	{
		OSSClient client = manager.getOSSClient();
		if(client == null)
		{
			return false;
		}
		
		client.deleteObject(CONSTANT.OSS_Bucket_DMCloud, srcFilePath);
		
		return true;
	}
	
	/**
	 * 删除存储在oss上的文件
	 * @param srcFileURL 源文件的URL
	 */
	public static boolean deleteFileInOSS(OSSClientManager manager, URL srcFileURL)
	{
		//一个存储对象的完整路径，如：http://dcloud-user.oss-cn-hangzhou.aliyuncs.com/image/PFC4XsvJ.jpg
		//那么，URL.getPath()=/image/PFC4XsvJ.jpg
		//而该对象的key为：image/PFC4XsvJ.jpg，即要去掉字符'/'
		String keyname = srcFileURL.getPath();
		keyname = keyname.substring(1);
		return deleteFileInOSS(manager, keyname);
	}
	
	/**
	 * 将字节数组直接写入oss中的文件
	 * @param key 目标文件在oss中的key
	 * @param lastPosition 文件的写入位置
	 * @param datas 字节数据
	 * @return 文件写入后的位置
	 * @throws IOException 
	 */
	public static long appendBytes2OSS(OSSClientManager manager, String key, long lastPosition, byte[] datas) throws IOException
	{
		OSSClient client = manager.getOSSClient();
		if(client == null)
		{
			throw new IOException("Get OSSClient failed!");
		}
		
		//生成追加文件的请求
		InputStream input = new ByteArrayInputStream(datas);
		AppendObjectRequest request = new AppendObjectRequest(CONSTANT.OSS_Bucket_DMCloud,
				key, input);
		request.setPosition(lastPosition);
		
		//将数据写入oss中
		AppendObjectResult appendObjectResult = client.appendObject(request);
		
		//返回最后文件写入的位置
		return appendObjectResult.getNextPosition();
	}
	
	/**
	 * 设置举报图片的路径
	 * @param userId
	 * @param desFilename
	 * @return
	 */
	public static String setViolationImagePathInOss(long userId, String desFilename)
	{
		//加上文件名
		String desPath = CONSTANT.OSS_Directory_ViolationImage + userId + "/" + desFilename;
		
		return desPath;
	}
}
