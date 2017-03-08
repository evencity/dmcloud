package com.apical.dmcloud.commons.infra.polygon;

import java.util.List;

public class PolygonUtils
{
	/**
	 * 判断点是否在多边形内部
	 * 采用引射线法：从目标点出发引一条射线，看这条射线和多边形所有边的交点数目。
	 * 如果有奇数个交点，则说明在内部，如果有偶数个交点，则说明在外部。
	 * @param p 目标点
	 * @param poligonVertexList 多边形顶点列表
	 * @return 是否在多边形内部
	 */
	public static boolean pointInPolygon(Point p, List<Point> poligonVertexList)
	{
		if(p == null || poligonVertexList == null)
		{
			return false;
		}
		
		//顶点个数少于3个
		int size = poligonVertexList.size();
		if(size < 3)
		{
			return false;
		}
		
		boolean result = false;
		for(int i = 0, j = size - 1; i < size; i++)
		{
			Point p1 = poligonVertexList.get(i);
			Point p2 = poligonVertexList.get(j);
			
			if(p1.y < p.y && p2.y >= p.y || p2.y < p.y && p1.y >= p.y)
			{
				//采用直线点斜式：(x-x1)/(x2-x1)=(y-y1)/(y2-y1)，算出交点的x坐标
				float intersectionX = p1.x + (p.y - p1.y) * (p2.x - p1.x) / (p2.y - p1.y);
				if(intersectionX < p.x)
				{
					//有交点
					result = !result;
				}
			}
		}
		
		return result;
	}
}
