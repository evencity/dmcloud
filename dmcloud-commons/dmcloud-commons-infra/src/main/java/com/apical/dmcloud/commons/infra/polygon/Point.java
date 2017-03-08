package com.apical.dmcloud.commons.infra.polygon;

import java.io.Serializable;

public class Point implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	/**
	 * x坐标
	 */
	public float x;
	
	/**
	 * y坐标
	 */
	public float y;
	
	public Point()
	{
		this(0, 0);
	}
	
	public Point(float x, float y)
	{
		this.x = x;
		this.y = y;
	}

	/**
	 * 获取x坐标
	 * @return x坐标
	 */
	public float getX()
	{
		return x;
	}

	/**
	 * 设置x坐标
	 * @param x x坐标
	 */
	public void setX(float x)
	{
		this.x = x;
	}

	/**
	 * 获取y坐标
	 * @return y坐标
	 */
	public float getY()
	{
		return y;
	}

	/**
	 * 设置y坐标
	 * @param y y坐标
	 */
	public void setY(float y)
	{
		this.y = y;
	}
}
