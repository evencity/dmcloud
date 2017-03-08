package com.apical.dmcloud.commons.infra.CoordinateConvert;

import java.util.List;

import com.apical.dmcloud.commons.infra.CONSTANT;

/**
 * 这个类实现了：从经纬度（即地球坐标系）转换到地图坐标系（这些地图坐标系包括：谷歌地图，百度地图，高德地图，腾讯地图等）
 * @author qiuzeng
 *
 */
public class CoordinateConvert
{
	final static double pi180 = 0.01745329251994329578;// pi180=pi/180
	final static double fun1 = 0.0000090435329131;//fun1 = 180/(a*pi*(1-ee))
	final static double fun2 = 0.0000089830007334;//fun2 = 180/(a*pi)
	final static double x_pi = 52.359877559829887333;//x_pi = pi * 3000.0 / 180
	
	/**
	 * 从地球坐标系 (WGS-84) 到火星坐标系 (GCJ-02) ;
	 * 谷歌地图，高德地图，腾讯地图 等采用火星坐标系;
	 * 这个坐标转换算法只适合于中国大陆;
	 * @param latitude - 纬度
	 * @param longtitude - 经度
	 * @return MapCoordinate - 转换后的经纬度值
	 */
	public static MapCoordinate WGS84_To_GCJ02(double latitude, double longtitude)
	{
		double wgLat, wgLon, outlat, outlng;
		
		wgLat = latitude;
		wgLon = longtitude;
		
		double dLat = transformLat(wgLon - 105.0, wgLat - 35.0);
		double dLon = transformLon(wgLon - 105.0, wgLat - 35.0);
		double radLat = wgLat * pi180;
		double magic = Math.sin(radLat);
		magic = 1 - CONSTANT.ee * magic * magic;
		double sqrtMagic = Math.sqrt(magic);
		dLat = dLat * magic * sqrtMagic * fun1;
		dLon = dLon * sqrtMagic * fun2 / Math.cos(radLat);
		outlat = wgLat + dLat;
		outlng = wgLon + dLon;
		
		MapCoordinate outCoordinate = new MapCoordinate(outlat, outlng);
		
		return outCoordinate;
	}
	
	/**
	 * 从地球坐标系 (WGS-84) 到火星坐标系 (GCJ-02) ;
	 * 谷歌地图，高德地图，腾讯地图 等采用火星坐标系;
	 * 这个坐标转换算法只适合于中国大陆;
	 * @param coordinate - 需要转换的经纬度值
	 * @return MapCoordinate - 转换后的经纬度值
	 */
	public static MapCoordinate WGS84_To_GCJ02(MapCoordinate coordinate)
	{
		double wgLat, wgLon, outlat, outlng;
		
		wgLat = coordinate.getLatitude();
		wgLon = coordinate.getLongitude();
		
		double dLat = transformLat(wgLon - 105.0, wgLat - 35.0);
		double dLon = transformLon(wgLon - 105.0, wgLat - 35.0);
		double radLat = wgLat * pi180;
		double magic = Math.sin(radLat);
		magic = 1 - CONSTANT.ee * magic * magic;
		double sqrtMagic = Math.sqrt(magic);
		dLat = dLat * magic * sqrtMagic * fun1;
		dLon = dLon * sqrtMagic * fun2 / Math.cos(radLat);
		outlat = wgLat + dLat;
		outlng = wgLon + dLon;
		
		MapCoordinate outCoordinate = new MapCoordinate(outlat, outlng);
		
		return outCoordinate;
	}
	
	/**
	 * 从地球坐标系 (WGS-84) 到火星坐标系 (GCJ-02)  ，批量坐标转换;
	 * 谷歌地图，高德地图，腾讯地图 等采用火星坐标系;
	 * 这个坐标转换算法只适合于中国大陆;
	 * @param coordinateList - 需要转换的经纬度列表
	 * @return 转换后的经纬度列表
	 */
	public static MapCoordinateList Points_From_WGS84_To_GCJ02(MapCoordinateList coordinateList)
	{
		double wgLat, wgLon, outlat, outlng;
		List<MapCoordinate> coordiantes = coordinateList.getCoordinateList();
		MapCoordinateList outMapCoordinateList = new MapCoordinateList();
		
		for(MapCoordinate coordinate : coordiantes)
		{
			wgLat = coordinate.getLatitude();
			wgLon = coordinate.getLongitude();
			
			double dLat = transformLat(wgLon - 105.0, wgLat - 35.0);
			double dLon = transformLon(wgLon - 105.0, wgLat - 35.0);
			double radLat = wgLat * pi180;
			double magic = Math.sin(radLat);
			magic = 1 - CONSTANT.ee * magic * magic;
			double sqrtMagic = Math.sqrt(magic);
			dLat = dLat * magic * sqrtMagic * fun1;
			dLon = dLon * sqrtMagic * fun2 / Math.cos(radLat);
			outlat = wgLat + dLat;
			outlng = wgLon + dLon;
			
			MapCoordinate outCoordinate = new MapCoordinate(outlat, outlng);
			outMapCoordinateList.addCoordinate(outCoordinate);
		}
		
		return outMapCoordinateList;
	}
	
	/**
	 * 从地球坐标系 (WGS-84) 到百度坐标系 (BD-09);
	 * 这个坐标转换算法只适合于中国大陆;
	 * @param latitude - 纬度
	 * @param longtitude - 经度
	 * @return 转换后的经纬度值
	 */
	public static MapCoordinate WGS84_To_BD09(double latitude, double longtitude)
	{
		double wgLat, wgLon, outlat, outlng;
		
		wgLat = latitude;
		wgLon = longtitude;
		
		double dLat = transformLat(wgLon - 105.0, wgLat - 35.0);
		double dLon = transformLon(wgLon - 105.0, wgLat - 35.0);
		double radLat = wgLat * pi180;
		double magic = Math.sin(radLat);
		magic = 1 - CONSTANT.ee * magic * magic;
		double sqrtMagic = Math.sqrt(magic);
		dLat = dLat * magic * sqrtMagic * fun1;
		dLon = dLon * sqrtMagic * fun2 / Math.cos(radLat);
		outlat = wgLat + dLat;
		outlng = wgLon + dLon;
		
		double z = Math.sqrt(outlng * outlng + outlat * outlat) + 0.00002 * Math.sin(outlat * x_pi);
		double theta = Math.atan2(outlat, outlng) + 0.000003 * Math.cos(outlng * x_pi);
		
		outlng = z * Math.cos(theta) + 0.0065;
		outlat = z * Math.sin(theta) + 0.006;
		
		MapCoordinate outCoordinate = new MapCoordinate(outlat, outlng);
		
		return outCoordinate;
	}
	
	/**
	 * 从地球坐标系 (WGS-84) 到百度坐标系 (BD-09);
	 * 这个坐标转换算法只适合于中国大陆;
	 * @param coordinate - 需要转换的经纬度值
	 * @return 转换后的经纬度值
	 */
	public static MapCoordinate WGS84_To_BD09(MapCoordinate coordinate)
	{
		double wgLat, wgLon, outlat, outlng;
		
		wgLat = coordinate.getLatitude();
		wgLon = coordinate.getLongitude();
		
		double dLat = transformLat(wgLon - 105.0, wgLat - 35.0);
		double dLon = transformLon(wgLon - 105.0, wgLat - 35.0);
		double radLat = wgLat * pi180;
		double magic = Math.sin(radLat);
		magic = 1 - CONSTANT.ee * magic * magic;
		double sqrtMagic = Math.sqrt(magic);
		dLat = dLat * magic * sqrtMagic * fun1;
		dLon = dLon * sqrtMagic * fun2 / Math.cos(radLat);
		outlat = wgLat + dLat;
		outlng = wgLon + dLon;
		
		double z = Math.sqrt(outlng * outlng + outlat * outlat) + 0.00002 * Math.sin(outlat * x_pi);
		double theta = Math.atan2(outlat, outlng) + 0.000003 * Math.cos(outlng * x_pi);
		
		outlng = z * Math.cos(theta) + 0.0065;
		outlat = z * Math.sin(theta) + 0.006;
		
		MapCoordinate outCoordinate = new MapCoordinate(outlat, outlng);
		
		return outCoordinate;
	}
	
	/**
	 * 从地球坐标系 (WGS-84) 到百度坐标系 (BD-09) ，批量坐标转换;
	 * 这个坐标转换算法只适合于中国大陆;
	 * @param coordinateList - 需要转换的经纬度列表
	 * @return 转换后的经纬度列表
	 */
	public static MapCoordinateList Points_From_WGS84_To_BD09(MapCoordinateList coordinateList)
	{
		double wgLat, wgLon, outlat, outlng;
		List<MapCoordinate> coordiantes = coordinateList.getCoordinateList();
		MapCoordinateList outMapCoordinateList = new MapCoordinateList();
		
		for(MapCoordinate coordinate : coordiantes)
		{
			wgLat = coordinate.getLatitude();
			wgLon = coordinate.getLongitude();
			
			double dLat = transformLat(wgLon - 105.0, wgLat - 35.0);
			double dLon = transformLon(wgLon - 105.0, wgLat - 35.0);
			double radLat = wgLat * pi180;
			double magic = Math.sin(radLat);
			magic = 1 - CONSTANT.ee * magic * magic;
			double sqrtMagic = Math.sqrt(magic);
			dLat = dLat * magic * sqrtMagic * fun1;
			dLon = dLon * sqrtMagic * fun2 / Math.cos(radLat);
			outlat = wgLat + dLat;
			outlng = wgLon + dLon;
			
			double z = Math.sqrt(outlng * outlng + outlat * outlat) + 0.00002 * Math.sin(outlat * x_pi);
			double theta = Math.atan2(outlat, outlng) + 0.000003 * Math.cos(outlng * x_pi);
			
			outlng = z * Math.cos(theta) + 0.0065;
			outlat = z * Math.sin(theta) + 0.006;
			
			MapCoordinate outCoordinate = new MapCoordinate(outlat, outlng);
			outMapCoordinateList.addCoordinate(outCoordinate);
		}
		
		return outMapCoordinateList;
	}
	
	private static double transformLat(double x, double y)
	{
		double ret = -100.0 + 2.0 * x + 3.0 * y + 0.2 * y * y + 0.1 * x * y + 0.2 * Math.sqrt(Math.abs(x));
		
		ret += ((Math.sin(6.0 * x * CONSTANT.pi) + Math.sin(2.0 * x * CONSTANT.pi)) * 13.333333333333333);
		ret += (13.333333333333333 * Math.sin(y * CONSTANT.pi) + 26.6666666666666667 * Math.sin(y / 3.0 * CONSTANT.pi));
		ret += (106.6666666666666667 * Math.sin(y / 12.0 * CONSTANT.pi) + 213.3333333333333333 * Math.sin(y * CONSTANT.pi / 30.0));
		
		return ret;
	}
	
	private static double transformLon(double x, double y)
	{
		double ret = 300.0 + x + 2.0 * y + 0.1 * x * x + 0.1 * x * y + 0.1 * Math.sqrt(Math.abs(x));
		
		ret += ((Math.sin(6.0 * x * CONSTANT.pi) + Math.sin(2.0 * x * CONSTANT.pi)) * 13.333333333333333);
		ret += (13.333333333333333 * Math.sin(x * CONSTANT.pi) + 26.6666666666666667 * Math.sin(x / 3.0 * CONSTANT.pi));
		ret += (100.0 * Math.sin(x / 12.0 * CONSTANT.pi) + 200.0 * Math.sin(x / 30.0 * CONSTANT.pi));
		
		return ret;
	}
}
