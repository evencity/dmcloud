package com.apical.dmcloud.commons.infra.CoordinateConvert;

import java.util.List;

import com.apical.dmcloud.commons.infra.CONSTANT;

/**
 * 该类实现了：计算地球上任意两点之间的距离，即球面上两点距离
 * @author qiuzeng
 *
 */
public class CoordinateDistance
{
	/**
	 * 计算地球上任意两点之间的距离，即球面上两点距离
	 * 计算公式如下：
	 * x = (lng2 - lng1) * PI * R * cos(((lat1 + lat2) / 2) * PI / 180) / 180;
	 * y = (lat2 - lat1) * PI * R / 180;
	 * s = sqrt(x * x + y * y);
	 * 其中，
	 * lng1表示第一个点的经度值
	 * lat1表示第一个点的纬度值
	 * lng2表示第二个点的经度值
	 * lat2表示第二个点的纬度值
	 * PI = 3.1415926535898
	 * R = 6371229 地球的半径
	 * 测试：
	 * (113.995861E,22.671563N),(113.995980E,22.671349N)距离为:,Delta s =26.79米
	 * (113.995980E,22.671349N),(113.996075E,22.671177N)距离为:,Delta s =21.51米
	 * @param lng1 lng1表示第一个点的经度值
	 * @param lat1 lat1表示第一个点的纬度值
	 * @param lng2 lng2表示第二个点的经度值
	 * @param lat2 lat2表示第二个点的纬度值
	 * @return 地球上任意两点之间的距离
	 */
	public static double calculateDistanceOfTwoPoints(double lng1,
			double lat1,
			double lng2,
			double lat2)
	{
		double x = lng2 - lng1;
		double y = lat2 + lat1;
		
		y = y * CONSTANT.pi;
		y = y / 360;
		x = x * Math.cos(y);
		
		y = lat2 - lat1;
		
		x = x * x;
		y = y * y;
		x = x + y;
		
		y = Math.sqrt(x) * CONSTANT.r * CONSTANT.pi;
		y = y / 180;
		
		return y;
	}
	
	public static double calculateDistanceOfTwoPoints(MapCoordinate firstPoint,
			MapCoordinate secondPoint)
	{
		return calculateDistanceOfTwoPoints(firstPoint.getLongitude(),
				firstPoint.getLatitude(),
				secondPoint.getLongitude(),
				secondPoint.getLatitude());
	}
	
	public static double calculateMileage(List<MapCoordinate> pointList)
	{
		if((pointList == null) || (pointList.size() < 2))
		{
			return 0;
		}
		
		double mileage = 0;
		MapCoordinate firstPoint = pointList.get(0);
		for(int i = 1; i < pointList.size(); i++)
		{
			MapCoordinate currentPoint = pointList.get(i);
			mileage += calculateDistanceOfTwoPoints(firstPoint, currentPoint);
			firstPoint = currentPoint;
		}
		
		return mileage;
	}
}
