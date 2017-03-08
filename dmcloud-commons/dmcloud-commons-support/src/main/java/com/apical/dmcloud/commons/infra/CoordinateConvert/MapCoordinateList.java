package com.apical.dmcloud.commons.infra.CoordinateConvert;

import java.util.ArrayList;
import java.util.List;

/**
 * 经纬度坐标列表
 * @author qiuzeng
 *
 */
public class MapCoordinateList
{
	private List<MapCoordinate> coordianteList = new ArrayList<MapCoordinate>();
	
	/**
	 * 添加坐标点
	 * @param latitude - 纬度
	 * @param longitude - 经度
	 */
	public void addCoordinate(double latitude, double longitude)
	{
		MapCoordinate coordinate = new MapCoordinate(latitude, longitude);
		coordianteList.add(coordinate);
	}
	
	/**
	 * 添加坐标点
	 * @param coordinate - 坐标点
	 */
	public void addCoordinate(MapCoordinate coordinate)
	{
		if(coordinate == null)
		{
			return;
		}
		coordianteList.add(coordinate);
	}
	
	/**
	 * 
	 * @return List<MapCoodinate> - 坐标点列表
	 */
	public List<MapCoordinate> getCoordinateList()
	{
		return coordianteList;
	}
}
