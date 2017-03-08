package com.apical.dmcloud.commons.infra;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;

public class HttpRequestUtils
{
	/**
	 * 向指定 URL 发送POST方法的请求
	 * @param url 发送请求的 URL
	 * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @param connectTimeout 连接超时，毫秒数
	 * @param readTimeout 读超时，毫秒数
	 * @return 所代表远程资源的响应结果
	 * @throws IOException 
	 */
	public static String doPostRequest(String url, String param, int connectTimeout,
			int readTimeout) throws IOException
	{
		DataOutputStream out = null;
		BufferedReader in = null;
		
		try
		{
			URL realUrl = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) realUrl.openConnection();
			
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			//设置连接超时和读超时
			conn.setConnectTimeout(connectTimeout);
			conn.setReadTimeout(readTimeout);
			
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			
			// 获取URLConnection对象对应的输出流
			out = new DataOutputStream(conn.getOutputStream());
			
			// 发送请求参数
			out.writeBytes(param);
			// flush输出流的缓冲
			out.flush();
			
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(
					new InputStreamReader(conn.getInputStream(), Charset.forName("UTF-8")));
			
			String result = "";
			String line;
			while ((line = in.readLine()) != null)
			{
				result += line;
			}
			
			return result;
		}
		finally
		{
			try
			{
				if(out != null)
				{
					out.close();
				}
			}
			catch(IOException ex)
			{
			    ex.printStackTrace();
			}
			
			try
			{
				if(in != null)
				{
					in.close();
				}
			}
			catch(IOException ex)
			{
			    ex.printStackTrace();
			}
		}
	}
	
	/**
	 * 向指定 URL 发送GET方法的请求
	 * @param url 发送请求的 URL
	 * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @param connectTimeout 连接超时，毫秒数
	 * @param readTimeout 读超时，毫秒数
	 * @return 所代表远程资源的响应结果
	 * @throws Exception
	 */
	public static String doGetRequest(String url, String param, int connectTimeout,
			int readTimeout) throws IOException
	{
		BufferedReader reader = null;
		try
		{
			String urlNameString = url + "?" + param;
			URL realUrl = new URL(urlNameString);
			
			// 根据拼凑的URL，打开连接，URL.openConnection()函数会根据 URL的类型，返回不同的URLConnection子类的对象;
			// 在这里我们的URL是一个http，因此它实际上返回的是HttpURLConnection 
			HttpURLConnection connection = (HttpURLConnection) realUrl.openConnection();
			//设置连接超时和读超时
			connection.setConnectTimeout(connectTimeout);
			connection.setReadTimeout(readTimeout);
			
			// 建立与服务器的连接，并未发送数据
			connection.connect();
			
			// 发送数据到服务器并使用Reader读取返回的数据 
			reader = new BufferedReader(
					new InputStreamReader(connection.getInputStream(), Charset.forName("UTF-8")));
			
			String result = "";
			String line;
			while ((line = reader.readLine()) != null)
			{
				result += line;
			}
			
			// 断开连接
			connection.disconnect();
			
			return result;
		}
		finally
		{
			try
			{
				if(reader != null)
				{
					reader.close();
					reader = null;
				}
			}
			catch(IOException ex)
			{
			    ex.printStackTrace();
			}
		}
	}
}
