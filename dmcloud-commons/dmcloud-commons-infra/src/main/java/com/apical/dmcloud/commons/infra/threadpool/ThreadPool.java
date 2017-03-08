/**
 * @(#) ThreadPool.java 2013-1-11
 */
package com.apical.dmcloud.commons.infra.threadpool;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * ThreadPool
 * 
 * @author Administrator
 * 
 */
public class ThreadPool<I, O> implements Assignable<ExecutableParameter<I, O>>, StateListener
{
	private ThreadPoolExecutor threadPool = null;
	private ThreadPoolParameter threadPoolPara = null;
	private String name = null;

	public ThreadPool(String name)
	{
		this.name = name;
	}
	
	/**
	 * 获取线程池的名称
	 * @return String - 线程池的名称
	 */
	public String getName()
	{
		return this.name;
	}
	
	/**
	 * 获取线程池中维护线程的最大数量
	 * @return int - 返回维护线程的最大数量
	 */
	public int getMax()
	{
		return threadPoolPara.getMax();
	}

	/**
	 * 设置线程池中维护线程的最大数量
	 * @param max - 维护线程的最大数量
	 */
	public void setMax(int max)
	{
		this.threadPoolPara.setMax(max);
		this.threadPool.setMaximumPoolSize(max);
	}

	/**
	 * 获取线程池中维护线程的最少数量
	 * @return int - 返回维护线程的最少数量
	 */
	public int getMin()
	{
		return threadPoolPara.getMin();
	}

	/**
	 * 设置线程池中维护线程的最少数量
	 * @param min - 维护线程的最少数量
	 */
	public void setMin(int min)
	{
		this.threadPoolPara.setMin(min);
		this.threadPool.setCorePoolSize(min);
	}

	/**
	 * 获取线程池的配置参数
	 * @return ThreadPoolParameter - 线程池配置参数对象
	 */
	public ThreadPoolParameter getThreadPoolPara()
	{
		return threadPoolPara;
	}

	/**
	 * 设置线程池的配置参数
	 * @param threadPoolPara - 线程池配置参数对象
	 */
	public void setThreadPoolPara(ThreadPoolParameter threadPoolPara)
	{
		this.threadPoolPara = threadPoolPara;
	}
	
	/**
	 * 验证线程池配置参数是否正确
	 * @throws IllegalArgumentException
	 */
	protected void validateArguments() throws IllegalArgumentException
	{
		if(this.threadPoolPara == null)
		{
			throw new IllegalArgumentException("threadpool parameter is null.");
		}
		
		int max = 0;
		int min = 0;
		long keepAliveTime = 0;
		BlockingQueue<Runnable> workQueue = null;
		
		max = this.threadPoolPara.getMax();
		min = this.threadPoolPara.getMin();
		keepAliveTime = this.threadPoolPara.getKeepAliveTime();
		workQueue = this.threadPoolPara.getWorkQueue();
		
		if (min < 0)
		{
			throw new IllegalArgumentException("threadPool argument 'min' must be large than 0.");
		}

		if (max < 0)
		{
			throw new IllegalArgumentException("threadPool argument 'max' must be large than 0.");
		}
		
		if (max < min)
		{
			throw new IllegalArgumentException("threadPool argument 'max' must be large than 'min'.");
		}
		
		if(keepAliveTime < 0)
		{
			throw new IllegalArgumentException("threadPool argument 'keepAliveTime' must be large than 0.");
		}
		
		if(workQueue == null)
		{
			throw new IllegalArgumentException("threadPool argument 'workQueue' is null.");
		}
	}

	public void initialize()
	{
		this.validateArguments();
		
		ThreadFactory threadFactory = null;
		RejectedExecutionHandler rejectedExcutionhandler = null;

		threadFactory = this.threadPoolPara.getThreadFactory();
		rejectedExcutionhandler = this.threadPoolPara.getRejectedExcutionhandler();
		
		if(threadFactory != null)
		{
			if(rejectedExcutionhandler != null)
			{
				this.threadPool = new ThreadPoolExecutor(
						this.threadPoolPara.getMin(),
						this.threadPoolPara.getMax(),
						this.threadPoolPara.getKeepAliveTime(),
						this.threadPoolPara.getTimeUnit(),
						this.threadPoolPara.getWorkQueue(),
						threadFactory,
						rejectedExcutionhandler
						);
			}
			else
			{
				this.threadPool = new ThreadPoolExecutor(
						this.threadPoolPara.getMin(),
						this.threadPoolPara.getMax(),
						this.threadPoolPara.getKeepAliveTime(),
						this.threadPoolPara.getTimeUnit(),
						this.threadPoolPara.getWorkQueue(),
						threadFactory
						);
			}
		}
		else
		{
			if(rejectedExcutionhandler != null)
			{
				this.threadPool = new ThreadPoolExecutor(
						this.threadPoolPara.getMin(),
						this.threadPoolPara.getMax(),
						this.threadPoolPara.getKeepAliveTime(),
						this.threadPoolPara.getTimeUnit(),
						this.threadPoolPara.getWorkQueue(),
						rejectedExcutionhandler
						);
			}
			else
			{
				this.threadPool = new ThreadPoolExecutor(
						this.threadPoolPara.getMin(),
						this.threadPoolPara.getMax(),
						this.threadPoolPara.getKeepAliveTime(),
						this.threadPoolPara.getTimeUnit(),
						this.threadPoolPara.getWorkQueue()
						);
			}
		}
	}

	public void shutdown()
	{
		this.threadPool.shutdown();
	}

	public void assign(ExecutableParameter<I, O> service)
	{
		WorkerThread<I, O> workerThread = new WorkerThread<I, O>();
		workerThread.assign(service);
		this.threadPool.execute(workerThread);
	}

	@Override
	public void busyTimes(StateEvent evt)
	{
		// TODO Auto-generated method stub
	}

	@Override
	public void freeTimes(StateEvent evt)
	{
		// TODO Auto-generated method stub
	}
}
