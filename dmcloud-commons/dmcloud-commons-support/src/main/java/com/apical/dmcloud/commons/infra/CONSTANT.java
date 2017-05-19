package com.apical.dmcloud.commons.infra;

public class CONSTANT
{
	//----------------------------------------------------------------
	//-----数学，地理常量定义
	public static double pi = 3.14159265358979324;//圆周率（Pi）
	public static double a = 6378245.0;
	public static double ee = 0.00669342162296594323;
	public static int r = 6371229;//地球半径
	public static double lng_1m = 0.0000089829;//1米约等于0.0000089829经度
	public static double lat_1m = 0.0000089979;//1米约等于0.0000089979纬度
	//----------------------------------------------------------------
	
	
	//----------------------------------------------------------------
	//-----文件大小及数量定义
	/**
	 * 字节数：4k
	 */
	public static int BYTES_4K = 4096;
	//----------------------------------------------------------------
	
	
	//----------------------------------------------------------------
	//-----行车记录宏定义
	/**
	 * gps文件后缀名
	 */
	public static String GPSFileExtension = ".txt";
	
	/**
	 * 每隔10秒钟，进行一次违规分析
	 */
	public static long RuleAnalysisInterval = 10 * 1000;
	
	/**
	 * 每隔5分钟，进行一次检测memcache中是否存在gps发送状态信息
	 */
	public static long CheckGPSStatusInterval = 5 * 60 * 1000;
	//----------------------------------------------------------------
	
	
	//----------------------------------------------------------------
	//-----存储位置变量定义
	/**
	 * hadoop存储系统
	 */
	public static int StorageLocation_Undefined = 0;
	
	/**
	 * hadoop存储系统
	 */
	public static int StorageLocation_Hadoop = 1;
	
	/**
	 * 阿里云对象存储系统
	 */
	public static int StorageLocation_OSS = 2;
	
	/**
	 * 文件存储位置
	 */
	public static int StorageLocation = StorageLocation_Undefined;
	//----------------------------------------------------------------
	
	
	/**
	 * 百度地图Geocoding API服务地址
	 */
	public static String LBSGeocodingUrl = "http://api.map.baidu.com/geocoder/v2/";
	
	/**
	 * 申请的百度地图Geocoding API服务access key
	 */
	public static String LBSGeocodingACCESSKey = "5X0unSx0GZIsdcLrFGsjCCGl";
	
	/**
	 * 百度地图Geocoding API服务的其他固定参数；当然可以进行更改，其他参数请参考百度地图api相关资料
	 * 其中：
	 * coordtype=wgs84ll表示：坐标类型为wgs84ll（ GPS经纬度）  //bd09ll
	 * output=json表示：输出格式为json，即百度服务器返回的数据类型为json
	 * pois=0表示：是否显示指定位置周边的poi，0为不显示，1为显示。当值为1时，显示周边100米内的poi。
	 */
	public static String LBSGeocodingParams = "&coordtype=bd09ll&output=json&pois=0";
	
	
	//-----------------------------------------------------------------
	//-----阿里云oss常量
	/**
	 * 使用OSS Bucket所在区域地址以及endpoint
	 */
	public static String OSS_Region = "oss-cn-hangzhou";
	public static String OSS_Endpoint = "http://oss-cn-hangzhou.aliyuncs.com";
	public static String OSS_Endpoint_Internal = "";
	
	/**
	 * 有效的 Access Key(包括AccessKeyId和AccessKeySecret)
	 */
	public static String OSS_AccessKeyId = "ylk2sLim2nXRBCCB";
	public static String OSS_AccessKeySecret = "VAiBjv8CobN6YptmlEjemwCiblfcZb";
	
	/**
	 * OSS客户端配置参数
	 */
	//最大连接数
	public static int OSS_MaxConnections = 256;
	
	/**
	 * 文件命名规范
	 */
	//用于存储资源的bucket
	public static String OSS_Bucket_DMCloud = "";
	//dmcloud在oss上的访问域名
	public static String OSS_DMCloud_CNAME = "http://" + OSS_Bucket_DMCloud + "." + OSS_Region + ".aliyuncs.com/";
	//存储用户头像的目录名称
	public static String OSS_Directory_UserPhoto = "image/userPhoto/";
	//存储用户图片的目录名称
	public static String OSS_Directory_UserImage = "image/userImage/";
	//存储用户视频的目录名称
	public static String OSS_Directory_UserVideo = "video/userVideo/";
	//存储设备上传的行车图片目录名称
	public static String OSS_Directory_DrivingImage_Device = "image/drivingImage/device/";
	//存储设备上传的行车视频目录名称
	public static String OSS_Directory_DrivingVideo_Device = "video/drivingVideo/device/";
	//存储用户上传的行车图片目录名称
	public static String OSS_Directory_DrivingImage_User = "image/drivingImage/user/";
	//存储用户上传的行车视频目录名称
	public static String OSS_Directory_DrivingVideo_User = "video/drivingVideo/user/";
	//存储设备上传的报警图片目录名称
	public static String OSS_Directory_AlarmImage_Device = "image/alarmImage/device/";
	//存储设备上传的报警视频目录名称
	public static String OSS_Directory_AlarmVideo_Device = "video/alarmVideo/device/";
	//存储用户上传的报警图片目录名称
	public static String OSS_Directory_AlarmImage_User = "image/alarmImage/user/";
	//存储用户上传的报警视频目录名称
	public static String OSS_Directory_AlarmVideo_User = "video/alarmVideo/user/";
	//存储其他的缩略图
	public static String OSS_Directory_Thumbnail = "image/thumbnails/";
	//存储广告资源
	public static String OSS_Directory_Advertisement = "advertisements/";
	//gps文件存储位置
	public static String OSS_Directory_GPSFile = "gpsfile/device/";
	
	public static String OSS_Directory_ViolationImage = "image/ViolationImage/";
	
	
	static {
		if (AliyunRegionIdConfig.regionId.equals(AliyunRegionIdConfig.cnHangzhou)) {//杭州
			OSS_Region = "oss-cn-hangzhou";
			OSS_Endpoint = "http://oss-cn-hangzhou.aliyuncs.com";
			OSS_Endpoint_Internal = "http://oss-cn-hangzhou-internal.aliyuncs.com";
			OSS_Bucket_DMCloud = "dmcloud-user";
		} else if (AliyunRegionIdConfig.regionId.equals(AliyunRegionIdConfig.apSsoutheast1)) {//亚太东南1
			OSS_Region = "oss-ap-southeast-1";
			OSS_Endpoint = "http://ons.ap-southeast-1.aliyuncs.com";
			OSS_Endpoint_Internal = "http://vpc100-oss-ap-southeast-1.aliyuncs.com";
			OSS_Bucket_DMCloud = "dmcloud-user-sg";
		}
	}
	
	
	
}
