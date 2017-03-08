package com.apical.dmcloud.commons.infra.threadpool;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ThreadPoolExecutor.AbortPolicy;

public class ThreadPoolParameter
{
	/**
	 * 线程池中维护线程的最大数量
	 */
	private int max = 20;
	
	/**
	 * 线程池中维护线程的最少数量
	 */
	private int min = 10;
	
	/**
	 * 线程池中维护线程所允许的空闲时间
	 */
	private long keepAliveTime = 0;
	
	/**
	 * 线程池中维护线程所允许的空闲时间单位，默认单位是秒;
	 * 可以设置为分钟，小时，天等
	 */
	private TimeUnit timeUnit = TimeUnit.SECONDS;
	
	/**
	 * 线程池的执行前用于保持任务的队列
	 */
	private BlockingQueue<Runnable> workQueue = null;
	
	/**
	 * 创建新线程的工厂；此为可选参数。
	 * 默认值为：Executors.defaultThreadFactory()
	 */
	private ThreadFactory threadFactory = Executors.defaultThreadFactory();
	
	/**
	 * 由于超出线程范围和队列容量而使执行被阻塞时所使用的处理程序；此为可选参数。
	 * 默认值为：AbortPolicy()
	 */
	private RejectedExecutionHandler rejectedExcutionhandler = new AbortPolicy();
	
	/**
	 * 获取线程池中维护线程的最大数量
	 * @return int - 返回维护线程的最大数量
	 */
	public int getMax()
	{
		return max;
	}

	/**
	 * 设置线程池中维护线程的最大数量
	 * @param max - 维护线程的最大数量
	 */
	public void setMax(int max)
	{
		this.max = max;
	}

	/**
	 * 获取线程池中维护线程的最少数量
	 * @return int - 返回维护线程的最少数量
	 */
	public int getMin()
	{
		return min;
	}

	/**
	 * 设置线程池中维护线程的最少数量
	 * @param min - 维护线程的最少数量
	 */
	public void setMin(int min)
	{
		this.min = min;
	}

	/**
	 * 获取线程池中维护线程所允许的空闲时间
	 * @return long - 维护线程所允许的空闲时间
	 */
	public long getKeepAliveTime()
	{
		return keepAliveTime;
	}

	/**
	 * 设置线程池中维护线程所允许的空闲时间
	 * @param keepAliveTime - 维护线程所允许的空闲时间
	 */
	public void setKeepAliveTime(long keepAliveTime)
	{
		this.keepAliveTime = keepAliveTime;
	}

	/**
	 * 获取线程池中维护线程所允许的空闲时间单位
	 * @return TimeUnit - 维护线程所允许的空闲时间单位
	 */
	public TimeUnit getTimeUnit()
	{
		return timeUnit;
	}

	/**
	 * 设置线程池中维护线程所允许的空闲时间单位
	 * @param timeUnit - 维护线程所允许的空闲时间单位
	 */
	public void setTimeUnit(TimeUnit timeUnit)
	{
		this.timeUnit = timeUnit;
	}

	/**
	 * 获取线程池的执行前用于保持任务的队列
	 * @return BlockingQueue<Runnable> - 保持任务的队列
	 */
	public BlockingQueue<Runnable> getWorkQueue()
	{
		return workQueue;
	}

	/**
	 * 设置线程池的执行前用于保持任务的队列
	 * @param workQueue - 保持任务的队列
	 */
	public void setWorkQueue(BlockingQueue<Runnable> workQueue)
	{
		this.workQueue = workQueue;
	}

	/**
	 * 获取执行程序创建新线程时使用的工厂
	 * @return ThreadFactory - 创建新线程的工厂
	 */
	public ThreadFactory getThreadFactory()
	{
		return threadFactory;
	}

	/**
	 * 设置执行程序创建新线程时使用的工厂
	 * @param threadFactory - 创建新线程的工厂
	 */
	public void setThreadFactory(ThreadFactory threadFactory)
	{
		this.threadFactory = threadFactory;
	}

	/**
	 * 获取由于超出线程范围和队列容量而使执行被阻塞时所使用的处理程序
	 * @return RejectedExecutionHandler - 被阻塞时所使用的处理程序
	 */
	public RejectedExecutionHandler getRejectedExcutionhandler()
	{
		return rejectedExcutionhandler;
	}

	/**
	 * 设置由于超出线程范围和队列容量而使执行被阻塞时所使用的处理程序
	 * @param rejectedExcutionhandler - 被阻塞时所使用的处理程序
	 */
	public void setRejectedExcutionhandler(
			RejectedExecutionHandler rejectedExcutionhandler)
	{
		this.rejectedExcutionhandler = rejectedExcutionhandler;
	}
}
