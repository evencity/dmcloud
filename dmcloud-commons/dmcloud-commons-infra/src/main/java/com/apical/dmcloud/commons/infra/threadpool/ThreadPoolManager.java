/**
 * @(#)ThreadPoolManager.java 2013-7-1
 *
 * Copyright 2013 MINDCENTER Inc. All rights reserved.
 * MINDCENTER PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.apical.dmcloud.commons.infra.threadpool;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author Administrator
 * 
 */
public class ThreadPoolManager
{
	private final static Hashtable<Entry, ThreadPool<?, ?>> table = new Hashtable<Entry, ThreadPool<?, ?>>();
	
	/**
	 * cachedThreadPoolParameter用来配置：无界线程池，可以进行自动线程回收，空闲的线程会在10分钟后被回收。
	 * 当任务数增加时，此线程池又可以智能的添加新线程来处理任务，除非，有空闲的线程。
	 */
	private final static ThreadPoolParameter cachedThreadPoolParameter = new ThreadPoolParameter();
	
	/**
	 * fixedThreadPoolParameter用来配置：固定大小线程池。目前，默认为10个，不会存在空闲的线程。
	 * 每次提交一个任务就创建一个线程，直到线程达到线程池的最大大小。线程池的大小一旦达到最大值就会保持不变，
	 * 若此时继续增加任务，则任务会被放入任务队列中，直到有空闲的线程。
	 */
	private final static ThreadPoolParameter fixedThreadPoolParameter = new ThreadPoolParameter();
	
	static
	{
		cachedThreadPoolParameter.setMin(0);
		cachedThreadPoolParameter.setMax(Integer.MAX_VALUE);
		cachedThreadPoolParameter.setKeepAliveTime(ThreadPoolConfiguration.CACHED_THREAD_ALIVETIME);
		cachedThreadPoolParameter.setTimeUnit(ThreadPoolConfiguration.CACHED_THREAD_ALIVETIME_UNIT);
		cachedThreadPoolParameter.setWorkQueue(new SynchronousQueue<Runnable>());
		
		fixedThreadPoolParameter.setMin(ThreadPoolConfiguration.FIXED_THREAD_COUNT);
		fixedThreadPoolParameter.setMax(ThreadPoolConfiguration.FIXED_THREAD_COUNT);
		fixedThreadPoolParameter.setKeepAliveTime(0);
		fixedThreadPoolParameter.setTimeUnit(TimeUnit.SECONDS);
		fixedThreadPoolParameter.setWorkQueue(new LinkedBlockingQueue<Runnable>());
	}
	
	/**
	 * 创建线程池对象
	 * @param inputType 输入类型
	 * @param outputType 输出类型
	 * @param reusable 是否可复用
	 * @param fallback 默认线程池对象
	 * @return 线程池对象
	 */
	@SuppressWarnings("unchecked")
	public static <I, O> ThreadPool<I, O> createThreadPool(
			Class<I> inputType,
			Class<O> outputType,
			boolean reusable,
			ThreadPool<I, O> fallback
			)
	{
		if(reusable)
		{
			//若创建的线程池可复用，则直接在缓存中进行查找
			Entry entry = new Entry(inputType, outputType);
			ThreadPool<I, O> cachedInstance = (ThreadPool<I, O>) table.get(entry);
			
			if (cachedInstance != null)
			{
				return cachedInstance;
			}
			
			if (fallback != null)
			{
				table.put(entry, fallback);
				for(Iterator<Entry> itr = table.keySet().iterator(); itr.hasNext();)
				{
					Entry key = itr.next();
					System.out.println(key.toString());
				}
				return fallback;
			}
			
			return null;
		}
		else
		{
			if (fallback != null)
			{
				for(Iterator<Entry> itr = table.keySet().iterator(); itr.hasNext();)
				{
					Entry key = itr.next();
					System.out.println(key.toString());
				}
				return fallback;
			}
			
			return null;
		}
	}
	
	/**
	 * 创建默认的无界线程池；在给该线程池添加任务时，会立即给新任务分配一个新的线程，线程池在完成这个动作后，才会去处理下一个任务
	 * @param inputType - 输入参数类型
	 * @param outputType - 输出参数类型
	 * @param reusable - 是否可复用
	 * @return ThreadPool<I, O> - 默认的无界线程
	 * @throws Exception
	 */
	public static <I, O> ThreadPool<I, O> getDefaultCachedThreadPool(Class<I> inputType,
			Class<O> outputType, boolean reusable)
	{
		ThreadPool<I, O> ref = null;
		ref = createThreadPool(inputType, outputType, reusable, null);
		if (ref == null)
		{
			ref = newThreadPoolInstance();
			ref.setThreadPoolPara(cachedThreadPoolParameter);
			ref.initialize();
		}
		
		return ref;
	}
	
	/**
	 * 创建默认的固定大小线程池；目前，该线程池最多只会创建10个线程，多余的任务会被放入任务队列中，等待空闲的线程
	 * @param inputType - 输入参数类型
	 * @param outputType - 输出参数类型
	 * @param reusable - 是否可复用
	 * @return 默认的固定大小线程池
	 * @throws Exception
	 */
	public static <I, O> ThreadPool<I, O> getDefaultFixedThreadPool(Class<I> inputType,
			Class<O> outputType, boolean reusable)
	{
		ThreadPool<I, O> ref = null;
		ref = createThreadPool(inputType, outputType, reusable, null);
		if (ref == null)
		{
			ref = newThreadPoolInstance();
			ref.setThreadPoolPara(fixedThreadPoolParameter);
			ref.initialize();
		}
		
		return ref;
	}
	
	/**
	 * 创建自定义的线程池
	 * @param inputType - 输入参数类型
	 * @param outputType - 输出参数类型
	 * @param threadPoolPara - 线程池配置参数
	 * @param reusable - 是否可复用
	 * @return 默认的固定大小线程池
	 * @throws Exception
	 */
	public static <I, O> ThreadPool<I, O> getThreadPool(Class<I> inputType,
			Class<O> outputType, ThreadPoolParameter threadPoolPara, boolean reusable)
	{
		ThreadPool<I, O> ref = null;
		ref = createThreadPool(inputType, outputType, reusable, null);
		if (ref == null)
		{
			ref = newThreadPoolInstance();
			ref.setThreadPoolPara(threadPoolPara);
			ref.initialize();
		}
		
		return ref;
	}
	
	public static <I, O> boolean checkThreadPoolType(Class<I> inputType, Class<O> outputType)
	{
		Entry entry = new Entry(inputType, outputType);
		
		return table.containsKey(entry);
	}
	
	private static <I, O> ThreadPool<I, O> newThreadPoolInstance()
	{
		return new ThreadPool<I, O>("Default-Thread-Pool");
	}
	
	static class Entry
	{
		final Class<?> input;
		final Class<?> output;
		
		/**
		 * @param input
		 * @param output
		 */
		public Entry(Class<?> input, Class<?> output)
		{
			super();
			this.input = input;
			this.output = output;
		}
		
		@Override
		public boolean equals(Object o)
		{
			if (o instanceof Entry)
			{
				Entry entry = (Entry) o;
				return (entry.input.equals(this.input)) && (entry.output.equals(this.output));
			}
			return false;
		}
		
		@Override
		public int hashCode()
		{
			return 0;
		}
		
		@Override
		public String toString()
		{
			String out = input.toString() + " " + output.toString();
			
			return out;
		}
		
		public static Entry valueOf(Class<?> input, Class<?> output)
		{
			return new Entry(input, output);
		}
		
		public static Entry asObjectEntry()
		{
			return Entry.valueOf(Object.class, Object.class);
		}
	}
}
