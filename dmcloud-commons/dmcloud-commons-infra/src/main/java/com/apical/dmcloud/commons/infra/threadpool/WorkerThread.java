/**
 * @(#) WorkerThread.java 2013-1-11
 */
package com.apical.dmcloud.commons.infra.threadpool;

import java.util.ArrayList;
import java.util.List;

/**
 * WorkerThread in the thread pool
 * 
 * @author mclaren
 * 
 */
public class WorkerThread<I, O> implements Runnable, Assignable<ExecutableParameter<I, O>>
{
	//工作线程的状态常量
	private static final int BUSYTIMES = 0;
	private static final int FREETIMES = 1;
	
	/**
	 * Lock for the multi-thread environment.
	 */
	private Object lock = new Object();
	
	/**
	 * 工作线程的任务对象
	 */
	private ExecutableParameter<I, O> executable = null;
	
	/**
	 * 工作线程的监听器列表
	 */
	private List<StateListener> stateListeners = new ArrayList<StateListener>();
	
	public WorkerThread()
	{
	}
	
	public void addStateListener(StateListener listener)
	{
		if (!this.stateListeners.contains(listener))
		{
			this.stateListeners.add(listener);
		}
	}
	
	public void removeStateListener(StateListener listener)
	{
		this.stateListeners.remove(listener);
	}

	/**
	 * Thread main entry point
	 */
	public void run() throws IllegalStateException
	{
		StateEvent evt = new StateEvent(this);
		
		synchronized (this.lock)
		{
			if (this.executable == null)
			{
				try
				{
					this.fireStateEvent(FREETIMES, evt);
					this.lock.wait();
				}
				catch (Exception e)
				{
					throw new IllegalStateException(e);
				}
			}
			
			this.fireStateEvent(BUSYTIMES, evt);
			/**
			 * run the executable
			 */
			try
			{
				O returns = this.executable.getExecutable().execute(this.executable.getParameter());
				this.executable.getCallbackHandler().callback(returns);
			}
			catch (Throwable e)
			{
				this.executable.getCallbackHandler().exceptionOccurred(e);
			}
			this.clean();
		}
	}
	
	protected void fireStateEvent(int type, StateEvent evt)
	{
		switch (type)
		{
		case BUSYTIMES:
			for (StateListener listener : this.stateListeners)
			{
				listener.busyTimes(evt);
			}
			break;
			
		case FREETIMES:
			for (StateListener listener : this.stateListeners)
			{
				listener.freeTimes(evt);
			}
			break;
		}
	}
	
	protected void clean()
	{
		synchronized (this.lock)
		{
			this.executable = null;
		}
	}
	
	public void assign(ExecutableParameter<I, O> service)
	{
		synchronized (this.lock)
		{
			this.executable = service;
			this.lock.notify();
		}
	}
	
	public void terminate()
	{
		synchronized (this.lock)
		{
			this.lock.notifyAll();
		}
	}
}
