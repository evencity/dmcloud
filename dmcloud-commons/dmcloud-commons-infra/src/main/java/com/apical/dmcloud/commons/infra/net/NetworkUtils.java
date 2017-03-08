package com.apical.dmcloud.commons.infra.net;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class NetworkUtils
{
	/**
	 * ip v4
	 */
	public static int IPv4 = 1;
	
	/**
	 * ip v6
	 */
	public static int IPv6 = 2;
	
	/**
	 * 内网ip，即地区本地地址（SiteLocalAddress）
	 * A类：10.0.0.0 ~ 10.255.255.255
	 * B类：172.16.0.0 ~ 172.31.255.255
	 * C类：192.168.0.0 ~ 192.168.255.255
	 */
	public static long ABegin = 167772160L;
	public static long AEnd = 184549375L;
	public static long BBegin = 2886729728L;
	public static long BEnd = 2887778303L;
	public static long CBegin = 3232235520L;
	public static long CEnd = 3232301055L;
	
	/**
	 * ip地址转long值
	 * @param ipAddress ip地址
	 * @return ip地址的long值
	 */
	private static long getIpNum(String address)
	{   
		String ipAddress = getServerIP(address);
		String [] ip = ipAddress.split("\\.");
		long a = Integer.parseInt(ip[0]);
		long b = Integer.parseInt(ip[1]);
		long c = Integer.parseInt(ip[2]);
		long d = Integer.parseInt(ip[3]);
  
		long ipNum = a * 256 * 256 * 256 + b * 256 * 256 + c * 256 + d;
		return ipNum;
	}
	/**
	 * www.apicalcloud.com.cn域名(不含http等)转IP
	 * @param domain 域名
	 * @return IP字符串
	 */
    public static String getServerIP(String domain) {  
    	InetAddress[] server = null;;
        try {  
            server = InetAddress.getAllByName(domain);  
        } catch (UnknownHostException e) {  
           // e.printStackTrace();  
        	
        	return "127.0.0.1";
        }  
        if(server.length > 0){
        	return server[0].getHostAddress();
        }
        return "127.0.0.1";  
    }  

    /**
	 * 是否是内网ip
	 * @param userIp 用户ip地址
	 * @param begin 内网起始ip地址的long值
	 * @param end 内网结束ip地址的long值
	 * @return 是否为内外ip
	 */
	private static boolean isInner(long userIp, long begin, long end)
	{
		return (userIp >= begin) && (userIp <= end);   
	}
	
	/**
	 * 通过ip地址判断，ip类型（ipv4，或ipv6）
	 * @param ip ip地址
	 * @return 1：ipv4, 2:ipv6, -1：未知
	 * @throws UnknownHostException 
	 */
	public static int IPAddressType(String ip) throws UnknownHostException
	{
		InetAddress address = InetAddress.getByName(ip);
		if(address instanceof java.net.Inet4Address)
		{ 
			return IPv4;
		}
		else if(address instanceof java.net.Inet6Address)
		{ 
			return IPv6;
		}
		else
		{ 
			return -1;
		}
	}
	
	/**
	 * 判断ip地址是否可以连接
	 * @param localHost 本地ip地址
	 * @param remoteHost 远程ip地址
	 * @return 是否可以连接
	 * @throws IOException
	 */
	public static boolean isIPAddressReachable(String localHost, String remoteHost) throws IOException
	{
		long remoteHostValue = getIpNum(remoteHost);
		long localHostValue = getIpNum(localHost);
		if(isInner(remoteHostValue, ABegin, AEnd))
		{//a类内网ip
			if(isInner(localHostValue, ABegin, AEnd))
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		else if(isInner(remoteHostValue, BBegin, BEnd))
		{//B类内网ip
			if(isInner(localHostValue, BBegin, BEnd))
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		else if(isInner(remoteHostValue, CBegin, CEnd))
		{//C类内网ip
			if(isInner(localHostValue, CBegin, CEnd))
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		else
		{
			return false;
		}
	}
}
