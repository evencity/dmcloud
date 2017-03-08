package com.apical.dmcloud.commons.infra.threadpool;

import java.util.concurrent.TimeUnit;

public interface ThreadPoolConfiguration
{
	/**
	 * 固定大小线程池的线程个数
	 */
	public static int FIXED_THREAD_COUNT = 10;
	
	/**
	 * 无界线程池中，空闲线程的空闲时间
	 */
	public static long CACHED_THREAD_ALIVETIME = 10;
	
	/**
	 * 无界线程池中，空闲线程的空闲时间单位
	 */
	public static TimeUnit CACHED_THREAD_ALIVETIME_UNIT = TimeUnit.MINUTES;
}
