/**
 * @(#)StringUtils.java 2013-6-5
 *
 * Copyright 2013 MINDCENTER Inc. All rights reserved.
 * MINDCENTER PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.apical.dmcloud.commons.infra;

import java.util.Random;

/**
 * @author Administrator
 * 
 */
public class StringUtils
{
	static final private String base = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	
	/**
	 * 按照目标长度，使用字符'0'，来补全目标字符串
	 * 注意：目标字符串的长度小于目标长度时，才进行补充操作
	 * @param str 需要补全的字符串
	 * @param length 目标长度
	 * @return 补全后的字符串
	 */
	public static String complete(String str, int length)
	{
		StringBuffer result = new StringBuffer();
		if(org.apache.commons.lang.StringUtils.isNotBlank(str) && (length > 0))
		{
			if(str.length() < length)
			{
				for(int x = 0; x < (length - str.length()); x++)
				{
					result.append("0");
				}
				result.append(str);
				return result.toString();
			}
			else
			{
				return str;
			}
		}
		return null;
	}
	
	/**
	 * 获取特定长度的随机字符串
	 * @param length - 表示生成字符串的长度
	 * @return 目标长度的随机字符串
	 */
	public static String getRandomString(int length)
	{
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		int nBaseLenth = base.length();
		
		for (int i = 0; i < length; i++)
		{
			int number = random.nextInt(nBaseLenth);
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}
	
	/**
	 * 倒置字符串
	 * @param str 需要进行倒置的目标字符串
	 * @return 倒置后的字符串
	 */
	public static String reverseString(String str)
	{
		char[] arr = str.toCharArray();
		int middle = arr.length>>1;//EQ length/2
		int limit = arr.length - 1;
		for (int i = 0; i < middle; i++)
		{
			char tmp = arr[i];
			arr[i] = arr[limit-i];
			arr[limit-i] = tmp;
		}
		return new String(arr);
	}
	
	/**
	 * 获取文件的后缀名
	 * @param filename 文件名
	 * @return 后缀名
	 */
	public static String getFileExtension(String filename)
	{
		//通过文件的后缀名来判断资源的类型（暂未实现）
		int nDotPos = 0;
		
		nDotPos = filename.lastIndexOf('.');
		if((nDotPos != -1) && (nDotPos != 0))
		{
			String extension = filename.substring(nDotPos + 1);
			return extension;
		}
		else
		{
			return null;
		}
	}
	
}
